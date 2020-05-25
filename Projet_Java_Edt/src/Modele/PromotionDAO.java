package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PromotionDAO extends DAO<Promotion> {

    public PromotionDAO() {
    }

    public PromotionDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Promotion find(int id) {
        Promotion lapromo = new Promotion();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM promotion WHERE id=" + id);

            if (rset.first())
                lapromo = new Promotion(id, rset.getInt("annee"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return lapromo;
    }

    @Override
    public Promotion create(Promotion obj) {
        return new Promotion();

    }

    @Override
    public void delete(Promotion obj) {

    }

    @Override
    public Promotion update(Promotion obj) {
        return new Promotion();

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
