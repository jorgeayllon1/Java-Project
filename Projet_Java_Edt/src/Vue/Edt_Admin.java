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


    public Edt_Admin(Utilisateur user) {
        super(user);

        this.rechercher.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JTextField nom = new JTextField();
        nom.setPreferredSize(new Dimension(100, 200));

        JTextField semaine = new JTextField();
        semaine.setPreferredSize(new Dimension(100, 200));

        schear.add(nom);
        schear.add(semaine);

        JButton chercher_utilisateur = new JButton(new AbstractAction("Chercher Utilisateur") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                control_recherche = new RechercheControleur();
                control_recherche.rechercher_utilisateur(nom.getText(), semaine.getText(), 1);
            }
        });

        JButton chercher_groupe = new JButton(new AbstractAction("Chercher Groupe") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                control_recherche = new RechercheControleur();
                control_recherche.rechercher_groupe(nom.getText(), semaine.getText());
            }
        });


        schear.add(chercher_utilisateur);
        schear.add(chercher_groupe);
        panel.add(schear);
        this.setVisible(true);
    }


}
