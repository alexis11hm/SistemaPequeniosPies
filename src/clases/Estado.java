package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;
import modelo.Menu;

public class Estado {

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

    public Vector<Estado> mostrarEstados() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Estado> datos = new Vector<Estado>();
        Estado dat = null;
        try {

            String sql = "select * from estado";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            //creando un bojeto de esta clase
            dat = new Estado();
            //como sera el primero que ira el el combo, se le asigna la posicion 0, ya que en la base de datos no hay 
            //ninguna id que sea 0
            dat.setClave(0);
            //asignamos el titulo que ira primero
            dat.setNombre("-Selecciona Estado-");
            //lo agregamos a nuestro Vector
            datos.add(dat);
            //llenando el Vector con los resultados que tengamos en la base de datos
            while (rs.next()) {
                dat = new Estado();
                dat.setClave(rs.getInt("cve_est"));
                dat.setNombre(rs.getString("nom_est"));
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
