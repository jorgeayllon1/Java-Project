package Modele;

import java.sql.*;
import java.util.*;

/**
 * @author jorge
 */

public class GroupeDAO extends DAO<Groupe> {

    public GroupeDAO() {
    }

    public GroupeDAO(Connection conn) {
        super(conn);
    }

    @Override
    public Groupe find(int id) {
        Groupe legroupe = new Groupe();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM groupe WHERE id=" + id);

            if (rset.first())
                legroupe = new Groupe(id, rset.getString("nom"), rset.getInt("id_promotion"));
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Connexion echouée : problème de classe");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.out.println("Connexion echouee : probleme SQL");
            sqle.printStackTrace();
        }

        return legroupe;
    }

    @Override
    public Groupe create(Groupe obj) {
        return  new Groupe();

    }

    @Override
    public void delete(Groupe obj) {

    }

    @Override
    public Groupe update(Groupe obj) {
        return  new Groupe();

    }
    
    

    public ArrayList<Integer> trouverIdSeance(Groupe groupe)
    {
        
        ArrayList<Integer> mes_id_seances = new ArrayList();
        try
        {
             try
            {      
                
                this.conn=Connexion.seConnecter();  
                
                this.rset = this.conn.createStatement(
                this.rset.TYPE_SCROLL_INSENSITIVE,                      
                this.rset.CONCUR_READ_ONLY).executeQuery("SELECT id_seance FROM seance_groupes WHERE id_groupe = " + groupe.getId()); //On cherche tout les ID des séances de ce groupe
                
                
                while(rset.next())
                {
                    mes_id_seances.add(rset.getInt("id_seance"));
                }
                

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
        return mes_id_seances;
    }
    
    public ArrayList<Seance> trouverAllSeances(ArrayList<Integer> array)
    {
        ArrayList<Seance> seance_groupe = new ArrayList();
        
        try
        {
             try
            {      
                this.conn=Connexion.seConnecter();  
                for(int i=0;i<array.size();i++)
                {
                    this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,                      
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE id="+array.get(i));//On cherche toutes les séances avec le même id_seance

                    if(rset.next())
                    {
                       seance_groupe.add(new Seance(array.get(i), rset.getInt("semaine"),
                  rset.getDate("date"),                 
                  rset.getTimestamp("heure_debut"),
                  rset.getTimestamp("heure_fin"),
                  rset.getInt("id_cours"),
                  rset.getInt("id_type")));
                    }   
                }
                
                

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
        return seance_groupe;
    }
}
