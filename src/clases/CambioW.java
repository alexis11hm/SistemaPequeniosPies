
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

public class CambioW {

   
    
    public void mostrarCambio(DefaultTableModel modeloCambioC,JTable tableCambioC){
        
        try {
            tableCambioC.setModel(modeloCambioC);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select codbar_pro, codbar2_pro, descripcion_cam,fecha_cam,cve_con,calle_suc from cambio c join sucursal s on c.cve_suc=s.cve_suc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloCambioC.addColumn("Codigo de Barras Entro");
            modeloCambioC.addColumn("Codigo de Barras Salio");
            modeloCambioC.addColumn("Descripcion");
            modeloCambioC.addColumn("Fecha");
            modeloCambioC.addColumn("Contrato");
            modeloCambioC.addColumn("Sucursal");
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloCambioC.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    
   public void buscarCambio(JTable tableCambioC,int sucursal, String fecha){
        String where = "";
        if(sucursal!=0){
            where = " where s.cve_suc="+sucursal+"';";
        }
        if (sucursal!=0 && !"".equals(fecha)) {
            where = " where s.cve_suc="+sucursal+" and fecha_cam='"+fecha+"';";
        }
        

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableCambioC.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql = "select codbar_pro, codbar2_pro, descripcion_cam,fecha_cam,cve_con,calle_suc from cambio c join sucursal s on c.cve_suc=s.cve_suc"+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Codigo de Barras Entro");
            modelo.addColumn("Codigo de Barras Salio");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Fecha");
            modelo.addColumn("Contrato");
            modelo.addColumn("Sucursal");
            
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