package Modele;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author jorge
 */

public class EnseignantDAO extends DAO<Enseignant> {

    public EnseignantDAO() {
        super();
    }

    public EnseignantDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Enseignant find(int id_utilisateur) {
        Enseignant leprof = new Enseignant();
        int id_user = 0;
        int id_cours = 0;
        Utilisateur user = new Utilisateur();
        UtilisateurDao userDao = new UtilisateurDao();
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM enseignant WHERE id_utilisateur=" + id_utilisateur);

            if (rset.first()) {
                id_user = rset.getInt("id_utilisateur");
                id_cours = rset.getInt("id_cours");
                user = userDao.find(id_user);
                cours = coursDao.find(id_cours);
                leprof = new Enseignant(id_utilisateur, user.getMdp(), user.getMail(), user.getNom(), user.getPrenom(), user.getDroit(), cours);
            }

        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL EnseignantDao");
            sqle.printStackTrace();
        }

        return leprof;
    }

    @Override
    public Enseignant create(Enseignant obj) {
        return new Enseignant();

    }

    @Override
    public void delete(Enseignant obj) {

    }

    @Override
    public Enseignant update(Enseignant obj) {
        return new Enseignant();

    }

    public ArrayList<Integer> trouverIdSeance(Enseignant prof) {

        ArrayList<Integer> mes_id_seances = new ArrayList();
        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_seance FROM seance_enseignants WHERE id_enseignant = " + prof.id); //On cherche tout les ID des séances de ce groupe


            while (rset.next()) {
                mes_id_seances.add(rset.getInt("id_seance"));
            }

        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EnseignantDao");
            e.printStackTrace();
        }
        return mes_id_seances;
    }

    public ArrayList<Seance> trouverAllSeances(ArrayList<Integer> array) {
        ArrayList<Seance> seance_prof = new ArrayList();
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours = 0;

        TypeCours type = new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type = 0;

        try {
            for (int i = 0; i < array.size(); i++) {
                this.rset = this.conn.createStatement(
                        this.rset.TYPE_SCROLL_INSENSITIVE,
                        this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id=" + array.get(i));//On cherche toutes les séances avec le même id_seance

                if (rset.next()) {
                    id_cours = rset.getInt("id_cours");
                    cours = coursDao.find(id_cours);
                    id_type = rset.getInt("id_type");
                    type = typeDao.find(id_type);
                    seance_prof.add(new Seance(array.get(i), rset.getInt("semaine"),
                            rset.getDate("date"),
                            rset.getTimestamp("heure_debut"),
                            rset.getTimestamp("heure_fin"),
                            cours,
                            type));
                }
            }

        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EnseignantDao");
            e.printStackTrace();
        }
        return seance_prof;
    }

    /**
     * Retourne la salle en fonction de la seance
     */
    public Salle trouverSalle(Seance seance) {
        int id_salle = 0;
        int id_site = 0;
        Salle salle = new Salle();
        Site site = new Site();
        SiteDAO siteDao = new SiteDAO();
        try {
            //this.conn = Connexion.seConnecter();

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_salle FROM seance_salles WHERE id_seance=" + seance.getID());//On cherche toutes les séances avec le même id_seance

            if (rset.next()) {
                id_salle = rset.getInt("id_salle"); //On trouve l'id de la salle
            }

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM salle WHERE id=" + id_salle);//On cherche toutes les séances avec le même id_seance

            if (rset.next()) {
                id_site = rset.getInt("id_site"); //On get l'id du site
                site = siteDao.find(id_site); //On créee un objet site en cherchant dans la bdd en fonction de cet id
                salle = new Salle(id_salle, rset.getString("nom"), rset.getInt("capacite"), site); //On créee un objet salle que l'on va retourner
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EnseignantDao");
            e.printStackTrace();
        }

        return salle;
    }


    public ArrayList<Enseignant> listeEnseignant() {
        ArrayList<Enseignant> liste_prof = new ArrayList();
        ArrayList<Integer> deja_compte = new ArrayList();
        int id_cours = 0;
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_user = 0;
        Utilisateur user = new Utilisateur();
        UtilisateurDao userDao = new UtilisateurDao();
        try {
            //this.conn = Connexion.seConnecter();

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM enseignant");

            while (rset.next()) {

                if (!deja_compte.contains(rset.getInt("id_utilisateur"))) //S'il est deja dans notre tableau d'int on ne l'ajoute plus sinon on l'ajoute
                {
                    id_cours = rset.getInt("id_cours");
                    cours = coursDao.find(id_cours);
                    id_user = rset.getInt("id_utilisateur");
                    user = userDao.find(id_user);
                    deja_compte.add(rset.getInt("id_utilisateur"));
                    liste_prof.add(new Enseignant(rset.getInt("id_utilisateur"), user.getMdp(), user.getMail(), user.getNom(), user.getPrenom(), user.getDroit(), cours));
                }

            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EnseignantDao");
            e.printStackTrace();
        }

        return liste_prof;
    }

}
