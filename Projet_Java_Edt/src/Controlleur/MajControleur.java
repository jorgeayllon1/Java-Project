package Controlleur;

import Modele.*;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Controleur principale du projet
 * Il est utilisé à chaque fois que l'on cherche a mettre à jours la BDD
 * Etat 0 : non validable, il manque un prof ou une salle ou un groupe
 * Etat 1 : en cours de validation, il y a tout mais c'est pas validé
 * Etat 2 : validé, il y a tout et c'est validé
 * Etat 3 : annulée, il y a tout mais c'est pas validé
 * On caractèrise une séance par son heure debut, heure fin et par sa salle
 * Deux séance differentes ne peuvent avoir lieu en même temps dans une même salle
 *
 * @author jorge
 */
public class MajControleur extends Controleur {

    /**
     * Constructeur par default du controleur des mise à jours
     */
    public MajControleur() {
        super();
    }

    /**
     * Contructeur du controleur avec le lien avec le modèle
     *
     * @param dao Le Modèle du projet
     */
    public MajControleur(DAO dao) {
        super(dao);
    }

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
        EnseignantDAO enseignantDAO = new EnseignantDAO();

        int id_cours = coursDao.id_celon_nom(nouv_cours_nom);
        int id_typecours = typeCoursDAO.id_celon_nom(nouv_typeCours_nom);

        // Blinder pour avoir un nom de cours et type de cours cohérent
        if (id_cours == -1 || id_typecours == -1) {
            System.out.println("Nom de cours ou nom de type de cours inconnu");
            return;
        }

        Enseignant prof = seanceDao.trouverEnseignant(seance);
        ArrayList<Cours> les_cours_du_prof = enseignantDAO.trouverAllCours(prof.getID());

        boolean ok = false;
        for (Cours uncours : les_cours_du_prof) {
            if (uncours.getNom().equals(nouv_cours_nom)) {
                ok = true;
                break;
            }
        }

        if (!ok) {
            System.out.println("Le prof ne peut pas enseigner cette matière");
            return;
        }

