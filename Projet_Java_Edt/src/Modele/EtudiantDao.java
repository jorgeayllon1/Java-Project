/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.sql.SQLException;

/**
 *
 * @author Wang David
 */
public class EtudiantDao extends UtilisateurDao{
    
    public EtudiantDao()
    {
        super();
    }
    
    public EtudiantDao(Connection conn)
    {
        super(conn);
    }
    
    public Utilisateur find(int id)
    {
        Etudiant etudiant = new Etudiant();   
        try
        {
             try
            {            
                this.conn=Connexion.seConnecter();      
                this.rset = this.conn.createStatement(
                this.rset.TYPE_SCROLL_INSENSITIVE,                      
                this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM etudiant WHERE id_utilisateur = " + id);
                
                if(rset.first())
                etudiant = new Etudiant(
                  id,
                  rset.getString("numero"),
                  rset.getInt("id_groupe")             
              
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
           
        return etudiant;
  
    }
    
}
