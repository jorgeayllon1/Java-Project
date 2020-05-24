/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.util.ArrayList;
import javafx.beans.Observable;
import Observer.*;

/**
 *
 * @author Wang David
 */
public abstract class DAO<T> implements Observable{
    
    
    protected Connection conn = null;
    protected Statement stmt = null;
    protected ResultSet rset;
    protected ResultSetMetaData rsetMeta;
    protected String urlBdd = "jdbc:mysql://localhost:3306/";
    
    private ArrayList<Observer> liste_observer = new ArrayList<Observer>();
    
    public DAO(Connection conn)
    {
        this.conn = conn;   
        
    }
    
    public DAO(){}
  
    public abstract T find(int id);
    
    public abstract void create(T obj);
    
    public abstract void delete(T obj);
    
    public abstract void update(T obj);
    
    public void afficherChampTable(String nomTable)  //Pas besoin abstract  En revanche, une classe contenant une méthode abstraite doit être déclarée abstraite
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
    
    
}
