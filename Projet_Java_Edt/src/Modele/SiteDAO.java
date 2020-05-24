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
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.urlBdd + "projet_java_edt", "root", "");
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM site WHERE id=" + id);

            if (rset.first())
                lesite = new Site(id, rset.getString("nom"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return lesite;
    }

    @Override
    public void create(Site obj) {

    }

    @Override
    public void delete(Site obj) {

    }

    @Override
    public void update(Site obj) {

    }

    
}
