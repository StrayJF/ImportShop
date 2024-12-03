package com.importshop.Ventanas;

//Importacion de una clase que se encuentra en otra carpeta en el mismo proyecto
import com.importshop.Constantes.baseWindow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;



public class ventanaPrincipal extends baseWindow{

    private String nombre, DNI, telefono, correo, direccion, tipo, seleccionDNI;
    private JTextField txtNombre, txtDNI, txtTelefono, txtCorreo, txtDireccion, filtroDNI;
    private JButton btnAgregar, btnRestablecer, btnModificar, btnEliminar;
    private JComboBox<String> cmbTipoPersona;
    private JTable tabla;
    private JLabel labelNombre, labelDNI, labelTelefono, labelCorreoElectronico, labelDireccion, labelTipoPersona;
    private DefaultTableModel modelo;
    private int selectedRow = -1;


    //Metodo definido utilizado para juntar todos los metodos y hacer los componentes visibles
    //Super llama al metodo definido en la clase, al no sobre-escribir el metodo en la subclase y al llamarlo, buscara automaticamente entre las capas de clases.
    public void ejecucion(){
        window(); //super.window()
        menu(); //super.menu()
        panelComplementario();
    }


    //Metodo definido para el panel derecho de la pantalla principal
    public void panelComplementario(){
        JPanel panelLateral= new JPanel();
        panelLateral.setLayout(null); //Se asigna el layout en null para poder usar coordenadas
        panelLateral.setBounds(400, 170, 1520, 840); //Coordenadas en (x, y, extencion 'x', extencion 'y')
        panelLateral.setBackground(new Color(238, 227, 227)); //Color descompuesto en RGB
        TitledBorder tituloDelBorde = BorderFactory.createTitledBorder("Registro Personas"); //Borde que funcione como marco y con un titulo
        tituloDelBorde.setTitleFont(new Font("Arial", Font.BOLD, 20)); //Fuente, tipo de letra, tamaño
        panelLateral.setBorder(tituloDelBorde); 


        //Declaracion de variables JTextField 
        txtNombre = new JTextField();
        txtDNI = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtDireccion = new JTextField();


        //Declaracion de variable JComboBox
        cmbTipoPersona = new JComboBox<>(new String[]{"Cliente", "Proveedor", "Ambos"});
        
        //Declaracion y ajustes de color de variables JButton
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(18, 195, 201));
        btnRestablecer = new JButton("Restablecer");
        btnRestablecer.setBackground(new Color(255, 87, 87));
        btnModificar = new JButton("Modificar");
        btnModificar.setBackground(new Color (18, 195, 201));
        btnEliminar = new JButton("Eliminar"); 
        btnEliminar.setBackground(new Color(255, 87, 87));


