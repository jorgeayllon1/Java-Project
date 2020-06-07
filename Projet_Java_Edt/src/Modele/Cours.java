package Modele;

/**
 * Entité cours, c'est une matière qu'un élève peut avoir
 *
 * @author Wang David
 */
public class Cours {

    /**
     * L'id du cours correspondant dans la bdd
     */
    private int id = 0;

    /**
     * Le nom du cours
     */
    private String nom = "";

    /**
     * Constructeur par default du cours
     */
    public Cours() {
    }

    /**
     * Constructeur normal d'un cours
     *
     * @param id  le id correspondant dans la bdd
     * @param nom le nom du cours
     */
    public Cours(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    /**
     * Retourne le id du cours
     *
     * @return Le id du cours
     */
    public int getID() {
        return this.id;
    }

    /**
     * Retourne le nom du cours
     *
     * @return le nom du cours
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * maj du nom
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * maj de l'id
     *
     * @param id le nouvel id
     */
    public void setId(int id) {
        this.id = id;
    }
}
