package Controlleur.Recherche;

import Controlleur.Controleur;
import Modele.*;

import java.awt.event.ActionEvent;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;

/**
 * @author jorge
 */

public class RechercheControleur extends Controleur {

    public RechercheControleur() {
        super();
    }

    /**
     * Recherche l'emplois du temps d'une personne celon la semaine choisie
     */
    public void rechercher_utilisateur(String nom, String semaine, int droit) {

        int numero_semaine = 0;
        boolean validationdacces = false;

        // On verifie que l'utilisateur a bien ecrit un entier pour la semaine
        try {
            numero_semaine = Integer.parseInt(semaine);
        } catch (NumberFormatException e) {
            System.out.println("Numero de semaine non valide");
            return;
        }

        // On crée les utilisateurs et on va faire une recherche dessus
        UtilisateurDao userDAO = new UtilisateurDao();
        ArrayList<Utilisateur> mesUsers = new ArrayList<>();

        for (int i = 1; i < userDAO.getTaille("utilisateur") + 1; i++) {
            mesUsers.add(userDAO.find(i));
        }

        Utilisateur leusersouhaiter = new Utilisateur();

        for (int i = 0; i < mesUsers.size(); i++) {
            if (mesUsers.get(i).getNom().equals(nom)) {
                leusersouhaiter = new Utilisateur(mesUsers.get(i));///dés qu'on trouve le bon utilisateur on sort de la boucle
                break;
            }
        }
        for (int i = 0; i < mesUsers.size(); i++) {
            if (mesUsers.get(i).getNom().equals(nom) && mesUsers.get(i).getDroit() == droit) {
                leusersouhaiter = new Utilisateur(mesUsers.get(i));
                break;
            }
        }

        if (droit == 1 || droit == 2) {// Admin et Referent on une vue total sur les utilisateur
            validationdacces = true;
        } else {
            if (droit == leusersouhaiter.getDroit())/// Pour les prof et etudiant, il ne peuvent voir que respectivement prof et etudiant
                validationdacces = true;
        }

        if (leusersouhaiter.getID() != 0) {
            if (validationdacces) {
                System.out.println("Les informations de la personne sont :");
                System.out.println(leusersouhaiter.getID() + " " + leusersouhaiter.getNom() + " " + leusersouhaiter.getPrenom()
                        + " " + leusersouhaiter.getMail() + " " + leusersouhaiter.getDroit());

                // On recupère les information de l'utilisateur si possible
                ArrayList<Seance> lesSeances = userDAO.lesSeance(leusersouhaiter.getID(), numero_semaine);

                System.out.println("Sont emplois du temps est le suivant :");
                for (Seance uneseance :
                        lesSeances) {
                    System.out.println(uneseance.getID() + " " + uneseance.getSemaine() + " " + uneseance.getDate().toString() + " " + uneseance.getHeureDebut()
                            + " " + uneseance.getHeureFin() + " " + uneseance.getCours().getNom() + " " + uneseance.getType().getNom());
                }
                System.out.println("Il faut modifier la vu pour les afficher correctement");
            } else System.out.println("Accés non autorisé");
        } else
            System.out.println("Personne non trouvé dans la BDD : " + nom);

    }

    /**
     * Recherche de l'emplois du temps d'un groupe
     */
    public void rechercher_groupe(String nom_groupe, String semaine) {

        int numero_semaine = 0;
        int id_groupe = 0;
        try {
            numero_semaine = Integer.parseInt(semaine);
        } catch (NumberFormatException e) {
            System.err.println("Numero de semaine non valide");
            return;
        }

        GroupeDAO groupeDAO = new GroupeDAO();

        id_groupe = groupeDAO.idCelonNom(nom_groupe);

        if (id_groupe != 0) {/// Si le id_groupe = 0 alors le groupe n'existe pas

            ArrayList<Seance> lesseances = groupeDAO.lesSeances(id_groupe, numero_semaine);

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                }
            } else System.out.println("Pas de séance cette semaine");
        }


    }

    public void rechercher_promotion(String anne_promotion, String semaine) {

        int numero_semaine = 0;
        int id_promotion = 0;
        try {
            numero_semaine = Integer.parseInt(semaine);
        } catch (NumberFormatException e) {
            System.err.println("Numero de semaine non valide");
            return;
        }

        PromotionDAO promotionDAO = new PromotionDAO();
        //anne_promotion = promotionDAO.idCelonAnne(anne_promotion)

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
