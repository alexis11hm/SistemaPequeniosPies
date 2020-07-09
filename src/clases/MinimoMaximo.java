
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import modelo.Menu;
import rojerusan.RSNotifyFade;


public class MinimoMaximo {
    
    
    public void mostrarInsercion(DefaultTableModel modeloMinMax,int codbar,String nombre,int minimo,int maximo,String sucursal){
      Object[] fila = new Object[5];
            fila[0] =codbar;
            fila[1] =nombre;
            fila[2] =minimo;
            fila[3] =maximo;
            fila[4] = sucursal;
            modeloMinMax.addRow(fila);
        
    }
    
    
    
    public void mostrarMinimoMaximo(DefaultTableModel modeloMinmax,JTable tableMinMax){
        
        try {
            tableMinMax.setModel(modeloMinmax);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql=" select p.codbar_pro, nom_pro,min_minmax,max_minmax,calle_suc from producto p join minmax mm on p.codbar_pro=mm.codbar_pro join sucursal s on mm.cve_suc=s.cve_suc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloMinmax.addColumn("Codigo de Barras");
            modeloMinmax.addColumn("Nombre");
            modeloMinmax.addColumn("Minimo");
            modeloMinmax.addColumn("Maximo");
            modeloMinmax.addColumn("Sucursal");
            
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloMinmax.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarMinimoMaximo(int codbar, int minimo,int maximo,int cal){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into minmax values(null,?,?,?,?)");
            ps.setInt(1,codbar);
            ps.setInt(2,minimo);
            ps.setInt(3,maximo);
            ps.setInt(4,cal);

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Minimo - Maximo Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("AVISO", "Minimo - Maximo No Se Ha Podido Registrar", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarMinimoMaximo(DefaultTableModel modeloMinMax,JTable tableMinMax){
        PreparedStatement ps = null;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableMinMax.getSelectedRow();
            int codbar = Integer.parseInt(tableMinMax.getValueAt(Fila, 0).toString());
            String calle = tableMinMax.getValueAt(Fila, 4).toString();
            int cal=new MinimoMaximo().numeroSucursal(calle);
            System.err.println(codbar);
             System.err.println(cal);
             
            ps = conn.prepareStatement("delete from minmax where codbar_pro="+codbar+" and cve_suc="+cal+";");
            ps.execute();
            modeloMinMax.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Minimo - Maximo Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            
            conn.close();
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Minimo - Maximo No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarMinimoMaximo(JTable tableMinMax,int codbar,int minimo, int maximo,String calle,String nombre){
        int Fila = tableMinMax.getSelectedRow();
        ResultSet rs=null;
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tableMinMax.getSelectedRow();
            int cb = Integer.parseInt(tableMinMax.getValueAt(Fila, 0).toString());
            String cal = tableMinMax.getValueAt(Fila, 4).toString();
            int call=new MinimoMaximo().numeroSucursal(cal);
            int callee=new MinimoMaximo().numeroSucursal(calle);
          
            ps = conn.prepareStatement("update minmax set codbar_pro="+codbar+", min_minmax="+minimo+" ,max_minmax="+maximo+" , cve_suc="+callee+" where codbar_pro="+cb+" and cve_suc="+call+";");
            ps.execute();
            ps.close();
            
            new rojerusan.RSNotifyFade("AVISO", "Minimo-Maximo Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();
        }catch(Exception e){
            new rojerusan.RSNotifyFade("ERROR", "Minimo-Maximo No Se Ha Podido Modificar", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.err.println(e);
        }
      
            tableMinMax.setValueAt(codbar, Fila, 0);
            tableMinMax.setValueAt(nombre, Fila, 1);
            tableMinMax.setValueAt(minimo, Fila, 2);
            tableMinMax.setValueAt(maximo, Fila, 3);
            tableMinMax.setValueAt(calle, Fila, 4);
    }
    
    public void buscarMinimoMaximo(JTable tableMinmax,int codbar,int sucursal){
        String where = "";
        if (codbar!=0) {
            where = " and p.codbar_pro = " + codbar +";";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableMinmax.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql=" select p.codbar_pro, nom_pro,min_minmax,max_minmax,calle_suc from producto p join minmax mm on p.codbar_pro=mm.codbar_pro join sucursal s on mm.cve_suc=s.cve_suc where s.cve_suc="+sucursal+""+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Codigo Barras");
            modelo.addColumn("Nombre");
            modelo.addColumn("Minimo");
            modelo.addColumn("Maximo");
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
    
    public String nombreProducto(int codbar){
        String col="";
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_pro from producto where codbar_pro="+codbar+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getString("nom_pro");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
}
