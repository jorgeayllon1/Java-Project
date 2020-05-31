package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class SiteDAO extends DAO<Site> {

    public SiteDAO() {
    }

    public SiteDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Site find(int id) {
        Site lesite = new Site();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM site WHERE id=" + id);

            if (rset.first())
                lesite = new Site(id, rset.getString("nom"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL SiteDao");
            sqle.printStackTrace();
        }

        return lesite;
    }

    @Override
    public Site create(Site obj) {
        return new Site();

    }

    @Override
    public void delete(Site obj) {

    }

    @Override
    public Site update(Site obj) {
        return new Site();

    }

    
}
