/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controlleur.Recherche.RechercheControleur;
import Modele.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.*;
import javax.swing.border.MatteBorder;

/**
 * @author Wang David
 */
public class Edt_Etudiant extends Edt {

    Utilisateur user;
    Etudiant etudiant;
    JPanel grille_edt = new JPanel();
    GridBagConstraints grille = new GridBagConstraints();
    Groupe groupe = null;
    GroupeDAO groupeDao = null;
    EtudiantDao etudiantDao = null;

    public Edt_Etudiant() {
    }

    public Edt_Etudiant(Utilisateur user, Etudiant etudiant) {

        super(user);
        this.etudiant = etudiant;
        afficherEdtAccueil();
        this.mes_cours.addActionListener(this);
        this.rechercher.addActionListener(this);
        this.summary.addActionListener(this);


        etudiantDao = new EtudiantDao(); //********************
        etudiant = (Etudiant) etudiantDao.find(etudiant.getID());//*****************
        groupeDao = new GroupeDAO();
        groupe = groupeDao.find(etudiant.getGroupe().getId());
        ///Si on clique sur l'un des boutons de la grille de semaine
        for (int nb_week = 0; nb_week < this.week_button.size(); nb_week++) {
            this.week_button.get(nb_week).addActionListener(this);

        }

    }


    /**
     * Méthode qui va afficher l'edt  de l'étudiant en fonction de la semaine actuelle
     */
    public void afficherEdtAccueil() {
        //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
        EtudiantDao etudiantDao = new EtudiantDao(); //********************
        etudiant = (Etudiant) etudiantDao.find(etudiant.getID());//*****************
        System.out.print("Numero etudiant :" + etudiant.getNumEtudiant());

        //Récupération données groupe
        groupeDao = new GroupeDAO();
        groupe = groupeDao.find(etudiant.getGroupe().getId());
        System.out.println(" Groupe :" + groupe.getNom() + " Promotion :" + groupe.getPromo().getAnnee());

        ///Affichage des séances relatives à cet eleve

        ArrayList<Integer> mes_id = new ArrayList();
        mes_id = groupeDao.trouverIdSeance(groupe);


        ArrayList<Seance> mes_seances = new ArrayList();
        mes_seances = groupeDao.trouverAllSeances(mes_id);

        System.out.println("bug  bug bug");
        SeanceDao seanceDao = new SeanceDao();
        Enseignant prof = new Enseignant();


        System.out.println("Nombre de séances prevues pour cet eleve : " + mes_seances.size());
        Salle salle = new Salle();
        for (int i = 0; i < mes_seances.size(); i++) {
            System.out.println("Mes seances: \n"
                    + "Intitule du cours : " + mes_seances.get(i).getCours().getNom()
                    + "\n date : " + mes_seances.get(i).getDate()
                    + "\nheure debut : " + mes_seances.get(i).getHeureDebut()
                    + "\nheure fin : " + mes_seances.get(i).getHeureFin()
                    + "\nType :" + mes_seances.get(i).getType().getNom());
            salle = etudiantDao.trouverSalle(mes_seances.get(i));
            System.out.println("Salle : " + salle.getNom() + " Capacite : " + salle.getCapacite() + " Site : " + salle.getSite().getNom());

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
                salle = etudiantDao.trouverSalle(mes_seances.get(i));
                Date date = mes_seances.get(i).getDate();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
                for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                    if (dayOfWeek == jour_semaine) //Si c un vendredi
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
                                prof = seanceDao.trouverEnseignant(mes_seances.get(i));

                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
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
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.mes_cours) {
            afficherEdtAccueil();
        }
        //Si on clique sur rechercher
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
                    control_recherche.rechercher_utilisateur(nom.getText(), semaine.getText(), 4);
                }
            });

            schear.add(lancerrecherche);
            panel.add(schear);
            this.setVisible(true);
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


                String string_semaine = this.week_button.get(s).getText(); //On get le string du numero de semaine

                int int_semaine = Integer.valueOf(string_semaine); //Cast en int

                ///Affichage des séances relatives à cet eleve
                ArrayList<Integer> mes_id = new ArrayList();
                mes_id = groupeDao.trouverIdSeance(groupe);


                ArrayList<Seance> mes_seances = new ArrayList();
                mes_seances = groupeDao.trouverAllSeancesSemaine(groupe.getId(), int_semaine); //On recup toutes les  séances relatives à cet etudiant dans cette semaine
                for (int i = 0; i < mes_seances.size(); i++) //On parcourt les séances
                {

                    Salle salle = new Salle();
                    salle = etudiantDao.trouverSalle(mes_seances.get(i));
                    Date date = mes_seances.get(i).getDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
                    for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                        if (dayOfWeek == jour_semaine) //Si c un vendredi
                        {


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
                                            "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Salle :" +
                                                    salle.getNom() + "<br>Site :" +
                                                    salle.getSite().getNom() + "</p></html>";

                                    grille_edt.add(new JLabel(myString), grille);
                                }
                            }

                        }
                    }

                }


                panel.add(grille_edt);
                this.setVisible(true);

            }

        }

        ///Si on clique sur recap
        if (e.getSource() == this.summary) {
        }


    }

}
