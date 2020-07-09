
package consultas;

import ClasesConsultas.*;
import clases.Calle;
import modelo.*;
import clases.ColorW;
import clases.ServicioS;
import java.awt.BorderLayout;
import java.util.Date;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import rojerusan.RSNotifyFade;

/**
 *
 * @author HP-Alexis
 */
public class ZapatoNoVendido extends javax.swing.JFrame {

     DefaultTableModel modeloZapato=new DefaultTableModel();
     
    public ZapatoNoVendido() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo.png")).getImage());
        Calle calle = new Calle();
        DefaultComboBoxModel modelCalleCambio = new DefaultComboBoxModel(calle.mostrarCalles());
        cbSucursal.setModel(modelCalleCambio);
        
        ZapatoNoVendidoZ zz = new ZapatoNoVendidoZ(); 
        zz.iniciarModeloTabla(modeloZapato, tableZapato);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableZapato = new javax.swing.JTable();
        botonPastel = new rojerusan.RSMaterialButtonRectangle();
        jLabel2 = new javax.swing.JLabel();
        cbSucursal = new javax.swing.JComboBox<>();
        botonCerrar = new rojerusan.RSMaterialButtonRectangle();
        botonConsultar = new rojerusan.RSMaterialButtonRectangle();
        panelGrafica = new javax.swing.JPanel();
        cbGraficas = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/zapatomasvendido.png"))); // NOI18N
        jLabel1.setText("   Zapato Aun No Vendido");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableZapato.setBackground(new java.awt.Color(204, 204, 255));
        tableZapato.setForeground(new java.awt.Color(0, 0, 0));
        tableZapato.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableZapato);

        botonPastel.setBackground(new java.awt.Color(0, 85, 33));
        botonPastel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonPastel.setText("Graficar");
        botonPastel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPastelActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Sucursal:");

        cbSucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        botonCerrar.setBackground(new java.awt.Color(255, 153, 51));
        botonCerrar.setForeground(new java.awt.Color(0, 0, 0));
        botonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonCerrar.setText("volver");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        botonConsultar.setBackground(new java.awt.Color(0, 51, 204));
        botonConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonConsultar.setText("Consultar");
        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGraficaLayout = new javax.swing.GroupLayout(panelGrafica);
        panelGrafica.setLayout(panelGraficaLayout);
        panelGraficaLayout.setHorizontalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
        );
        panelGraficaLayout.setVerticalGroup(
            panelGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        cbGraficas.setBackground(new java.awt.Color(255, 255, 255));
        cbGraficas.setForeground(new java.awt.Color(0, 0, 0));
        cbGraficas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Selecciona Grafica-", "Lineal", "Barras2D", "Pastel2D", "Barras3D", "Pastel3D" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(cbGraficas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonPastel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(panelGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonPastel, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbGraficas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(panelGrafica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonPastelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPastelActionPerformed
        ChartPanel panel=null;
        if(cbGraficas.getSelectedItem().toString().equals("Pastel2D")){
        DefaultPieDataset dataset = new DefaultPieDataset();
           
            int filas=tableZapato.getRowCount();
            for(int i=0;i<filas;i++){
                String nombre=tableZapato.getValueAt(i, 1).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.setValue(nombre, new Double(por));
            }
 
               
                JFreeChart chart = ChartFactory.createPieChart(// char t
                               
                                "Zapato Aun No Vendido",// title                                                                                                                                        
                                dataset, // data
                                true, // include legend
                                true, false);
               
               
                panel= new ChartPanel(chart);
        }if(cbGraficas.getSelectedItem().toString().equals("Barras3D")){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                
                int filas=tableZapato.getRowCount();
            for(int i=0;i<filas;i++){
                String nombre=tableZapato.getValueAt(i, 1).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.addValue(por, nombre, nombre);
            }
                JFreeChart barChart = ChartFactory.createBarChart3D(
                                "Zapato Aun No Vendido",
                                "Zapato",
                                "Existencia",
                                dataset,
                                PlotOrientation.VERTICAL,
                                true,
                                true,
                                false);
                panel= new ChartPanel(barChart);
                
        }if(cbGraficas.getSelectedItem().toString().equals("Pastel3D")){
            DefaultPieDataset dataset = new DefaultPieDataset();
           
            int filas=tableZapato.getRowCount();
            for(int i=0;i<filas;i++){
                String nombre=tableZapato.getValueAt(i, 1).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.setValue(nombre, new Double(por));
            }
 
               
                JFreeChart chart = ChartFactory.createPieChart3D(// char t
                               
                                "Zapatos Aun No Vendidos",// title                                                                                                                                        
                                dataset, // data
                                true, // include legend
                                true, false);
                
                PiePlot3D plot = ( PiePlot3D ) chart.getPlot( );            
                plot.setStartAngle( 270 );            
                plot.setForegroundAlpha( 1 );            
                plot.setInteriorGap( 0.02 );
               
               
                panel= new ChartPanel(chart);
        }if(cbGraficas.getSelectedItem().toString().equals("Barras2D")){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                
                int filas=tableZapato.getRowCount();
            for(int i=0;i<filas;i++){
                String nombre=tableZapato.getValueAt(i, 1).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.addValue(por, nombre, nombre);
            }
                JFreeChart barChart = ChartFactory.createBarChart(
                                "Zapatos Aun No Vendidos",
                                "Zapato",
                                "Existencia",
                                dataset,
                                PlotOrientation.VERTICAL,
                                true,
                                true,
                                false);
                panel= new ChartPanel(barChart);
            
        }if(cbGraficas.getSelectedItem().toString().equals("Lineal")){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            int filas=tableZapato.getRowCount();
            for(int i=0;i<filas;i++){
                String nombre=tableZapato.getValueAt(i, 1).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
                dataset.addValue(por, "Zapatos", nombre);
            }
                JFreeChart lineChart = ChartFactory.createLineChart(
                                "Zapatos Aun No Vendidos",
                                "Zapato",
                                "Existencia",
                                dataset,
                                PlotOrientation.VERTICAL, true, true, false);
 
 
               
                panel = new ChartPanel(lineChart);
            
        }
               
               
               
                panel.setSize(510,440);
                panel.setLocation(5, 5);
                panelGrafica.removeAll();
                panelGrafica.add(panel,BorderLayout.CENTER);
                panelGrafica.revalidate();
                panelGrafica.repaint();
    }//GEN-LAST:event_botonPastelActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void botonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarActionPerformed
        try{
        ZapatoNoVendidoZ zz = new ZapatoNoVendidoZ();
        String sucu=cbSucursal.getSelectedItem().toString();
        int sucursal=zz.numeroSucursal(sucu);
        zz.filtrarZapato(tableZapato, sucursal);
        
        
       }catch(Exception e){     
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos ingresadaos son incorrectos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                
        }
    }//GEN-LAST:event_botonConsultarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ZapatoNoVendido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZapatoNoVendido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZapatoNoVendido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZapatoNoVendido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ZapatoNoVendido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle botonCerrar;
    private rojerusan.RSMaterialButtonRectangle botonConsultar;
    private rojerusan.RSMaterialButtonRectangle botonPastel;
    private javax.swing.JComboBox<String> cbGraficas;
    private javax.swing.JComboBox<String> cbSucursal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelGrafica;
    private javax.swing.JTable tableZapato;
    // End of variables declaration//GEN-END:variables
}
