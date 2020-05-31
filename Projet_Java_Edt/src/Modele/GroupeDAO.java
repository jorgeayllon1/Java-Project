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
        
        int id_promo=0;
        Promotion promo = new Promotion();
        PromotionDAO promoDao = new PromotionDAO();
        try {
            this.conn=Connexion.seConnecter();
            this.rset = this.conn.createStatement(this.rset.TYPE_SCROLL_INSENSITIVE, this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM groupe WHERE id=" + id);

            if (rset.first())
            {
                id_promo=rset.getInt("id_promotion");
                promo = promoDao.find(id_promo);
                legroupe = new Groupe(id, rset.getString("nom"), promo);
            }
                
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
        Cours cours=new Cours();
        CoursDao coursDao=new CoursDao();
        int id_cours=0;
        
        TypeCours type=new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type=0;
        
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
                        id_cours=rset.getInt("id_cours");
                        cours = coursDao.find(id_cours);
                        id_type=rset.getInt("id_type");
                        type = typeDao.find(id_type);
                       seance_groupe.add(new Seance(array.get(i), rset.getInt("semaine"),
                  rset.getDate("date"),                 
                  rset.getTimestamp("heure_debut"),
                  rset.getTimestamp("heure_fin"),
                  cours,
                  type));
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
    
    /**Méthode renvoyant toutes les séances d'un etudiant en fonction d'une semaine prenant en param 
     * une liste d'integer representant les id_seances relatives à cet etudiant
     *
     * @param array
     * @param semaine 
     */
    
    public ArrayList<Seance> allSeancesAvecSemaine(ArrayList<Integer> array,int semaine)
    {
        ArrayList<Seance> seance_groupe = new ArrayList();
        Cours cours=new Cours();
        CoursDao coursDao=new CoursDao();
        int id_cours=0;
        
        TypeCours type=new TypeCours();
        TypeCoursDAO typeDao = new TypeCoursDAO();
        int id_type=0;
        
        try
        {
             try
            {      
                this.conn=Connexion.seConnecter();  
                for(int i=0;i<array.size();i++)
                {
                    this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,                      
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT * FROM seance WHERE semaine="+semaine);//On cherche toutes les séances avec le même id_seance
                    
                    if(rset.next())
                    {
                        id_cours=rset.getInt("id_cours");
                        cours = coursDao.find(id_cours);
                        id_type=rset.getInt("id_type");
                        type = typeDao.find(id_type);
                       seance_groupe.add(new Seance(array.get(i), rset.getInt("semaine"),
                  rset.getDate("date"),                 
                  rset.getTimestamp("heure_debut"),
                  rset.getTimestamp("heure_fin"),
                  cours,
                  type));
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
