package Modele;

import java.sql.Connection;

/**
 * Classe qui genere les dao du projet
 * Cette classe n'est pas obligatoire dans le projet car peut utiliser
 *
 * @author jorge
 * @deprecated utiliser le contructeur normal de chque dao
 */
public class DAOFactory {
    protected static final Connection conn = SdzConnection.getInstance();

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Utilisateur> getUtilisateur() {
        return new UtilisateurDao(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Etudiant> getEtudiant() {
        return new EtudiantDao(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Cours> getCours() {
        return new CoursDao(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Enseignant> getEnseignant() {
        return new EnseignantDAO(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Groupe> getGroupe() {
        return new GroupeDAO(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Site> getSite() {
        return new SiteDAO(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<TypeCours> getTypeCours() {
        return new TypeCoursDAO(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Seance> getSeance() {
        return new SeanceDao(conn);
    }

    /**
     * Retourne un objet Classe interagissant avec la BDD
     *
     * @return DAO
     */
    public static DAO<Salle> getSalle() {
        return new SalleDAO(conn);
    }
}
