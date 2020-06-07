/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Wang David
 */
public class Annule extends JFrame{
    
    
    public Annule(){}
    
    /**Affiche toutes les seances annullées d'un étudiant
     * 
     * @param etudiant 
     */
    public Annule (Etudiant etudiant)
    {
        super("Votre reporting");

        this.setSize(1000,800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);
        
        
        JPanel panel = new JPanel(new GridLayout(0,1));
        JTextPane cours_annules = new JTextPane();
        
        StyledDocument d = cours_annules.getStyledDocument();
        SimpleAttributeSet au_milieu = new SimpleAttributeSet();
        StyleConstants.setAlignment(au_milieu, StyleConstants.ALIGN_CENTER);
        d.setParagraphAttributes(0, d.getLength(), au_milieu, false);
        Font font = new Font("Dialog", Font.BOLD, 20);
        cours_annules.setFont(font);
        String donnees="Voici tous vos cours annulés dans l'année :\n";;
        
        EtudiantDao eDao = new EtudiantDao();
        ArrayList<Seance> liste_annule = new ArrayList();
        liste_annule = eDao.allSeance(etudiant.getID());
        
        for(int i=0;i<liste_annule.size();i++)
        {
            if(liste_annule.get(i).getEtat()==3)
            {
                donnees+="ID de la seance annulée : "+liste_annule.get(i).getID() + " Nom de la seance : "
                        + liste_annule.get(i).getCours().getNom()
                        +" Date : "+ liste_annule.get(i).getDate()
                        +"\n";
            }
        }
        cours_annules.setText(donnees);
        panel.add(cours_annules);
        this.add(panel);
      
        this.setVisible(true);
        
    }
    
    /**Affiche toutes les séances annulées d'un prof
     * 
     * @param prof 
     */
    public Annule (Enseignant prof)
    {
        
        super("Votre reporting");

        this.setSize(1000,800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);
        
        
        JPanel panel = new JPanel(new GridLayout(0,1));
        JTextPane cours_annules = new JTextPane();
        
        StyledDocument d = cours_annules.getStyledDocument();
        SimpleAttributeSet au_milieu = new SimpleAttributeSet();
        StyleConstants.setAlignment(au_milieu, StyleConstants.ALIGN_CENTER);
        d.setParagraphAttributes(0, d.getLength(), au_milieu, false);
        Font font = new Font("Dialog", Font.BOLD, 20);
        cours_annules.setFont(font);
        String donnees="Voici tous vos cours annulés dans l'année :\n";
        
        EnseignantDAO eDao = new EnseignantDAO();
        ArrayList<Seance> liste_annule = new ArrayList();
        liste_annule = eDao.allSeance(prof.getID());
        
        for(int i=0;i<liste_annule.size();i++)
        {
            if(liste_annule.get(i).getEtat()==3)
            {
                donnees+="ID de la seance annulée : "+liste_annule.get(i).getID() + " Nom de la seance : " 
                        +" Date : "+ liste_annule.get(i).getDate()
                        +"\n";
            }
        }
        cours_annules.setText(donnees);
        panel.add(cours_annules);
        this.add(panel);
      
        this.setVisible(true);
    }
}
