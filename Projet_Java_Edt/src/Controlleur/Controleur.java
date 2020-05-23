/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;
import Vue.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author Wang David
 */
public class Controleur {
    
    public static void main(String args[]) throws UnsupportedLookAndFeelException
    {
        UIManager.setLookAndFeel(new NimbusLookAndFeel ());
        Accueil accueil = new Accueil();
    }
    
}
