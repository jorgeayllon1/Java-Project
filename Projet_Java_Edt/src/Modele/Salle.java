package Modele;

/**
 * @author jorge
 */

public class Salle {

    private int id = 0;
    private String nom = "";
    private int capacite = 0;
    private int id_site = 0;

    public Salle(int id, String nom, int capacite, int id_site) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.id_site = id_site;
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

    public int getId_site() {
        return this.id_site;
    }
}

