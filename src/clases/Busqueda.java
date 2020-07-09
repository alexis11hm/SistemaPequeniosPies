
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;


public class Busqueda {
    
    
    public void buscarZapato(JTable tableBusqueda,String sucursal,String tipo,String temporada,String material,String color,String marca,String categoria){
        Busqueda se = new Busqueda();
        int sucu=se.numeroSucursal(sucursal);
        String where=" and cve_suc="+sucu;
        if(!tipo.equals("")){
            where=" and nom_tip='"+tipo+"' and cve_suc="+sucu;
        }
        if(!temporada.equals("")){
            where=" and nom_tem='"+temporada+"' and cve_suc="+sucu;
        }
        if(!material.equals("")){
            where=" and nom_mat='"+material+"' and cve_suc="+sucu; 
        }
        if(!color.equals("")){  
            where=" and col_col='"+color+"' and cve_suc="+sucu;
        }
        if(!marca.equals("")){
            where=" and marca_pro='"+marca+"' and cve_suc="+sucu;
        }
        if(!categoria.equals("")){
            where=" and nom_cat='"+categoria+"' and cve_suc="+sucu;
        }
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tableBusqueda.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql="select v.codbar_pro,nom_pro,nom_tip,marca_pro,modelo_pro,col_col,nom_cat,nom_mat,nom_tem,baja_renres,num_num from v_productos v join renglonresurtir rr on v.codbar_pro=rr.codbar_pro join resurtir r on rr.cve_res=r.cve_res join numerozapato nz on nz.cve_num=rr.cve_num where baja_renres>0 and fechacad_renres>curdate()"+where+";";
            
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
            modelo.addColumn("Existencia");
            modelo.addColumn("Numero");
            
            
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
