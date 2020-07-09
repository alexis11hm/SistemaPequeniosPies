
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

public class ZapatoCompraAltaZ {

   
    
    public void iniciarModeloTabla(DefaultTableModel modeloCompra,JTable tableCompra){
        tableCompra.setModel(modeloCompra);
           
            modeloCompra.addColumn("Codigo de Barras");
            modeloCompra.addColumn("Nombre");
            modeloCompra.addColumn("Precio de Compra por Unidad");
    }
    
    
    
    public void filtrarCompra(JTable tableCompra,String inicio,String fin,int sucursal){
       
        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableCompra.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select rr.codbar_pro, nom_pro, ppu_renres from renglonresurtir rr join resurtir r on rr.cve_res=r.cve_res join producto p on p.codbar_pro=rr.codbar_pro where cve_suc="+sucursal+" and fecha_res between '"+inicio+"' and '"+fin+"' group by nom_pro order by ppu_renres desc";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Codigo de Barras");
            modelo.addColumn("Nombre");
            modelo.addColumn("Precio de Compra por Unidad");
            
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
   
}
