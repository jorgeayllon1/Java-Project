package Modele;

import java.sql.*;
import java.util.ArrayList;

/**
 * Partie DAO d'un enseignant
 *
 * @author jorge
 */
public class EnseignantDAO extends DAO<Enseignant> {

    /**
     * Default construtor
     */
    public EnseignantDAO() {
        super();
    }

    /**
     * Constructeur avec connexion bdd
     *
     * @param conn la connexion à la bdd
     */
    public EnseignantDAO(Connection conn) {
        super(conn);
    }

    /**
     * @param id_utilisateur
     * @return
     * @see DAO#find(int)
     */
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

    /**
     * @param obj objet a crée dans la bdd
     * @return
     * @see DAO#create(Object)
     */
    @Override
    public boolean create(Enseignant obj) {
        return false;
    }

    /**
     * @param obj objet a effacer
     * @return
     * @see DAO#delete(Object)
     */
    @Override
    public boolean delete(Enseignant obj) {

        return false;
    }

    /**
     * @param obj objet a mettre a jours
     * @return
     * @see DAO
     */
    @Override
    public boolean update(Enseignant obj) {
        return false;

    }

    /**
     * trouve les id des séances
     *
     * @param prof prof que l'on veut
     * @return
     */
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

    /**
     * Retourne la liste des seances
     *
     * @param array
     * @return
     * @deprecated utiliser une autre méthode
     */
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

    /**
     * Retourne tout les enseignant
     *
     * @return
     */
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

    /**
     * Retourne toutes les salles d'un prof sur une semaine
     *
     * @param id_prof
     * @param numero_semaine
     * @return
     */
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

    /**
     * Retourne toutes les seance sur une periode celon un groupe
     *
     * @param id_prof
     * @param id_groupe
     * @param debut
     * @param fin
     * @return
     */
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

    /**
     * Retourne l'id du prof dans la bdd celon le nom
     *
     * @param nom
     * @return
     */
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

    /**
     * Verifie si un enseignant est disponible pour une seance
     *
     * @param seance
     * @param id_enseignant
     * @return
     */
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
                long a = heure_debut.getTime();
                long b = heure_fin.getTime();
                long c = seance.getHeureDebut().getTime();
                long d = seance.getHeureFin().getTime();
                if (c < a && d > a ||
                        c > a && d < b ||
                        c < b && d > b) {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Verifie si un enseignant est disponible pour une periode
     *
     * @param heure_debut
     * @param heure_fin
     * @param id_enseignant
     * @return
     */
    public boolean disponible(Timestamp heure_debut, Timestamp heure_fin, int id_enseignant) {
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
                Timestamp seance_heure_debut = rset.getTimestamp("heure_debut");
                Timestamp seance_heure_fin = rset.getTimestamp("heure_fin");
                long a = seance_heure_debut.getTime();
                long b = seance_heure_fin.getTime();
                if (heure_debut.getTime() < a && heure_fin.getTime() > a ||
                        heure_debut.getTime() > a && heure_fin.getTime() < b ||
                        heure_debut.getTime() < b && heure_fin.getTime() > b) {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Retoune tout les cours d'un prof
     *
     * @param id_prof
     * @return
     */
    public ArrayList<Cours> trouverAllCours(int id_prof) {
        ArrayList<Cours> les_cours = new ArrayList<>();
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT DISTINCT * FROM cours\n" +
                            "INNER JOIN enseignant\n" +
                            "ON enseignant.id_cours=cours.id\n" +
                            "WHERE enseignant.id_utilisateur=" + id_prof
            );
            while (rset.next()) {
                Cours cours = new Cours();
                cours.setId(rset.getInt("id"));
                cours.setNom(rset.getString("nom"));
                les_cours.add(cours);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL GroupeDAO");
            e.printStackTrace();
        }
        return les_cours;
    }

    /**
     * Retourne toutes les séance d'un prof
     *
     * @param id_prof
     * @return
     */
    public ArrayList<Seance> allSeance(int id_prof) {
        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT seance.id,semaine,date,heure_debut,heure_fin,etat,id_cours,id_type FROM seance\n" +
                            "INNER JOIN seance_enseignants\n" +
                            "ON seance.id=seance_enseignants.id_seance\n" +
                            "WHERE seance_enseignants.id_enseignant=" + id_prof
            );

            while (rset.next()) {

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

                les_seances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL EnseignantDAO");
            e.printStackTrace();
        }

        return les_seances;
    }
}
