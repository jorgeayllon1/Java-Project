/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author Wang David
 */
public abstract class DAO<T> {
    
  
    public abstract T find(int id);
    
    public abstract void create(T obj);
    
    public abstract void delete(T obj);
    
    
}
