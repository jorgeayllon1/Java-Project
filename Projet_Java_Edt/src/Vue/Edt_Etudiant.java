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
public class Edt_Etudiant extends Edt{
    
    public Edt_Etudiant(){}
    
    public Edt_Etudiant(Utilisateur user, Etudiant etudiant)
    {
        
        super(user);
       //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
        DAO<Utilisateur> etudiantDao = new EtudiantDao(); //********************
        etudiant =(Etudiant) etudiantDao.find(etudiant.getID());//*****************
        System.out.println("Numero etudiant : "+etudiant.getNumEtudiant());
        
        //Récupération données groupe
        GroupeDAO groupeDao = new GroupeDAO();
        Groupe groupe = groupeDao.find(etudiant.getIdGroupe());
        System.out.println(groupe.getNom());
        
        ///Affichage des séances relatives à cet eleve
        
        ArrayList<Integer> mes_id = new ArrayList();
        mes_id = groupeDao.trouverIdSeance(groupe);
        
        
        ArrayList<Seance> mes_seances = new ArrayList();
        mes_seances = groupeDao.trouverAllSeances(mes_id);
        
        System.out.println("Nombre de séances prevues pour cet eleve : " +mes_seances.size());
        for(int i=0;i<mes_seances.size();i++)
        {
            System.out.println("Mes seances:\n date : " +mes_seances.get(i).getDate()+ "\nheure debut : " + mes_seances.get(i).getHeureDebut() +"\nheure fin : " +mes_seances.get(i).getHeureFin());
        }
       
    }
    
}