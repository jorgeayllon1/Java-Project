/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlleur;

import Modele.DAO;
import Modele.*;
import Modele.UtilisateurDao;
import Vue.*;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * @author Wang David
 */
public class AccueilControleur extends Controleur {

    Utilisateur user;
    Accueil accueil;
    //UtilisateurDao userDao;

    public AccueilControleur() {
        super();
    }

    public AccueilControleur(Utilisateur user) {
        this.user = user;

    }

    public void control_accueil(String identifiant, String mdp) {
        ArrayList<Utilisateur> mesUsers = new ArrayList<>();

        DAO<Utilisateur> userDao = new UtilisateurDao();

        for (int i = 1; i < 11; i++) {
            Utilisateur user = userDao.find(i);
            mesUsers.add(user);
        }


        for (int i = 0; i < mesUsers.size(); i++) //On parcourt toute la liste des utilisateurs dans la bdd
        {
            if ((identifiant.equals(mesUsers.get(i).getNom())) && (mdp.equals(mesUsers.get(i).getMdp()))) //Si id et mdp juste
            {

                System.out.println("Connexion réussie !");
                int droit = mesUsers.get(i).getDroit();
                switch (droit) {
                    case 1:
                        System.out.println("Droit : Admin");
                        Edt_Admin edt_admin = new Edt_Admin(mesUsers.get(i));
                        break;
                    case 2:
                        System.out.println("Droit : Referent");
                        Edt_Admin edt_ref = new Edt_Admin(mesUsers.get(i));

                        break;
                    case 3:
                        System.out.println("Droit : Enseignant");
                        EnseignantDAO profDao = new EnseignantDAO();
                        Enseignant prof = (Enseignant) profDao.find(mesUsers.get(i).getID());

                        Edt_Enseignant edt_prof = new Edt_Enseignant(mesUsers.get(i), prof);
                        break;
                    case 4: //C'est un etudiant
                        System.out.println("Droit : Eleve");
                        DAO<Utilisateur> etudiantDao = new EtudiantDao(); //********************
                        Etudiant etudiant = (Etudiant) etudiantDao.find(mesUsers.get(i).getID());//*****************

                        //On affichera les cours et séances relatifs à cet etudiant (classe edt abstraite héritage edt_etudiant, edt_prof...)
                        Edt_Etudiant edt_etudiant = new Edt_Etudiant(mesUsers.get(i), etudiant);
                        break;
                }
            }


        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }


}
