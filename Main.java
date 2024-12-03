package com.importshop.Backend;


import com.importshop.Constantes.baseWindow;
import com.importshop.Ventanas.ventanaPrincipal;



public class Main{
    public static void main(String[] args) {
        baseWindow bw = new ventanaPrincipal();
        bw.ejecucion();
        bw.setVisible(true);
    }
}
