package Modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Partie DAO d'une seance
 *
 * @author Wang David
 * @see Modele.DAO
 */
public class SeanceDao extends DAO<Seance> {

    public SeanceDao() {
        super();
    }

    public SeanceDao(Connection conn) {
        super(conn);
    }

    @Override
    public Seance find(int id) {
        Seance seance = new Seance();
        Cours cours = new Cours();
        CoursDao coursDao = new CoursDao();
        int id_cours = 0;

        TypeCours type = new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type = 0;
        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id = " + id);

            if (rset.first()) {
                id_cours = rset.getInt("id_cours");
                cours = coursDao.find(id_cours);
                id_type = rset.getInt("id_type");
                type = typeDao.find(id_type);
                seance = new Seance(
                        id,
                        rset.getInt("semaine"),
                        rset.getDate("date"),
                        rset.getTimestamp("heure_debut"),
                        rset.getTimestamp("heure_fin"),
                        rset.getInt("etat"),
                        cours,
                        type
                );
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return seance;

    }

    @Override
    public boolean create(Seance obj) {

        int id_dispo = this.idmax() + 1; //Trouver id dispo

        obj.setId(id_dispo);

        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "INSERT INTO seance\n" +
                            "VALUES (" + obj.getID() + "," + obj.getSemaine() + ",'" + obj.getDate() + "'," +
                            "'" + obj.getHeureDebut() + "','" + obj.getHeureFin() + "'," +
                            obj.getEtat() + "," + obj.getCours().getID() + "," + obj.getType().getId() + ")"
            );
        } catch (SQLException e) {
            System.err.println("ERROR SQL Update Seance");
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean delete(Seance seance) {
        try {
            this.conn.createStatement(rset.TYPE_SCROLL_INSENSITIVE,
                    rset.CONCUR_UPDATABLE
            ).executeUpdate(
                    "DELETE FROM utilisateur WHERE id = " + seance.getID()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean update(Seance obj) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "UPDATE seance\n" +
                            "SET seance.semaine=" + obj.getSemaine() + ",\n" +
                            "seance.date='" + obj.getDate() + "',\n" +
                            "seance.heure_debut='" + obj.getHeureDebut() + "',\n" +
                            "seance.heure_fin='" + obj.getHeureFin() + "',\n" +
                            "seance.etat=" + obj.getEtat() + ",\n" +
                            "seance.id_cours=" + obj.getCours().getID() + ",\n" +
                            "seance.id_type=" + obj.getType().getId() + "\n" +
                            "WHERE seance.id=" + obj.getID()
            );

        } catch (SQLException e) {
            System.err.println("ERROR SQL Update Seance");
            e.printStackTrace();
        }
        return true;
    }

    public Enseignant trouverEnseignant(Seance seance) {
        Enseignant prof = null;
        EnseignantDAO profDao = new EnseignantDAO();
        int id_prof;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_enseignant FROM seance_enseignants WHERE id_seance = " + seance.getID()); //On cherche tout les ID des séances de ce groupe


            while (rset.next()) {

                id_prof = rset.getInt("id_enseignant");
                prof = profDao.find(id_prof);


            }


        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return prof;
    }

    public Groupe trouverGroupe(Seance seance) {
        Groupe groupe = null;
        GroupeDAO groupeDao = new GroupeDAO();
        int id_groupe;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_groupe FROM seance_groupes WHERE id_seance = " + seance.getID()); //On cherche tout les ID des séances de ce groupe

            while (rset.next()) {
                id_groupe = rset.getInt("id_groupe");
                groupe = groupeDao.find(id_groupe);
            }
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return groupe;
    }

    public Seance chercherSeance(String nom_salle, Timestamp heure_debut, Timestamp heure_fin) {

        Seance seance = new Seance();
        Cours cours;
        CoursDao coursDao = new CoursDao();
        int id_cours;

        TypeCours type;
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_salles\n" +
                            "ON seance.id=seance_salles.id_seance\n" +
                            "INNER JOIN salle\n" +
                            "ON salle.nom='" + nom_salle + "'\n" +
                            "WHERE seance.heure_debut='" + heure_debut + "'\n" +
                            "AND seance.heure_fin='" + heure_fin + "'"
            );

            if (rset.first()) {
                id_cours = rset.getInt("id_cours");
                cours = coursDao.find(id_cours);
                id_type = rset.getInt("id_type");
                type = typeDao.find(id_type);
                seance = new Seance(
                        rset.getInt("id"),
                        rset.getInt("semaine"),
                        rset.getDate("date"),
                        heure_debut,
                        heure_fin,
                        rset.getInt("etat"),
                        cours,
                        type
                );
            } else {
                System.err.println("Error : séance non trouvée");
                return null;
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return seance;

    }

    /**
     * Retourne l'id max dans la bdd
     * cette methode n'est que très peu utiliser
     *
     * @return
     */
    int idmax() {
        int id_max = 0;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT MAX(id) FROM seance"
            );

            while (rset.next()) {
                id_max = rset.getInt("MAX(id)");
            }


        } catch (SQLException e) {

            System.err.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }

