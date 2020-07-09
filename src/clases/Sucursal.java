
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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


public class Sucursal {
    
    
    public void mostrarInsercion(DefaultTableModel modelo,String calle, int numero, String orientacion,String colonia,String codigo, String municipio,String estado){
        Object[] fila = new Object[7];
            fila[0] = calle;
            fila[1] = numero;
            fila[2] = orientacion;
            fila[3] = colonia;
            fila[4] = codigo;
            fila[5] = municipio;
            fila[6] = estado;     
            modelo.addRow(fila);
        
    }
    
    
    
    public void mostrarSucursal(DefaultTableModel modelo,JTable tableSucursal){
        
        try {
            tableSucursal.setModel(modelo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_sucursales";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
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
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarSucursal(String calle, int numero, String orientacion,int colonia){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into sucursal values(null,?,?,?,?)");
            ps.setString(1, calle);
            ps.setInt(2, numero);
            ps.setString(3, orientacion);
            ps.setInt(4, colonia);

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Sucursal Registrada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Guardar Producto");
            System.out.println(ex);
        }
    }
    
    public void eliminarSucursal(DefaultTableModel modelo,JTable tableSucursal){
        PreparedStatement ps = null;
        
        int cve=0;
        try {
            
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int Fila = tableSucursal.getSelectedRow();
            String cal = tableSucursal.getValueAt(Fila, 0).toString();
            int numero= Integer.parseInt(tableSucursal.getValueAt(Fila, 1).toString());
            String calle="'"+cal+"'";   
            System.err.println(calle);
            System.err.println(numero);
            
           
            ResultSet rs = null;
            String sql = "select cve_suc from sucursal where calle_suc="+calle+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            cve=rs.getInt("cve_suc");
            }
            rs.close();
            ps.close();

            ps = conn.prepareStatement("delete from sucursalservicio where cve_suc="+cve+"; ");
            ps.execute();
            ps = conn.prepareStatement("delete from sucursal where calle_suc="+calle+";");
            ps.execute();
            ps = conn.prepareStatement("commit;");
            ps.execute();
            conn.close();
            modelo.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Sucursal Eliminada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            

        } catch (Exception ex) {
            try {
                new rojerusan.RSNotifyFade("ERROR", "Sucursal No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
                System.out.println(ex.toString());
                Conexion objCon = new Conexion();
                Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
                ps = conn.prepareStatement("rollback;");
                ps.execute();
                conn.close();
            } catch (SQLException ex1) {
                Logger.getLogger(Sucursal.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
        }
    }
    
    public void modificarSucursal(JTable tableSucursal,String calle,int numero,String orientacion,String coloniaa,int colonia, String codigo, String municipio,String estado ){
        int Fila = tableSucursal.getSelectedRow();

        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tableSucursal.getSelectedRow();
            String callee = tableSucursal.getValueAt(numFila, 0).toString();
            int numeroSucursal= Integer.parseInt(tableSucursal.getValueAt(numFila, 1).toString());
            String calleSucursal="'"+callee+"'";   
            String cal="'"+calle+"'";
            String ori="'"+orientacion+"'";
            
            ps = conn.prepareStatement("update sucursal set calle_suc="+cal+" ,num_suc="+numero+" ,orientacion_suc="+ori+" , cve_col="+colonia+" where calle_suc="+calleSucursal+" and num_suc="+numeroSucursal);

            

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Sucursal Modificada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            tableSucursal.setValueAt(calle, Fila, 0);
            tableSucursal.setValueAt(numero, Fila, 1);
            tableSucursal.setValueAt(orientacion, Fila, 2);
            tableSucursal.setValueAt(coloniaa, Fila, 3);
            tableSucursal.setValueAt(codigo, Fila, 4);
            tableSucursal.setValueAt(municipio, Fila, 5);
            tableSucursal.setValueAt(estado, Fila, 6);
            conn.close();
            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Sucursal No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarSucursal(JTable tableSucursal,String calle){
        String where = "";
        if (!"".equals(calle)) {
            where = "where calle_suc = '" + calle + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableSucursal.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_sucursales "+where+";";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

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
