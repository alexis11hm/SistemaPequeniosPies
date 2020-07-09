
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

public class ServicioS {

    public void mostrarInsercion(DefaultTableModel modeloServicioS,String servicio){
        Object[] fila = new Object[1];
            fila[0] = servicio;
            modeloServicioS.addRow(fila);
        
    }
    
    public void mostrarServicioS(DefaultTableModel modeloServicioS,JTable tableServicioS){
        
        try {
            tableServicioS.setModel(modeloServicioS);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_ser from servicio";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloServicioS.addColumn("Servicio");
            
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloServicioS.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarServicio(String servicio){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into servicio values(null,?)");
            ps.setString(1, servicio);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Servicio Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Servicio No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarServicio(DefaultTableModel modeloServicioS,JTable tableServicioS){
        PreparedStatement ps = null;
        int aux=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableServicioS.getSelectedRow();
            String servicio = tableServicioS.getValueAt(Fila, 0).toString();
            servicio="'"+servicio+"'";
            
            ps = conn.prepareStatement("delete from servicio where nom_ser="+servicio+";");
            ps.execute();

            modeloServicioS.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Servicio Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Servicio No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarServicio(JTable tableServicioS,String servicio){
       PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            int Filaa = tableServicioS.getSelectedRow();
            String serv = tableServicioS.getValueAt(Filaa, 0).toString();
            servicio="'"+servicio+"'";
            serv="'"+serv+"'";
            ps = conn.prepareStatement("update servicio set nom_ser="+servicio+"  where nom_ser="+serv+";");
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Servicio Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            tableServicioS.setValueAt(servicio, Filaa, 0);
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Servicio No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarServicio(JTable tableServicioS,String servicio){
        String where = "";
        if (!"".equals(servicio)) {
            where = "where nom_ser = '" + servicio + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableServicioS.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_ser from servicio "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Servicio");
            
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
