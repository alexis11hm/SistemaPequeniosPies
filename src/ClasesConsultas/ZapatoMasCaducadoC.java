
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

public class ZapatoMasCaducadoC {

   
    
    public void iniciarModeloTabla(DefaultTableModel modeloZapato,JTable tableZapato){
        tableZapato.setModel(modeloZapato);
           
            modeloZapato.addColumn("Nombre Producto");
            modeloZapato.addColumn("Codigo de Barras");
            modeloZapato.addColumn("Cantidad");
            
    }
    
    
    
    public void filtrarZapato(JTable tableZapato,String inicio,String fin,int sucursal){
       
        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableZapato.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select nom_pro,rr.codbar_pro,sum(rr.cant_renres) as cuenta from renglonresurtir rr join producto p on rr.codbar_pro=p.codbar_pro join resurtir r on rr.cve_res=r.cve_res where cve_suc="+sucursal+" and fechacad_renres<'"+fin+"' and fecha_res between '"+inicio+"' and '"+fin+"' group by rr.codbar_pro order by cuenta desc";
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Nombre Producto");
            modelo.addColumn("Codigo de Barras");
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
   
}
