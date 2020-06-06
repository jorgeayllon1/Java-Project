
package Vue;

import Modele.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * @author Wang David
 */
public class Edt_Etudiant extends Edt {

    private Utilisateur user = null;
    private Etudiant etudiant = null;
    private Groupe groupe = null;
    private GroupeDAO groupeDao = null;
    private EtudiantDao etudiantDao = null;

    private ArrayList<Seance> mes_seances = new ArrayList();
    private ArrayList<Integer> mes_id = new ArrayList();
    private SeanceDao seanceDao = new SeanceDao();
    private Salle salle = null;
    private Enseignant prof = null;

    private JPanel panel_recherche = new JPanel();
    private JPanel content3=new JPanel(new GridLayout(0, 1));

    public Edt_Etudiant() {
    }

    public Edt_Etudiant(Utilisateur user, Etudiant etudiant) {

        super(user);
        this.etudiant = etudiant;
        etudiantDao = new EtudiantDao(); //********************
        etudiant = (Etudiant) etudiantDao.find(etudiant.getID());//*****************
        groupeDao = new GroupeDAO();
        groupe = groupeDao.find(etudiant.getGroupe().getId());

        afficherEdtEtudiantAccueil();
        this.mes_cours.addActionListener(this);
        this.rechercher.addActionListener(this);
        this.summary.addActionListener(this);

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
        this.afficherDateEdt(num_semaine);
        this.afficherEdtEtudiant(4);

    }

    /**
     * Méthode qui va afficher l'edt lors de la connexion ainsi que
     * quand il clique sur l'onglet cours
     *
     * @param droit
     */

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
                                prof = seanceDao.trouverEnseignant(mes_seances.get(i));
                            if(mes_seances.get(i).getEtat()==0 || mes_seances.get(i).getEtat()==1)
                            {
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom()+ "<br>Invalidable ou en cours <br>Manque salle/prof/groupe</p></html>";


                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                
                            }
                            else if(mes_seances.get(i).getEtat()==2 )
                            {
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Valide</p></html>";


                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                
                            }
                            else if(mes_seances.get(i).getEtat()==3)
                            {
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Annulé</p></html>";


                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                
                            }
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

    /**
     * Méthode qui va afficher l'edt de l'étudiant
     * en fonction d'une semaine
     *
     * @param droit
     * @param semaine
     */
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
                            if(mes_seances.get(i).getEtat()==0 || mes_seances.get(i).getEtat()==1)
                            {
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom()+ "<br>Invalidable ou en cours <br>Manque salle/prof/groupe</p></html>";


                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                
                            }
                            else if(mes_seances.get(i).getEtat()==2 )
                            {
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Valide</p></html>";


                                tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                
                            }
                            else if(mes_seances.get(i).getEtat()==3)
                            {
                                String myString =
                                        "<html><p>" + mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Annulé</p></html>";


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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Si clique sur mes cours
        if (e.getSource() == this.mes_cours) {
            afficherEdtEtudiantAccueil();
  

        }
        //Si on clique sur rechercher
        if (e.getSource() == this.rechercher) {
            
            suppPanel(this.content3);
            suppPanel(this.content);
            content = new JPanel(new BorderLayout());
            panel_recherche = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

            JLabel label_nom = new JLabel();
            JLabel label_semaine = new JLabel();

            label_nom.setText("Nom de l'étudiant :");
            label_semaine.setText("Numéro semaine");
            JTextField nom = new JTextField(etudiant.getNom());
            nom.setEditable(false);
            nom.setPreferredSize(new Dimension(100, 70));

            JTextField semaine = new JTextField();
            semaine.setPreferredSize(new Dimension(100, 70));

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
                    
                    String string_semaine = semaine.getText();

                    int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                    afficherGrille();
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineEtudiant(4, int_semaine);//On peut que rechercher ses propres cours

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
            suppPanel(this.content3);
            suppPanel(this.content);
            //Si c'est cliqué
            if (e.getActionCommand().equals(this.week_button.get(s).getText())) {
                System.out.println(this.week_button.get(s).getText()); //On affiche le texte du bouton cliqué
                
                

                String string_semaine = this.week_button.get(s).getText(); //On get le string du numero de semaine

                int int_semaine = Integer.valueOf(string_semaine); //Cast en int           
                this.afficherGrille();
                this.afficherDateEdt(int_semaine);
                this.afficherEdtSemaineEtudiant(4, int_semaine);


            }

        }

        ///Si on clique sur recap///
        if (e.getSource() == this.summary) {
            

            
            JButton hello = new JButton("Hello");
            content3.add(hello);

            /// ATTENTION peut ne pas marcher si la date d'aujourd'hui n'est pas bonne
            /// Pensez à ajuster l'heure par des +- jours

            long temps_debut = new java.util.Date().getTime() - 259200000;
            long temps_fin = new java.util.Date().getTime() + 959200000;

            java.sql.Date debut = new java.sql.Date(temps_debut);
            java.sql.Date fin = new java.sql.Date(temps_fin);
            //voirrecap(debut, fin);
            Recap recap = new Recap(this.etudiant);
        }

        if (e.getSource() == this.logout) {
            this.dispose(); //Fermeture fenêtre
        }

    }