        return id_max;
    }

    public void enleverProf(Seance seance) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "DELETE FROM seance_enseignants\n" +
                            "WHERE id_seance=" + seance.getID()
            );

        } catch (SQLException e) {
            System.err.println("ERROR SQL EnleverProf Seance");
            e.printStackTrace();
        }
    }

    public Salle trouverSalle(Seance seance) {
        Salle salle = null;
        SalleDAO salleDAO = new SalleDAO();
        int id_salle;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_salle FROM seance_salles WHERE id_seance = " + seance.getID()); //On cherche tout les ID des séances de ce groupe

            while (rset.next()) {
                id_salle = rset.getInt("id_salle");
                salle = salleDAO.find(id_salle);
            }
        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return salle;
    }

    public void enleverSalle(Seance seance) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "DELETE FROM seance_salles\n" +
                            "WHERE id_seance=" + seance.getID()
            );

        } catch (SQLException e) {
            System.err.println("ERROR SQL EnleverSalle SeanceDAO");
            e.printStackTrace();
        }
    }

    /**
     * Retourne tout les groupes present dans cette seance
     *
     * @param seance
     * @return
     */
    public ArrayList<Groupe> allGroupes(Seance seance) {
        ArrayList<Groupe> mes_groupes = new ArrayList();
        ArrayList<Integer> id_des_groupes = new ArrayList<>();
        GroupeDAO groupeDAO = new GroupeDAO();

        try {

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT id_groupe FROM seance_groupes\n" +
                            "WHERE seance_groupes.id_seance=" + seance.getID()
            );

            while (rset.next()) {
                id_des_groupes.add(rset.getInt("id_groupe"));
            }
        } catch (SQLException e) {
            System.out.println("Probleme SQL : probleme SQL SeanceDao");
            e.printStackTrace();
        }

        for (Integer id : id_des_groupes) {
            mes_groupes.add(groupeDAO.find(id));
        }

        return mes_groupes;
    }

    public void enleverGroupe(Seance seance, int id_groupe) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "DELETE FROM seance_groupes\n" +
                            "WHERE id_seance=" + seance.getID() + "\n" +
                            "AND id_groupe=" + id_groupe
            );

        } catch (SQLException e) {
            System.err.println("ERROR SQL EnleverGroupe SeanceDAO");
            e.printStackTrace();
        }
    }

    /**
     * Verifie si la seance existe dans la bdd celon son id
     *
     * @param id
     * @return
     */
    public boolean siExiste(int id) {
        Seance seance = new Seance();
        SeanceDao seanceDao = new SeanceDao();
        int id_seance = 0;
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM seance WHERE id=" + id);


            while (rset.next()) {

                id_seance = rset.getInt("id");
                seance = seanceDao.find(id_seance);
                existe = true;
            }

        } catch (SQLException e) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            e.printStackTrace();
        }
        return existe;
    }

    public void ajouterSalle(Seance seance, int id_salle) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "INSERT INTO seance_salles\n" +
                            "VALUES ( " + seance.getID() + "," + id_salle + ")"
            );
        } catch (SQLException e) {
            System.err.println("ERROR SQL ajouterSalle Seance");
            e.printStackTrace();
        }
    }

    /**
     * Gere le change d'etat d'une séance
     *
     * @param seance
     */
    public void majEtat(Seance seance) {
        if (this.trouverSalle(seance) != null && this.trouverEnseignant(seance) != null && this.allGroupes(seance).size() != 0) {
            if (seance.getEtat() == 0)
                seance.setEtat(1);
        }
    }

    public void ajouterProf(Seance seance, int id_prof) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "INSERT INTO seance_enseignants\n" +
                            "VALUES ( " + seance.getID() + "," + id_prof + ")"
            );
        } catch (SQLException e) {
            System.err.println("ERROR SQL ajouterProf SeanceDAO");
            e.printStackTrace();
        }
    }

    /**
     * Retourne le nombre d'élève dans une séance
     *
     * @param seance
     * @return
     */
    public int nombreEleve(Seance seance) {
        int nombredeleve = 0;
        GroupeDAO groupeDAO = new GroupeDAO();

        for (Groupe ungroupe : this.allGroupes(seance)) {
            nombredeleve += groupeDAO.nombreEleve(ungroupe.getId());
        }

        return nombredeleve;
    }

    public void ajouterGroupe(Seance seance, int id_groupe) {
        try {
            this.conn
                    .createStatement(
                            rset.TYPE_SCROLL_INSENSITIVE,
                            rset.CONCUR_UPDATABLE
                    ).executeUpdate(
                    "INSERT INTO seance_groupes\n" +
                            "VALUES ( " + seance.getID() + "," + id_groupe + ")"
            );
        } catch (SQLException e) {
            System.err.println("ERROR SQL SeanceDAO");
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui va retourner oui ou non si il y a une salle affectée à cette séance
     *
     * @param seance
     * @return
     */
    public boolean siSalle(Seance seance) {
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance_salles WHERE id_seance =" + seance.getID());


            if (rset.next()) {
                existe = true;
            }


        } catch (SQLException ex) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            ex.printStackTrace();
        }
        return existe;
    }

    /**
     * Méthode qui va retourner oui ou non si il y a un prof affectée à cette séance
     *
     * @param seance
     * @return
     */
    public boolean siProf(Seance seance) {
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance_enseignants WHERE id_seance =" + seance.getID());


            if (rset.next()) {
                existe = true;
            }


        } catch (SQLException ex) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            ex.printStackTrace();
        }
        return existe;
    }

    /**
     * Méthode qui va retourner oui ou non si il y a un groupe affectée à cette séance
     *
     * @param seance
     * @return
     */
    public boolean siGroupe(Seance seance) {
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance_groupes WHERE id_seance =" + seance.getID());


            if (rset.next()) {
                existe = true;
            }


        } catch (SQLException ex) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            ex.printStackTrace();
        }
        return existe;
    }
}
