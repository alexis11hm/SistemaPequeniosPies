
package clases;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Acceso;
import rojerusan.RSNotifyFade;


public class Checador {
    
  
     public void mostrarInsercion(DefaultTableModel modeloChecador,int contrato,String entrada,int clave){
         Object[] fila = new Object[4];
            fila[0]=clave;
            fila[1] =contrato;
            fila[2] =entrada;
            fila[3] ="";
            modeloChecador.addRow(fila);
            
    }
    
    
    
    public void mostrarChecador(DefaultTableModel modeloChecador,JTable tableChecador){
        
        try {
            tableChecador.setModel(modeloChecador);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql="select num_che,cve_con,entrada_che,salida_che from checador where date(entrada_che)=curdate();";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            modeloChecador.addColumn("No.");
            modeloChecador.addColumn("No. Contrato");
            modeloChecador.addColumn("Entrada");
            modeloChecador.addColumn("Salida");
            
            
            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloChecador.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public String registrarEntrada(int contrato){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;
        String entrada="";
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_checarentrada(?,?)}");
            call.setInt(1, contrato);
            call.registerOutParameter("entrada", Types.VARCHAR);
            
            call.execute();
            entrada=call.getString("entrada");
            new rojerusan.RSNotifyFade("AVISO", "Chequeo de Entrada Correctamente", 3, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            call.close();    
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("Error", "Error al Checar", 3, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
        return entrada;
    }
    public void registrarSalida(JTable tableChecador){
        PreparedStatement ps = null;
        java.sql.CallableStatement call = null;
        String salida="";
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            int Fila = tableChecador.getSelectedRow();
            int clave = Integer.parseInt(tableChecador.getValueAt(Fila, 0).toString());
            
            call=conn.prepareCall("{call sp_checarsalida(?,?)}");
            call.setInt(1, clave);
            call.registerOutParameter("salida", Types.VARCHAR);
            
            call.execute();
            salida=call.getString("salida");
            tableChecador.setValueAt(salida, Fila, 3);
            new rojerusan.RSNotifyFade("AVISO", "Chequeo de Salida Correctamente", 3, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            call.close();    
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("Error", "Error Al Checar", 3, RSNotifyFade.PositionNotify.TopRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex);
        }
    }    
    
    public int numeroChecador(int contrato,String fecha){
        int col=0;
            try{
            fecha="'"+fecha+"'";
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select num_che from checador where entrada_che="+fecha+" and cve_con="+contrato+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("num_che");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
}
