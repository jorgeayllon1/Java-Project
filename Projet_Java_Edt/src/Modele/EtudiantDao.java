/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Partie DAO d'un etudiant
 *
 * @author Wang David
 * @see DAO pour plus d'info
 */
public class EtudiantDao extends DAO<Etudiant> {

    public EtudiantDao() {
        super();
    }

    public EtudiantDao(Connection conn) {
        super(conn);
    }

    @Override
    public Etudiant find(int id) {
        Etudiant etudiant = new Etudiant();
        int id_user = 0;
        int id_groupe = 0;
        Utilisateur user = new Utilisateur();
        UtilisateurDao userDao = new UtilisateurDao();
        Groupe groupe = new Groupe();
        GroupeDAO groupeDao = new GroupeDAO();
        try {
            //this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM etudiant WHERE id_utilisateur = " + id);

            if (rset.first()) {
                id_user = rset.getInt("id_utilisateur");
                id_groupe = rset.getInt("id_groupe");
                user = userDao.find(id_user);
                groupe = groupeDao.find(id_groupe);
                etudiant = new Etudiant(
                        id, user.getMdp(), user.getMail(), user.getNom(), user.getPrenom(), user.getDroit(), rset.getString("numero"), groupe


                );
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EtudiantDao");
            e.printStackTrace();
        }

        return etudiant;

    }

    @Override
    public boolean create(Etudiant obj) {
        return false;
    }

    @Override
    public boolean delete(Etudiant obj) {
        return false;
    }

    @Override
    public boolean update(Etudiant obj) {
        return false;
    }

    /**
     * Retourne la salle en fonction de la seance
     */
    public Salle trouverSalle(Seance seance) {
        int id_salle = 0;
        int id_site = 0;
        Salle salle = new Salle();
        Site site = new Site();
        SiteDAO siteDao = new SiteDAO();
        try {
            //this.conn=Connexion.seConnecter();

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_salle FROM seance_salles WHERE id_seance=" + seance.getID());//On cherche toutes les séances avec le même id_seance

            while (rset.next()) {
                id_salle = rset.getInt("id_salle"); //On trouve l'id de la salle
            }

            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM salle WHERE id=" + id_salle);//On cherche toutes les séances avec le même id_seance

            while (rset.next()) {
                id_site = rset.getInt("id_site"); //On get l'id du site
                site = siteDao.find(id_site); //On créee un objet site en cherchant dans la bdd en fonction de cet id
                salle = new Salle(id_salle, rset.getString("nom"), rset.getInt("capacite"), site); //On créee un objet salle que l'on va retourner
            }


        } catch (SQLException e) {
            System.out.println("Connexion echouee : probleme SQL EtudiantDao");
            e.printStackTrace();
        }

        return salle;
    }

    /**
     * Retourne toutes les séance d'un élève sur une periode
     *
     * @param id_eleve
     * @param debut
     * @param fin
     * @return
     */
    public ArrayList<Seance> trouverSeancesSurPeriode(int id_eleve, Date debut, Date fin) {

        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            //this.conn = Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT * FROM seance\n" +
                            "INNER JOIN seance_groupes\n" +
                            "ON seance_groupes.id_seance=seance.id\n" +
                            "INNER JOIN groupe\n" +
                            "ON groupe.id = seance_groupes.id_groupe\n" +
                            "INNER JOIN etudiant\n" +
                            "ON etudiant.id_groupe=groupe.id\n" +
                            "WHERE etudiant.id_utilisateur=" + id_eleve + "\n" +
                            "AND seance.date BETWEEN '" + debut + "' AND '" + fin + "'"
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

                les_seances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL GroupeDAO");
            e.printStackTrace();
        }

        return les_seances;
    }

    /**
     * Méthode pour trouver un eleve en fonction de son nom
     *
     * @param nom
     */
    public Etudiant trouverEleveAvecNom(String nom) {
        Etudiant e = new Etudiant();
        EtudiantDao eDao = new EtudiantDao();
        int id_user = 0;

        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM utilisateur WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id_user = rset.getInt("id");
                e = eDao.find(id_user);


            }


        } catch (SQLException ex) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            ex.printStackTrace();
        }
        return e;
    }

    /**
     * Méthode qui renvoie vrai si le nom existe dans la bdd false sinon
     *
     * @param nom
     */
    public boolean siExiste(String nom) {
        Etudiant e = new Etudiant();
        EtudiantDao eDao = new EtudiantDao();
        int id_user = 0;
        boolean existe = false;


        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id FROM utilisateur WHERE nom ='" + nom + "'");


            while (rset.next()) {

                id_user = rset.getInt("id");
                e = eDao.find(id_user);
                existe = true;


            }


        } catch (SQLException ex) {

            System.out.println("Connexion echouee : probleme SQL SeanceDAO");
            ex.printStackTrace();
        }
        return existe;
    }

    /**
     * Renvoie toutes les seances de l'élève
     *
     * @param id_eleve
     * @return
     */
    public ArrayList<Seance> allSeance(int id_eleve) {
        ArrayList<Seance> les_seances = new ArrayList<>();
        DAO<Cours> coursDAO = DAOFactory.getCours();
        DAO<TypeCours> typeCoursDAO = DAOFactory.getTypeCours();

        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT seance.id,semaine,date,heure_debut,heure_fin,etat,id_cours,id_type FROM seance\n" +
                            "INNER JOIN seance_groupes\n" +
                            "ON seance_groupes.id_seance=seance.id\n" +
                            "INNER JOIN groupe\n" +
                            "ON seance_groupes.id_groupe=groupe.id\n" +
                            "INNER JOIN etudiant\n" +
                            "ON etudiant.id_groupe=groupe.id\n" +
                            "WHERE etudiant.id_utilisateur=" + id_eleve
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

                les_seances.add(new Seance(id, semaine, date, heure_debut, heure_fin, etat, cours, typeCours));

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL EtudiantDAO");
            e.printStackTrace();
        }

        return les_seances;
    }

    /**
     * Retourne tout les cours d'un élève
     *
     * @param id_eleve
     * @return
     */
    public ArrayList<Cours> trouverAllCours(int id_eleve) {
        ArrayList<Cours> les_cours = new ArrayList<>();
        try {
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery(
                    "SELECT DISTINCT cours.id,cours.nom FROM cours\n" +
                            "INNER JOIN seance\n" +
                            "ON seance.id_cours=cours.id\n" +
                            "INNER JOIN seance_groupes\n" +
                            "ON seance_groupes.id_seance=seance.id\n" +
                            "INNER JOIN groupe\n" +
                            "ON groupe.id=seance_groupes.id_groupe\n" +
                            "INNER JOIN etudiant\n" +
                            "ON etudiant.id_groupe=groupe.id\n" +
                            "WHERE etudiant.id_utilisateur=" + id_eleve
            );
            while (rset.next()) {
                Cours cours = new Cours();
                cours.setId(rset.getInt("id"));
                cours.setNom(rset.getString("nom"));
                les_cours.add(cours);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL GroupeDAO");
            e.printStackTrace();
        }
        return les_cours;
    }

}
