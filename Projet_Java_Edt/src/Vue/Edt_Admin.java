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
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Wang David
 */
public class Edt_Admin extends Edt {
    
    JPanel schear = new JPanel();

    public Edt_Admin() {
    }


    public Edt_Admin(Utilisateur user) {
        super(user);

        this.rechercher.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==this.rechercher)
        {
            schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            Object[] deroulant = new Object[]{"Rechercher...", "Utilisateur" , "Groupe" , "Promotion" , "Salle"};
            JComboBox liste = new JComboBox(deroulant);

            
            schear.add(liste);
            
            JLabel label_nom = new JLabel();
            JLabel label_semaine = new JLabel();
            
            JTextField nom = new JTextField();
            nom.setPreferredSize(new Dimension(100, 200));

            JTextField semaine = new JTextField();
            semaine.setPreferredSize(new Dimension(100, 200));
            schear.add(label_nom);
            schear.add(nom);
            schear.add(label_semaine);
            schear.add(semaine);
            panel.add(schear);
            
            liste.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    e.getSource();
                    String choisi = (String)liste.getSelectedItem();
                    if(choisi=="Utilisateur")
                    {
                        

                        JButton chercher_utilisateur = new JButton(new AbstractAction("Chercher Utilisateur") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_utilisateur(nom.getText(), semaine.getText(), 1);
                            }
                        });

                        label_nom.setText("Nom utilisateur :");
                        label_semaine.setText("Numéro semaine");
                        schear.add(chercher_utilisateur);     
                        panel.add(schear);
  
                        
                    }
                    else if(choisi=="Groupe")
                    {
                        JButton chercher_groupe = new JButton(new AbstractAction("Chercher Groupe") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_groupe(nom.getText(), semaine.getText());
                            }
                        });
                        
                        panel.repaint();
                        label_nom.setText("Nom groupe :");
                        label_semaine.setText("Numéro semaine");
                        schear.add(chercher_groupe);     
                        panel.add(schear);
                    }
                    
                    else if(choisi=="Promotion")
                    {
                        JButton chercher_promotion = new JButton(new AbstractAction("Chercher Promotion") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_promotion(nom.getText(), semaine.getText());
                            }
                        });
                        
                        label_nom.setText("Promotion :");
                        label_semaine.setText("Numéro semaine");
                        schear.add(chercher_promotion);     
                        panel.add(schear);
                    }
                    
                    else if(choisi=="Salle")
                    {
                        JButton chercher_salle = new JButton(new AbstractAction("Chercher Salle") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_salle(nom.getText(), semaine.getText());
                            }
                        });
                        
                        label_nom.setText("Salle :");
                        label_semaine.setText("Numéro semaine");
                        schear.add(chercher_salle);     
                        panel.add(schear);
                    }
                   
                }
            });

            
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
     * Recherche de l'emplois du temps d'un groupe
     */
    public void rechercher_groupe(String nom_groupe, String semaine) {

        int numero_semaine = 0;
        int id_groupe = 0;
        try {
            numero_semaine = Integer.parseInt(semaine);
        } catch (NumberFormatException e) {
            System.err.println("Numero de semaine non valide");
            return;
        }

        GroupeDAO groupeDAO = new GroupeDAO();

        id_groupe = groupeDAO.idCelonNom(nom_groupe);

        if (id_groupe != 0) {/// Si le id_groupe = 0 alors le groupe n'existe pas

            ArrayList<Seance> lesseances = groupeDAO.trouverAllSeancesSemaine(id_groupe, numero_semaine);

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                }
            } else System.out.println("Pas de séance cette semaine");
        } else System.out.println("Ce groupe n'existe pas");


    }

    /**
     * Recherche l'emplois du temps d'une promo pour une semaine choisie
     */
    public void rechercher_promotion(String anne_promotion, String semaine) {

        int numero_semaine = 0;//Valeur temp pour gestion erreur
        int numero_promotion = 0;//Valeur temp pour gestion erreur

        int id_promotion = 0;

        try {
            numero_semaine = Integer.parseInt(semaine);
            numero_promotion = Integer.parseInt(anne_promotion);
        } catch (NumberFormatException e) {
            System.err.println("Numero de semaine ou de année non valide");
            return;
        }

        PromotionDAO promotionDAO = new PromotionDAO();
        id_promotion = promotionDAO.idCelonAnne(numero_promotion);

        if (id_promotion != 0) {
            ArrayList<Seance> lesseances = promotionDAO.lesSeances(id_promotion, numero_semaine);
            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                }
            } else System.out.println("Pas de séance cette semaine");

        } else System.out.println("Cette promotion n'existe pas");

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
