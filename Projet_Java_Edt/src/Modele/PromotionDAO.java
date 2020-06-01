package Modele;

import java.sql.*;
import java.util.ArrayList;

public class PromotionDAO extends DAO<Promotion> {

    public PromotionDAO() {
        super();
    }

    /*public PromotionDAO(Connection conn) {
        super(conn);
    }*/

    @Override
    public Promotion find(int id) {
        Promotion lapromo = new Promotion();
        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM promotion WHERE id=" + id);

            if (rset.first())
                lapromo = new Promotion(id, rset.getInt("annee"));
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL PromotionDao");
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

    /**
     * Retourne le id correspondant à l'année de la promotion
     *
     * @param anne
     * @return Id de la promotion
     */
    public int idCelonAnne(int anne) {
        int le_id = 0;

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT id FROM promotion\n" +
                            "WHERE annee=" + anne
            );
            while (rset.next()) {
                le_id = rset.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Probème SQL");
            e.printStackTrace();
        }

        if (le_id != 0) return le_id;
        else {
            System.err.println("Nom de groupe inconnu");
            return 0;
        }

    }

    public ArrayList<Seance> lesSeances(int id_promotion, int numero_semaine) {
        ArrayList<Seance> lesseances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_groupes\n" +
                            "ON seance.id = seance_groupes.id_seance\n" +
                            "INNER JOIN groupe\n" +
                            "ON groupe.id = seance_groupes.id_groupe\n" +
                            "INNER JOIN promotion\n" +
                            "ON promotion.id = groupe.id_promotion\n" +
                            "WHERE promotion.id=" + id_promotion + "\n" +
                            "AND seance.semaine=" + numero_semaine
            );

            while (rset.next()) {

                int id = rset.getInt("id");
                int semaine = rset.getInt("semaine");
                Date date = rset.getDate("date");
                Timestamp heure_debut = rset.getTimestamp("heure_debut");
                Timestamp heure_fin = rset.getTimestamp("heure_fin");
                int id_cours = rset.getInt("id_cours");
                Cours cours = coursDAO.find(id_cours);
                int id_type = rset.getInt("id_type");
                TypeCours typeCours = typeCoursDAO.find(id_type);

                lesseances.add(new Seance(id, semaine, date, heure_debut, heure_fin, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL PromotionDao");
            e.printStackTrace();
        }
        return lesseances;
    }
}
