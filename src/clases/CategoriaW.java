
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

public class CategoriaW {

    public void mostrarInsercion(DefaultTableModel modeloCategoria,String categoria,String sexo){
        Object[] fila = new Object[2];
            fila[0] = categoria;
            fila[1] = sexo;
            modeloCategoria.addRow(fila);
        
    }
    
    public void mostrarCategoria(DefaultTableModel modeloCategoria,JTable tableCategoria){
        
        try {
            tableCategoria.setModel(modeloCategoria);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_cat,sexo_cat from categoria";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloCategoria.addColumn("categoria");
            modeloCategoria.addColumn("Sexo");
            
            

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloCategoria.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarCategoria(String categoria,String sexo){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into categoria values(null,?,?)");
            ps.setString(1,categoria);
            ps.setString(2,sexo);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Categoria Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Categoria No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarCategoria(DefaultTableModel modeloCategoria,JTable tableCategoria){
        PreparedStatement ps = null;
        int aux=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableCategoria.getSelectedRow();
            String categoria =tableCategoria.getValueAt(Fila, 0).toString();
            categoria="'"+categoria+"'";
            
            ps = conn.prepareStatement("delete from categoria where nom_cat="+categoria+";");
            ps.execute();

            modeloCategoria.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Categoria Eliminada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            conn.close();

        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Categoria No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarCategoria(JTable tableCategoria,String categoria,String sexo){
       PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            int Filaa = tableCategoria.getSelectedRow();
            String cat = tableCategoria.getValueAt(Filaa, 0).toString();
            categoria="'"+categoria+"'";
            sexo="'"+sexo+"'";
            cat="'"+cat+"'";
            ps = conn.prepareStatement("update categoria set nom_cat="+categoria+", sexo_cat="+sexo+"  where nom_cat="+cat+";");
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Categoria Modificada Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            conn.close();
            tableCategoria.setValueAt(categoria, Filaa, 0);
            tableCategoria.setValueAt(sexo, Filaa, 1);
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Categoria No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarCategoria(JTable tableCategoria,String categoria){
        String where = "";
        if (!"".equals(categoria)) {
            where = "where nom_cat = '" + categoria + "'";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableCategoria.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select nom_cat,sexo_cat from categoria "+where;
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Categoria");
            modelo.addColumn("Sexo");
            
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
