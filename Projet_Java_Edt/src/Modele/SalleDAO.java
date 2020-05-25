package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class SalleDAO extends DAO<Salle> {

    public SalleDAO() {
    }

    public SalleDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Salle find(int id) {
        Salle lasalle = new Salle();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM salle WHERE id=" + id);

            if (rset.first())
                lasalle = new Salle(id, rset.getString("nom"), rset.getInt("capacite"), rset.getInt("id_site"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return lasalle;
    }

    @Override
    public Salle create(Salle obj) {
        return new Salle();
    }

    @Override
    public void delete(Salle obj) {
    }

    @Override
    public Salle update(Salle obj) {
        return new Salle();
    }

    
}
