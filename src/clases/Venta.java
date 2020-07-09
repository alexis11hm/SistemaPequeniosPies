
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


public class Venta {

    public void iniciarModeloTabla(DefaultTableModel modeloVenta,JTable tableVenta){
        tableVenta.setModel(modeloVenta);
           
            modeloVenta.addColumn("Codigo Barras");
            modeloVenta.addColumn("Nombre");
            modeloVenta.addColumn("Cantidad");
            modeloVenta.addColumn("Precio");
            modeloVenta.addColumn("Total");
    }
    
    public void iniciarModeloTablaPago(DefaultTableModel modeloPago,JTable tablePago){
        tablePago.setModel(modeloPago);
           
            modeloPago.addColumn("Forma de Pago");
            modeloPago.addColumn("Cantidad");
    }
    
    public void agregarProductoTicket(DefaultTableModel modeloVenta,int codbar,String nombre,int cantidad,float precio,float total){
         
        Object[] fila = new Object[5];
            fila[0]= codbar;
            fila[1] = nombre;
            fila[2] = cantidad;
            fila[3] = precio;
            fila[4] = total;
            modeloVenta.addRow(fila);
            new rojerusan.RSNotifyFade("AVISO", "Producto Agregado Exitosamente", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

    }
    
    public void agregarPagoTicket(DefaultTableModel modeloPago,String forma, float cantidad){
         
        Object[] fila = new Object[2];
            fila[0]= forma;
            fila[1] = cantidad;
            modeloPago.addRow(fila);
            new rojerusan.RSNotifyFade("AVISO", "Pago Agregado Exitosamente", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

    }
    
    public float eliminarProducto(DefaultTableModel modeloVenta,JTable tableVenta){
        float total=0;
        try{
            int Fila = tableVenta.getSelectedRow();
            total = Float.parseFloat(tableVenta.getValueAt(Fila, 4).toString());
            modeloVenta.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Producto Eliminado Exitosamente", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Se Ha Podido Eliminar!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
        return total;
    }
    
    public float eliminarPago(DefaultTableModel modeloPago,JTable tablePago){
        float total=0;
        try{
            int Fila = tablePago.getSelectedRow();
            total = Float.parseFloat(tablePago.getValueAt(Fila, 1).toString());
            modeloPago.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Pago Eliminado Exitosamente", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pago No Se Ha Podido Eliminar!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
        return total;
    }
    
    public void concluirCompra(JTable tableVenta, JTable tablePago,int contrato, int sucursal,float total){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;
        String fecha="";
        int folio=0;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            //_____________NUEVO TICKET_________________
            call=conn.prepareCall("{call sp_nuevoticket(?,?,?,?)}");
            call.setInt(1, contrato);
            call.setInt(2, sucursal);
            call.registerOutParameter("fecha", Types.VARCHAR);
            call.registerOutParameter("folio", Types.INTEGER);
            call.execute();
            fecha=call.getString("fecha");
            folio=call.getInt("folio");
            //____________PRODUCTOS__________________
            int filas=tableVenta.getRowCount();
            for(int i=0;i<filas;i++){
                float cantidad=Float.parseFloat(tableVenta.getValueAt(i, 2).toString());
                int codbar=Integer.parseInt(tableVenta.getValueAt(i, 0).toString());
                //____________RENGLON TICKET__________________
                call=conn.prepareCall("{call sp_llenarrenglonticket(?,?,?)}");
                call.setFloat(1,cantidad);
                call.setInt(2,codbar);
                call.setInt(3,folio);
                call.execute();
                //____________BAJA PRODUCTO__________________
                call=conn.prepareCall("{call sp_bajaproducto(?,?,?)}");
                call.setFloat(1,cantidad);
                call.setInt(2,codbar);
                call.setInt(3,sucursal);
                call.execute(); 
                //_____________COMISION____________________
                call=conn.prepareCall("{call sp_registrarcomision()}");
                call.execute();
            }
            //____________TICKET PAGO__________________
            int filasPago=tablePago.getRowCount();
            for(int i=0;i<filasPago;i++){
                String form=tablePago.getValueAt(i, 0).toString();
                int forma=new Venta().numeroForma(form);
                float monto=Float.parseFloat(tablePago.getValueAt(i, 1).toString());
                
                call=conn.prepareCall("{call sp_llenarticketpago(?,?,?)}");
                call.setFloat(1,monto);
                call.setInt(2,forma);
                call.setInt(3,folio);
                call.execute();
                
            }
            //____________CERRAR TICKET__________________
            call=conn.prepareCall("{call sp_actualizarticket(?,?)}");
            call.setFloat(1,total);
            call.setInt(2,folio);
            call.execute();
            call=conn.prepareCall("{call sp_registrardineroticket()}");
            call.execute();
            call.close();
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Venta completada correctamente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ocurrio un error al registar la venta", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
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
