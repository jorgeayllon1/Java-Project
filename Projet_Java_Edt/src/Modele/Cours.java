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
        this.nom = nom;
    }

    public int getID() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }
}
