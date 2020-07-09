
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import rojerusan.RSNotifyFade;


public class SucursalServicio {
    
    
    public void mostrarInsercion(DefaultTableModel modeloSucursalServicio,String calle,String servicio, Date fecha, float monto){
        Object[] fila = new Object[4];
            fila[0] = calle;
            fila[1] = servicio;
            fila[2] = fecha;
            fila[3] = monto;
                
            modeloSucursalServicio.addRow(fila);
        
    }
    
    public void mostrarSucursalServicio(DefaultTableModel modeloSucursalServicio,JTable tableServicioSucursal){
        
        try {
            tableServicioSucursal.setModel(modeloSucursalServicio);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select calle_suc, nom_ser, fechasuc_sucser, precio_sucser from sucursal s join sucursalservicio ss on s.cve_suc=ss.cve_suc join servicio se on ss.cve_ser=se.cve_ser";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloSucursalServicio.addColumn("Sucursal");
            modeloSucursalServicio.addColumn("Servicio");
            modeloSucursalServicio.addColumn("Fecha");
            modeloSucursalServicio.addColumn("Monto");
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloSucursalServicio.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarSucursalServicio(int calle, int servicio, float monto,Date fecha){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into sucursalservicio values(null,?,?,?,?)");
            ps.setDate(1, fecha);
            ps.setFloat(2, monto);
            ps.setInt(3, calle);
            ps.setInt(4, servicio);

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Pago Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pago No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
    }
    
    public void eliminarSucursalServicio(DefaultTableModel modeloSucursalServicio,JTable tableServicioSucursal){
        PreparedStatement ps = null;
        int sucu=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableServicioSucursal.getSelectedRow();
            String sucursal = tableServicioSucursal.getValueAt(Fila, 0).toString();
            String fecha= tableServicioSucursal.getValueAt(Fila, 2).toString();
            float monto= Float.parseFloat(tableServicioSucursal.getValueAt(Fila, 3).toString());
           
            String sucursall="'"+sucursal+"'";   
            String fechaa="'"+fecha+"'";  
            
            try{
            ResultSet rs = null;
            String sql = "select cve_suc from sucursal where calle_suc="+sucursall+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            sucu=rs.getInt("cve_suc");
            }
            rs.close();
            ps.close();
            }catch(SQLException e){
                System.out.println(e);
            }
            
            ps = conn.prepareStatement("delete from sucursalservicio where cve_suc="+sucu+" and fechasuc_sucser="+fechaa+" and precio_sucser="+monto+";");
            ps.execute();

            modeloSucursalServicio.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Sucursal Eliminada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Sucursal No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarSucursalServicio(JTable tableSucursalServicio,String sucursal,String servicio,Date fecha,Float monto){
        int sucu=0;
        int sucu2=0;
        int serv=0;
        String fechai="'"+fecha+"'";
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            //____________CUALES SE VAN A CAMBIAR
            int Filaa = tableSucursalServicio.getSelectedRow();
            String sucursale = tableSucursalServicio.getValueAt(Filaa, 0).toString();
            String fechaa= tableSucursalServicio.getValueAt(Filaa, 2).toString();
            float montoo= Float.parseFloat(tableSucursalServicio.getValueAt(Filaa, 3).toString());
           
            String sucursall="'"+sucursale+"'";   
            String fechaaa="'"+fechaa+"'";  
            String serviciooo="'"+servicio+"'";
            
            try{
            ResultSet rs = null;
            String sql = "select cve_suc from sucursal where calle_suc="+sucursall+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            sucu=rs.getInt("cve_suc");
            }
            rs.close();
            ps.close();
            }catch(SQLException e){
                System.out.println(e);
            }
            
            //_________________POR CUALES SE CAMBIARAN________________________
            String sucursalNueva="'"+sucursal+"'";
            try{
            ResultSet rs = null;
            String sql = "select cve_suc from sucursal where calle_suc="+sucursalNueva+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            sucu2=rs.getInt("cve_suc");
            }
            rs.close();
            ps.close();
            }catch(SQLException e){
                System.out.println(e);
            }
            
            String servicioNuevo="'"+servicio+"'";
            try{
            ResultSet rs = null;
            String sql = "select cve_ser from servicio where nom_ser="+servicioNuevo+";";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
            serv=rs.getInt("cve_ser");
            }
            rs.close();
            ps.close();
            }catch(SQLException e){
                System.out.println(e);
            }
            
            ps = conn.prepareStatement("update sucursalservicio set fechasuc_sucser="+fechai+" ,precio_sucser="+monto+" ,cve_suc="+sucu2+" , cve_ser="+serv+" where cve_suc="+sucu+" and fechasuc_sucser="+fechaaa+" and precio_sucser="+montoo+";");
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Pago Servicio Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            tableSucursalServicio.setValueAt(sucursal, Filaa, 0);
            tableSucursalServicio.setValueAt(servicio, Filaa, 1);
            tableSucursalServicio.setValueAt(fecha, Filaa, 2);
            tableSucursalServicio.setValueAt(monto, Filaa, 3);
           

            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Sucursal No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarSucursalServicio(JTable tableServicioSucursal,String fecha){
        String where = "";
        if (!"".equals(fecha)) {
            where = "where fechasuc_sucser = '" + fecha + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableServicioSucursal.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select calle_suc, nom_ser, fechasuc_sucser, precio_sucser from sucursal s join sucursalservicio ss on s.cve_suc=ss.cve_suc join servicio se on ss.cve_ser=se.cve_ser "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Sucursal");
            modelo.addColumn("Servicio");
            modelo.addColumn("Fecha");
            modelo.addColumn("Monto");
            
            
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
    
    public int numeroCalle(String calle){
        int cal=0;
            try{
            String callee="'"+calle+"'";
            System.out.println("salida colonia:"+callee);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_suc from sucursal where calle_suc="+callee+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            cal=rs.getInt("cve_suc");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return cal;
    }
    
    public int numeroServicio(String servicio){
        int ser=0;
            try{
            String servicioo="'"+servicio+"'";
            System.out.println("salida servicio:"+servicioo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_ser from servicio where nom_ser="+servicioo+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            ser=rs.getInt("cve_ser");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return ser;
    }
    
}
