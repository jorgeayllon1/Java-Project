package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//CTRL + SHIFT + O pour générer les imports
public class SdzConnection {
    //URL de connexion
    private String url = "jdbc:mysql://localhost:3306/projet_java_edt?autoReconnect=true&useSSL=false";
    //Nom du user
    private String user = "root";
    //Mot de passe de l'utilisateur
    private String passwd = "";
    //Objet Connection
    private static Connection connect;

    //Constructeur privé
    private SdzConnection() {
        try {
            connect = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Méthode qui va nous retourner notre instance et la créer si elle n'existe pas

    public static Connection getInstance() {
        if (connect == null) {
            new SdzConnection();
            System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
        } else {
            System.out.println("CONNEXION SQL EXISTANTE ! ");
        }
        return connect;
    }

}
