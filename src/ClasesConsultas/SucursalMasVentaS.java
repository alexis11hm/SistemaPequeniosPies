
package ClasesConsultas;

import clases.*;
import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import rojerusan.RSNotifyFade;

public class SucursalMasVentaS {

   
    
    public void iniciarModeloTabla(DefaultTableModel modeloSucursal,JTable tableSucursal){
        tableSucursal.setModel(modeloSucursal);
           
            modeloSucursal.addColumn("Sucursal");
            modeloSucursal.addColumn("Cantidad");
    }
    
    
    
    public void filtrarSucursal(JTable tableSucursal,String inicio,String fin){
       
        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableSucursal.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select s.calle_suc, sum(total_tic) as cantidad from ticket t join sucursal s on t.cve_suc=s.cve_suc where fecha_tic between '"+inicio+"' and '"+fin+"' group by t.cve_suc order by cantidad desc";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
             modelo.addColumn("Sucursal");
             modelo.addColumn("Cantidad");
            
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
    
    public String nombreSucursal(int sucursal){
        String sucu="";
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select calle_suc from sucursal where cve_suc="+sucursal+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            sucu=rs.getString("calle_suc");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return sucu;
    }
   
}
