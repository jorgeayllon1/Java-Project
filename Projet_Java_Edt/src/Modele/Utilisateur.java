/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Observable;

/**
 *
 * @author Wang David
 */
public class Utilisateur extends Observable {
    
    protected int id=0;
    private String email="";
    private String mdp="";
    protected String nom="";
    protected String prenom="";
    private int droit=0;
    
    public Utilisateur(int id)
    {
        this.id=id;
    }
    
    public Utilisateur(int id,String mdp, String email,String nom, String prenom,int droit)
    {
        this.id=id;
        this.email=email;
        this.mdp=mdp;
        this.nom=nom;
        this.prenom=prenom;
        this.droit=droit;
        
    
    }
    public Utilisateur(String mdp, String email,String nom, String prenom,int droit)
    {
        
        this.email=email;
        this.mdp=mdp;
        this.nom=nom;
        this.prenom=prenom;
        this.droit=droit;
    
    }
    
    public Utilisateur(){}
    
    
    public String getNom()
    {
        return this.nom;
    }
    
    public String getMail()
    {
        return this.email;
    }
    
    public String getPrenom()
    {
        return this.prenom;
    }
    
    public int getID()
    {
        return this.id;
    }
    
    public String getMdp()
    {
        return this.mdp;
    }
    
    public int getDroit()
    {
        return this.droit;
    }
    
}
