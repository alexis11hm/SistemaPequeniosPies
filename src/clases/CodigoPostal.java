package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class CodigoPostal {

    private String clave;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    

    

    @Override
    public String toString() {
        return this.clave;
    }

    public Vector<CodigoPostal> mostrarCodigoPostal(int claveMunicipio) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<CodigoPostal> datos = new Vector<CodigoPostal>();
        CodigoPostal dat = null;
        try {

            String sql = "select * from codigo where cve_mun=" + claveMunicipio;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new CodigoPostal();
            
            dat.setClave("-Selecciona Codigo Postal-");
            datos.add(dat);

            while (rs.next()) {
                dat = new CodigoPostal();
                dat.setClave(rs.getString("cp_cod"));
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
