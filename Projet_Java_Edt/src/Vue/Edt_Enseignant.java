package Vue;

import Controlleur.Recherche.RechercheControleur;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
    }


    ///Affichage de l'onglet cours qui sert de page d'accueil lors de la connexion
    public void afficherEdtProfAccueil() {

        this.afficherGrille();
        this.afficherEdtProf(3);

    }

    public void afficherEdtProf(int droit) {
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

        this.panel.add(tableau);
        this.setVisible(true);
    }

    public void afficherEdtSemaineProf(int droit, int semaine) {
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
                                /*TableColumn t = tableau.getColumnModel().getColumn(3);
                                t.setCellRenderer(new ColorierCase(Color.GREEN, Color.PINK));*/
                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                            }
                        }

                    }
                }

            }


            this.panel.add(tableau);
            this.setVisible(true);
        }
    }

    ///Toutes les actions sont controlés ici
    @Override
    public void actionPerformed(ActionEvent e) {

        //Si clique sur rechercher
        if (e.getSource() == this.rechercher) {

            content = new JPanel(new BorderLayout());
            schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

            JLabel label_nom = new JLabel();
            JLabel label_semaine = new JLabel();

            label_nom.setText("Nom utilisateur :");
            label_semaine.setText("Numéro semaine");

            JTextField nom = new JTextField();
            nom.setPreferredSize(new Dimension(100, 200));

            JTextField semaine = new JTextField();
            semaine.setPreferredSize(new Dimension(100, 200));

            schear.add(label_nom);
            schear.add(nom);
            schear.add(label_semaine);
            schear.add(semaine);
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
            schear.add(chercher_utilisateur);
            this.panel.remove(this.tableau);
            this.panel.add(schear, BorderLayout.CENTER);
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

        /*
        ///Il y a possiblement un beug dans la recup des cours
        ///Je crois que dans les seances, l'objet Cours associé est mal contruit
        ///Et que la valeur de ID_Cours est mauvaise
        ///Elle recupère la valeur de ID_Seance au lieu de crée la sienne
        ///Ce n'est que des suppositions
        System.out.println("ICI LE TEST");
        for (Seance uneseance : lesseances_prof) {
            System.out.println(uneseance.getCours().getID() + " " + uneseance.getCours().getNom());
        }
        System.out.println("ICI FIN TEST");
         */

        System.out.println("Tout les cours du prof sur une periode :");
        if (lesseances_prof.size() != 0) {
            for (Seance uneseance : lesseances_prof) {
                System.out.println(uneseance.getID() + " " + uneseance.getCours().getNom() + " " + uneseance.getDate());
            }

            ArrayList<Cours> cours_des_seances = new ArrayList<>();///C'est une liste des cours des seances

            /// On recupère les differents cours des seances
            for (Seance uneseance : lesseances_prof) {
                boolean valide = true;
                for (Cours uncours : cours_des_seances) {
                    if (uneseance.getCours().getNom().equals(uncours.getNom())) {
                        valide = false;
                        break;
                    }
                }
                if (valide) cours_des_seances.add(uneseance.getCours());
            }

            System.out.println("Ma liste de cours :");
            for (Cours lescours : cours_des_seances) System.out.println(lescours.getNom());

            ///C'est une liste de seance celon la matiere
            ///On a n seance et n matière alors on fait double ArrayList
            ArrayList<ArrayList<Seance>> cours_celon_matiere = new ArrayList<>();
            int indice_conteneur = 0;

            /// cette fonction va trier les seances celon leurs cours, pour chaque cours, on a un ArrayList
            for (Cours uncours : cours_des_seances) {
                cours_celon_matiere.add(new ArrayList<>());
                for (Seance uneseance : lesseances_prof) {
                    if (uneseance.getCours().getID() == uncours.getID()) {
                        cours_celon_matiere.get(indice_conteneur).add(uneseance);
                    }
                }
                indice_conteneur++;
            }

            /// Parcours Final pour le recap

            System.out.println("----RECAP----");
            for (ArrayList<Seance> unelistedecours : cours_celon_matiere) {

                Seance premiere_seance = unelistedecours.get(0);
                Seance derniere_seance = unelistedecours.get(0);

                System.out.println("Pour la matière " + unelistedecours.get(0).getCours().getNom() + " du groupe id : " + recip_id_groupe);

                // on cherche la première et la dernière seance
                for (Seance uneseance : unelistedecours) {
                    if (uneseance.getDate().getTime() < premiere_seance.getDate().getTime()) {
                        premiere_seance = uneseance;
                    }
                    if (uneseance.getDate().getTime() > derniere_seance.getDate().getTime()) {
                        derniere_seance = uneseance;
                    }
                }

                System.out.println("La premier seance de " + premiere_seance.getCours().getNom() + " est le : " + premiere_seance.getDate());
                System.out.println("La dernière seance de " + derniere_seance.getCours().getNom() + " est le : " + derniere_seance.getDate());
                System.out.println("Le nombre de séance est : " + unelistedecours.size());

                long temps_seance = 90;
                int nombre_seance = unelistedecours.size();
                long temps_final = temps_seance * nombre_seance;
                int hours = (int) TimeUnit.MINUTES.toHours(temps_final);
                int minutes = (int) (temps_final - TimeUnit.HOURS.toMinutes(hours));

                System.out.println("Le volume horaire est : " + hours + "h" + minutes);

            }


/*
            ///Parcourir par cours
            for (Cours unematiere : cours_des_seances) {
                for (Seance uneseance : lesseances_prof) {///Refaire avec le bon conteuneur, un conteur par matière
                    /// Si la metière de la séance correspond à la matière qu'on a isoler pendant le parcours alors on peut continuer
                    if (uneseance.getCours().getID() == unematiere.getID()) {
                        // On recupère les première et dernière seance
                        // Ce code a été test pour peu de cas, il faudrait agrandir la bdd pour vraiment voir si ça marche
                        if (uneseance.getDate().getTime() < premiere_seance.getDate().getTime()) {
                            premiere_seance = uneseance;/// ATTETION problème potentiel de reference de copie
                        }
                        if (uneseance.getDate().getTime() > derniere_seance.getDate().getTime()) {
                            derniere_seance = uneseance;/// ATTETION problème potentiel de reference de copie
                        }
                    }



        }
        premiere_seance = lesseances_prof.get(0);
        derniere_seance = lesseances_prof.get(0);
    }
 */

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

    }

}
