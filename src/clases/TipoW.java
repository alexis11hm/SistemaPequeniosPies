
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

public class TipoW {

    public void mostrarInsercion(DefaultTableModel modeloTipo,String tipo){
        Object[] fila = new Object[1];
            fila[0] = tipo;
            modeloTipo.addRow(fila);
        
    }
    
    public void mostrarTipoW(DefaultTableModel modeloTipo,JTable tableTipo){
        
        try {
            tableTipo.setModel(modeloTipo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_tip from tipo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloTipo.addColumn("Tipo");
            
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloTipo.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarTipo(String tipo){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into tipo values(null,?)");
            ps.setString(1,tipo);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Tipo Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Tipo No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarTipo(DefaultTableModel modeloTipo,JTable tableTipo){
        PreparedStatement ps = null;
        int aux=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableTipo.getSelectedRow();
            String tipo = tableTipo.getValueAt(Fila, 0).toString();
            tipo="'"+tipo+"'";
            
            ps = conn.prepareStatement("delete from tipo where nom_tip="+tipo+";");
            ps.execute();

            modeloTipo.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Tipo Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Tipo No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarTipo(JTable tableTipo,String tipo){
       PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            int Filaa = tableTipo.getSelectedRow();
            String t = tableTipo.getValueAt(Filaa, 0).toString();
            tipo="'"+tipo+"'";
            t="'"+t+"'";
            ps = conn.prepareStatement("update tipo set nom_tip="+tipo+"  where nom_tip="+t+";");
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Tipo Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            tableTipo.setValueAt(tipo, Filaa, 0);
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Tipo No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarTipo(JTable tableTipo,String tipo){
        String where = "";
        if (!"".equals(tipo)) {
            where = "where nom_tip = '" + tipo + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableTipo.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_tip from tipo "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Tipo");
            
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
