package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class TypeCoursDAO extends DAO<TypeCours> {

    public TypeCoursDAO() {
    }

    public TypeCoursDAO(Connection conn) {
        super(conn);
    }

    public TypeCours find(int id) {
        TypeCours letype = new TypeCours();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM type_cours WHERE id=" + id);

            if (rset.first())
                letype = new TypeCours(id, rset.getString("nom"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return letype;
    }

    public TypeCours create(TypeCours obj) {
        return new TypeCours();

    }

    public void delete(TypeCours obj) {

    }

    public TypeCours update(TypeCours obj) {
        return new TypeCours();

    }

}
