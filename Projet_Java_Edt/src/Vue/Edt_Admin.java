/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.*;

import javax.swing.*;

/**
 *
 * @author Wang David
 */
public class Edt_Admin extends Edt{

    public Edt_Admin(){}
    
    public Edt_Admin(Utilisateur user, int droit)
    {
        super(user);
        if(droit==1)
            System.out.println("Admin !");
        else if(droit==2)
            System.out.println("Referent !");


                        
    }
    
}
