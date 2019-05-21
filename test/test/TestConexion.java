/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import gestionventa.imp.ConexionBD;

/**
 *
 * @author billy.johnson
 */
public class TestConexion {
    public static void main(String [] args){
        System.out.println(ConexionBD.getInstance().testConnection());
    }
}
