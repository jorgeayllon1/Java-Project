package Vue;

import Controlleur.Recherche.RechercheControleur;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableColumn;

/**
 * @author Wang David
 */
public class Edt_Enseignant extends Edt {

    Utilisateur user;
    Enseignant prof;
    Groupe groupe = null;
    Salle salle = new Salle();

    GroupeDAO groupeDao = null;
    EnseignantDAO profDao = null;
    SeanceDao seanceDao = new SeanceDao();

    GridBagConstraints grille = new GridBagConstraints();
    JPanel schear = new JPanel();


    ArrayList<Seance> mes_seances = new ArrayList();
    ArrayList<Integer> mes_id = new ArrayList();
    
    JPanel panel_recherche=new JPanel();



    ///Constructeurs
    public Edt_Enseignant() {
    }

    public Edt_Enseignant(Utilisateur user, Enseignant prof) {
        super(user);
        this.summary.addActionListener(this::actionPerformed);
        this.prof = prof;
        afficherEdtProfAccueil();

        this.mes_cours.addActionListener(this);
        this.rechercher.addActionListener(this);
        ///Si on clique sur l'un des boutons de la grille de semaine
        for (int nb_week = 0; nb_week < this.week_button.size(); nb_week++) {
            this.week_button.get(nb_week).addActionListener(this);

        }     
        panel.add(panel_recherche);
        panel_edt.add(tableau);
        panel.add(panel_edt);
        
    }


    ///Affichage de l'onglet cours qui sert de page d'accueil lors de la connexion
    public void afficherEdtProfAccueil() {

        this.afficherGrille();
        this.afficherEdtProf(3);

    }

