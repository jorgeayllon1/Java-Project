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
}
