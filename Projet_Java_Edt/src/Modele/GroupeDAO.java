package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class GroupeDAO extends DAO<Groupe> {

    public GroupeDAO() {
    }

    public GroupeDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Groupe find(int id) {
        Groupe legroupe = new Groupe();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM groupe WHERE id=" + id);

            if (rset.first())
                legroupe = new Groupe(id, rset.getString("nom"), rset.getInt("id_promotion"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return legroupe;
    }

    @Override
    public Groupe create(Groupe obj) {
        return  new Groupe();

    }

    @Override
    public void delete(Groupe obj) {

    }

    @Override
    public Groupe update(Groupe obj) {
        return  new Groupe();

    }

    
}
