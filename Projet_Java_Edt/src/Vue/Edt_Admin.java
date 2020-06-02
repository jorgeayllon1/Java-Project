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
import java.util.*;

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
            JPanel content = new JPanel(new BorderLayout());
            JPanel schear = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
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
            JPanel infos = new JPanel(new BorderLayout() );
            recup_info = new JLabel("f", JLabel.CENTER);
            infos.add(recup_info);
            content.add(schear, BorderLayout.NORTH);
            content.add(infos, BorderLayout.CENTER);
            this.panel.add(content);
            
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
                        content.add(schear, BorderLayout.NORTH);
                        panel.add(content);
  
                        
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
                        content.add(schear, BorderLayout.NORTH);
                        panel.add(content);
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
                        content.add(schear, BorderLayout.NORTH);
                        panel.add(content);
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
                        content.add(schear, BorderLayout.NORTH);
                        panel.add(content);
                    }
                   
                }
            });

            
        }
   
    }
   

    /**
     * Recherche de l'emploi du temps d'un groupe
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
            
            String s = "<html><p>Les séances pour le groupe " + nom_groupe +" dans la semaine du " + semaine+ " sont : <br>";

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("Les seances sont  :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                    s+=lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom()+"<br>";
                }
                s+="</p></html>";
                recup_info.setText(s);
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
            String s = "<html><p>Les séances sont pour la promotion "+anne_promotion+" dans la semaine" +semaine+" : <br>";
            ArrayList<Seance> lesseances = promotionDAO.lesSeances(id_promotion, numero_semaine);
            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("Les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                    
                    s+=lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom()+"<br>";
                }
                s+="</p></html>";
            recup_info.setText(s);
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
            String s = "<html><p>Les séances pour la salle " +nom_salle+" dans la semaine " +semaine +"  sont : <br>";

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("Les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                    s+=lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom()+"<br>";
                }
                s+="</p></html>";
                recup_info.setText(s);
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
