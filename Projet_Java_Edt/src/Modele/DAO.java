/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;

/**
 *
 * @author Wang David
 */
public abstract class DAO<T> {
    
    
    protected Connection conn = null;
    protected Statement stmt = null;
    protected ResultSet rset;
    protected ResultSetMetaData rsetMeta;
    protected String urlBdd = "jdbc:mysql://localhost:3306/";
    
    public DAO(Connection conn)
    {
        this.conn = conn;   
        
    }
    
    public DAO(){}
  
    public abstract T find(int id);
    
    public abstract void create(T obj);
    
    public abstract void delete(T obj);
    
    public abstract void update(T obj);
    
    public abstract void afficherChampTable(String nomTable);
    
    
}
