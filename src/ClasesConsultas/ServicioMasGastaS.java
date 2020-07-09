
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

public class ServicioMasGastaS {

   
    
    public void iniciarModeloTabla(DefaultTableModel modeloGasta,JTable tableGasta){
        tableGasta.setModel(modeloGasta);
           
            modeloGasta.addColumn("Servicio");
            modeloGasta.addColumn("Total $ Pagado");
    }
    
    
    
    public void filtrarServicio(JTable tableGasta,String inicio,String fin,int sucursal){
       
        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableGasta.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select nom_ser, sum(precio_sucser) as total from sucursalservicio ss join sucursal s on ss.cve_suc=s.cve_suc join servicio se on se.cve_ser=ss.cve_ser where ss.cve_suc="+sucursal+" and fechasuc_sucser between '"+inicio+"' and '"+fin+"' group by nom_ser order by total desc";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Servicio");
            modelo.addColumn("Total $ Pagado");
            
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
