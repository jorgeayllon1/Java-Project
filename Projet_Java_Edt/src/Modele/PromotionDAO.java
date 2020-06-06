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
    public boolean create(Promotion obj) {
        return false;

    }

    @Override
    public boolean delete(Promotion obj) {

        return false;
    }

    @Override
    public boolean update(Promotion obj) {
        return false;

    }

    /**
     * Retourne le id correspondant à l'année de la promotion
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

    /**
     * Retourne la liste de seances d'une promo pour une semaine choisi
     */
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
                int etat = rset.getInt("etat");
                int id_cours = rset.getInt("id_cours");
                Cours cours = coursDAO.find(id_cours);
                int id_type = rset.getInt("id_type");
                TypeCours typeCours = typeCoursDAO.find(id_type);

                lesseances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL PromotionDao");
            e.printStackTrace();
        }
        return lesseances;
    }
    
    /**Retourne la liste de tous les groupes issus de cette promo
     * 
     * @param promo
     * @return 
     */
    public ArrayList<Groupe> allGroupes(Promotion promo)
    {
        ArrayList<Groupe> mes_groupes = new ArrayList();
        Promotion promotion = new Promotion();
        PromotionDAO promoDao = new PromotionDAO();

        try {
            //this.conn = Connexion.seConnecter();

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM groupe WHERE id_promotion="+promo.getId());

            while (rset.next()) {

                    promotion = promoDao.find(promo.getId());
                    mes_groupes.add(new Groupe(rset.getInt("id"), rset.getString("nom"),promotion));
                
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EnseignantDao");
            e.printStackTrace();
        }
        return mes_groupes;
    }
    
    public Promotion trouverPromoAvecAnnee(int annee)
    {
        Promotion promo = new Promotion();
        PromotionDAO promoDao = new PromotionDAO();
        int id_promo=0;
        
        
        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM promotion WHERE annee ="+annee); 


            while (rset.next())
            {

                id_promo = rset.getInt("id");
                promo = promoDao.find(id_promo);


            }


        } catch (SQLException e) {
            
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return promo;
    }
}
