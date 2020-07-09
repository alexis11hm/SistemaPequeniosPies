package clasesDeshabilitadas;


import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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


public class RenglonResurtir {
   
    
    public void mostrarInsercion(DefaultTableModel modeloRenglonResurtir,int codbar,int resurtido,float ppu,Date fechacad,int cantidad,int baja,float mexico,float eua,float uk){
            Object[] fila = new Object[9];
            fila[0] = codbar;
            fila[1] = resurtido;
            fila[2] = ppu;
            fila[3] = fechacad;
            fila[4] = cantidad;
            fila[5] = baja;
            fila[6] = mexico;
            fila[7] = eua;
            fila[8] = uk;
            modeloRenglonResurtir.addRow(fila);
     
    }
    
    
    
    public void mostrarRenglonResurtir(DefaultTableModel modeloRenglonResurtir,JTable tableRenglonResurtir){
        
        try {
            tableRenglonResurtir.setModel(modeloRenglonResurtir);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select codbar_pro,cve_res,ppu_renres,fechacad_renres,cant_renres,baja_renres,num_num,numeua_num,numuk_num from renglonresurtir rr join numerozapato nz on rr.cve_num=nz.cve_num";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modeloRenglonResurtir.addColumn("Codigo Barras");
            modeloRenglonResurtir.addColumn("Resurtido");
            modeloRenglonResurtir.addColumn("Precio Por Unidad");
            modeloRenglonResurtir.addColumn("Fecha Caducidad");
            modeloRenglonResurtir.addColumn("Cantidad");
            modeloRenglonResurtir.addColumn("Baja");
            modeloRenglonResurtir.addColumn("No. Mexico");
            modeloRenglonResurtir.addColumn("No. EUA");
            modeloRenglonResurtir.addColumn("No. Inglaterra");

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modeloRenglonResurtir.addRow(filas);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void registrarRenglonResurtido(float ppu,int cantidad,Date fechacad,int baja,int codbar,int resurtido,int numero){
        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            //String fecha="'"+fechacad+"'";
            
            ps = conn.prepareStatement("insert into renglonresurtir values(null,?,?,?,?,?,?,?)");
            ps.setFloat(1, ppu);
            ps.setInt(2, cantidad);
            ps.setDate(3, fechacad);
            ps.setInt(4, baja);
            ps.setInt(5, codbar);
            ps.setInt(6, resurtido);
            ps.setInt(7, numero);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Pedido Registrado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pedido No Registrado", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            
            System.out.println(ex);
        }
    }
    
    public void eliminarRenglonResurtido(DefaultTableModel modeloRenglonResurtir,JTable tableRenglonResurtir){
        PreparedStatement ps = null;
        int cve=0;
        try {

            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);

            int Fila = tableRenglonResurtir.getSelectedRow();
            int codbar = Integer.parseInt(tableRenglonResurtir.getValueAt(Fila, 0).toString());
            int resurtido = Integer.parseInt(tableRenglonResurtir.getValueAt(Fila, 1).toString()); 
            float ppu = Float.parseFloat(tableRenglonResurtir.getValueAt(Fila, 2).toString());
            
            ps = conn.prepareStatement("delete from renglonresurtir where codbar_pro="+codbar+" and cve_res="+resurtido+" and ppu_renres="+ppu+";");
            ps.execute();
            ps.close();

            modeloRenglonResurtir.removeRow(Fila);
            new rojerusan.RSNotifyFade("AVISO", "Pedido Eliminado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            
            conn.close();
        } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pedido No Se Ha Podido Eliminar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void modificarRenglonResurtir(JTable tableRenglonResurtir,float ppu,int cantidad,Date fechacad,int baja, int codbar,int resurtido,int numero,float mexico,float eua, float uk){
        int Fila = tableRenglonResurtir.getSelectedRow();

        PreparedStatement ps = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion(Acceso.usuario,Acceso.contra);
            String fecha="'"+fechacad+"'";
            int numFila = tableRenglonResurtir.getSelectedRow();
            int producto = Integer.parseInt(tableRenglonResurtir.getValueAt(numFila, 0).toString());
            int resur = Integer.parseInt(tableRenglonResurtir.getValueAt(numFila, 1).toString());
            float pp = Float.parseFloat(tableRenglonResurtir.getValueAt(numFila, 2).toString());
            
            String sql="update renglonresurtir set ppu_renres="+ppu+" ,cant_renres="+cantidad+" ,fechacad_renres="+fecha+" , baja_renres="+baja+" , codbar_pro="+codbar+" , cve_res="+resurtido+" , cve_num="+numero+" where codbar_pro="+producto+" and cve_res="+resur+" and ppu_renres="+pp;
            System.err.println(sql);
            ps = conn.prepareStatement(sql);
            ps.execute();

            new rojerusan.RSNotifyFade("AVISO", "Pedido Modificado Exitosamente", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.SUCCESS).setVisible(true);
            
            tableRenglonResurtir.setValueAt(codbar, Fila, 0);
            tableRenglonResurtir.setValueAt(resurtido, Fila, 1);
            tableRenglonResurtir.setValueAt(ppu, Fila, 2);
            tableRenglonResurtir.setValueAt(fechacad, Fila, 3);
            tableRenglonResurtir.setValueAt(cantidad, Fila, 4);
            tableRenglonResurtir.setValueAt(baja, Fila, 5);
            tableRenglonResurtir.setValueAt(mexico, Fila, 6);
            tableRenglonResurtir.setValueAt(eua, Fila, 7);
            tableRenglonResurtir.setValueAt(uk, Fila, 8);
            conn.close();
            } catch (Exception ex) {
            new rojerusan.RSNotifyFade("ERROR", "Pedido No Se Ha Podido Modificar!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
            System.out.println(ex.toString());
        }
    }
    
    public void buscarResurtido(JTable tableRenglonResurtir,int resurtido){
        String where = "";
        if (resurtido!=0) {
            where = "where cve_res = " + resurtido +";";
        }

        try {
             DefaultTableModel modelo = new DefaultTableModel();
            tableRenglonResurtir.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select codbar_pro,cve_res,ppu_renres,fechacad_renres,cant_renres,baja_renres,num_num,numeua_num,numuk_num from renglonresurtir rr join numerozapato nz on rr.cve_num=nz.cve_num "+where;
            System.out.println(sql);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();
            
            modelo.addColumn("Codigo Barras");
            modelo.addColumn("Resurtido");
            modelo.addColumn("Precio Por Unidad");
            modelo.addColumn("Fecha Caducidad");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Baja");
            modelo.addColumn("No. Mexico");
            modelo.addColumn("No. EUA");
            modelo.addColumn("No. Inglaterra");
            
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
    
    public int numeroZapato(float numero){
        int col=0;
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select cve_num from numerozapato where num_num="+numero+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("cve_num");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    public float numeroEUA(float numero){
        float col=0;
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select numeua_num from numerozapato where num_num="+numero+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("numeua_num");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    public float numeroUK(float numero){
        float col=0;
            try{
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion(Acceso.usuario,Acceso.contra);

            String sql = "select numuk_num from numerozapato where num_num="+numero+";";
        
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
            col=rs.getInt("numuk_num");
            }
            rs.close();
            con.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        return col;
    }
    
    
    
    
}
