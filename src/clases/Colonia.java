package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class Colonia {

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

    public Vector<Colonia> mostrarColonias(String claveCodigo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Colonia> datos = new Vector<Colonia>();
        Colonia dat = null;
        try {

            String sql = "select * from colonia where cp_cod="+claveCodigo+"";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Colonia();
            dat.setClave(0);
            dat.setNombre("-Selecciona Colonia-");
            datos.add(dat);

            while (rs.next()) {
                dat = new Colonia();
                dat.setClave(rs.getInt("cve_col"));
                dat.setNombre(rs.getString("nom_col"));
                System.out.println(rs.getString("nom_col"));
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