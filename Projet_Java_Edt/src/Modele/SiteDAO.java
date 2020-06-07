package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class SiteDAO extends DAO<Site> {

    public SiteDAO() {
        super();
    }

    public SiteDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Site find(int id) {
        Site lesite = new Site();
        try {
            //this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM site WHERE id=" + id);

            if (rset.first())
                lesite = new Site(id, rset.getString("nom"));
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL SiteDao");
            sqle.printStackTrace();
        }

        return lesite;
    }

    @Override
    public boolean create(Site obj) {
        return false;

    }

    @Override
    public boolean delete(Site obj) {
        return false;
    }

    @Override
    public boolean update(Site obj) {
        return false;
    }

    public int capaciteTot(int id_site) {
        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT SUM(capacite) FROM salle\n" +
                            "WHERE salle.id_site=" + id_site
            );
            while (rset.next()) {
                return rset.getInt("SUM(capacite)");
            }
        } catch (SQLException e) {
            System.err.println("Connexion echouee : probleme SQL CoursDAO");
            e.printStackTrace();
        }
        return 0;
    }

}
