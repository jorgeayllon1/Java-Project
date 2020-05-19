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
    
    private Connexion maconnexion;
    private ResultSet rset;
    private Statement stmt;
    
    public UtilisateurDao(){}
    
    public Utilisateur find(int id)
    {
        Utilisateur user = new Utilisateur();
        
        try
        {
             try
            {
                ArrayList<String> liste;
                this.maconnexion = new Connexion("projet_java_edt","root","");
        
                liste = this.maconnexion.remplirChampsTable("utilisateur");
                for(String liste1 : liste)
                {
                    System.out.println(liste);
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
           
        return user;
        
        
    }
    
    public void afficherChampTable()
    {
        try
        {
             try
            {
                ArrayList<String> liste;
                this.maconnexion = new Connexion("projet_java_edt","root","");
        
                liste = this.maconnexion.remplirChampsTable("utilisateur");
                for(String liste1 : liste)
                {
                    System.out.println(liste);
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
    }
}
