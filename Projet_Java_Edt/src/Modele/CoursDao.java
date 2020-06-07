package Modele;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Version DAO du cours
 *
 * @author Wang David
 */
public class CoursDao extends DAO<Cours> {

    /**
     * Constructeur principale
     */
    public CoursDao() {
        super();
    }

    /**
     * Contructeur normal
     *
     * @param conn La connection qu'on va utiliser pour se connecter à la bdd
     */
    public CoursDao(Connection conn) {
        super();
    }

    /**
     * Trouve le cours celon l'id
     *
     * @param id l'id du cours a chercher
     * @return le cours trouvé
     */
    public Cours find(int id) {
        Cours cours = new Cours();
        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM cours WHERE id = " + id);

            if (rset.first())
                cours = new Cours(
                        id,
                        rset.getString("nom")
                );

        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL CoursDao");
            e.printStackTrace();
        }

        return cours;
    }

    /**
     * Cree un nouveau cours dans la bdd
     *
     * @param cours le cours a crée
     * @return
     */
    public boolean create(Cours cours) {
        return false;
    }

    /**
     * Efface le cours dans la bdd
     *
     * @param cours le cours a effacer
     * @return vraie si tout c'est bien passer
     */
    public boolean delete(Cours cours) {
        return false;
    }

    /**
     * maj d'un cours
     *
     * @param cours cours a maj
     * @return vraie si tout c'est bien passer
     */
    public boolean update(Cours cours) {
        return false;
    }

    /**
     * Verifie si un cours existe pour l'id correspondant
     *
     * @param id id du cours a chercher
     * @return vraie si tout c'est bien passer
     */
    public boolean siExiste(int id) {
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours = 0;
        boolean existe = false;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM cours WHERE id=" + id);

            while (rset.next()) {

                id_cours = rset.getInt("id");
                cours = coursDao.find(id_cours);
                existe = true;
            }

        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return existe;
    }

    /**
     * Verifie si le nom fournie est un nom de cours
     *
     * @param nom nom a verifier
     * @return vraie si tout c'est bien passer
     */
    public boolean nomCoherent(String nom) {

        for (int i = 1; i < this.taille; i++) {
            if (nom.equals(this.find(i).getNom())) {
                return true;
            }
        }
        System.out.println("Nom de cours incohérent");
        return false;
    }

    /**
     * Retourne l'id du cours celon le nom fourni
     *
     * @param nom le nom a chercher
     * @return le id
     */
    public int id_celon_nom(String nom) {

        int id = -1;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM cours WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id = rset.getInt("id");
            }


        } catch (SQLException e) {

            System.err.println("Connexion echouee : probleme SQL CoursDAO");
            e.printStackTrace();
        }
        if (id == -1) {
            System.err.println("ERREUR : nom de cours non trouvé");
        }
        return id;
    }

    /**
     * Retourne le nombre de seance d'un cours
     *
     * @param id_cours le id du cours a chercher
     * @return le nom de seance
     */
    public int nombreDeSeance(int id_cours) {
        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT COUNT(id) FROM seance\n" +
                            "WHERE seance.id_cours=" + id_cours
            );
            while (rset.next()) {
                return rset.getInt("COUNT(id)");
            }


        } catch (SQLException e) {
            System.err.println("Connexion echouee : probleme SQL CoursDAO");
            e.printStackTrace();
        }
        return 0;
    }

}
