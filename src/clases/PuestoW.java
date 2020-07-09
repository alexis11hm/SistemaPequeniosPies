
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

public class PuestoW {

    public void mostrarInsercion(DefaultTableModel modeloPuesto,String puesto){
        Object[] fila = new Object[1];
            fila[0] = puesto;
            modeloPuesto.addRow(fila);
        
    }
    
    public void mostrarPuesto(DefaultTableModel modeloPuesto,JTable tablePuesto){
        
        try {
            tablePuesto.setModel(modeloPuesto);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_pue from puesto";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloPuesto.addColumn("Puesto");
            
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloPuesto.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarPuesto(String puesto){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into puesto values(null,?)");
            ps.setString(1,puesto);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Puesto Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Puesto No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarPuesto(DefaultTableModel modeloPuesto,JTable tablePuesto){
        PreparedStatement ps = null;
        int aux=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tablePuesto.getSelectedRow();
            String puesto = tablePuesto.getValueAt(Fila, 0).toString();
            puesto="'"+puesto+"'";
            
            ps = conn.prepareStatement("delete from puesto where nom_pue="+puesto+";");
            ps.execute();

            modeloPuesto.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Puesto Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Puesto No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarPuesto(JTable tablePuesto,String puesto){
       PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            int Filaa = tablePuesto.getSelectedRow();
            String pue = tablePuesto.getValueAt(Filaa, 0).toString();
            puesto="'"+puesto+"'";
            pue="'"+pue+"'";
            ps = conn.prepareStatement("update puesto set nom_pue="+puesto+"  where nom_pue="+pue+";");
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Puesto Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            tablePuesto.setValueAt(puesto, Filaa, 0);
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Puesto No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarPuesto(JTable tablePuesto,String puesto){
        String where = "";
        if (!"".equals(puesto)) {
            where = "where nom_pue = '" + puesto + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tablePuesto.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_pue from puesto "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Puesto");
            
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
