
package clasesDeshabilitadas;

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


public class Venta {
    
    
    public void mostrarProducto(DefaultTableModel modeloVenta,JTable tableVenta,int folio,int codbar,String nombre,int cantidad,float precio,float total){
        
        
        Object[] fila = new Object[6];
            fila[0] =folio;
            fila[1] =codbar;
            fila[2] =nombre;
            fila[3] =cantidad;
            fila[4] = precio;
            fila[5] = total;
            modeloVenta.addRow(fila);
        
    }
    
    
    
    public void registrarTicket(int sucursal,int contrato){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into ticket values(null,now(),null,?,?)");
            ps.setInt(1,contrato);
            ps.setInt(2,sucursal);
            ps.execute();
            
            new rojerusan.RSNotifyFade("SI", "Ticket Listo", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.INFORMATION).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("NO", "Ticket No Listo", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void registrarProducto(int cantidad,int codbar,int folio){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into renglonticket values(null,?,?,?)");
            ps.setInt(1,cantidad);
            ps.setInt(2,codbar);
            ps.setInt(3,folio);
            ps.execute();
            
            new rojerusan.RSNotifyFade("AVISO", "Producto Agregado", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Agregado", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarProducto(DefaultTableModel modeloVenta,JTable tableVenta){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableVenta.getSelectedRow();
            System.out.println("Numero Fila: "+Fila);
            int folio = Integer.parseInt(tableVenta.getValueAt(Fila, 0).toString());
            int codbar = Integer.parseInt(tableVenta.getValueAt(Fila, 1).toString());
            int cantidad = Integer.parseInt(tableVenta.getValueAt(Fila, 3).toString());
            
            ps = conn.prepareStatement("delete from renglonticket where folio_tic="+folio+" and codbar_pro="+codbar+" and cant_rentic="+cantidad+";");
            ps.execute();
            modeloVenta.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Producto Eliminado Exitosamente", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Se Ha Podido Eliminar!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    

    
    public int obtenerFolio(){
        int col=0;
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = " select folio_tic,fecha_tic from ticket where fecha_tic=(select max(fecha_tic) from ticket);";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("folio_tic");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public int numeroSucursal(String calle){
        int col=0;
            try{
            String callee="'"+calle+"'";
            System.out.println("salida calle:"+calle);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_suc from sucursal where calle_suc="+callee+";";
        
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
    
    public int numeroContrato(String curp,Date fi){
        int col=0;
            try{
            String contratoo="'"+curp+"'";
            String fecha="'"+fi+"'";
            System.out.println("salida contrato y fecha:"+contratoo+","+fecha);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_con from contrato where curp_per="+contratoo+" and finicio_con="+fecha+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_con");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public String nombreProducto(int codbar){
        String col="";
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_pro from producto where codbar_pro="+codbar+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getString("nom_pro");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public float precioProducto(int codbar){
        float col=0;
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select precio_pre from precio where fecha_pre=(select  max(fecha_pre) from precio where codbar_pro="+codbar+");";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getFloat("precio_pre");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public String fechaTicket(int folio){
        String fecha="";
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select fecha_tic from ticket where folio_tic="+folio+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            fecha=rs.getString("fecha_tic");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return fecha;
    }
}
