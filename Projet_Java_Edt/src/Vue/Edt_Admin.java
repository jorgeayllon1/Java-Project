/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controlleur.Recherche.RechercheControleur;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Wang David
 */
public class Edt_Admin extends Edt {

    public Edt_Admin() {
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        /// Vasy je fait taffer du swing

        System.out.println("je suis la");

        JPanel schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JTextField texte = new JTextField();
        texte.setPreferredSize(new Dimension(200, 100));

        schear.add(texte);

        JButton lancerrecherche = new JButton(new AbstractAction("Rechercher") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                control_recherche = new RechercheControleur();
                control_recherche.rechercher_controleur(texte.getText());
            }
        });


        schear.add(lancerrecherche);
        panel.add(schear);
        this.setVisible(true);
    }


    public Edt_Admin(Utilisateur user, int droit) {
        super(user);
        if (droit == 1)
            System.out.println("Admin !");
        else if (droit == 2)
            System.out.println("Referent !");

        this.rechercher.addActionListener(this);
    }


}
