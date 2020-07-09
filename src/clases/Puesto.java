
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;


public class Puesto {
    
    private int clave;
    private String puesto;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    @Override
    public String toString() {
        return this.puesto;
    }

    public Vector<Puesto> mostrarPuestos() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Puesto> datos = new Vector<Puesto>();
        Puesto dat = null;
        try {

            String sql = "select nom_pue from puesto;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Puesto();
            dat.setPuesto("-Selecciona Puesto-");
            datos.add(dat);
            while (rs.next()) {
                dat = new Puesto();
                dat.setPuesto(rs.getString("nom_pue"));
                datos.add(dat);
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
    
}
