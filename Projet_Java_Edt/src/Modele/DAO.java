package Modele;

import java.sql.*;
import java.util.*;

import javafx.beans.Observable;


/**
 * Classe abstraite qui va definir le comportement principale de toutes les autre classe liée au dao
 *
 * @author Wang David
 */
public abstract class DAO<T> {

    ///Attributs
    /**
     * Connection utilisé pour se connecter à la bdd
     */
    protected Connection conn;
    /**
     * Phrase que l'on va utiliser pour le sql
     */
    protected Statement stmt = null;
    /**
     * Resultat du sql
     */
    protected ResultSet rset;
    /**
     * Données spéciales du resultat du sql
     */
    protected ResultSetMetaData rsetMeta;
    /**
     * Cet attribut n'est pas obligatoire dans le projet
     * Il a juste aider pour detecter le bug en debut de projet
     * Taille qui vaut le nombre de fois l'entité dans la bdd
     */
    protected int taille = -1;/// valeur impossible mise expres

    /**
     * Constructeur avec connexion
     */
    public DAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Constructeur avec connexion V2
     */
    public DAO() {
        this.conn = SdzConnection.getInstance();
    }

    /**
     * Trouve le id de l'objet dans la bdd
     *
     * @param id id a chercher
     * @return l'objet souhaiter
     */
    public abstract T find(int id);

    /**
     * Crée l'objet dans la bdd
     *
     * @param obj objet a crée dans la bdd
     * @return vraie si tout c'est bien passer
     */
    public abstract boolean create(T obj);

    /**
     * Efface l'objet dans la bdd
     *
     * @param obj objet a effacer
     * @return vraie si tout c'est bien passer
     */
    public abstract boolean delete(T obj);

    /**
     * Met à jours l'objet dans la bdd
     *
     * @param obj objet a mettre a jours
     * @return vraie si tout c'est bien passer
     */
    public abstract boolean update(T obj);


    /**
     * Méthode pour afficher les champs d'une table en prenant en param le nom de la table
     *
     * @param nomTable nom de la table dans la bdd
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
     * Cette methode n'est pas obligatoire dans le projet
     * Elle a juste aider pour detecter le bug en debut de projet
     * Retroune la valeur d'une nombre d'entité dans la bdd
     *
     * @param nomTable le nom de la table dans la bdd
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