        seance.getCours().setNom(nouv_cours_nom);
        seance.getCours().setId(id_cours);
        seance.getType().setNom(nouv_typeCours_nom);
        seance.getType().setId(id_typecours);
        seanceDao.update(seance);

    }

    /**
     * Crée une séance à l'état 0
     *
     * @param semaine
     * @param date
     * @param heure_debut
     * @param heure_fin
     * @param nom_cours
     * @param nom_typecours
     */
    public void creationSeance(int semaine, Date date, Timestamp heure_debut, Timestamp heure_fin, String
            nom_cours, String nom_typecours) {
        CoursDao coursDao = new CoursDao();
        TypeCoursDAO typeCoursDAO = new TypeCoursDAO();
        SeanceDao seanceDao = new SeanceDao();

        int id_cours = coursDao.id_celon_nom(nom_cours);
        int id_typecours = typeCoursDAO.id_celon_nom(nom_typecours);

        String jours_semaine = (new SimpleDateFormat("E")).format(date.getTime());
        int heure_debut_entier = Integer.parseInt((new SimpleDateFormat("H")).format(heure_debut.getTime()));
        int heure_fin_entier = Integer.parseInt((new SimpleDateFormat("H")).format(heure_fin.getTime()));

        // Blindage de l'horaire et du week-end
        if (jours_semaine.equals("dim.") || jours_semaine.equals("sam.") ||
                heure_debut_entier < 8 || heure_debut_entier > 20 ||
                heure_fin_entier < 8 || heure_fin_entier > 20) {
            System.out.println("Problème date ou heure");
            return;
        }

        Seance seance = new Seance(0, semaine, date, heure_debut, heure_fin, 0, new Cours(id_cours, nom_cours), new TypeCours(id_typecours, nom_typecours));

        System.out.println("Je crée une séance la semaine " + seance.getSemaine() + " le " + seance.getDate() + " de " + seance.getHeureDebut() + " à " + seance.getHeureFin()
                + seance.getEtat() + " " + seance.getCours().getNom() + " " + seance.getType().getNom());

        seanceDao.create(seance);
    }

    /**
     * Enlève l'enseignant associé à une séance
     *
     * @param seance la seance a modifier
     */
    public void enleverEnseignantdeSeance(Seance seance) {

        SeanceDao seanceDao = new SeanceDao();

        // Si il y a un enseignant dans la séance
        if (seanceDao.trouverEnseignant(seance) != null) {

            System.out.println("j'enleve le prof de la seance " + seance.getID());
            seance.setEtat(0);
            seanceDao.update(seance);
            seanceDao.enleverProf(seance);

        } else System.out.println("Il n'y a pas d'enseignant à ce cours");

    }

    /**
     * Enlève la salle associé à une séance
     *
     * @param seance la seance a modifier
     */
    public void enleverSalledeSeance(Seance seance) {
        SeanceDao seanceDao = new SeanceDao();

        // Si il y a un salle associée à la séance
        if (seanceDao.trouverSalle(seance) != null) {
            System.out.println("j'enleve la salle pour la seance " + seance.getID());
            seance.setEtat(0);
            seanceDao.update(seance);
            seanceDao.enleverSalle(seance);

        } else System.out.println("Il n'y a pas de salle pour ce cours");
    }

    /**
     * Enlève le groupe associé à une séance
     *
     * @param seance     la seance a modifier
     * @param nom_groupe le nom du groupe a modifier
     */
    public void enleverGroupeSeance(Seance seance, String nom_groupe) {
        SeanceDao seanceDao = new SeanceDao();
        GroupeDAO groupeDAO = new GroupeDAO();

        int id_groupe = groupeDAO.idCelonNom(nom_groupe);

        // Blindage du nom du groupe
        if (id_groupe == 0) {
            System.out.println("Nom de groupe inconnu");
            return;
        }

        ArrayList<Groupe> groupes = seanceDao.allGroupes(seance);

        //Blindage taille des groupes
        if (groupes.size() == 0) {
            System.out.println("Pas de groupe pour cette séance");
            return;
        }

        // Blindage : le groupe à bien cette seance
        boolean groupe_dans_seance = false;
        for (Groupe ungroupe : groupes) {
            if (ungroupe.getNom().equals(nom_groupe))
                groupe_dans_seance = true;
        }
        if (!groupe_dans_seance) {
            System.out.println("Erreur ce groupe n'a pas cette séance");
            return;
        }
        // Fin blindage

        // Si le nombre de groupe est de 1 alors le prochain groupe qu'on enlève fera que la séance ne sera pas validable car pas de groupe
        if (groupes.size() == 1) {
            System.out.println("j'enleve le groupe " + nom_groupe + " pour la seance " + seance.getID());
            seance.setEtat(0);
            seanceDao.update(seance);
            seanceDao.enleverGroupe(seance, id_groupe);

        }//Sinon on enlève juste le groupe => la séance l'état de la séance ne change pas
        else if (groupes.size() != 0) {
            System.out.println("j'enleve le groupe " + nom_groupe + " pour la seance " + seance.getID());
            seanceDao.enleverGroupe(seance, id_groupe);
        } else
            System.out.println("Il n'y a pas CE groupe pour cette séance");//Si ce code s'active, il y a peut être un problème dans le code
    }

    /**
     * Affecte la salle associé à une séance
     *
     * @param seance    la seance a modifier
     * @param nom_salle le nom de la salle a modifier
     */
    public void affecterSalleSeance(Seance seance, String nom_salle) {
        SeanceDao seanceDao = new SeanceDao();
        SalleDAO salleDAO = new SalleDAO();

        int id_salle = salleDAO.idCelonNom(nom_salle);

        // Blindage du nom de la salle
        if (id_salle == 0) {
            System.out.println("Nom de la salle inconnu");
            return;
        }

        //Blindage existance
        if (seanceDao.trouverSalle(seance) != null) {
            System.out.println("Cette seance à déjà une salle");
            return;
        }

        //Blindage disponibilité salle
        if (!salleDAO.disponible(seance, id_salle)) {
            System.out.println("Salle non disponible pour cette seance");
            return;
        }

        //Blindage Capacité salle
        if (salleDAO.find(id_salle).getCapacite() < seanceDao.nombreEleve(seance)) {
            System.out.println("Salle de capacité non suffisante");
            return;
        }

        System.out.println("j'ajoute la salle " + nom_salle + " pour la seance " + seance.getID());
        seanceDao.ajouterSalle(seance, id_salle);
        seanceDao.majEtat(seance);// Si on ajoute une salle, on change potentielemnt l'état de la séance
        seanceDao.update(seance);
    }

    /**
     * Affecte l'enseignant associé à une séance
     *
     * @param seance         la seance a modifier
     * @param nom_enseignant le nom de l'enseignant a modifier
     */
    public void affecterEnseignatSeance(Seance seance, String nom_enseignant) {
        SeanceDao seanceDao = new SeanceDao();
        EnseignantDAO enseignantDAO = new EnseignantDAO();

        int id_enseignant = enseignantDAO.idCelonNom(nom_enseignant);

        //Blindage du nom de l'enseignant
        if (id_enseignant == 0 || enseignantDAO.find(id_enseignant).getDroit() != 3) {
            System.out.println("Nom d'enseignant inconnu");
            return;
        }

        //Blindage existence
        if (seanceDao.trouverEnseignant(seance) != null) {
            System.out.println("Cette séance à déjà un prof");
            return;
        }


        //Blindage disponibilité prof
        if (!enseignantDAO.disponible(seance, id_enseignant)) {
            System.out.println("Enseignant non disponible pour cette seance");
            return;
        }

        System.out.println("j'ajoute le prof " + nom_enseignant + " pour la seance " + seance.getID());
        seanceDao.ajouterProf(seance, id_enseignant);
        seanceDao.majEtat(seance);
        seanceDao.update(seance);
    }

    /**
     * Affecte le groupe associé à une séance
     *
     * @param seance     la seance a modifier
     * @param nom_groupe le nom du groupe a modifier
     */
    public void affecterGroupeSeance(Seance seance, String nom_groupe) {
        SeanceDao seanceDao = new SeanceDao();
        GroupeDAO groupeDAO = new GroupeDAO();

        int id_groupe = groupeDAO.idCelonNom(nom_groupe);

        //Blindage du nom de groupe
        if (id_groupe == 0) {
            System.out.println("Nom de groupe inconnu");
            return;
        }

        //Blindage le groupe a déjà la séance : existence
        for (Groupe ungroupe : seanceDao.allGroupes(seance)) {
            if (ungroupe.getId() == id_groupe) {
                System.out.println("Le groupe a déjà le cours");
                return;
            }
        }

        //Blindage de la capacité
        int eleveTot = seanceDao.nombreEleve(seance) + groupeDAO.nombreEleve(id_groupe);

        // Si la séance a déjà une salle, on blind pour la capacite
        // ATTENTION si une séance n'a pas de salle, elle peut avoir un nombre infini d'étudiant tant qu'elle n'est pas validée
        if (seanceDao.trouverSalle(seance) != null) {
            if (eleveTot > seanceDao.trouverSalle(seance).getCapacite()) {
                System.out.println("Capacité de salle insuffisante");
                return;
            }
        }
        //Fin blindage taille

        //Ce code permet de dire que deux élève au sein d'un même groupe non pas forcement les mêmes cours
        //Blindage disponibilité du groupe
        //for (Etudiant eleve : groupeDAO.allEleves(id_groupe)) {
        //  if (!EtudiantDao.disponible(seance)) {
        //    System.out.println("Erreur : un étudiant n'est pas disponible pour ce cours");
        //  return;
        // }
        //}

        //Blindage disponibilité du groupe
        for (Groupe ungroupe : seanceDao.allGroupes(seance)) {
            if (!groupeDAO.disponible(seance, ungroupe.getId())) {
                System.out.println("Au moins un groupe n'est pas disponible pour la seance");
                return;
            }
        }

        System.out.println("J'ajoute le groupe " + nom_groupe + " à la séance id :" + seance.getID());
        seanceDao.ajouterGroupe(seance, id_groupe);
        seanceDao.majEtat(seance);
        seanceDao.update(seance);

    }

    /**
     * Valide une Seance
     *
     * @param seance la seance a modifier
     */
    public void validerSeance(Seance seance) {
        SeanceDao seanceDao = new SeanceDao();

        //Pas besoin de recalculer l'état mais on sait jamais
        seanceDao.majEtat(seance);
        seanceDao.update(seance);
        if (seance.getEtat() == 1 || seance.getEtat() == 3) {
            seance.setEtat(2);
            seanceDao.update(seance);
            System.out.println("Seance validée");
        } else System.out.println("Cette seance ne peut pas être validée");
    }

    /**
     * Annule une seance
     *
     * @param seance la seance a modifier
     */
    public void annulerSeance(Seance seance) {
        SeanceDao seanceDao = new SeanceDao();

        //Pas besoin de recalculer l'état mais on sait jamais
        seanceDao.majEtat(seance);
        seanceDao.update(seance);
        if (seance.getEtat() == 2) {
            seance.setEtat(3);
            seanceDao.update(seance);
            System.out.println("Seance annulée");
        } else System.out.println("Cette séance ne peut pas être annulée");
    }

    /**
     * Deplace une séance vers une nouvelle salle a une nouvelle date
     *
     * @param seance           la seance a modifier
     * @param nouv_heure_debut nouvelle heure de debut de seance
     * @param nouv_heure_fin   nouvelle heure de fin de seance
     * @param nouv_salle       nom de la nouvelle salle
     */
    public void deplacerSeance(Seance seance, Timestamp nouv_heure_debut, Timestamp nouv_heure_fin, String
            nouv_salle) {

        SalleDAO salleDAO = new SalleDAO();
        GroupeDAO groupeDAO = new GroupeDAO();
        EnseignantDAO enseignantDAO = new EnseignantDAO();
        SeanceDao seanceDao = new SeanceDao();

        int id_salle = salleDAO.idCelonNom(nouv_salle);

        // Blindage du nom de la salle
        if (id_salle == 0) {
            System.out.println("Nom de salle inconnue");
        }

        // Blindage weekend + heure
        int nouv_heure_debut_entier = Integer.parseInt((new SimpleDateFormat("H")).format(nouv_heure_debut.getTime()));
        int nouv_heure_fin_entier = Integer.parseInt((new SimpleDateFormat("H")).format(nouv_heure_fin.getTime()));
        String date_txt = (new SimpleDateFormat("EEE")).format(nouv_heure_fin.getTime());

        if (date_txt.equals("dim.") || date_txt.equals("sam.") ||
                nouv_heure_debut_entier < 8 || nouv_heure_debut_entier > 20 ||
                nouv_heure_fin_entier < 8 || nouv_heure_fin_entier > 20) {
            return;
        }
        // Fin blindage weekend + heure

        int nouv_semaine = Integer.parseInt((new SimpleDateFormat("w")).format(nouv_heure_debut.getTime()));
        Date nouv_date = new Date(nouv_heure_debut.getTime());

        seance.setHeure_debut(nouv_heure_debut);
        seance.setHeure_fin(nouv_heure_fin);
        seance.setSemaine(nouv_semaine);
        seance.setDate(nouv_date);

        //Blindage disponibilité salle V2
        if (!salleDAO.disponible(nouv_heure_debut, nouv_heure_fin, id_salle)) {
            System.out.println("La salle " + salleDAO.find(id_salle).getNom() + " est occupée de " + nouv_heure_debut + " a " + nouv_heure_fin);
            return;
        }

        //Blindage capacité salle
        if (salleDAO.find(id_salle).getCapacite() < seanceDao.nombreEleve(seance)) {
            System.out.println("Salle de capacité non suffisante");
            return;
        }

        //Blindage disponibilité enseignant V2
        //Changer enseignantDAO disponible

        Enseignant prof = seanceDao.trouverEnseignant(seance);

        //Blindage disponibilité prof
        if (!enseignantDAO.disponible(nouv_heure_debut, nouv_heure_fin, prof.getID())) {
            System.out.println("Enseignant non disponible pour cette seance");
            return;
        }

        //Blindage disponibilité Groupe
        for (Groupe ungroupe : seanceDao.allGroupes(seance)) {
            if (!groupeDAO.disponible(nouv_heure_debut, nouv_heure_fin, ungroupe.getId())) {
                System.out.println("Au moins un groupe n'est pas disponible pour la seance");
                return;
            }
        }

        seanceDao.majEtat(seance);//Au cas ou
        seanceDao.update(seance);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
