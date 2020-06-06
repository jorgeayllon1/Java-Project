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
    public boolean create(Enseignant obj) {
        return false;
    }

    @Override
    public boolean delete(Enseignant obj) {

        return false;
    }

    @Override
    public boolean update(Enseignant obj) {
        return false;

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
                            rset.getInt("etat"),
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

    public ArrayList<Seance> trouverAllSeancesSemaine(int id_prof, int numero_semaine) {


        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();
        ArrayList<Integer> deja_compte = new ArrayList();

        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_enseignants\n" +
                            "ON seance.id = seance_enseignants.id_seance\n" +
                            "INNER JOIN enseignant\n" +
                            "ON seance_enseignants.id_enseignant=enseignant.id_utilisateur\n" +
                            "WHERE enseignant.id_utilisateur=" + id_prof + "\n" +
                            "AND seance.semaine=" + numero_semaine
            );


            while (rset.next()) {

                if (!deja_compte.contains(rset.getInt("id"))) {
                    int id = rset.getInt("id");
                    int semaine = rset.getInt("semaine");
                    Date date = rset.getDate("date");
                    Timestamp heure_debut = rset.getTimestamp("heure_debut");
                    Timestamp heure_fin = rset.getTimestamp("heure_fin");
                    int etat = rset.getInt("etat");
                    int id_cours = rset.getInt("id_cours");
                    Cours cours = coursDAO.find(id_cours);
                    int id_type = rset.getInt("id_type");
                    TypeCours typeCours = typeCoursDAO.find(id_type);
                    deja_compte.add(rset.getInt("id"));
                    les_seances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));
                }


            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL GroupeDAO");
            e.printStackTrace();
        }

        return les_seances;
    }

    public ArrayList<Seance> trouverSeancesParGroupeSurPeriode(int id_prof, int id_groupe, Date debut, Date fin) {
        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();
        ArrayList<Integer> deja_compte = new ArrayList();

        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_enseignants\n" +
                            "ON seance.id = seance_enseignants.id_seance\n" +
                            "INNER JOIN enseignant\n" +
                            "ON seance_enseignants.id_enseignant=enseignant.id_utilisateur\n" +
                            "INNER JOIN seance_groupes\n" +
                            "ON seance_groupes.id_groupe=" + id_groupe + "\n" +
                            "WHERE enseignant.id_utilisateur=" + id_prof + "\n" +
                            "AND seance.date BETWEEN \'" + debut + "\' AND \'" + fin + "\'"
            );


            while (rset.next()) {

                if (!deja_compte.contains(rset.getInt("id"))) {
                    int id = rset.getInt("id");
                    int semaine = rset.getInt("semaine");
                    Date date = rset.getDate("date");
                    Timestamp heure_debut = rset.getTimestamp("heure_debut");
                    Timestamp heure_fin = rset.getTimestamp("heure_fin");
                    int etat = rset.getInt("etat");
                    int id_cours = rset.getInt("id_cours");
                    Cours cours = coursDAO.find(id_cours);
                    int id_type = rset.getInt("id_type");
                    TypeCours typeCours = typeCoursDAO.find(id_type);
                    deja_compte.add(rset.getInt("id"));
                    les_seances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));
                }


            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL GroupeDAO");
            e.printStackTrace();
        }

        return les_seances;
    }

    /**
     * Méthode pour trouver un prof en fonction de son nom
     *
     * @param nom
     */
    public Enseignant trouverProfAvecNom(String nom) {
        Enseignant prof = new Enseignant();
        EnseignantDAO profDao = new EnseignantDAO();
        int id_user = 0;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM utilisateur WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id_user = rset.getInt("id");
                prof = profDao.find(id_user);


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return prof;
    }

    /**
     * Méthode qui renvoie vrai si le nom existe dans la bdd false sinon
     *
     * @param nom
     */
    public boolean siExiste(String nom) {
        Enseignant prof = new Enseignant();
        EnseignantDAO profDao = new EnseignantDAO();
        int id_user = 0;
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM utilisateur WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id_user = rset.getInt("id");
                prof = profDao.find(id_user);
                existe = true;


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return existe;
    }

    public int idCelonNom(String nom) {
        int le_id = 0;
        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT id FROM utilisateur\n" +
                            "WHERE nom='" + nom + "'"
            );
            while (rset.next()) {
                le_id = rset.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL");
            e.printStackTrace();
        }

        return le_id;
    }

    public boolean disponible(Seance seance, int id_enseignant) {
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT DISTINCT heure_debut,heure_fin FROM seance\n" +
                            "INNER JOIN seance_enseignants\n" +
                            "ON seance_enseignants.id_seance=seance.id\n" +
                            "INNER JOIN enseignant\n" +
                            "ON enseignant.id_utilisateur=seance_enseignants.id_enseignant\n" +
                            "WHERE enseignant.id_utilisateur=" + id_enseignant
            );
            while (rset.next()) {
                Timestamp heure_debut = rset.getTimestamp("heure_debut");
                Timestamp heure_fin = rset.getTimestamp("heure_fin");
                if (seance.getHeureDebut().getTime() < heure_fin.getTime() || seance.getHeureFin().getTime() > heure_debut.getTime()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL");
            e.printStackTrace();
        }
        return true;
    }

}
