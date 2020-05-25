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
    private int id_groupe=0; 
    //private Groupe groupe;
    
    public Etudiant()
    {
        
    }
    
    public Etudiant(int id, String num_etudiant, int id_groupe)
    {
        this.id=id;
        this.num_etudiant=num_etudiant;
        this.id_groupe=id_groupe;
    }
    
    public String getNumEtudiant()
    {
        return this.num_etudiant;
    }
    
    public int getIdGroupe()
    {
        return this.id_groupe;
    }
    
}
