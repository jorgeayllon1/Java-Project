/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Wang David
 */
public class SeanceDao extends DAO<Seance> {

    public SeanceDao() {
    }

    public SeanceDao(Connection conn) {
        super(conn);
    }

    @Override
    public Seance find(int id) {
        Seance seance = new Seance();
        try {
            try {
                this.conn = Connexion.seConnecter();
                this.rset = this.conn.createStatement(
                        this.rset.TYPE_SCROLL_INSENSITIVE,
                        this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id = " + id);

                if (rset.first())
                    seance = new Seance(
                            id,
                            rset.getInt("semaine"),
                            rset.getDate("date"),
                            rset.getTimestamp("heure_debut"),
                            rset.getTimestamp("heure_fin"),
                            rset.getInt("id_cours"),
                            rset.getInt("id_type")
                    );

            } catch (ClassNotFoundException cnfe) {
                System.out.println("Connexion echouee : probleme de classe");
                cnfe.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL");
            e.printStackTrace();
        }

        return seance;

    }

    @Override
    public Seance create(Seance obj) {

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
                prepare.setInt(6, obj.getIdCours());
                prepare.setInt(7, obj.getIdType());

                //On éxécute
                prepare.executeUpdate();
                obj = this.find(id);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public void delete(Seance seance) {
        try {
            this.conn.createStatement(rset.TYPE_SCROLL_INSENSITIVE,
                    rset.CONCUR_UPDATABLE
            ).executeUpdate(
                    "DELETE FROM utilisateur WHERE id = " + seance.getID()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Seance update(Seance obj) {
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

        return obj;

    }


}
