package Modele;

/**
 * @author jorge
 */

public class Enseignant {
    private int id_utilisateur = 0;
    private int id_cours = 0;

    public Enseignant(int id_utilisateur, int id_cours) {
        this.id_utilisateur = id_utilisateur;
        this.id_cours = id_cours;
    }

    public Enseignant() {
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public int getId_cours() {
        return id_cours;
    }
}
