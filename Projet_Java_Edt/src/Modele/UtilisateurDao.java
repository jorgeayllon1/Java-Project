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
                this.conn=Connexion.seConnecter();  
                this.rset = this.conn.createStatement(
                this.rset.TYPE_SCROLL_INSENSITIVE,                      
                this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM utilisateur WHERE id = " + id);
                
                if(rset.first())
                user = new Utilisateur(
                  id,
                  rset.getString("passwd"),
                  rset.getString("email"),                 
                  rset.getString("nom"),
                  rset.getString("prenom"),
                  rset.getInt("droit")
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
    

    public Utilisateur create(Utilisateur user){
    return new Utilisateur();}
    
    public void delete(Utilisateur user)
    {
        try{
            this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                	ResultSet.CONCUR_UPDATABLE
                 ).executeUpdate(
                	"DELETE FROM utilisateur WHERE id = " + user.getID()
                 );
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public Utilisateur update(Utilisateur user){
    return new Utilisateur();}
    
}
