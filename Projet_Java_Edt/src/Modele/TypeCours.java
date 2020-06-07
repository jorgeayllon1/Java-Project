package Modele;

/**
 * Type de cours d'un cours
 * Un cours peut être un td, un cours majistral, de soutien...
 *
 * @author jorge
 */
public class TypeCours {
    private int id = 0;
    private String nom = "";

    public TypeCours(int id, String nom) {
        this.id = id;
        this.nom = nom;
        /*
        switch (this.id) {
            case 1:
                this.nom = "Cours interactif";
                break;
            case 2:
                this.nom = "Cours magistral";
                break;
            case 3:
                this.nom = "TD";
                break;
            case 4:
                this.nom = "TP";
                break;
            case 5:
                this.nom = "Projet";
                break;
            case 6:
                this.nom = "Soutien";
                break;
            default:
                this.nom = "";
                System.out.println("Type de cours inconnue");
                break;
        }
         */
    }

    public TypeCours() {
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }
}
