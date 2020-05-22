/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_java_edt;

import Modele.*;

import java.sql.*;


/**
 * @author Wang David
 */
public class Projet_Java_Edt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        DAO<Utilisateur> userDao = new UtilisateurDao();

        for (int i = 1; i < 11; i++) {
            Utilisateur user = userDao.find(i);
            System.out.println("id: " + user.getID() + " email: " + user.getMail() + " nom: " + user.getNom() + " prenom: " + user.getPrenom());

        }
        userDao.afficherChampTable("utilisateur");


        DAO<Cours> coursDao = new CoursDao();
        for (int i = 1; i < 11; i++) {
            Cours cours = coursDao.find(i);
            System.out.println("id: " + cours.getID() + " nom: " + cours.getNom());
        }

        DAO<Salle> salleDAO = new SalleDAO();
        for (int i = 1; i < 6; i++) {
            Salle salle = salleDAO.find(i);
            System.out.println("id: " + salle.getID() + " nom: " + salle.getNom() + " capacite: " + salle.getCapacite() + " id_site: " + salle.getId_site());
        }


    }

}
