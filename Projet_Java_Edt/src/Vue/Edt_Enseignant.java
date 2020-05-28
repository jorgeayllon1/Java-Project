/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;
import Modele.*;
import java.util.ArrayList;

/**
 *
 * @author Wang David
 */
public class Edt_Enseignant extends Edt{
    
    public Edt_Enseignant(){}
    
    public Edt_Enseignant(Utilisateur user, Enseignant prof)
    {
        super(user);
        System.out.println("Bienvenue "+prof.getNom());
        
        
        ///Affichage des séances relatives à cet enseignant
        EnseignantDAO profDao = new EnseignantDAO();
        ArrayList<Integer> mes_id = new ArrayList();
        mes_id = profDao.trouverIdSeance(prof);
        
        ArrayList<Seance> mes_seances = new ArrayList();
        mes_seances = profDao.trouverAllSeances(mes_id);
        
        System.out.println("Nombre de séances prevues pour cet enseignant : " +mes_seances.size());
        for(int i=0;i<mes_seances.size();i++)
        {
            System.out.println("Mes seances:\n date : " +mes_seances.get(i).getDate()+ "\nheure debut : " 
                    + mes_seances.get(i).getHeureDebut() +"\nheure fin : " +mes_seances.get(i).getHeureFin());
            Salle salle = profDao.trouverSalle(mes_seances.get(i));
            System.out.println("Salle : " + salle.getNom() + " Capacite : " + salle.getCapacite() + " Site : " + salle.getSite().getNom());
        }
        
        
    }
    
}
