package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;

public class TipoPago {

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

    public Vector<TipoPago> mostrarTipoPago() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<TipoPago> datos = new Vector<TipoPago>();
        TipoPago dat = null;
        try {

            String sql = "select * from tipopago";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new TipoPago();
            dat.setClave(0);
            dat.setNombre("-Selecciona Tipo-");
            datos.add(dat);

            while (rs.next()) {
                dat = new TipoPago();
                dat.setClave(rs.getInt("cve_tippag"));
                dat.setNombre(rs.getString("tipo_tippag"));
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
