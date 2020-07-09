
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Tipo {
    
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

    public Vector<Tipo> mostrarTipos() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Tipo> datos = new Vector<Tipo>();
        Tipo dat = null;
        try {

            String sql = "select nom_tip from tipo";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dat = new Tipo();
            dat.setNombre("-Selecciona Tipo-");
            
            datos.add(dat);
            
            while (rs.next()) {
                dat = new Tipo();
                dat.setNombre(rs.getString("nom_tip"));
                datos.add(dat);
            }
            dat = new Tipo();
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
