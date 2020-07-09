
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


public class Proveedor {
    
    public void mostrarInsercion(DefaultTableModel modeloProveedor,String nombre,String telefono,String calle,int numero,String orientacion,String colonia,String codigo,String municipio,String estado){
        Object[] fila = new Object[9];
            fila[0] = nombre;
            fila[1] = telefono;
            fila[2] = calle;
            fila[3] = numero;
            fila[4] = orientacion;
            fila[5] = colonia;
            fila[6] = codigo; 
            fila[7] = municipio;
            fila[8] = estado; 
            modeloProveedor.addRow(fila);
        
    }
    
    
    
    public void mostrarProveedor(DefaultTableModel modeloProveedor,JTable tableProveedor){
        
        try {
            tableProveedor.setModel(modeloProveedor);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_proveedores";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloProveedor.addColumn("Nombre");
            modeloProveedor.addColumn("Telefono");
            modeloProveedor.addColumn("Calle");
            modeloProveedor.addColumn("Numero");
            modeloProveedor.addColumn("Orientacion");
            modeloProveedor.addColumn("Colonia");
            modeloProveedor.addColumn("Codigo Postal");
            modeloProveedor.addColumn("Municipio");
            modeloProveedor.addColumn("Estado");

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloProveedor.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarProveedor(String nombre,String telefono,String calle, int numero, String orientacion,int colonia){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into proveedor values(null,?,?,?,?,?,?)");
            ps.setString(1,nombre);
            ps.setString(2, calle);
            ps.setInt(3, numero);
            ps.setString(4, orientacion);
            ps.setString(5, telefono);
            ps.setInt(6, colonia);

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Proveedor Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("Error", "Erorr al registrar Proveedor", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
    }
    
    public void eliminarProveedor(DefaultTableModel modeloProveedor,JTable tableProveedor){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableProveedor.getSelectedRow();
            String nom = tableProveedor.getValueAt(Fila, 0).toString();
            String tel= tableProveedor.getValueAt(Fila, 1).toString();
            String nombre="'"+nom+"'"; 
            String telefono="'"+tel+"'"; 
            System.err.println(nombre);
            System.err.println(telefono);
            
           
            
            ps = conn.prepareStatement("delete from proveedor where nom_prov="+nombre+" and numtel_prov="+telefono+";");
            ps.execute();
            
            ps.close();
            conn.close();
            modeloProveedor.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Proveedor Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Proveedor No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarProveedor(JTable tableProveedor,String nombre,String telefono,String calle,int numero,String orientacion,String coloniaa,int colonia, String codigo, String municipio,String estado ){
        int Fila = tableProveedor.getSelectedRow();

        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int Filaa = tableProveedor.getSelectedRow();
            String nom = tableProveedor.getValueAt(Filaa, 0).toString();
            String tel= tableProveedor.getValueAt(Filaa, 1).toString();
            String nombree="'"+nom+"'"; 
            String telefonoo="'"+tel+"'"; 
            String name="'"+nombre+"'";
            String street="'"+calle+"'";
            String orie="'"+orientacion+"'";
            String number="'"+telefono+"'";
            ps = conn.prepareStatement("update proveedor set nom_prov="+name+" ,calle_prov="+street+" ,num_prov="+numero+" , orientacion_prov="+orie+" , numtel_prov="+number+" , cve_col="+colonia+" where nom_prov="+nombree+" and numtel_prov="+telefonoo);

            

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Proveedor Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            tableProveedor.setValueAt(nombre, Filaa, 0);
            tableProveedor.setValueAt(telefono, Filaa, 1);
            tableProveedor.setValueAt(calle, Filaa, 2);
            tableProveedor.setValueAt(numero, Filaa, 3);
            tableProveedor.setValueAt(orientacion, Filaa, 4);
            tableProveedor.setValueAt(coloniaa, Filaa, 5);
            tableProveedor.setValueAt(codigo, Filaa, 6);
            tableProveedor.setValueAt(municipio, Filaa, 7);
            tableProveedor.setValueAt(estado, Filaa, 8);

            
            conn.close();
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Sucursal No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarProveedor(JTable tableSucursal,String nombre){
        String where = "";
        if (!"".equals(nombre)) {
            where = "where nom_prov = '" + nombre + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableSucursal.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_proveedores "+where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Nombre");
            modelo.addColumn("Telefono");
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
