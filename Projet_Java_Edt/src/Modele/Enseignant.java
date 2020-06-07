package Modele;

/**
 * Enseignant dans l'école
 *
 * @author jorge
 */
public class Enseignant extends Utilisateur {

    //Pas besoin d'attribut Utilisateur car il hérite d'utilisateur

    /**
     * Il posède le cours qu'il enseigne
     */
    private Cours cours;

    /**
     * Constructeur identique a utisateur
     *
     * @param id
     * @param mdp
     * @param email
     * @param nom
     * @param prenom
     * @param droit
     * @param cours
     * @see Utilisateur#Utilisateur()
     * il a juste un cours en plus
     */
    public Enseignant(int id, String mdp, String email, String nom, String prenom, int droit, Cours cours) {
        super(id, mdp, email, nom, prenom, droit);
        this.cours = cours;
    }

    /**
     * Constructeur par default
     */
    public Enseignant() {
    }

    /**
     * Retourne le cours du prof
     *
     * @return le cours du prof
     */
    public Cours getCours() {
        return this.cours;
    }

}
