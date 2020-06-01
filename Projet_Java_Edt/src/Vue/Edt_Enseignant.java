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
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Wang David
 */
public class Edt_Enseignant extends Edt {

    Enseignant enseignant;

    public Edt_Enseignant() {
    }

    public Edt_Enseignant(Utilisateur user, Enseignant prof) {
        super(user);

        this.enseignant = prof;

        this.summary.addActionListener(this::actionPerformed);

        System.out.println("Bienvenue " + prof.getNom());


        ///Affichage des séances relatives à cet enseignant
        EnseignantDAO profDao = new EnseignantDAO();
        ArrayList<Integer> mes_id = new ArrayList();
        mes_id = profDao.trouverIdSeance(prof);

        ArrayList<Seance> mes_seances = new ArrayList();
        mes_seances = profDao.trouverAllSeances(mes_id);

        System.out.println("Nombre de séances prevues pour cet enseignant : " + mes_seances.size());
        for (int i = 0; i < mes_seances.size(); i++) {
            System.out.println("Mes seances:"
                    + "\n date : " + mes_seances.get(i).getDate()
                    + "\nheure debut : " + mes_seances.get(i).getHeureDebut()
                    + "\nheure fin : " + mes_seances.get(i).getHeureFin()
                    + " \nType : " + mes_seances.get(i).getType().getNom());
            Salle salle = profDao.trouverSalle(mes_seances.get(i));
            System.out.println("Salle : " + salle.getNom() + " Capacite : " + salle.getCapacite() + " Site : " + salle.getSite().getNom());
        }

        System.out.println("Voici la liste de tous les enseignants : ");
        ArrayList<Enseignant> mes_profs = new ArrayList();
        mes_profs = profDao.listeEnseignant();
        for (int i = 0; i < mes_profs.size(); i++) {
            System.out.println(mes_profs.get(i).getNom());
        }

        this.rechercher.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ///Si on clique sur rechercher
        if (e.getSource() == this.rechercher) {

            JPanel schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

            JTextField nom = new JTextField();
            nom.setPreferredSize(new Dimension(100, 200));

            JTextField semaine = new JTextField();
            semaine.setPreferredSize(new Dimension(100, 200));

            schear.add(nom);
            schear.add(semaine);

            JButton lancerrecherche = new JButton(new AbstractAction("Rechercher") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    control_recherche = new RechercheControleur();
                    control_recherche.rechercher_utilisateur(nom.getText(), semaine.getText(), 3);
                }
            });


            schear.add(lancerrecherche);
            panel.add(schear);
            this.setVisible(true);
        }

        ///Si on clique sur recap
        if (e.getSource() == this.summary) {
            control_recherche = new RechercheControleur();
            control_recherche.voirrecap(this.enseignant, new Date(2020, 6, 2), new Date(2020, 6, 11));
        }

    }
}
