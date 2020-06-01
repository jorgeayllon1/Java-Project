/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.sql.SQLException;

/**
 * @author Wang David
 */
public class EtudiantDao extends UtilisateurDao {

    public EtudiantDao() {
        super();
    }

    public EtudiantDao(Connection conn) {
        super(conn);
    }

    public Etudiant find(int id) {
        Etudiant etudiant = new Etudiant();
        int id_user = 0;
        int id_groupe = 0;
        Utilisateur user = new Utilisateur();
        UtilisateurDao userDao = new UtilisateurDao();
        Groupe groupe = new Groupe();
        GroupeDAO groupeDao = new GroupeDAO();
        try {
            //this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM etudiant WHERE id_utilisateur = " + id);

            if (rset.first()) {
                id_user = rset.getInt("id_utilisateur");
                id_groupe = rset.getInt("id_groupe");
                user = userDao.find(id_user);
                groupe = groupeDao.find(id_groupe);
                etudiant = new Etudiant(
                        id, user.getMdp(), user.getMail(), user.getNom(), user.getPrenom(), user.getDroit(), rset.getString("numero"), groupe


                );
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EtudiantDao");
            e.printStackTrace();
        }

        return etudiant;

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
            //this.conn=Connexion.seConnecter();

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_salle FROM seance_salles WHERE id_seance=" + seance.getID());//On cherche toutes les séances avec le même id_seance

            while (rset.next()) {
                id_salle = rset.getInt("id_salle"); //On trouve l'id de la salle
            }

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM salle WHERE id=" + id_salle);//On cherche toutes les séances avec le même id_seance

            while (rset.next()) {
                id_site = rset.getInt("id_site"); //On get l'id du site
                site = siteDao.find(id_site); //On créee un objet site en cherchant dans la bdd en fonction de cet id
                salle = new Salle(id_salle, rset.getString("nom"), rset.getInt("capacite"), site); //On créee un objet salle que l'on va retourner
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EtudiantDao");
            e.printStackTrace();
        }

        return salle;
    }

}
