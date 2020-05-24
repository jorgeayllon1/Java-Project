/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;
import Vue.*;
import Modele.*;
import java.util.ArrayList;
import static javafx.scene.input.KeyCode.T;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 *
 * @author Wang David
 */
public  class Controleur {
    
    protected DAO dao;
    
    
    public Controleur(){}
    
    public Controleur(DAO dao)
    {
        this.dao=dao;
    }
    
    public void control_accueil(String identifiant, String mdp)
    {
       ArrayList <Utilisateur> mesUsers = new ArrayList<>();
        
        DAO<Utilisateur> userDao = new UtilisateurDao();
        
        for (int i = 1; i <11; i++)
        {
            Utilisateur user = userDao.find(i);
            mesUsers.add(user);
        }
  
        for(int i=0;i<mesUsers.size();i++)
        {
            if( (identifiant.equals(mesUsers.get(i).getNom())) && (mdp.equals(mesUsers.get(i).getMdp())))
            {
                System.out.println("Connexion réussie !");
                int droit=mesUsers.get(i).getDroit();
                switch(droit)
                {
                    case 1:
                        System.out.println("Admin");
                        break;
                    case 2:
                        System.out.println("Referent");
                        break;
                    case 3:
                        System.out.println("Enseignant");
                        break;
                    case 4:
                        System.out.println("Eleve");
                        break;                      
                }
            }
          
        }
    }
    
    public static void main(String args[]) throws UnsupportedLookAndFeelException
    {
        
        UIManager.setLookAndFeel(new NimbusLookAndFeel ());
        Accueil accueil = new Accueil();
    }
    
}
