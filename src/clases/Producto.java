
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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


public class Producto {
   
    public void mostrarInsercion(DefaultTableModel modeloProducto,int codbar,String nombre,String tipo,String marca,String modelo,String color,String categoria,String material,String temporada){
        Object[] fila = new Object[9];
            fila[0] = codbar;
            fila[1] = nombre;
            fila[2] = tipo;
            fila[3] = marca;
            fila[4] = modelo;
            fila[5] = color;
            fila[6] = categoria; 
            fila[7] = material;
            fila[8] = temporada; 
            modeloProducto.addRow(fila);
        
    }
    
    
    
    public void mostrarProducto(DefaultTableModel modeloProducto,JTable tableProducto){
        
        try {
            tableProducto.setModel(modeloProducto);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select * from v_productos";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloProducto.addColumn("Codigo de Barras");
            modeloProducto.addColumn("Nombre");
            modeloProducto.addColumn("Tipo");
            modeloProducto.addColumn("Marca");
            modeloProducto.addColumn("Modelo");
            modeloProducto.addColumn("Color");
            modeloProducto.addColumn("Categoria");
            modeloProducto.addColumn("Material");
            modeloProducto.addColumn("Temporada");

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloProducto.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarProducto(int codbar, String nombre,int tipo ,String marca, int temporada,String modelo,int material, int color, int categoria){
        PreparedStatement ps = null;
        CallableStatement call = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_registrarproducto(?,?,?,?,?,?,?,?,?,?)}");
            call.setInt(1, codbar);
            call.setString(2, nombre);
            call.setInt(3, tipo);
            call.setString(4, marca);
            call.setInt(5, temporada);
            call.setString(6, modelo);
            call.setInt(7, material);
            call.setInt(8, color);
            call.setInt(9, categoria);
            call.registerOutParameter("salida", Types.INTEGER);
            
            call.execute();      
            if(call.getInt("salida")==1){
                new rojerusan.RSNotifyFade("ADVERTENCIA", "Producto Ya Esta Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            }else{
                new rojerusan.RSNotifyFade("AVISO", "Producto Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            }
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
    }
    
    public void eliminarProducto(DefaultTableModel modeloProducto,JTable tableProducto){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableProducto.getSelectedRow();
            int codbar = Integer.parseInt(tableProducto.getValueAt(Fila, 0).toString());
            System.err.println(codbar);

            ps = conn.prepareStatement("delete from producto where codbar_pro="+codbar+";");
            ps.execute();
            ps.close();

            modeloProducto.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Producto Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            
            conn.close();
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarProducto(JTable tableProducto,int codbar,String nombre,String tip, int tipo, String modelo,String marca,String col, int color,String cat, int categoria,String mat, int material,String tem, int temporada){
        int Fila = tableProducto.getSelectedRow();

        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tableProducto.getSelectedRow();
            int codbarr= Integer.parseInt(tableProducto.getValueAt(numFila, 0).toString());
            
            String nombree="'"+nombre+"'";
            String marcaa="'"+marca+"'";
            String modeloo="'"+modelo+"'";
            ps = conn.prepareStatement("update producto set codbar_pro="+codbar+" ,nom_pro="+nombree+" ,cve_tip="+tipo+" , marca_pro="+marcaa+", modelo_pro="+modeloo+", cve_mat="+material+", num_cat="+categoria+", cve_col="+color+", cve_tem="+temporada+" where codbar_pro="+codbarr);

            

            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Producto Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            tableProducto.setValueAt(codbar, Fila, 0);
            tableProducto.setValueAt(nombre, Fila, 1);
            tableProducto.setValueAt(tip, Fila, 2);
            tableProducto.setValueAt(marca, Fila, 3);
            tableProducto.setValueAt(modelo, Fila, 4);
            tableProducto.setValueAt(col, Fila, 5);
            tableProducto.setValueAt(cat, Fila, 6);
            tableProducto.setValueAt(mat, Fila, 7);
            tableProducto.setValueAt(tem, Fila, 8);
            

            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Producto No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarProducto(JTable tableProducto,int codbar){
        String where = "";
        if (codbar!=0) {
            where = "where codbar_pro = " + codbar +";";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableProducto.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            
            String sql = "select * from v_productos "+where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Codigo de Barras");
            modelo.addColumn("Nombre");
            modelo.addColumn("Tipo");
            modelo.addColumn("Marca");
            modelo.addColumn("Modelo");
            modelo.addColumn("Color");
            modelo.addColumn("Categoria");
            modelo.addColumn("Material");
            modelo.addColumn("Temporada");
            
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
    
    public int numeroTipo(String tipo){
        int col=0;
            try{
            String tipoo="'"+tipo+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_tip from tipo where nom_tip="+tipoo+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_tip");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    public int numeroTemporada(String temporada){
        int col=0;
            try{
            String tempo="'"+temporada+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_tem from temporada where nom_tem="+tempo+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_tem");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public int numeroMaterial(String material){
        int col=0;
            try{
            String mate="'"+material+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_mat from material where nom_mat="+mate+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_mat");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public int numeroColor(String color){
        int col=0;
            try{
            String colo="'"+color+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_col from color where col_col="+colo+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_col");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public int numeroCategoria(String categoria){
        int col=0;
            try{
            String cat="'"+categoria+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select num_cat from categoria where nom_cat="+cat+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("num_cat");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
}
