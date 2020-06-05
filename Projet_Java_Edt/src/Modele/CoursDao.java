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

    public Cours create(Cours cours) {
        return new Cours();
    }

    public void delete(Cours cours) {
    }

    public Cours update(Cours cours) {
        return new Cours();
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


}
