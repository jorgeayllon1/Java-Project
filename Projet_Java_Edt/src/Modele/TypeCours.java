package Modele;

/**
 * @author jorge
 */

public class TypeCours {
    private int id = 0;
    private String nom = "";

    public TypeCours(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public TypeCours() {
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }
}
