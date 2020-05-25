/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Wang David
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * Connexion a votre BDD locale ou à distance sur le serveur de l'ECE via le tunnel SSH
 * 
 * @author segado
 */
public class Connexion {

    /**
     * Attributs prives : connexion JDBC, statement, ordre requete et resultat
     * requete
     */
    private static Connection conn;

    
    
    public static Connection seConnecter() throws SQLException, ClassNotFoundException{
        
        
        try
        {
             try
            {
                Class.forName("com.mysql.jdbc.Driver");
        //création d'une connexion JDBC à la base 
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet_java_edt","root","");
                
            }
            catch(ClassNotFoundException cnfe)
            {
                    System.out.println("Connexion echouee : probleme de classe");
                    cnfe.printStackTrace();
            }
        }
        catch(SQLException e) 
        {
                System.out.println("Connexion echouee : probleme SQL");
                e.printStackTrace();
        }
      
        return conn;
    }

}
