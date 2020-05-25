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

    public int trouverIdDispo()
    {
        int max=0;
        try 
        {
            this.rset = this.conn.createStatement(
            this.rset.TYPE_SCROLL_INSENSITIVE,                      
            this.rset.CONCUR_READ_ONLY).executeQuery("SELECT max(id) FROM utilisateur");
            
            if(rset.first())
            {
                max=rset.getInt("max(id)")+1;
            }
            System.out.println(max);
        }
	     catch (SQLException e) {
	            e.printStackTrace();
	}
        return max;
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
    
    
    public Utilisateur create(Utilisateur user)
    {
        int id=this.trouverIdDispo(); //Trouver id dispo
        
        try {		
			if(rset.first()){
				
    			PreparedStatement prepare = this.conn
                                                    .prepareStatement(
                                                    	"INSERT INTO utilisateur (id, email,passwd,nom,prenom,droit) VALUES(?,?,?,?,?,?)"
                                                    );
				prepare.setInt(1, id);
				prepare.setString(2, user.getMail());
                                prepare.setString(3,user.getMdp());
                                prepare.setString(4, user.getNom());
                                prepare.setString(5,user.getPrenom());
                                prepare.setInt(6, user.getDroit());
				
				prepare.executeUpdate();
				user = this.find(13);	
				
			}
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
        
            return user;
    }
    
    //Supprimer un élément dans la table
    public void delete(Utilisateur user)
    {
        try{
            this.conn.createStatement(rset.TYPE_SCROLL_INSENSITIVE, 
                	rset.CONCUR_UPDATABLE
                 ).executeUpdate(
                	"DELETE FROM utilisateur WHERE id = " + user.getID()
                 );
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    //Changer mail
    public Utilisateur update(Utilisateur user)
    {
       try {
	
                this .conn	
                     .createStatement(
                    	rset.TYPE_SCROLL_INSENSITIVE, 
                        rset.CONCUR_UPDATABLE
                     ).executeUpdate(
                    	"UPDATE utilisateur SET email = '" + user.getMail() + "'"+
                    	" WHERE id = " + user.getID()
                     );
			
			user = this.find(user.getID());
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
        
      return user;
    }
    
}
