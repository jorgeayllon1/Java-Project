package Controlleur.Recherche;

import Controlleur.Controleur;

import java.awt.event.ActionEvent;

public class RechercheControleur extends Controleur {

    public RechercheControleur() {
        super();
    }

    public void control_rechercher(String nom) {
        System.out.println(nom);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
