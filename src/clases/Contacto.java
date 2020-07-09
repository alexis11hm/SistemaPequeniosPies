
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


public class Contacto {
    
    
    public void mostrarInsercion(DefaultTableModel modeloContacto,String curp,String medio, String descripcion){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
        String curpp="'"+curp+"'";
        String ap="";
        String am="";
        String nombre="";
        try {

            String sql = "select nom_per,ap_per,am_per,curp_per from persona where curp_per="+curpp;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombre=rs.getString("nom_per");
                ap=rs.getString("ap_per");
                am=rs.getString("am_per");
            }
            rs.close();
            con.close();
        }catch(Exception e){
            System.err.println("Ocurrio un error al mostrar insercion contacto");
        }
        
        Object[] fila = new Object[6];
            fila[0] = nombre;
            fila[1] = ap;
            fila[2] = am;
            fila[3] = curp;
            fila[4] = medio;
            fila[5] = descripcion;
            modeloContacto.addRow(fila);
        
    }
    
    
    
    public void mostrarContacto(DefaultTableModel modeloContacto,JTable tableContacto){
        
        try {
            tableContacto.setModel(modeloContacto);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_contactos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloContacto.addColumn("Nombre");
            modeloContacto.addColumn("Apellido Paterno");
            modeloContacto.addColumn("Apellido Materno");
            modeloContacto.addColumn("CURP");
            modeloContacto.addColumn("Medio");
            modeloContacto.addColumn("Descripcion");
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloContacto.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarContacto(String curp,String descripcion,String medio){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into contacto values(null,?,?,?)");
            ps.setString(1,descripcion);
            ps.setString(2,medio);
            ps.setString(3,curp);

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Contacto Registrada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Registrar Contacto");
            System.out.println(ex);
        }
    }
    
    public void eliminarContacto(DefaultTableModel modeloContacto,JTable tableContacto){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableContacto.getSelectedRow();
            String curp = tableContacto.getValueAt(Fila, 3).toString();
            String descripcion = tableContacto.getValueAt(Fila, 5).toString();
            
            String curpp="'"+curp+"'";  
            String des="'"+descripcion+"'";
            System.err.println(curpp);
            System.err.println(des);

            ps=conn.prepareStatement("delete from sucursalservicio where cve_suc="+cve+";");
            ps.execute();
            ps.close();
            
          

            ps = conn.prepareStatement("delete from contacto where curp_per="+curpp+" and descripcion_cont="+des+";");
            ps.execute();
            modeloContacto.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Contacto Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Contacto No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarContacto(JTable tableContacto,String curp,String descripcion,String medio){
        int Fila = tableContacto.getSelectedRow();
        ResultSet rs=null;
        PreparedStatement ps = null;
        String nombre="",ap="",am="";
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tableContacto.getSelectedRow();
            String curpp = tableContacto.getValueAt(Fila, 3).toString();
            String descripcionn = tableContacto.getValueAt(Fila, 5).toString();
            
            String cuurp="'"+curpp+"'";
            String des="'"+descripcionn+"'";
            System.err.println(cuurp);
            System.err.println(des);
            
            String cu="'"+curp+"'";
            String med="'"+medio+"'";
            String de="'"+descripcion+"'";
            
            
            ps = conn.prepareStatement("update contacto set curp_per="+cu+", descripcion_cont="+de+" ,medio_cont="+med+" where curp_per="+cuurp+" and descripcion_cont="+des+";");

            ps.execute();
            ps.close();

            new rojerusan.RSNotifyFade("AVISO", "Contacto modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            try {

            String sql = "select nom_per,ap_per,am_per,curp_per from persona where curp_per="+cu;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombre=rs.getString("nom_per");
                ap=rs.getString("ap_per");
                am=rs.getString("am_per");
            }
            rs.close();
            conn.close();
        }catch(Exception e){
            System.err.println("Ocurrio un error al mostrar insercion contacto");
        }
            
            tableContacto.setValueAt(nombre, Fila, 0);
            tableContacto.setValueAt(ap, Fila, 1);
            tableContacto.setValueAt(am, Fila, 2);
            tableContacto.setValueAt(curp, Fila, 3);
            tableContacto.setValueAt(medio, Fila, 4);
            tableContacto.setValueAt(descripcion, Fila, 5);
            

            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Contacto No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarContacto(JTable tableContacto,String curp){
        String where = "";
        if (!"".equals(curp)) {
            where = "where curp_per = '" + curp + "'";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableContacto.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql = "select * from v_contactos "+where+";";
           
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido Paterno");
            modelo.addColumn("Apellido Materno");
            modelo.addColumn("CURP");
            modelo.addColumn("Medio");
            modelo.addColumn("Descripcion");
            
            
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
