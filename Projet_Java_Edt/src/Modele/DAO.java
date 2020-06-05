/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.*;
import java.util.*;

import javafx.beans.Observable;


/**
 * @author Wang David
 */
public abstract class DAO<T> {

    ///Attributs
    protected Connection conn = null;
    protected Statement stmt = null;
    protected ResultSet rset;
    protected ResultSetMetaData rsetMeta;
    protected String urlBdd = "jdbc:mysql://localhost:3306/";
    protected int taille = -1;/// valeur impossible mise expres

    /**
     * Constructeur avec connexion
     */

    public DAO(Connection conn) {
        this.conn = conn;
    }

    public DAO() {
        this.conn = SdzConnection.getInstance();
    }

    public abstract T find(int id);

    public abstract boolean create(T obj);

    public abstract boolean delete(T obj);

    public abstract boolean update(T obj);

    /**
     * Méthode qui va trouver l'id le plus haut disponible
     */
    public int trouverIdDispo() {
        int max = 0;
        try {
            this.rset = this.conn.createStatement(
                    this.rset.TYPE_SCROLL_INSENSITIVE,
                    this.rset.CONCUR_READ_ONLY).executeQuery("SELECT max(id) FROM utilisateur");

            if (rset.first()) {
                max = rset.getInt("max(id)") + 1; //On prend le prochain id
            }
            //System.out.println(max);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max; //On retourne le max
    }

    /**
     * Méthode pour afficher les champs d'une table en prenant en param le nom de la table
     *
     * @param nomTable
     */

    public void afficherChampTable(String nomTable)  //Pas besoin abstract  En revanche, une classe contenant une méthode abstraite doit être déclarée abstraite
    {
        System.out.println("Affichage des champs de la table : ");
        try {
            this.stmt = conn.createStatement();
            this.rset = this.stmt.executeQuery("SELECT * FROM " + nomTable);
            this.rsetMeta = rset.getMetaData();

            for (int i = 1; i <= rsetMeta.getColumnCount(); i++)
                System.out.print(rsetMeta.getColumnName(i) + " ");
            System.out.println("\n");

        } catch (SQLException e) {
            System.err.println("Connexion echouee : probleme SQL");
            e.printStackTrace();
        }
    }

    /**
     * @param nomTable
     * @return La taille de la table
     */

    public int getTaille(String nomTable) {

        try {
            this.stmt = this.conn.createStatement();
            this.rset = this.stmt.executeQuery("select * from " + nomTable);
            this.rsetMeta = rset.getMetaData();

            rset.last();

            taille = rset.getRow();

            return taille;

        } catch (SQLException e) {
            System.err.println("Erreur lors du retour de getTaille");
            e.printStackTrace();
            return -1;
        }
    }

}

