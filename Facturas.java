package com.importshop.Ventanas;

import com.importshop.Constantes.baseWindow;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class Facturas extends baseWindow{
    public void ejecucion(){
        window();
        menu();
        panelComplementario();
    }


    public void panelComplementario(){
        JPanel panelLateral= new JPanel();
        panelLateral.setLayout(null); //Se asigna el layout en null para poder usar coordenadas
        panelLateral.setBounds(400, 170, 1520, 840); //Coordenadas en (x, y, extencion 'x', extencion 'y')
        panelLateral.setBackground(new Color(238, 227, 227)); //Color descompuesto en RGB
        TitledBorder tituloDelBorde = BorderFactory.createTitledBorder("Facturas"); //Borde que funcione como marco y con un titulo
        tituloDelBorde.setTitleFont(new Font("Arial", Font.BOLD, 20)); //Fuente, tipo de letra, tamaño
        panelLateral.setBorder(tituloDelBorde);
        


        add(panelLateral);
    }

    public void limpiarCampos(){

    }
}
