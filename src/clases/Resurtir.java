
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


public class Resurtir {

    public void iniciarModeloTabla(DefaultTableModel modeloResurtir,JTable tableResurtir){
        tableResurtir.setModel(modeloResurtir);
           
            modeloResurtir.addColumn("Proveedor");
            modeloResurtir.addColumn("Sucursal");
            modeloResurtir.addColumn("Factura");
            modeloResurtir.addColumn("Fecha.");
            modeloResurtir.addColumn("Codigo Barras");
            modeloResurtir.addColumn("Precio P/U.");
            modeloResurtir.addColumn("Numero");
            modeloResurtir.addColumn("Fecha Caducidad");
            modeloResurtir.addColumn("Cantidad");
            modeloResurtir.addColumn("Existencia");
    }
    
    public void agregarProducto(DefaultTableModel modeloResurtir,String proveedor,String sucursal, int factura,Date fecha,int codbar,float ppu,float numero,Date fechacad,int cantidad,int existencia){
         Object[] fila = new Object[11];
            fila[0]= proveedor;
            fila[1] = sucursal;
            fila[2] = factura;
            fila[3] = fecha;
            fila[4] = codbar;
            fila[5] = ppu;
            fila[6] = numero;
            fila[7] = fechacad;
            fila[8] = cantidad;
            fila[9] = existencia;
            modeloResurtir.addRow(fila);
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
    
    public int registrarResurtido(JTable tableResurtir){//,int proveedor, int sucursal, int factura,Date fecha,int codbar,float ppu,float numero,Date fechacad,int cantidad,int existencia){
        java.sql.CallableStatement call = null;
        int clave=0;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            String prov=tableResurtir.getValueAt(0, 0).toString();
            int proveedor=new Resurtir().numeroProveedor(prov);
            String sucu=tableResurtir.getValueAt(0, 1).toString();
            int sucursal=new Resurtir().numeroSucursal(sucu);
            String fecha=tableResurtir.getValueAt(0, 3).toString();
            int factura=Integer.parseInt(tableResurtir.getValueAt(0, 2).toString());
            
            call=conn.prepareCall("{call sp_resurtir(?,?,?,?,?)}");
            call.setInt(1, proveedor);
            call.setString(2, fecha);
            call.setInt(3, factura);
            call.setInt(4, sucursal);
            call.registerOutParameter("resurtido", Types.INTEGER);
            call.execute();
            clave=call.getInt("resurtido");
            
            int filas=tableResurtir.getRowCount();
            for(int i=0;i<filas;i++){
                int codbar=Integer.parseInt(tableResurtir.getValueAt(i, 4).toString());
                float ppu=Float.parseFloat(tableResurtir.getValueAt(i, 5).toString());
                float num=Float.parseFloat(tableResurtir.getValueAt(i, 6).toString());
                int numero=new Resurtir().numeroZapato(num);
                String fechacad=tableResurtir.getValueAt(i, 7).toString();
                int cantidad=Integer.parseInt(tableResurtir.getValueAt(i, 8).toString());
                int existencia=Integer.parseInt(tableResurtir.getValueAt(i, 9).toString());
                
                call=conn.prepareCall("{call sp_registrarresurtido(?,?,?,?,?,?,?)}");
                call.setFloat(1,ppu);
                call.setInt(2,codbar);
                call.setFloat(3,numero);
                call.setString(4,fechacad);
                call.setInt(5,cantidad);
                call.setInt(6,existencia);
                call.setInt(7,clave);
                call.execute();
            }
            call=conn.prepareCall("{call sp_cerrarresurtido()}");
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
