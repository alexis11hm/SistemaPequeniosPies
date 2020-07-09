
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


public class Regalo {

    public void iniciarModeloTabla(DefaultTableModel modeloRegalo,JTable tableRegalo){
        tableRegalo.setModel(modeloRegalo);
           
            modeloRegalo.addColumn("Codigo de Barras");
            modeloRegalo.addColumn("Cantidad");
            modeloRegalo.addColumn("Sucursal");
            modeloRegalo.addColumn("Empleado");
    }
    
    public void agregarRegalo(DefaultTableModel modeloRegalo,int codbar,String sucursal, int cantidad,int contrato){
         Object[] fila = new Object[11];
            fila[0]= codbar;
            fila[1] = cantidad;
            fila[2] = sucursal;
            fila[3] = contrato;
            modeloRegalo.addRow(fila);
            new rojerusan.RSNotifyFade("AVISO", "Producto Agregado Exitosamente", 2, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

    }
    
    public void eliminarProducto(DefaultTableModel modeloResurtir,JTable tableResurtir){
        try{
            int Fila = tableResurtir.getSelectedRow();
            modeloResurtir.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Producto Eliminado Exitosamente", 2, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Se Ha Podido Eliminar!", 2, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public int registrarRegalos(JTable tableRegalo){//,int proveedor, int sucursal, int factura,Date fecha,int codbar,float ppu,float numero,Date fechacad,int cantidad,int existencia){
        java.sql.CallableStatement call = null;
        int clave=0;
        Venta v = new Venta();
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            call=conn.prepareCall("{call sp_comenzar()}");
            call.execute();
            int filas=tableRegalo.getRowCount();
            for(int i=0;i<filas;i++){
                
                int codbar=Integer.parseInt(tableRegalo.getValueAt(i, 0).toString());
                int cantidad=Integer.parseInt(tableRegalo.getValueAt(i, 1).toString());
                String calle=tableRegalo.getValueAt(i, 2).toString();
                int contrato=Integer.parseInt(tableRegalo.getValueAt(i, 3).toString());
                int sucursal = v.numeroSucursal(calle);
                call=conn.prepareCall("{call sp_bajaregalo(?,?,?)}");
                call.setFloat(1,cantidad);
                call.setInt(2,codbar);
                call.setFloat(3,sucursal);
                call.execute();
                
                call=conn.prepareCall("{call sp_insertarzapato(?,?,?)}");
                call.setFloat(1,cantidad);
                call.setInt(2,contrato);
                call.setFloat(3,codbar);
                call.execute();
                
                
            }
            call=conn.prepareCall("{call sp_terminar()}");
            call.execute();
            call.close();
            conn.close();
            new rojerusan.RSNotifyFade("AVISO", "Resurtido completado correctamente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ocurrio un error al registar el resurtido", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
        return clave;
    }
    
    public boolean existenciaProducto(int codbar, int sucursal, int cantidad){
       java.sql.CallableStatement call = null;
        boolean existe=false;
        int clave=0;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            call=conn.prepareCall("{call sp_regaloexistente(?,?,?,?)}");
            call.setInt(1, codbar);
            call.setInt(2, sucursal);
            call.setInt(3, cantidad);
            call.registerOutParameter("salida", Types.INTEGER);
            call.execute();
            clave=call.getInt("salida");
            if(clave==1){
                existe=true;
            }else{
                existe=false;
            }
        }
        catch(Exception e){
           new rojerusan.RSNotifyFade("ERROR", "Ocurrio un error al consultar la cantidad existente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);      
        }
        return existe;
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
    
    public int numeroSucursal(String sucursal){
        int aux=0;
            try{
            sucursal="'"+sucursal+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_suc from sucursal where calle_suc="+sucursal+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            aux=rs.getInt("cve_suc");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return aux;
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
}
