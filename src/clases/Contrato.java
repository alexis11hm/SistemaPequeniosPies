
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;


public class Contrato {
    
    private String clave;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String toString() {
        return this.clave;
    }

    public Vector<Contrato> mostrarContratos() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Contrato> datos = new Vector<Contrato>();
        Contrato dat = null;
        try {

            String sql = "select cve_con from contrato";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Contrato();
            dat.setClave("-Selecciona Contrato-");
            datos.add(dat);

            while (rs.next()) {
                dat = new Contrato();
                dat.setClave(Integer.toString(rs.getInt("cve_con")));
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
