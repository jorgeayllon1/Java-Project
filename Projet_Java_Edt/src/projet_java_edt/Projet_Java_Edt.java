/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_java_edt;

import Modele.*;
import Controlleur.*;
import Vue.*;

import java.sql.*;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


/**
 * @author Wang David
 */
public class Projet_Java_Edt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        // TODO code application logic here

        ///MAIN POUR FAIRE DES TEST///

        DAO<Utilisateur> userDao = new UtilisateurDao();


        /*for (int i = 1; i < 13; i++) {
            Utilisateur user = userDao.find(i);
            System.out.println("id: " + user.getID() + " email: " + user.getMail() + " nom: " + user.getNom() + " prenom: " + user.getPrenom());
            // if(i==12) //erreur
            //{
            //  userDao.delete(user); //Test pour delete un user (marche)
            //}
        }*/

        //userDao.afficherChampTable("utilisateur"); //Afficher champ table


        //Test get date et heure en JAVA (marche)

        /*

        DAO<Seance> seanceDao = new SeanceDao();

        Seance seance = seanceDao.find(1);
        System.out.println(seance.getDate() + " " + seance.getHeureDebut() + " " + seance.getHeureFin());*/

        ///AFFICHAGE OAGE CONNEXION///
        //Affichage page connexion OK

        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Accueil accueil = new Accueil();


        //Test pour ajouter un nouvel user en trouvant l'id max (marche)
        //Utilisateur david = new Utilisateur("ghjgjh","mail.fr","wang","david",4);
        //userDao.create(david);



/*
        ///------Tout ce qui en bas marche------///
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

        DAO<Groupe> groupeDAO = new GroupeDAO();
        for (int i = 0; i < 7; i++) {
            Groupe groupe = groupeDAO.find(i);
            System.out.println("id: " + groupe.getId() + " nom: " + groupe.getNom() + " id_promotion: " + groupe.getId_promotion());
        }
/// ///////////////////////////////////////////
        DAO<Seance> seanceDAO = new SeanceDao();
        for (int i = 1; i < 5; i++) {
            Seance seance = seanceDAO.find(i);
            System.out.println("id: " + seance.getID() + " semaine: " + seance.getSemaine());
        }
/// ///////////////////////////////////////////

        DAO<Promotion> promotionDAO = new PromotionDAO();
        for (int i = 1; i < 4; i++) {
            Promotion promotion = promotionDAO.find(i);
            System.out.println("id: " + promotion.getId() + " annee: " + promotion.getAnnee());
        }

        DAO<Site> siteDAO = new SiteDAO();
        for (int i = 0; i < 5; i++) {
            Site site = siteDAO.find(i);
            System.out.println("id: " + site.getId() + " annee: " + site.getNom());
        }

        DAO<TypeCours> typeCoursDAO = new TypeCoursDAO();
        for (int i = 1; i < 7; i++) {
            TypeCours typeCours = typeCoursDAO.find(i);
            System.out.println("id: " + typeCours.getId() + " nom: " + typeCours.getNom());
        }

*/
    }

}
