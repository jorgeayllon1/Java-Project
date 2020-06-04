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
import java.util.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

/**
 * @author Wang David
 */
public class Edt_Etudiant extends Edt {

    Utilisateur user = null;
    Etudiant etudiant = null;

    Groupe groupe = null;
    GroupeDAO groupeDao = null;
    EtudiantDao etudiantDao = null;
    ArrayList<Seance> mes_seances = new ArrayList();
    ArrayList<Integer> mes_id = new ArrayList();
    SeanceDao seanceDao = new SeanceDao();
    Salle salle = null;
    Enseignant prof = null;
    JPanel schear = null;

    JPanel panel_recherche = new JPanel();


    public Edt_Etudiant() {
    }

    public Edt_Etudiant(Utilisateur user, Etudiant etudiant) {

        super(user);
        this.etudiant = etudiant;
        afficherEdtEtudiantAccueil();
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

        panel.add(panel_recherche);
        panel_edt.add(tableau);
        panel.add(panel_edt);

    }


    /**
     * Méthode qui va afficher l'edt  de l'étudiant en fonction de la semaine actuelle
     */
    public void afficherEdtEtudiantAccueil() {

        this.afficherGrille();
        this.afficherEdtEtudiant(4);


    }


    public void afficherEdtEtudiant(int droit) {
        panel_edt.removeAll();
        panel_edt.validate();
        panel_edt.repaint();
        ArrayList<JLabel> mes_labels = new ArrayList();
        if (droit == 4) {
            //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
            etudiantDao = new EtudiantDao(); //********************
            etudiant = (Etudiant) etudiantDao.find(etudiant.getID());//*****************
            System.out.print("Numero etudiant :" + etudiant.getNumEtudiant());

            //Récupération données groupe
            groupeDao = new GroupeDAO();
            groupe = groupeDao.find(etudiant.getGroupe().getId());
            System.out.println(" Groupe :" + groupe.getNom() + " Promotion :" + groupe.getPromo().getAnnee());

            ///Affichage des séances relatives à cet eleve

            mes_id = new ArrayList();
            mes_id = groupeDao.trouverIdSeance(groupe);


            mes_seances = new ArrayList();
            mes_seances = groupeDao.trouverAllSeances(mes_id);

            seanceDao = new SeanceDao();
            prof = new Enseignant();

        }


        salle = new Salle();

        for (int i = 0; i < mes_seances.size(); i++) //On parcourt toutes séances relatives à cet etudiant
        {
            if (mes_seances.get(i).getSemaine() == this.num_semaine)//Si il y a un cours dans la semaine actuelle
            {

                salle = etudiantDao.trouverSalle(mes_seances.get(i)); //On get la salle de ce cours
                Date date = mes_seances.get(i).getDate(); //On get sa date
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
                for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) //On parcourt chaque jour de la semaine 2=lundi
                {
                    if (dayOfWeek == jour_semaine) //Si le jour où a lieu le cours est le meme 
                    {
                        String str = mes_seances.get(i).getHeureDebut().toString();  //Heure debut
                        char str2 = str.charAt(11);
                        char str3 = str.charAt(12);
                        StringBuilder str4 = new StringBuilder();
                        if (str2 == '0') {  //cas 08
                            str4.append(str3);
                        } else {  //cas 10-20
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

                            int colonne_semaine = jour_semaine - 1;
                            int ligne_semaine = 0;
                            if (str4.toString().equals(heure)) //Si ca commence à 10h
                            {
                                if (heure.contains("8"))
                                    ligne_semaine = 1;
                                if (heure.contains("10"))
                                    ligne_semaine = 2;
                                if (heure.contains("12"))
                                    ligne_semaine = 3;
                                if (heure.contains("14"))
                                    ligne_semaine = 4;
                                if (heure.contains("16"))
                                    ligne_semaine = 5;
                                if (heure.contains("18"))
                                    ligne_semaine = 6;
                                if (heure.contains("20"))
                                    ligne_semaine = 7;

                                prof = seanceDao.trouverEnseignant(mes_seances.get(i));
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "</p></html>";

                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                            }
                        }

                    }
                }

            }


        }
        this.panel_recherche.setVisible(false);
        this.panel_edt.add(tableau);

