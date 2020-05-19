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
                ArrayList<ArrayList<String>> liste;
                this.maconnexion = new Connexion("projet_java_edt","root","");
        
                liste = this.maconnexion.remplirChampsRequete(" SELECT* FROM utilisateur");
                for(int i=0 ; i<liste.size() ; i++)
                {
                    for(int j=0; j<4;j++)
                    {
                        user = new Utilisateur(id, liste.get(i).get(j), liste.get(i).get(j), liste.get(i).get(j));
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
           
        return user;
        
        
    }
    
    public void afficherChampTable()
    {
        System.out.println("Affichage des champs de la table : ");
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
    
    public void afficherDonnees()
    {
        System.out.println("Affichage des données de la table selon requete : ");
        try
        {
             try
            {
                ArrayList<ArrayList<String>> liste;
                this.maconnexion = new Connexion("projet_java_edt","root","");
        
                liste = this.maconnexion.remplirChampsRequete(" SELECT nom FROM utilisateur");
                
                    System.out.println(liste);
                    
                    //System.out.println(liste.size());
                
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
    
}
