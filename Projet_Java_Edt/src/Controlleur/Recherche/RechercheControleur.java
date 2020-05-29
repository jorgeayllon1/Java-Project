package Controlleur.Recherche;

import Controlleur.Controleur;
import Modele.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RechercheControleur extends Controleur {

    public RechercheControleur() {
        super();
    }

    @Override
    public void rechercher_controleur(String nom, String semain) {

        DAO<Utilisateur> userDAO = DAOFactory.getUtilisateur();

        ArrayList<Utilisateur> mesUsers = new ArrayList<>();

        for (int i = 1; i < userDAO.getTaille("utilisateur") + 1; i++) {
            mesUsers.add(userDAO.find(i));
        }

        Utilisateur leusersouhaiter = new Utilisateur();

        for (int i = 0; i < mesUsers.size(); i++) {
            if (mesUsers.get(i).getNom().equals(nom)) {
                leusersouhaiter = new Utilisateur(mesUsers.get(i));
                break;
            }
        }

        if (leusersouhaiter.getID() != 0) {
            System.out.println("Les informations de la personne sont :");
            System.out.println(leusersouhaiter.getID() + " " + leusersouhaiter.getNom() + " " + leusersouhaiter.getPrenom()
                    + " " + leusersouhaiter.getMail() + " " + leusersouhaiter.getDroit());
            System.out.println("Il faut modifier la vu pour les afficher correctement");
        } else System.out.println("Personne non trouvé dans la BDD : " + nom);


    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