        this.panel_edt.revalidate();
        this.panel_edt.repaint();
        this.panel_edt.setVisible(true);
        this.setVisible(true);
    }


    public void afficherEdtSemaineEtudiant(int droit, int semaine) {
        panel_edt.removeAll();
        panel_edt.validate();
        panel_edt.repaint();
        content = new JPanel(new BorderLayout());
        if (droit == 4) {
            etudiantDao = new EtudiantDao(); //********************
            etudiant = (Etudiant) etudiantDao.find(etudiant.getID());//*****************
            System.out.print("Numero etudiant :" + etudiant.getNumEtudiant());

            //Récupération données groupe
            groupeDao = new GroupeDAO();
            groupe = groupeDao.find(etudiant.getGroupe().getId());
            System.out.println(" Groupe :" + groupe.getNom() + " Promotion :" + groupe.getPromo().getAnnee());

            ///Affichage des séances relatives à cet eleve

            mes_id = new ArrayList();
            mes_id = groupeDao.trouverIdSeance(groupe);


            mes_seances = new ArrayList();
            mes_seances = groupeDao.trouverAllSeancesSemaine(groupe.getId(), semaine); //On recup toutes les  séances relatives à cet etudiant dans cette semaine

            seanceDao = new SeanceDao();
            prof = new Enseignant();

            salle = new Salle();

            for (int i = 0; i < mes_seances.size(); i++) //On parcourt toutes séances relatives à cet etudiant
            {

                salle = etudiantDao.trouverSalle(mes_seances.get(i));
                Date date = mes_seances.get(i).getDate();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
                for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                    if (dayOfWeek == jour_semaine) {
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

                            int colonne_semaine = jour_semaine - 1;
                            int ligne_semaine = 0;
                            if (str4.toString().equals(heure)) //Si ca commence à 10h
                            {
                                if (heure.contains("8"))
                                    ligne_semaine = 1;
                                if (heure.contains("10"))
                                    ligne_semaine = 2;
                                if (heure.contains("12"))
                                    ligne_semaine = 3;
                                if (heure.contains("14"))
                                    ligne_semaine = 4;
                                if (heure.contains("16"))
                                    ligne_semaine = 5;
                                if (heure.contains("18"))
                                    ligne_semaine = 6;
                                if (heure.contains("20"))
                                    ligne_semaine = 7;

                                prof = seanceDao.trouverEnseignant(mes_seances.get(i));
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "</p></html>";


                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                            }
                        }

                    }
                }

            }
            this.panel_recherche.setVisible(false);
            this.panel_edt.add(tableau);

            this.panel_edt.revalidate();
            this.panel_edt.repaint();
            this.panel_edt.setVisible(true);
            this.setVisible(true);


        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Si clique sur mes cours
        if (e.getSource() == this.mes_cours) {

            afficherEdtEtudiantAccueil();
        }
        //Si on clique sur rechercher
        if (e.getSource() == this.rechercher) {
            content = new JPanel(new BorderLayout());
            panel_recherche = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

            JLabel label_nom = new JLabel();
            JLabel label_semaine = new JLabel();

            label_nom.setText("Nom de l'étudiant :");
            label_semaine.setText("Numéro semaine");
            JTextField nom = new JTextField();
            nom.setPreferredSize(new Dimension(100, 200));

            JTextField semaine = new JTextField();
            semaine.setPreferredSize(new Dimension(100, 200));

            panel_recherche.add(label_nom);
            panel_recherche.add(nom);
            panel_recherche.add(label_semaine);
            panel_recherche.add(semaine);

            JPanel infos = new JPanel(new BorderLayout());
            recup_info = new JLabel("", JLabel.CENTER);
            infos.add(recup_info);

            JButton lancerrecherche = new JButton(new AbstractAction("Rechercher") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    rechercher_utilisateur(nom.getText(), semaine.getText(), 4);
                    afficherGrille();
                    String string_semaine = semaine.getText();

                    int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                    afficherEdtSemaineEtudiant(4, int_semaine);

                }
            });

            panel_edt.setVisible(false);
            panel_recherche.setVisible(true);
            panel_recherche.add(lancerrecherche);
            this.panel.add(panel_recherche);
            this.add(panel, BorderLayout.CENTER);
            this.setVisible(true);
        }

        ///Si onclique sur un des boutons de la grille de semaine///
        for (int s = 1; s < this.week_button.size(); s++) {
            //Si c'est cliqué
            if (e.getActionCommand().equals(this.week_button.get(s).getText())) {
                System.out.println(this.week_button.get(s).getText()); //On affiche le texte du bouton cliqué

                this.afficherGrille();

                String string_semaine = this.week_button.get(s).getText(); //On get le string du numero de semaine

                int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                this.afficherEdtSemaineEtudiant(4, int_semaine);


            }

        }

        ///Si on clique sur recap///
        if (e.getSource() == this.summary) {
            //JOptionPane stop = new JOptionPane();
            //stop.showMessageDialog(null, "Indisponible pour le moment", "ERREUR", JOptionPane.ERROR_MESSAGE);
            voirrecap(this.etudiant, new java.sql.Date(2020, 6, 2), new java.sql.Date(2020, 6, 11));
        }


    }


    /**
     * Renvoie un recap de toutes les informations d'un enseignant
     */
    public void voirrecap(Etudiant eleve, java.sql.Date date_debut, java.sql.Date date_fin) {

        EtudiantDao etudiantDao = new EtudiantDao();

        long temps_debut = new java.util.Date().getTime() - 259200000;
        long temps_fin = new java.util.Date().getTime() + 959200000;

        java.sql.Date debut = new java.sql.Date(temps_debut);
        java.sql.Date fin = new java.sql.Date(temps_fin);

        System.out.println("Mon ID est " + this.etudiant.getID() + " je suis " + prof.getNom() +
                " je veux mon emplois du temps du " + debut + " au " + fin + " pour le groupe " + this.etudiant.getGroupe().getNom());

        ArrayList<Seance> lesseances_eleve =
                etudiantDao.trouverSeancesSurPeriode(this.etudiant.getID(), debut, fin);

    }

}
