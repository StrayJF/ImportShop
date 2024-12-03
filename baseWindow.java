package com.importshop.Constantes;

import com.importshop.Ventanas.Contabilidad;
import com.importshop.Ventanas.Facturas;
import com.importshop.Ventanas.Productos;
import com.importshop.Ventanas.ventanaPrincipal;
//Importacion de una clase que se encuentra en otra carpeta en el mismo proyecto
import com.importshop.Visual.Ventana;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;


public abstract class baseWindow extends JFrame implements Ventana{

    private boolean boleanoPersonas = false;
    private boolean boleanoProductos = false;
    private boolean boleanoFacturas = false;
    private boolean boleanoContabilidad = false;
    
    //Metodo definido paara generar la ventana
    public void window(){
        setSize(1920, 1010);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }


    //Metodo definido para generar el panel/menu izquierdo
    public void menu(){

        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(null);
        panelLateral.setBounds(0, 0, 400, getHeight());
        panelLateral.setBackground(new Color(18, 201, 168));

        //Botones

        JButton registroPersonas = new JButton("<html>Registro de<br>Personas</html>");
        registroPersonas.setBounds(200, 240, 180, 100);
        registroPersonas.setForeground(Color.WHITE);
        registroPersonas.setContentAreaFilled(false); //Fondo del boton 
        registroPersonas.setBorderPainted(false); //Borde del boton
        registroPersonas.setFocusPainted(false); //Marco del boton
        registroPersonas.setFont(new Font("Dialog", Font.PLAIN, 24));
        registroPersonas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                boleanoPersonas = true;
                if (boleanoPersonas){
                    baseWindow personas = new ventanaPrincipal();
                    personas.ejecucion();
                    personas.setVisible(true);
                    dispose();
                }
            }
        });


        JButton productos = new JButton("Productos");
        productos.setBounds(200, 450, 180, 30);
        productos.setForeground(Color.WHITE);
        productos.setContentAreaFilled(false); //Fondo del boton 
        productos.setBorderPainted(false); //Borde del boton
        productos.setFocusPainted(false); //Marco del boton
        productos.setFont(new Font("Dialog", Font.PLAIN, 24));
        productos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                boleanoProductos = true;
                if (boleanoProductos){
                    // Crear una nueva ventana de Productos y hacerla visible
                    baseWindow productos = new Productos();
                    productos.ejecucion();  // Ejecutar cualquier lógica que necesites en Productos
                    productos.setVisible(true);
                    // Ocultar ventana principal
                    dispose();
                }
            }
        });


        JButton facturas = new JButton("Facturas");
        facturas.setBounds(200, 640, 180, 30);
        facturas.setForeground(Color.WHITE);
        facturas.setContentAreaFilled(false); //Fondo del boton 
        facturas.setBorderPainted(false); //Borde del boton
        facturas.setFocusPainted(false); //Marco del boton
        facturas.setFont(new Font("Dialog", Font.PLAIN, 24));
        facturas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                boleanoFacturas = true;
                if (boleanoFacturas){
                    // Crear una nueva ventana de Productos y hacerla visible
                    baseWindow facturas = new Facturas();
                    facturas.ejecucion();  // Ejecutar cualquier lógica que necesites en Productos
                    facturas.setVisible(true);
                    // Ocultar ventana principal
                    dispose();
                }
            }
        });


        JButton sistemaContable = new JButton("<html>Sistema<br>Contable</html>");
        sistemaContable.setBounds(200, 780, 180, 100);
        sistemaContable.setForeground(Color.WHITE);
        sistemaContable.setContentAreaFilled(false); //Fondo del boton 
        sistemaContable.setBorderPainted(false); //Borde del boton
        sistemaContable.setFocusPainted(false); //Marco del boton
        sistemaContable.setFont(new Font("Dialog", Font.PLAIN, 24));
        sistemaContable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                boleanoContabilidad = true;
                if (boleanoContabilidad){
                    // Crear una nueva ventana de Productos y hacerla visible
                    baseWindow sistemaContable = new Contabilidad();
                    sistemaContable.ejecucion();  // Ejecutar cualquier lógica que necesites en Productos
                    sistemaContable.setVisible(true);
                    // Ocultar ventana principal
                    dispose();
                }
            }
        });


        //texto titulo
        JLabel titulo = new JLabel("Import Shop");
        titulo.setBounds(140, 60, 180, 50);
        titulo.setFont(new Font("Italic", Font.ITALIC, 32));
        titulo.setForeground(Color.WHITE);

        panelLateral.add(registroPersonas);
        panelLateral.add(productos);
        panelLateral.add(facturas);
        panelLateral.add(sistemaContable);
        panelLateral.add(titulo);


        //imagenes
        ImageIcon iconoRegistroOriginal = new ImageIcon("C:\\Users\\juang\\.vscode\\Java\\importshop\\src\\main\\java\\com\\importshop\\Constantes\\iamgen1.png");
        Image imagenRegistro = iconoRegistroOriginal.getImage(); // Obtener la imagen original
        Image escaladaRegistro = imagenRegistro.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Redimensionar
        ImageIcon iconoRegistro = new ImageIcon(escaladaRegistro); // Crear un nuevo ImageIcon con la imagen redimensionada
        JLabel registroImg = new JLabel(iconoRegistro);
        registroImg.setBounds(50, 198, 144, 150);
        panelLateral.add(registroImg);


        ImageIcon iconoFacturaOriginal = new ImageIcon("C:\\Users\\juang\\.vscode\\Java\\importshop\\src\\main\\java\\com\\importshop\\Constantes\\factura.png");
        Image imagenFactura = iconoFacturaOriginal.getImage();
        Image escalaFactura = imagenFactura.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon iconoFactura = new ImageIcon(escalaFactura);
        JLabel facturaImg = new JLabel(iconoFactura);
        facturaImg.setBounds(50, 390, 150, 150);
        panelLateral.add(facturaImg);


        ImageIcon iconoProductoOriginal = new ImageIcon("C:\\Users\\juang\\.vscode\\Java\\importshop\\src\\main\\java\\com\\importshop\\Constantes\\producto.png");
        Image imagenProducto = iconoProductoOriginal.getImage();
        Image escalaProducto = imagenProducto.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon iconoProducto = new ImageIcon(escalaProducto);
        JLabel productoImg = new JLabel(iconoProducto);
        productoImg.setBounds(45, 570, 150, 150);
        panelLateral.add(productoImg);


        ImageIcon iconoContabilidadOriginal = new ImageIcon("C:\\Users\\juang\\.vscode\\Java\\importshop\\src\\main\\java\\com\\importshop\\Constantes\\contabilidad.png");
        Image imagenContabilidad = iconoContabilidadOriginal.getImage();
        Image escalaContabilidad = imagenContabilidad.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon iconoContabilidad = new ImageIcon(escalaContabilidad);
        JLabel contabilidadImg = new JLabel(iconoContabilidad);
        contabilidadImg.setBounds(50, 750, 150, 150);
        panelLateral.add(contabilidadImg);


        ImageIcon iconoLogoOrignal = new ImageIcon("C:\\Users\\juang\\.vscode\\Java\\importshop\\src\\main\\java\\com\\importshop\\Constantes\\logo.png");
        Image imagenLogo = iconoLogoOrignal.getImage();
        Image escalaLogo = imagenLogo.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ImageIcon iconoLogo = new ImageIcon(escalaLogo);
        JLabel logoImg = new JLabel(iconoLogo);
        logoImg.setBounds(0, 30, 100, 100);
        panelLateral.add(logoImg);


        //Separadores
        JSeparator separador1 = new JSeparator();
        separador1.setBounds(10, 370, 380, 2); // Ajusta la posición y el tamaño según sea necesario
        panelLateral.add(separador1);

        JSeparator separador2 = new JSeparator();
        separador2.setBounds(10, 565, 380, 2); // Ajusta la posición y el tamaño según sea necesario
        panelLateral.add(separador2);

        JSeparator separador3 = new JSeparator();
        separador3.setBounds(10, 740, 380, 2); // Ajusta la posición y el tamaño según sea necesario
        panelLateral.add(separador3);

        JSeparator separador4 = new JSeparator();
        separador4.setBounds(10, 170, 380, 2); // Ajusta la posición y el tamaño según sea necesario
        panelLateral.add(separador4);

        //Añadidura del panel
        add(panelLateral);
    }

    //protected abstract void changePanel(String panelName);

    public static Connection getConexion() {
        String url = "jdbc:sqlserver://Stray\\SQLEXPRESS:1433;databaseName=Store;encrypt=true;trustServerCertificate=true;user=stray;password=stray";
        try{ 
        Connection Conexion = DriverManager.getConnection(url);
        return Conexion;    
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.toString());
            return null;
        }   
    }

    //Declaracion de restricciones a utilizar en los campo JTextField
    //Esta restriccion solo admite letras, se utiliza en txtNombre 
    protected KeyListener soloLetras = new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            e.consume();
        }
        }
    };

    //Esta restriccion solo admite valores numericos, se utiliza en txtTelefono
    protected KeyListener soloNumeros = new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();
        }
        }
    };
}
