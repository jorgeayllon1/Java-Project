/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Wang David
 */
public class Recap extends JFrame{
    
    public Recap(){}
    
    public Recap(Etudiant etudiant)
    {
        super("Votre récapitulatif de cours");

        this.setSize(800,600); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);
        
        Edt_Etudiant edt_etudiant = new Edt_Etudiant();
        long temps_debut = new java.util.Date().getTime() - 259200000;
        long temps_fin = new java.util.Date().getTime() + 959200000;

        java.sql.Date debut = new java.sql.Date(temps_debut);
        java.sql.Date fin = new java.sql.Date(temps_fin);
        
        edt_etudiant.voirrecap(debut, fin,etudiant);
        
        JPanel content = new JPanel(new BorderLayout());
        JPanel haut = new JPanel(new FlowLayout());
        JPanel centre = new JPanel(new GridLayout(0,1));
        JTextArea mon_recap = new JTextArea();
        
        centre.add(mon_recap);
        
        content.add(haut,BorderLayout.NORTH);
        content.add(centre, BorderLayout.CENTER);
        this.add(content);
        

        this.setVisible(true);
    }
    
    
    
}
