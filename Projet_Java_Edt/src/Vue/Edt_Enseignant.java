package Vue;

import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.*;

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
<<<<<<< HEAD
    SeanceDao seanceDao = new SeanceDao();
=======
    SeanceDao seanceDao=new SeanceDao();
    
    GridBagConstraints grille = new GridBagConstraints();
>>>>>>> 329e1af55ca2b0d2048e48575ce0be310ed281cb
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

    public void afficherEdtProf(int droit)
    {
        ///Affichage des séances relatives à cet enseignant
        EnseignantDAO profDao = new EnseignantDAO();
        mes_id = new ArrayList();
        mes_id = profDao.trouverIdSeance(prof);

        mes_seances = new ArrayList();
        mes_seances = profDao.trouverAllSeances(mes_id);
<<<<<<< HEAD

        Salle salle = new Salle();

        /*System.out.println("Nombre de séances prevues pour cet enseignant : " + mes_seances.size());
        
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
        }*/

        this.afficherGrille();

=======
        
        salle = new Salle();
>>>>>>> 329e1af55ca2b0d2048e48575ce0be310ed281cb
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
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Groupe :" +
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
    }
<<<<<<< HEAD


    ///Toutes les actions sont controlés ici
    @Override
    public void actionPerformed(ActionEvent e) {

        //Si clique sur rechercher
        if (e.getSource() == this.rechercher) {

            JPanel content = new JPanel(new BorderLayout());
            JPanel schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

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
                }
            });


            schear.add(chercher_utilisateur);
            content.add(schear, BorderLayout.NORTH);
            content.add(infos, BorderLayout.CENTER);
            this.panel.add(content);
            this.setVisible(true);


        }

        //Si clique sur cours
        if (e.getSource() == this.mes_cours) {
            afficherEdtProfAccueil();
        }

        ///Si on clique sur recap
        if (e.getSource() == this.summary) {
            ///Changer c'est valeur par celle recup du JPanel
            /// Pour la date j'espere que c'est facile parce que j'ai galérer comme un ouf pour en crée une à la dure
            voirrecap(this.prof, "rien", new Date(2020, 6, 2), new Date(2020, 6, 11));
        }


        ///Si onclique sur un des boutons de la grille de semaine
        for (int s = 1; s < this.week_button.size(); s++) {
            //Si c'est cliqué
            if (e.getActionCommand().equals(this.week_button.get(s).getText())) {
                System.out.println(this.week_button.get(s).getText()); //On affiche le texte du bouton cliqué

                this.afficherGrille();

                String string_semaine = this.week_button.get(s).getText(); //On get le string du numero de semaine

                int int_semaine = Integer.valueOf(string_semaine); //Cast en int

                EnseignantDAO profDao = new EnseignantDAO();
                ArrayList<Integer> mes_id = new ArrayList();
                mes_id = profDao.trouverIdSeance(prof);

                ArrayList<Seance> mes_seances = new ArrayList();
                mes_seances = profDao.trouverAllSeancesSemaine(prof.getID(), int_semaine);

                for (int i = 0; i < mes_seances.size(); i++) //On parcourt les séances
=======
    
    public void afficherEdtSemaineProf(int droit, int semaine)
    {
        content = new JPanel(new BorderLayout());
        if(droit==4)
        {
            profDao = new EnseignantDAO();
            mes_id = new ArrayList();
            mes_id = profDao.trouverIdSeance(prof);

            mes_seances = new ArrayList();
            mes_seances = profDao.trouverAllSeancesSemaine(prof.getID(), semaine);
            salle = new Salle();
            
            for (int i = 0; i < mes_seances.size(); i++) //On parcourt les séances
>>>>>>> 329e1af55ca2b0d2048e48575ce0be310ed281cb
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
                                            "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Groupe :" +
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

<<<<<<< HEAD

                panel.add(grille_edt);
=======
                this.content.add(grille_edt , BorderLayout.CENTER);
                this.panel.add(content, BorderLayout.CENTER);
>>>>>>> 329e1af55ca2b0d2048e48575ce0be310ed281cb
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

<<<<<<< HEAD

    ///Méthodes de recherche

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
=======
                JTextField nom = new JTextField();
                nom.setPreferredSize(new Dimension(100, 200));
                     
                JTextField semaine = new JTextField();
                semaine.setPreferredSize(new Dimension(100, 200));
                
                schear.add(label_nom);
                schear.add(nom);
                schear.add(label_semaine);
                schear.add(semaine);
                JPanel infos = new JPanel(new BorderLayout() );
                recup_info = new JLabel("", JLabel.CENTER);
                infos.add(recup_info);
>>>>>>> 329e1af55ca2b0d2048e48575ce0be310ed281cb

                JButton chercher_utilisateur = new JButton(new AbstractAction("Chercher Utilisateur") {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        rechercher_utilisateur(nom.getText(), semaine.getText(), 3);
                        afficherGrille();
                        String string_semaine = semaine.getText();

                        int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                        afficherEdtSemaineProf(4,int_semaine);
                        
                        
                    }
                    
                });


                schear.add(chercher_utilisateur); 
                
                content.add(schear, BorderLayout.NORTH);
                //content.add(infos, BorderLayout.CENTER);
                
                this.panel.add(content, BorderLayout.CENTER);
                this.setVisible(true);


        }

        //Si clique sur cours
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
                this.afficherGrille();             
                String string_semaine = this.week_button.get(s).getText(); //On get le string du numero de semaine
                int int_semaine = Integer.valueOf(string_semaine); //Cast en int              
                this.afficherEdtSemaineProf(4, int_semaine);

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
