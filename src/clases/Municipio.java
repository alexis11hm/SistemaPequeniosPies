package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Municipio {

    private int clave;
    private String nombre;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

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

    public Vector<Municipio> mostrarMunicipio(int claveEstado) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Municipio> datos = new Vector<Municipio>();
        Municipio dat = null;
        try {

            String sql = "select * from municipio where cve_est=" + claveEstado;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Municipio();
            dat.setClave(0);
            dat.setNombre("-Selecciona Municipio-");
            datos.add(dat);

            while (rs.next()) {
                dat = new Municipio();
                dat.setClave(rs.getInt("cve_mun"));
                dat.setNombre(rs.getString("nom_mun"));
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
