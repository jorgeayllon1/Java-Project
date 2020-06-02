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
import java.util.Calendar;
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
        ///Si on clique sur l'un des boutons de la grille de semaine
        for (int nb_week = 0; nb_week < this.week_button.size(); nb_week++) {
            this.week_button.get(nb_week).addActionListener(this);

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
        Salle salle = new Salle();
        for (int i = 0; i < mes_seances.size(); i++) {
            System.out.println("Mes seances:"
                    + "\n date : " + mes_seances.get(i).getDate()
                    + "\nheure debut : " + mes_seances.get(i).getHeureDebut()
                    + "\nheure fin : " + mes_seances.get(i).getHeureFin()
                    + " \nType : " + mes_seances.get(i).getType().getNom());
            salle = profDao.trouverSalle(mes_seances.get(i));
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
        
        for (int i = 0; i < mes_seances.size(); i++) //On parcourt toutes séances relatives à cet etudiant
        {
            if (mes_seances.get(i).getSemaine() == this.num_semaine)//Si il y a un cours dans la semaine actuelle
            {
                salle = profDao.trouverSalle(mes_seances.get(i));
                java.util.Date date = mes_seances.get(i).getDate();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
                for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                    if (dayOfWeek == jour_semaine) 
                    {
                            /*SimpleDateFormat sdf = new SimpleDateFormat("h");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String str = sdf.format(mes_seances.get(i).getHeureDebut()); //On stocke dans un string l'entier de l'heure de début*/
                        String str = mes_seances.get(i).getHeureDebut().toString();
                        char str2 = str.charAt(11);
                        char str3 = str.charAt(12);
                        StringBuilder str4 = new StringBuilder();
                        if (str2 == '0') {
                            str4.append(str3);
                        } else {
                            str4.append(str2).append(str3);
                        }

                        int n = 0;
                        String heure = "";
                        for (int m = 0; m < 7; m++) {
                            if (m == 0) {
                                heure = Integer.toString(m + 8 + n);
                            } else {
                                if ((m + n) % 2 == 0) {
                                    n += 2;
                                    heure = Integer.toString(m + 8 + n);
                                } else if ((m + n) % 2 != 0) {
                                    n++;
                                    heure = Integer.toString(m + 8 + n);
                                }
                            }

                            grille.weightx = 0.1;
                            grille.weighty = 0.15;
                            grille.gridx = jour_semaine - 1;
                            if (str4.toString().equals(heure)) //Si ca commence à 10h
                            {
                                if (heure.contains("8"))
                                    grille.gridy = 1;
                                if (heure.contains("10"))
                                    grille.gridy = 2;
                                if (heure.contains("12"))
                                    grille.gridy = 3;
                                if (heure.contains("14"))
                                    grille.gridy = 4;
                                if (heure.contains("16"))
                                    grille.gridy = 5;
                                if (heure.contains("18"))
                                    grille.gridy = 6;
                                if (heure.contains("20"))
                                    grille.gridy = 7;
                                

                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() 
                                                 + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "</p></html>";

                                grille_edt.add(new JLabel(myString), grille);
                            }
                        }

                    }
                }

            }


        }

        this.panel.add(grille_edt);
        this.setVisible(true);
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
        
        
        ///Si onclique sur un des boutons de la grille de semaine
        for (int s = 1; s < this.week_button.size(); s++) {
            //Si c'est cliqué
            if (e.getActionCommand().equals(this.week_button.get(s).getText())) {
                System.out.println(this.week_button.get(s).getText()); //On affiche le texte du bouton cliqué

                grille_edt = new JPanel(new GridBagLayout());//Initialisations

                ///Ajout des jours de la semaine
                grille = new GridBagConstraints();
                grille.fill = GridBagConstraints.HORIZONTAL;
                grille.gridx = 0;
                grille.gridy = 0;
                grille.weightx = 0.1;
                grille.weighty = 0.1;

                grille_edt.add(new JLabel("Heures "), grille);

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
                for (int a = 0; a < 6; a++) {

                    grille.gridx = 0;
                    grille.gridy = a + 1;
                    grille.weightx = 0.1;
                    grille.weighty = 0.15;
                    grille_edt.add(new JLabel(j + "H-" + k + "H"), grille);  //Ajout des horaires
                    j += 2;
                    k += 2;
                }



                panel.add(grille_edt);
                this.setVisible(true);

            }

        }


    }

    

}
