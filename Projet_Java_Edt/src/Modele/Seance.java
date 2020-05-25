/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Wang David
 */
public class Seance {
    
    private int id=0;
    private int semaine=0;
    private Date date=null;
    private Timestamp heure_debut=null;
    private Timestamp heure_fin=null;
    private int id_cours=0;
    private int id_type=0;
    
    
    
    public Seance(){};
    
    public Seance(int id, int semaine, Date date, Timestamp heure_debut, Timestamp heure_fin, int id_cours, int id_type)
    {
        this.id=id;
        this.semaine=semaine;
        this.date=date;
        this.heure_debut=heure_debut;
        this.heure_fin=heure_fin;
        this.id_cours=id_cours;
        this.id_type=id_type;
    }
    
    public Date getDate()
    {
        return this.date;
    }
    
    public Timestamp getHeureDebut()
    {
        return this.heure_debut;
    }
    
    public Timestamp getHeureFin()
    {
        return this.heure_fin;
    }
    
    public int getID()
    {
        return this.id;
    }
}
