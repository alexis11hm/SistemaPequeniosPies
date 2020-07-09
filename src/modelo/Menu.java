package modelo;
import consultas.*;
import ClasesConsultas.*;
import clases.*;
import clases.Color;
import clases.Curp;
import clases.Servicio;
import clases.Calle;
import clases.Categoria;
import clases.Checador;
import clases.CodigoPostal;
import clases.Colonia;
import clases.Contacto;
import clases.Contrato;
import clases.Empleado;
import clases.Estado;
import clases.Material;
import clases.Municipio;
import clases.PagoEmpleado;
import clases.Persona;
import clases.Proveedor;
import clases.Puesto;
import clases.Sucursal;
import clases.SucursalServicio;
import clases.Temporada;
import clases.Tipo;
import com.mysql.jdbc.Connection;
import conexion.Conexion;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

import java.awt.event.ItemEvent;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;
import javax.swing.table.DefaultTableModel;
import rojerusan.RSNotifyFade;
import rojerusan.RSPanelsSlider;

public class Menu extends javax.swing.JFrame implements Printable{
    
DefaultTableModel modelo=new DefaultTableModel();
DefaultTableModel modeloSucursalServicio=new DefaultTableModel();
DefaultTableModel modeloProveedor=new DefaultTableModel();
DefaultTableModel modeloPersona=new DefaultTableModel();
DefaultTableModel modeloContacto=new DefaultTableModel();
DefaultTableModel modeloEmpleado=new DefaultTableModel();
DefaultTableModel modeloChecador=new DefaultTableModel();
DefaultTableModel modeloPagoEmpleado=new DefaultTableModel();
DefaultTableModel modeloProducto=new DefaultTableModel();
DefaultTableModel modeloResurtir=new DefaultTableModel();
DefaultTableModel modeloRenglonResurtir=new DefaultTableModel();
DefaultTableModel modeloMinmax=new DefaultTableModel();
DefaultTableModel modeloPrecio=new DefaultTableModel();
DefaultTableModel modeloRegalo=new DefaultTableModel();
DefaultTableModel modeloVenta=new DefaultTableModel();
DefaultTableModel modeloPago=new DefaultTableModel();
DefaultTableModel modeloHorario=new DefaultTableModel();
DefaultTableModel modeloIntercambio=new DefaultTableModel();
DefaultTableModel modeloCambio=new DefaultTableModel();
DefaultTableModel modeloCambioPago=new DefaultTableModel();
DefaultTableModel modeloDevolucion=new DefaultTableModel();

float total=0;
float totalRestante=0;
String fecha="";
    public Menu() {
        initComponents();
        setTitle("Menu Principal");
        setExtendedState(MAXIMIZED_BOTH);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/logo.png")).getImage());
        
        rsscalelabel.RSScaleLabel.setScaleLabel(labelImagen,"src/Productos/sinzapato.png");
        rsscalelabel.RSScaleLabel.setScaleLabel(labelFotoPersona,"src/Personas/sinpersona.png");
        rsscalelabel.RSScaleLabel.setScaleLabel(marcoTicket,"src/imagenes/logo.png");
        
        botonConsultarSucursal.setToolTipText("¡ Necesitas ingresar el nombre de la calle para su consulta !");
        botonConsultarSucursalServicio.setToolTipText("¡ Necesitas ingresar la fecha para la consulta de los servcicios !");
        botonConsultarProducto.setToolTipText("¡ Necesitas ingresar el codigo de barras para la consulta de un producto !");
        botonConsultarMaxMin.setToolTipText("¡ Necesitas ingresar el codigo de barras para la consulta del maximo y minimo de un producto !");
        botonConsultarPrecio.setToolTipText("¡ Necesitas ingresar una fecha para la filtracion de los precios del producto !");
        botonConsultarContacto.setToolTipText("¡ Necesitas seleccionar la CURP de una persona para la consulta !");
        botonConsultarPersona.setToolTipText("¡ Necesitas ingresar la CURP de una persona para su consulta !");
        botonConsultarEmpleado.setToolTipText("¡ Necesitas seleccionar la CURP de una persona para la consulta !");
        botonConsultarProveedor.setToolTipText("¡ Necesitas ingresar el nombre del proveedor para su consulta !");
        botonConsultarRegalo.setToolTipText("¡ Necesitas ingresar una fecha para la filtracion de los regalos de los zapatos !");
        botonConsultarPagoEmpleado.setToolTipText("¡ Necesitas ingresar una fecha para la filtracion de los pagos de los empleados !");
        
        txtTotalVenta.setText("0.0");
        
        Intercambio xch = new Intercambio();
        xch.iniciarModeloTabla(modeloIntercambio, tableIntercambio);
        
        TipoPago tp = new TipoPago();
        DefaultComboBoxModel modelTipoPagoContrato = new DefaultComboBoxModel(tp.mostrarTipoPago());
        cbTipoPagoEmpleado.setModel(modelTipoPagoContrato);
        
        Devolucion dev = new Devolucion();
        dev.iniciarModeloTabla(modeloDevolucion, tableDevolucion);
        
        Venta venta = new Venta();
        venta.iniciarModeloTabla(modeloVenta, tableVenta);
        venta.iniciarModeloTablaPago(modeloPago, tablePago);
       
        Estado estado = new Estado();
        
        DefaultComboBoxModel modelEstadoProveedor = new DefaultComboBoxModel(estado.mostrarEstados());
        DefaultComboBoxModel modelEstadoSucursal = new DefaultComboBoxModel(estado.mostrarEstados());
        DefaultComboBoxModel modelEstadoPersona = new DefaultComboBoxModel(estado.mostrarEstados());
        cbEstadoSucursal.setModel(modelEstadoSucursal);
        cbEstadoProveedor.setModel(modelEstadoProveedor);
        cbEstado.setModel(modelEstadoPersona);
        
        Sucursal sucursal = new Sucursal();
        sucursal.mostrarSucursal(modelo,tableSucursal);
        
        Calle calle = new Calle();
        DefaultComboBoxModel modelCalleServicio = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleResurtir = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleMinmax = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleVenta = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleInventario = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleMaximoMinimo = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleHorario = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleRegalo = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleSaleInter = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleEntraInter = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleCambio = new DefaultComboBoxModel(calle.mostrarCalles());
        DefaultComboBoxModel modelCalleDevolver = new DefaultComboBoxModel(calle.mostrarCalles());
        cbSucursalDevolver.setModel(modelCalleDevolver);
        cbSucursalCambio.setModel(modelCalleCambio);
        cbSucursalServicio.setModel(modelCalleServicio);
        cbSucursalResurtir.setModel(modelCalleResurtir);
        cbSucursalMaxMin.setModel(modelCalleMinmax);
        cbSucursalVenta.setModel(modelCalleVenta);
        cbSucursalInventario.setModel(modelCalleInventario);
        cbSucursalMaximoMinimoB.setModel(modelCalleMaximoMinimo);
        cbSucursalHorarioH.setModel(modelCalleHorario);
        cbSucursalRegaloR.setModel(modelCalleRegalo);
        cbSucursalSaleIntercambio.setModel(modelCalleSaleInter);
        cbSucursalEntraIntercambio.setModel(modelCalleEntraInter);
        
        SucursalServicio sucser = new SucursalServicio();
        sucser.mostrarSucursalServicio(modeloSucursalServicio, tableServicioSucursal);
        
        Servicio servicio = new Servicio();
        DefaultComboBoxModel modelServicio = new DefaultComboBoxModel(servicio.mostrarServicios());
        cbServicio.setModel(modelServicio);
        
        Proveedor proveedor = new Proveedor();
        proveedor.mostrarProveedor(modeloProveedor, tableProveedor);
        
        Persona persona = new Persona();
        persona.mostrarPersona(modeloPersona, tablePersona);
        
        Resurtir res = new Resurtir();
        res.iniciarModeloTabla(modeloResurtir, tableResurtir);
        
        Contacto contacto = new Contacto();
        contacto.mostrarContacto(modeloContacto, tableContacto);
        
        Curp curp = new Curp();
        DefaultComboBoxModel modelCurpContacto = new DefaultComboBoxModel(curp.mostrarCurps());
        DefaultComboBoxModel modelCurpEmpleado = new DefaultComboBoxModel(curp.mostrarCurps());
        cbCurpContacto.setModel(modelCurpContacto);
        cbCurpEmpleado.setModel(modelCurpEmpleado);
       
        Contrato contrato = new Contrato();
        //DefaultComboBoxModel modelContratoHorario = new DefaultComboBoxModel(contrato.mostrarContratos());
        DefaultComboBoxModel modelContratoChecador = new DefaultComboBoxModel(contrato.mostrarContratos());
        DefaultComboBoxModel modelContratoPago = new DefaultComboBoxModel(contrato.mostrarContratos());
        //DefaultComboBoxModel modelContratoVenta = new DefaultComboBoxModel(contrato.mostrarContratos());
        DefaultComboBoxModel modelContratoCambio = new DefaultComboBoxModel(contrato.mostrarContratos());
        DefaultComboBoxModel modelContratoInter = new DefaultComboBoxModel(contrato.mostrarContratos());
        
        cbContratoIntercambio.setModel(modelContratoInter);
        cbContratoChecador.setModel(modelContratoChecador);
        cbContratoPagoEmpleado.setModel(modelContratoPago);
        //cbContratoVenta.setModel(modelContratoVenta);
        cbContratoCambio.setModel(modelContratoCambio);
        //cbSucursalRegaloR.setModel(modelContratoRegalo);
        
        
        Puesto puesto = new Puesto();
        DefaultComboBoxModel modelPuesto = new DefaultComboBoxModel(puesto.mostrarPuestos());
        cbPuestoEmpleado.setModel(modelPuesto);
        
        DefaultComboBoxModel modelCalle = new DefaultComboBoxModel(calle.mostrarCalles());
        cbSucursalEmpleado.setModel(modelCalle);
        
        Empleado empleado = new Empleado();
        empleado.mostrarEmpleado(modeloEmpleado, tableEmpleado);
        
        Checador checador = new Checador();
        checador.mostrarChecador(modeloChecador, tableChecador);
        
        PagoEmpleado pe = new PagoEmpleado();
        pe.mostrarPagoEmpleado(modeloPagoEmpleado, tablePagoEmpleado);
        
        Tipo tipo = new Tipo();
        DefaultComboBoxModel modelTipo = new DefaultComboBoxModel(tipo.mostrarTipos());
        cbTipoProducto.setModel(modelTipo);
        
        Temporada temporada = new Temporada();
        DefaultComboBoxModel modelTemporada = new DefaultComboBoxModel(temporada.mostrarTemporadas());
        cbTemporadaProducto.setModel(modelTemporada);
        
        Material material = new Material();
        DefaultComboBoxModel modelMaterial = new DefaultComboBoxModel(material.mostrarMateriales());
        cbMaterialProducto.setModel(modelMaterial);
        
        Categoria categoria = new Categoria();
        DefaultComboBoxModel modelCategoria = new DefaultComboBoxModel(categoria.mostrarCategorias());
        cbCategoriaProducto.setModel(modelCategoria);
        
        clases.Color color = new clases.Color();
        DefaultComboBoxModel modelColor = new DefaultComboBoxModel(color.mostrarColores());
        cbColorProducto.setModel(modelColor);
        
        Producto pro = new Producto();
        pro.mostrarProducto(modeloProducto, tableProducto);
        
        Provee prov = new Provee();
        DefaultComboBoxModel modelProvee = new DefaultComboBoxModel(prov.mostrarProveedores());
        DefaultComboBoxModel modelProveeDevolver = new DefaultComboBoxModel(prov.mostrarProveedores());
        cbProveedorDevolver.setModel(modelProveeDevolver);
        cbProveedorResurtir.setModel(modelProvee);
        
        /*MinimoMaximo mm = new MinimoMaximo();
        mm.mostrarMinimoMaximo(modeloMinmax, tableMinMax);*/
        
        Precio precio = new Precio();
        precio.mostrarPrecio(modeloPrecio, tablePrecio);
        
        FormaPago fp = new FormaPago();
        DefaultComboBoxModel modelFormaPagoVenta = new DefaultComboBoxModel(fp.mostrarFormasPago());
        DefaultComboBoxModel modelFormaPagoCambio = new DefaultComboBoxModel(fp.mostrarFormasPago());
        cbFormaPagoPago.setModel(modelFormaPagoVenta);
        cbFormaPagoCambio.setModel(modelFormaPagoCambio);
        
                
        Horario hor = new Horario();
        hor.mostrarHorario(modeloHorario, tableHorario);
        
        Regalo regalo = new Regalo();
        regalo.iniciarModeloTabla(modeloRegalo, tableRegalo);
        
        Cambio cam = new Cambio();
        cam.iniciarModeloTabla(modeloCambio, tableCambio);
        cam.iniciarModeloTablaPagoCambio(modeloCambioPago, tableCambioPago);
    }