    /**
     * Renvoie un recap de toutes les informations d'un enseignant
     */
    public String voirrecap(java.sql.Date date_debut, java.sql.Date date_fin, Etudiant etudiant) {

        String donnees="";
        EtudiantDao etudiantDao = new EtudiantDao();

        System.out.println("Mon ID est " + etudiant.getID() + " je suis " + etudiant.getNom() +
                " je veux mon emplois du temps du " + date_debut + " au " + date_fin + " pour le " + etudiant.getGroupe().getNom());
        donnees+="Mon ID est " + etudiant.getID() + " je suis " + etudiant.getNom() +
                " je veux mon emplois du temps du " + date_debut + " au " + date_fin + " pour le " + etudiant.getGroupe().getNom() +"\n";

        /// C'est cette methode qui retourne les seances sur une periode
        ArrayList<Seance> lesseances_eleve =
                etudiantDao.trouverSeancesSurPeriode(etudiant.getID(), date_debut, date_fin);

        System.out.println("Tout les cours de l'eleve sur une periode :");
        donnees+="Tout les cours de l'eleve sur une periode :\n";
        if (lesseances_eleve.size() != 0) {

            for (Seance uneseance : lesseances_eleve) {
                System.out.println(uneseance.getID() + " " + uneseance.getCours().getNom() + " " + uneseance.getDate() + " " + uneseance.getEtat());
                donnees+=uneseance.getID() + " " + uneseance.getCours().getNom() + " " + uneseance.getDate() + " " + uneseance.getEtat()+"\n";
                
            }

            ArrayList<Cours> cours_des_seances = new ArrayList<>();///C'est une liste des cours des seances

            /// On recupère les differents cours des seances
            for (Seance uneseance : lesseances_eleve) {
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
            donnees+="Ma liste de cours :\n";
            for (Cours lescours : cours_des_seances) System.out.println(lescours.getNom());

            ///C'est une liste de seance celon la matiere
            ///On a n seance et n matière alors on fait double ArrayList
            ArrayList<ArrayList<Seance>> cours_celon_matiere = new ArrayList<>();
            int indice_conteneur = 0;

            // cette fonction va trier les seances celon leurs cours
            // pour chaque cours on a un ArrayList
            for (Cours uncours : cours_des_seances) {
                cours_celon_matiere.add(new ArrayList<>());
                for (Seance uneseance : lesseances_eleve) {
                    if (uneseance.getCours().getID() == uncours.getID()) {
                        cours_celon_matiere.get(indice_conteneur).add(uneseance);
                    }
                }
                indice_conteneur++;
            }

            /// Parcours Final pour le recap

            System.out.println("----RECAP----");
            donnees+="----RECAP----\n";
            for (ArrayList<Seance> unelistedecours : cours_celon_matiere) {

                Seance premiere_seance = unelistedecours.get(0);
                Seance derniere_seance = unelistedecours.get(0);

                System.out.println("Pour la matière " + unelistedecours.get(0).getCours().getNom() + " : ");
                donnees+="Pour la matière " + unelistedecours.get(0).getCours().getNom() + " : \n";

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
                donnees+="La premier seance de " + premiere_seance.getCours().getNom() + " est le : " + premiere_seance.getDate()+"\n";
                System.out.println("La dernière seance de " + derniere_seance.getCours().getNom() + " est le : " + derniere_seance.getDate());
                donnees+="La dernière seance de " + derniere_seance.getCours().getNom() + " est le : " + derniere_seance.getDate()+"\n";
                System.out.println("Le nombre de séance est : " + unelistedecours.size());
                donnees+="Le nombre de séance est : " + unelistedecours.size()+"\n";

                long temps_seance = 90;
                int nombre_seance = unelistedecours.size();
                long temps_final = temps_seance * nombre_seance;
                int hours = (int) TimeUnit.MINUTES.toHours(temps_final);
                int minutes = (int) (temps_final - TimeUnit.HOURS.toMinutes(hours));

                System.out.println("Le volume horaire est : " + hours + "h" + minutes);
                donnees+="Le volume horaire est : " + hours + "h" + minutes+"\n";

            }

        } else
        {
            System.out.println("Pas de seance en cette periode");
            donnees+="Pas de seance en cette periode";
        }
        return donnees;

    }
    

}
