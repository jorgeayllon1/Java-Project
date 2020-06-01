package Modele;

import java.sql.Connection;

//CTRL + SHIFT + O pour générer les imports
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

    public static DAO<Utilisateur> getEtudiant() {
        return new EtudiantDao(conn);
    }

    public static DAO<Cours> getCours() {
        return new CoursDao(conn);
    }

    public static DAO<Enseignant> getEnseignant() {
        return new EnseignantDAO(conn);
    }

    public static DAO<Groupe> getGroupe() {
        return new GroupeDAO(conn);
    }

    /*
    public static DAO<Promotion> getPromotion() {
        return new PromotionDAO(conn);
    }
*/
    public static DAO<Site> getSite() {
        return new SiteDAO(conn);
    }

    public static DAO<TypeCours> getTypeCours() {
        return new TypeCoursDAO(conn);
    }

    public static DAO<Seance> getSeance() {
        return new SeanceDao(conn);
    }

    public static DAO<Salle> getSalle() {
        return new SalleDAO(conn);
    }
}
