/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Wang David
 */
public class CoursDao extends DAO<Cours> {

    public CoursDao() {
    }

    public CoursDao(Connection conn) {
        super();
    }

    public Cours find(int id) {
        Cours cours = new Cours();
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.conn = DriverManager.getConnection(this.urlBdd + "projet_java_edt", "root", "");
                this.rset = this.conn.createStatement(
                        this.rset.TYPE_SCROLL_INSENSITIVE,
                        this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM cours WHERE id = " + id);

                if (rset.first())
                    cours = new Cours(
                            id,
                            rset.getString("nom")
                    );

            } catch (ClassNotFoundException cnfe) {
                System.out.println("Connexion echouee : probleme de classe");
                cnfe.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL");
            e.printStackTrace();
        }

        return cours;
    }

    public void create(Cours cours) {
    }

    public void delete(Cours cours) {
    }

    public void update(Cours cours) {
    }

    public void afficherChampTable(String nomTable) {

    }


}
