
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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


public class Persona {
    
    public void mostrarInsercion(DefaultTableModel modeloPersona,String curp, String ap,String am,String nombre,String sexo,String fecha,String calle,int numero,String orientacion,String colonia,String codigo,String municipio,String estado){
        Object[] fila = new Object[13];
            fila[0] = curp;
            fila[1] = nombre;
            fila[2] = ap;
            fila[3] = am;
            fila[4] = sexo;
            fila[5] = fecha;
            fila[6] = calle;
            fila[7] = numero;
            fila[8] = orientacion;
            fila[9] = colonia;
            fila[10] = codigo;
            fila[11] = municipio;
            fila[12] = estado;
            modeloPersona.addRow(fila);
        
    }
    
    
    
    public void mostrarPersona(DefaultTableModel modeloPersona,JTable tablePersona){
        
        try {
            tablePersona.setModel(modeloPersona);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_personas";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloPersona.addColumn("CURP");
            modeloPersona.addColumn("Nombre");
            modeloPersona.addColumn("Apellido Paterno");
            modeloPersona.addColumn("Apellido Materno");
            modeloPersona.addColumn("Sexo");
            modeloPersona.addColumn("Fecha de Nacimiento");
            modeloPersona.addColumn("Calle");
            modeloPersona.addColumn("Numero");
            modeloPersona.addColumn("Orientacion");
            modeloPersona.addColumn("Colonia");
            modeloPersona.addColumn("Codigo Postal");
            modeloPersona.addColumn("Municipio");
            modeloPersona.addColumn("Estado");
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloPersona.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarPersona(String curp, String ap,String am,String nombre,String sexo,Date fecha,String calle,int numero,String orientacion,int colonia){
        PreparedStatement ps = null;
        CallableStatement call=null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_registrarpersona(?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, curp);
            call.setString(2, ap);
            call.setString(3, am);
            call.setString(4, nombre);
            call.setString(5, sexo);
            call.setDate(6, fecha);
            call.setString(7, calle);
            call.setInt(8, numero);
            call.setString(9, orientacion);
            call.setInt(10, colonia);
            call.registerOutParameter("salida", Types.INTEGER);
            
            call.execute();      
            if(call.getInt("salida")==1){
                new rojerusan.RSNotifyFade("ADVERTENCIA", "Persona Ya Esta Registrada", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            }else{
                new rojerusan.RSNotifyFade("AVISO", "Persona Registrada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            }
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Registrar Persona");
            System.out.println(ex);
        }
    }
    
    public void eliminarPersona(DefaultTableModel modeloPersona,DefaultTableModel modeloContacto,DefaultTableModel modeloEmpleado,JTable tablePersona){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tablePersona.getSelectedRow();
            String curp = tablePersona.getValueAt(Fila, 0).toString();
            String curpp="'"+curp+"'";   
            System.err.println(curp);
            
            ps = conn.prepareStatement("delete from contacto where curp_per="+curpp+";");
            ps.execute();
            modeloContacto.removeRow(Fila);
            ps.close();
            
            ps = conn.prepareStatement("delete from empleado where curp_per="+curpp+";");
            ps.execute();
            modeloEmpleado.removeRow(Fila);
            ps.close();

            ps = conn.prepareStatement("delete from persona where curp_per="+curpp+";");
            ps.execute();
            modeloPersona.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Persona Eliminada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Persona No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarPersona(JTable tablePersona,String nombre,String ap,String am, String curp,String sexo,Date fecha,String calle,int numero,String orientacion,String coloniaa,int colonia, String codigo, String municipio,String estado ){
        int Fila = tablePersona.getSelectedRow();

        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tablePersona.getSelectedRow();
            String curpp = tablePersona.getValueAt(numFila, 0).toString();
            String cuurp="'"+curpp+"'";
            System.err.println(cuurp);
            
            String name="'"+nombre+"'";
            String paterno="'"+ap+"'";
            String materno="'"+am+"'";
            String cu="'"+curp+"'";
            String sex="'"+sexo+"'";
            String date="'"+fecha+"'";
            String street="'"+calle+"'";
            String ori="'"+orientacion+"'";
            
            
            ps = conn.prepareStatement("update persona set curp_per="+cu+", ap_per="+paterno+" ,am_per="+materno+" , nom_per="+name+" , sexo_per="+sex+" ,fechanac_per="+date+" ,calle_per="+street+", num_per="+numero+", orientacion_per="+ori+",cve_col="+colonia+" where curp_per="+cuurp+";");

            

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Persona Modificada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            tablePersona.setValueAt(curp, Fila, 0);
            tablePersona.setValueAt(nombre, Fila, 1);
            tablePersona.setValueAt(ap, Fila, 2);
            tablePersona.setValueAt(am, Fila, 3);
            tablePersona.setValueAt(sexo, Fila, 4);
            tablePersona.setValueAt(fecha, Fila, 5);
            tablePersona.setValueAt(calle, Fila, 6);
            tablePersona.setValueAt(numero, Fila, 7);
            tablePersona.setValueAt(orientacion, Fila, 8);
            tablePersona.setValueAt(coloniaa, Fila, 9);
            tablePersona.setValueAt(codigo, Fila, 10);
            tablePersona.setValueAt(municipio, Fila, 11);
            tablePersona.setValueAt(estado, Fila, 12);

            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Persona No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarPersona(JTable tablePersona,String curp){
        String where = "";
        if (!"".equals(curp)) {
            where = "where curp_per = '" + curp + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tablePersona.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = " select * from v_personas "+where+";";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("CURP");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido Paterno");
            modelo.addColumn("Apellido Materno");
            modelo.addColumn("Sexo");
            modelo.addColumn("Fecha Nacimiento");
            modelo.addColumn("Calle");
            modelo.addColumn("Numero");
            modelo.addColumn("Orientacion");
            modelo.addColumn("Colonia");
            modelo.addColumn("Codigo Postal");
            modelo.addColumn("Municipio");
            modelo.addColumn("Estado");
            
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
    
    public int numeroColonia(String colonia){
        int col=0;
            try{
            String coloniaa="'"+colonia+"'";
            System.out.println("salida colonia:"+coloniaa);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_col from colonia where nom_col="+coloniaa+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_col");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
}
