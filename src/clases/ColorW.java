
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import rojerusan.RSNotifyFade;

public class ColorW {

    public void mostrarInsercion(DefaultTableModel modeloColor,String color){
        Object[] fila = new Object[1];
            fila[0] = color;
            modeloColor.addRow(fila);
        
    }
    
    public void mostrarColor(DefaultTableModel modeloColor,JTable tableColor){
        
        try {
            tableColor.setModel(modeloColor);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select col_col from color";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloColor.addColumn("Color");
            
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloColor.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarColor(String color){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into color values(null,?)");
            ps.setString(1,color);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Color Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Color No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarColor(DefaultTableModel modeloColor,JTable tableColor){
        PreparedStatement ps = null;
        int aux=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableColor.getSelectedRow();
            String color = tableColor.getValueAt(Fila, 0).toString();
            color="'"+color+"'";
            
            ps = conn.prepareStatement("delete from color where col_col="+color+";");
            ps.execute();

            modeloColor.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Color Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Color No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarColor(JTable tableColor,String color){
       PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            int Filaa = tableColor.getSelectedRow();
            String col = tableColor.getValueAt(Filaa, 0).toString();
            color="'"+color+"'";
            col="'"+col+"'";
            ps = conn.prepareStatement("update color set col_col="+color+"  where col_col="+col+";");
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Color Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            tableColor.setValueAt(color, Filaa, 0);
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Color No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarColor(JTable tableColor,String color){
        String where = "";
        if (!"".equals(color)) {
            where = "where col_col = '" + color + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableColor.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select col_col from color "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Color");
            
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
