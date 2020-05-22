package Modele;

/**
 * @author jorge
 */

public class Groupe {
    private int id = 0;
    private String nom = "";
    private int id_promotion = 0;

    public Groupe(int id, String nom, int id_promotion) {
        this.id = id;
        this.nom = nom;
        this.id_promotion = id_promotion;
    }

    public Groupe() {
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public int getId_promotion() {
        return this.id_promotion;
    }

}
