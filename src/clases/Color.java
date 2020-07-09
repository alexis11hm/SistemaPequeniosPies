
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Color {
    
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

    public Vector<Color> mostrarColores() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Color> datos = new Vector<Color>();
        Color dat = null;
        try {

            String sql = "select col_col from color";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dat = new Color();
            dat.setNombre("-Selecciona Color-");
            
            datos.add(dat);
            
            while (rs.next()) {
                dat = new Color();
                dat.setNombre(rs.getString("col_col"));
                datos.add(dat);
            }
            dat = new Color();
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
