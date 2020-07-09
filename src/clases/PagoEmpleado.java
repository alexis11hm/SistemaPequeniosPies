
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import modelo.Menu;
import rojerusan.RSNotifyFade;


public class PagoEmpleado {
    
    public void mostrarPago(DefaultTableModel modeloPagoEmpleado,int contrato, String tipo,Date fecha,Float monto){
        Object[] fila = new Object[4];
            fila[0] = contrato;
            fila[1] = tipo;
            fila[2] = monto;
            fila[3] = fecha;
            modeloPagoEmpleado.addRow(fila);
        
    }
    
    
    
    public void mostrarPagoEmpleado(DefaultTableModel modeloPagoEmpleado,JTable tablePagoEmpleado){
        
        try {
            tablePagoEmpleado.setModel(modeloPagoEmpleado);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = " select cve_con,tipo_tippag,monto_conpag,fechapago_conpag from contratopago cp join tipopago tp on cp.cve_tippag=tp.cve_tippag;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloPagoEmpleado.addColumn("No. Contrato");
            modeloPagoEmpleado.addColumn("Tipo");
            modeloPagoEmpleado.addColumn("Monto");
            modeloPagoEmpleado.addColumn("Fecha");
            
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloPagoEmpleado.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarPago(int contrato,float monto,Date fecha,int tipo){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            ps = conn.prepareStatement("insert into contratopago values(null,?,?,?,?)");
            ps.setFloat(1,monto);
            ps.setDate(2,fecha);
            ps.setInt(3,contrato);
            ps.setInt(4,tipo);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Pago Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("Error", "Error Al Registrar El Pago", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }
    
    public void eliminarPago(DefaultTableModel modeloPagoEmpleado,JTable tablePagoEmpleado){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tablePagoEmpleado.getSelectedRow();
            int contrato = Integer.parseInt(tablePagoEmpleado.getValueAt(Fila, 0).toString());
            String fecha = tablePagoEmpleado.getValueAt(Fila, 3).toString();
            
            String fechaa="'"+fecha+"'";  
            String contratoo="'"+contrato+"'";
            System.err.println(fechaa);
            System.err.println(contratoo);

            ps=conn.prepareStatement("delete from contratopago where cve_con="+contratoo+" and fechapago_conpag="+fechaa+";");
            ps.execute();
            modeloPagoEmpleado.removeRow(Fila);
            ps.close();
            new rojerusan.RSNotifyFade("AVISO", "Pago Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            
            conn.close();
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pago No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarPago(JTable tablePagoEmpleado,int contrato,int tipo,String tip,Float monto,Date fecha){
        int Fila = tablePagoEmpleado.getSelectedRow();
        ResultSet rs=null;
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int numFila = tablePagoEmpleado.getSelectedRow();
            int contratoo = Integer.parseInt(tablePagoEmpleado.getValueAt(Fila, 0).toString());
            String fechaa = tablePagoEmpleado.getValueAt(Fila, 3).toString();
            
            String con="'"+contratoo+"'";
            String fe="'"+fechaa+"'";
            String fech="'"+fecha+"'";
            System.err.println(contratoo);
            System.err.println(fechaa);
            
            ps = conn.prepareStatement("update contratopago set cve_tippag="+tipo+" ,cve_con="+contrato+", monto_conpag="+monto+" ,fechapago_conpag="+fech+" where cve_con="+con+" and fechapago_conpag="+fe+";");

            ps.execute();
            ps.close();

            new rojerusan.RSNotifyFade("AVISO", "Pago Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            
            
            tablePagoEmpleado.setValueAt(contrato, Fila, 0);
            tablePagoEmpleado.setValueAt(tip, Fila, 1);
            tablePagoEmpleado.setValueAt(monto, Fila, 2);
            tablePagoEmpleado.setValueAt(fecha, Fila, 3);
            conn.close();
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pago No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarPago(JTable tablePagoEmpleado,String fecha){
        String where = "";
        if (!"".equals(fecha)) {
            where = " where fechapago_conpag = '" + fecha + "'";
        }

        try {
            DefaultTableModel modelo = new DefaultTableModel();
            tablePagoEmpleado.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            String sql = " select cve_con,tipo_tippag,monto_conpag,fechapago_conpag from contratopago cp join tipopago tp on cp.cve_tippag=tp.cve_tippag;";
            
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("No. Contrato");
            modelo.addColumn("Tipo");
            modelo.addColumn("Monto");
            modelo.addColumn("Fecha");
            
            
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
            }
            con.close();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
    
    public int numeroTipoPago(String tipo){
        int col=0;
            try{
            String tipoo="'"+tipo+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_tippag from tipopago where tipo_tippag="+tipoo+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_tippag");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
}