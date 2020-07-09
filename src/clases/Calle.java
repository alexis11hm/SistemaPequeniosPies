
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;


public class Calle {
    
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

    public Vector<Calle> mostrarCalles() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Calle> datos = new Vector<Calle>();
        Calle dat = null;
        try {

            String sql = "select * from sucursal";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            //creando un bojeto de esta clase
            dat = new Calle();
            //como sera el primero que ira el el combo, se le asigna la posicion 0, ya que en la base de datos no hay 
            //ninguna id que sea 0
            dat.setClave(0);
            //asignamos el titulo que ira primero
            dat.setNombre("-Selecciona Sucursal-");
            //lo agregamos a nuestro Vector
            datos.add(dat);
            //llenando el Vector con los resultados que tengamos en la base de datos
            while (rs.next()) {
                dat = new Calle();
                dat.setClave(rs.getInt("cve_suc"));
                dat.setNombre(rs.getString("calle_suc"));
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
