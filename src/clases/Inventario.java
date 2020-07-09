
package clases;

import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;


public class Inventario {
    
    public void mostrarInventario(DefaultTableModel modeloInventario,JTable tableInventario,int sucursal,int codbar){
        String where = "";
        if (codbar!=0) {
            where = "and p.codbar_pro = "+ codbar;
        }
        try {
            tableInventario.setModel(modeloInventario);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql ="select p.codbar_pro,nom_pro,ppu_renres,num_num,fechacad_renres,cant_renres,baja_renres from producto p join renglonresurtir rr on p.codbar_pro=rr.codbar_pro join numerozapato nz on rr.cve_num=nz.cve_num join resurtir r on rr.cve_res=r.cve_res where baja_renres>0 and cve_suc="+sucursal+" "+where+" order by fechacad_renres";
            System.err.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloInventario.addColumn("Codigo de Barras");
            modeloInventario.addColumn("Nombre");
            modeloInventario.addColumn("Precio P/Unidad");
            modeloInventario.addColumn("Numero de Calzado");
            modeloInventario.addColumn("Fecha de Caducidad");
            modeloInventario.addColumn("Cantidad");
            modeloInventario.addColumn("Baja");

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloInventario.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
}
