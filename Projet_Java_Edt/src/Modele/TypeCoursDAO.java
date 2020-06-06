package Modele;

import java.sql.*;

/**
 * @author jorge
 */

public class TypeCoursDAO extends DAO<TypeCours> {

    public TypeCoursDAO() {
        super();
    }

    public TypeCoursDAO(Connection conn) {
        super(conn);
    }

    public TypeCours find(int id) {
        TypeCours letype = new TypeCours();
        try {
            //this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM type_cours WHERE id=" + id);

            if (rset.first())
                letype = new TypeCours(id, rset.getString("nom"));
        } catch (SQLException sqle) {
            System.err.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return letype;
    }

    public boolean create(TypeCours obj) {
        return false;

    }

    public boolean delete(TypeCours obj) {
        return false;
    }

    /**
     * On ne peut pas changer le id du Type de cours avec cette methode
     */
    public boolean update(TypeCours obj) {
        try {

            this.stmt.executeUpdate(
                    "UPDATE cours\n" +
                            "SET cours.nom='" + obj.getNom() + "'\n" +
                            "WHERE cours.id=" + obj.getId()
            );

        } catch (SQLException sqle) {
            System.err.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean nomCoherent(String nom) {
        for (int i = 1; i < this.taille; i++) {
            if (nom.equals(this.find(i).getNom())) {
                return true;
            }
        }
        System.out.println("Nom de type incohérent");
        return false;
    }

    public int id_celon_nom(String nom) {

        int id = -1;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM type_cours WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id = rset.getInt("id");
            }


        } catch (SQLException e) {

            System.err.println("Connexion echouee : probleme SQL TypeCoursDAO");
            e.printStackTrace();
        }
        if (id == -1) {
            System.err.println("ERREUR : nom de type non trouvé");
        }
        return id;
    }

}
