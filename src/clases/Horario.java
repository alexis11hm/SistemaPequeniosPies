
package clases;

import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
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


public class Horario {
   
    public void mostrarInsercion(DefaultTableModel modeloHorario,int contrato,String entrada,String salida,String comida,Date descanso,String fecha){
        Object[] fila = new Object[6];
            fila[0] = contrato;
            fila[1] = entrada;
            fila[2] = salida;
            fila[3] = comida;
            fila[4] = descanso; 
            fila[5] = fecha;
            modeloHorario.addRow(fila);
        
    }
    
    
    
    public void mostrarHorario(DefaultTableModel modeloHorario,JTable tableHorario){
        
        try {
            tableHorario.setModel(modeloHorario);

            modeloHorario.addColumn("Contrato");
            modeloHorario.addColumn("Entrada");
            modeloHorario.addColumn("Salida");
            modeloHorario.addColumn("Comida");
            modeloHorario.addColumn("Dia Descanso");
            modeloHorario.addColumn("Fecha");
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
    
    public String registrarHorario(DefaultTableModel modeloHorario,String entrada, String salida,String comida,Date descanso,int contrato){
        PreparedStatement ps = null;
        CallableStatement call = null;
        int com=0;
        int dia=0;
        int hentrada=0;
        int hsalida=0;
        String fecha="";
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_horacomida(?,?,?)}");
            call.setString(1, comida);
            call.setInt(2, contrato);
            call.registerOutParameter("estatus", Types.INTEGER);       
            call.execute();      
            com=call.getInt("estatus");
            
            call=conn.prepareCall("{call sp_diadescanso(?,?,?)}");
            call.setDate(1, descanso);
            call.setInt(2, contrato);
            call.registerOutParameter("status", Types.INTEGER);       
            call.execute();      
            dia=call.getInt("status");
            
            call=conn.prepareCall("{call sp_verificarhoraentrada(?,?)}");
            call.setString(1, entrada);
            call.registerOutParameter("estatus", Types.INTEGER);       
            call.execute();      
            hentrada=call.getInt("estatus");
            
            call=conn.prepareCall("{call sp_verificarhorasalida(?,?)}");
            call.setString(1, salida);
            call.registerOutParameter("estatus", Types.INTEGER);       
            call.execute();      
            hsalida=call.getInt("estatus");
            
            if(com==1 && dia==1 && hentrada==1 && hsalida==1){
                call=conn.prepareCall("{call sp_registrarhorario(?,?,?,?,?,?)}");
                call.setString(1, entrada);
                call.setString(2, salida);
                call.setString(3, comida);
                call.setDate(4, descanso);
                call.setInt(5, contrato);
                call.registerOutParameter("fecha", Types.VARCHAR);  
                call.execute();
                fecha=call.getString("fecha");
                new rojerusan.RSNotifyFade("AVISO", "Horario Semanal Correctamente Registrado!", 5, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
                new Horario().mostrarInsercion(modeloHorario, contrato, entrada, salida, comida, descanso,fecha);
            }else{
                if(dia==0){
                    new rojerusan.RSNotifyFade("ADVERTENCIA", "No puedes descansar este dia, elige otro!", 5, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                }
                if(com==0){
                   new rojerusan.RSNotifyFade("ADVERTENCIA", "Hora de comida seleccionada, elige otra!", 5, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                }
                if(hentrada==0){
                   new rojerusan.RSNotifyFade("ADVERTENCIA", "Tu horario no puede tener esa hora de entrada, aun no esta abierta la tienda, cambia a uno despues de las 10:00:00!", 5, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                }
                if(hsalida==0){
                   new rojerusan.RSNotifyFade("ADVERTENCIA", "Tu horario no puede tener esa hora de salida, ya esta cerrada la tienda, cambia a uno antess de las 20:00:00!", 5, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                }
            }
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Horario No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
        return fecha;
    }
    
    public void actualizarHorario(JTable tableHorario,int sucursal){
        CallableStatement call = null;
        String fecha="";
        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableHorario.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);
            
            call=con.prepareCall("{call sp_obtenerfechaactual(?)}");
                call.registerOutParameter("fecha", Types.VARCHAR);  
                call.execute();
                fecha=call.getString("fecha");
                fecha="'"+fecha+"'";
            
            String sql;
            sql = "select c.cve_con,hentrada_horcon,hsalida_horcon,hcomida_horcon,diadescanso_horcon from horariocontrato hc join contrato c on c.cve_con=hc.cve_con where cve_suc="+sucursal+" and fecha_horcon="+fecha;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("Contrato");
            modelo.addColumn("Hora Entrada");
            modelo.addColumn("Hora Salida");
            modelo.addColumn("Hora Comida");
            modelo.addColumn("Dia de Descanso");
            
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
    
    public boolean habilitarHorario(int diaHabilitar){
        CallableStatement call = null;
        int dia=0;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            
            call=conn.prepareCall("{call sp_habilitarhorario(?)}");
            call.registerOutParameter("disponible", Types.INTEGER);       
            call.execute();      
            dia=call.getInt("disponible");
            call.close();   
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Ocurrio Un Error", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
        if(dia==diaHabilitar){
                return true;
            }else{
                return false;
            }
    }
    
    public int numeroSucursal(String calle){
        int col=0;
            try{
            String callee="'"+calle+"'";
            System.out.println("salida calle:"+calle);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_suc from sucursal where calle_suc="+callee+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_suc");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
}
