package Controlleur;

import Modele.*;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MajControleur extends Controleur {

    public MajControleur() {
        super();
    }

    public MajControleur(DAO dao) {
        super(dao);
    }

    //On caractèrise une séance par son heure debut, heure fin et par sa salle
    // Deux séance differentes ne peuvent avoir lieu en même temps dans une même salle

    /**
     * Modifie le nom du cours et le nom du type d'une seance
     *
     * @param seance
     * @param nouv_cours_nom
     * @param nouv_typeCours_nom
     */
    public void modifierSeance(Seance seance, String nouv_cours_nom, String nouv_typeCours_nom) {

        CoursDao coursDao = new CoursDao();
        TypeCoursDAO typeCoursDAO = new TypeCoursDAO();
        SeanceDao seanceDao = new SeanceDao();

        int id_cours = coursDao.id_celon_nom(nouv_cours_nom);
        int id_typecours = typeCoursDAO.id_celon_nom(nouv_typeCours_nom);

        // Blinder pour avoir un nom de cours et type de cours cohérent
        if (id_cours != -1 && id_typecours != -1) {
            seance.getCours().setNom(nouv_cours_nom);
            seance.getCours().setId(id_cours);
            seance.getType().setNom(nouv_typeCours_nom);
            seance.getType().setId(id_typecours);

        } else System.out.println("Nom de cours ou nom de type de cours inconnu");

        seanceDao.update(seance);

    }

    public void creationSeance(int semaine, Date date, Timestamp heure_debut, Timestamp heure_fin, String nom_cours, String nom_typecours) {
        CoursDao coursDao = new CoursDao();
        TypeCoursDAO typeCoursDAO = new TypeCoursDAO();
        SeanceDao seanceDao = new SeanceDao();

        int id_cours = coursDao.id_celon_nom(nom_cours);
        int id_typecours = typeCoursDAO.id_celon_nom(nom_typecours);

        String jours_semaine = (new SimpleDateFormat("E")).format(date.getTime());
        int heure_debut_entier = Integer.parseInt((new SimpleDateFormat("h")).format(heure_debut.getTime()));
        int heure_fin_entier = Integer.parseInt((new SimpleDateFormat("h")).format(heure_fin.getTime()));

        // Blindage de l'horaire et du week-end
        if (jours_semaine.equals("dim.") || jours_semaine.equals("sam.") ||
                heure_debut_entier < 8 || heure_debut_entier > 20 ||
                heure_fin_entier < 8 || heure_fin_entier > 20) {
            System.out.println("Problème date ou heure");
            return;
        }

        Seance seance = new Seance(0, semaine, date, heure_debut, heure_fin, 0, new Cours(id_cours, nom_cours), new TypeCours(id_typecours, nom_typecours));

        System.out.println("Je crée une séance la semaine " + seance.getSemaine() + " le " + seance.getDate() + " de " + seance.getHeureDebut() + " à " + seance.getHeureFin()
                + "\n" + seance.getEtat() + " " + seance.getCours().getNom() + " " + seance.getType().getNom());

        seanceDao.create(seance);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
