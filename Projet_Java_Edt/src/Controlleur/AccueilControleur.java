
package Controlleur;

import Modele.*;
import Vue.*;

import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * @author Wang David
 */

public class AccueilControleur extends Controleur {
    private int auth = 0;

    public AccueilControleur() {
        super();
    }

    public void control_accueil(String identifiant, String mdp) {
        ArrayList<Utilisateur> mesUsers = new ArrayList<>();
        DAO<Utilisateur> userDao = new UtilisateurDao();

        for (int i = 1; i < userDao.getTaille("utilisateur"); i++) {
            Utilisateur user = userDao.find(i);
            mesUsers.add(user);
        }

        for (int i = 0; i < mesUsers.size(); i++) //On parcourt toute la liste des utilisateurs dans la bdd
        {
            if ((identifiant.equals(mesUsers.get(i).getNom())) && (mdp.equals(mesUsers.get(i).getMdp()))) //Si id et mdp juste
            {

                auth = 0;
                System.out.println("Connexion réussie !");
                int droit = mesUsers.get(i).getDroit();
                switch (droit) {
                    case 1:

                        Edt_Admin edt_admin = new Edt_Admin(mesUsers.get(i));
                        break;
                    case 2:
                        System.out.println("Droit : Referent");
                        Edt_Admin edt_ref = new Edt_Admin(mesUsers.get(i));
                        break;
                    case 3:

                        EnseignantDAO profDao = new EnseignantDAO();
                        Enseignant prof = (Enseignant) profDao.find(mesUsers.get(i).getID());
                        Edt_Enseignant edt_prof = new Edt_Enseignant(mesUsers.get(i), prof);
                        break;
                    case 4: //C'est un etudiant

                        DAO<Etudiant> etudiantDao = new EtudiantDao(); //********************
                        Etudiant etudiant = (Etudiant) etudiantDao.find(mesUsers.get(i).getID());//*****************
                        Edt_Etudiant edt_etudiant = new Edt_Etudiant(mesUsers.get(i), etudiant);
                        break;
                }
                auth = 0;
                break;

            } else if (identifiant.isEmpty() || mdp.isEmpty()) {
                auth = 2;
            } else {
                auth = 1;

            }

        }
        if (auth == 1) {
            JOptionPane faux = new JOptionPane();
            faux.showMessageDialog(null, "Identidiant ou mot de passe incorrect", "ERREUR", JOptionPane.ERROR_MESSAGE);
        } else if (auth == 2) {
            JOptionPane vide = new JOptionPane();
            vide.showMessageDialog(null, "Au moins un des champ est vide", "CHAMP VIDE", JOptionPane.WARNING_MESSAGE);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
