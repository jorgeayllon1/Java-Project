/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 * @author Wang David
 */
public class Cours {

    private int id = 0;
    private String nom = "";

    public Cours() {
    }

    public Cours(int id, String nom) {
        this.id = id;
        switch (this.id) {
            case 1:
                this.nom = "Java POO";
                break;
            case 2:
                this.nom = "Traitement de Signal";
                break;
            case 3:
                this.nom = "Probabilités et statistiques";
                break;
            case 4:
                this.nom = "Thermodynamique";
                break;
            case 5:
                this.nom = "C++ POO";
                break;
            case 6:
                this.nom = "Systèmes bouclés";
                break;
            case 7:
                this.nom = "Electromagnestisme";
                break;
            case 8:
                this.nom = "Algebre lineaire";
                break;
            case 9:
                this.nom = "C#";
                break;
            case 10:
                this.nom = "Ondes electromagnetiques";
                break;
            default:
                this.nom = nom;
                System.out.println("Cours inconnue");
                break;
        }
    }

    public int getID() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }
}
