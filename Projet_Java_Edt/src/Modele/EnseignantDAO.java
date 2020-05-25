package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class EnseignantDAO extends DAO<Enseignant> {

    public EnseignantDAO() {
    }

    public EnseignantDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Enseignant find(int id_utilisateur) {
        Enseignant leprof = new Enseignant();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM enseignant WHERE id_utilisateur=" + id_utilisateur);

            if (rset.first())
                leprof = new Enseignant(id_utilisateur, rset.getInt("id_utilisateur"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return leprof;
    }

    @Override
    public Enseignant create(Enseignant obj) {
        return new Enseignant();

    }

    @Override
    public void delete(Enseignant obj) {

    }

    @Override
    public  Enseignant update(Enseignant obj) {
        return new Enseignant();

    }

    
}
