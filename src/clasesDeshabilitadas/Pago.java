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


public class Pago {
    
    
    public void mostrarPago(DefaultTableModel modeloPago,JTable tablePago,int folio,String forma,float monto){
        Object[] fila = new Object[6];
            fila[0] =folio;
            fila[1] =forma;
            fila[2] =monto;
            modeloPago.addRow(fila);
        
    }
    
    public void registrarForma(float monto,int forma,int folio){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into ticketpago values(null,?,?,?)");
            ps.setFloat(1,monto);
            ps.setInt(2,forma);
            ps.setInt(3,folio);
            ps.execute();
            
            new rojerusan.RSNotifyFade("AVISO", "Pago Agregado", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pago No Agregado", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarPago(DefaultTableModel modeloPago,JTable tablePago){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tablePago.getSelectedRow();
            System.out.println("Numero de Fila: "+Fila);
            int folio = Integer.parseInt(tablePago.getValueAt(Fila, 0).toString());
            String fo = tablePago.getValueAt(Fila, 1).toString();
            float monto = Float.parseFloat(tablePago.getValueAt(Fila, 2).toString());
            int forma=new Pago().numeroForma(fo);
            
            ps = conn.prepareStatement("delete from ticketpago where monto_ticpag="+monto+" and cve_forpag="+forma+" and folio_tic="+folio+";");
            ps.execute();
            modeloPago.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Pago Eliminado Exitosamente", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pago No Se Ha Podido Eliminar!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void llenarPago(float total,int folio){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("update ticket set total_tic="+total+" where folio_tic="+folio+";");
            
            ps.execute();
            
            new rojerusan.RSNotifyFade("AVISO", "Ticket Registrado", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.INFORMATION).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ticket No Registrado", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
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
    
    public int numeroForma(String forma){
        int col=0;
            try{
            forma="'"+forma+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_forpag from formapago where forma_forpag="+forma+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_forpag");
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
}
