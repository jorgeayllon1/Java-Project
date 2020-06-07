package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection principale à la bdd
 * Cette classe va permettre de se connecter à la bdd
 * c'est ici qu'il faut modifier les parametre si vous changer de bdd
 * ou si vous avez un problème de connection
 */
public class SdzConnection {

    private String url = "jdbc:mysql://localhost:3306/projet_java_edt?autoReconnect=true&useSSL=false";

    private String user = "root";

    private String passwd = "";

    private static Connection connect;

    /**
     * Constructeur par default
     */
    public SdzConnection() {
        try {
            connect = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui va nous retourner notre instance et la créer si elle n'existe pas
     */
    public static Connection getInstance() {
        if (connect == null) {
            new SdzConnection();
        } else {
        }
        return connect;
    }

}
