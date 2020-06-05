/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Wang David
 */
public class SeanceDao extends DAO<Seance> {

    public SeanceDao() {
        super();
    }

    public SeanceDao(Connection conn) {
        super(conn);
    }

    @Override
    public Seance find(int id) {
        Seance seance = new Seance();
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours = 0;

        TypeCours type = new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type = 0;
        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id = " + id);

            if (rset.first()) {
                id_cours = rset.getInt("id_cours");
                cours = coursDao.find(id_cours);
                id_type = rset.getInt("id_type");
                type = typeDao.find(id_type);
                seance = new Seance(
                        id,
                        rset.getInt("semaine"),
                        rset.getDate("date"),
                        rset.getTimestamp("heure_debut"),
                        rset.getTimestamp("heure_fin"),
                        rset.getInt("etat"),
                        cours,
                        type
                );
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return seance;

    }

    @Override
    public boolean create(Seance obj) {

        int id = this.trouverIdDispo(); //Trouver id dispo

        try {
            if (rset.first()) {

                PreparedStatement prepare = this.conn
                        .prepareStatement(
                                "INSERT INTO seance (id, semaine,date,heure_debut,heure_fin,id_cours,id_type) VALUES(?,?,?,?,?,?,?)"
                        );
                ///On insère les données
                prepare.setInt(1, id);
                prepare.setInt(2, obj.getSemaine());
                prepare.setDate(3, obj.getDate());
                prepare.setTimestamp(4, obj.getHeureDebut());
                prepare.setTimestamp(5, obj.getHeureFin());
                prepare.setInt(6, obj.getCours().getID());
                prepare.setInt(7, obj.getType().getId());

                //On éxécute
                prepare.executeUpdate();
                obj = this.find(id);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean delete(Seance seance) {
        try {
            this.conn.createStatement(rset.TYPE_SCROLL_INSENSITIVE,
                    rset.CONCUR_UPDATABLE
            ).executeUpdate(
                    "DELETE FROM utilisateur WHERE id = " + seance.getID()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean update(Seance obj) {
        try {

            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "UPDATE seance SET date = '" + obj.getDate() + "'" +
                            " WHERE id = " + obj.getID()
            );

            obj = this.find(obj.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Enseignant trouverEnseignant(Seance seance) {
        Enseignant prof = new Enseignant();
        EnseignantDAO profDao = new EnseignantDAO();
        int id_prof = 0;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_enseignant FROM seance_enseignants WHERE id_seance = " + seance.getID()); //On cherche tout les ID des séances de ce groupe


            while (rset.next()) {

                id_prof = rset.getInt("id_enseignant");
                prof = profDao.find(id_prof);


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return prof;
    }

    public Groupe trouverGroupe(Seance seance) {
        Groupe groupe = new Groupe();
        GroupeDAO groupeDao = new GroupeDAO();
        int id_groupe = 0;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_groupe FROM seance_groupes WHERE id_seance = " + seance.getID()); //On cherche tout les ID des séances de ce groupe


            while (rset.next()) {

                id_groupe = rset.getInt("id_groupe");
                groupe = groupeDao.find(id_groupe);


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return groupe;
    }

    public Seance chercherSeance(String nom_salle, Timestamp heure_debut, Timestamp heure_fin) {

        Seance seance = new Seance();
        Cours cours;
        CoursDao coursDao = new CoursDao();
        int id_cours;

        TypeCours type;
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_salles\n" +
                            "ON seance.id=seance_salles.id_seance\n" +
                            "INNER JOIN salle\n" +
                            "ON salle.nom='" + nom_salle + "'\n" +
                            "WHERE seance.heure_debut='" + heure_debut + "'\n" +
                            "AND seance.heure_fin='" + heure_fin + "'"
            );

            if (rset.first()) {
                id_cours = rset.getInt("id_cours");
                cours = coursDao.find(id_cours);
                id_type = rset.getInt("id_type");
                type = typeDao.find(id_type);
                seance = new Seance(
                        rset.getInt("id"),
                        rset.getInt("semaine"),
                        rset.getDate("date"),
                        heure_debut,
                        heure_fin,
                        rset.getInt("etat"),
                        cours,
                        type
                );
            } else {
                System.err.println("Error : séance non trouvée");
                return null;
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return seance;

    }

}
