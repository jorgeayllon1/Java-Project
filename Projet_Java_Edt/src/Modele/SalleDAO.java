package Modele;

import java.sql.*;
import java.util.ArrayList;

/**
 * Partie DAO de la salle de classe
 *
 * @author jorge
 * @see Modele.DAO
 */

public class SalleDAO extends DAO<Salle> {

    public SalleDAO() {
        super();
    }

    public SalleDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Salle find(int id) {
        Salle lasalle = new Salle();
        int id_site = 0;
        Site site = new Site();
        SiteDAO siteDao = new SiteDAO();
        try {
            //this.conn = Connexion.seConnecter();
            //this.rset = this.conn.prepareStatement("SELECT * FROM salle WHERE id=" + id).executeQuery();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM salle WHERE id=" + id);


            if (rset.first()) {
                id_site = rset.getInt("id_site");
                site = siteDao.find(id_site);
                lasalle = new Salle(id, rset.getString("nom"), rset.getInt("capacite"), site);
            }


        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL SalleDao");
            sqle.printStackTrace();
        }

        return lasalle;
    }

    @Override
    public boolean create(Salle obj) {
        return false;
    }

    @Override
    public boolean delete(Salle obj) {
        return false;
    }

    @Override
    public boolean update(Salle obj) {
        return false;
    }

    public int idCelonNom(String nom) {
        int le_id = 0;

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT id FROM salle\n" +
                            "WHERE nom='" + nom+"'"
            );
            while (rset.next()) {
                le_id = rset.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL");
            e.printStackTrace();
        }

        if (le_id != 0) return le_id;
        else {
            System.err.println("Nom de salle inconnu");
            return 0;
        }

    }

    /**
     * Retourne les seances d'une salle pour une semaine choisie
     */
    public ArrayList<Seance> lesSeances(int id_salle, int numero_semaine) {

        ArrayList<Seance> lesseances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_salles\n" +
                            "ON seance_salles.id_seance = seance.id\n" +
                            "INNER JOIN salle\n" +
                            "on salle.id=seance_salles.id_salle\n" +
                            "WHERE salle.id=" + id_salle + "\n" +
                            "AND seance.semaine=" + numero_semaine
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

                lesseances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL SalleDao");
            e.printStackTrace();
        }
        return lesseances;
    }

    /**
     * Verifie si une seance est disponible pour telle salle
     *
     * @param seance
     * @param id_salle
     * @return
     */
    public boolean disponible(Seance seance, int id_salle) {
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT seance.heure_debut,seance.heure_fin FROM seance\n" +
                            "INNER JOIN seance_salles\n" +
                            "ON seance.id=seance_salles.id_seance\n" +
                            "INNER JOIN salle\n" +
                            "ON salle.id=seance_salles.id_salle\n" +
                            "WHERE salle.id=" + id_salle
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
     * Méthode qui renvoie vrai si le nom existe dans la bdd false sinon
     *
     * @param nom
     */
    public boolean siExiste(String nom) {
        Salle s = new Salle();
        SalleDAO sDao = new SalleDAO();
        int id_user = 0;
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM salle WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id_user = rset.getInt("id");
                s = sDao.find(id_user);
                existe = true;


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return existe;
    }

    /**
     * Verifie si la salle est disponible à telle periode
     *
     * @param heure_debut
     * @param heure_fin
     * @param id_salle
     * @return
     */
    public boolean disponible(Timestamp heure_debut, Timestamp heure_fin, int id_salle) {
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT seance.heure_debut,seance.heure_fin FROM seance\n" +
                            "INNER JOIN seance_salles\n" +
                            "ON seance.id=seance_salles.id_seance\n" +
                            "INNER JOIN salle\n" +
                            "ON salle.id=seance_salles.id_salle\n" +
                            "WHERE salle.id=" + id_salle
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
}