    public void afficherEdtProf(int droit) {
        panel_edt.removeAll();
        panel_edt.validate();
        panel_edt.repaint();
        ArrayList<JLabel> mes_labels = new ArrayList();
        ///Affichage des séances relatives à cet enseignant
        EnseignantDAO profDao = new EnseignantDAO();
        mes_id = new ArrayList();
        mes_id = profDao.trouverIdSeance(prof);

        mes_seances = new ArrayList();
        mes_seances = profDao.trouverAllSeances(mes_id);

        salle = new Salle();
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


                                groupe = seanceDao.trouverGroupe(mes_seances.get(i));

                                String myString =
                                        "<html><p>" + mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom() + "<br>Groupe :" +
                                                groupe.getNom()
                                                + "<br>Salle :" +
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

    public void afficherEdtSemaineProf(int droit, int semaine) {
        panel_edt.removeAll();
        panel_edt.validate();
        panel_edt.repaint();
        ArrayList<JLabel> mes_labels = new ArrayList();
        content = new JPanel(new BorderLayout());
        if (droit == 3) {
            profDao = new EnseignantDAO();
            mes_id = new ArrayList();
            mes_id = profDao.trouverIdSeance(prof);

            mes_seances = new ArrayList();
            mes_seances = profDao.trouverAllSeancesSemaine(prof.getID(), semaine);
            salle = new Salle();

            for (int i = 0; i < mes_seances.size(); i++) //On parcourt les séances
            {
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


                                groupe = seanceDao.trouverGroupe(mes_seances.get(i));


                                String myString =
                                        "<html><p>" + mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom() + "<br>Groupe :" +
                                                groupe.getNom()
                                                + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "</p></html>";
                                mes_labels.add(new JLabel(myString));
                                int last = mes_labels.size() - 1;
                                mes_labels.get(last).setBackground(Color.red);
                                mes_labels.get(last).setOpaque(true);
                                
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

    ///Toutes les actions sont controlés ici
    @Override
    public void actionPerformed(ActionEvent e) {

        //Si clique sur rechercher
        if (e.getSource() == this.rechercher) {

            content = new JPanel(new BorderLayout());
            panel_recherche = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

            JLabel label_nom = new JLabel();
            JLabel label_semaine = new JLabel();

            label_nom.setText("Nom utilisateur :");
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


            JButton chercher_utilisateur = new JButton(new AbstractAction("Chercher Utilisateur") {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    rechercher_utilisateur(nom.getText(), semaine.getText(), 3);
                    afficherGrille();
                    String string_semaine = semaine.getText();

                    int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                    afficherEdtSemaineProf(3, int_semaine);

                }

            });

            panel_edt.setVisible(false);
            panel_recherche.setVisible(true);
            panel_recherche.add(chercher_utilisateur);
            this.panel.add(panel_recherche);
            this.add(panel, BorderLayout.CENTER);
            this.setVisible(true);
        }

        //Si clique sur cours
        if (e.getSource() == this.mes_cours) {
            afficherEdtProfAccueil();
        }

        ///Si on clique sur recap
        if (e.getSource() == this.summary) {
            voirrecap(this.prof, "1", new Date(2020, 6, 2), new Date(2020, 6, 11));
        }


        ///Si onclique sur un des boutons de la grille de semaine
        for (int s = 1; s < this.week_button.size(); s++) {
            //Si c'est cliqué
            if (e.getActionCommand().equals(this.week_button.get(s).getText())) {
                //System.out.println(this.week_button.get(s).getText()); //On affiche le texte du bouton cliqué
                this.afficherGrille();   //Affichage grille vide          
                String string_semaine = this.week_button.get(s).getText(); //On get le string du numero de semaine
                int int_semaine = Integer.valueOf(string_semaine); //Cast en int              
                this.afficherEdtSemaineProf(3, int_semaine);

            }
        }
    }

    /**
     * Renvoie un recap de toutes les informations d'un enseignant
     */
    public void voirrecap(Enseignant prof, String nomGroupe, Date date_debut, Date date_fin) {

        EnseignantDAO enseignantDAO = new EnseignantDAO();
        CoursDao coursDao = new CoursDao();

        /// ATTENTION peut ne pas marcher si la date d'aujourd'hui n'est pas bonne
        /// Pensez à ajuster l'heure par des +- jours

        long temps_debut = new java.util.Date().getTime() - 259200000;
        long temps_fin = new java.util.Date().getTime() + 959200000;
        int recip_id_groupe = 1;

        java.sql.Date debut = new java.sql.Date(temps_debut);
        java.sql.Date fin = new java.sql.Date(temps_fin);

        System.out.println("Mon ID est " + this.prof.getID() + " je suis " + prof.getNom() +
                " je veux mon emplois du temps du " + debut + " au " + fin + " pour le groupe " + recip_id_groupe);

        /// C'est cette methode qui retourne les seances d'un groupe sur une periode
        ArrayList<Seance> lesseances_prof =
                enseignantDAO.trouverSeancesParGroupeSurPeriode(this.prof.getID(), recip_id_groupe, debut, fin);

        for (Seance uneseance : lesseances_prof) {
            System.out.println(uneseance.getID() + " " + uneseance.getCours().getNom() + " " + uneseance.getDate());
        }

        //Afficher première et dernier seance
        //Compter nombre de seance
        //Calculer volume horaire

        /////////////SECOND TRY//////////////
        //ArrayList<Seance>lesseances = enseignantDAO.trouverSeancesParGroupeSurPeriode()

        /// Pour chaque cours des seances fori
        /// Afficher la première et la dernière seances
        /// Incrementer avec une variable pour calculer le nombre de cours, puis l'afficher
        /// Calculer le volume horaire, car 1 seance = 1h30

        //////////////FRIST TRY//////////////
        /// Pour chaque Cours (matière) fori
        /// Pour charque Groupe         forj

        /// Si le groupe à le cours à une certaine seance
        // et que cette seance ne fait pas déjà partie de l'ensemble
        /// Ajouter à l'ensemble de seance final

        /// afficher information de la première et dernière seance de l'ensemble final
        /// afficher le volume horaire et le nombre de seance

    }

}
