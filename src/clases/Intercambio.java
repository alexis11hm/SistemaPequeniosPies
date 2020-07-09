
package clases;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import rojerusan.RSNotifyFade;


public class Intercambio {

    public void iniciarModeloTabla(DefaultTableModel modeloIntercambio,JTable tableIntercambio){
        tableIntercambio.setModel(modeloIntercambio);
           
            modeloIntercambio.addColumn("Codigo Barras");
            modeloIntercambio.addColumn("Cantidad");
            modeloIntercambio.addColumn("Sucursal Entra:");
            modeloIntercambio.addColumn("Sucursal Sale:");
            modeloIntercambio.addColumn("Empleado");
            modeloIntercambio.addColumn("Fecha");
    }
    
    
    public void agregarIntercambio(DefaultTableModel modeloVenta,int codbar,String sale,String entra,int cantidad,int contrato, Date fecha){
        Object[] fila = new Object[5];
            fila[0]= codbar;
            fila[1] = cantidad;
            fila[2] = entra;
            fila[3] = sale;
            fila[4] = contrato;
            fila[4] = fecha;
            modeloVenta.addRow(fila);
            new rojerusan.RSNotifyFade("AVISO", "Intercambio correcto!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

    }
    
    
    public void concluirIntercambio(int cantidad, int codbar,int sale, int entra, int contrato, Date fecha){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;
        
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_bajaintercambio(?,?,?,?,?,?)}");
            call.setInt(1,cantidad);
            call.setInt(2,codbar);
            call.setInt(3, entra);
            call.setInt(4, sale);
            call.setInt(5, contrato);
            call.setDate(6, fecha);
            call.execute();
            
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Intercambio Completado correctamente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ocurrio un error al hacer el intercambio", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
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
    
    public int numeroContrato(String curp){
        int col=0;
            try{
            String contratoo="'"+curp+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_con from contrato where curp_per="+contratoo+";";
        
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

            String sql = "select precio_pre from precio where codbar_pro="+codbar+" and fecha_pre=(select  max(fecha_pre) from precio where codbar_pro="+codbar+");";
        
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
    
    public boolean existenciaProducto(int codbar,int sucursal,int cantidad){
    java.sql.CallableStatement call = null;
    int bol=0;
    try{
    Conexion objCon = new Conexion();
    Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
    call=conn.prepareCall("{call sp_existenciaproducto(?,?,?,?)}");
    call.setInt(1, codbar);
    call.setInt(2, sucursal);
    call.setInt(3, cantidad);
    call.registerOutParameter("salida", Types.INTEGER);
    call.execute();
    bol=call.getInt("salida");
    }catch(Exception e){
        System.err.println(e);
    }
    if(bol==1){
        return true;
    }else{
        return false;
    }
    }
    
}
