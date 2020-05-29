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
    public void rechercher_controleur(String text) {

        System.out.println(text);

        DAO<Utilisateur> userDAO = DAOFactory.getUtilisateur();

        Utilisateur moi = userDAO.find(2);

        System.out.println(moi.getNom() + " " + moi.getPrenom());

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
