package Controlleur;

import Modele.DAO;

import java.awt.event.ActionEvent;

public class MajControleur extends Controleur {

    public MajControleur() {
        super();
    }

    public MajControleur(DAO dao) {
        super(dao);
    }

    public void affecterEnseignant() {
        System.out.println("Coucou");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
