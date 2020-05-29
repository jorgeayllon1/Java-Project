package Modele;

/**
 * @author jorge
 */

public class Groupe {
    private int id = 0;
    private String nom = "";
    //private int id_promotion = 0;
    private Promotion promo=null;

    public Groupe(int id, String nom, Promotion promo) {
        this.id = id;
        this.nom = nom;
        this.promo=promo;
    }

    public Groupe() {
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public Promotion getPromo()
    {
        return this.promo;
    }

}
