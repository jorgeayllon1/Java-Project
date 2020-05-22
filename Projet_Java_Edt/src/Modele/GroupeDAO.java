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
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.urlBdd + "projet_java_edt", "root", "");
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
    public void create(Groupe obj) {

    }

    @Override
    public void delete(Groupe obj) {

    }

    @Override
    public void update(Groupe obj) {

    }

    @Override
    public void afficherChampTable(String nomTable) {
        System.out.println("Affichage des champs de la table : ");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.urlBdd + "projet_java_edt", "root", "");
            this.stmt = conn.createStatement();
            this.rset = this.stmt.executeQuery("SELECT * FROM " + nomTable);
            this.rsetMeta = rset.getMetaData();

            for (int i = 1; i <= rsetMeta.getColumnCount(); i++)
                System.out.print(rsetMeta.getColumnName(i) + " ");
            System.out.println("\n");
        } catch (ClassNotFoundException e) {
            System.out.println("Connexion echouee : probleme de classe");
            e.printStackTrace();
        } catch (SQLException throwables) {
            System.out.println("Connexion echouee : probleme SQL");
            throwables.printStackTrace();

        }
    }
}
