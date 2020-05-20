/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author Wang David
 */
public class UtilisateurDao extends DAO<Utilisateur> {
    
    public UtilisateurDao(){};
    
    public UtilisateurDao(Connection conn) 
    {
        super(conn);
    }

    public Utilisateur find(int id)
    {
        Utilisateur user = new Utilisateur();      
        try
        {
             try
            {            
                Class.forName("com.mysql.jdbc.Driver");
                this.conn = DriverManager.getConnection(this.urlBdd+"projet_java_edt", "root", "");       
                this.rset = this.conn.createStatement(
                this.rset.TYPE_SCROLL_INSENSITIVE,                      
                this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM utilisateur WHERE id = " + id);
                
                if(rset.first())
                user = new Utilisateur(
                  id,
                  rset.getString("passwd"),
                  rset.getString("email"),                 
                  rset.getString("nom"),
                  rset.getString("prenom")
                );       

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
           
        return user;
  
    }
    
    public void afficherChampTable(String nomTable)
    {
        System.out.println("Affichage des champs de la table : ");
        try
        {
             try
            {
                Class.forName("com.mysql.jdbc.Driver");             
                this.conn = DriverManager.getConnection(this.urlBdd+"projet_java_edt","root", "");             
                this.stmt = conn.createStatement();
                this.rset = this.stmt.executeQuery("SELECT * FROM "+ nomTable);
                this.rsetMeta = rset.getMetaData();
        
                for(int i = 1; i <= rsetMeta.getColumnCount(); i++)
                System.out.print( rsetMeta.getColumnName(i) + " ");
                System.out.println("\n");
                
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
    }

    public void create(Utilisateur user){}
    
    public void delete(Utilisateur user){}
    
    public void update(Utilisateur user){}
    
}
