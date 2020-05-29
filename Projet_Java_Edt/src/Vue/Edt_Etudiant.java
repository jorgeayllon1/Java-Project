/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.*;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Wang David
 */
public class Edt_Etudiant extends Edt{
    
    public Edt_Etudiant(){}
    
    public Edt_Etudiant(Utilisateur user, Etudiant etudiant)
    {
        
        super(user);
        JPanel grille_edt = new JPanel(new GridLayout(7,6,1,1));
        
        //Grille d'edt
        for(int i=1;i<8;i++)
        {
            grille_edt.add(new JLabel("8h-10h"));
            for(int j=1;j<7;j++)
            {
                grille_edt.add(new JLabel(String.valueOf(i*j),JLabel.CENTER));

            }
            
        }
        
        this.panel.add(grille_edt);
       //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
        EtudiantDao etudiantDao = new EtudiantDao(); //********************
        etudiant =(Etudiant) etudiantDao.find(etudiant.getID());//*****************
        System.out.print("Numero etudiant :"+etudiant.getNumEtudiant());
        
        //Récupération données groupe
        GroupeDAO groupeDao = new GroupeDAO();
        Groupe groupe = groupeDao.find(etudiant.getGroupe().getId());
        System.out.println(" Groupe :" +groupe.getNom() + " Promotion :" + groupe.getPromo().getAnnee());
        
        ///Affichage des séances relatives à cet eleve
        
        ArrayList<Integer> mes_id = new ArrayList();
        mes_id = groupeDao.trouverIdSeance(groupe);
        
        
        ArrayList<Seance> mes_seances = new ArrayList();
        mes_seances = groupeDao.trouverAllSeances(mes_id);
        
        System.out.println("Nombre de séances prevues pour cet eleve : " +mes_seances.size());
        Salle salle =new Salle();
        for(int i=0;i<mes_seances.size();i++)
        {
            System.out.println("Mes seances: \n"
                    +"Intitule du cours : " + mes_seances.get(i).getCours().getNom()
                    +"\n date : " +mes_seances.get(i).getDate()
                    +"\nheure debut : "  + mes_seances.get(i).getHeureDebut() 
                    +"\nheure fin : " +mes_seances.get(i).getHeureFin()
                    +"\nType :" + mes_seances.get(i).getType().getNom());
            salle = etudiantDao.trouverSalle(mes_seances.get(i));
            System.out.println("Salle : " + salle.getNom() + " Capacite : " + salle.getCapacite() + " Site : " + salle.getSite().getNom()  );
        }
       
    }
    
    
    
}