        //Esta restriccion solo admite un String que contenga el signo '@' para corroborar que sea correo electronico
        FocusListener validarCorreo = new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String correo = txtCorreo.getText().trim();
                if (!correo.contains("@")) {
                    JOptionPane.showMessageDialog(null, "El correo debe contener un '@'.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtCorreo.requestFocus();
                }
            }
        };

        //Declaracion y definicion de atributos de texto complementario y variables tipo JTextField
        labelNombre = new JLabel("Nombre");
        labelNombre.setFont(new Font("Arial", Font.BOLD, 20));
        panelLateral.add(labelNombre).setBounds(115, 60, 100, 25);
        panelLateral.add(txtNombre).setBounds(80, 120, 150, 25);
        txtNombre.addKeyListener(soloLetras);
        
        labelDNI = new JLabel("DNI");
        labelDNI.setFont(new Font("Arial", Font.BOLD, 20));
        panelLateral.add(labelDNI).setBounds(305, 60, 100, 25);
        panelLateral.add(txtDNI).setBounds(260, 120, 150, 25);
        txtDNI.addKeyListener(soloNumeros);

        labelTelefono = new JLabel("Teléfono");
        labelTelefono.setFont(new Font("Arial", Font.BOLD, 20));
        panelLateral.add(labelTelefono).setBounds(460, 60, 100, 25);
        panelLateral.add(txtTelefono).setBounds(440, 120, 150, 25);
        txtTelefono.addKeyListener(soloNumeros);

        labelCorreoElectronico = new JLabel("Correo Electrónico");
        labelCorreoElectronico.setFont(new Font("Arial", Font.BOLD, 20));
        panelLateral.add(labelCorreoElectronico).setBounds(605, 60, 200, 25);
        panelLateral.add(txtCorreo).setBounds(620, 120, 150, 25);
        txtCorreo.addFocusListener(validarCorreo);

        labelDireccion = new JLabel("Dirección");
        labelDireccion.setFont(new Font("Arial", Font.BOLD, 20));
        panelLateral.add(labelDireccion).setBounds(825, 60, 100, 25);
        panelLateral.add(txtDireccion).setBounds(800, 120, 150, 25);

        labelTipoPersona = new JLabel("Clasificación");
        labelTipoPersona.setFont(new Font("Arial", Font.BOLD, 20));
        panelLateral.add(labelTipoPersona).setBounds(990, 60, 150, 25);
        panelLateral.add(cmbTipoPersona).setBounds(980, 120, 150, 25);


        //Intregracion de variables JButton al panel creado
        panelLateral.add(btnAgregar).setBounds(1225, 110, 150, 25);
        panelLateral.add(btnRestablecer).setBounds(1225, 135, 150, 25);
        panelLateral.add(btnModificar).setBounds(1275, 795, 100, 25); 
        panelLateral.add(btnEliminar).setBounds(1400, 795, 100, 25);


        // Desactivar botones al inicio
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false); 


        //Tabla para almacenar los valores agregados en un formato establecido
        String[] columnas = {"Nombre", "DNI", "Teléfono", "Correo Electrónico", "Dirección", "Clasificación"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo) {
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar edición
            }
        };

        try {
            Connection con = getConexion();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Personas");
            ResultSet rs = ps.executeQuery();
        
            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                String dni = rs.getString("DNI");
                String telefono = rs.getString("Telefono");
                String correo = rs.getString("CorreoElectronico");
                String direccion = rs.getString("Direccion");
                String tipo = rs.getString("TipodePersona");

            // Agregar cada fila al modelo de la tabla
            modelo.addRow(new Object[]{nombre, dni, telefono, correo, direccion, tipo});
            }
        }catch (SQLException a) {
            JOptionPane.showMessageDialog(null, a.toString());
        }

        // Crear un JScrollPane para agregar la tabla dentro de un panel desplazable
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(20, 270, 1480, 505);
        panelLateral.add(scrollPane);


        // Acción del botón Agregar
        btnAgregar.addActionListener(new ActionListener() { // Agrega un ActionListener al botón Agregar.
            public void actionPerformed(ActionEvent e) { // Define lo que ocurre cuando se presiona el botón.
                nombre = txtNombre.getText().trim();
                DNI = txtDNI.getText().trim();  
                telefono = txtTelefono.getText().trim(); 
                correo = txtCorreo.getText().trim();  
                direccion = txtDireccion.getText().trim();
                tipo = (String) cmbTipoPersona.getSelectedItem();

                if (!nombre.isEmpty() && !telefono.isEmpty() && !DNI.isEmpty() && !correo.isEmpty() && !tipo.isEmpty() ) { // Verifica que los campos requeridos no estén vacíos.
                    modelo.addRow(new Object[]{nombre, DNI, telefono, correo, direccion, tipo}); // Añade una fila a la tabla.
                    limpiarCampos(); // Limpia los campos después de agregar.


                    try{
                        Connection con = getConexion();
                        PreparedStatement ps = con.prepareStatement("INSERT INTO Personas (Nombre, DNI, Telefono, CorreoElectronico, Direccion, TipodePersona) VALUES (?,?,?,?,?,?)");
                        ps.setString(1, nombre);
                        ps.setString(2, DNI);
                        ps.setString(3, telefono);
                        ps.setString(4, correo);
                        ps.setString(5, direccion);
                        ps.setString(6, tipo);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                    } catch (SQLException a) {
                        JOptionPane.showMessageDialog(null, a.toString());
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos requeridos."); // Muestra un mensaje de error si faltan datos.
                }
            }
        });
        

        // Acción para seleccionar una fila en la tabla
        tabla.addMouseListener(new MouseAdapter() { // Agrega un MouseAdapter para detectar clics en la tabla.
            public void mouseClicked(MouseEvent e) { // Método llamado al hacer clic en la tabla.
                selectedRow = tabla.getSelectedRow(); // Obtiene el índice de la fila seleccionada.
                if (selectedRow >= 0) { // Si hay una fila seleccionada.
                    btnModificar.setEnabled(true); // Activa el botón Modificar.
                    btnEliminar.setEnabled(true); // Activa el botón Eliminar.
                }
            }
        });


        // Acción del botón Modificar
        btnModificar.addActionListener(new ActionListener() { // Agrega un ActionListener al botón Modificar.
            public void actionPerformed(ActionEvent e) { // Define lo que ocurre cuando se presiona el botón.
                if (selectedRow >= 0) { // Verifica que haya una fila seleccionada.
                    // Cargar los valores de la fila seleccionada
                    nombre = (String) tabla.getValueAt(selectedRow, 0);
                    DNI = (String) tabla.getValueAt(selectedRow, 1); 
                    telefono = (String) tabla.getValueAt(selectedRow, 2);
                    correo = (String) tabla.getValueAt(selectedRow, 3); 
                    direccion = (String) tabla.getValueAt(selectedRow, 4); 
                    tipo = (String) tabla.getValueAt(selectedRow, 5);

                    // Crear un formulario para modificar los datos
                    JPanel panelEdicion = new JPanel(new GridLayout(6, 2)); // Crea un nuevo formulario en un panel.
                    txtNombre = new JTextField(nombre); // Campo de texto para Nombre con valor actual.
                    txtTelefono = new JTextField(telefono); // Campo de texto para Teléfono con valor actual.
                    txtDNI = new JTextField(DNI); // Campo de texto para CL o RUC con valor actual.
                    txtCorreo = new JTextField(correo); // Campo de texto para Correo con valor actual.
                    txtDireccion = new JTextField(direccion); // Campo de texto para Dirección con valor actual.
                    JComboBox<String> cmbTipoPersona = new JComboBox<>(new String[]{"Cliente", "Proveedor", "Ambos"}); // Desplegable para Tipo.
                    cmbTipoPersona.setSelectedItem(tipo); // Selecciona el tipo actual en el desplegable.

                    // Añade componentes al formulario de edición
                    panelEdicion.add(new JLabel("Nombre:"));
                    panelEdicion.add(txtNombre);
                    panelEdicion.add(new JLabel("Teléfono:"));
                    panelEdicion.add(txtTelefono);
                    panelEdicion.add(new JLabel("DNI:"));
                    panelEdicion.add(txtDNI);
                    panelEdicion.add(new JLabel("Correo:"));
                    panelEdicion.add(txtCorreo);
                    panelEdicion.add(new JLabel("Dirección:"));
                    panelEdicion.add(txtDireccion);
                    panelEdicion.add(new JLabel("Clasificación:"));
                    panelEdicion.add(cmbTipoPersona);

                    // Muestra un cuadro de diálogo para editar
                    int result = JOptionPane.showConfirmDialog(null, panelEdicion, "Modificar Persona", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) { // Si el usuario confirma los cambios.
                        tabla.setValueAt(txtNombre.getText(), selectedRow, 0); // Actualiza el Nombre.
                        tabla.setValueAt(txtDNI.getText(), selectedRow, 1); // Actualiza el CL o RUC.
                        tabla.setValueAt(txtTelefono.getText(), selectedRow, 2); // Actualiza el Teléfono.
                        tabla.setValueAt(txtCorreo.getText(), selectedRow, 3); // Actualiza el Correo.
                        tabla.setValueAt(txtDireccion.getText(), selectedRow, 4); // Actualiza la Dirección.
                        tabla.setValueAt(cmbTipoPersona.getSelectedItem().toString(), selectedRow, 5); // Actualiza el Tipo.
                    }

                try{
                    Connection con = getConexion();
                    PreparedStatement ps = con.prepareStatement("UPDATE Personas SET Nombre = ?, DNI = ?, Telefono = ?, CorreoElectronico = ?, Direccion = ?, TipodePersona = ? WHERE DNI = ?");
                    ps.setString(1, txtNombre.getText());
                    ps.setString(2, txtDNI.getText());
                    ps.setString(3, txtTelefono.getText());
                    ps.setString(4, txtCorreo.getText());
                    ps.setString(5, txtDireccion.getText());
                    ps.setString(6, cmbTipoPersona.getSelectedItem().toString());
                    ps.setString(7, DNI);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registro Actualizado");
                } catch (SQLException a) {
                    JOptionPane.showMessageDialog(null, a.toString());
                }
            
                }
            }
        });


        // Acción del botón Eliminar
        btnEliminar.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                // Confirmación de eliminación
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta fila?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION && selectedRow >= 0) { 
                    try {
                        // Obtener el valor del DNI de la fila seleccionada
                        String dni = modelo.getValueAt(selectedRow, 2).toString();

                        // Conexión a la base de datos
                        Connection con = getConexion();
                        PreparedStatement ps = con.prepareStatement("DELETE FROM Personas WHERE DNI = ?");
                        ps.setString(1, dni); // Establecer el DNI en la consulta
                        int rowsAffected = ps.executeUpdate();

                        // Verificar si se eliminó correctamente
                        if (rowsAffected > 0) {
                            // Eliminar la fila del modelo
                            modelo.removeRow(selectedRow);
                            selectedRow = -1; // Reiniciar la selección
                            btnModificar.setEnabled(false); 
                            btnEliminar.setEnabled(false); 
                            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró el registro en la base de datos.");
                        }

                        } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + ex.getMessage());
                    }
                }
            }
        });
        

        btnRestablecer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                limpiarCampos();
            }
        });


        filtroDNI = new JTextField();
        panelLateral.add(new JLabel("Filtrar por:")).setBounds(1210, 60, 100, 25);
        panelLateral.add(filtroDNI).setBounds(1300, 60, 150, 25);
        filtroDNI.addKeyListener(soloNumeros);



        filtroDNI.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            seleccionDNI = filtroDNI.getText();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
            tabla.setRowSorter(sorter);
        
            if (seleccionDNI.equals(null)) {
                sorter.setRowFilter(null); 
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(seleccionDNI, 1)); 
            }
            }
        });

        add(panelLateral);
    }

        public void limpiarCampos() { 
            txtNombre.setText("");
            txtTelefono.setText("");
            txtDNI.setText(""); 
            txtCorreo.setText("");  
            txtDireccion.setText("");  
            }
}


