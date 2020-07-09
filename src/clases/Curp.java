
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import modelo.Acceso;


public class Curp {
    
    private String curp;

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }
    
    @Override
    public String toString() {
        return this.curp;
    }

    public Vector<Curp> mostrarCurps() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

        Vector<Curp> datos = new Vector<Curp>();
        Curp dat = null;
        try {

            String sql = "select curp_per from persona;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Curp();
            dat.setCurp("-Selecciona CURP-");
            datos.add(dat);
            while (rs.next()) {
                dat = new Curp();
                dat.setCurp(rs.getString("curp_per"));
                datos.add(dat);
            }
            Curp cu = new Curp();
            cu.setCurp("");
            datos.add(cu);
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
    
}
