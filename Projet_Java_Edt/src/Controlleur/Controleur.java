/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;
import Vue.*;
import Modele.*;
import java.util.ArrayList;
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
        
        
        ArrayList <Utilisateur> mesUsers = new ArrayList<>();
        
        DAO<Utilisateur> userDao = new UtilisateurDao();

        for (int i = 1; i < 11; i++) {
            Utilisateur user = userDao.find(i);
            mesUsers.add(user);

        }
        
        for(int i=0;i<mesUsers.size();i++)
        {
            System.out.println(mesUsers.get(i).getMail());
        }
        
        UIManager.setLookAndFeel(new NimbusLookAndFeel ());
        Accueil accueil = new Accueil();
    }
    
}
