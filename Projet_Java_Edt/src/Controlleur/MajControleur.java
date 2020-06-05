package Controlleur;

import Modele.*;

import java.awt.event.ActionEvent;

public class MajControleur extends Controleur {

    public MajControleur() {
        super();
    }

    public MajControleur(DAO dao) {
        super(dao);
    }

    public void creationSeance() {
        Seance nouv_seance;
        Seance test = (Seance) this.dao.find(1);
        System.out.println(test.getCours().getNom() + " " + test.getDate());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
