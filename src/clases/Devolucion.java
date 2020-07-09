
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


public class Devolucion {

    public void iniciarModeloTabla(DefaultTableModel modeloDevolucionPedido,JTable tableDevolucionPedido){
        tableDevolucionPedido.setModel(modeloDevolucionPedido);
           
            modeloDevolucionPedido.addColumn("Fecha Resurtido");
            modeloDevolucionPedido.addColumn("Codigo de Barras");
            modeloDevolucionPedido.addColumn("Precio Por Unidad");
            modeloDevolucionPedido.addColumn("Cantidad");
            modeloDevolucionPedido.addColumn("Existencia");
            modeloDevolucionPedido.addColumn("Fecha Caducidad");
            modeloDevolucionPedido.addColumn("Numero");
    }
    
    public void filtrarResurtido(JTable tableDevolucionPedido,String factura,int proveedor,int sucursal){
        String where = "";
        if (!"".equals(factura) && proveedor!=0 && sucursal!=0) {
            where = " where factura_res='"+factura+"' and cve_prov="+proveedor+" and cve_suc="+sucursal;
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableDevolucionPedido.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = " select fecha_res,codbar_pro,ppu_renres,cant_renres,baja_renres,fechacad_renres,num_num from renglonresurtir rr join  resurtir r on rr.cve_res=r.cve_res join numerozapato nz on nz.cve_num=rr.cve_num"+where+";";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Fecha Resurtido");
            modelo.addColumn("Codigo de Barras");
            modelo.addColumn("Precio Por Unidad");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Existencia");
            modelo.addColumn("Fecha Caducidad");
            modelo.addColumn("Numero");
            
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
    
    
    public void bajaDevolucion(int cantidad, int codbar,String factura, int proveedor, int sucursal){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;  
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            call=conn.prepareCall("{call sp_bajadevolucion(?,?,?,?,?)}");
            call.setInt(1,cantidad);
            call.setInt(2,codbar);
            call.setString(3, factura);
            call.setInt(4, proveedor);
            call.setInt(5, sucursal);
            call.execute();
            call.close();
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Devolucion Completadada correctamente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ocurrio un error al hacer el intercambio", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void bajaDevolucionTodo(JTable tableDevolucion,String factura, int proveedor, int sucursal){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;  
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_comenzar()}");
            call.execute();
            
            int filas=tableDevolucion.getRowCount();
            for(int i=0;i<filas;i++){
                int codbar=Integer.parseInt(tableDevolucion.getValueAt(i, 1).toString());
                int cantidad=Integer.parseInt(tableDevolucion.getValueAt(i, 4).toString());
                
            call=conn.prepareCall("{call sp_bajadevolucion(?,?,?,?,?)}");
            call.setInt(1,cantidad);
            call.setInt(2,codbar);
            call.setString(3, factura);
            call.setInt(4, proveedor);
            call.setInt(5, sucursal);
            call.execute();
            }
            
            call=conn.prepareCall("{call sp_terminar()}");
            call.execute();
            
            call.close();
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Devolucion Completadada correctamente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
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
        
        public int numeroProveedor(String proveedor){
        int aux=0;
            try{
            proveedor="'"+proveedor+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_prov from proveedor where nom_prov="+proveedor+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            aux=rs.getInt("cve_prov");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return aux;
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
