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
public class Utilisateur {
    
    private int id=0;
    private String nom="";
    private String prenom="";
    
    public Utilisateur(int id, String nom, String prenom)
    {
        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
    
    }
    
    public Utilisateur(){}
    
    public String getNom()
    {
        return this.nom;
    }
    
}
