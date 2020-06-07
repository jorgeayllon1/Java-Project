/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_java_edt;

import Modele.*;
import Controlleur.*;
import Vue.*;

import java.sql.*;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


/**
 * @author Wang David
 */
public class Projet_Java_Edt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {


        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Accueil accueil = new Accueil();


    }

}
