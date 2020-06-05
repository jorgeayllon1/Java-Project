/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.SQLException;

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


}
