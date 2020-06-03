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
    SeanceDao seanceDao=new SeanceDao();
    
    GridBagConstraints grille = new GridBagConstraints();
    JPanel schear = new JPanel();
    
    
    ArrayList<Seance> mes_seances = new ArrayList();
    ArrayList<Integer> mes_id = new ArrayList();
    


    ///Constructeurs
    public Edt_Enseignant() {
    }

    public Edt_Enseignant(Utilisateur user, Enseignant prof) 
    {
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

                            int colonne_semaine = jour_semaine-1;
                            int ligne_semaine=0;
                            if (str4.toString().equals(heure)) //Si ca commence à 10h
                            {
                                if (heure.contains("8"))
                                    ligne_semaine=1;
                                if (heure.contains("10"))
                                    ligne_semaine=2;
                                if (heure.contains("12"))
                                    ligne_semaine=3;
                                if (heure.contains("14"))
                                    ligne_semaine=4;
                                if (heure.contains("16"))
                                    ligne_semaine=5;
                                if (heure.contains("18"))
                                    ligne_semaine=6;
                                if(heure.contains("20"))
                                    ligne_semaine=7;

                                
                                groupe = seanceDao.trouverGroupe(mes_seances.get(i));

                                String myString =
                                        "<html><p>" +mes_seances.get(i).getID()+ mes_seances.get(i).getCours().getNom()  + "<br>Groupe :" +
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
    
    public void afficherEdtSemaineProf(int droit, int semaine)
    {
        ArrayList<JLabel> mes_labels = new ArrayList();
        content = new JPanel(new BorderLayout());
        if(droit==3)
        {
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


                            int colonne_semaine = jour_semaine-1;
                            int ligne_semaine=0;
                            if (str4.toString().equals(heure)) //Si ca commence à 10h
                            {
                                if (heure.contains("8"))
                                    ligne_semaine=1;
                                if (heure.contains("10"))
                                    ligne_semaine=2;
                                if (heure.contains("12"))
                                    ligne_semaine=3;
                                if (heure.contains("14"))
                                    ligne_semaine=4;
                                if (heure.contains("16"))
                                    ligne_semaine=5;
                                if (heure.contains("18"))
                                    ligne_semaine=6;
                                if(heure.contains("20"))
                                    ligne_semaine=7;

                                
                                groupe = seanceDao.trouverGroupe(mes_seances.get(i));
                                

                                String myString =
                                        "<html><p>" +mes_seances.get(i).getID()+ mes_seances.get(i).getCours().getNom()  + "<br>Groupe :" +
                                                groupe.getNom()
                                                 + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "</p></html>";
                                mes_labels.add(new JLabel(myString));
                                int last = mes_labels.size()-1;
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
                JPanel infos = new JPanel(new BorderLayout() );
                recup_info = new JLabel("", JLabel.CENTER);
                infos.add(recup_info);
                
                

                JButton chercher_utilisateur = new JButton(new AbstractAction("Chercher Utilisateur") {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        rechercher_utilisateur(nom.getText(), semaine.getText(), 3);
                        afficherGrille();
                        String string_semaine = semaine.getText();

                        int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                        afficherEdtSemaineProf(3,int_semaine);

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
                this.afficherEdtSemaineProf(3, int_semaine);

            }
        }
    }

    /**
     * Renvoie un recap de toutes les informations d'un enseignant
     */
    public void voirrecap(Enseignant prof, Date date_debut, Date date_fin) {
        System.out.println("je suis " + prof.getNom() + " je veux mon emplois du temps du " + date_debut + " au " + date_fin);
    }
    
}
