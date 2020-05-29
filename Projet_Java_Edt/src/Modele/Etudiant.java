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
public class Etudiant extends Utilisateur{
    
    private String num_etudiant="";
    private Groupe groupe;
    
    public Etudiant()
    {
        
    }
    
    public Etudiant(int id,String mdp, String email,String nom, String prenom, int droit , String num_etudiant , Groupe groupe)
    {
        super(id,mdp,email,nom,prenom,droit);
        this.num_etudiant=num_etudiant;
        this.groupe=groupe;
    }
    
    public String getNom()
    {
        return this.nom;
    }
    
    public String getPrenom()
    {
        return this.prenom;
    }
    
    public String getNumEtudiant()
    {
        return this.num_etudiant;
    }
    
    public Groupe getGroupe()
    {
        return this.groupe;
    }
    
}
