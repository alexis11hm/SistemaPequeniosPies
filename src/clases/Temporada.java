
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Temporada {
    
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

    public Vector<Temporada> mostrarTemporadas() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Temporada> datos = new Vector<Temporada>();
        Temporada dat = null;
        try {

            String sql = "select nom_tem from temporada";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dat = new Temporada();
            dat.setNombre("-Selecciona Temporada-");
            
            datos.add(dat);
            
            while (rs.next()) {
                dat = new Temporada();
                dat.setNombre(rs.getString("nom_tem"));
                datos.add(dat);
            }
            dat = new Temporada();
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
