package Modele;

/**
 * @author jorge
 */

public class Enseignant extends Utilisateur{
    //private int id_utilisateur = 0;
    //private int id_cours = 0;
    //Pas besoin d'attribut Utilisateur car il hérite d'utilisateur
    
    private Cours cours;

    public Enseignant(int id, String mdp, String email,String nom, String prenom,int droit, Cours cours) {
        super(id,mdp,email,nom,prenom,droit);
        this.cours=cours;
    }

    public Enseignant() {
    }

    /*
    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public int getId_cours() {
        return id_cours;
    }*/
    
    public Cours getCours()
    {
        return this.cours;
    }
    
}
