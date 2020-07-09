/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ZapatoMasCaducados extends javax.swing.JFrame {

     DefaultTableModel modeloZapato=new DefaultTableModel();
     
    public ZapatoMasCaducados() {
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo.png")).getImage());
        Calle calle = new Calle();
        DefaultComboBoxModel modelCalleCambio = new DefaultComboBoxModel(calle.mostrarCalles());
        cbSucursal.setModel(modelCalleCambio);
        
        ZapatoMasCaducadoC zc = new ZapatoMasCaducadoC();
        zc.iniciarModeloTabla(modeloZapato, tableZapato);
        
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbSucursal = new javax.swing.JComboBox<>();
        dateFin = new com.toedter.calendar.JDateChooser();
        dateInicio = new com.toedter.calendar.JDateChooser();
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
        jLabel1.setText("Zapato Mas Caducado/ Se Quedado");

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
        botonPastel.setText("graficar");
        botonPastel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPastelActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Sucursal:");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Fecha Inicio:");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fecha Fin:");

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(botonConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(cbGraficas, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonPastel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addGap(18, 18, 18)
                .addComponent(panelGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(cbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                String nombre=tableZapato.getValueAt(i, 0).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.setValue(nombre, new Double(por));
            }
 
               
                JFreeChart chart = ChartFactory.createPieChart(// char t
                               
                                "Zapato Mas Caducado/ Se Quedo",// title                                                                                                                                        
                                dataset, // data
                                true, // include legend
                                true, false);
               
               
                panel= new ChartPanel(chart);
        }if(cbGraficas.getSelectedItem().toString().equals("Barras3D")){
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                
                int filas=tableZapato.getRowCount();
            for(int i=0;i<filas;i++){
                String nombre=tableZapato.getValueAt(i, 0).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.addValue(por, nombre, nombre);
            }
                JFreeChart barChart = ChartFactory.createBarChart3D(
                                "Zapato Mas Caducado/Quedado",
                                "Zapato",
                                "Cantidad",
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
                String nombre=tableZapato.getValueAt(i, 0).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.setValue(nombre, new Double(por));
            }
 
               
                JFreeChart chart = ChartFactory.createPieChart3D(// char t
                               
                                "Zapato Mas Caducado/Quedado",// title                                                                                                                                        
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
                String nombre=tableZapato.getValueAt(i, 0).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
           
                dataset.addValue(por, nombre, nombre);
            }
                JFreeChart barChart = ChartFactory.createBarChart(
                                "Zapato Mas Caducado/Quedado",
                                "Zapato",
                                "Cantidad",
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
                String nombre=tableZapato.getValueAt(i, 0).toString();
                double por=Double.parseDouble(tableZapato.getValueAt(i, 2).toString());     
                dataset.addValue(por, "Zapatos", nombre);
            }
                JFreeChart lineChart = ChartFactory.createLineChart(
                                "Zapatos Mas Caducados/Quedados",
                                "Zapato",
                                "Cantidad",
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
        ZapatoMasCaducadoC zc = new ZapatoMasCaducadoC(); 
        Date date = dateInicio.getDate();
        long d=date.getTime();
        java.sql.Date inicio = new java.sql.Date(d);
        
        Date date2 = dateFin.getDate();
        long d2=date2.getTime();
        java.sql.Date fin = new java.sql.Date(d2);
        
        String sucu=cbSucursal.getSelectedItem().toString();
        int sucursal=zc.numeroSucursal(sucu);
        zc.filtrarZapato(tableZapato, inicio.toString(), fin.toString(), sucursal);
        
        
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
            java.util.logging.Logger.getLogger(ZapatoMasCaducados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZapatoMasCaducados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZapatoMasCaducados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZapatoMasCaducados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ZapatoMasCaducados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle botonCerrar;
    private rojerusan.RSMaterialButtonRectangle botonConsultar;
    private rojerusan.RSMaterialButtonRectangle botonPastel;
    private javax.swing.JComboBox<String> cbGraficas;
    private javax.swing.JComboBox<String> cbSucursal;
    private com.toedter.calendar.JDateChooser dateFin;
    private com.toedter.calendar.JDateChooser dateInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelGrafica;
    private javax.swing.JTable tableZapato;
    // End of variables declaration//GEN-END:variables
}
