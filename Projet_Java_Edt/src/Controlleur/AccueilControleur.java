/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;

import Modele.DAO;
import Modele.*;
import Modele.UtilisateurDao;
import Vue.Edt;
import java.util.ArrayList;

/**
 *
 * @author Wang David
 */
public class AccueilControleur extends Controleur {
    
    public AccueilControleur()
    {
        super();
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
  
        for(int i=0;i<mesUsers.size();i++) //On parcourt toute la liste des utilisateurs dans la bdd
        {
            if( (identifiant.equals(mesUsers.get(i).getNom())) && (mdp.equals(mesUsers.get(i).getMdp()))) //Si id et mdp juste
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
                    case 4: //C'est un etudiant
                        System.out.println("Eleve");
                        //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
                        DAO<Utilisateur> etudiantDao = new EtudiantDao(); //********************
                        Etudiant etudiant =(Etudiant) etudiantDao.find(mesUsers.get(i).getID());//*****************
                        System.out.println(etudiant.getNumEtudiant());
                        
                        //On affichera les cours et séances relatifs à cet etudiant (classe edt abstraite héritage edt_etudiant, edt_prof...)
                        Edt edt = new Edt(mesUsers.get(i));
                        break;                      
                }
            }
          
        }
    }
}
