package Modele;

/**
 * @author jorge
 */

public class Site {
    private int id = 0;
    private String nom = "";

    public Site(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Site() {
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }
}
