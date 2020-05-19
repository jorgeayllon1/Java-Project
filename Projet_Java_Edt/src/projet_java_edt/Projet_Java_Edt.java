/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_java_edt;
import Modele.*;


/**
 *
 * @author Wang David
 */
public class Projet_Java_Edt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        UtilisateurDao user = new UtilisateurDao();
        user.afficherChampTable();
        user.afficherDonnees();
        
       /* DAO<Utilisateur> userDao = new UtilisateurDao();
        
        for(int i=0; i<3;i++)
        {
            Utilisateur user = userDao.find(i);
            System.out.println(user.getID() + user.getNom() + user.getPrenom());
        }*/
        
        
    }
    
}
