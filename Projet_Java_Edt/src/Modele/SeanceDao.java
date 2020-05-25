/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Wang David
 */
public class SeanceDao extends DAO<Seance> {
    
    public Seance find(int id)
    {
        Seance seance = new Seance();      
        try
        {
             try
            {            
                Class.forName("com.mysql.jdbc.Driver");
                this.conn = DriverManager.getConnection(this.urlBdd+"projet_java_edt", "root", "");       
                this.rset = this.conn.createStatement(
                this.rset.TYPE_SCROLL_INSENSITIVE,                      
                this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id = " + id);
                
                if(rset.first())
                seance = new Seance(
                  id,
                  rset.getInt("semaine"),
                  rset.getDate("date"),                 
                  rset.getTimestamp("heure_debut"),
                  rset.getTimestamp("heure_fin"),
                  rset.getInt("id_cours"),
                  rset.getInt("id_type")
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
           
        return seance;
  
    }
    
    @Override
    public Seance create(Seance obj) {
        return new Seance();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Seance obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Seance update(Seance obj) {
        return new Seance();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
