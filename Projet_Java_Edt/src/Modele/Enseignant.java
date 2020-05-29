package Modele;

/**
 * @author jorge
 */

public class Enseignant extends Utilisateur{

    //Pas besoin d'attribut Utilisateur car il hérite d'utilisateur
    
    private Cours cours;

    public Enseignant(int id, String mdp, String email,String nom, String prenom,int droit, Cours cours) {
        super(id,mdp,email,nom,prenom,droit);
        this.cours=cours;
    }

    public Enseignant() {
    }
    
    public Cours getCours()
    {
        return this.cours;
    }
    
}
