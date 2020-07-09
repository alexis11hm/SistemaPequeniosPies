
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.WindowConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Acceso;
import net.sf.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte {

    public void generarReporteVenta(int sucursal,Date inicio,Date fin){
    try {
    Conexion con = new Conexion();
    Connection conn =con.getConexion(Acceso.usuario, Acceso.contra);
    String in="";
    String fi="";
    in="'"+inicio+"'";
    fi="'"+fin+"'";
    JasperReport reporte = null;
    String ruta="src\\reportes\\Ventas.jasper";
       
            reporte=(JasperReport) JRLoader.loadObjectFromFile(ruta);
            Map parametro=new HashMap();
            parametro.put("sucursal",sucursal);
            parametro.put("inicio",in);
            parametro.put("fin",fi);
            JasperPrint jprint= JasperFillManager.fillReport(ruta, parametro,conn);
            
            JasperViewer view = new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generarReporteInventario(int sucursal){
    try {
    Conexion con = new Conexion();
    Connection conn =con.getConexion(Acceso.usuario, Acceso.contra);
    JasperReport reporte = null;
    String ruta="src\\reportes\\Inventario.jasper";
       
            reporte=(JasperReport) JRLoader.loadObjectFromFile(ruta);
            Map parametro=new HashMap();
            parametro.put("sucursal",sucursal);
            JasperPrint jprint= JasperFillManager.fillReport(ruta, parametro,conn);
            
            JasperViewer view = new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generarReporteCompra(int sucursal,Date inicio,Date fin){
    try {
    Conexion con = new Conexion();
    Connection conn =con.getConexion(Acceso.usuario, Acceso.contra);
    String in="";
    String fi="";
    in="'"+inicio+"'";
    fi="'"+fin+"'";
    JasperReport reporte = null;
    String ruta="src\\reportes\\Compras.jasper";
       
            reporte=(JasperReport) JRLoader.loadObjectFromFile(ruta);
            Map parametro=new HashMap();
            parametro.put("sucursal",sucursal);
            parametro.put("inicio",in);
            parametro.put("fin",fi);
            JasperPrint jprint= JasperFillManager.fillReport(ruta, parametro,conn);
            
            JasperViewer view = new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generarReporteCaja(Date inicio,Date fin){
    try {
    Conexion con = new Conexion();
    Connection conn =con.getConexion(Acceso.usuario, Acceso.contra);
    String in="";
    String fi="";
    in="'"+inicio+"'";
    fi="'"+fin+"'";
    JasperReport reporte = null;
    String ruta="src\\reportes\\Caja.jasper";
       
            reporte=(JasperReport) JRLoader.loadObjectFromFile(ruta);
            Map parametro=new HashMap();
            parametro.put("inicio",in);
            parametro.put("fin",fi);
            JasperPrint jprint= JasperFillManager.fillReport(ruta, parametro,conn);
            
            JasperViewer view = new JasperViewer(jprint,false);
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
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
