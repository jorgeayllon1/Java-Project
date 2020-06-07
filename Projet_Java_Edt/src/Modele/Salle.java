package Modele;

/**
 * Salle de classe de l'école
 *
 * @author jorge
 */
public class Salle {

    private int id = 0;
    private String nom = "";
    private int capacite = 0;
    private Site site = null;

    public Salle(int id, String nom, int capacite, Site site) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.site = site;
    }

    public Salle() {
    }

    public int getID() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public int getCapacite() {
        return this.capacite;
    }

    public Site getSite() {
        return this.site;
    }
}

