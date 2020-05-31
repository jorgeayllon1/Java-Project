/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.sql.Date;
import java.util.*;


/**
 * @author Wang David
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
            this.conn = Connexion.seConnecter();


            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM utilisateur WHERE id = " + id);


            //this.rset = this.conn.prepareStatement("SELECT * FROM utilisateur WHERE id = " + id).executeQuery();

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

        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouee : probleme de classe");
            cnfe.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL");
            e.printStackTrace();
        }

        return user;

    }


    public Utilisateur create(Utilisateur user) {
        int id = this.trouverIdDispo(); //Trouver id dispo

        try {
            if (rset.first()) {

                PreparedStatement prepare = this.conn
                        .prepareStatement(
                                "INSERT INTO utilisateur (id, email,passwd,nom,prenom,droit) VALUES(?,?,?,?,?,?)"
                        );
                ///On insère les données
                prepare.setInt(1, id);
                prepare.setString(2, user.getMail());
                prepare.setString(3, user.getMdp());
                prepare.setString(4, user.getNom());
                prepare.setString(5, user.getPrenom());
                prepare.setInt(6, user.getDroit());

                //On éxécute
                prepare.executeUpdate();
                user = this.find(id);    //On trouve le nouvel utilisateur qui a été crée pour le retourner

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user; //On retourne le nouvel utilisateur
    }

    /**
     * Supprimer un élément dans la table
     *
     * @param user
     */
    public void delete(Utilisateur user) {
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

    }

    //Changer mail
    public Utilisateur update(Utilisateur user) {
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

        return user;
    }

    /**
     * Retourne les seances de l'utilisateur
     */
    public ArrayList<Seance> lesSeance(int id_utilisateur, int numero_semaine) {

        ArrayList<Seance> lesseances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            this.conn = Connexion.seConnecter();
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
                int id_cours = rset.getInt("id_cours");
                Cours cours = coursDAO.find(id_cours);
                int id_type = rset.getInt("id_type");
                TypeCours typeCours = typeCoursDAO.find(id_type);

                lesseances.add(new Seance(id, semaine, date, heure_debut, heure_fin, cours, typeCours));

            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erreur SQL");
            e.printStackTrace();
        }

        return lesseances;
    }

}
