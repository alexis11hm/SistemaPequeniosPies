
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


public class Cambio {

    public void iniciarModeloTabla(DefaultTableModel modeloCambio,JTable tableCambio){
        tableCambio.setModel(modeloCambio);
           
            modeloCambio.addColumn("Folio");
            modeloCambio.addColumn("Codbar Entra");
            modeloCambio.addColumn("Codbar Sale");
            modeloCambio.addColumn("Descripcion");
            modeloCambio.addColumn("Fecha");
            modeloCambio.addColumn("Empleado");
            modeloCambio.addColumn("Sucursal");
            modeloCambio.addColumn("Numero");
    }
    public void iniciarModeloTablaPagoCambio(DefaultTableModel modeloCambioPago,JTable tableCambioPago){
        tableCambioPago.setModel(modeloCambioPago);
           
            modeloCambioPago.addColumn("Forma");
            modeloCambioPago.addColumn("Monto");
    }
    
    
    public void agregarCambio(DefaultTableModel modeloCambio,int folio,int codbarentra,int codbarsale,String descripcion,Date fecha,int empleado,String sucursal,float numero){
        Object[] fila = new Object[8];
            fila[0]= folio;
            fila[1] = codbarentra;
            fila[2] = codbarsale;
            fila[3] = descripcion;
            fila[4] = fecha;
            fila[5] = empleado;
            fila[6] = sucursal;
            fila[7] = numero;
            modeloCambio.addRow(fila);
            new rojerusan.RSNotifyFade("AVISO", "Cambio Agregado!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

    }
    
     public void agregarPago(DefaultTableModel modeloCambioPago,int forma,float monto){
        Object[] fila = new Object[2];
            fila[0]= forma;
            fila[1] = monto;
            modeloCambioPago.addRow(fila);
            new rojerusan.RSNotifyFade("AVISO", "Monto Agregado!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

    }
    
    
    public void concluirCambio(JTable tableCambio,JTable tableCambioPago){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;
        int clave=0;
        Cambio c = new Cambio();
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int entra=Integer.parseInt(tableCambio.getValueAt(0, 1).toString());
            float num=Float.parseFloat(tableCambio.getValueAt(0,7).toString());
            int numero=c.numeroZapato(num);
            int sale=Integer.parseInt(tableCambio.getValueAt(0,2).toString());
            String descripcion=tableCambio.getValueAt(0,3).toString();
            String fecha=tableCambio.getValueAt(0,4).toString();
            int contrato=Integer.parseInt(tableCambio.getValueAt(0,5).toString());
            String sucu=tableCambio.getValueAt(0,6).toString();
            int sucursal=c.numeroSucursal(sucu);
            
            
            call=conn.prepareCall("{call sp_registrarcambiozapato(?,?,?,?,?,?,?,?,?)}");
            call.setInt(1,entra);
            call.setInt(2,numero);
            call.setInt(3,sale);
            call.setInt(4,numero);
            call.setString(5,descripcion);
            call.setString(6,fecha);
            call.setInt(7,contrato);
            call.setInt(8,sucursal);
            call.registerOutParameter("cvecambio", Types.INTEGER);
            call.execute();
            clave=call.getInt("cvecambio");
            
            int filas=tableCambioPago.getRowCount();
            System.err.println("filas:"+filas);
            for(int i=0;i<filas;i++){
                int forma=Integer.parseInt(tableCambioPago.getValueAt(i, 0).toString());
                float monto=Float.parseFloat(tableCambioPago.getValueAt(i, 1).toString());
                call=conn.prepareCall("{call sp_registrarpagocambio(?,?,?)}");
                call.setInt(1,forma);
                call.setFloat(2,monto);
                call.setInt(3,clave);
                call.execute();
            }
            call=conn.prepareCall("{call sp_terminar()}");
            call.execute();
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Cambio Completado correctamente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ocurrio un error al hacer el intercambio", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public boolean verificarZapato(int folio){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null; 
        int var=0;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_verificarmes(?,?)}");
            call.setInt(1,folio);
            call.registerOutParameter("salida", Types.INTEGER);
            call.execute();
            var=call.getInt("salida");
            call.execute();  
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Cambio Disponible!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "El cambio no esta disponible", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
        if(var==1){
                return true;
            }else{
                return false;
            }
    }
    
    public boolean verificarExiste(int codbarsale,int numero,int sucursal){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null; 
        int var=0;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_existezapatoinventario(?,?,?,?)}");
            call.setInt(1,codbarsale);
            call.setInt(2,numero);
            call.setInt(3,sucursal);
            call.registerOutParameter("existe", Types.INTEGER);
            call.execute();
            var=call.getInt("existe");
            call.execute();  
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Zapato existente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "No hay zapatos1", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
        if(var==1){
                return true;
            }else{
                return false;
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
    
    public int numeroZapato(float numero){
        int aux=0;
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_num from numerozapato where num_num="+numero+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            aux=rs.getInt("cve_num");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return aux;
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
    
   
    
}