    public void mostrarUsuarioActual(String nombre){
        this.labelUser.setText(nombre);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        labelUser = new javax.swing.JLabel();
        labelUserImage = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        panUno = new javax.swing.JPanel();
        btnSucursal = new rsbuttom.RSButtonMetro();
        btnVenta = new rsbuttom.RSButtonMetro();
        btnInevntario = new rsbuttom.RSButtonMetro();
        btnEmpleado = new rsbuttom.RSButtonMetro();
        btnMas = new rsbuttom.RSButtonMetro();
        rSButtonMetro2 = new rsbuttom.RSButtonMetro();
        btnReporte = new rsbuttom.RSButtonMetro();
        rSPanelsSlider1 = new rojerusan.RSPanelsSlider();
        panelSucursal = new javax.swing.JTabbedPane();
        panelSucursalRegistro = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        cbCodigoPostalSucursal = new javax.swing.JComboBox<>();
        cbMunicipioSucursal = new javax.swing.JComboBox<>();
        cbEstadoSucursal = new javax.swing.JComboBox<>();
        cbColoniaSucursal = new javax.swing.JComboBox<>();
        txtCalleSucursal = new javax.swing.JTextField();
        spNumeroSucursal = new javax.swing.JSpinner();
        cbOrientacionSucursal = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSucursal = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        b = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarSucursal = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarSucursal = new rojerusan.RSMaterialButtonRectangle();
        botonModificarSucursal = new rojerusan.RSMaterialButtonRectangle();
        panelSucursalServicio = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cbServicio = new javax.swing.JComboBox<>();
        cbSucursalServicio = new javax.swing.JComboBox<>();
        dataFechaServicio = new com.toedter.calendar.JDateChooser();
        txtMontoServicio = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableServicioSucursal = new javax.swing.JTable();
        botonAgregarSucursalServicio = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarSucursalServicio = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarSucursalServicio = new rojerusan.RSMaterialButtonRectangle();
        botonModificarSucursalServicio = new rojerusan.RSMaterialButtonRectangle();
        botonServicioMas = new rojerusan.RSMaterialButtonCircle();
        panelVenta = new javax.swing.JTabbedPane();
        panelVentaVenta = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtCantidadVenta = new javax.swing.JTextField();
        cbContratoVenta = new javax.swing.JComboBox<>();
        cbSucursalVenta = new javax.swing.JComboBox<>();
        jLabel90 = new javax.swing.JLabel();
        panelBotonesVenta = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        txtCodigoBarrasVenta = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        txtCantidadPago = new javax.swing.JTextField();
        txtRestantePago = new javax.swing.JTextField();
        cbFormaPagoPago = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        recibo = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tableVenta = new javax.swing.JTable();
        jLabel89 = new javax.swing.JLabel();
        txtFolioVenta = new javax.swing.JTextField();
        marcoTicket = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        txtTotalVenta = new javax.swing.JTextField();
        jLabel88 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        tablePago = new javax.swing.JTable();
        jLabel127 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        txtFechaTicket = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        botonAgregarVenta = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarVenta = new rojerusan.RSMaterialButtonRectangle();
        botonLimpiarVenta = new rojerusan.RSMaterialButtonRectangle();
        botonAgregarPago = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarPago = new rojerusan.RSMaterialButtonRectangle();
        botonImprimirTicket = new rojerusan.RSMaterialButtonRectangle();
        panelInventario = new javax.swing.JTabbedPane();
        panelInventarioInventario = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableInventario = new javax.swing.JTable();
        botonActualizarInventario = new rojerusan.RSMaterialButtonRectangle();
        cbSucursalInventario = new javax.swing.JComboBox<>();
        jLabel125 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        txtCodigoBarrasInventario = new javax.swing.JTextField();
        botonBusquedaAvanzada = new rojerusan.RSMaterialButtonRectangle();
        panelProducto = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtCodigoBarrasProducto = new javax.swing.JTextField();
        txtNombreProducto = new javax.swing.JTextField();
        cbTipoProducto = new javax.swing.JComboBox<>();
        txtModeloProducto = new javax.swing.JTextField();
        cbMaterialProducto = new javax.swing.JComboBox<>();
        cbCategoriaProducto = new javax.swing.JComboBox<>();
        cbColorProducto = new javax.swing.JComboBox<>();
        cbTemporadaProducto = new javax.swing.JComboBox<>();
        panelBotonesProducto = new javax.swing.JPanel();
        botonAgregarProducto = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarProducto = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarSucursal1 = new rojerusan.RSMaterialButtonRectangle();
        botonModificarSucursal1 = new rojerusan.RSMaterialButtonRectangle();
        txtMarcaProducto = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableProducto = new javax.swing.JTable();
        panelImagenProducto = new javax.swing.JPanel();
        labelImagen = new javax.swing.JLabel();
        botonAgregarMaterial = new rojerusan.RSMaterialButtonCircle();
        botonAgregarColor = new rojerusan.RSMaterialButtonCircle();
        botonAgregarCategoria = new rojerusan.RSMaterialButtonCircle();
        botonAgregarTipo = new rojerusan.RSMaterialButtonCircle();
        panelResurtir = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableResurtir = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        txtPPPRenRes = new javax.swing.JTextField();
        txtCodigoBarrasRenRes = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        spBajaRenRes = new javax.swing.JSpinner();
        spCantidadRenRes = new javax.swing.JSpinner();
        jLabel77 = new javax.swing.JLabel();
        dataFechaCaducidadRenRes = new com.toedter.calendar.JDateChooser();
        txtNumeroCalzadoRenres = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        botonLimpiarResurtir = new rojerusan.RSMaterialButtonRectangle();
        botonAgregarResurtir = new rojerusan.RSMaterialButtonRectangle();
        botonConcluirResurtir1 = new rojerusan.RSMaterialButtonRectangle();
        botoneliminarResurtir = new rojerusan.RSMaterialButtonRectangle();
        botonLimpiarTodoResurtir = new rojerusan.RSMaterialButtonRectangle();
        botonMostraNumeros = new rojerusan.RSMaterialButtonCircle();
        jPanel5 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        cbProveedorResurtir = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        cbSucursalResurtir = new javax.swing.JComboBox<>();
        txtfacturaResurtir = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        dataFechaResurtir = new com.toedter.calendar.JDateChooser();
        panelDevolverResurtir = new javax.swing.JPanel();
        jLabel119 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        txtFacturaDevolver = new javax.swing.JTextField();
        cbProveedorDevolver = new javax.swing.JComboBox<>();
        cbSucursalDevolver = new javax.swing.JComboBox<>();
        jScrollPane22 = new javax.swing.JScrollPane();
        tableDevolucion = new javax.swing.JTable();
        botonDevolverProducto = new rojerusan.RSMaterialButtonRectangle();
        txtCodigoBarrasDevolucion = new javax.swing.JTextField();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        txtCantidadDevolucion = new javax.swing.JTextField();
        botonFiltrarDevolucion = new rojerusan.RSMaterialButtonRectangle();
        botonDevolverTodo = new rojerusan.RSMaterialButtonRectangle();
        panelMinimoMaximo = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableMinMax = new javax.swing.JTable();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        cbSucursalMaxMin = new javax.swing.JComboBox<>();
        spMinimo = new javax.swing.JSpinner();
        spMaximo = new javax.swing.JSpinner();
        txtCodigoBarrasMaximo = new javax.swing.JTextField();
        panelBotonesMaxMin = new javax.swing.JPanel();
        botonAgregarMaxMin = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarMaxMin = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarMaxMin = new rojerusan.RSMaterialButtonRectangle();
        botonModificarMaxMin = new rojerusan.RSMaterialButtonRectangle();
        cbSucursalMaximoMinimoB = new javax.swing.JComboBox<>();
        jLabel130 = new javax.swing.JLabel();
        panelPrecio = new javax.swing.JPanel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        txtCodigoBarrasPrecio = new javax.swing.JTextField();
        txtPrecioPrecio = new javax.swing.JTextField();
        dataFechaPrecio = new com.toedter.calendar.JDateChooser();
        jScrollPane17 = new javax.swing.JScrollPane();
        tablePrecio = new javax.swing.JTable();
        panelBotonesPrecio = new javax.swing.JPanel();
        botonAgregarPrecio = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarPrecio = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarPrecio = new rojerusan.RSMaterialButtonRectangle();
        botonModificarPrecio = new rojerusan.RSMaterialButtonRectangle();
        panelEmpleados = new javax.swing.JTabbedPane();
        panelPersona = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePersona = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAPaternoPersona = new javax.swing.JTextField();
        txtAMaternoPersona = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCurpPersona = new javax.swing.JTextField();
        dataFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox<>();
        cbMunicipio = new javax.swing.JComboBox<>();
        cbCodigoPostal = new javax.swing.JComboBox<>();
        cbColonia = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        spNumero = new javax.swing.JSpinner();
        cbOrientacion = new javax.swing.JComboBox<>();
        panelBotonesPersona = new javax.swing.JPanel();
        botonAgregarPersona = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarPersona = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarPersona = new rojerusan.RSMaterialButtonRectangle();
        botonModificarPersona = new rojerusan.RSMaterialButtonRectangle();
        cbEstado = new javax.swing.JComboBox<>();
        txtCalle = new javax.swing.JTextField();
        panelFotoEmpleado = new javax.swing.JPanel();
        labelFotoPersona = new javax.swing.JLabel();
        panelEmpleado = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        cbCurpEmpleado = new javax.swing.JComboBox<>();
        cbPuestoEmpleado = new javax.swing.JComboBox<>();
        txtSueldoEmpleado = new javax.swing.JTextField();
        dataFechaInicioEmpleado = new com.toedter.calendar.JDateChooser();
        dataFechaFinEmpleado = new com.toedter.calendar.JDateChooser();
        cbSucursalEmpleado = new javax.swing.JComboBox<>();
        cbTurnoEmpleado = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableEmpleado = new javax.swing.JTable();
        panelBotonesEmpleado = new javax.swing.JPanel();
        botonAgregarEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonModificarEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonAgregarPuesto = new rojerusan.RSMaterialButtonCircle();
        PanelContacto = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        cbMedioContacto = new javax.swing.JComboBox<>();
        txtDescripcionContacto = new javax.swing.JTextField();
        cbCurpContacto = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableContacto = new javax.swing.JTable();
        panelBotonesContacto = new javax.swing.JPanel();
        botonAgregarContacto = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarContacto = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarContacto = new rojerusan.RSMaterialButtonRectangle();
        botonModificarContacto = new rojerusan.RSMaterialButtonRectangle();
        panelHorario = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        cbContratoHorario = new javax.swing.JComboBox<>();
        dateDiaDescanso = new com.toedter.calendar.JDateChooser();
        txtHoraEntrada = new javax.swing.JTextField();
        txtHoraSalida = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        tableHorario = new javax.swing.JTable();
        botonAgregarHorario = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarHorario = new rojerusan.RSMaterialButtonRectangle();
        txtHoraComida = new javax.swing.JTextField();
        jLabel131 = new javax.swing.JLabel();
        cbSucursalHorarioH = new javax.swing.JComboBox<>();
        panelChecador = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        cbContratoChecador = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableChecador = new javax.swing.JTable();
        botonChecarEntrada = new rojerusan.RSMaterialButtonRectangle();
        botonChecarSalida = new rojerusan.RSMaterialButtonRectangle();
        panelPagoEmpleados = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        cbContratoPagoEmpleado = new javax.swing.JComboBox<>();
        txtMontoPagoEmpleado = new javax.swing.JTextField();
        dateFechaPagoEmpleado = new com.toedter.calendar.JDateChooser();
        jScrollPane16 = new javax.swing.JScrollPane();
        tablePagoEmpleado = new javax.swing.JTable();
        panelBotonesPagoEmpleado = new javax.swing.JPanel();
        botonAgregarPagoEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarPagoEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarPagoEmpleado = new rojerusan.RSMaterialButtonRectangle();
        botonModificarPagoEmpleado = new rojerusan.RSMaterialButtonRectangle();
        jLabel132 = new javax.swing.JLabel();
        cbTipoPagoEmpleado = new javax.swing.JComboBox<>();
        panelProveedor = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        txtCalleProveedor = new javax.swing.JTextField();
        cbCodigoPostalProveedor = new javax.swing.JComboBox<>();
        cbEstadoProveedor = new javax.swing.JComboBox<>();
        cbMunicipioProveedor = new javax.swing.JComboBox<>();
        cbColoniaProveedor = new javax.swing.JComboBox<>();
        cbOrientacionProveedor = new javax.swing.JComboBox<>();
        txtNumeroProveedor = new javax.swing.JTextField();
        spNumeroProveedor = new javax.swing.JSpinner();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableProveedor = new javax.swing.JTable();
        panelBotonesProveedor = new javax.swing.JPanel();
        botonAgregarProveedor = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarProveedor = new rojerusan.RSMaterialButtonRectangle();
        botonEliminarProveedor = new rojerusan.RSMaterialButtonRectangle();
        botonModificarProveedor = new rojerusan.RSMaterialButtonRectangle();
        panelMas = new javax.swing.JTabbedPane();
        panelConsultas = new javax.swing.JPanel();
        jLabel146 = new javax.swing.JLabel();
        botonZapatoCaducado = new rsbuttom.RSButtonMetro();
        botonZapatoMasVendido = new rsbuttom.RSButtonMetro();
        botonSucursalIngresoVenta = new rsbuttom.RSButtonMetro();
        botonEmpleadoMasVende = new rsbuttom.RSButtonMetro();
        botonZapatoRegala = new rsbuttom.RSButtonMetro();
        botonZapatosResurten = new rsbuttom.RSButtonMetro();
        botonSucursalGasta = new rsbuttom.RSButtonMetro();
        botonProveedorCompra = new rsbuttom.RSButtonMetro();
        botonFormaPagoUsada = new rsbuttom.RSButtonMetro();
        botonZapatoNoVendido = new rsbuttom.RSButtonMetro();
        botonZapatoCompraAlta = new rsbuttom.RSButtonMetro();
        botonZapatoMasIntercambiado = new rsbuttom.RSButtonMetro();
        panelReportes = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle5 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle6 = new rojerusan.RSMaterialButtonRectangle();
        panelRegalo = new javax.swing.JPanel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        txtCodigoBarrasRegalo = new javax.swing.JTextField();
        jScrollPane21 = new javax.swing.JScrollPane();
        tableRegalo = new javax.swing.JTable();
        panelBotonesPersona1 = new javax.swing.JPanel();
        botonAgregarRegalo = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarRegalo = new rojerusan.RSMaterialButtonRectangle();
        spCantidadRegalo = new javax.swing.JSpinner();
        jLabel133 = new javax.swing.JLabel();
        cbSucursalRegaloR = new javax.swing.JComboBox<>();
        cbContratoRegaloR = new javax.swing.JComboBox<>();
        botonDetallesIntercambio1 = new rojerusan.RSMaterialButtonRectangle();
        panelIntercambio = new javax.swing.JPanel();
        jLabel106 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        txtCodigoBarrasIntercambio = new javax.swing.JTextField();
        cbSucursalSaleIntercambio = new javax.swing.JComboBox<>();
        cbSucursalEntraIntercambio = new javax.swing.JComboBox<>();
        cbContratoIntercambio = new javax.swing.JComboBox<>();
        dateFechaIntercambio = new com.toedter.calendar.JDateChooser();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableIntercambio = new javax.swing.JTable();
        botonAgregarSucursalServicio1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel138 = new javax.swing.JLabel();
        txtCantidadIntercambio = new javax.swing.JTextField();
        botonDetallesIntercambio = new rojerusan.RSMaterialButtonRectangle();
        panelCambio = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        txtFolioCambio = new javax.swing.JTextField();
        txtSaleCambio = new javax.swing.JTextField();
        txtEntraCambio = new javax.swing.JTextField();
        dataFechaCambio = new com.toedter.calendar.JDateChooser();
        cbContratoCambio = new javax.swing.JComboBox<>();
        jScrollPane14 = new javax.swing.JScrollPane();
        tableCambio = new javax.swing.JTable();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtDescripcionCambio = new javax.swing.JTextArea();
        cbFormaPagoCambio = new javax.swing.JComboBox<>();
        txtMontoCambioPago = new javax.swing.JTextField();
        jScrollPane20 = new javax.swing.JScrollPane();
        tableCambioPago = new javax.swing.JTable();
        jLabel139 = new javax.swing.JLabel();
        cbSucursalCambio = new javax.swing.JComboBox<>();
        panelBotones1 = new javax.swing.JPanel();
        botonAgregarColor2 = new rojerusan.RSMaterialButtonRectangle();
        botonConsultarColor = new rojerusan.RSMaterialButtonRectangle();
        botonAgregarColor1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel140 = new javax.swing.JLabel();
        txtNumeroCambio = new javax.swing.JTextField();
        botonDetallesCambio = new rojerusan.RSMaterialButtonRectangle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 153));
        jPanel1.setEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 51, 153));
        jButton1.setForeground(new java.awt.Color(0, 51, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 16, -1, -1));

        labelUser.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelUser.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(labelUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 30, 140, 29));

        labelUserImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user_menu.png"))); // NOI18N
        jPanel1.add(labelUserImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 20, 50, 50));

        lblPassword.setEnabled(false);
        jPanel1.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, -1, -1));

        lblUser.setEnabled(false);
        jPanel1.add(lblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, -1, -1));

        panelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 80));

        panUno.setBackground(new java.awt.Color(70, 70, 70));
        panUno.setForeground(new java.awt.Color(204, 204, 204));
        panUno.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSucursal.setBackground(new java.awt.Color(70, 70, 70));
        btnSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/store.png"))); // NOI18N
        btnSucursal.setText("    SUCURSAL");
        btnSucursal.setColorHover(new java.awt.Color(0, 0, 0));
        btnSucursal.setColorNormal(new java.awt.Color(70, 70, 70));
        btnSucursal.setColorPressed(new java.awt.Color(51, 204, 0));
        btnSucursal.setSelected(true);
        btnSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSucursalActionPerformed(evt);
            }
        });
        panUno.add(btnSucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 191, 90));

        btnVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sell.png"))); // NOI18N
        btnVenta.setText("    VENTA");
        btnVenta.setColorHover(new java.awt.Color(0, 0, 0));
        btnVenta.setColorNormal(new java.awt.Color(70, 70, 70));
        btnVenta.setColorPressed(new java.awt.Color(51, 204, 0));
        btnVenta.setSelected(true);
        btnVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnVentaMousePressed(evt);
            }
        });
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });
        panUno.add(btnVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 191, 90));

        btnInevntario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/inventory.png"))); // NOI18N
        btnInevntario.setText("    INVENTARIO");
        btnInevntario.setColorHover(new java.awt.Color(0, 0, 0));
        btnInevntario.setColorNormal(new java.awt.Color(70, 70, 70));
        btnInevntario.setColorPressed(new java.awt.Color(51, 204, 0));
        btnInevntario.setSelected(true);
        btnInevntario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInevntarioActionPerformed(evt);
            }
        });
        panUno.add(btnInevntario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 191, 90));

        btnEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/employee.png"))); // NOI18N
        btnEmpleado.setText("    EMPLEADO");
        btnEmpleado.setColorHover(new java.awt.Color(0, 0, 0));
        btnEmpleado.setColorNormal(new java.awt.Color(70, 70, 70));
        btnEmpleado.setColorPressed(new java.awt.Color(51, 204, 0));
        btnEmpleado.setSelected(true);
        btnEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadoActionPerformed(evt);
            }
        });
        panUno.add(btnEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 191, 90));

        btnMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/provider.png"))); // NOI18N
        btnMas.setText("PROVEEDOR");
        btnMas.setColorHover(new java.awt.Color(0, 0, 0));
        btnMas.setColorNormal(new java.awt.Color(70, 70, 70));
        btnMas.setColorPressed(new java.awt.Color(51, 204, 0));
        btnMas.setSelected(true);
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });
        panUno.add(btnMas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 191, 90));

        rSButtonMetro2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exit.png"))); // NOI18N
        rSButtonMetro2.setText("    SALIR");
        rSButtonMetro2.setColorHover(new java.awt.Color(0, 0, 0));
        rSButtonMetro2.setColorNormal(new java.awt.Color(70, 70, 70));
        rSButtonMetro2.setColorPressed(new java.awt.Color(51, 204, 0));
        rSButtonMetro2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonMetro2ActionPerformed(evt);
            }
        });
        panUno.add(rSButtonMetro2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 191, 90));

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/more.png"))); // NOI18N
        btnReporte.setText(" MAS");
        btnReporte.setToolTipText("");
        btnReporte.setColorHover(new java.awt.Color(0, 0, 0));
        btnReporte.setColorNormal(new java.awt.Color(70, 70, 70));
        btnReporte.setColorPressed(new java.awt.Color(51, 204, 0));
        btnReporte.setSelected(true);
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        panUno.add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 190, 90));

        panelPrincipal.add(panUno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 72, -1, 680));

        rSPanelsSlider1.setBackground(new java.awt.Color(255, 255, 255));

        panelSucursal.setBackground(new java.awt.Color(255, 255, 255));
        panelSucursal.setForeground(new java.awt.Color(0, 0, 0));
        panelSucursal.setName("panelSucursal"); // NOI18N

        panelSucursalRegistro.setBackground(new java.awt.Color(255, 255, 255));
        panelSucursalRegistro.setForeground(new java.awt.Color(0, 0, 0));
        panelSucursalRegistro.setName("panelSucursalRegistro"); // NOI18N

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Municipio:");

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Estado:");

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Codigo Postal:");

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));
        jLabel23.setText("Colonia:");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Calle");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("Numero:");

        jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("Orientacion:");

        cbCodigoPostalSucursal.setBackground(new java.awt.Color(255, 255, 255));
        cbCodigoPostalSucursal.setForeground(new java.awt.Color(0, 0, 0));
        cbCodigoPostalSucursal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCodigoPostalSucursalItemStateChanged(evt);
            }
        });

        cbMunicipioSucursal.setBackground(new java.awt.Color(255, 255, 255));
        cbMunicipioSucursal.setForeground(new java.awt.Color(0, 0, 0));
        cbMunicipioSucursal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMunicipioSucursalItemStateChanged(evt);
            }
        });
        cbMunicipioSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMunicipioSucursalActionPerformed(evt);
            }
        });

        cbEstadoSucursal.setBackground(new java.awt.Color(255, 255, 255));
        cbEstadoSucursal.setForeground(new java.awt.Color(0, 0, 0));
        cbEstadoSucursal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstadoSucursalItemStateChanged(evt);
            }
        });

        cbColoniaSucursal.setBackground(new java.awt.Color(255, 255, 255));
        cbColoniaSucursal.setForeground(new java.awt.Color(0, 0, 0));

        txtCalleSucursal.setBackground(new java.awt.Color(255, 255, 255));
        txtCalleSucursal.setForeground(new java.awt.Color(0, 0, 0));
        txtCalleSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalleSucursalActionPerformed(evt);
            }
        });

        cbOrientacionSucursal.setBackground(new java.awt.Color(255, 255, 255));
        cbOrientacionSucursal.setForeground(new java.awt.Color(0, 0, 0));
        cbOrientacionSucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Seleccione -", "Norte", "Sur", "Poniente", "Oriente" }));
        cbOrientacionSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOrientacionSucursalActionPerformed(evt);
            }
        });

        tableSucursal.setBackground(new java.awt.Color(204, 204, 255));
        tableSucursal.setForeground(new java.awt.Color(0, 0, 0));
        tableSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Calle", "Numero", "Orientacion", "Colonia", "Codigo Postal", "Municipio", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSucursalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableSucursal);

        jLabel27.setBackground(new java.awt.Color(0, 0, 0));
        jLabel27.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/sucursal.png"))); // NOI18N
        jLabel27.setText("SUCURSALES");

        panelBotones.setBackground(new java.awt.Color(255, 255, 255));
        panelBotones.setForeground(new java.awt.Color(255, 255, 255));

        b.setBackground(new java.awt.Color(0, 161, 112));
        b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        b.setText("Agregar");
        b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bActionPerformed(evt);
            }
        });

        botonConsultarSucursal.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarSucursal.setText("Consultar");
        botonConsultarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarSucursalActionPerformed(evt);
            }
        });

        botonEliminarSucursal.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarSucursal.setText("ELIMINAR");
        botonEliminarSucursal.setEnabled(false);
        botonEliminarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarSucursalActionPerformed(evt);
            }
        });

        botonModificarSucursal.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarSucursal.setText("modificar");
        botonModificarSucursal.setEnabled(false);
        botonModificarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarSucursalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(b, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonConsultarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonEliminarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonModificarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout panelSucursalRegistroLayout = new javax.swing.GroupLayout(panelSucursalRegistro);
        panelSucursalRegistro.setLayout(panelSucursalRegistroLayout);
        panelSucursalRegistroLayout.setHorizontalGroup(
            panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbEstadoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cbMunicipioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                                    .addComponent(jLabel22)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbCodigoPostalSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(117, 117, 117)
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                                .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26))
                                .addGap(83, 83, 83)
                                .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbOrientacionSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCalleSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spNumeroSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(62, 62, 62)
                                .addComponent(cbColoniaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(117, 117, 117)
                        .addComponent(jLabel24)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1051, Short.MAX_VALUE)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        panelSucursalRegistroLayout.setVerticalGroup(
            panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelSucursalRegistroLayout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(cbEstadoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(txtCalleSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cbMunicipioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(spNumeroSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(cbCodigoPostalSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(cbOrientacionSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(panelSucursalRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(cbColoniaSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(277, Short.MAX_VALUE))
        );

        panelSucursal.addTab("Sucursal", panelSucursalRegistro);

        panelSucursalServicio.setBackground(new java.awt.Color(255, 255, 255));
        panelSucursalServicio.setName("panelSucursalServicio"); // NOI18N

        jLabel28.setBackground(new java.awt.Color(0, 0, 0));
        jLabel28.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 0));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/servicios.png"))); // NOI18N
        jLabel28.setText("Servicios");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Sucursal:");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 0));
        jLabel29.setText("Servicio:");

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Fecha:");

        jLabel31.setForeground(new java.awt.Color(0, 0, 0));

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setText("Monto:");

        cbServicio.setBackground(new java.awt.Color(255, 255, 255));
        cbServicio.setForeground(new java.awt.Color(0, 0, 0));

        cbSucursalServicio.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalServicio.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalServicio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        dataFechaServicio.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaServicio.setForeground(new java.awt.Color(0, 0, 0));

        txtMontoServicio.setBackground(new java.awt.Color(255, 255, 255));
        txtMontoServicio.setForeground(new java.awt.Color(0, 0, 0));

        tableServicioSucursal.setBackground(new java.awt.Color(204, 204, 255));
        tableServicioSucursal.setForeground(new java.awt.Color(0, 0, 0));
        tableServicioSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Sucursal", "Servicio", "Fecha", "Monto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableServicioSucursal);

        botonAgregarSucursalServicio.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarSucursalServicio.setText("Agregar");
        botonAgregarSucursalServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarSucursalServicioActionPerformed(evt);
            }
        });

        botonConsultarSucursalServicio.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarSucursalServicio.setText("Consultar");
        botonConsultarSucursalServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarSucursalServicioActionPerformed(evt);
            }
        });

        botonEliminarSucursalServicio.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarSucursalServicio.setText("ELIMINAR");
        botonEliminarSucursalServicio.setEnabled(false);
        botonEliminarSucursalServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarSucursalServicioActionPerformed(evt);
            }
        });

        botonModificarSucursalServicio.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarSucursalServicio.setText("modificar");
        botonModificarSucursalServicio.setEnabled(false);
        botonModificarSucursalServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarSucursalServicioActionPerformed(evt);
            }
        });

        botonServicioMas.setText("+");
        botonServicioMas.setToolTipText("Agregar Servicios");
        botonServicioMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonServicioMasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSucursalServicioLayout = new javax.swing.GroupLayout(panelSucursalServicio);
        panelSucursalServicio.setLayout(panelSucursalServicioLayout);
        panelSucursalServicioLayout.setHorizontalGroup(
            panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonConsultarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonAgregarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonEliminarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonModificarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel32)
                                .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSucursalServicioLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSucursalServicioLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addGap(42, 42, 42)
                                        .addComponent(cbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel31)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dataFechaServicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtMontoServicio))))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonServicioMas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelSucursalServicioLayout.setVerticalGroup(
            panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonServicioMas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addGap(16, 16, 16)
                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                                    .addGap(16, 16, 16)
                                    .addComponent(jLabel30)))
                            .addComponent(dataFechaServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelSucursalServicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel32)
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSucursalServicioLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMontoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)))
                        .addComponent(botonAgregarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonConsultarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonEliminarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonModificarSucursalServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelSucursalServicioLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(287, Short.MAX_VALUE))
        );

        panelSucursal.addTab("Servicios Sucursales", panelSucursalServicio);

        rSPanelsSlider1.add(panelSucursal, "card8");

        panelVenta.setBackground(new java.awt.Color(255, 255, 255));
        panelVenta.setForeground(new java.awt.Color(0, 0, 0));
        panelVenta.setName("panelVenta"); // NOI18N

        panelVentaVenta.setBackground(new java.awt.Color(255, 255, 255));
        panelVentaVenta.setName("panelVentaVenta"); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/venta.png"))); // NOI18N
        jLabel1.setText("VENTA");

        jLabel85.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 0, 0));
        jLabel85.setText("Cantidad:");

        jLabel86.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(0, 0, 0));
        jLabel86.setText("Sucursal:");

        txtCantidadVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidadVenta.setForeground(new java.awt.Color(0, 0, 0));

        cbContratoVenta.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoVenta.setForeground(new java.awt.Color(0, 0, 0));
        cbContratoVenta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbContratoVentaItemStateChanged(evt);
            }
        });
        cbContratoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbContratoVentaActionPerformed(evt);
            }
        });

        cbSucursalVenta.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalVenta.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalVenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSucursalVenta.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSucursalVentaItemStateChanged(evt);
            }
        });
        cbSucursalVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSucursalVentaActionPerformed(evt);
            }
        });

        jLabel90.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(0, 0, 0));
        jLabel90.setText("Empleado:");

        panelBotonesVenta.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBotonesVentaLayout = new javax.swing.GroupLayout(panelBotonesVenta);
        panelBotonesVenta.setLayout(panelBotonesVentaLayout);
        panelBotonesVentaLayout.setHorizontalGroup(
            panelBotonesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 994, Short.MAX_VALUE)
        );
        panelBotonesVentaLayout.setVerticalGroup(
            panelBotonesVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        jLabel84.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 0, 0));
        jLabel84.setText("Codigo de Barras:");

        txtCodigoBarrasVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoBarrasVenta.setForeground(new java.awt.Color(0, 0, 0));

        jLabel87.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(0, 0, 0));
        jLabel87.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/pago.png"))); // NOI18N
        jLabel87.setText("   PAGO");

        jLabel112.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(0, 0, 0));
        jLabel112.setText("Forma Pago:");

        jLabel113.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(0, 0, 0));
        jLabel113.setText("Cantidad:");

        jLabel114.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(0, 0, 0));
        jLabel114.setText("Restante:");

        txtCantidadPago.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidadPago.setForeground(new java.awt.Color(0, 0, 0));

        txtRestantePago.setBackground(new java.awt.Color(255, 255, 255));
        txtRestantePago.setForeground(new java.awt.Color(0, 0, 0));
        txtRestantePago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRestantePagoActionPerformed(evt);
            }
        });

        cbFormaPagoPago.setBackground(new java.awt.Color(255, 255, 255));
        cbFormaPagoPago.setForeground(new java.awt.Color(0, 0, 0));
        cbFormaPagoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        recibo.setBackground(new java.awt.Color(255, 255, 255));
        recibo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        tableVenta.setBackground(new java.awt.Color(204, 204, 255));
        tableVenta.setForeground(new java.awt.Color(0, 0, 0));
        tableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo de Barras", "Nombre", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane13.setViewportView(tableVenta);

        jLabel89.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(0, 0, 0));
        jLabel89.setText("Folio:");

        txtFolioVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtFolioVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtFolioVenta.setBorder(null);

        jLabel126.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel126.setForeground(new java.awt.Color(0, 0, 0));
        jLabel126.setText("Zapateria \"Pequeños Pies\"");

        txtTotalVenta.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalVenta.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalVenta.setBorder(null);

        jLabel88.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 0, 0));
        jLabel88.setText("Total:");

        tablePago.setBackground(new java.awt.Color(204, 204, 255));
        tablePago.setForeground(new java.awt.Color(0, 0, 0));
        tablePago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Folio Ticket", "Forma Pago", "Monto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane18.setViewportView(tablePago);

        jLabel127.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jLabel127.setForeground(new java.awt.Color(0, 0, 0));
        jLabel127.setText("¡Gracias Por Tu Compra!");

        jLabel128.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(0, 0, 0));
        jLabel128.setText("Fecha:");

        txtFechaTicket.setBackground(new java.awt.Color(255, 255, 255));
        txtFechaTicket.setBorder(null);

        javax.swing.GroupLayout reciboLayout = new javax.swing.GroupLayout(recibo);
        recibo.setLayout(reciboLayout);
        reciboLayout.setHorizontalGroup(
            reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reciboLayout.createSequentialGroup()
                .addGroup(reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(reciboLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel88)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(reciboLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, reciboLayout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(jLabel126)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, reciboLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel89)
                        .addGap(18, 18, 18)
                        .addComponent(txtFolioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel128)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFechaTicket)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reciboLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reciboLayout.createSequentialGroup()
                        .addComponent(jLabel127)
                        .addGap(234, 234, 234))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reciboLayout.createSequentialGroup()
                        .addComponent(marcoTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(258, 258, 258))))
        );
        reciboLayout.setVerticalGroup(
            reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reciboLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(marcoTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel126)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaTicket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFolioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel89)
                        .addComponent(jLabel128)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(reciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel88)
                    .addComponent(txtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel127)
                .addContainerGap())
        );

        jLabel72.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 0));
        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/carrito.png"))); // NOI18N
        jLabel72.setText("PRODUCTOS");

        botonAgregarVenta.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonAgregarVenta.setText("Agregar");
        botonAgregarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarVentaActionPerformed(evt);
            }
        });

        botonEliminarVenta.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarVenta.setText("ELIMINAR");
        botonEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarVentaActionPerformed(evt);
            }
        });

        botonLimpiarVenta.setBackground(new java.awt.Color(255, 153, 0));
        botonLimpiarVenta.setText("limpiar");
        botonLimpiarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarVentaActionPerformed(evt);
            }
        });

        botonAgregarPago.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarPago.setText("Agregar");
        botonAgregarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarPagoActionPerformed(evt);
            }
        });

        botonEliminarPago.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarPago.setText("ELIMINAR");
        botonEliminarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarPagoActionPerformed(evt);
            }
        });

        botonImprimirTicket.setBackground(new java.awt.Color(0, 93, 171));
        botonImprimirTicket.setText("Concluir");
        botonImprimirTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonImprimirTicketActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelVentaVentaLayout = new javax.swing.GroupLayout(panelVentaVenta);
        panelVentaVenta.setLayout(panelVentaVentaLayout);
        panelVentaVentaLayout.setHorizontalGroup(
            panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentaVentaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addComponent(jLabel86)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbSucursalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel90)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbContratoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel113)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCantidadPago, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel114)
                                .addGap(26, 26, 26)
                                .addComponent(txtRestantePago, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(botonAgregarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonEliminarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonImprimirTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVentaVentaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel112)
                                .addGap(18, 18, 18)
                                .addComponent(cbFormaPagoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)))
                        .addGroup(panelVentaVentaLayout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addComponent(botonAgregarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(botonEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(botonLimpiarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                    .addGap(75, 75, 75)
                                    .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel84)
                                        .addComponent(jLabel85))
                                    .addGap(18, 18, 18)
                                    .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtCodigoBarrasVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(18, 18, 18)
                .addComponent(recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1175, 1175, 1175)
                .addComponent(panelBotonesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelVentaVentaLayout.setVerticalGroup(
            panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVentaVentaLayout.createSequentialGroup()
                        .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addComponent(panelBotonesVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88))
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel86)
                                    .addComponent(cbSucursalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel90)
                                    .addComponent(cbContratoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel84)
                                    .addComponent(txtCodigoBarrasVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel85)
                                    .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(botonAgregarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonLimpiarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)))
                        .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel112)
                                    .addComponent(cbFormaPagoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel113)
                                    .addComponent(txtCantidadPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel114)))
                            .addGroup(panelVentaVentaLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(txtRestantePago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelVentaVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonAgregarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonEliminarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonImprimirTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelVentaVentaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(recibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        panelVenta.addTab("Venta", panelVentaVenta);

        rSPanelsSlider1.add(panelVenta, "card8");

        panelInventario.setBackground(new java.awt.Color(255, 255, 255));
        panelInventario.setForeground(new java.awt.Color(0, 0, 0));
        panelInventario.setName("panelInventario"); // NOI18N

        panelInventarioInventario.setBackground(new java.awt.Color(255, 255, 255));
        panelInventarioInventario.setName("panelInventarioInventario"); // NOI18N

        jLabel78.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 0, 0));
        jLabel78.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/inventario.png"))); // NOI18N
        jLabel78.setText("INVENTARIO");

        tableInventario.setBackground(new java.awt.Color(204, 204, 255));
        tableInventario.setForeground(new java.awt.Color(0, 0, 0));
        tableInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane11.setViewportView(tableInventario);

        botonActualizarInventario.setBackground(new java.awt.Color(0, 93, 171));
        botonActualizarInventario.setText("Actualizar");
        botonActualizarInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarInventarioActionPerformed(evt);
            }
        });

        cbSucursalInventario.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalInventario.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalInventario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel125.setForeground(new java.awt.Color(0, 0, 0));
        jLabel125.setText("Codigo de Barras:");

        jLabel129.setForeground(new java.awt.Color(0, 0, 0));
        jLabel129.setText("Sucursal:");

        txtCodigoBarrasInventario.setBackground(new java.awt.Color(255, 255, 255));

        botonBusquedaAvanzada.setBackground(new java.awt.Color(21, 77, 42));
        botonBusquedaAvanzada.setText("BUSQUEDA");
        botonBusquedaAvanzada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBusquedaAvanzadaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInventarioInventarioLayout = new javax.swing.GroupLayout(panelInventarioInventario);
        panelInventarioInventario.setLayout(panelInventarioInventarioLayout);
        panelInventarioInventarioLayout.setHorizontalGroup(
            panelInventarioInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioInventarioLayout.createSequentialGroup()
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel129)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSucursalInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigoBarrasInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonActualizarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonBusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 1179, Short.MAX_VALUE)
        );
        panelInventarioInventarioLayout.setVerticalGroup(
            panelInventarioInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInventarioInventarioLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelInventarioInventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonActualizarInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSucursalInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel125)
                    .addComponent(jLabel129)
                    .addComponent(txtCodigoBarrasInventario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBusquedaAvanzada, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        panelInventario.addTab("Inventario", panelInventarioInventario);

        panelProducto.setBackground(new java.awt.Color(255, 255, 255));
        panelProducto.setName("panelProducto"); // NOI18N

        jLabel46.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/producto.png"))); // NOI18N
        jLabel46.setText("PRODUCTO");

        jLabel47.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Codigo de Barras:");

        jLabel48.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 0));
        jLabel48.setText("Nombre:");

        jLabel49.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Tipo:");

        jLabel50.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Marca:");

        jLabel51.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Temporada:");

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Color:");

        jLabel53.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("Material:");

        jLabel54.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Categoria:");

        jLabel56.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Modelo:");

        txtCodigoBarrasProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoBarrasProducto.setForeground(new java.awt.Color(0, 0, 0));

        txtNombreProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreProducto.setForeground(new java.awt.Color(0, 0, 0));

        cbTipoProducto.setBackground(new java.awt.Color(255, 255, 255));
        cbTipoProducto.setForeground(new java.awt.Color(0, 0, 0));
        cbTipoProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtModeloProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtModeloProducto.setForeground(new java.awt.Color(0, 0, 0));

        cbMaterialProducto.setBackground(new java.awt.Color(255, 255, 255));
        cbMaterialProducto.setForeground(new java.awt.Color(0, 0, 0));
        cbMaterialProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbCategoriaProducto.setBackground(new java.awt.Color(255, 255, 255));
        cbCategoriaProducto.setForeground(new java.awt.Color(0, 0, 0));
        cbCategoriaProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbColorProducto.setBackground(new java.awt.Color(255, 255, 255));
        cbColorProducto.setForeground(new java.awt.Color(0, 0, 0));
        cbColorProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbTemporadaProducto.setBackground(new java.awt.Color(255, 255, 255));
        cbTemporadaProducto.setForeground(new java.awt.Color(0, 0, 0));
        cbTemporadaProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        panelBotonesProducto.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarProducto.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarProducto.setText("Agregar");
        botonAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarProductoActionPerformed(evt);
            }
        });

        botonConsultarProducto.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarProducto.setText("Consultar");
        botonConsultarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarProductoActionPerformed(evt);
            }
        });

        botonEliminarSucursal1.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarSucursal1.setText("ELIMINAR");
        botonEliminarSucursal1.setEnabled(false);
        botonEliminarSucursal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarSucursal1ActionPerformed(evt);
            }
        });

        botonModificarSucursal1.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarSucursal1.setText("modificar");
        botonModificarSucursal1.setEnabled(false);
        botonModificarSucursal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarSucursal1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesProductoLayout = new javax.swing.GroupLayout(panelBotonesProducto);
        panelBotonesProducto.setLayout(panelBotonesProductoLayout);
        panelBotonesProductoLayout.setHorizontalGroup(
            panelBotonesProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesProductoLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(botonAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(botonConsultarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(botonEliminarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(botonModificarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        panelBotonesProductoLayout.setVerticalGroup(
            panelBotonesProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAgregarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonModificarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtMarcaProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtMarcaProducto.setForeground(new java.awt.Color(0, 0, 0));

        tableProducto.setBackground(new java.awt.Color(204, 204, 255));
        tableProducto.setForeground(new java.awt.Color(0, 0, 0));
        tableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Barras", "Nombre", "Tipo", "Marca", "Modelo", "Color", "Categoria", "Material", "Temporada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tableProducto);

        panelImagenProducto.setBackground(new java.awt.Color(255, 255, 255));

        labelImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        botonAgregarMaterial.setText("+");
        botonAgregarMaterial.setToolTipText("Agregar Servicios");
        botonAgregarMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarMaterialActionPerformed(evt);
            }
        });

        botonAgregarColor.setText("+");
        botonAgregarColor.setToolTipText("Agregar Servicios");
        botonAgregarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarColorActionPerformed(evt);
            }
        });

        botonAgregarCategoria.setText("+");
        botonAgregarCategoria.setToolTipText("Agregar Servicios");
        botonAgregarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImagenProductoLayout = new javax.swing.GroupLayout(panelImagenProducto);
        panelImagenProducto.setLayout(panelImagenProductoLayout);
        panelImagenProductoLayout.setHorizontalGroup(
            panelImagenProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagenProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImagenProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonAgregarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarColor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelImagenProductoLayout.setVerticalGroup(
            panelImagenProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagenProductoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImagenProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImagenProductoLayout.createSequentialGroup()
                        .addComponent(labelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImagenProductoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonAgregarMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonAgregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(botonAgregarColor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        botonAgregarTipo.setText("+");
        botonAgregarTipo.setToolTipText("Agregar Servicios");
        botonAgregarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductoLayout = new javax.swing.GroupLayout(panelProducto);
        panelProducto.setLayout(panelProductoLayout);
        panelProductoLayout.setHorizontalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addComponent(panelBotonesProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(jLabel48)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel50))
                                .addGap(18, 18, 18)
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigoBarrasProducto)
                                    .addComponent(txtNombreProducto)
                                    .addComponent(cbTipoProducto, 0, 222, Short.MAX_VALUE)
                                    .addComponent(txtMarcaProducto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonAgregarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel53)
                            .addComponent(jLabel54)
                            .addComponent(jLabel52)
                            .addComponent(jLabel51))
                        .addGap(31, 31, 31)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtModeloProducto)
                            .addComponent(cbMaterialProducto, 0, 222, Short.MAX_VALUE)
                            .addComponent(cbCategoriaProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbColorProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTemporadaProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelImagenProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelProductoLayout.createSequentialGroup()
                .addComponent(jScrollPane7)
                .addContainerGap())
        );
        panelProductoLayout.setVerticalGroup(
            panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductoLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelProductoLayout.createSequentialGroup()
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTemporadaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jLabel56)
                            .addComponent(txtCodigoBarrasProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtModeloProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel48)
                                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelProductoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbMaterialProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel53))))
                        .addGap(6, 6, 6)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(cbTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonAgregarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54)
                            .addComponent(cbCategoriaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(panelProductoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(jLabel52)
                            .addComponent(cbColorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMarcaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelImagenProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelBotonesProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        panelInventario.addTab("Producto", panelProducto);

        panelResurtir.setBackground(new java.awt.Color(255, 255, 255));
        panelResurtir.setForeground(new java.awt.Color(0, 0, 0));
        panelResurtir.setName("panelResurtir"); // NOI18N

        tableResurtir.setBackground(new java.awt.Color(204, 204, 255));
        tableResurtir.setForeground(new java.awt.Color(0, 0, 0));
        tableResurtir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Barras", "Resurtido", "Precio Por Unidad", "Fecha de Caducidad", "Cantidad", "Cantidad Real", "Baja"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tableResurtir);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel71.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setText("Codigo Barras:");

        jLabel75.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(0, 0, 0));
        jLabel75.setText("Precio P/ Unidad:");

        txtPPPRenRes.setBackground(new java.awt.Color(255, 255, 255));
        txtPPPRenRes.setForeground(new java.awt.Color(0, 0, 0));

        txtCodigoBarrasRenRes.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoBarrasRenRes.setForeground(new java.awt.Color(0, 0, 0));

        jLabel76.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(0, 0, 0));
        jLabel76.setText("Existencia:");

        jLabel74.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(0, 0, 0));
        jLabel74.setText("Cantidad:");

        jLabel77.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 0, 0));
        jLabel77.setText("Fecha Caducidad:");

        dataFechaCaducidadRenRes.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaCaducidadRenRes.setForeground(new java.awt.Color(0, 0, 0));

        txtNumeroCalzadoRenres.setBackground(new java.awt.Color(255, 255, 255));
        txtNumeroCalzadoRenres.setForeground(new java.awt.Color(0, 0, 0));

        jLabel111.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(0, 0, 0));
        jLabel111.setText("Numero Calzado:");

        jLabel70.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/resurtir.png"))); // NOI18N
        jLabel70.setText("PRODUCTOS RESURTIDOS");

        botonLimpiarResurtir.setBackground(new java.awt.Color(255, 153, 0));
        botonLimpiarResurtir.setText("limpiar");
        botonLimpiarResurtir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarResurtirActionPerformed(evt);
            }
        });

        botonAgregarResurtir.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarResurtir.setText("Agregar");
        botonAgregarResurtir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarResurtirActionPerformed(evt);
            }
        });

        botonConcluirResurtir1.setText("Concluir");
        botonConcluirResurtir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConcluirResurtir1ActionPerformed(evt);
            }
        });

        botoneliminarResurtir.setBackground(new java.awt.Color(255, 51, 51));
        botoneliminarResurtir.setText("Eliminar");
        botoneliminarResurtir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoneliminarResurtirActionPerformed(evt);
            }
        });

        botonLimpiarTodoResurtir.setBackground(new java.awt.Color(255, 102, 0));
        botonLimpiarTodoResurtir.setText("limpiar todo");
        botonLimpiarTodoResurtir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarTodoResurtirActionPerformed(evt);
            }
        });

        botonMostraNumeros.setText("...");
        botonMostraNumeros.setToolTipText("Agregar Servicios");
        botonMostraNumeros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMostraNumerosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel77)
                            .addComponent(jLabel71))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dataFechaCaducidadRenRes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigoBarrasRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel75)
                            .addComponent(jLabel74))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPPPRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spCantidadRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel111)
                            .addComponent(jLabel76))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNumeroCalzadoRenres)
                            .addComponent(spBajaRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonMostraNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel70)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(botonAgregarResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botoneliminarResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonLimpiarResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonLimpiarTodoResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonConcluirResurtir1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoBarrasRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel71)
                            .addComponent(txtNumeroCalzadoRenres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel111)
                            .addComponent(botonMostraNumeros, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel77)
                                .addComponent(dataFechaCaducidadRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel76)
                                .addComponent(spBajaRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botoneliminarResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonLimpiarResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonConcluirResurtir1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonLimpiarTodoResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonAgregarResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPPPRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel75))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel74)
                            .addComponent(spCantidadRenRes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel65.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/factura.png"))); // NOI18N
        jLabel65.setText("RESURTIR");

        cbProveedorResurtir.setBackground(new java.awt.Color(255, 255, 255));
        cbProveedorResurtir.setForeground(new java.awt.Color(0, 0, 0));
        cbProveedorResurtir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText("Proveedor:");

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText("Sucursal:");

        cbSucursalResurtir.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalResurtir.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalResurtir.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtfacturaResurtir.setBackground(new java.awt.Color(255, 255, 255));
        txtfacturaResurtir.setForeground(new java.awt.Color(0, 0, 0));

        jLabel69.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText("Factura:");

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText("Fecha:");

        dataFechaResurtir.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaResurtir.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel66)
                .addGap(18, 18, 18)
                .addComponent(cbProveedorResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbSucursalResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfacturaResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel68)
                .addGap(18, 18, 18)
                .addComponent(dataFechaResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataFechaResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel66)
                        .addComponent(cbProveedorResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel67)
                        .addComponent(cbSucursalResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel69)
                        .addComponent(txtfacturaResurtir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelResurtirLayout = new javax.swing.GroupLayout(panelResurtir);
        panelResurtir.setLayout(panelResurtirLayout);
        panelResurtirLayout.setHorizontalGroup(
            panelResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResurtirLayout.createSequentialGroup()
                .addGroup(panelResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelResurtirLayout.setVerticalGroup(
            panelResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResurtirLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        panelInventario.addTab(" Resurtir", panelResurtir);

        panelDevolverResurtir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel119.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel119.setForeground(new java.awt.Color(0, 0, 0));
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel119.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/devolver.png"))); // NOI18N
        jLabel119.setText("DEVOLVER");

        jLabel141.setBackground(new java.awt.Color(0, 0, 0));
        jLabel141.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(0, 0, 0));
        jLabel141.setText("Proveedor:");

        jLabel142.setBackground(new java.awt.Color(0, 0, 0));
        jLabel142.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(0, 0, 0));
        jLabel142.setText("Sucursal:");

        jLabel143.setBackground(new java.awt.Color(0, 0, 0));
        jLabel143.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(0, 0, 0));
        jLabel143.setText("Factura:");

        txtFacturaDevolver.setBackground(new java.awt.Color(255, 255, 255));

        cbProveedorDevolver.setBackground(new java.awt.Color(255, 255, 255));

        cbSucursalDevolver.setBackground(new java.awt.Color(255, 255, 255));

        tableDevolucion.setBackground(new java.awt.Color(204, 204, 255));
        tableDevolucion.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane22.setViewportView(tableDevolucion);

        botonDevolverProducto.setBackground(new java.awt.Color(255, 153, 51));
        botonDevolverProducto.setText("Devolver");
        botonDevolverProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDevolverProductoActionPerformed(evt);
            }
        });

        txtCodigoBarrasDevolucion.setBackground(new java.awt.Color(255, 255, 255));

        jLabel144.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(0, 0, 0));
        jLabel144.setText("Codigo de Barras:");

        jLabel145.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(0, 0, 0));
        jLabel145.setText("Cantidad:");

        txtCantidadDevolucion.setBackground(new java.awt.Color(255, 255, 255));

        botonFiltrarDevolucion.setBackground(new java.awt.Color(0, 93, 171));
        botonFiltrarDevolucion.setText("FILTRAR");
        botonFiltrarDevolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFiltrarDevolucionActionPerformed(evt);
            }
        });

        botonDevolverTodo.setBackground(new java.awt.Color(255, 0, 0));
        botonDevolverTodo.setText("Devolver Todo");
        botonDevolverTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDevolverTodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDevolverResurtirLayout = new javax.swing.GroupLayout(panelDevolverResurtir);
        panelDevolverResurtir.setLayout(panelDevolverResurtirLayout);
        panelDevolverResurtirLayout.setHorizontalGroup(
            panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDevolverResurtirLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelDevolverResurtirLayout.createSequentialGroup()
                        .addComponent(jLabel141)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbProveedorDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel142)
                        .addGap(18, 18, 18)
                        .addComponent(cbSucursalDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel143)
                        .addGap(18, 18, 18)
                        .addComponent(txtFacturaDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane22)
            .addGroup(panelDevolverResurtirLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel144)
                .addGap(18, 18, 18)
                .addComponent(txtCodigoBarrasDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jLabel145)
                .addGap(18, 18, 18)
                .addComponent(txtCantidadDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(botonDevolverProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(botonDevolverTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDevolverResurtirLayout.createSequentialGroup()
                    .addContainerGap(950, Short.MAX_VALUE)
                    .addComponent(botonFiltrarDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(33, 33, 33)))
        );
        panelDevolverResurtirLayout.setVerticalGroup(
            panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDevolverResurtirLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel141)
                    .addComponent(jLabel142)
                    .addComponent(jLabel143)
                    .addComponent(txtFacturaDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProveedorDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSucursalDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoBarrasDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel144)
                    .addComponent(jLabel145)
                    .addComponent(txtCantidadDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonDevolverProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonDevolverTodo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
            .addGroup(panelDevolverResurtirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDevolverResurtirLayout.createSequentialGroup()
                    .addGap(89, 89, 89)
                    .addComponent(botonFiltrarDevolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(510, Short.MAX_VALUE)))
        );

        panelInventario.addTab("Devolver Pedido", panelDevolverResurtir);

        panelMinimoMaximo.setBackground(new java.awt.Color(255, 255, 255));
        panelMinimoMaximo.setForeground(new java.awt.Color(0, 0, 0));
        panelMinimoMaximo.setName("panelMinimoMaximo"); // NOI18N

        jLabel79.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 0, 0));
        jLabel79.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/maxmin.png"))); // NOI18N
        jLabel79.setText("MAXIMO - MINIMO");

        tableMinMax.setBackground(new java.awt.Color(204, 204, 255));
        tableMinMax.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo de Barras", "Nombre", "Minimo", "Maximo", "Sucursal"
            }
        ));
        jScrollPane12.setViewportView(tableMinMax);

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(0, 0, 0));
        jLabel80.setText("Codigo Barras:");

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(0, 0, 0));
        jLabel81.setText("Sucursal:");

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 0, 0));
        jLabel82.setText("Minimo:");

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 0, 0));
        jLabel83.setText("Maximo:");

        cbSucursalMaxMin.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalMaxMin.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalMaxMin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtCodigoBarrasMaximo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoBarrasMaximo.setForeground(new java.awt.Color(0, 0, 0));

        panelBotonesMaxMin.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarMaxMin.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarMaxMin.setText("Agregar");
        botonAgregarMaxMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarMaxMinActionPerformed(evt);
            }
        });

        botonConsultarMaxMin.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarMaxMin.setText("Consultar");
        botonConsultarMaxMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarMaxMinActionPerformed(evt);
            }
        });

        botonEliminarMaxMin.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarMaxMin.setText("ELIMINAR");
        botonEliminarMaxMin.setEnabled(false);
        botonEliminarMaxMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarMaxMinActionPerformed(evt);
            }
        });

        botonModificarMaxMin.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarMaxMin.setText("modificar");
        botonModificarMaxMin.setEnabled(false);
        botonModificarMaxMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarMaxMinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesMaxMinLayout = new javax.swing.GroupLayout(panelBotonesMaxMin);
        panelBotonesMaxMin.setLayout(panelBotonesMaxMinLayout);
        panelBotonesMaxMinLayout.setHorizontalGroup(
            panelBotonesMaxMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesMaxMinLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesMaxMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panelBotonesMaxMinLayout.setVerticalGroup(
            panelBotonesMaxMinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesMaxMinLayout.createSequentialGroup()
                .addComponent(botonAgregarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEliminarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        cbSucursalMaximoMinimoB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel130.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(0, 0, 102));
        jLabel130.setText("Listar Por Sucursal De:");

        javax.swing.GroupLayout panelMinimoMaximoLayout = new javax.swing.GroupLayout(panelMinimoMaximo);
        panelMinimoMaximo.setLayout(panelMinimoMaximoLayout);
        panelMinimoMaximoLayout.setHorizontalGroup(
            panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12)
            .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel81)
                            .addComponent(jLabel80))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSucursalMaxMin, 0, 230, Short.MAX_VALUE)
                            .addComponent(txtCodigoBarrasMaximo))
                        .addGap(25, 25, 25))
                    .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel130)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel82)
                            .addComponent(jLabel83))
                        .addGap(28, 28, 28)
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(cbSucursalMaximoMinimoB, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(panelBotonesMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        panelMinimoMaximoLayout.setVerticalGroup(
            panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMinimoMaximoLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbSucursalMaximoMinimoB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel130)))
                        .addGap(32, 32, 32)
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel80)
                            .addComponent(jLabel83)
                            .addComponent(spMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoBarrasMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(panelMinimoMaximoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(jLabel82)
                            .addComponent(cbSucursalMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMinimoMaximoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelBotonesMaxMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        panelInventario.addTab(" Maximo - Minimo", panelMinimoMaximo);

        panelPrecio.setBackground(new java.awt.Color(255, 255, 255));
        panelPrecio.setName("panelPrecio"); // NOI18N

        jLabel107.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(0, 0, 0));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/precio.png"))); // NOI18N
        jLabel107.setText("PRECIO DE VENTA");

        jLabel108.setBackground(new java.awt.Color(0, 0, 0));
        jLabel108.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(0, 0, 0));
        jLabel108.setText("Codigo Barras:");

        jLabel109.setBackground(new java.awt.Color(0, 0, 0));
        jLabel109.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(0, 0, 0));
        jLabel109.setText("Precio Venta:");

        jLabel110.setBackground(new java.awt.Color(0, 0, 0));
        jLabel110.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(0, 0, 0));
        jLabel110.setText("Fecha:");

        txtCodigoBarrasPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoBarrasPrecio.setForeground(new java.awt.Color(0, 0, 0));

        txtPrecioPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioPrecio.setForeground(new java.awt.Color(0, 0, 0));

        dataFechaPrecio.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaPrecio.setForeground(new java.awt.Color(0, 0, 0));

        tablePrecio.setBackground(new java.awt.Color(204, 204, 255));
        tablePrecio.setForeground(new java.awt.Color(0, 0, 0));
        tablePrecio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo Barras", "Precio Venta", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane17.setViewportView(tablePrecio);

        panelBotonesPrecio.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarPrecio.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarPrecio.setText("Agregar");
        botonAgregarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarPrecioActionPerformed(evt);
            }
        });

        botonConsultarPrecio.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarPrecio.setText("Consultar");
        botonConsultarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarPrecioActionPerformed(evt);
            }
        });

        botonEliminarPrecio.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarPrecio.setText("ELIMINAR");
        botonEliminarPrecio.setEnabled(false);
        botonEliminarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarPrecioActionPerformed(evt);
            }
        });

        botonModificarPrecio.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarPrecio.setText("modificar");
        botonModificarPrecio.setEnabled(false);
        botonModificarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesPrecioLayout = new javax.swing.GroupLayout(panelBotonesPrecio);
        panelBotonesPrecio.setLayout(panelBotonesPrecioLayout);
        panelBotonesPrecioLayout.setHorizontalGroup(
            panelBotonesPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesPrecioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panelBotonesPrecioLayout.setVerticalGroup(
            panelBotonesPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesPrecioLayout.createSequentialGroup()
                .addComponent(botonAgregarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEliminarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelPrecioLayout = new javax.swing.GroupLayout(panelPrecio);
        panelPrecio.setLayout(panelPrecioLayout);
        panelPrecioLayout.setHorizontalGroup(
            panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrecioLayout.createSequentialGroup()
                .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrecioLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel108)
                            .addComponent(jLabel109)
                            .addComponent(jLabel110))
                        .addGap(18, 18, 18)
                        .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigoBarrasPrecio)
                            .addComponent(txtPrecioPrecio)
                            .addComponent(dataFechaPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))
                    .addGroup(panelPrecioLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrecioLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(panelBotonesPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE))
        );
        panelPrecioLayout.setVerticalGroup(
            panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrecioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane17)
                    .addGroup(panelPrecioLayout.createSequentialGroup()
                        .addComponent(jLabel107, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel108)
                            .addComponent(txtCodigoBarrasPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel109)
                            .addComponent(txtPrecioPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(panelPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel110)
                            .addComponent(dataFechaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addComponent(panelBotonesPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(127, Short.MAX_VALUE))))
        );

        panelInventario.addTab("Precio", panelPrecio);

        rSPanelsSlider1.add(panelInventario, "card7");

        panelEmpleados.setBackground(new java.awt.Color(255, 255, 255));
        panelEmpleados.setForeground(new java.awt.Color(0, 0, 0));
        panelEmpleados.setName("panelEmpleados"); // NOI18N

        panelPersona.setBackground(new java.awt.Color(255, 255, 255));
        panelPersona.setName("panelPersona"); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/persona.png"))); // NOI18N
        jLabel4.setText("PERSONA");

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Nombre:");

        tablePersona.setBackground(new java.awt.Color(204, 204, 255));
        tablePersona.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CURP", "Nombre ", "Apellido Paterno", "Apellido Materno", "Sexo", "Fecha Nacimiento", "Calle", "Numero", "Orientacion", "Colonia", "Codigo Postal", "Municipio", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablePersona);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Apellido Paterno:");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Apellido Materno:");

        txtAPaternoPersona.setBackground(new java.awt.Color(255, 255, 255));
        txtAPaternoPersona.setForeground(new java.awt.Color(0, 0, 0));

        txtAMaternoPersona.setBackground(new java.awt.Color(255, 255, 255));
        txtAMaternoPersona.setForeground(new java.awt.Color(0, 0, 0));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("CURP:");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Fecha Nacimiento:");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Sexo:");

        txtCurpPersona.setBackground(new java.awt.Color(255, 255, 255));
        txtCurpPersona.setForeground(new java.awt.Color(0, 0, 0));

        dataFechaNacimiento.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaNacimiento.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Estado:");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Municipio:");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Codigo Postal:");

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Colonia:");

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Calle:");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Numero:");

        cbSexo.setBackground(new java.awt.Color(255, 255, 255));
        cbSexo.setForeground(new java.awt.Color(0, 0, 0));
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Selecciona Sexo -", "Masculino", "Femenino" }));

        cbMunicipio.setBackground(new java.awt.Color(255, 255, 255));
        cbMunicipio.setForeground(new java.awt.Color(0, 0, 0));
        cbMunicipio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMunicipioItemStateChanged(evt);
            }
        });

        cbCodigoPostal.setBackground(new java.awt.Color(255, 255, 255));
        cbCodigoPostal.setForeground(new java.awt.Color(0, 0, 0));
        cbCodigoPostal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCodigoPostalItemStateChanged(evt);
            }
        });

        cbColonia.setBackground(new java.awt.Color(255, 255, 255));
        cbColonia.setForeground(new java.awt.Color(0, 0, 0));
        cbColonia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbColoniaItemStateChanged(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Orientacion:");

        cbOrientacion.setBackground(new java.awt.Color(255, 255, 255));
        cbOrientacion.setForeground(new java.awt.Color(0, 0, 0));
        cbOrientacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Selecciona Orientacion -", "Norte ", "Sur ", "Poniente", "Oriente", " " }));

        panelBotonesPersona.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarPersona.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarPersona.setText("Agregar");
        botonAgregarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarPersonaActionPerformed(evt);
            }
        });

        botonConsultarPersona.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarPersona.setText("Consultar");
        botonConsultarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarPersonaActionPerformed(evt);
            }
        });

        botonEliminarPersona.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarPersona.setText("ELIMINAR");
        botonEliminarPersona.setEnabled(false);
        botonEliminarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarPersonaActionPerformed(evt);
            }
        });

        botonModificarPersona.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarPersona.setText("modificar");
        botonModificarPersona.setEnabled(false);
        botonModificarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarPersonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesPersonaLayout = new javax.swing.GroupLayout(panelBotonesPersona);
        panelBotonesPersona.setLayout(panelBotonesPersonaLayout);
        panelBotonesPersonaLayout.setHorizontalGroup(
            panelBotonesPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesPersonaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonAgregarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonEliminarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonModificarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelBotonesPersonaLayout.setVerticalGroup(
            panelBotonesPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesPersonaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAgregarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonModificarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbEstado.setBackground(new java.awt.Color(255, 255, 255));
        cbEstado.setForeground(new java.awt.Color(0, 0, 0));
        cbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstadoItemStateChanged(evt);
            }
        });
        cbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstadoActionPerformed(evt);
            }
        });

        txtCalle.setBackground(new java.awt.Color(255, 255, 255));
        txtCalle.setForeground(new java.awt.Color(0, 0, 0));

        panelFotoEmpleado.setBackground(new java.awt.Color(255, 255, 255));

        labelFotoPersona.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelFotoEmpleadoLayout = new javax.swing.GroupLayout(panelFotoEmpleado);
        panelFotoEmpleado.setLayout(panelFotoEmpleadoLayout);
        panelFotoEmpleadoLayout.setHorizontalGroup(
            panelFotoEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFotoEmpleadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelFotoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelFotoEmpleadoLayout.setVerticalGroup(
            panelFotoEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFotoEmpleadoLayout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addComponent(labelFotoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelPersonaLayout = new javax.swing.GroupLayout(panelPersona);
        panelPersona.setLayout(panelPersonaLayout);
        panelPersonaLayout.setHorizontalGroup(
            panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPersonaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBotonesPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(168, 168, 168))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPersonaLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(40, 40, 40))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPersonaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelPersonaLayout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelPersonaLayout.createSequentialGroup()
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dataFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panelPersonaLayout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelPersonaLayout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAPaternoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelPersonaLayout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCurpPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelPersonaLayout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAMaternoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel15))
                    .addComponent(jLabel3)
                    .addComponent(jLabel18)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCalle)
                    .addComponent(cbMunicipio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbCodigoPostal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbColonia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbOrientacion, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addComponent(panelFotoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
        );
        panelPersonaLayout.setVerticalGroup(
            panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPersonaLayout.createSequentialGroup()
                .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPersonaLayout.createSequentialGroup()
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPersonaLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPersonaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre)
                            .addComponent(jLabel13)
                            .addComponent(cbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAPaternoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(cbCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAMaternoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(cbColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCurpPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(spNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPersonaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel18)
                                .addComponent(cbOrientacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dataFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(panelPersonaLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(panelFotoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(panelBotonesPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );

        panelEmpleados.addTab("Persona", panelPersona);

        panelEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        panelEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        panelEmpleado.setName("panelEmpleado"); // NOI18N

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/empleado.png"))); // NOI18N
        jLabel19.setText("EMPLEADO");

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("CURP:");

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Sueldo:");

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Puesto:");

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setText("Fecha Fin:");

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Sucursal:");

        jLabel38.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Fecha Inicio:");

        jLabel39.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setText("Turno:");

        cbCurpEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        cbCurpEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        cbPuestoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        cbPuestoEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        txtSueldoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtSueldoEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        dataFechaInicioEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaInicioEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        dataFechaFinEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaFinEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        cbSucursalEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        cbTurnoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        cbTurnoEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        cbTurnoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Seleccione -", "Matutino", "Vespertino" }));

        tableEmpleado.setBackground(new java.awt.Color(204, 204, 255));
        tableEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CURP", "Nombre", "Apellido Paterno", "Puesto", "Sueldo", "Fecha Inicio", "Fecha Fin", "Sucursal", "Turno"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableEmpleado);

        panelBotonesEmpleado.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarEmpleado.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarEmpleado.setText("Agregar");
        botonAgregarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarEmpleadoActionPerformed(evt);
            }
        });

        botonConsultarEmpleado.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarEmpleado.setText("Consultar");
        botonConsultarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarEmpleadoActionPerformed(evt);
            }
        });

        botonEliminarEmpleado.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarEmpleado.setText("ELIMINAR");
        botonEliminarEmpleado.setEnabled(false);
        botonEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarEmpleadoActionPerformed(evt);
            }
        });

        botonModificarEmpleado.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarEmpleado.setText("modificar");
        botonModificarEmpleado.setEnabled(false);
        botonModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesEmpleadoLayout = new javax.swing.GroupLayout(panelBotonesEmpleado);
        panelBotonesEmpleado.setLayout(panelBotonesEmpleadoLayout);
        panelBotonesEmpleadoLayout.setHorizontalGroup(
            panelBotonesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesEmpleadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panelBotonesEmpleadoLayout.setVerticalGroup(
            panelBotonesEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesEmpleadoLayout.createSequentialGroup()
                .addComponent(botonAgregarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEliminarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        botonAgregarPuesto.setText("+");
        botonAgregarPuesto.setToolTipText("Agregar Servicios");
        botonAgregarPuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarPuestoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEmpleadoLayout = new javax.swing.GroupLayout(panelEmpleado);
        panelEmpleado.setLayout(panelEmpleadoLayout);
        panelEmpleadoLayout.setHorizontalGroup(
            panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel34)
                            .addComponent(jLabel37)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCurpEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSueldoEmpleado)
                                    .addComponent(cbPuestoEmpleado, 0, 234, Short.MAX_VALUE)
                                    .addComponent(cbSucursalEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonAgregarPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel36)
                            .addComponent(jLabel39))
                        .addGap(29, 29, 29)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dataFechaInicioEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                            .addComponent(dataFechaFinEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbTurnoEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(panelBotonesEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        panelEmpleadoLayout.setVerticalGroup(
            panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelBotonesEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelEmpleadoLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelEmpleadoLayout.createSequentialGroup()
                                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel33)
                                        .addComponent(jLabel38)
                                        .addComponent(cbCurpEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dataFechaInicioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel36)
                                    .addComponent(cbPuestoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonAgregarPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(dataFechaFinEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtSueldoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(cbTurnoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(panelEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(cbSucursalEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        panelEmpleados.addTab("Empleado", panelEmpleado);

        PanelContacto.setBackground(new java.awt.Color(255, 255, 255));
        PanelContacto.setName("panelContacto"); // NOI18N

        jLabel40.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/contacto.png"))); // NOI18N
        jLabel40.setText("CONTACTO");

        jLabel41.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Medio:");

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 0));
        jLabel42.setText("Descripcion:");

        jLabel44.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("CURP:");

        cbMedioContacto.setBackground(new java.awt.Color(255, 255, 255));
        cbMedioContacto.setForeground(new java.awt.Color(0, 0, 0));
        cbMedioContacto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Seleccione -", "Telefono", "Celular", "Correo Electronico", "Fax", "Otro" }));

        txtDescripcionContacto.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionContacto.setForeground(new java.awt.Color(0, 0, 0));

        cbCurpContacto.setBackground(new java.awt.Color(255, 255, 255));
        cbCurpContacto.setForeground(new java.awt.Color(0, 0, 0));
        cbCurpContacto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tableContacto.setBackground(new java.awt.Color(204, 204, 255));
        tableContacto.setForeground(new java.awt.Color(0, 0, 0));
        tableContacto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                " Nombre", "Apellido Paterno", "Apellido Materno", "CURP", "Medio", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tableContacto);

        panelBotonesContacto.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarContacto.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarContacto.setText("Agregar");
        botonAgregarContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarContactoActionPerformed(evt);
            }
        });

        botonConsultarContacto.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarContacto.setText("Consultar");
        botonConsultarContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarContactoActionPerformed(evt);
            }
        });

        botonEliminarContacto.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarContacto.setText("ELIMINAR");
        botonEliminarContacto.setEnabled(false);
        botonEliminarContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarContactoActionPerformed(evt);
            }
        });

        botonModificarContacto.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarContacto.setText("modificar");
        botonModificarContacto.setEnabled(false);
        botonModificarContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarContactoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesContactoLayout = new javax.swing.GroupLayout(panelBotonesContacto);
        panelBotonesContacto.setLayout(panelBotonesContactoLayout);
        panelBotonesContactoLayout.setHorizontalGroup(
            panelBotonesContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesContactoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panelBotonesContactoLayout.setVerticalGroup(
            panelBotonesContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesContactoLayout.createSequentialGroup()
                .addComponent(botonAgregarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEliminarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelContactoLayout = new javax.swing.GroupLayout(PanelContacto);
        PanelContacto.setLayout(PanelContactoLayout);
        PanelContactoLayout.setHorizontalGroup(
            PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContactoLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelContactoLayout.createSequentialGroup()
                        .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jLabel42)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCurpContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMedioContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelContactoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(panelBotonesContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        PanelContactoLayout.setVerticalGroup(
            PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelContactoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5)
                    .addGroup(PanelContactoLayout.createSequentialGroup()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(cbMedioContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtDescripcionContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(75, 75, 75)
                        .addGroup(PanelContactoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(cbCurpContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addComponent(panelBotonesContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        panelEmpleados.addTab("Contacto", PanelContacto);

        panelHorario.setBackground(new java.awt.Color(255, 255, 255));
        panelHorario.setName("panelHorario"); // NOI18N

        jLabel95.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(0, 0, 0));
        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel95.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/horario.png"))); // NOI18N
        jLabel95.setText("HORARIO");

        jLabel96.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(0, 0, 0));
        jLabel96.setText("No. Contrato:");

        jLabel97.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(0, 0, 0));
        jLabel97.setText("Hora Entrada:");

        jLabel98.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(0, 0, 0));
        jLabel98.setText("Dia Descanso");

        jLabel99.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(0, 0, 0));
        jLabel99.setText("Hora Comida:");

        jLabel100.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(0, 0, 0));
        jLabel100.setText("Hora Salida:");

        cbContratoHorario.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoHorario.setForeground(new java.awt.Color(0, 0, 0));

        dateDiaDescanso.setBackground(new java.awt.Color(255, 255, 255));
        dateDiaDescanso.setForeground(new java.awt.Color(0, 0, 0));

        txtHoraEntrada.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraEntrada.setForeground(new java.awt.Color(0, 0, 0));
        txtHoraEntrada.setText("HH-MM-SS");

        txtHoraSalida.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraSalida.setForeground(new java.awt.Color(0, 0, 0));
        txtHoraSalida.setText("HH-MM-SS");

        tableHorario.setBackground(new java.awt.Color(204, 204, 255));
        tableHorario.setForeground(new java.awt.Color(0, 0, 0));
        tableHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CURP", "Dia Descanso", "Hora Entrada", "Hora Salida", "Hora Comida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane15.setViewportView(tableHorario);

        botonAgregarHorario.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarHorario.setText("Agregar");
        botonAgregarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarHorarioActionPerformed(evt);
            }
        });

        botonConsultarHorario.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarHorario.setText("ACTUALIZAR");
        botonConsultarHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarHorarioActionPerformed(evt);
            }
        });

        txtHoraComida.setBackground(new java.awt.Color(255, 255, 255));
        txtHoraComida.setForeground(new java.awt.Color(0, 0, 0));
        txtHoraComida.setText("HH-MM-SS");

        jLabel131.setBackground(new java.awt.Color(0, 0, 0));
        jLabel131.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(0, 0, 0));
        jLabel131.setText("Sucursal:");

        cbSucursalHorarioH.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalHorarioH.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalHorarioH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSucursalHorarioH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSucursalHorarioHItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelHorarioLayout = new javax.swing.GroupLayout(panelHorario);
        panelHorario.setLayout(panelHorarioLayout);
        panelHorarioLayout.setHorizontalGroup(
            panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15)
            .addGroup(panelHorarioLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHorarioLayout.createSequentialGroup()
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel96)
                            .addComponent(jLabel98))
                        .addGap(24, 24, 24)
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbContratoHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateDiaDescanso, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelHorarioLayout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(txtHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelHorarioLayout.createSequentialGroup()
                                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel97)
                                    .addComponent(jLabel100))
                                .addGap(35, 35, 35)
                                .addComponent(txtHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(jLabel99)
                        .addGap(44, 44, 44)
                        .addComponent(txtHoraComida, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(panelHorarioLayout.createSequentialGroup()
                        .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(botonAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(botonConsultarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel131)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSucursalHorarioH, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelHorarioLayout.setVerticalGroup(
            panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHorarioLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel131)
                    .addComponent(cbSucursalHorarioH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHorarioLayout.createSequentialGroup()
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel97)
                            .addComponent(txtHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel100)
                            .addComponent(txtHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelHorarioLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel96)
                            .addComponent(cbContratoHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel98)
                            .addComponent(dateDiaDescanso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel99)
                        .addComponent(txtHoraComida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEmpleados.addTab("Horario", panelHorario);

        panelChecador.setBackground(new java.awt.Color(255, 255, 255));
        panelChecador.setForeground(new java.awt.Color(0, 0, 0));
        panelChecador.setName("panelChecador"); // NOI18N

        jLabel43.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/checador.png"))); // NOI18N
        jLabel43.setText("CHECADOR");

        jLabel45.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 0));
        jLabel45.setText("No. Contrato: ");

        cbContratoChecador.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoChecador.setForeground(new java.awt.Color(0, 0, 0));
        cbContratoChecador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));

        tableChecador.setBackground(new java.awt.Color(204, 204, 255));
        tableChecador.setForeground(new java.awt.Color(0, 0, 0));
        tableChecador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CURP", "Hora Entrada", "Hora Salida"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tableChecador);

        botonChecarEntrada.setBackground(new java.awt.Color(0, 161, 112));
        botonChecarEntrada.setText("Entrada");
        botonChecarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonChecarEntradaActionPerformed(evt);
            }
        });

        botonChecarSalida.setBackground(new java.awt.Color(255, 153, 51));
        botonChecarSalida.setText("salida");
        botonChecarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonChecarSalidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChecadorLayout = new javax.swing.GroupLayout(panelChecador);
        panelChecador.setLayout(panelChecadorLayout);
        panelChecadorLayout.setHorizontalGroup(
            panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChecadorLayout.createSequentialGroup()
                .addGroup(panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChecadorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(botonChecarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelChecadorLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(jLabel45)
                            .addGap(33, 33, 33)
                            .addComponent(cbContratoChecador, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelChecadorLayout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChecadorLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(botonChecarSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        panelChecadorLayout.setVerticalGroup(
            panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChecadorLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelChecadorLayout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addGroup(panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(cbContratoChecador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(panelChecadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonChecarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonChecarSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(254, Short.MAX_VALUE))
        );

        panelEmpleados.addTab("Checador", panelChecador);

        panelPagoEmpleados.setBackground(new java.awt.Color(255, 255, 255));
        panelPagoEmpleados.setName("panelPagoEmpleados"); // NOI18N

        jLabel101.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(0, 0, 0));
        jLabel101.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel101.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/pagoempleado.png"))); // NOI18N
        jLabel101.setText("PAGO");

        jLabel102.setBackground(new java.awt.Color(255, 255, 255));
        jLabel102.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel102.setForeground(new java.awt.Color(0, 0, 0));
        jLabel102.setText("No. Contrato:");

        jLabel103.setBackground(new java.awt.Color(255, 255, 255));
        jLabel103.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(0, 0, 0));
        jLabel103.setText("Monto:");

        jLabel104.setBackground(new java.awt.Color(255, 255, 255));
        jLabel104.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(0, 0, 0));
        jLabel104.setText("Fecha:");

        cbContratoPagoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoPagoEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        cbContratoPagoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtMontoPagoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtMontoPagoEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        dateFechaPagoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        dateFechaPagoEmpleado.setForeground(new java.awt.Color(0, 0, 0));

        tablePagoEmpleado.setBackground(new java.awt.Color(204, 204, 255));
        tablePagoEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        tablePagoEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CURP", "Monto", "Fecha"
            }
        ));
        jScrollPane16.setViewportView(tablePagoEmpleado);

        panelBotonesPagoEmpleado.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarPagoEmpleado.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarPagoEmpleado.setText("Agregar");
        botonAgregarPagoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarPagoEmpleadoActionPerformed(evt);
            }
        });

        botonConsultarPagoEmpleado.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarPagoEmpleado.setText("Consultar");
        botonConsultarPagoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarPagoEmpleadoActionPerformed(evt);
            }
        });

        botonEliminarPagoEmpleado.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarPagoEmpleado.setText("ELIMINAR");
        botonEliminarPagoEmpleado.setEnabled(false);
        botonEliminarPagoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarPagoEmpleadoActionPerformed(evt);
            }
        });

        botonModificarPagoEmpleado.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarPagoEmpleado.setText("modificar");
        botonModificarPagoEmpleado.setEnabled(false);
        botonModificarPagoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarPagoEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesPagoEmpleadoLayout = new javax.swing.GroupLayout(panelBotonesPagoEmpleado);
        panelBotonesPagoEmpleado.setLayout(panelBotonesPagoEmpleadoLayout);
        panelBotonesPagoEmpleadoLayout.setHorizontalGroup(
            panelBotonesPagoEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesPagoEmpleadoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesPagoEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panelBotonesPagoEmpleadoLayout.setVerticalGroup(
            panelBotonesPagoEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesPagoEmpleadoLayout.createSequentialGroup()
                .addComponent(botonAgregarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEliminarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jLabel132.setBackground(new java.awt.Color(255, 255, 255));
        jLabel132.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(0, 0, 0));
        jLabel132.setText("Tipo:");

        cbTipoPagoEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        cbTipoPagoEmpleado.setForeground(new java.awt.Color(0, 0, 0));
        cbTipoPagoEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout panelPagoEmpleadosLayout = new javax.swing.GroupLayout(panelPagoEmpleados);
        panelPagoEmpleados.setLayout(panelPagoEmpleadosLayout);
        panelPagoEmpleadosLayout.setHorizontalGroup(
            panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                        .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagoEmpleadosLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel104))
                            .addComponent(jLabel103))
                        .addGap(55, 55, 55)
                        .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMontoPagoEmpleado)
                            .addComponent(dateFechaPagoEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
                    .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                        .addComponent(jLabel102)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbContratoPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(panelBotonesPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                        .addComponent(jLabel132)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTipoPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelPagoEmpleadosLayout.setVerticalGroup(
            panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelPagoEmpleadosLayout.createSequentialGroup()
                        .addComponent(jLabel101, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel102)
                            .addComponent(cbContratoPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel132)
                            .addComponent(cbTipoPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel103)
                            .addComponent(txtMontoPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(panelPagoEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel104)
                            .addComponent(dateFechaPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(panelBotonesPagoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        panelEmpleados.addTab("Pago", panelPagoEmpleados);

        rSPanelsSlider1.add(panelEmpleados, "card8");

        panelProveedor.setBackground(new java.awt.Color(255, 255, 255));
        panelProveedor.setName("panelProveedor"); // NOI18N

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/proveedor.png"))); // NOI18N
        jLabel6.setText("PROVEEDOR");

        jLabel55.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Nombre:");

        jLabel57.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 0, 0));
        jLabel57.setText("Codigo Postal:");

        jLabel58.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Colonia:");

        jLabel59.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("Numero Tel:");

        jLabel60.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 0, 0));
        jLabel60.setText("Estado:");

        jLabel61.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText("Calle:");

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText("Municipio:");

        jLabel63.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Numero:");

        jLabel64.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(0, 0, 0));
        jLabel64.setText("Orientacion:");

        txtNombreProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreProveedor.setForeground(new java.awt.Color(0, 0, 0));

        txtCalleProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtCalleProveedor.setForeground(new java.awt.Color(0, 0, 0));

        cbCodigoPostalProveedor.setBackground(new java.awt.Color(255, 255, 255));
        cbCodigoPostalProveedor.setForeground(new java.awt.Color(0, 0, 0));
        cbCodigoPostalProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCodigoPostalProveedorItemStateChanged(evt);
            }
        });

        cbEstadoProveedor.setBackground(new java.awt.Color(255, 255, 255));
        cbEstadoProveedor.setForeground(new java.awt.Color(0, 0, 0));
        cbEstadoProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEstadoProveedorItemStateChanged(evt);
            }
        });

        cbMunicipioProveedor.setBackground(new java.awt.Color(255, 255, 255));
        cbMunicipioProveedor.setForeground(new java.awt.Color(0, 0, 0));
        cbMunicipioProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMunicipioProveedorItemStateChanged(evt);
            }
        });

        cbColoniaProveedor.setBackground(new java.awt.Color(255, 255, 255));
        cbColoniaProveedor.setForeground(new java.awt.Color(0, 0, 0));

        cbOrientacionProveedor.setBackground(new java.awt.Color(255, 255, 255));
        cbOrientacionProveedor.setForeground(new java.awt.Color(0, 0, 0));
        cbOrientacionProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Seleccione -", "Norte", "Sur", "Oriente", "Poniente" }));

        txtNumeroProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtNumeroProveedor.setForeground(new java.awt.Color(0, 0, 0));

        tableProveedor.setBackground(new java.awt.Color(204, 204, 255));
        tableProveedor.setForeground(new java.awt.Color(0, 0, 0));
        tableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Numero Tel", "Calle", "Numero", "Orientacion", "Colonia", "Codigo Postal", "Municipio", "Estado"
            }
        ));
        jScrollPane8.setViewportView(tableProveedor);

        panelBotonesProveedor.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarProveedor.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarProveedor.setText("Agregar");
        botonAgregarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarProveedorActionPerformed(evt);
            }
        });

        botonConsultarProveedor.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarProveedor.setText("Consultar");
        botonConsultarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarProveedorActionPerformed(evt);
            }
        });

        botonEliminarProveedor.setBackground(new java.awt.Color(255, 51, 51));
        botonEliminarProveedor.setText("ELIMINAR");
        botonEliminarProveedor.setEnabled(false);
        botonEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarProveedorActionPerformed(evt);
            }
        });

        botonModificarProveedor.setBackground(new java.awt.Color(255, 153, 51));
        botonModificarProveedor.setText("modificar");
        botonModificarProveedor.setEnabled(false);
        botonModificarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesProveedorLayout = new javax.swing.GroupLayout(panelBotonesProveedor);
        panelBotonesProveedor.setLayout(panelBotonesProveedorLayout);
        panelBotonesProveedorLayout.setHorizontalGroup(
            panelBotonesProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesProveedorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBotonesProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonModificarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAgregarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );
        panelBotonesProveedorLayout.setVerticalGroup(
            panelBotonesProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesProveedorLayout.createSequentialGroup()
                .addComponent(botonAgregarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonModificarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelProveedorLayout = new javax.swing.GroupLayout(panelProveedor);
        panelProveedor.setLayout(panelProveedorLayout);
        panelProveedorLayout.setHorizontalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProveedorLayout.createSequentialGroup()
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel59)
                            .addComponent(jLabel60)
                            .addComponent(jLabel62))
                        .addGap(27, 27, 27)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombreProveedor)
                                .addComponent(cbEstadoProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNumeroProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                            .addComponent(cbMunicipioProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelProveedorLayout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbCodigoPostalProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProveedorLayout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCalleProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProveedorLayout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbColoniaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProveedorLayout.createSequentialGroup()
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel64))
                        .addGap(41, 41, 41)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spNumeroProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(cbOrientacionProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(57, 57, 57)
                .addComponent(panelBotonesProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1166, Short.MAX_VALUE))
            .addComponent(jScrollPane8)
        );
        panelProveedorLayout.setVerticalGroup(
            panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProveedorLayout.createSequentialGroup()
                .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProveedorLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(cbCodigoPostalProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel55)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58)
                            .addComponent(cbColoniaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(txtNumeroProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)
                            .addComponent(txtCalleProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(cbEstadoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)
                            .addComponent(spNumeroProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(panelProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(cbMunicipioProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel64)
                            .addComponent(cbOrientacionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProveedorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelBotonesProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        rSPanelsSlider1.add(panelProveedor, "card7");

        panelMas.setName("panelMas"); // NOI18N

        panelConsultas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel146.setBackground(new java.awt.Color(0, 0, 0));
        jLabel146.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(0, 0, 0));
        jLabel146.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel146.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/consulta.png"))); // NOI18N
        jLabel146.setText("  CONSULTAS");

        botonZapatoCaducado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatomascaducado.png"))); // NOI18N
        botonZapatoCaducado.setText("Zapato Mas Caducado/Quedado");
        botonZapatoCaducado.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatoCaducado.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatoCaducado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatoCaducadoActionPerformed(evt);
            }
        });

        botonZapatoMasVendido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatomasvendido.png"))); // NOI18N
        botonZapatoMasVendido.setText("   Zapato mas vendido");
        botonZapatoMasVendido.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatoMasVendido.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatoMasVendido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatoMasVendidoActionPerformed(evt);
            }
        });

        botonSucursalIngresoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bsucursalmasingreso.png"))); // NOI18N
        botonSucursalIngresoVenta.setText("   Sucursal Mas Ingreso Por Venta");
        botonSucursalIngresoVenta.setColorHover(new java.awt.Color(51, 51, 51));
        botonSucursalIngresoVenta.setColorNormal(new java.awt.Color(0, 70, 204));
        botonSucursalIngresoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSucursalIngresoVentaActionPerformed(evt);
            }
        });

        botonEmpleadoMasVende.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bempleadomasvende.png"))); // NOI18N
        botonEmpleadoMasVende.setText("   Empleado Mas Vende");
        botonEmpleadoMasVende.setColorHover(new java.awt.Color(51, 51, 51));
        botonEmpleadoMasVende.setColorNormal(new java.awt.Color(0, 70, 204));
        botonEmpleadoMasVende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEmpleadoMasVendeActionPerformed(evt);
            }
        });

        botonZapatoRegala.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatomasregala.png"))); // NOI18N
        botonZapatoRegala.setText("   Zapato Mas Se Regala");
        botonZapatoRegala.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatoRegala.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatoRegala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatoRegalaActionPerformed(evt);
            }
        });

        botonZapatosResurten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatomasresurtido.png"))); // NOI18N
        botonZapatosResurten.setText("   Zapato Mas Resurtido");
        botonZapatosResurten.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatosResurten.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatosResurten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatosResurtenActionPerformed(evt);
            }
        });

        botonSucursalGasta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bsucursalmasgasta.png"))); // NOI18N
        botonSucursalGasta.setText("  Sucursal Mas Gasta En Servicios");
        botonSucursalGasta.setColorHover(new java.awt.Color(51, 51, 51));
        botonSucursalGasta.setColorNormal(new java.awt.Color(0, 70, 204));
        botonSucursalGasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSucursalGastaActionPerformed(evt);
            }
        });

        botonProveedorCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bproveedormassecompra.png"))); // NOI18N
        botonProveedorCompra.setText("  Proveedor Mas Veces Se Compra");
        botonProveedorCompra.setColorHover(new java.awt.Color(51, 51, 51));
        botonProveedorCompra.setColorNormal(new java.awt.Color(0, 70, 204));
        botonProveedorCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProveedorCompraActionPerformed(evt);
            }
        });

        botonFormaPagoUsada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bformapago.png"))); // NOI18N
        botonFormaPagoUsada.setText("   Forma de Pago Mas Usada");
        botonFormaPagoUsada.setColorHover(new java.awt.Color(51, 51, 51));
        botonFormaPagoUsada.setColorNormal(new java.awt.Color(0, 70, 204));
        botonFormaPagoUsada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFormaPagoUsadaActionPerformed(evt);
            }
        });

        botonZapatoNoVendido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatosnosehanvendido.png"))); // NOI18N
        botonZapatoNoVendido.setText("  Zapatos No Se Han Vendido");
        botonZapatoNoVendido.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatoNoVendido.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatoNoVendido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatoNoVendidoActionPerformed(evt);
            }
        });

        botonZapatoCompraAlta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatomascaro.png"))); // NOI18N
        botonZapatoCompraAlta.setText("Zapato Mas Caro Se Compra");
        botonZapatoCompraAlta.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatoCompraAlta.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatoCompraAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatoCompraAltaActionPerformed(evt);
            }
        });

        botonZapatoMasIntercambiado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/bzapatomasintercambiado.png"))); // NOI18N
        botonZapatoMasIntercambiado.setText("  Zapato Mas Intercambiado");
        botonZapatoMasIntercambiado.setColorHover(new java.awt.Color(51, 51, 51));
        botonZapatoMasIntercambiado.setColorNormal(new java.awt.Color(0, 70, 204));
        botonZapatoMasIntercambiado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonZapatoMasIntercambiadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConsultasLayout = new javax.swing.GroupLayout(panelConsultas);
        panelConsultas.setLayout(panelConsultasLayout);
        panelConsultasLayout.setHorizontalGroup(
            panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultasLayout.createSequentialGroup()
                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConsultasLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelConsultasLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonZapatosResurten, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelConsultasLayout.createSequentialGroup()
                                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConsultasLayout.createSequentialGroup()
                                        .addComponent(botonZapatoMasVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(botonSucursalIngresoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelConsultasLayout.createSequentialGroup()
                                        .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(botonFormaPagoUsada, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                            .addComponent(botonEmpleadoMasVende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(botonZapatoMasIntercambiado, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                            .addComponent(botonZapatoRegala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(botonZapatoCompraAlta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(botonZapatoCaducado, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(botonSucursalGasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(botonZapatoNoVendido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(botonProveedorCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        panelConsultasLayout.setVerticalGroup(
            panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultasLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel146, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonSucursalIngresoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonZapatoMasVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonZapatoCaducado, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(botonFormaPagoUsada, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonZapatoRegala, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonSucursalGasta, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonZapatosResurten, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonZapatoNoVendido, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonZapatoMasIntercambiado, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonProveedorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelConsultasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(botonZapatoCompraAlta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addComponent(botonEmpleadoMasVende, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        panelMas.addTab("Consultas", panelConsultas);

        panelReportes.setBackground(new java.awt.Color(255, 255, 255));
        panelReportes.setName("panelReportes"); // NOI18N

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/reporte.png"))); // NOI18N
        jLabel5.setText("REPORTES");

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(102, 255, 102));
        rSMaterialButtonRectangle2.setForeground(new java.awt.Color(0, 0, 0));
        rSMaterialButtonRectangle2.setText("COMPRAS");
        rSMaterialButtonRectangle2.setFont(new java.awt.Font("Lucida Bright", 1, 48)); // NOI18N
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle3.setBackground(new java.awt.Color(255, 102, 102));
        rSMaterialButtonRectangle3.setForeground(new java.awt.Color(0, 0, 0));
        rSMaterialButtonRectangle3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/inventario.png"))); // NOI18N
        rSMaterialButtonRectangle3.setText("INVENTARIO");
        rSMaterialButtonRectangle3.setFont(new java.awt.Font("Lucida Bright", 1, 48)); // NOI18N
        rSMaterialButtonRectangle3.setIconTextGap(0);
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle5.setBackground(new java.awt.Color(102, 153, 255));
        rSMaterialButtonRectangle5.setForeground(new java.awt.Color(0, 0, 0));
        rSMaterialButtonRectangle5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/venta.png"))); // NOI18N
        rSMaterialButtonRectangle5.setText("VENTAS");
        rSMaterialButtonRectangle5.setFont(new java.awt.Font("Lucida Bright", 1, 48)); // NOI18N
        rSMaterialButtonRectangle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle5ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle6.setBackground(new java.awt.Color(255, 153, 51));
        rSMaterialButtonRectangle6.setForeground(new java.awt.Color(0, 0, 0));
        rSMaterialButtonRectangle6.setText("caja");
        rSMaterialButtonRectangle6.setFont(new java.awt.Font("Lucida Bright", 1, 48)); // NOI18N
        rSMaterialButtonRectangle6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelReportesLayout = new javax.swing.GroupLayout(panelReportes);
        panelReportes.setLayout(panelReportesLayout);
        panelReportesLayout.setHorizontalGroup(
            panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReportesLayout.createSequentialGroup()
                .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReportesLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rSMaterialButtonRectangle2, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                            .addComponent(rSMaterialButtonRectangle5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rSMaterialButtonRectangle3, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                            .addComponent(rSMaterialButtonRectangle6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelReportesLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1131, Short.MAX_VALUE))
        );
        panelReportesLayout.setVerticalGroup(
            panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReportesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSMaterialButtonRectangle5, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSMaterialButtonRectangle2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle6, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );

        panelMas.addTab("Reportes", panelReportes);

        panelRegalo.setBackground(new java.awt.Color(255, 255, 255));

        jLabel120.setBackground(new java.awt.Color(0, 0, 0));
        jLabel120.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(0, 0, 0));
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/regalo.png"))); // NOI18N
        jLabel120.setText("REGALO");

        jLabel121.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel121.setForeground(new java.awt.Color(0, 0, 0));
        jLabel121.setText("Sucursal:");

        jLabel123.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel123.setForeground(new java.awt.Color(0, 0, 0));
        jLabel123.setText("Cantidad:");

        jLabel124.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel124.setForeground(new java.awt.Color(0, 0, 0));
        jLabel124.setText("Codigo de Barras:");

        txtCodigoBarrasRegalo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoBarrasRegalo.setForeground(new java.awt.Color(0, 0, 0));

        tableRegalo.setBackground(new java.awt.Color(204, 204, 255));
        tableRegalo.setForeground(new java.awt.Color(0, 0, 0));
        tableRegalo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane21.setViewportView(tableRegalo);

        panelBotonesPersona1.setBackground(new java.awt.Color(255, 255, 255));

        botonAgregarRegalo.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarRegalo.setText("Agregar");
        botonAgregarRegalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarRegaloActionPerformed(evt);
            }
        });

        botonConsultarRegalo.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarRegalo.setText("Finalizar");
        botonConsultarRegalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarRegaloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesPersona1Layout = new javax.swing.GroupLayout(panelBotonesPersona1);
        panelBotonesPersona1.setLayout(panelBotonesPersona1Layout);
        panelBotonesPersona1Layout.setHorizontalGroup(
            panelBotonesPersona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesPersona1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAgregarRegalo, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(panelBotonesPersona1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botonConsultarRegalo, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBotonesPersona1Layout.setVerticalGroup(
            panelBotonesPersona1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesPersona1Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(botonAgregarRegalo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonConsultarRegalo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jLabel133.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(0, 0, 0));
        jLabel133.setText("Empleado:");

        cbSucursalRegaloR.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalRegaloR.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSucursalRegaloRItemStateChanged(evt);
            }
        });

        cbContratoRegaloR.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoRegaloR.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbContratoRegaloRItemStateChanged(evt);
            }
        });

        botonDetallesIntercambio1.setBackground(new java.awt.Color(255, 153, 0));
        botonDetallesIntercambio1.setText("Detalles");
        botonDetallesIntercambio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDetallesIntercambio1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRegaloLayout = new javax.swing.GroupLayout(panelRegalo);
        panelRegalo.setLayout(panelRegaloLayout);
        panelRegaloLayout.setHorizontalGroup(
            panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegaloLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegaloLayout.createSequentialGroup()
                        .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel124)
                            .addComponent(jLabel121))
                        .addGap(49, 49, 49)
                        .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigoBarrasRegalo, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(cbSucursalRegaloR, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelRegaloLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegaloLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel123)
                            .addComponent(jLabel133))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1080, Short.MAX_VALUE)
                        .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(spCantidadRegalo, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(cbContratoRegaloR, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(56, 56, 56))
                    .addGroup(panelRegaloLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(botonDetallesIntercambio1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(panelBotonesPersona1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addComponent(jScrollPane21)
        );
        panelRegaloLayout.setVerticalGroup(
            panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegaloLayout.createSequentialGroup()
                .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegaloLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonDetallesIntercambio1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRegaloLayout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel123)
                                    .addComponent(jLabel124)
                                    .addComponent(txtCodigoBarrasRegalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spCantidadRegalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelRegaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel121)
                                .addComponent(jLabel133)
                                .addComponent(cbSucursalRegaloR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbContratoRegaloR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelRegaloLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelBotonesPersona1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addGap(79, 79, 79))
        );

        panelMas.addTab("Regalo", panelRegalo);

        panelIntercambio.setBackground(new java.awt.Color(255, 255, 255));
        panelIntercambio.setForeground(new java.awt.Color(255, 255, 255));
        panelIntercambio.setName("panelIntercambio"); // NOI18N

        jLabel106.setBackground(new java.awt.Color(0, 0, 0));
        jLabel106.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(0, 0, 0));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/intercambio.png"))); // NOI18N
        jLabel106.setText("INTERCAMBIO");

        jLabel122.setBackground(new java.awt.Color(0, 0, 0));
        jLabel122.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel122.setForeground(new java.awt.Color(0, 0, 0));
        jLabel122.setText("Codigo de Barras:");

        jLabel134.setBackground(new java.awt.Color(0, 0, 0));
        jLabel134.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(0, 0, 0));
        jLabel134.setText("Sucursal Sale:");

        jLabel135.setBackground(new java.awt.Color(0, 0, 0));
        jLabel135.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(0, 0, 0));
        jLabel135.setText("Sucursal Entra:");

        jLabel136.setBackground(new java.awt.Color(0, 0, 0));
        jLabel136.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(0, 0, 0));
        jLabel136.setText("Empleado:");

        jLabel137.setBackground(new java.awt.Color(0, 0, 0));
        jLabel137.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(0, 0, 0));
        jLabel137.setText("Fecha:");

        txtCodigoBarrasIntercambio.setBackground(new java.awt.Color(255, 255, 255));

        cbSucursalSaleIntercambio.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalSaleIntercambio.setForeground(new java.awt.Color(0, 0, 0));

        cbSucursalEntraIntercambio.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalEntraIntercambio.setForeground(new java.awt.Color(0, 0, 0));

        cbContratoIntercambio.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoIntercambio.setForeground(new java.awt.Color(0, 0, 0));

        dateFechaIntercambio.setBackground(new java.awt.Color(255, 255, 255));
        dateFechaIntercambio.setForeground(new java.awt.Color(0, 0, 0));

        tableIntercambio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(tableIntercambio);

        botonAgregarSucursalServicio1.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarSucursalServicio1.setText("cambio");
        botonAgregarSucursalServicio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarSucursalServicio1ActionPerformed(evt);
            }
        });

        jLabel138.setBackground(new java.awt.Color(0, 0, 0));
        jLabel138.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(0, 0, 0));
        jLabel138.setText("Cantidad:");

        txtCantidadIntercambio.setBackground(new java.awt.Color(255, 255, 255));

        botonDetallesIntercambio.setBackground(new java.awt.Color(255, 153, 0));
        botonDetallesIntercambio.setText("Detalles");
        botonDetallesIntercambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDetallesIntercambioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelIntercambioLayout = new javax.swing.GroupLayout(panelIntercambio);
        panelIntercambio.setLayout(panelIntercambioLayout);
        panelIntercambioLayout.setHorizontalGroup(
            panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIntercambioLayout.createSequentialGroup()
                .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelIntercambioLayout.createSequentialGroup()
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelIntercambioLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelIntercambioLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelIntercambioLayout.createSequentialGroup()
                                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel122)
                                            .addComponent(jLabel134)
                                            .addComponent(jLabel135))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbSucursalEntraIntercambio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbSucursalSaleIntercambio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtCodigoBarrasIntercambio)))
                                    .addGroup(panelIntercambioLayout.createSequentialGroup()
                                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel136)
                                            .addComponent(jLabel137)
                                            .addComponent(jLabel138))
                                        .addGap(71, 71, 71)
                                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelIntercambioLayout.createSequentialGroup()
                                                .addComponent(dateFechaIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(cbContratoIntercambio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtCantidadIntercambio))))))
                        .addGap(43, 43, 43))
                    .addGroup(panelIntercambioLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonDetallesIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonAgregarSucursalServicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1047, 1047, 1047))
        );
        panelIntercambioLayout.setVerticalGroup(
            panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIntercambioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelIntercambioLayout.createSequentialGroup()
                        .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel122)
                            .addComponent(txtCodigoBarrasIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel134)
                            .addComponent(cbSucursalSaleIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel135)
                            .addComponent(cbSucursalEntraIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel136)
                            .addComponent(cbContratoIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel138)
                            .addComponent(txtCantidadIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(panelIntercambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel137)
                            .addComponent(dateFechaIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(botonAgregarSucursalServicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonDetallesIntercambio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        panelMas.addTab("Intercambio", panelIntercambio);

        panelCambio.setBackground(new java.awt.Color(255, 255, 255));
        panelCambio.setName("panelCambio"); // NOI18N

        jLabel105.setBackground(new java.awt.Color(0, 0, 0));
        jLabel105.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(0, 0, 0));
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/cambio.png"))); // NOI18N
        jLabel105.setText("CAMBIO");

        jLabel73.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 0));
        jLabel73.setText("Folio");

        jLabel91.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(0, 0, 0));
        jLabel91.setText("Zapato (Entra):");

        jLabel92.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 0, 0));
        jLabel92.setText("Zapato (Sale):");

        jLabel93.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(0, 0, 0));
        jLabel93.setText("Descripcion:");

        jLabel94.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 0, 0));
        jLabel94.setText("Fecha:");

        jLabel115.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(0, 0, 0));
        jLabel115.setText("Empleado:");

        txtFolioCambio.setBackground(new java.awt.Color(255, 255, 255));
        txtFolioCambio.setForeground(new java.awt.Color(0, 0, 0));

        txtSaleCambio.setBackground(new java.awt.Color(255, 255, 255));
        txtSaleCambio.setForeground(new java.awt.Color(0, 0, 0));

        txtEntraCambio.setBackground(new java.awt.Color(255, 255, 255));
        txtEntraCambio.setForeground(new java.awt.Color(0, 0, 0));

        dataFechaCambio.setBackground(new java.awt.Color(255, 255, 255));
        dataFechaCambio.setForeground(new java.awt.Color(0, 0, 0));

        cbContratoCambio.setBackground(new java.awt.Color(255, 255, 255));
        cbContratoCambio.setForeground(new java.awt.Color(0, 0, 0));
        cbContratoCambio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tableCambio.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane14.setViewportView(tableCambio);

        jLabel116.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel116.setForeground(new java.awt.Color(0, 0, 0));
        jLabel116.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel116.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img1/pago.png"))); // NOI18N
        jLabel116.setText("   CAMBIO PAGO");

        jLabel117.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel117.setForeground(new java.awt.Color(0, 0, 0));
        jLabel117.setText("Monto:");

        jLabel118.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel118.setForeground(new java.awt.Color(0, 0, 0));
        jLabel118.setText("Forma:");

        txtDescripcionCambio.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionCambio.setColumns(20);
        txtDescripcionCambio.setForeground(new java.awt.Color(0, 0, 0));
        txtDescripcionCambio.setRows(5);
        jScrollPane19.setViewportView(txtDescripcionCambio);

        cbFormaPagoCambio.setBackground(new java.awt.Color(255, 255, 255));
        cbFormaPagoCambio.setForeground(new java.awt.Color(0, 0, 0));
        cbFormaPagoCambio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtMontoCambioPago.setBackground(new java.awt.Color(255, 255, 255));
        txtMontoCambioPago.setForeground(new java.awt.Color(0, 0, 0));

        tableCambioPago.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane20.setViewportView(tableCambioPago);

        jLabel139.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(0, 0, 0));
        jLabel139.setText("Sucursal:");

        cbSucursalCambio.setBackground(new java.awt.Color(255, 255, 255));
        cbSucursalCambio.setForeground(new java.awt.Color(0, 0, 0));
        cbSucursalCambio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        panelBotones1.setBackground(new java.awt.Color(255, 255, 255));
        panelBotones1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBotones1Layout = new javax.swing.GroupLayout(panelBotones1);
        panelBotones1.setLayout(panelBotones1Layout);
        panelBotones1Layout.setHorizontalGroup(
            panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );
        panelBotones1Layout.setVerticalGroup(
            panelBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 87, Short.MAX_VALUE)
        );

        botonAgregarColor2.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarColor2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonAgregarColor2.setText("agregar");
        botonAgregarColor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarColor2ActionPerformed(evt);
            }
        });

        botonConsultarColor.setBackground(new java.awt.Color(0, 93, 171));
        botonConsultarColor.setText("Cambiar");
        botonConsultarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarColorActionPerformed(evt);
            }
        });

        botonAgregarColor1.setBackground(new java.awt.Color(0, 161, 112));
        botonAgregarColor1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonAgregarColor1.setText("verificar");
        botonAgregarColor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarColor1ActionPerformed(evt);
            }
        });

        jLabel140.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(0, 0, 0));
        jLabel140.setText("Numero:");

        txtNumeroCambio.setBackground(new java.awt.Color(255, 255, 255));
        txtNumeroCambio.setForeground(new java.awt.Color(0, 0, 0));

        botonDetallesCambio.setBackground(new java.awt.Color(255, 153, 0));
        botonDetallesCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imaBotones/agregar.png"))); // NOI18N
        botonDetallesCambio.setText("detalles");
        botonDetallesCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDetallesCambioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCambioLayout = new javax.swing.GroupLayout(panelCambio);
        panelCambio.setLayout(panelCambioLayout);
        panelCambioLayout.setHorizontalGroup(
            panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCambioLayout.createSequentialGroup()
                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCambioLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCambioLayout.createSequentialGroup()
                                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel94)
                                    .addComponent(jLabel115)
                                    .addComponent(jLabel139))
                                .addGap(47, 47, 47)
                                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dataFechaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbContratoCambio, javax.swing.GroupLayout.Alignment.LEADING, 0, 177, Short.MAX_VALUE)
                                        .addComponent(cbSucursalCambio, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtNumeroCambio, javax.swing.GroupLayout.Alignment.LEADING)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(botonConsultarColor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonAgregarColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelCambioLayout.createSequentialGroup()
                                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel73)
                                    .addComponent(jLabel92)
                                    .addComponent(jLabel93)
                                    .addComponent(jLabel91))
                                .addGap(18, 18, 18)
                                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEntraCambio)
                                    .addComponent(txtSaleCambio)
                                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txtFolioCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel140)
                            .addGroup(panelCambioLayout.createSequentialGroup()
                                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCambioLayout.createSequentialGroup()
                                        .addComponent(jLabel117)
                                        .addGap(69, 69, 69)
                                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtMontoCambioPago, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbFormaPagoCambio, 0, 179, Short.MAX_VALUE)))
                                    .addComponent(jLabel118))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonAgregarColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                            .addComponent(jScrollPane20)))
                    .addGroup(panelCambioLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(522, 522, 522)
                        .addComponent(botonDetallesCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCambioLayout.createSequentialGroup()
                    .addGap(366, 366, 366)
                    .addComponent(panelBotones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1686, Short.MAX_VALUE)))
        );
        panelCambioLayout.setVerticalGroup(
            panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCambioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonDetallesCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCambioLayout.createSequentialGroup()
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCambioLayout.createSequentialGroup()
                                .addComponent(botonAgregarColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(botonConsultarColor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCambioLayout.createSequentialGroup()
                                .addComponent(botonAgregarColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)))
                        .addGap(83, 83, 83))
                    .addGroup(panelCambioLayout.createSequentialGroup()
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel73)
                            .addComponent(txtFolioCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel91)
                            .addComponent(txtEntraCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel92)
                            .addComponent(txtSaleCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel93)
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel94, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dataFechaCambio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel115)
                            .addComponent(cbContratoCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbSucursalCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel139))
                        .addGap(20, 20, 20)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel140)
                            .addComponent(txtNumeroCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel116, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel118)
                            .addComponent(cbFormaPagoCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel117)
                            .addComponent(txtMontoCambioPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(122, 122, 122))))
            .addGroup(panelCambioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCambioLayout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addComponent(panelBotones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(525, Short.MAX_VALUE)))
        );

        panelMas.addTab("Cambio", panelCambio);

        rSPanelsSlider1.add(panelMas, "card8");

        panelPrincipal.add(rSPanelsSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 1180, 670));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rSButtonMetro2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonMetro2ActionPerformed

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dispose();
                    CerrandoSesion cs = new CerrandoSesion();
                    cs.setVisible(true);
                    Thread.sleep(4000);
                    cs.dispose();
                    Acceso ac = new Acceso();
                    ac.setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Acceso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }//GEN-LAST:event_rSButtonMetro2ActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed
        rSPanelsSlider1.slidPanel(15, panelVenta,RSPanelsSlider.direct.Right);
        
    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnVentaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVentaMousePressed

    }//GEN-LAST:event_btnVentaMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int posicion=panUno.getX();
        System.out.println(posicion);
        if (posicion>-1) {
            Animacion.Animacion.mover_izquierda(0, -188, 2, 2, panUno);
        }else{
            Animacion.Animacion.mover_derecha(-188, 0, 2, 2, panUno);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnInevntarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInevntarioActionPerformed
      rSPanelsSlider1.slidPanel(15, panelInventario,RSPanelsSlider.direct.Right);
        
    }//GEN-LAST:event_btnInevntarioActionPerformed

    private void btnSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSucursalActionPerformed
       rSPanelsSlider1.slidPanel(15, panelSucursal,RSPanelsSlider.direct.Right);
       
    }//GEN-LAST:event_btnSucursalActionPerformed

    private void btnEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadoActionPerformed
            rSPanelsSlider1.slidPanel(15, panelEmpleados,RSPanelsSlider.direct.Right);
      
    }//GEN-LAST:event_btnEmpleadoActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
            rSPanelsSlider1.slidPanel(15, panelMas,RSPanelsSlider.direct.Right);
      
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
            rSPanelsSlider1.slidPanel(15, panelProveedor,RSPanelsSlider.direct.Right);
      
    }//GEN-LAST:event_btnMasActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void botonConsultarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarSucursalActionPerformed
        Sucursal sucursal = new Sucursal();
        sucursal.buscarSucursal(tableSucursal, txtCalleSucursal.getText());
    }//GEN-LAST:event_botonConsultarSucursalActionPerformed

    private void botonConsultarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarProductoActionPerformed
        Producto p = new Producto();
        if("".equals(txtCodigoBarrasProducto.getText())){
        p.buscarProducto(tableProducto, 0);
        String barras=String.valueOf(txtCodigoBarrasProducto.getText());
        rsscalelabel.RSScaleLabel.setScaleLabel(labelImagen,"src/Productos/sinzapato.png");
        }else{
        int codbar=Integer.parseInt(txtCodigoBarrasProducto.getText());
        p.buscarProducto(tableProducto, codbar);
        String barras=String.valueOf(codbar);
        rsscalelabel.RSScaleLabel.setScaleLabel(labelImagen,"src/Productos/"+barras+".jpg");
        }
    }//GEN-LAST:event_botonConsultarProductoActionPerformed

    private void botonConsultarMaxMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarMaxMinActionPerformed
        MinimoMaximo mm = new MinimoMaximo();
        String sucu=cbSucursalMaximoMinimoB.getSelectedItem().toString();
        int sucursal=mm.numeroSucursal(sucu);
        if("".equals(txtCodigoBarrasMaximo.getText())){
        mm.buscarMinimoMaximo(tableMinMax, 0, sucursal);
        }else{
        int codbar=Integer.parseInt(txtCodigoBarrasMaximo.getText());
        mm.buscarMinimoMaximo(tableMinMax, codbar,sucursal);
        }
    }//GEN-LAST:event_botonConsultarMaxMinActionPerformed

    private void botonConsultarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarPrecioActionPerformed
       Precio precio = new Precio();
       java.sql.Date fecha ;
       Date date = dataFechaPrecio.getDate();
       if(date==(null)){
       String fechaBlanco="";
       precio.buscarPrecio(tablePrecio, fechaBlanco);   
       }else{
       long d=date.getTime();
       fecha= new java.sql.Date(d);
       precio.buscarPrecio(tablePrecio, fecha.toString());
       }
    }//GEN-LAST:event_botonConsultarPrecioActionPerformed

    private void botonConsultarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarPersonaActionPerformed
        Persona persona = new Persona();
        String curp=txtCurpPersona.getText();
        if(!curp.equals("")){
        persona.buscarPersona(tablePersona,curp);
        rsscalelabel.RSScaleLabel.setScaleLabel(labelFotoPersona,"src/Personas/"+curp+".jpg");
        }else{
        persona.buscarPersona(tablePersona,curp);
        rsscalelabel.RSScaleLabel.setScaleLabel(labelFotoPersona,"src/Personas/sinpersona.png");
        }
    }//GEN-LAST:event_botonConsultarPersonaActionPerformed

    private void botonConsultarContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarContactoActionPerformed
       Contacto contacto = new Contacto();
       String curp=cbCurpContacto.getSelectedItem().toString();
       contacto.buscarContacto(tableContacto,curp);
    }//GEN-LAST:event_botonConsultarContactoActionPerformed

    private void botonConsultarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarEmpleadoActionPerformed
        Empleado empleado = new Empleado();
        String curp=cbCurpEmpleado.getSelectedItem().toString();
        empleado.buscarEmpleado(tableEmpleado, curp);
    }//GEN-LAST:event_botonConsultarEmpleadoActionPerformed

    private void botonConsultarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarHorarioActionPerformed
        Horario hor = new Horario();
        if(hor.habilitarHorario(2)){
        String sucu=cbSucursalHorarioH.getSelectedItem().toString();
        int sucursal=hor.numeroSucursal(sucu);
        hor.actualizarHorario(tableHorario,sucursal);
        }else{
        new rojerusan.RSNotifyFade("NO DISPONIBLE", "Hoy no puede actualizar el horario!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
        }
    }//GEN-LAST:event_botonConsultarHorarioActionPerformed

    private void botonConsultarPagoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarPagoEmpleadoActionPerformed
       PagoEmpleado pe = new PagoEmpleado();
       java.sql.Date fecha;
       Date date = dateFechaPagoEmpleado.getDate();
       if(date==(null)){
       String fechaBlanco="";
       pe.buscarPago(tablePagoEmpleado, fechaBlanco);   
       }else{
       long d=date.getTime();
       fecha= new java.sql.Date(d);
       pe.buscarPago(tablePagoEmpleado, fecha.toString());
       }
    }//GEN-LAST:event_botonConsultarPagoEmpleadoActionPerformed

    private void botonConsultarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarProveedorActionPerformed
        Proveedor proveedor = new Proveedor();
        String nombre=txtNombreProveedor.getText();
        proveedor.buscarProveedor(tableProveedor, nombre);
    }//GEN-LAST:event_botonConsultarProveedorActionPerformed

    private void cbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEstadoActionPerformed

    private void cbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoItemStateChanged
        //Evaluando si se produjo el evento
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Estado estado = (Estado) cbEstado.getSelectedItem();
            Municipio municipio = new Municipio();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(municipio.mostrarMunicipio(estado.getClave()));
            cbMunicipio.setModel(modelMunicipio);
            cbCodigoPostal.removeAllItems();
            cbColonia.removeAllItems();
        }
    }//GEN-LAST:event_cbEstadoItemStateChanged

    private void cbMunicipioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMunicipioItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Municipio municipio = (Municipio) cbMunicipio.getSelectedItem();
            CodigoPostal cp = new CodigoPostal();
            DefaultComboBoxModel modelCodigo = new DefaultComboBoxModel(cp.mostrarCodigoPostal(municipio.getClave()));
            cbCodigoPostal.setModel(modelCodigo);
            
            cbColonia.removeAllItems();
        }
    }//GEN-LAST:event_cbMunicipioItemStateChanged

    private void cbColoniaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbColoniaItemStateChanged
        
    }//GEN-LAST:event_cbColoniaItemStateChanged

    private void cbCodigoPostalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCodigoPostalItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            CodigoPostal cp = (CodigoPostal) cbCodigoPostal.getSelectedItem();
            System.out.println(cbCodigoPostal.getSelectedItem());
            Colonia col = new Colonia();
            DefaultComboBoxModel modelColonia = new DefaultComboBoxModel(col.mostrarColonias(cp.getClave()));
            cbColonia.setModel(modelColonia);
        }
    }//GEN-LAST:event_cbCodigoPostalItemStateChanged

    private void cbEstadoSucursalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoSucursalItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Estado estado = (Estado) cbEstadoSucursal.getSelectedItem();
            Municipio municipio = new Municipio();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(municipio.mostrarMunicipio(estado.getClave()));
            cbMunicipioSucursal.setModel(modelMunicipio);
            cbCodigoPostalSucursal.removeAllItems();
            cbColoniaSucursal.removeAllItems();
        }
    }//GEN-LAST:event_cbEstadoSucursalItemStateChanged

    private void cbMunicipioSucursalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMunicipioSucursalItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Municipio municipio = (Municipio) cbMunicipioSucursal.getSelectedItem();
            CodigoPostal cp = new CodigoPostal();
            DefaultComboBoxModel modelCodigo = new DefaultComboBoxModel(cp.mostrarCodigoPostal(municipio.getClave()));
            cbCodigoPostalSucursal.setModel(modelCodigo);
            cbColoniaSucursal.removeAllItems();
        }
    }//GEN-LAST:event_cbMunicipioSucursalItemStateChanged

    private void cbCodigoPostalSucursalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCodigoPostalSucursalItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            CodigoPostal cp = (CodigoPostal) cbCodigoPostalSucursal.getSelectedItem();
            Colonia col = new Colonia();
            DefaultComboBoxModel modelColonia = new DefaultComboBoxModel(col.mostrarColonias(cp.getClave()));
            cbColoniaSucursal.setModel(modelColonia);
        }
    }//GEN-LAST:event_cbCodigoPostalSucursalItemStateChanged

    private void cbEstadoProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEstadoProveedorItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Estado estado = (Estado) cbEstadoProveedor.getSelectedItem();
            Municipio municipio = new Municipio();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(municipio.mostrarMunicipio(estado.getClave()));
            cbMunicipioProveedor.setModel(modelMunicipio);
            cbCodigoPostalProveedor.removeAllItems();
            cbColoniaProveedor.removeAllItems();
        }
    }//GEN-LAST:event_cbEstadoProveedorItemStateChanged

    private void cbMunicipioProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMunicipioProveedorItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Municipio municipio = (Municipio) cbMunicipioProveedor.getSelectedItem();
            CodigoPostal cp = new CodigoPostal();
            DefaultComboBoxModel modelCodigo = new DefaultComboBoxModel(cp.mostrarCodigoPostal(municipio.getClave()));
            cbCodigoPostalProveedor.setModel(modelCodigo);
            cbColoniaProveedor.removeAllItems();
        }
    }//GEN-LAST:event_cbMunicipioProveedorItemStateChanged

    private void cbCodigoPostalProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCodigoPostalProveedorItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            CodigoPostal cp = (CodigoPostal) cbCodigoPostalProveedor.getSelectedItem();
            Colonia col = new Colonia();
            DefaultComboBoxModel modelColonia = new DefaultComboBoxModel(col.mostrarColonias(cp.getClave()));
            cbColoniaProveedor.setModel(modelColonia);
        }
    }//GEN-LAST:event_cbCodigoPostalProveedorItemStateChanged

    private void txtCalleSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalleSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCalleSucursalActionPerformed

    private void bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bActionPerformed
        try{
        int numero=(int) spNumeroSucursal.getValue();
        if(numero==0 || txtCalleSucursal.getText()==""){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);      
        }else{
        Sucursal sucursal = new Sucursal();
        String calle=txtCalleSucursal.getText();
        String orientacion=(String) cbOrientacionSucursal.getSelectedItem();
        String colonia=cbColoniaSucursal.getSelectedItem().toString();
        int col=sucursal.numeroColonia(colonia);
        String codigo=cbCodigoPostalSucursal.getSelectedItem().toString();
        String municipio = cbMunicipioSucursal.getSelectedItem().toString();
        String estado=cbEstadoSucursal.getSelectedItem().toString();
        //if(calle.equals("") || numero==0 || orientacion.equals("") || colonia.equals("") || codigo.equals("") || municipio.equals("") || estado.equ){
        sucursal.registrarSucursal(calle, numero, orientacion, col);
        sucursal.mostrarInsercion(modelo,calle, numero, orientacion, colonia, codigo, municipio, estado);
        }
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_bActionPerformed

    private void tableSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSucursalMouseClicked
       /* PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Conexion objCon = new Conexion();
            Connection conn = objCon.getConexion();

            int Fila = tableSucursal.getSelectedRow();
            String cal = tableSucursal.getValueAt(Fila, 0).toString();
            
            int numero = Integer.parseInt(tableSucursal.getValueAt(Fila, 1).toString());
            String calle="'"+cal+"'";   
            System.err.println(calle);
            System.err.println(numero);
            String sql="select calle_suc, num_suc, orientacion_suc, nom_col, cp.cp_cod, nom_mun, nom_est from sucursal s join colonia c on s.cve_col=c.cve_col join codigo cp on c.cp_cod=cp.cp_cod join municipio m on cp.cve_mun=m.cve_mun join estado e on m.cve_est=e.cve_est where calle_suc="+calle+" and num_suc="+numero+";";
            ps = conn.prepareStatement(sql);
            System.out.println(sql);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                txtCalleSucursal.setText(rs.getString("calle_suc"));
                spNumeroSucursal.setValue(rs.getInt("num_suc"));
                cbOrientacionSucursal.setSelectedItem(rs.getString("orientacion_suc"));
                
                Vector orientacion = new Vector();
                orientacion.add(rs.getString("orientacion_suc"));
                DefaultComboBoxModel modelOrientacion = new DefaultComboBoxModel(orientacion);
                cbOrientacionSucursal.setModel(modelOrientacion);
                
                Vector estado = new Vector();
                estado.add(rs.getString("nom_est"));
                DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(estado);
                cbEstadoSucursal.setModel(modelEstado);
                
                Vector municipio = new Vector();
                municipio.add(rs.getString("nom_mun"));
                DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(municipio);
                cbMunicipioSucursal.setModel(modelMunicipio);
                
                Vector codigo = new Vector();
                codigo.add(rs.getString("cp.cp_cod"));
                DefaultComboBoxModel modelCodigo = new DefaultComboBoxModel(codigo);
                cbCodigoPostalSucursal.setModel(modelCodigo);
                
                Vector colonia = new Vector();
                colonia.add(rs.getString("nom_col"));
                DefaultComboBoxModel modelColonia = new DefaultComboBoxModel(colonia);
                cbColoniaSucursal.setModel(modelColonia);
               
                
                
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }*/
    }//GEN-LAST:event_tableSucursalMouseClicked

    private void botonEliminarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarSucursalActionPerformed
        Sucursal sucursal = new Sucursal();
        SucursalServicio sucser = new SucursalServicio();
        sucursal.eliminarSucursal(modelo,tableSucursal);
    }//GEN-LAST:event_botonEliminarSucursalActionPerformed

    private void botonModificarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarSucursalActionPerformed
        try{
        int numero=(int) spNumeroSucursal.getValue();    
        if(numero==0 || txtCalleSucursal.getText()==""){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);  
        }else{
        Sucursal sucursal = new Sucursal();
        String calle=txtCalleSucursal.getText();
        String orientacion=(String) cbOrientacionSucursal.getSelectedItem();
        String colonia=cbColoniaSucursal.getSelectedItem().toString();
        int col=sucursal.numeroColonia(colonia);
        String codigo= cbCodigoPostalSucursal.getSelectedItem().toString();
        String municipio= cbMunicipioSucursal.getSelectedItem().toString();
        String estado=cbEstadoSucursal.getSelectedItem().toString();
        sucursal.modificarSucursal(tableSucursal, calle, numero, orientacion,colonia ,col, codigo, municipio, estado);    
        }
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonModificarSucursalActionPerformed

    private void botonAgregarSucursalServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarSucursalServicioActionPerformed
        try{
        if(txtMontoServicio.getText()=="" || dataFechaServicio.getDate()==null){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);      
        }else{
        SucursalServicio sucser = new SucursalServicio();
        String cal=cbSucursalServicio.getSelectedItem().toString();
        String ser=cbServicio.getSelectedItem().toString();
        float monto=Float.parseFloat(txtMontoServicio.getText());
        //obtenemos la fecha seleccionada y guardada en el campo
        Date date = dataFechaServicio.getDate();
        //guardamos el tiempo 
        long d=date.getTime();
        //convertimos a tipo de date, pero de mysql, y le pasamos la fecha antes guardada
        java.sql.Date fecha = new java.sql.Date(d);
        String fechaTabla=dataFechaServicio.getDate().toString();
        int calle=sucser.numeroCalle(cal);
        int servicio=sucser.numeroServicio(ser);
        sucser.registrarSucursalServicio(calle, servicio, monto, fecha);
        sucser.mostrarInsercion(modeloSucursalServicio, cal, ser, fecha, monto);    
        }
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonAgregarSucursalServicioActionPerformed

    private void cbOrientacionSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOrientacionSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbOrientacionSucursalActionPerformed

    private void botonEliminarSucursalServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarSucursalServicioActionPerformed
        SucursalServicio sucser = new SucursalServicio();
        sucser.eliminarSucursalServicio(modeloSucursalServicio, tableServicioSucursal);
    }//GEN-LAST:event_botonEliminarSucursalServicioActionPerformed

    private void botonModificarSucursalServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarSucursalServicioActionPerformed
       try{
       if(txtMontoServicio.getText()=="" || dataFechaServicio.getDate()==null){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       }else{
       SucursalServicio sucser = new SucursalServicio();
       String sucursal= cbSucursalServicio.getSelectedItem().toString();
       String servicio= cbServicio.getSelectedItem().toString();
       float monto=Float.parseFloat(txtMontoServicio.getText());
       Date date = dataFechaServicio.getDate();
       long d=date.getTime();
       java.sql.Date fecha = new java.sql.Date(d);
       sucser.modificarSucursalServicio(tableServicioSucursal, sucursal, servicio, fecha, monto);  
       }
       }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonModificarSucursalServicioActionPerformed

    private void botonConsultarSucursalServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarSucursalServicioActionPerformed
       SucursalServicio sucser = new SucursalServicio();
       java.sql.Date fecha ;
       Date date = dataFechaServicio.getDate();
       if(date==(null)){
       String fechaBlanco="";
       sucser.buscarSucursalServicio(tableServicioSucursal, fechaBlanco);   
       }else{
       long d=date.getTime();
       fecha= new java.sql.Date(d);
       sucser.buscarSucursalServicio(tableServicioSucursal, fecha.toString());
       }
        
       
    }//GEN-LAST:event_botonConsultarSucursalServicioActionPerformed

    private void botonAgregarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarProveedorActionPerformed
        try{
        Proveedor proveedor = new Proveedor();
        String nombre=txtNombreProveedor.getText();
        String telefono=txtNumeroProveedor.getText();
        String calle=txtCalleProveedor.getText();
        int numero=Integer.parseInt(spNumeroProveedor.getValue().toString());
        String orientacion=cbOrientacionProveedor.getSelectedItem().toString();
        String col=cbColoniaProveedor.getSelectedItem().toString();
        int colonia=proveedor.numeroColonia(col);
        String codigo=cbCodigoPostalProveedor.getSelectedItem().toString();
        String municipio=cbMunicipioProveedor.getSelectedItem().toString();
        String estado=cbEstadoProveedor.getSelectedItem().toString();
        proveedor.registrarProveedor(nombre, telefono, calle, numero, orientacion, colonia);
        proveedor.mostrarInsercion(modeloProveedor, nombre, telefono, calle, numero, orientacion, col, codigo, municipio, estado);
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonAgregarProveedorActionPerformed

    private void botonEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarProveedorActionPerformed
        Proveedor proveedor = new Proveedor();
        proveedor.eliminarProveedor(modeloProveedor, tableProveedor);
    }//GEN-LAST:event_botonEliminarProveedorActionPerformed

    private void botonModificarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarProveedorActionPerformed
        try{
        Proveedor proveedor = new Proveedor();
        String nombre=txtNombreProveedor.getText();
        String telefono=txtNumeroProveedor.getText();
        String calle=txtCalleProveedor.getText();
        int numero=Integer.parseInt(spNumeroProveedor.getValue().toString());
        String orientacion=cbOrientacionProveedor.getSelectedItem().toString();
        String colonia=cbColoniaProveedor.getSelectedItem().toString();
        int coloniaa=proveedor.numeroColonia(colonia);
        String codigo=cbCodigoPostalProveedor.getSelectedItem().toString();
        String municipio=cbMunicipioProveedor.getSelectedItem().toString();
        String estado=cbEstadoProveedor.getSelectedItem().toString();
        proveedor.modificarProveedor(tableProveedor, nombre, telefono, calle, numero, orientacion, colonia, coloniaa, codigo, municipio, estado);
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonModificarProveedorActionPerformed

    private void botonAgregarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarPersonaActionPerformed
        try{
        Persona persona = new Persona();
        String curp=txtCurpPersona.getText();
        String ap=txtAPaternoPersona.getText();
        String am=txtAMaternoPersona.getText();
        String nombre=txtNombre.getText();
        Date date = dataFechaNacimiento.getDate();
        long d=date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
        String fechaN=dataFechaNacimiento.getDate().toString();
        String sexo=cbSexo.getSelectedItem().toString();
        String calle=txtCalle.getText();
        String col=cbColonia.getSelectedItem().toString();
        int colonia=persona.numeroColonia(col);
        int numero=Integer.parseInt(spNumero.getValue().toString());
        String orientacion=cbOrientacion.getSelectedItem().toString();
        String codigo=cbCodigoPostal.getSelectedItem().toString();
        String municipio=cbMunicipio.getSelectedItem().toString();
        String estado=cbEstado.getSelectedItem().toString();
        persona.registrarPersona(curp,ap , am,nombre, sexo, fecha, calle, numero, orientacion,colonia);
        persona.mostrarInsercion(modeloPersona, curp, ap, am, nombre, sexo, fechaN, calle, numero, orientacion, col, codigo, municipio, estado);
        rsscalelabel.RSScaleLabel.setScaleLabel(labelFotoPersona,"src/Personas/"+curp+".jpg");
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarPersonaActionPerformed

    private void botonEliminarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarPersonaActionPerformed
        Persona persona = new Persona();
        persona.eliminarPersona(modeloPersona,modeloContacto,modeloEmpleado, tablePersona);
    }//GEN-LAST:event_botonEliminarPersonaActionPerformed

    private void botonModificarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarPersonaActionPerformed
        try{
        Persona persona = new Persona();
        String curp=txtCurpPersona.getText();
        String ap=txtAPaternoPersona.getText();
        String am=txtAMaternoPersona.getText();
        String nombre=txtNombre.getText();
        Date date = dataFechaNacimiento.getDate();
        long d=date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
        String fechaN=dataFechaNacimiento.getDate().toString();
        String sexo=cbSexo.getSelectedItem().toString();
        String calle=txtCalle.getText();
        String col=cbColonia.getSelectedItem().toString();
        int colonia=persona.numeroColonia(col);
        int numero=Integer.parseInt(spNumero.getValue().toString());
        String orientacion=cbOrientacion.getSelectedItem().toString();
        String codigo=cbCodigoPostal.getSelectedItem().toString();
        String municipio=cbMunicipio.getSelectedItem().toString();
        String estado=cbEstado.getSelectedItem().toString();
        persona.modificarPersona(tablePersona, nombre, ap, am, curp, sexo, fecha, calle, numero, orientacion, codigo, colonia, codigo, municipio, estado);
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonModificarPersonaActionPerformed

    private void botonAgregarContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarContactoActionPerformed
       try{
       Contacto contacto = new Contacto();
       String curp=cbCurpContacto.getSelectedItem().toString();
       String medio=cbMedioContacto.getSelectedItem().toString();
       String descripcion=txtDescripcionContacto.getText();
       contacto.registrarContacto(curp, descripcion, medio);
       contacto.mostrarInsercion(modeloContacto, curp, medio, descripcion);
       }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarContactoActionPerformed

    private void botonEliminarContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarContactoActionPerformed
        Contacto contacto = new Contacto();
        contacto.eliminarContacto(modeloContacto, tableContacto);
    }//GEN-LAST:event_botonEliminarContactoActionPerformed

    private void botonModificarContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarContactoActionPerformed
        try{
        Contacto contacto = new Contacto();
        String curp=cbCurpContacto.getSelectedItem().toString();
        String medio=cbMedioContacto.getSelectedItem().toString();
        String descripcion=txtDescripcionContacto.getText();
        contacto.modificarContacto(tableContacto, curp, descripcion, medio);
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonModificarContactoActionPerformed

    private void botonAgregarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarEmpleadoActionPerformed
        try{
        Empleado empleado = new Empleado();
        String curp=cbCurpEmpleado.getSelectedItem().toString();
        String pue=cbPuestoEmpleado.getSelectedItem().toString();
        float sueldo=Float.parseFloat(txtSueldoEmpleado.getText());
        String suc=cbSucursalEmpleado.getSelectedItem().toString();
        int puesto = empleado.numeroPuesto(pue);
        int sucursal = empleado.numeroSucursal(suc);
        
        Date date = dataFechaInicioEmpleado.getDate();
        long d=date.getTime();
        java.sql.Date fi = new java.sql.Date(d);
        
        Date date2 = dataFechaFinEmpleado.getDate();
        long d2=date2.getTime();
        java.sql.Date fin = new java.sql.Date(d2);
        
        String tuno=cbTurnoEmpleado.getSelectedItem().toString();
        
        empleado.registrarEmpleado(curp, fi, fin, tuno, sueldo, sucursal, puesto);
        int contrato=empleado.numeroContrato(curp,fi);
        empleado.mostrarInsercion(modeloEmpleado,contrato ,curp, fi, fin, tuno, sueldo, suc, pue);
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarEmpleadoActionPerformed

    private void botonEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarEmpleadoActionPerformed
        Empleado empleado = new Empleado();
        empleado.eliminarEmpleado(modeloEmpleado, tableEmpleado);
    }//GEN-LAST:event_botonEliminarEmpleadoActionPerformed

    private void botonModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarEmpleadoActionPerformed
        try{
        Empleado empleado = new Empleado();
        String curp=cbCurpEmpleado.getSelectedItem().toString();
        String pue=cbPuestoEmpleado.getSelectedItem().toString();
        int puesto=empleado.numeroPuesto(pue);
        float sueldo=Float.parseFloat(txtSueldoEmpleado.getText());
        String suc = cbSucursalEmpleado.getSelectedItem().toString();
        int sucursal=empleado.numeroSucursal(suc);
        
        Date date = dataFechaInicioEmpleado.getDate();
        long d=date.getTime();
        java.sql.Date fi = new java.sql.Date(d);
        
        Date date2 = dataFechaFinEmpleado.getDate();
        long d2=date2.getTime();
        java.sql.Date fin = new java.sql.Date(d2);
        
        String turno=cbTurnoEmpleado.getSelectedItem().toString();
        empleado.modificarEmpleado(tableEmpleado, curp, pue, puesto, sueldo, suc, sucursal, fi, fin, turno);
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonModificarEmpleadoActionPerformed

    private void botonChecarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonChecarEntradaActionPerformed
        Checador checador = new Checador();
        int contrato=Integer.parseInt(cbContratoChecador.getSelectedItem().toString());
        String fecha=checador.registrarEntrada(contrato);
        checador.mostrarInsercion(modeloChecador, contrato, fecha,checador.numeroChecador(contrato, fecha));
    }//GEN-LAST:event_botonChecarEntradaActionPerformed

    private void botonAgregarPagoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarPagoEmpleadoActionPerformed
        try{
        PagoEmpleado pe = new PagoEmpleado();
        int contrato=Integer.parseInt(cbContratoPagoEmpleado.getSelectedItem().toString());
        float monto=Float.parseFloat(txtMontoPagoEmpleado.getText());
        Date date = dateFechaPagoEmpleado.getDate();
        String ti=cbTipoPagoEmpleado.getSelectedItem().toString();
        int tipo=pe.numeroTipoPago(ti);
        long d=date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
        pe.registrarPago(contrato, monto, fecha,tipo);
        pe.mostrarPago(modeloPagoEmpleado, contrato,ti,fecha, monto);
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarPagoEmpleadoActionPerformed

    private void botonEliminarPagoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarPagoEmpleadoActionPerformed
        PagoEmpleado pe = new PagoEmpleado();
        pe.eliminarPago(modeloPagoEmpleado, tablePagoEmpleado);
    }//GEN-LAST:event_botonEliminarPagoEmpleadoActionPerformed

    private void botonModificarPagoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarPagoEmpleadoActionPerformed
        try{
        PagoEmpleado pe = new PagoEmpleado();
        int contrato=Integer.parseInt(cbContratoPagoEmpleado.getSelectedItem().toString());
        float monto=Float.parseFloat(txtMontoPagoEmpleado.getText());
        String ti=cbTipoPagoEmpleado.getSelectedItem().toString();
        int tipo=pe.numeroTipoPago(ti);
        Date date = dateFechaPagoEmpleado.getDate();
        long d=date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
        pe.modificarPago(tablePagoEmpleado, contrato,tipo,ti, monto, fecha);
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonModificarPagoEmpleadoActionPerformed

    private void botonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarProductoActionPerformed
       try{
       Producto p = new Producto();
       int codbar=Integer.parseInt(txtCodigoBarrasProducto.getText());
       String nombre=txtNombreProducto.getText();
       String tip=cbTipoProducto.getSelectedItem().toString();
       int tipo=p.numeroTipo(tip);
       System.err.println(tipo);
       String marca=txtMarcaProducto.getText();
       String tem=cbTemporadaProducto.getSelectedItem().toString();
       int temporada=p.numeroTemporada(tem);
       System.err.println(temporada);
       String modelo=txtModeloProducto.getText();
       String mat = cbMaterialProducto.getSelectedItem().toString();
       int material =p.numeroMaterial(mat);
       System.err.println(material);
       String cat=cbCategoriaProducto.getSelectedItem().toString();
       int categoria=p.numeroCategoria(cat);
       System.err.println(categoria);
       String col=cbColorProducto.getSelectedItem().toString();
       int color=p.numeroColor(col);
       System.err.println(color);
       p.registrarProducto(codbar, nombre, tipo, marca, temporada, modelo, material, color, categoria);
       p.mostrarInsercion(modeloProducto, codbar, nombre, tip, marca, modelo, col, cat, mat, tem);
       
       String barras=String.valueOf(codbar);
       rsscalelabel.RSScaleLabel.setScaleLabel(labelImagen,"src/Productos/"+barras+".jpg");
       }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarProductoActionPerformed

    private void botonEliminarSucursal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarSucursal1ActionPerformed
        Producto pro = new Producto();
        pro.eliminarProducto(modeloProducto, tableProducto);
    }//GEN-LAST:event_botonEliminarSucursal1ActionPerformed

    private void botonModificarSucursal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarSucursal1ActionPerformed
       try{
       Producto p = new Producto();
       int codbar=Integer.parseInt(txtCodigoBarrasProducto.getText());
       String nombre=txtNombreProducto.getText();
       String tip=cbTipoProducto.getSelectedItem().toString();
       int tipo=p.numeroTipo(tip);
       System.err.println(tipo);
       String marca=txtMarcaProducto.getText();
       String tem=cbTemporadaProducto.getSelectedItem().toString();
       int temporada=p.numeroTemporada(tem);
       System.err.println(temporada);
       String modelo=txtModeloProducto.getText();
       String mat = cbMaterialProducto.getSelectedItem().toString();
       int material =p.numeroMaterial(mat);
       System.err.println(material);
       String cat=cbCategoriaProducto.getSelectedItem().toString();
       int categoria=p.numeroCategoria(cat);
       System.err.println(categoria);
       String col=cbColorProducto.getSelectedItem().toString();
       int color=p.numeroColor(col);
       p.modificarProducto(tableProducto, codbar, nombre, tip, tipo, modelo, marca, col, color, cat, categoria, mat, material, tem, temporada);
       }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonModificarSucursal1ActionPerformed

    private void botonAgregarMaxMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarMaxMinActionPerformed
        try{
        MinimoMaximo mm = new MinimoMaximo();
        int codbar=Integer.parseInt(txtCodigoBarrasMaximo.getText());
        int minimo=Integer.parseInt(spMinimo.getValue().toString());
        int maximo=Integer.parseInt(spMaximo.getValue().toString());
        String sucursal=cbSucursalMaxMin.getSelectedItem().toString();
        int cal=mm.numeroSucursal(sucursal);
        String nombre=mm.nombreProducto(codbar);
        
        mm.registrarMinimoMaximo(codbar, minimo, maximo, cal);
        mm.mostrarInsercion(modeloMinmax, codbar, nombre, minimo, maximo, sucursal);
        }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarMaxMinActionPerformed

    private void botonEliminarMaxMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarMaxMinActionPerformed
       MinimoMaximo mm = new MinimoMaximo();
       mm.eliminarMinimoMaximo(modeloMinmax, tableMinMax);
    }//GEN-LAST:event_botonEliminarMaxMinActionPerformed

    private void botonModificarMaxMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarMaxMinActionPerformed
        try{
        MinimoMaximo mm = new MinimoMaximo();
        int codbar=Integer.parseInt(txtCodigoBarrasMaximo.getText());
        int minimo=Integer.parseInt(spMinimo.getValue().toString());
        int maximo=Integer.parseInt(spMaximo.getValue().toString());
        String calle=cbSucursalMaxMin.getSelectedItem().toString();
        String nombre=mm.nombreProducto(codbar);
        mm.modificarMinimoMaximo(tableMinMax, codbar, minimo, maximo, calle,nombre);
        }catch(Exception n){
        new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
        System.err.println(n);        
        }
    }//GEN-LAST:event_botonModificarMaxMinActionPerformed

    private void botonAgregarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarPrecioActionPerformed
       try{
       Precio pre = new Precio();
       int codbar=Integer.parseInt(txtCodigoBarrasPrecio.getText());
       float precio=Float.parseFloat(txtPrecioPrecio.getText());
       Date date = dataFechaPrecio.getDate();
       long d=date.getTime();
       java.sql.Date fecha = new java.sql.Date(d);
       pre.registrarPrecio(codbar, precio, fecha);
       pre.mostrarInsercion(modeloPrecio, codbar, precio, fecha);
       }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarPrecioActionPerformed

    private void botonEliminarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarPrecioActionPerformed
        Precio pre = new Precio();
        pre.eliminarPrecio(modeloPrecio, tablePrecio);
    }//GEN-LAST:event_botonEliminarPrecioActionPerformed

    private void botonModificarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarPrecioActionPerformed
       try{
       Precio pre = new Precio();
       int codbar=Integer.parseInt(txtCodigoBarrasPrecio.getText());
       float precio=Float.parseFloat(txtPrecioPrecio.getText());
       Date date = dataFechaPrecio.getDate();
       long d=date.getTime();
       java.sql.Date fecha = new java.sql.Date(d);
       pre.modificarPrecio(tablePrecio, codbar, precio, fecha);
       }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonModificarPrecioActionPerformed

    private void rSMaterialButtonRectangle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle5ActionPerformed
        ReporteVenta rv = new ReporteVenta();
    }//GEN-LAST:event_rSMaterialButtonRectangle5ActionPerformed

    private void botonAgregarRegaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarRegaloActionPerformed
        try{
        Regalo regalo = new Regalo();
        String sucursal=cbSucursalRegaloR.getSelectedItem().toString();
        int codbar=Integer.parseInt(txtCodigoBarrasRegalo.getText());
        int contrato=Integer.parseInt(cbContratoRegaloR.getSelectedItem().toString());
        int cantidad=Integer.parseInt(spCantidadRegalo.getValue().toString());
        int suc=regalo.numeroSucursal(sucursal);
        if(regalo.existenciaProducto(codbar, suc, cantidad)){
        regalo.agregarRegalo(modeloRegalo, codbar, sucursal, cantidad, contrato);
        }else{
         new rojerusan.RSNotifyFade("ERROR", "No hay la cantidad o el producto solicitado!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
          
        }
       }catch(Exception n){
       new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
       System.err.println(n);        
       }
    }//GEN-LAST:event_botonAgregarRegaloActionPerformed

    private void botonConsultarRegaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarRegaloActionPerformed
        try {
            Regalo gift = new Regalo();
            int filas=tableRegalo.getRowCount();
            if(filas>0){
            gift.registrarRegalos(tableRegalo);
            modeloRegalo.setRowCount(0);
                    
            }else{
             new rojerusan.RSNotifyFade("ERROR", "No hay datos agregados a la tabla!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
        }  
        } catch (Exception e) {
        new rojerusan.RSNotifyFade("ERROR", "Ha ocurrido un error al registrar el regalo!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
        
        }
    }//GEN-LAST:event_botonConsultarRegaloActionPerformed

    private void botonChecarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonChecarSalidaActionPerformed
        Checador checador = new Checador();
        checador.registrarSalida(tableChecador);
    }//GEN-LAST:event_botonChecarSalidaActionPerformed

    private void botonServicioMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonServicioMasActionPerformed
        ServicioServicio ss = new ServicioServicio(); 
    }//GEN-LAST:event_botonServicioMasActionPerformed

    private void botonAgregarMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarMaterialActionPerformed
        MaterialM mm = new MaterialM();
    }//GEN-LAST:event_botonAgregarMaterialActionPerformed

    private void botonAgregarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarTipoActionPerformed
        TipoT tt = new TipoT();
    }//GEN-LAST:event_botonAgregarTipoActionPerformed

    private void botonAgregarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarColorActionPerformed
       ColorC cc = new ColorC();
    }//GEN-LAST:event_botonAgregarColorActionPerformed

    private void botonAgregarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarCategoriaActionPerformed
        CategoriaC cc = new CategoriaC();
    }//GEN-LAST:event_botonAgregarCategoriaActionPerformed

    private void botonAgregarPuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarPuestoActionPerformed
        PuestoP pp = new PuestoP();
    }//GEN-LAST:event_botonAgregarPuestoActionPerformed

    private void botonAgregarResurtirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarResurtirActionPerformed
        try{
            Resurtir res = new Resurtir();
            String proveedor=cbProveedorResurtir.getSelectedItem().toString();
            String sucursal=cbSucursalResurtir.getSelectedItem().toString();
            int factura =Integer.parseInt(txtfacturaResurtir.getText());
            
            Date date = dataFechaResurtir.getDate();
            long d=date.getTime();
            java.sql.Date fecha = new java.sql.Date(d); 
            
            int codbar =Integer.parseInt(txtCodigoBarrasRenRes.getText());
            float ppu = Float.parseFloat(txtPPPRenRes.getText());
            float numero=Float.parseFloat(txtNumeroCalzadoRenres.getText());
            
            Date datecad = dataFechaCaducidadRenRes.getDate();
            long da=datecad.getTime();
            java.sql.Date fechacad = new java.sql.Date(da); 
            
            int cantidad = Integer.parseInt(spCantidadRenRes.getValue().toString());
            int existencia =Integer.parseInt(spBajaRenRes.getValue().toString());
            res.agregarProducto(modeloResurtir, proveedor, sucursal, factura, fecha, codbar, ppu, numero, fechacad, cantidad, existencia);
                    
        }catch(Exception e){
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos ingresadaos son incorrectos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                
        }
    }//GEN-LAST:event_botonAgregarResurtirActionPerformed

    private void botoneliminarResurtirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoneliminarResurtirActionPerformed
        Resurtir res = new Resurtir();
        res.eliminarProducto(modeloResurtir, tableResurtir);
    }//GEN-LAST:event_botoneliminarResurtirActionPerformed

    private void botonLimpiarResurtirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarResurtirActionPerformed
       txtCodigoBarrasRenRes.setText("");
       txtPPPRenRes.setText("");
       txtNumeroCalzadoRenres.setText("");
       spCantidadRenRes.setValue(0);
       spBajaRenRes.setValue(0);
       dataFechaCaducidadRenRes.setDate(null);
    }//GEN-LAST:event_botonLimpiarResurtirActionPerformed

    private void botonLimpiarTodoResurtirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarTodoResurtirActionPerformed
       txtfacturaResurtir.setText("");
       txtCodigoBarrasRenRes.setText("");
       txtPPPRenRes.setText("");
       txtNumeroCalzadoRenres.setText("");
       spCantidadRenRes.setValue(0);
       spBajaRenRes.setValue(0);
       dataFechaResurtir.setDate(null);
       dataFechaCaducidadRenRes.setDate(null);
       int filas=tableResurtir.getRowCount();
       System.err.println(filas);
       if(filas!=0){
                modeloResurtir.setRowCount(0);
        }
        
    }//GEN-LAST:event_botonLimpiarTodoResurtirActionPerformed

    private void botonConcluirResurtir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConcluirResurtir1ActionPerformed
        Resurtir res = new Resurtir();
        res.registrarResurtido(tableResurtir);
        int filas=tableResurtir.getRowCount();
        System.err.println(filas);
        if(filas!=0){
                modeloResurtir.setRowCount(0);
        }
    }//GEN-LAST:event_botonConcluirResurtir1ActionPerformed

    private void botonActualizarInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarInventarioActionPerformed
        DefaultTableModel modeloInventario=new DefaultTableModel();
        Inventario inv = new Inventario();
        Venta v = new Venta();
        String sucu=cbSucursalInventario.getSelectedItem().toString();
        int sucursal=v.numeroSucursal(sucu);
        if("".equals(txtCodigoBarrasInventario.getText())){
        inv.mostrarInventario(modeloInventario, tableInventario,sucursal,0);
        }else{ 
        int codbar=Integer.parseInt(txtCodigoBarrasInventario.getText());
        inv.mostrarInventario(modeloInventario, tableInventario,sucursal,codbar);
        }
    }//GEN-LAST:event_botonActualizarInventarioActionPerformed

    private void botonMostraNumerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMostraNumerosActionPerformed
        NumeroCalzado nc = new NumeroCalzado();
    }//GEN-LAST:event_botonMostraNumerosActionPerformed

    private void botonImprimirTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonImprimirTicketActionPerformed
       if(Float.parseFloat(txtRestantePago.getText().toString())<=0){
           Venta venta = new Venta();
           int contrato=Integer.parseInt(cbContratoVenta.getSelectedItem().toString());
           String sucur=cbSucursalVenta.getSelectedItem().toString();
           int sucursal=venta.numeroSucursal(sucur);
           float total=Float.parseFloat(txtTotalVenta.getText());
           int resp=showConfirmDialog(null, "¿Desea Concluir La Compra?");
           if(resp==YES_OPTION){
           venta.concluirCompra(tableVenta, tablePago, contrato, sucursal, total);
           int respuesta=showConfirmDialog(null, "¿Desea Imprimir el Ticket?");
                
        if(respuesta==YES_OPTION){
            try {
                //hacemos que el objeto se reconozca como grafico, a modo de que se pueda renderizar, para crear la imagen a imprimir
                PrinterJob pj = PrinterJob.getPrinterJob();
                pj.setPrintable(this);
                //si selecciona aceptar en la ventana, el valor sera true
                boolean res=pj.printDialog();
                if(res){
                    //en caso de que si sea true, se va a imprimir
                    pj.print();
                    int filasVenta=tableVenta.getRowCount();
                    if(filasVenta>0){
                    modeloVenta.setRowCount(0);
                    }
                    int filasPago=tablePago.getRowCount();
                    if(filasPago>0){
                    modeloPago.setRowCount(0);
                    }
                    txtCodigoBarrasVenta.setText("0");
                    txtCantidadVenta.setText("0");
                    txtTotalVenta.setText("0");
                    txtCantidadPago.setText("0");
                    txtRestantePago.setText("0");
                }
            } catch (Exception e) {
                new rojerusan.RSNotifyFade("ERROR", "Error Al Imprimir El Ticket", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
                System.err.println("salida"+e);
                 int filasVenta=tableVenta.getRowCount();
                if(filasVenta>0){
                modeloVenta.setRowCount(0);
                }
                int filasPago=tablePago.getRowCount();
                if(filasPago>0){
                modeloPago.setRowCount(0);
                }
                txtCodigoBarrasVenta.setText("0");
                txtCantidadVenta.setText("0");
                txtTotalVenta.setText("0");
                txtCantidadPago.setText("0");
                txtRestantePago.setText("0");
            }
        }
       }   
       }
       else{
         new rojerusan.RSNotifyFade("ADVERTENCIA", "Aun falta monto a pagar!", 1, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
                  
       }
    }//GEN-LAST:event_botonImprimirTicketActionPerformed

    private void botonEliminarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarPagoActionPerformed
       Venta venta = new Venta();
       float total=venta.eliminarPago(modeloPago, tablePago);
       float tot=Float.parseFloat(txtRestantePago.getText());
       txtRestantePago.setText(String.valueOf(tot+total));
    }//GEN-LAST:event_botonEliminarPagoActionPerformed

    private void botonAgregarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarPagoActionPerformed
       try{
            Venta venta = new Venta();
            //int folio=Integer.parseInt(txtFoliopago.getText());
            String formapago = cbFormaPagoPago.getSelectedItem().toString();
            float cantidad = Float.parseFloat(txtCantidadPago.getText());
            venta.agregarPagoTicket(modeloPago, formapago, cantidad);
            float total=Float.parseFloat(txtRestantePago.getText());
            txtRestantePago.setText(String.valueOf(total-cantidad));
        }catch(Exception n){
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            System.err.println(n);
        }
    }//GEN-LAST:event_botonAgregarPagoActionPerformed

    private void botonLimpiarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarVentaActionPerformed
        txtCodigoBarrasVenta.setText("");
        txtCantidadVenta.setText("");
    }//GEN-LAST:event_botonLimpiarVentaActionPerformed

    private void botonEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarVentaActionPerformed
        Venta sale = new Venta();
        float total=sale.eliminarProducto(modeloVenta, tableVenta);
        float tot=Float.parseFloat(txtTotalVenta.getText());
        txtTotalVenta.setText(String.valueOf(tot-total));
        txtRestantePago.setText(String.valueOf(tot-total));
    }//GEN-LAST:event_botonEliminarVentaActionPerformed

    private void botonAgregarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarVentaActionPerformed
       // try{
            Venta sale = new Venta();
            int codbar=Integer.parseInt(txtCodigoBarrasVenta.getText());
            int cantidad=Integer.parseInt(txtCantidadVenta.getText());
            String suc=cbSucursalVenta.getSelectedItem().toString();
            int sucursal=sale.numeroSucursal(suc);
            if(sale.existenciaProducto(codbar, sucursal, cantidad)){
            String nombre=sale.nombreProducto(codbar);
            float precio=sale.precioProducto(codbar);
            float total=precio*cantidad;
            float tot=Float.parseFloat(txtTotalVenta.getText());
            txtTotalVenta.setText(String.valueOf(tot+total));
            txtRestantePago.setText(String.valueOf(tot+total));
            sale.agregarProductoTicket(modeloVenta, codbar, nombre, cantidad, precio, total);
            }else{
               new rojerusan.RSNotifyFade("Error", "No hay la cantidad del producto suficiente!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);
             
            }
            
            
        //}catch(Exception n){
          //  new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o datos no coinciden con los solicitados!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);
            //System.err.println(n);
        //}
    }//GEN-LAST:event_botonAgregarVentaActionPerformed

    private void txtRestantePagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRestantePagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRestantePagoActionPerformed

    private void botonAgregarHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarHorarioActionPerformed
        try{
        Horario hor = new Horario();
        if(hor.habilitarHorario(2)){
        String entrada=txtHoraEntrada.getText().toString();
        String salida=txtHoraSalida.getText().toString();
        String comida=txtHoraComida.getText().toString();
        int contrato=Integer.parseInt(cbContratoHorario.getSelectedItem().toString());
     
        Date date = dateDiaDescanso.getDate();
        long da=date.getTime();
        java.sql.Date descanso = new java.sql.Date(da);
        fecha=hor.registrarHorario(modeloHorario,entrada, salida, comida, descanso, contrato);
        }else{
        new rojerusan.RSNotifyFade("NO DISPONIBLE", "Hoy no es dia de registrar horario!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
        }
        }catch(Exception e){
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o la informacion es incorrecta!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
        
        }
        
    }//GEN-LAST:event_botonAgregarHorarioActionPerformed

    private void cbMunicipioSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMunicipioSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMunicipioSucursalActionPerformed

    private void cbContratoVentaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbContratoVentaItemStateChanged
        
    }//GEN-LAST:event_cbContratoVentaItemStateChanged

    private void cbSucursalVentaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSucursalVentaItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Calle calle = (Calle) cbSucursalVenta.getSelectedItem();
            System.out.println(cbSucursalVenta.getSelectedItem());
            ContratoAnidado ca = new ContratoAnidado();
            DefaultComboBoxModel modelContratoVenta = new DefaultComboBoxModel(ca.mostrarContratos(calle.getClave()));
            cbContratoVenta.setModel(modelContratoVenta);
        }
    }//GEN-LAST:event_cbSucursalVentaItemStateChanged

    private void cbSucursalHorarioHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSucursalHorarioHItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Calle calle = (Calle) cbSucursalHorarioH.getSelectedItem();
            System.out.println(cbSucursalHorarioH.getSelectedItem());
            ContratoAnidado ca = new ContratoAnidado();
            DefaultComboBoxModel modelContratoHorario = new DefaultComboBoxModel(ca.mostrarContratos(calle.getClave()));
            cbContratoHorario.setModel(modelContratoHorario);
        }
    }//GEN-LAST:event_cbSucursalHorarioHItemStateChanged

    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        ReporteInventario ri = new ReporteInventario();
    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        ReporteCompra rc = new ReporteCompra();
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void rSMaterialButtonRectangle6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle6ActionPerformed
        ReporteCaja rc = new ReporteCaja();
    }//GEN-LAST:event_rSMaterialButtonRectangle6ActionPerformed

    private void cbSucursalVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSucursalVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSucursalVentaActionPerformed

    private void cbContratoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbContratoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbContratoVentaActionPerformed

    private void cbSucursalRegaloRItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSucursalRegaloRItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            //llamamos la variable y la igualamos al valor del ComboBox, debemos hacer un casteo ya que es de tipo objeto
            Calle calle = (Calle) cbSucursalRegaloR.getSelectedItem();
            System.out.println(cbSucursalRegaloR.getSelectedItem());
            ContratoAnidado ca = new ContratoAnidado();
            DefaultComboBoxModel modelContratoRegalo = new DefaultComboBoxModel(ca.mostrarContratos(calle.getClave()));
            cbContratoRegaloR.setModel(modelContratoRegalo);
        }
    }//GEN-LAST:event_cbSucursalRegaloRItemStateChanged

    private void cbContratoRegaloRItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbContratoRegaloRItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbContratoRegaloRItemStateChanged

    private void botonBusquedaAvanzadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBusquedaAvanzadaActionPerformed
        BusquedaInventario bi = new BusquedaInventario();
       
    }//GEN-LAST:event_botonBusquedaAvanzadaActionPerformed

    private void botonAgregarSucursalServicio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarSucursalServicio1ActionPerformed
       try{
        Intercambio xch = new Intercambio();
        int codbar=Integer.parseInt(txtCodigoBarrasIntercambio.getText().toString());
        String sa=cbSucursalSaleIntercambio.getSelectedItem().toString();
        int sale=xch.numeroSucursal(sa);
        String en=cbSucursalEntraIntercambio.getSelectedItem().toString();
        int entra=xch.numeroSucursal(en);
        int cantidad=Integer.parseInt(txtCantidadIntercambio.getText().toString());
        int contrato=Integer.parseInt(cbContratoIntercambio.getSelectedItem().toString());
        Date date = dateFechaIntercambio.getDate();
        long da=date.getTime();
        java.sql.Date fecha = new java.sql.Date(da);
        xch.agregarIntercambio(modeloIntercambio, codbar, sa, en, cantidad, contrato, fecha);
        xch.concluirIntercambio(cantidad, codbar, sale, entra, contrato, fecha);
       }catch(Exception e){
           System.err.println(e);
           new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos no son correctos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       }
    }//GEN-LAST:event_botonAgregarSucursalServicio1ActionPerformed

    private void botonAgregarColor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarColor1ActionPerformed
        try {
            Cambio c = new Cambio();
            int folio=Integer.parseInt(txtFolioCambio.getText().toString());
            int codbaentra=Integer.parseInt(txtEntraCambio.getText().toString());
            int codbarsale=Integer.parseInt(txtSaleCambio.getText().toString());
            String descripcion=txtDescripcionCambio.getText().toString();
            Date date = dataFechaCambio.getDate();
            long da=date.getTime();
            java.sql.Date fecha = new java.sql.Date(da);
            String sucu=cbSucursalCambio.getSelectedItem().toString();
            int sucursal=c.numeroSucursal(sucu);
            float num=Float.parseFloat(txtNumeroCambio.getText().toString());
            int numero=c.numeroZapato(num);
            int contrato=Integer.parseInt(cbContratoCambio.getSelectedItem().toString());
            if(c.verificarZapato(folio)){
                if(c.verificarExiste(codbarsale, numero, sucursal)){
                    c.agregarCambio(modeloCambio, folio, codbaentra, codbarsale, descripcion, fecha, contrato, sucu,num);
                }else{
                   new rojerusan.RSNotifyFade("ADVERTENCIA", "No hay en existencia el zapato que desea!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
        
                }
            }else{
             new rojerusan.RSNotifyFade("ADVERTENCIA", "No se puede hacer el cambio, tiene mas de un mes la compra!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
          
            }
        } catch (Exception e) {
            System.err.println(e);
           new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos no son correctos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       
        }
    }//GEN-LAST:event_botonAgregarColor1ActionPerformed

    private void botonConsultarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarColorActionPerformed
        try {
            Cambio c = new Cambio();
            c.concluirCambio(tableCambio, tableCambioPago);
                    int filas=tableCambio.getRowCount();
                    if(filas>0){
                    modeloCambio.setRowCount(0);
                    }
                    int filasPago=tableCambioPago.getRowCount();
                    if(filasPago>0){
                    modeloCambioPago.setRowCount(0);
                    }
        } catch (Exception e) {
            System.err.println(e);
            new rojerusan.RSNotifyFade("ERROR", "Ha ocurrido un error!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.ERROR).setVisible(true);    
       
        }
            
    }//GEN-LAST:event_botonConsultarColorActionPerformed

    private void botonAgregarColor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAgregarColor2ActionPerformed
        try{
            Cambio c = new Cambio();
            String form=cbFormaPagoCambio.getSelectedItem().toString();
            int forma=c.numeroForma(form);
            float monto=Float.parseFloat(txtMontoCambioPago.getText().toString());
            c.agregarPago(modeloCambioPago, forma, monto);
        }catch(Exception e){
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos no son correctos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       
        }
    }//GEN-LAST:event_botonAgregarColor2ActionPerformed

    private void botonDevolverProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDevolverProductoActionPerformed
        try{
            Devolucion dev = new Devolucion();
            String sucu=cbSucursalDevolver.getSelectedItem().toString();
            int sucursal=dev.numeroSucursal(sucu);
            String prov=cbProveedorDevolver.getSelectedItem().toString();
            int proveedor=dev.numeroProveedor(prov);
            String factura=txtFacturaDevolver.getText().toString();
            int codbar=Integer.parseInt(txtCodigoBarrasDevolucion.getText().toString());
            int cantidad=Integer.parseInt(txtCantidadDevolucion.getText().toString());
            dev.bajaDevolucion(cantidad, codbar, factura, proveedor, sucursal);
            
        }catch(Exception e){
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos no son correctos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       
        }
    }//GEN-LAST:event_botonDevolverProductoActionPerformed

    private void botonFiltrarDevolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFiltrarDevolucionActionPerformed
       try{
            Devolucion dev = new Devolucion();
            String sucu=cbSucursalDevolver.getSelectedItem().toString();
            int sucursal=dev.numeroSucursal(sucu);
            String prov=cbProveedorDevolver.getSelectedItem().toString();
            int proveedor=dev.numeroProveedor(prov);
            String factura=txtFacturaDevolver.getText().toString();
            dev.filtrarResurtido(tableDevolucion, factura, proveedor, sucursal);
        }catch(Exception e){
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos no son correctos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       
        }
    }//GEN-LAST:event_botonFiltrarDevolucionActionPerformed

    private void botonDevolverTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDevolverTodoActionPerformed
       try{
            Devolucion dev = new Devolucion();
            String sucu=cbSucursalDevolver.getSelectedItem().toString();
            int sucursal=dev.numeroSucursal(sucu);
            String prov=cbProveedorDevolver.getSelectedItem().toString();
            int proveedor=dev.numeroProveedor(prov);
            String factura=txtFacturaDevolver.getText().toString();
            dev.bajaDevolucionTodo(tableDevolucion, factura, proveedor, sucursal);
        }catch(Exception e){
            System.err.println(e);
            new rojerusan.RSNotifyFade("ADVERTENCIA", "Algunos campos estan vacios o los datos no son correctos!", 3, RSNotifyFade.PositionNotify.BottomRight, RSNotifyFade.TypeNotify.WARNING).setVisible(true);    
       
        }
    }//GEN-LAST:event_botonDevolverTodoActionPerformed

    private void botonDetallesIntercambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDetallesIntercambioActionPerformed
        IntercambioI ii = new IntercambioI();
    }//GEN-LAST:event_botonDetallesIntercambioActionPerformed

    private void botonDetallesCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDetallesCambioActionPerformed
        CambioC cc = new CambioC();
    }//GEN-LAST:event_botonDetallesCambioActionPerformed

    private void botonDetallesIntercambio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDetallesIntercambio1ActionPerformed
        RegaloR rr = new RegaloR();
    }//GEN-LAST:event_botonDetallesIntercambio1ActionPerformed

    private void botonZapatoMasVendidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatoMasVendidoActionPerformed
        ZapatoMasVendido zm = new ZapatoMasVendido();
    }//GEN-LAST:event_botonZapatoMasVendidoActionPerformed

    private void botonEmpleadoMasVendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEmpleadoMasVendeActionPerformed
       EmpleadoMasVende emv = new EmpleadoMasVende();
    }//GEN-LAST:event_botonEmpleadoMasVendeActionPerformed

    private void botonSucursalIngresoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSucursalIngresoVentaActionPerformed
        SucursalMasVenta ss = new SucursalMasVenta();
    }//GEN-LAST:event_botonSucursalIngresoVentaActionPerformed

    private void botonZapatoCaducadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatoCaducadoActionPerformed
        ZapatoMasCaducados zc = new ZapatoMasCaducados();
    }//GEN-LAST:event_botonZapatoCaducadoActionPerformed

    private void botonFormaPagoUsadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFormaPagoUsadaActionPerformed
        FormaPagoMasUsada fp = new FormaPagoMasUsada();
    }//GEN-LAST:event_botonFormaPagoUsadaActionPerformed

    private void botonZapatoRegalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatoRegalaActionPerformed
        ZapatoMasRegala zz = new ZapatoMasRegala();
        
    }//GEN-LAST:event_botonZapatoRegalaActionPerformed

    private void botonProveedorCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProveedorCompraActionPerformed
        ProveedorMasVeces pv = new ProveedorMasVeces();
    }//GEN-LAST:event_botonProveedorCompraActionPerformed

    private void botonSucursalGastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSucursalGastaActionPerformed
        ServicioMasGasta sg = new ServicioMasGasta();
    }//GEN-LAST:event_botonSucursalGastaActionPerformed

    private void botonZapatosResurtenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatosResurtenActionPerformed
        ZapatoMasResurtido zr = new ZapatoMasResurtido();
    }//GEN-LAST:event_botonZapatosResurtenActionPerformed

    private void botonZapatoCompraAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatoCompraAltaActionPerformed
        ZapatoCompraAlta za = new ZapatoCompraAlta();
    }//GEN-LAST:event_botonZapatoCompraAltaActionPerformed

    private void botonZapatoMasIntercambiadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatoMasIntercambiadoActionPerformed
        ZapatoMasIntercambiado zi = new ZapatoMasIntercambiado();
    }//GEN-LAST:event_botonZapatoMasIntercambiadoActionPerformed

    private void botonZapatoNoVendidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonZapatoNoVendidoActionPerformed
        ZapatoNoVendido zv = new ZapatoNoVendido();
    }//GEN-LAST:event_botonZapatoNoVendidoActionPerformed

    
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelContacto;
    private rojerusan.RSMaterialButtonRectangle b;
    private rojerusan.RSMaterialButtonRectangle botonActualizarInventario;
    private rojerusan.RSMaterialButtonCircle botonAgregarCategoria;
    private rojerusan.RSMaterialButtonCircle botonAgregarColor;
    private rojerusan.RSMaterialButtonRectangle botonAgregarColor1;
    private rojerusan.RSMaterialButtonRectangle botonAgregarColor2;
    private rojerusan.RSMaterialButtonRectangle botonAgregarContacto;
    private rojerusan.RSMaterialButtonRectangle botonAgregarEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonAgregarHorario;
    private rojerusan.RSMaterialButtonCircle botonAgregarMaterial;
    private rojerusan.RSMaterialButtonRectangle botonAgregarMaxMin;
    private rojerusan.RSMaterialButtonRectangle botonAgregarPago;
    private rojerusan.RSMaterialButtonRectangle botonAgregarPagoEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonAgregarPersona;
    private rojerusan.RSMaterialButtonRectangle botonAgregarPrecio;
    private rojerusan.RSMaterialButtonRectangle botonAgregarProducto;
    private rojerusan.RSMaterialButtonRectangle botonAgregarProveedor;
    private rojerusan.RSMaterialButtonCircle botonAgregarPuesto;
    private rojerusan.RSMaterialButtonRectangle botonAgregarRegalo;
    private rojerusan.RSMaterialButtonRectangle botonAgregarResurtir;
    private rojerusan.RSMaterialButtonRectangle botonAgregarSucursalServicio;
    private rojerusan.RSMaterialButtonRectangle botonAgregarSucursalServicio1;
    private rojerusan.RSMaterialButtonCircle botonAgregarTipo;
    private rojerusan.RSMaterialButtonRectangle botonAgregarVenta;
    private rojerusan.RSMaterialButtonRectangle botonBusquedaAvanzada;
    private rojerusan.RSMaterialButtonRectangle botonChecarEntrada;
    private rojerusan.RSMaterialButtonRectangle botonChecarSalida;
    private rojerusan.RSMaterialButtonRectangle botonConcluirResurtir1;
    private rojerusan.RSMaterialButtonRectangle botonConsultarColor;
    private rojerusan.RSMaterialButtonRectangle botonConsultarContacto;
    private rojerusan.RSMaterialButtonRectangle botonConsultarEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonConsultarHorario;
    private rojerusan.RSMaterialButtonRectangle botonConsultarMaxMin;
    private rojerusan.RSMaterialButtonRectangle botonConsultarPagoEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonConsultarPersona;
    private rojerusan.RSMaterialButtonRectangle botonConsultarPrecio;
    private rojerusan.RSMaterialButtonRectangle botonConsultarProducto;
    private rojerusan.RSMaterialButtonRectangle botonConsultarProveedor;
    private rojerusan.RSMaterialButtonRectangle botonConsultarRegalo;
    private rojerusan.RSMaterialButtonRectangle botonConsultarSucursal;
    private rojerusan.RSMaterialButtonRectangle botonConsultarSucursalServicio;
    private rojerusan.RSMaterialButtonRectangle botonDetallesCambio;
    private rojerusan.RSMaterialButtonRectangle botonDetallesIntercambio;
    private rojerusan.RSMaterialButtonRectangle botonDetallesIntercambio1;
    private rojerusan.RSMaterialButtonRectangle botonDevolverProducto;
    private rojerusan.RSMaterialButtonRectangle botonDevolverTodo;
    private rojerusan.RSMaterialButtonRectangle botonEliminarContacto;
    private rojerusan.RSMaterialButtonRectangle botonEliminarEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonEliminarMaxMin;
    private rojerusan.RSMaterialButtonRectangle botonEliminarPago;
    private rojerusan.RSMaterialButtonRectangle botonEliminarPagoEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonEliminarPersona;
    private rojerusan.RSMaterialButtonRectangle botonEliminarPrecio;
    private rojerusan.RSMaterialButtonRectangle botonEliminarProveedor;
    private rojerusan.RSMaterialButtonRectangle botonEliminarSucursal;
    private rojerusan.RSMaterialButtonRectangle botonEliminarSucursal1;
    private rojerusan.RSMaterialButtonRectangle botonEliminarSucursalServicio;
    private rojerusan.RSMaterialButtonRectangle botonEliminarVenta;
    private rsbuttom.RSButtonMetro botonEmpleadoMasVende;
    private rojerusan.RSMaterialButtonRectangle botonFiltrarDevolucion;
    private rsbuttom.RSButtonMetro botonFormaPagoUsada;
    private rojerusan.RSMaterialButtonRectangle botonImprimirTicket;
    private rojerusan.RSMaterialButtonRectangle botonLimpiarResurtir;
    private rojerusan.RSMaterialButtonRectangle botonLimpiarTodoResurtir;
    private rojerusan.RSMaterialButtonRectangle botonLimpiarVenta;
    private rojerusan.RSMaterialButtonRectangle botonModificarContacto;
    private rojerusan.RSMaterialButtonRectangle botonModificarEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonModificarMaxMin;
    private rojerusan.RSMaterialButtonRectangle botonModificarPagoEmpleado;
    private rojerusan.RSMaterialButtonRectangle botonModificarPersona;
    private rojerusan.RSMaterialButtonRectangle botonModificarPrecio;
    private rojerusan.RSMaterialButtonRectangle botonModificarProveedor;
    private rojerusan.RSMaterialButtonRectangle botonModificarSucursal;
    private rojerusan.RSMaterialButtonRectangle botonModificarSucursal1;
    private rojerusan.RSMaterialButtonRectangle botonModificarSucursalServicio;
    private rojerusan.RSMaterialButtonCircle botonMostraNumeros;
    private rsbuttom.RSButtonMetro botonProveedorCompra;
    private rojerusan.RSMaterialButtonCircle botonServicioMas;
    private rsbuttom.RSButtonMetro botonSucursalGasta;
    private rsbuttom.RSButtonMetro botonSucursalIngresoVenta;
    private rsbuttom.RSButtonMetro botonZapatoCaducado;
    private rsbuttom.RSButtonMetro botonZapatoCompraAlta;
    private rsbuttom.RSButtonMetro botonZapatoMasIntercambiado;
    private rsbuttom.RSButtonMetro botonZapatoMasVendido;
    private rsbuttom.RSButtonMetro botonZapatoNoVendido;
    private rsbuttom.RSButtonMetro botonZapatoRegala;
    private rsbuttom.RSButtonMetro botonZapatosResurten;
    private rojerusan.RSMaterialButtonRectangle botoneliminarResurtir;
    private rsbuttom.RSButtonMetro btnEmpleado;
    private rsbuttom.RSButtonMetro btnInevntario;
    private rsbuttom.RSButtonMetro btnMas;
    private rsbuttom.RSButtonMetro btnReporte;
    private rsbuttom.RSButtonMetro btnSucursal;
    private rsbuttom.RSButtonMetro btnVenta;
    private javax.swing.JComboBox<String> cbCategoriaProducto;
    private javax.swing.JComboBox<String> cbCodigoPostal;
    private javax.swing.JComboBox<String> cbCodigoPostalProveedor;
    private javax.swing.JComboBox<String> cbCodigoPostalSucursal;
    private javax.swing.JComboBox<String> cbColonia;
    private javax.swing.JComboBox<String> cbColoniaProveedor;
    private javax.swing.JComboBox<String> cbColoniaSucursal;
    private javax.swing.JComboBox<String> cbColorProducto;
    private javax.swing.JComboBox<String> cbContratoCambio;
    private javax.swing.JComboBox<String> cbContratoChecador;
    private javax.swing.JComboBox<String> cbContratoHorario;
    private javax.swing.JComboBox<String> cbContratoIntercambio;
    private javax.swing.JComboBox<String> cbContratoPagoEmpleado;
    private javax.swing.JComboBox<String> cbContratoRegaloR;
    private javax.swing.JComboBox<String> cbContratoVenta;
    private javax.swing.JComboBox<String> cbCurpContacto;
    private javax.swing.JComboBox<String> cbCurpEmpleado;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbEstadoProveedor;
    private javax.swing.JComboBox<String> cbEstadoSucursal;
    private javax.swing.JComboBox<String> cbFormaPagoCambio;
    private javax.swing.JComboBox<String> cbFormaPagoPago;
    private javax.swing.JComboBox<String> cbMaterialProducto;
    private javax.swing.JComboBox<String> cbMedioContacto;
    private javax.swing.JComboBox<String> cbMunicipio;
    private javax.swing.JComboBox<String> cbMunicipioProveedor;
    private javax.swing.JComboBox<String> cbMunicipioSucursal;
    private javax.swing.JComboBox<String> cbOrientacion;
    private javax.swing.JComboBox<String> cbOrientacionProveedor;
    private javax.swing.JComboBox<String> cbOrientacionSucursal;
    private javax.swing.JComboBox<String> cbProveedorDevolver;
    private javax.swing.JComboBox<String> cbProveedorResurtir;
    private javax.swing.JComboBox<String> cbPuestoEmpleado;
    private javax.swing.JComboBox<String> cbServicio;
    private javax.swing.JComboBox<String> cbSexo;
    private javax.swing.JComboBox<String> cbSucursalCambio;
    private javax.swing.JComboBox<String> cbSucursalDevolver;
    private javax.swing.JComboBox<String> cbSucursalEmpleado;
    private javax.swing.JComboBox<String> cbSucursalEntraIntercambio;
    private javax.swing.JComboBox<String> cbSucursalHorarioH;
    private javax.swing.JComboBox<String> cbSucursalInventario;
    private javax.swing.JComboBox<String> cbSucursalMaxMin;
    private javax.swing.JComboBox<String> cbSucursalMaximoMinimoB;
    private javax.swing.JComboBox<String> cbSucursalRegaloR;
    private javax.swing.JComboBox<String> cbSucursalResurtir;
    private javax.swing.JComboBox<String> cbSucursalSaleIntercambio;
    private javax.swing.JComboBox<String> cbSucursalServicio;
    private javax.swing.JComboBox<String> cbSucursalVenta;
    private javax.swing.JComboBox<String> cbTemporadaProducto;
    private javax.swing.JComboBox<String> cbTipoPagoEmpleado;
    private javax.swing.JComboBox<String> cbTipoProducto;
    private javax.swing.JComboBox<String> cbTurnoEmpleado;
    private com.toedter.calendar.JDateChooser dataFechaCaducidadRenRes;
    private com.toedter.calendar.JDateChooser dataFechaCambio;
    private com.toedter.calendar.JDateChooser dataFechaFinEmpleado;
    private com.toedter.calendar.JDateChooser dataFechaInicioEmpleado;
    private com.toedter.calendar.JDateChooser dataFechaNacimiento;
    private com.toedter.calendar.JDateChooser dataFechaPrecio;
    private com.toedter.calendar.JDateChooser dataFechaResurtir;
    private com.toedter.calendar.JDateChooser dataFechaServicio;
    private com.toedter.calendar.JDateChooser dateDiaDescanso;
    private com.toedter.calendar.JDateChooser dateFechaIntercambio;
    private com.toedter.calendar.JDateChooser dateFechaPagoEmpleado;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel labelFotoPersona;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelUser;
    private javax.swing.JLabel labelUserImage;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel marcoTicket;
    private javax.swing.JPanel panUno;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelBotones1;
    private javax.swing.JPanel panelBotonesContacto;
    private javax.swing.JPanel panelBotonesEmpleado;
    private javax.swing.JPanel panelBotonesMaxMin;
    private javax.swing.JPanel panelBotonesPagoEmpleado;
    private javax.swing.JPanel panelBotonesPersona;
    private javax.swing.JPanel panelBotonesPersona1;
    private javax.swing.JPanel panelBotonesPrecio;
    private javax.swing.JPanel panelBotonesProducto;
    private javax.swing.JPanel panelBotonesProveedor;
    private javax.swing.JPanel panelBotonesVenta;
    private javax.swing.JPanel panelCambio;
    private javax.swing.JPanel panelChecador;
    private javax.swing.JPanel panelConsultas;
    private javax.swing.JPanel panelDevolverResurtir;
    private javax.swing.JPanel panelEmpleado;
    private javax.swing.JTabbedPane panelEmpleados;
    private javax.swing.JPanel panelFotoEmpleado;
    private javax.swing.JPanel panelHorario;
    private javax.swing.JPanel panelImagenProducto;
    private javax.swing.JPanel panelIntercambio;
    private javax.swing.JTabbedPane panelInventario;
    private javax.swing.JPanel panelInventarioInventario;
    private javax.swing.JTabbedPane panelMas;
    private javax.swing.JPanel panelMinimoMaximo;
    private javax.swing.JPanel panelPagoEmpleados;
    private javax.swing.JPanel panelPersona;
    private javax.swing.JPanel panelPrecio;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelProducto;
    private javax.swing.JPanel panelProveedor;
    private javax.swing.JPanel panelRegalo;
    private javax.swing.JPanel panelReportes;
    private javax.swing.JPanel panelResurtir;
    private javax.swing.JTabbedPane panelSucursal;
    private javax.swing.JPanel panelSucursalRegistro;
    private javax.swing.JPanel panelSucursalServicio;
    private javax.swing.JTabbedPane panelVenta;
    private javax.swing.JPanel panelVentaVenta;
    private rsbuttom.RSButtonMetro rSButtonMetro2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle5;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle6;
    private rojerusan.RSPanelsSlider rSPanelsSlider1;
    private javax.swing.JPanel recibo;
    private javax.swing.JSpinner spBajaRenRes;
    private javax.swing.JSpinner spCantidadRegalo;
    private javax.swing.JSpinner spCantidadRenRes;
    private javax.swing.JSpinner spMaximo;
    private javax.swing.JSpinner spMinimo;
    private javax.swing.JSpinner spNumero;
    private javax.swing.JSpinner spNumeroProveedor;
    private javax.swing.JSpinner spNumeroSucursal;
    private javax.swing.JTable tableCambio;
    private javax.swing.JTable tableCambioPago;
    private javax.swing.JTable tableChecador;
    private javax.swing.JTable tableContacto;
    private javax.swing.JTable tableDevolucion;
    private javax.swing.JTable tableEmpleado;
    private javax.swing.JTable tableHorario;
    private javax.swing.JTable tableIntercambio;
    private javax.swing.JTable tableInventario;
    private javax.swing.JTable tableMinMax;
    private javax.swing.JTable tablePago;
    private javax.swing.JTable tablePagoEmpleado;
    private javax.swing.JTable tablePersona;
    private javax.swing.JTable tablePrecio;
    private javax.swing.JTable tableProducto;
    private javax.swing.JTable tableProveedor;
    private javax.swing.JTable tableRegalo;
    private javax.swing.JTable tableResurtir;
    private javax.swing.JTable tableServicioSucursal;
    private javax.swing.JTable tableSucursal;
    private javax.swing.JTable tableVenta;
    private javax.swing.JTextField txtAMaternoPersona;
    private javax.swing.JTextField txtAPaternoPersona;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCalleProveedor;
    private javax.swing.JTextField txtCalleSucursal;
    private javax.swing.JTextField txtCantidadDevolucion;
    private javax.swing.JTextField txtCantidadIntercambio;
    private javax.swing.JTextField txtCantidadPago;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoBarrasDevolucion;
    private javax.swing.JTextField txtCodigoBarrasIntercambio;
    private javax.swing.JTextField txtCodigoBarrasInventario;
    private javax.swing.JTextField txtCodigoBarrasMaximo;
    private javax.swing.JTextField txtCodigoBarrasPrecio;
    private javax.swing.JTextField txtCodigoBarrasProducto;
    private javax.swing.JTextField txtCodigoBarrasRegalo;
    private javax.swing.JTextField txtCodigoBarrasRenRes;
    private javax.swing.JTextField txtCodigoBarrasVenta;
    private javax.swing.JTextField txtCurpPersona;
    private javax.swing.JTextArea txtDescripcionCambio;
    private javax.swing.JTextField txtDescripcionContacto;
    private javax.swing.JTextField txtEntraCambio;
    private javax.swing.JTextField txtFacturaDevolver;
    private javax.swing.JTextField txtFechaTicket;
    private javax.swing.JTextField txtFolioCambio;
    private javax.swing.JTextField txtFolioVenta;
    private javax.swing.JTextField txtHoraComida;
    private javax.swing.JTextField txtHoraEntrada;
    private javax.swing.JTextField txtHoraSalida;
    private javax.swing.JTextField txtMarcaProducto;
    private javax.swing.JTextField txtModeloProducto;
    private javax.swing.JTextField txtMontoCambioPago;
    private javax.swing.JTextField txtMontoPagoEmpleado;
    private javax.swing.JTextField txtMontoServicio;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNumeroCalzadoRenres;
    private javax.swing.JTextField txtNumeroCambio;
    private javax.swing.JTextField txtNumeroProveedor;
    private javax.swing.JTextField txtPPPRenRes;
    private javax.swing.JTextField txtPrecioPrecio;
    private javax.swing.JTextField txtRestantePago;
    private javax.swing.JTextField txtSaleCambio;
    private javax.swing.JTextField txtSueldoEmpleado;
    private javax.swing.JTextField txtTotalVenta;
    private javax.swing.JTextField txtfacturaResurtir;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics grafico, PageFormat formatoPagina, int indicePagina) throws PrinterException {
        //solo hara una impresion, si el indice es mayor a cero quiere decir que no imprimira mas
    if(indicePagina>0){
        return NO_SUCH_PAGE;
    }//hace que obtenga las caracteristicas del obejto, en este caso la GUI
    Graphics2D g = (Graphics2D)grafico;
    //valores para la impresion en x y en y, si lo dejamos por defecto se pegara a la esquina
    g.translate(formatoPagina.getImageableX()+30, formatoPagina.getImageableY()+30);
    //para que no se imprima al 100%, sino mas pequeño o grande, depende de la nesecidad 1.0=completo, 0.5=media
    g.scale(0.5,0.5);
    //el panel recibo es el que se va a imprimir en el grafico
    recibo.printAll(grafico);
    return PAGE_EXISTS;
    }
}
