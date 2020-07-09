
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import modelo.Menu;
import rojerusan.RSNotifyFade;


public class Empleado {
    
    
    public void mostrarInsercion(DefaultTableModel modeloEmpleado,int contrato,String curp,Date fi,Date fin,String turno,float sueldo,String suc,String puesto){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
        String curpp="'"+curp+"'";
        String ap="";
        String nombre="";
        try {

            String sql = "select nom_per,ap_per from persona where curp_per="+curpp;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombre=rs.getString("nom_per");
                ap=rs.getString("ap_per");
            }
            rs.close();
            con.close();
        }catch(Exception e){
            System.err.println("Ocurrio un error al mostrar insercion empleado");
        }
        
        Object[] fila = new Object[9];
            fila[0] =contrato;
            fila[1] =curp;
            fila[2] =nombre;
            fila[3] =ap;
            fila[4] = puesto;
            fila[5] = sueldo;
            fila[6] = fi;
            fila[7] = fin;
            fila[8] = turno;
            modeloEmpleado.addRow(fila);
        
    }
    
    
    
    public void mostrarEmpleado(DefaultTableModel modeloEmpleado,JTable tableEmpleado){
        
        try {
            tableEmpleado.setModel(modeloEmpleado);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select * from v_contratos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloEmpleado.addColumn("No. Contrato");
            modeloEmpleado.addColumn("CURP");
            modeloEmpleado.addColumn("Nombre");
            modeloEmpleado.addColumn("Apellido Paterno");
            modeloEmpleado.addColumn("Puesto");
            modeloEmpleado.addColumn("Sueldo");
            modeloEmpleado.addColumn("Fecha Inicio");
            modeloEmpleado.addColumn("Fecha Fin");
            modeloEmpleado.addColumn("Calle");
            modeloEmpleado.addColumn("Turno");
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloEmpleado.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarEmpleado(String curp,Date fi,Date fin,String turno,float sueldo,int sucursal,int puesto){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into contrato values(null,?,?,?,?,?,?,?)");
            ps.setDate(1,fi);
            ps.setDate(2,fin);
            ps.setString(3,turno);
            ps.setFloat(4,sueldo);
            ps.setString(5,curp);
            ps.setInt(6,sucursal);
            ps.setInt(7,puesto);

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Empleado Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Registrar Empleado");
            System.out.println(ex);
        }
    }
    
    public void eliminarEmpleado(DefaultTableModel modeloEmpleado,JTable tableEmpleado){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableEmpleado.getSelectedRow();
            String curp = tableEmpleado.getValueAt(Fila, 0).toString();
            String calle = tableEmpleado.getValueAt(Fila, 7).toString();
            String fi = tableEmpleado.getValueAt(Fila, 5).toString();
            
            String curpp="'"+curp+"'";  
            //String callee="'"+calle+"'";
            String ffii="'"+fi+"'";
            
            System.err.println(curpp);
            //System.err.println(callee);
             System.err.println(ffii);
             
            ps = conn.prepareStatement("delete from contrato where curp_per="+curpp+" and finicio_con="+ffii+";");
            ps.execute();
            modeloEmpleado.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Empleado Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Empleado No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarEmpleado(JTable tableEmpleado,String curp,String pue,int puesto,float sueldo,String suc, int sucursal,Date fi, Date fin,String turno){
        int Fila = tableEmpleado.getSelectedRow();
        ResultSet rs=null;
        PreparedStatement ps = null;
        String nombre="",ap="";
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tableEmpleado.getSelectedRow();
            String curpp = tableEmpleado.getValueAt(Fila, 0).toString();
            String fechaini = tableEmpleado.getValueAt(Fila, 5).toString();
            
            String cuurp="'"+curpp+"'";
            String fecha="'"+fechaini+"'";
            System.err.println(cuurp);
            System.err.println(fecha);
            
            String cu="'"+curp+"'";
            String turnoo="'"+turno+"'";
            String feini="'"+fi+"'";
            String fefin="'"+fin+"'";
            System.out.println("curp:"+cu+" turno:"+turnoo+" inicio:"+feini+" fin:"+fefin);
            
            ps = conn.prepareStatement("update contrato set finicio_con="+feini+", ffin_con="+fefin+" ,turno_con="+turnoo+" , sueldo_con="+sueldo+" , curp_per="+cu+" , cve_suc="+sucursal+", cve_pue="+puesto+" where curp_per="+cuurp+" and finicio_con="+fecha+";");
            ps.execute();
            ps.close();
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Empleado modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            
        }catch(Exception e){
            new rojerusan.RSNotifyFade("ERROR", "Empleado No Se Ha Podido Modificar", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.err.println(e);
        }
            
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            String cu="'"+curp+"'";
            String sql = "select nom_per,ap_per from persona where curp_per="+cu;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombre=rs.getString("nom_per");
                ap=rs.getString("ap_per");
            }
            rs.close();
            conn.close();
        }catch(Exception e){
            System.err.println("Ocurrio un error alhacer consulta empleado");
        }
            tableEmpleado.setValueAt(curp, Fila, 0);
            tableEmpleado.setValueAt(nombre, Fila, 1);
            tableEmpleado.setValueAt(ap, Fila, 2);
            tableEmpleado.setValueAt(puesto, Fila, 3);
            tableEmpleado.setValueAt(sueldo, Fila, 4);
            tableEmpleado.setValueAt(fi, Fila, 5);
            tableEmpleado.setValueAt(fin, Fila, 6);
            tableEmpleado.setValueAt(sucursal, Fila, 7);
            tableEmpleado.setValueAt(turno, Fila, 8);
    }
    
    public void buscarEmpleado(JTable tableEmpleado,String curp){
        String where = "";
        if (!"".equals(curp)) {
            where = "where curp_per = '" + curp + "'";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableEmpleado.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql="select * from v_contratos "+where+";";
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("No. Contrato");
            modelo.addColumn("CURP");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido Paterno");
            modelo.addColumn("Puesto");
            modelo.addColumn("Sueldo");
            modelo.addColumn("Fecha Inicio");
            modelo.addColumn("Fecha Fin");
            modelo.addColumn("Calle");
            modelo.addColumn("Turno");
            
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
            }
            con.close();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
    
    public int numeroPuesto(String puesto){
        int col=0;
            try{
            String puestoo="'"+puesto+"'";
            System.out.println("salida puesto:"+puestoo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_pue from puesto where nom_pue="+puestoo+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_pue");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public int numeroSucursal(String calle){
        int col=0;
            try{
            String callee="'"+calle+"'";
            System.out.println("salida calle:"+calle);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_suc from sucursal where calle_suc="+callee+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_suc");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public int numeroContrato(String curp,Date fi){
        int col=0;
            try{
            String contratoo="'"+curp+"'";
            String fecha="'"+fi+"'";
            System.out.println("salida contrato y fecha:"+contratoo+","+fecha);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_con from contrato where curp_per="+contratoo+" and finicio_con="+fecha+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_con");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
}
