package Controlleur.Recherche;

import Controlleur.Controleur;

import java.awt.event.ActionEvent;

public class RechercheControleur extends Controleur {

    public RechercheControleur() {
        super();
    }

    @Override
    public void rechercher_controleur(String text) {

        System.out.println(text);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
