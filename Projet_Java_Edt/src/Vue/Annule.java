/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

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
        JTextArea cours_annules = new JTextArea();
        
        String donnees="";
        
        EtudiantDao eDao = new EtudiantDao();
        ArrayList<Seance> liste_annule = new ArrayList();
        liste_annule = eDao.allSeance(etudiant.getID());
        
        for(int i=0;i<liste_annule.size();i++)
        {
            if(liste_annule.get(i).getEtat()==3)
            {
                donnees+="ID de la seance annulée : "+liste_annule.get(i).getID() + " Nom de la seance : " + liste_annule.get(i).getCours().getNom();
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
        JTextArea cours_annules = new JTextArea();
        
        String donnees="";
        
        EnseignantDAO eDao = new EnseignantDAO();
        ArrayList<Seance> liste_annule = new ArrayList();
        liste_annule = eDao.allSeance(prof.getID());
        
        for(int i=0;i<liste_annule.size();i++)
        {
            if(liste_annule.get(i).getEtat()==3)
            {
                donnees+="ID de la seance annulée : "+liste_annule.get(i).getID() + " Nom de la seance : " + liste_annule.get(i).getCours().getNom();
            }
        }
        cours_annules.setText(donnees);
        panel.add(cours_annules);
        this.add(panel);
      
        this.setVisible(true);
    }
}
