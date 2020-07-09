
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Material {
    
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

    public Vector<Material> mostrarMateriales() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Material> datos = new Vector<Material>();
        Material dat = null;
        try {

            String sql = "select nom_mat from material";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dat = new Material();
            dat.setNombre("-Selecciona Material-");
            
            datos.add(dat);
            
            while (rs.next()) {
                dat = new Material();
                dat.setNombre(rs.getString("nom_mat"));
                datos.add(dat);
            }
            dat = new Material();
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
