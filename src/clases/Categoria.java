
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Categoria {
    
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }

    public Vector<Categoria> mostrarCategorias() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Categoria> datos = new Vector<Categoria>();
        Categoria dat = null;
        try {

            String sql = "select nom_cat from categoria";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dat = new Categoria();
            dat.setNombre("-Selecciona Categoria-");
            
            datos.add(dat);
            
            while (rs.next()) {
                dat = new Categoria();
                dat.setNombre(rs.getString("nom_cat"));
                datos.add(dat);
            }
            dat = new Categoria();
            dat.setNombre("");
            
            datos.add(dat);
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
    
}
