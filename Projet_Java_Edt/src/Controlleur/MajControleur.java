package Controlleur;

import Modele.*;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;

public class MajControleur extends Controleur {

    public MajControleur() {
        super();
    }

    public MajControleur(DAO dao) {
        super(dao);
    }

    //On caractèrise une séance par son heure debut, heure fin et par sa salle
    // Deux séance differentes ne peuvent avoir lieu en même temps dans une même salle
    public void modifierSeance(String salle, Timestamp heure_debut, Timestamp heure_fin, String nouv_cours_nom, String nouv_typeCours_nom) {
        SeanceDao seanceDao = new SeanceDao();
        Seance seance = seanceDao.chercherSeance(salle, heure_debut, heure_fin);

        if (seance != null) {
            System.out.println(seance.getCours().getNom() + " " + seance.getDate() + " " + seance.getID());
        } else System.out.println("Pas de séance trouvée");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
