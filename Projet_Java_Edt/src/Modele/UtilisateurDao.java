package Modele;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Partie DAO d'un utilisateur
 *
 * @author Wang David
 * @see Modele.DAO
 */
public class UtilisateurDao extends DAO<Utilisateur> {

    /**
     * Constructeur
     */
    public UtilisateurDao() {
        super();
    }

    public UtilisateurDao(Connection conn) {
        super(conn);
    }

    public Utilisateur find(int id) {
        Utilisateur user = new Utilisateur();
        try {

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM utilisateur WHERE id = " + id);


            if (rset.first())
                user = new Utilisateur(
                        id,
                        rset.getString("passwd"),
                        rset.getString("email"),
                        rset.getString("nom"),
                        rset.getString("prenom"),
                        rset.getInt("droit")
                );
            else user = null;

        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL UtilisateurDao");
            e.printStackTrace();
        }

        return user;

    }

    public boolean create(Utilisateur user) {
        return false;
    }

    /**
     * Supprimer un élément dans la table
     *
     * @param user
     * @return
     */
    public boolean delete(Utilisateur user) {
        try {
            this.conn.createStatement(rset.TYPE_SCROLL_INSENSITIVE,
                    rset.CONCUR_UPDATABLE
            ).executeUpdate(
                    "DELETE FROM utilisateur WHERE id = " + user.getID()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //this.taille--;

        return true;
    }

    public boolean update(Utilisateur user) {
        try {

            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "UPDATE utilisateur SET email = '" + user.getMail() + "'" +
                            " WHERE id = " + user.getID()
            );

            user = this.find(user.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Retourne les seances de l'utilisateur pour une semaine choisie
     */
    public ArrayList<Seance> lesSeance(int id_utilisateur, int numero_semaine) {

        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            // this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM `seance` INNER JOIN seance_groupes ON seance_groupes.id_seance=seance.id" +
                            " INNER JOIN groupe ON groupe.id = seance_groupes.id_groupe INNER JOIN etudiant ON " +
                            "etudiant.id_groupe=groupe.id WHERE etudiant.id_utilisateur=" + id_utilisateur +
                            " AND seance.semaine = " + numero_semaine);


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
            System.out.println("Erreur SQL UtilisateurDao");
            e.printStackTrace();
        }

        return les_seances;
    }

}
