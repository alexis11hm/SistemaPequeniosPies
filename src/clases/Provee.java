
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;


public class Provee {
    
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

    public Vector<Provee> mostrarProveedores() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Provee> datos = new Vector<Provee>();
        Provee dat = null;
        try {

            String sql = "select nom_prov from proveedor;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Provee();
            dat.setNombre("-Selecciona Proveedor-");
            datos.add(dat);
            while (rs.next()) {
                dat = new Provee();
                dat.setNombre(rs.getString("nom_prov"));
                datos.add(dat);
            }
            Provee cu = new Provee();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
    
}
