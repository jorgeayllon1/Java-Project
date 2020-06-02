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
    SeanceDao seanceDao=new SeanceDao();

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
                                
                                groupe = seanceDao.trouverGroupe(mes_seances.get(i));

                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom()  + "<br>Groupe :" +
                                                groupe.getNom()
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
                    rechercher_utilisateur(nom.getText(), semaine.getText(), 3);
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
            voirrecap(this.prof, new Date(2020, 6, 2), new Date(2020, 6, 11));
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
                
                EnseignantDAO profDao = new EnseignantDAO();
              ArrayList<Integer> mes_id = new ArrayList();
           mes_id = profDao.trouverIdSeance(prof);

           ArrayList<Seance> mes_seances = new ArrayList();
           mes_seances = profDao.trouverAllSeancesSemaine(prof.getID(), int_semaine);
                
                for (int i = 0; i < mes_seances.size(); i++) //On parcourt les séances
                {

                    Salle salle = new Salle();
                    salle = profDao.trouverSalle(mes_seances.get(i));
                    java.util.Date date = mes_seances.get(i).getDate();
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

                                groupe = seanceDao.trouverGroupe(mes_seances.get(i));

                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom()  + "<br>Groupe :" +
                                                groupe.getNom()
                                                 + "<br>Salle :" +
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


    }

    
    public void rechercher_utilisateur(String nom, String semaine, int droit) {

        int numero_semaine = 0;
        boolean validationdacces = false;

        // On verifie que l'utilisateur a bien ecrit un entier pour la semaine
        try {
            numero_semaine = Integer.parseInt(semaine);
        } catch (NumberFormatException e) {
            System.out.println("Numero de semaine non valide");
            return;
        }

        // On crée les utilisateurs et on va faire une recherche dessus
        UtilisateurDao userDAO = new UtilisateurDao();
        ArrayList<Utilisateur> mesUsers = new ArrayList<>();

        for (int i = 1; i < userDAO.getTaille("utilisateur") + 1; i++) {
            mesUsers.add(userDAO.find(i));
        }

        Utilisateur leusersouhaiter = new Utilisateur();

        for (int i = 0; i < mesUsers.size(); i++) {
            if (mesUsers.get(i).getNom().equals(nom)) {
                leusersouhaiter = new Utilisateur(mesUsers.get(i));///dés qu'on trouve le bon utilisateur on sort de la boucle
                break;
            }
        }
        for (int i = 0; i < mesUsers.size(); i++) {
            if (mesUsers.get(i).getNom().equals(nom) && mesUsers.get(i).getDroit() == droit) {
                leusersouhaiter = new Utilisateur(mesUsers.get(i));
                break;
            }
        }

        if (droit == 1 || droit == 2) {// Admin et Referent on une vue total sur les utilisateur
            validationdacces = true;
        } else {
            if (droit == leusersouhaiter.getDroit())/// Pour les prof et etudiant, il ne peuvent voir que respectivement prof et etudiant
                validationdacces = true;
        }

        if (leusersouhaiter.getID() != 0) {
            if (validationdacces) {
                System.out.println("Les informations de la personne sont :");
                System.out.println(leusersouhaiter.getID() + " " + leusersouhaiter.getNom() + " " + leusersouhaiter.getPrenom()
                        + " " + leusersouhaiter.getMail() + " " + leusersouhaiter.getDroit());

                // On recupère les information de l'utilisateur si possible
                ArrayList<Seance> lesSeances = userDAO.lesSeance(leusersouhaiter.getID(), numero_semaine);//ICI LES SEANCES

                System.out.println("Son emploi du temps est le suivant :");
                for (Seance uneseance :
                        lesSeances) {
                    System.out.println(uneseance.getID() + " " + uneseance.getSemaine() + " " + uneseance.getDate().toString() + " " + uneseance.getHeureDebut()
                            + " " + uneseance.getHeureFin() + " " + uneseance.getCours().getNom() + " " + uneseance.getType().getNom());
                }
                System.out.println("Il faut modifier la vu pour les afficher correctement");
            } else System.out.println("Accés non autorisé");
        } else
            System.out.println("Personne non trouvé dans la BDD : " + nom);

    }

    

    /**
     * Recherche l'emplois du temps du salle pour une semaine choisi
     */
    public void rechercher_salle(String nom_salle, String semaine) {

        int numero_semaine = 0;
        int id_salle = 0;
        try {
            numero_semaine = Integer.parseInt(semaine);
        } catch (NumberFormatException e) {
            System.err.println("Numero de semaine non valide");
            return;
        }

        SalleDAO salleDAO = new SalleDAO();

        id_salle = salleDAO.idCelonNom(nom_salle);

        if (id_salle != 0) {/// Si le id_groupe = 0 alors le groupe n'existe pas

            ArrayList<Seance> lesseances = salleDAO.lesSeances(id_salle, numero_semaine);// ICI LES SEANCES

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                }
            } else System.out.println("Pas de séance cette semaine");
        } else System.out.println("Cette salle n'existe pas n'existe pas");

    }

    /**
     * Renvoie un recap de toutes les informations d'un enseignant
     */
    public void voirrecap(Enseignant prof, Date date_debut, Date date_fin) {
        System.out.println("je suis " + prof.getNom() + " je veux mon emplois du temps du " + date_debut + " au " + date_fin);
    }
    

}
