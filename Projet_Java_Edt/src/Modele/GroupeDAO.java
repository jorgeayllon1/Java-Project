package Modele;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * @author jorge
 */

public class GroupeDAO extends DAO<Groupe> {

    public GroupeDAO() {
        super();
    }

    public GroupeDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Groupe find(int id) {
        Groupe legroupe = new Groupe();

        int id_promo = 0;
        Promotion promo = new Promotion();
        PromotionDAO promoDao = new PromotionDAO();
        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM groupe WHERE id=" + id);

            if (rset.first()) {
                id_promo = rset.getInt("id_promotion");
                promo = promoDao.find(id_promo);
                legroupe = new Groupe(id, rset.getString("nom"), promo);
            }

        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL GroupeDAO");
            sqle.printStackTrace();
        }

        return legroupe;
    }

    @Override
    public boolean create(Groupe obj) {
        return false;

    }

    @Override
    public boolean delete(Groupe obj) {

        return false;
    }

    @Override
    public boolean update(Groupe obj) {
        return false;

    }

    public ArrayList<Integer> trouverIdSeance(Groupe groupe) {

        ArrayList<Integer> mes_id_seances = new ArrayList();
        try {

            //this.conn = Connexion.seConnecter();

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_seance FROM seance_groupes WHERE id_groupe = " + groupe.getId()); //On cherche tout les ID des séances de ce groupe


            while (rset.next()) {
                mes_id_seances.add(rset.getInt("id_seance"));
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL GroupeDAO");
            e.printStackTrace();
        }
        return mes_id_seances;
    }

    public ArrayList<Seance> trouverAllSeances(ArrayList<Integer> array) {
        ArrayList<Seance> seance_groupe = new ArrayList();
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours = 0;

        TypeCours type = new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type = 0;

        try {
            //this.conn = Connexion.seConnecter();
            for (int i = 0; i < array.size(); i++) {
                this.rset = this.conn.createStatement(
                        this.rset.TYPE_SCROLL_INSENSITIVE,
                        this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id=" + array.get(i));//On cherche toutes les séances avec le même id_seance

                while (rset.next()) {
                    id_cours = rset.getInt("id_cours");
                    cours = coursDao.find(id_cours);
                    id_type = rset.getInt("id_type");
                    type = typeDao.find(id_type);
                    seance_groupe.add(new Seance(array.get(i), rset.getInt("semaine"),
                            rset.getDate("date"),
                            rset.getTimestamp("heure_debut"),
                            rset.getTimestamp("heure_fin"),
                            rset.getInt("etat"),
                            cours,
                            type));
                }
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL GroupeDAO");
            e.printStackTrace();
        }
        return seance_groupe;
    }

    /**
     * Méthode renvoyant toutes les séances d'un etudiant en fonction d'une semaine prenant en param
     * une liste d'integer representant les id_seances relatives à cet etudiant
     *
     * @param array
     * @param semaine
     */

    /*public ArrayList<Seance> allSeancesAvecSemaine(ArrayList<Integer> array, int semaine) {
        ArrayList<Seance> seance_groupe = new ArrayList();
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours = 0;

        TypeCours type = new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type = 0;

        try {
            //this.conn = Connexion.seConnecter();
            for (int i = 0; i < array.size(); i++) {
                this.rset = this.conn.createStatement(
                        this.rset.TYPE_SCROLL_INSENSITIVE,
                        this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE semaine=" + semaine+ "AND id="+array.get(i));//On cherche toutes les séances avec le même id_seance

                while (rset.next()) {
                    id_cours = rset.getInt("id_cours");
                    cours = coursDao.find(id_cours);
                    id_type = rset.getInt("id_type");
                    type = typeDao.find(id_type);
                    seance_groupe.add(new Seance(array.get(i), rset.getInt("semaine"),
                            rset.getDate("date"),
                            rset.getTimestamp("heure_debut"),
                            rset.getTimestamp("heure_fin"),
                            cours,
                            type));
                }
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL GroupeDAO");
            e.printStackTrace();
        }
        return seance_groupe;
    }*/

    /**
     * Retourne le id celon le nom de groupe rechercher
     */
    public int idCelonNom(String lenom) {

        int le_id = 0;

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT id FROM groupe\n" +
                            "WHERE nom=\"" + lenom + "\""
            );
            while (rset.next()) {
                le_id = rset.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL GroupeDAO");
            e.printStackTrace();
        }

        if (le_id != 0) return le_id;
        else {
            System.err.println("Nom de groupe inconnu");
            return 0;
        }
    }

    /**
     * Retourne les toutes les seances celons une semaine choisi
     */
    public ArrayList<Seance> trouverAllSeancesSemaine(int id_groupe, int numero_semaine) {

        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_groupes\n" +
                            "ON seance.id = seance_groupes.id_seance\n" +
                            "INNER JOIN groupe\n" +
                            "ON seance_groupes.id_groupe=groupe.id\n" +
                            "WHERE groupe.id=" + id_groupe + "\n" +
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

                les_seances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL GroupeDAO");
            e.printStackTrace();
        }

        return les_seances;
    }

    public int nombreEleve(int id_groupe) {
        int nombreEleve = 0;

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT COUNT(id_utilisateur) FROM etudiant\n" +
                            "WHERE id_groupe=" + id_groupe
            );
            while (rset.next()) {
                nombreEleve = rset.getInt("COUNT(id_utilisateur)");
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL GroupeDAO");
            e.printStackTrace();
        }

        return nombreEleve;
    }

    public boolean disponible(Seance seance, int id_groupe) {
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT heure_debut,heure_fin FROM seance\n" +
                            "INNER JOIN seance_groupes\n" +
                            "on seance_groupes.id_seance=seance.id\n" +
                            "INNER JOIN groupe\n" +
                            "ON seance_groupes.id_groupe=groupe.id\n" +
                            "WHERE groupe.id=" + id_groupe
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

    public boolean disponible(Timestamp heure_debut, Timestamp heure_fin, int id_groupe) {
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT heure_debut,heure_fin FROM seance\n" +
                            "INNER JOIN seance_groupes\n" +
                            "on seance_groupes.id_seance=seance.id\n" +
                            "INNER JOIN groupe\n" +
                            "ON seance_groupes.id_groupe=groupe.id\n" +
                            "WHERE groupe.id=" + id_groupe
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
     * Méthode qui renvoie vrai si le nom existe dans la bdd false sinon
     *
     * @param nom
     */
    public boolean siExiste(String nom) {
        Groupe g = new Groupe();
        GroupeDAO gDao = new GroupeDAO();
        int id_user = 0;
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM groupe WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id_user = rset.getInt("id");
                g = gDao.find(id_user);
                existe = true;


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return existe;
    }

}
