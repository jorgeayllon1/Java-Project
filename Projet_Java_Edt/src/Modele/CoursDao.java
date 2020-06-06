/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Wang David
 */
public class CoursDao extends DAO<Cours> {

    public CoursDao() {
        super();
    }

    public CoursDao(Connection conn) {
        super();
    }

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

    public boolean create(Cours cours) {
        return false;
    }

    public boolean delete(Cours cours) {
        return false;
    }

    public boolean update(Cours cours) {
        return false;
    }
    
    public boolean siExiste(int id)
    {
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours=0;
        boolean existe = false;
        
        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM cours WHERE id="+id); 

            while (rset.next())
            {

                id_cours = rset.getInt("id");
                cours = coursDao.find(id_cours);
                existe=true;
            }

        } catch (SQLException e) {
            
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return existe;
    }

    public boolean nomCoherent(String nom) {

        for (int i = 1; i < this.taille; i++) {
            if (nom.equals(this.find(i).getNom())) {
                return true;
            }
        }
        System.out.println("Nom de cours incohérent");
        return false;
    }

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

}
