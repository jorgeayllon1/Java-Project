package Modele;

import com.sun.source.tree.BreakTree;

/**
 * @author jorge
 */

public class Promotion {
    private int id = 0;
    private int annee = 0;

    public Promotion(int id, int annee) {
        this.id = id;
        this.annee = annee;
    }

    public Promotion() {
    }

    public int getId() {
        return this.id;
    }

    public int getAnnee() {
        return this.annee;
    }
}
