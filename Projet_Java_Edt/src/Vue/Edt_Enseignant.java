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
import javax.swing.border.MatteBorder;

/**
 * @author Wang David
 */
public class Edt_Enseignant extends Edt {

    Utilisateur user;
    Enseignant prof;
    JPanel grille_edt = new JPanel();
    GridBagConstraints grille = new GridBagConstraints();
    Groupe groupe = null;
    GroupeDAO groupeDao = null;
    EnseignantDAO profDao = null;

    public Edt_Enseignant() {
    }

    public Edt_Enseignant(Utilisateur user, Enseignant prof) {
        super(user);

        this.summary.addActionListener(this::actionPerformed);

        System.out.println("Bienvenue " + prof.getNom());
        this.prof = prof;
        afficherEdtProfAccueil();

        this.mes_cours.addActionListener(this);
        this.rechercher.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

        if (e.getSource() == this.mes_cours) {
            afficherEdtProfAccueil();
        }

        ///Si on clique sur recap
        if (e.getSource() == this.summary) {
            control_recherche = new RechercheControleur();
            control_recherche.voirrecap(this.prof, new Date(2020, 6, 2), new Date(2020, 6, 11));
        }


    }

    public void afficherEdtProfAccueil() {
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

        grille_edt = new JPanel(new GridBagLayout());

        grille = new GridBagConstraints();

        grille.fill = GridBagConstraints.HORIZONTAL;
        grille.gridx = 0;
        grille.gridy = 0;
        grille.weightx = 0.1;
        grille.weighty = 0.1;

        grille_edt.add(new JLabel("Heures "), grille);
        grille_edt.setBorder(new MatteBorder(0, 0, 1, 1, Color.black));

        grille.gridx = 1;
        grille.gridy = 0;
        grille.weightx = 0.15;
        grille.weighty = 0.15;
        grille_edt.add(new JLabel("Lundi "), grille);

        grille.gridx = 2;
        grille.gridy = 0;
        grille.weightx = 0.1;
        grille.weighty = 0.15;
        grille_edt.add(new JLabel("Mardi "), grille);

        grille.gridx = 3;
        grille.gridy = 0;
        grille.weightx = 0.1;
        grille.weighty = 0.15;
        grille_edt.add(new JLabel("Mercredi "), grille);

        grille.gridx = 4;
        grille.gridy = 0;
        grille.weightx = 0.1;
        grille.weighty = 0.15;
        grille_edt.add(new JLabel("jeudi "), grille);

        grille.gridx = 5;
        grille.gridy = 0;
        grille.weightx = 0.1;
        grille.weighty = 0.15;
        grille_edt.add(new JLabel("Vendredi "), grille);

        grille.gridx = 6;
        grille.gridy = 0;
        grille.weightx = 0.1;
        grille.weighty = 0.15;
        grille_edt.add(new JLabel("Samedi "), grille);

        int j = 8;
        int k = j + 2;
        for (int i = 0; i < 6; i++) {

            grille.gridx = 0;
            grille.gridy = i + 1;
            grille.weightx = 0.1;
            grille.weighty = 0.15;
            grille_edt.add(new JLabel(j + "H-" + k + "H"), grille);
            j += 2;
            k += 2;
        }

        this.panel.add(grille_edt);
        this.setVisible(true);
        this.rechercher.addActionListener(this);
    }

}
