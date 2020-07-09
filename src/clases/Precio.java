
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


public class Precio {
    
    
    public void mostrarInsercion(DefaultTableModel modeloPrecio,int codbar,float precio,Date fecha){
      Object[] fila = new Object[3];
            fila[0] =codbar;
            fila[1] =precio;
            fila[2] =fecha;
            modeloPrecio.addRow(fila);
        
    }
    
    
    
    public void mostrarPrecio(DefaultTableModel modeloPrecio,JTable tablePrecio){
        
        try {
            tablePrecio.setModel(modeloPrecio);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select * from v_precios";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloPrecio.addColumn("Codigo de Barras");
            modeloPrecio.addColumn("Precio de Venta");
            modeloPrecio.addColumn("Fecha");
            
            
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloPrecio.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
          
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarPrecio(int codbar, float precio,Date fecha){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into precio values(null,?,?,?)");
            ps.setFloat(1,precio);
            ps.setDate(2,fecha);
            ps.setInt(3,codbar);
            
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Precio Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("AVISO", "Precio No Se Ha Podido Registrar", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarPrecio(DefaultTableModel modeloPrecio,JTable tablePrecio){
        PreparedStatement ps = null;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tablePrecio.getSelectedRow();
            int codbar = Integer.parseInt(tablePrecio.getValueAt(Fila, 0).toString());
            float precio = Float.parseFloat(tablePrecio.getValueAt(Fila, 1).toString());
            String fecha =tablePrecio.getValueAt(Fila, 2).toString();
            fecha="'"+fecha+"'";
            System.err.println(codbar);
            System.err.println(precio);
            System.err.println(fecha);
             
            ps = conn.prepareStatement("delete from precio where codbar_pro="+codbar+" and fecha_pre="+fecha+" and precio_pre="+precio+";");
            ps.execute();
            modeloPrecio.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Precio Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Precio No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarPrecio(JTable tablePrecio,int codbar,float precio,Date fecha){
        int Fila = tablePrecio.getSelectedRow();
        ResultSet rs=null;
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tablePrecio.getSelectedRow();
            int cb = Integer.parseInt(tablePrecio.getValueAt(Fila, 0).toString());
            float pre = Float.parseFloat(tablePrecio.getValueAt(Fila, 1).toString());
            String fe = tablePrecio.getValueAt(Fila, 2).toString();
            fe="'"+fe+"'";
            String fechaa="'"+fecha+"'";
            
            ps = conn.prepareStatement("update precio set codbar_pro="+codbar+", precio_pre="+precio+" ,fecha_pre="+fechaa+" where codbar_pro="+cb+" and precio_pre="+pre+" and fecha_pre="+fe+";");
            ps.execute();
            ps.close();
            
            new rojerusan.RSNotifyFade("AVISO", "Precio Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();
        }catch(Exception e){
            new rojerusan.RSNotifyFade("ERROR", "Precio No Se Ha Podido Modificar", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.err.println(e);
        }
      
            tablePrecio.setValueAt(codbar, Fila, 0);
            tablePrecio.setValueAt(precio, Fila, 1);
            tablePrecio.setValueAt(fecha, Fila, 2);
            
    }
    
    public void buscarPrecio(JTable tableMinmax,String fecha){
        String where = "";
        if (!"".equals(fecha)) {
            where = "where fecha_pre = '" + fecha +"';";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableMinmax.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql="select * from v_precios "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Codigo Barras");
            modelo.addColumn("Precio");
            modelo.addColumn("Fecha");
            
            
            
            
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
 
}
