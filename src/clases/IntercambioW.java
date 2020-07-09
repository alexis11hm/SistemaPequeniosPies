
package clases;

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

public class IntercambioW {

   
    
    public void mostrarIntercambio(DefaultTableModel modeloIntercambioI,JTable tableIntercambioI){
        
        try {
            tableIntercambioI.setModel(modeloIntercambioI);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select codbar_pro,cant_inter, calle_suc, cve_con,fecha_inter from intercambio i join renglonresurtir rr on i.num_renres=rr.num_renres join sucursal s on i.cve_suc=s.cve_suc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloIntercambioI.addColumn("Codigo de Barras");
            modeloIntercambioI.addColumn("Cantidad");
            modeloIntercambioI.addColumn("Sucursal Destino");
            modeloIntercambioI.addColumn("Contrato");
            modeloIntercambioI.addColumn("Fecha Traspaso");
            
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloIntercambioI.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    
   public void buscarIntercambio(JTable tableMinmax,String inicio, String fin){
        String where = "";
        if (!"".equals(inicio) && !"".equals(fin)) {
            where = " where fecha_inter between '"+inicio+"' and '"+fin+"';";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableMinmax.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql = "select codbar_pro,cant_inter, calle_suc, cve_con,fecha_inter from intercambio i join renglonresurtir rr on i.num_renres=rr.num_renres join sucursal s on i.cve_suc=s.cve_suc"+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Codigo de Barras");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Sucursal Destino");
            modelo.addColumn("Contrato");
            modelo.addColumn("Fecha Traspaso");
            
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
   
}
