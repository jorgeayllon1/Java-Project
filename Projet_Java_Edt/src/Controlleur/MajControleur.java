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

    public void modifierSeance(Seance seance, String nouv_cours_nom, String nouv_typeCours_nom) {

        CoursDao coursDao = new CoursDao();
        TypeCoursDAO typeCoursDAO = new TypeCoursDAO();
        SeanceDao seanceDao = new SeanceDao();

        System.out.println(seance.getCours().getNom() + " " + seance.getID() + " " + seance.getType().getNom());

        // Blinder pour avoir un nom de cours et type de cours cohérent

        int id_cours = coursDao.id_celon_nom(nouv_cours_nom);
        int id_typecours = typeCoursDAO.id_celon_nom(nouv_typeCours_nom);

        if (id_cours != -1 && id_typecours != -1) {
            seance.getCours().setNom(nouv_cours_nom);
            seance.getCours().setId(id_cours);
            seance.getType().setNom(nouv_typeCours_nom);
            seance.getType().setId(id_typecours);

        } else System.out.println("Nom de cours ou nom de type de cours inconnu");

        System.out.println(seance.getCours().getNom() + " " + seance.getID() + " " + seance.getType().getNom());

        seanceDao.update(seance);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
