
package clasesDeshabilitadas;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
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


public class Resurtir {
    
    
    public void mostrarInsercion(DefaultTableModel modeloResurtir,Date fecha,String factura,String sucursal,String proveedor){
 
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String fe="'"+fecha+"'";
            String fac="'"+factura+"'";
            int prov=numeroProveedor(proveedor);
            int clave=0;
            try{

            String sql = "select cve_res from resurtir where fecha_res="+fe+" and factura_res="+fac+" and cve_prov="+prov+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            clave=rs.getInt("cve_res");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
            Object[] fila = new Object[5];
            fila[0] = clave;
            fila[1] = proveedor;
            fila[2] = sucursal;
            fila[3] = factura;
            fila[4] = fecha;
            modeloResurtir.addRow(fila);
     
    }
    
    
    
    public void mostrarResurtir(DefaultTableModel modeloResurtir,JTable tableResurtir){
        
        try {
            tableResurtir.setModel(modeloResurtir);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_res,nom_prov, calle_suc,factura_res,fecha_res from resurtir r join proveedor p on r.cve_prov=p.cve_prov join sucursal s on s.cve_suc=r.cve_suc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
             modeloResurtir.addColumn("Clave");
            modeloResurtir.addColumn("Proveedor");
            modeloResurtir.addColumn("Sucursal");
            modeloResurtir.addColumn("Factura");
            modeloResurtir.addColumn("Fecha");
           

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloResurtir.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarResurtido(Date fecha,String factura,int proveedor,int sucursal){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            String fechaa="'"+fecha+"'";
            String facturaa="'"+factura+"'";
            
            ps = conn.prepareStatement("insert into resurtir values(null,?,?,?,?)");
            ps.setDate(1, fecha);
            ps.setString(2, factura);
            ps.setInt(3, proveedor);
            ps.setInt(4, sucursal);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Resurtido Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Resurtido No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
    }
    
    public void eliminarResurtido(DefaultTableModel modeloResurtir,JTable tableResurtir){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableResurtir.getSelectedRow();
            String prov = tableResurtir.getValueAt(Fila, 1).toString();
            String factura = tableResurtir.getValueAt(Fila, 3).toString();    
            System.err.println(prov);
            System.err.println(factura);
            
            Resurtir res = new Resurtir();
            int proveedor=res.numeroProveedor(prov);
            
            ps = conn.prepareStatement("delete from resurtir where cve_prov="+proveedor+" and factura_res="+factura+";");
            ps.execute();
            ps.close();

            modeloResurtir.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Resurtido Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Resurtido No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarResurtido(JTable tableResurtir,String prov, int proveedor,String sucu, int sucursal,Date fecha,String factura){
        int Fila = tableResurtir.getSelectedRow();

        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tableResurtir.getSelectedRow();
            String prove = tableResurtir.getValueAt(numFila, 0).toString();
            String factu = tableResurtir.getValueAt(numFila, 2).toString();
            
            String facturaa="'"+factu+"'";
            int proveedorr=new Resurtir().numeroProveedor(prove);
            
            factura="'"+factura+"'";
            String date="'"+fecha+"'";
            System.err.println(facturaa);
            System.err.println(proveedorr);
            
            String sql="update resurtir set fecha_res="+date+" ,factura_res="+factura+" ,cve_prov="+proveedor+" , cve_suc="+sucursal+" where cve_prov="+proveedorr+" and factura_res="+facturaa;
            System.err.println(sql);
            ps = conn.prepareStatement(sql);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Resurtido Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            tableResurtir.setValueAt(prov, Fila, 0);
            tableResurtir.setValueAt(sucu, Fila, 1);
            tableResurtir.setValueAt(factura, Fila, 2);
            tableResurtir.setValueAt(fecha, Fila, 3);
            conn.close();
            } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Resurtido No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarResurtido(JTable tableResurtir,String fecha){
        String where = "";
        if (!"".equals(fecha)) {
            where = "where fecha_res = '" + fecha +"';";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableResurtir.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_res,nom_prov, calle_suc,factura_res,fecha_res from resurtir r join proveedor p on r.cve_prov=p.cve_prov join sucursal s on s.cve_suc=r.cve_suc "+where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Clave");
            modelo.addColumn("Proveedor");
            modelo.addColumn("Sucursal");
            modelo.addColumn("Factura");
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
    
    public int numeroSucursal(String sucursal){
        int col=0;
            try{
            String sucu="'"+sucursal+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_suc from sucursal where calle_suc="+sucu+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_suc");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    public int numeroProveedor(String proveedor){
        int col=0;
            try{
            String prov="'"+proveedor+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_prov from proveedor where nom_prov="+prov+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_prov");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    
    
    
}
