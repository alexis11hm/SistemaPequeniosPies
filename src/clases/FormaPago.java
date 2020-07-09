
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class FormaPago {
    
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

    public Vector<FormaPago> mostrarFormasPago() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<FormaPago> datos = new Vector<FormaPago>();
        FormaPago dat = null;
        try {

            String sql = "select forma_forpag from formapago";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            dat = new FormaPago();
            dat.setNombre("-Selecciona Forma de Pago-");
            
            datos.add(dat);
            
            while (rs.next()) {
                dat = new FormaPago();
                dat.setNombre(rs.getString("forma_forpag"));
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
