package Controlleur.Recherche;

import Controlleur.Controleur;

import java.awt.event.ActionEvent;

public class RechercheControleur extends Controleur {

    public RechercheControleur() {
        super();
    }

    public void rechercher_controleur(String texte) {
        System.out.println(texte);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}

