/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controlleur.Maj.MajControleur;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Wang David
 */
public class Edt_Admin extends Edt {
    
    private JPanel panel_recherche = new JPanel();
    private JPanel boutons_search = new JPanel();
    private JPanel infos = new JPanel();
    
    private JButton chercher_utilisateur=null;
    private JButton chercher_salle=null;
    private JButton chercher_groupe=null;
    private JButton chercher_promotion=null;
    private JButton maj = new JButton ("Mise à jour");
    
    private Utilisateur user = new Utilisateur();
    private Enseignant prof = new Enseignant();
    private Groupe groupe = new Groupe();
    private Salle salle = new Salle();

    private GroupeDAO groupeDao = new GroupeDAO();
    private EnseignantDAO profDao = new EnseignantDAO();
    private SeanceDao seanceDao = new SeanceDao();
    
    private ArrayList<Seance> mes_seances = new ArrayList();
    private ArrayList<Integer> mes_id = new ArrayList();
    Seance[][] stock_seances = null;  //Tableaux pour stocker les seances
    
    ///Mise à jour des données///
    private JPanel content2 = new JPanel(new GridLayout(0,1));

    public Edt_Admin() {
    }


    public Edt_Admin(Utilisateur user) {
        super(user);
       
        this.annule.setVisible(false);
        this.semaine.setVisible(false);
        this.semaine.remove(this.semaine);
        


        ImageIcon maj_icon = new ImageIcon(new ImageIcon("src/Icones/refresh.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        maj = new JButton("Mise à jour", maj_icon);
        this.iconFont(maj);

        this.rechercher.addActionListener(this);
        this.mes_cours.addActionListener(this);
        this.summary.addActionListener(this);
        this.report.addActionListener(this);
        this.maj.addActionListener(this);

        this.panel_recherche.add(boutons_search);

    }

    public void afficherEdtSemaineProf(Enseignant prof, int semaine) {
        suppPanel(infos);
        suppPanel(this.panel_recherche);
        ArrayList<JLabel> mes_labels = new ArrayList();
        content = new JPanel(new BorderLayout());

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
        infos = new JPanel(new GridLayout(0, 1));
        //infos = new JPanel(new BorderLayout() );
        ajoutPanel(infos, tableau);
        content.add(infos, BorderLayout.CENTER);
        this.panel.add(content);
        this.setVisible(true);


    }
    
    ///Méthodes de redirection de panel///
    
    public void suppPanel(JComponent parent)
    {

        parent.removeAll();
        parent.validate();
        parent.repaint();
    }

    public void ajoutPanel(JComponent parent, JComponent child) {
        parent.add(child);
        parent.revalidate();
        parent.repaint();
        parent.setVisible(true);
        child.setVisible(true);
    }

    public void ajoutPanel2(JComponent parent)
    {
        parent.revalidate();
        parent.repaint();
        parent.setVisible(true);
        this.add(parent);
        this.setVisible(true);
    }
    
    ///ACTIONS///
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.mes_cours) {
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Vous n'etes ni etudiant ni enseignant", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        
        if(e.getSource()==this.rechercher)
        {
            ///SUPRESSION///
            suppPanel(panel); //Vider le panel
            suppPanel(content2); //Supprimer le content2 pour maj        
            this.info.setVisible(false);
            suppPanel(boutons_search);
            ///INSTANCIATION
            content = new JPanel(new BorderLayout());
            panel_recherche = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            boutons_search = new JPanel(new FlowLayout());

            Object[] deroulant = new Object[]{"Rechercher...", "Utilisateur", "Groupe", "Promotion", "Salle"};
            JComboBox liste = new JComboBox(deroulant);
            
            panel_recherche.add(liste);

            JLabel label_nom = new JLabel();
            JLabel label_semaine = new JLabel();

            JTextField nom = new JTextField();
            nom.setPreferredSize(new Dimension(100, 50));

            JTextField semaine = new JTextField();
            semaine.setPreferredSize(new Dimension(100, 50));
            
            
            ///INSTANTICATION DES BOUTONS///
            chercher_utilisateur = new JButton();
            chercher_groupe = new JButton();
            chercher_promotion = new JButton();
            chercher_salle = new JButton();
   
            ///AJOUT DANS LE PANEL SUPERIEUR DE RECHERCHE///

            panel_recherche.add(label_nom);
            panel_recherche.add(nom);
            panel_recherche.add(label_semaine);
            panel_recherche.add(semaine);

            infos = new JPanel(new GridLayout(0, 1));
            //infos = new JPanel(new BorderLayout() );
            recup_info = new JLabel("", JLabel.CENTER);
            //infos.add(recup_info);


            content.add(panel_recherche, BorderLayout.NORTH);//Ajout en haut des composants de recherche

            this.panel.add(content);
            this.panel.setVisible(true);

            liste.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    e.getSource();
                    String choisi = (String)liste.getSelectedItem(); //On stocke dans un string l'item selectionné
                    if(choisi=="Utilisateur") //Si on choisit utilisateur dans la liste
                    {                      
                        suppPanel(boutons_search); //On remove les anciens boutons
                        label_nom.setText("Nom Prof :");  //Changement des labels
                        label_semaine.setText("Numéro semaine");
                                   
                        chercher_utilisateur = new JButton(new AbstractAction("Chercher Prof") { //Création nouveau bouton + invocation classe anonyme
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_utilisateur(nom.getText(), semaine.getText(), 1); //On recherhce et controle s'il y a cet utilisateur
                                afficherGrille();
                                String string_semaine = semaine.getText();
                                
                                prof = profDao.trouverProfAvecNom(nom.getText()); //On instancie l'objet prof
                                
                                boolean existe = profDao.siExiste(nom.getText()); //On vérifie s'il existe
                                
                                if(existe==true)//S'il existe dans la bdd
                                {
                                    int int_semaine = Integer.valueOf(string_semaine); //Cast en int
                                    afficherEdtSemaineProf( prof,int_semaine); //On affiche l'edt du prof en question
                                }

                                /*content.add(panel_recherche, BorderLayout.NORTH);
                                panel.add(content);*/
                            }
                        });
                        ajoutPanel(boutons_search,chercher_utilisateur) ; //On ajoute le bouton dans son layout parent
                        panel_recherche.add(boutons_search); //Puis on l'ajoute dans le panel recherche
   
                    }
                    else if(choisi=="Groupe")
                    {
                        suppPanel(boutons_search);
                        chercher_groupe = new JButton(new AbstractAction("Chercher Groupe") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_groupe(nom.getText(), semaine.getText());
                            }
                        });


                        label_nom.setText("Nom groupe :");
                        label_semaine.setText("Numéro semaine");
                        ajoutPanel(boutons_search, chercher_groupe);
                        panel_recherche.add(boutons_search);
                        content.add(panel_recherche, BorderLayout.NORTH);
                        panel.add(content);
                    } else if (choisi == "Promotion") {
                        suppPanel(boutons_search);

                        chercher_promotion = new JButton(new AbstractAction("Chercher Promotion") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_promotion(nom.getText(), semaine.getText());
                            }
                        });

                        label_nom.setText("Promotion :");
                        label_semaine.setText("Numéro semaine");
                        ajoutPanel(boutons_search, chercher_promotion);
                        panel_recherche.add(boutons_search);
                        content.add(panel_recherche, BorderLayout.NORTH);
                        panel.add(content);
                    } else if (choisi == "Salle") {

                        suppPanel(boutons_search);
                        chercher_salle = new JButton(new AbstractAction("Chercher Salle") {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                rechercher_salle(nom.getText(), semaine.getText());
                            }
                        });

                        label_nom.setText("Salle :");
                        label_semaine.setText("Numéro semaine");
                        ajoutPanel(boutons_search, chercher_salle);
                        panel_recherche.add(boutons_search);
                        content.add(panel_recherche, BorderLayout.NORTH);
                        panel.add(content);
                    }

                }
            });


        }

        if (e.getSource() == this.maj) {
            ///Méthode affichage maj
            System.out.println("Mise a jour");
            afficherInterfaceMaj();
        }
        
        
        if(e.getSource()==this.report)
        {
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Non dispo", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        
        if(e.getSource()==this.logout)
        {
            this.dispose();
        }
   

        /// ICI les test de mise à jours
        if (e.getSource() == this.summary) {
            MajControleur controleur_maj = new MajControleur();
            controleur_maj.affecterEnseignat();
        }
        /// Finf des test

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
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Numero de semaine non valide", "ERREUR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GroupeDAO groupeDAO = new GroupeDAO();

        id_groupe = groupeDAO.idCelonNom(nom_groupe);


        if (id_groupe != 0) {/// Si le id_groupe = 0 alors le groupe n'existe pas

            ArrayList<Seance> lesseances = groupeDAO.trouverAllSeancesSemaine(id_groupe, numero_semaine);

            String s = "<html><p>Les séances pour le groupe " + nom_groupe + " dans la semaine du " + semaine + " sont : <br>";

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("Les seances sont  :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                    s += lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom() + "<br>";
                }
                s += "</p></html>";
                recup_info.setText(s);
            } else System.out.println("Pas de séance cette semaine");
        } else {
            System.out.println("Ce groupe n'existe pas");
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Erreur lors de la saisie", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }


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
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Numero de semaine ou annee non valide", "ERREUR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PromotionDAO promotionDAO = new PromotionDAO();
        id_promotion = promotionDAO.idCelonAnne(numero_promotion);

        if (id_promotion != 0) {
            String s = "<html><p>Les séances sont pour la promotion " + anne_promotion + " dans la semaine" + semaine + " : <br>";
            ArrayList<Seance> lesseances = promotionDAO.lesSeances(id_promotion, numero_semaine);
            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("Les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());

                    s += lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom() + "<br>";
                }
                s += "</p></html>";
                recup_info.setText(s);
            } else System.out.println("Pas de séance cette semaine");


        } else {
            System.out.println("Cette promotion n'existe pas");
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Cette promotion n'existe pas", "ERREUR", JOptionPane.ERROR_MESSAGE);

        }

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
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Numero de semaine non valide", "ERREUR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SalleDAO salleDAO = new SalleDAO();

        id_salle = salleDAO.idCelonNom(nom_salle);

        if (id_salle != 0) {/// Si le id_groupe = 0 alors le groupe n'existe pas

            ArrayList<Seance> lesseances = salleDAO.lesSeances(id_salle, numero_semaine);// ICI LES SEANCES
            String s = "<html><p>Les séances pour la salle " + nom_salle + " dans la semaine " + semaine + "  sont : <br>";

            if (lesseances.size() != 0) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                System.out.println("Les seances sont :");

                for (int i = 0; i < lesseances.size(); i++) {
                    System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom());
                    s += lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                            + lesseances.get(i).getCours().getNom() + "<br>";
                }
                s += "</p></html>";
                recup_info.setText(s);
            } else System.out.println("Pas de séance cette semaine");
        } else {
            System.out.println("Cette salle n'existe pas");
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Cette salle n'existe pas", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Renvoie un recap de toutes les informations d'un enseignant
     */
    public void voirrecap(Enseignant prof, Date date_debut, Date date_fin) {
        System.out.println("je suis " + prof.getNom() + " je veux mon emplois du temps du " + date_debut + " au " + date_fin);
    }

    //////////////////////////////////----------------------------MISE A JOUR DES DONNEES----------------------------------///////////////////////////
    public void afficherInterfaceMaj()
    {
        suppPanel(panel);
        suppPanel(content2);
        suppPanel(this.content);
        
        JPanel panel_enlever = new JPanel(new BorderLayout());
        JPanel panel_affecter = new JPanel(new BorderLayout());
        JPanel panel_modifier = new JPanel(new BorderLayout());
        JPanel panel_ajouter = new JPanel(new BorderLayout());
        JPanel panel_annuler = new JPanel(new BorderLayout());
        JPanel panel_valider= new JPanel(new BorderLayout());
        JPanel panel_deplacer = new JPanel(new BorderLayout());
        
        this.afficherPanelEnlever(panel_enlever);
        this.afficherPanelAffecter(panel_affecter);
        this.afficherPanelModifier(panel_modifier);
        this.afficherPanelAjouter(panel_ajouter);
        this.afficherPanelAnnuler(panel_annuler);
        this.afficherPanelValider(panel_valider);
        this.afficherPanelDeplacer(panel_deplacer);
        
        JTabbedPane bar_onglets = new JTabbedPane(JTabbedPane.TOP);
        Font font = new Font("Script", Font.CENTER_BASELINE, 18);
        bar_onglets.setFont(font);
        
        
        bar_onglets.addTab("Enlever", panel_enlever);
        bar_onglets.addTab("Affecter", panel_affecter);
        bar_onglets.addTab("Modifier", panel_modifier);
        bar_onglets.addTab("Ajouter", panel_ajouter);
        bar_onglets.addTab("Annuler", panel_annuler);
        bar_onglets.addTab("Valider", panel_valider);
        bar_onglets.addTab("Deplacer", panel_deplacer);
        
        ajoutPanel(content2,bar_onglets);
        ajoutPanel(panel,content2);
        panel.setVisible(true);
        
    }
    
    public void afficherPanelEnlever(JPanel panel)
    {
        
        
        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0,1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre
        
        ///Bouton à ajouter dans le bas
        JButton enlever = new JButton("ENLEVER");
        panel.add(enlever,BorderLayout.SOUTH);
        
        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user :");
        JRadioButton radio_prof=new JRadioButton("Enseignant");    
        JRadioButton radio_eleve=new JRadioButton("Groupe");  
        ButtonGroup bg=new ButtonGroup();  
        bg.add(radio_prof); bg.add(radio_eleve);
        JLabel text_user = new JLabel("");
        
        radio_prof.addActionListener(new ActionListener() { //Si clique sur prof
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radio_prof.isSelected())
                {
                    text_user.setText("Nom prof : ");
                }
                
            }
        });
        
        radio_eleve.addActionListener(new ActionListener() { //Si clique sur etudiant
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radio_eleve.isSelected())
                {
                    text_user.setText("Nom groupe : ");
                }
                
            }
        });
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80,30));
        
        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[]=new String[52];
        
        
        for(int i=0;i<52;i++) //Initialisation des numéros de semaine
        { 
            semaine[i]=""+(int)(i+1); 

        } 
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");
        
        chercher.addActionListener(new ActionListener() { //Si clique sur bouton chercher
            @Override
            public void actionPerformed(ActionEvent e) {
                String semaine_str = (String)list_semaine.getSelectedItem();
                
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur         
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe

                if(existe==true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf( prof,int_semaine,centre); //On affiche l'edt du prof en question
                }
                tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                                int col = tableau.columnAtPoint(evt.getPoint());
                                
                                    if(stock_seances[row][col]!=null) //Si il y a une séance dans la cellule
                                    {
                                        System.out.println(stock_seances[row][col].getID());
                                        JFrame frame =new JFrame();
                                        JOptionPane.showConfirmDialog(frame,"Voulez-vous supprimer cette séance : \n" 
                                                + "Nom séance : " +stock_seances[row][col].getCours().getNom()
                                                + " Date séance : " +stock_seances[row][col].getDate());
                                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                    }

                    }
                });
                
                
            }
        });
        
        
        haut.add(text_enlever);
        haut.add(radio_prof); haut.add(radio_eleve);
        haut.add(text_user);
        haut.add(field_nom);
        
        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);
        
        panel.add(haut,BorderLayout.NORTH);
        
    }
    
    public void afficherPanelAffecter(JPanel panel)
    {
        
        JButton test = new JButton("affecter");
        panel.add(test);
        
    }
    
    public void afficherPanelModifier(JPanel panel)
    {
        ///Panel centre on  dans le panel centre
        JPanel centre = new JPanel(new FlowLayout());
        JTextArea donnees_seance = new JTextArea();
        donnees_seance.setPreferredSize(new Dimension(600,200));
        centre.add(donnees_seance);
        
        JLabel text_cours = new JLabel("Id du cours remplaçant : ");
        JTextField field_cours = new JTextField();
        field_cours.setPreferredSize(new Dimension(30,30));
        String liste[] = new String[]{"Cours interactif", "Cours magistral" , "TD" , "TP" , "Projet", "Soutien"};
        JLabel text_type = new JLabel("Type du cours : ");
        JComboBox liste_type = new JComboBox(liste);
        JButton nom_cours = new JButton("Modifier nom");
        JButton type_cours = new JButton("Modifier type cours");
        
        centre.add(text_cours);
        centre.add(field_cours);
        centre.add(text_type);
        centre.add(liste_type);
        centre.add(nom_cours);
        centre.add(type_cours);
        
        nom_cours.setVisible(false);
        type_cours.setVisible(false);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        
        ///Bouton à ajouter dans le bas
        JButton modifier = new JButton("MODIFIER");
        panel.add(modifier,BorderLayout.SOUTH);
        
        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_seance = new JLabel("Veuillez entrer l'id de la séance à modifier :");
                
        JTextField field_seance = new JTextField();
        field_seance.setPreferredSize(new Dimension(80,30));

        JButton chercher = new JButton("Chercher");
        
        chercher.addActionListener(new ActionListener() { //Si clique sur bouton chercher
            @Override
            public void actionPerformed(ActionEvent e) {
                String seance_str = field_seance.getText(); //On get l'id de la seance
                int id_seance = Integer.parseInt(seance_str); //Cast en int
                SeanceDao sDao = new SeanceDao();
                boolean existe = sDao.siExiste(id_seance); 
                if(existe==true) //Si existe
                {
                    Seance s = sDao.find(id_seance); //On crée l'objet, c'est avec cet objet qu'on va modifier ses données
                    String donnees = "Nom du cours : " +s.getCours().getNom()
                                           +"<br> Date du cours : " + s.getDate() 
                                           +"<br>Type du cours : " + s.getType().getNom();
                    donnees_seance.setText(donnees);
                    nom_cours.setVisible(true);
                    type_cours.setVisible(true);
                }
                else
                {
                    JOptionPane stop = new JOptionPane();
                    stop.showMessageDialog(null, "Seance inconnue", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
        });
        
        nom_cours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cours_str = field_cours.getText(); //On get la saisie dans le field
                if(cours_str.equals("")) //Si vide
                {
                    JOptionPane stop = new JOptionPane();
                    stop.showMessageDialog(null, "Champ vide ", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    int cours_id = Integer.parseInt(cours_str);
                    CoursDao cDao = new CoursDao();
                    Cours c = new Cours();
                    boolean existe = cDao.siExiste(cours_id);
                    c = cDao.find(cours_id); //On trouve le cours

                    if(existe==true)
                    {
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Cours modifié en : "+c.getNom(), "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Cours id inconnu", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                
            }
        });
        
        type_cours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String choisi = (String)liste_type.getSelectedItem();
                JOptionPane stop = new JOptionPane();
                stop.showMessageDialog(null, "Type modifié en : "+choisi, "ERREUR", JOptionPane.ERROR_MESSAGE);
   
            }
        });
        
        
        haut.add(text_seance);
        haut.add(field_seance);
        haut.add(chercher);
        
        panel.add(haut,BorderLayout.NORTH);
    }
    
    public void afficherPanelAjouter(JPanel panel)
    {
        JButton test = new JButton("ajouter");
        panel.add(test);
    }
    
    public void afficherPanelAnnuler(JPanel panel)
    {
       
        
        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0,1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre
        
        ///Bouton à ajouter dans le bas
        JButton annuler = new JButton("ANNULER");
        panel.add(annuler,BorderLayout.SOUTH);
        
        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user :");
        JRadioButton radio_prof=new JRadioButton("Enseignant");    
        JRadioButton radio_eleve=new JRadioButton("Groupe");  
        ButtonGroup bg=new ButtonGroup();  
        bg.add(radio_prof); bg.add(radio_eleve);
        JLabel text_user = new JLabel("");
        
        radio_prof.addActionListener(new ActionListener() { //Si clique sur prof
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radio_prof.isSelected())
                {
                    text_user.setText("Nom prof : ");
                }
                
            }
        });
        
        radio_eleve.addActionListener(new ActionListener() { //Si clique sur etudiant
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radio_eleve.isSelected())
                {
                    text_user.setText("Nom groupe : ");
                }
                
            }
        });
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80,30));
        
        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[]=new String[52];
        
        
        for(int i=0;i<52;i++) //Initialisation des numéros de semaine
        { 
            semaine[i]=""+(int)(i+1); 

        } 
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");
        
        chercher.addActionListener(new ActionListener() { //Si clique sur bouton chercher
            @Override
            public void actionPerformed(ActionEvent e) {
                String semaine_str = (String)list_semaine.getSelectedItem();
                
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur         
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe

                if(existe==true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf( prof,int_semaine,centre); //On affiche l'edt du prof en question
                }
                tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                                int col = tableau.columnAtPoint(evt.getPoint());
                                
                                    if(stock_seances[row][col]!=null) //Si il y a une séance dans la cellule
                                    {
                                        Date today = new Date(System.currentTimeMillis());
                                        if(stock_seances[row][col].getDate().compareTo(today)>0) //Si la séance selectionnée vennat après la date actuelle
                                        {
                                            System.out.println(stock_seances[row][col].getID());
                                            JFrame frame =new JFrame();
                                            JOptionPane.showConfirmDialog(frame,"Voulez-vous annuler cette séance : \n" 
                                                    + "Nom séance : " +stock_seances[row][col].getCours().getNom()
                                                    + " Date séance : " +stock_seances[row][col].getDate());
                                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        }
                                        /*else
                                        {
                                            JOptionPane stop = new JOptionPane();
                                             stop.showMessageDialog(null, "Vous ne pouvez pas annuler une séance avant la date actuelle", "ERREUR", JOptionPane.ERROR_MESSAGE);
                                        }*/
                                        

                                    }

                    }
                });
                
                
            }
        });
        
        
        haut.add(text_enlever);
        haut.add(radio_prof); haut.add(radio_eleve);
        haut.add(text_user);
        haut.add(field_nom);
        
        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);
        
        panel.add(haut,BorderLayout.NORTH);
    }
    
    public void afficherPanelValider(JPanel panel)
    {
        
        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0,1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre
        
        ///Bouton à valider dans le bas
        JButton valider = new JButton("VALIDER");
        panel.add(valider,BorderLayout.SOUTH);
        
        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user :");
        JRadioButton radio_prof=new JRadioButton("Enseignant");    
        JRadioButton radio_eleve=new JRadioButton("Groupe");  
        ButtonGroup bg=new ButtonGroup();  
        bg.add(radio_prof); bg.add(radio_eleve);
        JLabel text_user = new JLabel("");
        
        radio_prof.addActionListener(new ActionListener() { //Si clique sur prof
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radio_prof.isSelected())
                {
                    text_user.setText("Nom prof : ");
                }
                
            }
        });
        
        radio_eleve.addActionListener(new ActionListener() { //Si clique sur etudiant
            @Override
            public void actionPerformed(ActionEvent e) {
                if(radio_eleve.isSelected())
                {
                    text_user.setText("Nom groupe : ");
                }
                
            }
        });
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80,30));
        
        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[]=new String[52];
        
        
        for(int i=0;i<52;i++) //Initialisation des numéros de semaine
        { 
            semaine[i]=""+(int)(i+1); 

        } 
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");
        
        chercher.addActionListener(new ActionListener() { //Si clique sur bouton chercher
            @Override
            public void actionPerformed(ActionEvent e) {
                String semaine_str = (String)list_semaine.getSelectedItem();
                
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur         
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe

                if(existe==true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf( prof,int_semaine,centre); //On affiche l'edt du prof en question
                }
                tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                                int col = tableau.columnAtPoint(evt.getPoint());
                                
                                    if(stock_seances[row][col]!=null) //Si il y a une séance dans la cellule
                                    {
                                        Date today = new Date(System.currentTimeMillis());
                                        if(stock_seances[row][col].getDate().compareTo(today)<0) //Si la séance selectionnée venaat après la date actuelle
                                        {
                                            System.out.println(stock_seances[row][col].getID());
                                            JFrame frame =new JFrame();
                                            JOptionPane.showConfirmDialog(frame,"Voulez-vous valider cette séance : \n" 
                                                    + "Nom séance : " +stock_seances[row][col].getCours().getNom()
                                                    + " Date séance : " +stock_seances[row][col].getDate());
                                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        }
                                        /*else
                                        {
                                            JOptionPane stop = new JOptionPane();
                                             stop.showMessageDialog(null, "Vous ne pouvez validez que les séances avant la date d'aujourd'hui", "ERREUR", JOptionPane.ERROR_MESSAGE);
                                        }*/
                                        

                                    }

                    }
                });
                
                
            }
        });
        
        
        haut.add(text_enlever);
        haut.add(radio_prof); haut.add(radio_eleve);
        haut.add(text_user);
        haut.add(field_nom);
        
        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);
        
        panel.add(haut,BorderLayout.NORTH);
    }
    
    public void afficherPanelDeplacer(JPanel panel)
    {
        JButton test = new JButton("deplacer");
        panel.add(test);
    }
    /**Méthode pour afficher la date à coté des différents jours
     * de la semaine selon le numéro de semaine en param
     * @param semaine 
     */
     
     public void afficherEdtSemaineProf(Enseignant prof, int semaine, JPanel pan) {

        suppPanel(pan);
        ///Clear le tableau
        for(int i=1;i<tableau.getRowCount();i++)
        {
            for(int j=1;j<tableau.getColumnCount();j++)
            {
                tableau.getModel().setValueAt("", i, j);
            }
        } 
        
        profDao = new EnseignantDAO();
        mes_id = new ArrayList();
        mes_id = profDao.trouverIdSeance(prof);

        mes_seances = new ArrayList();
        mes_seances = profDao.trouverAllSeancesSemaine(prof.getID(), semaine);
        
        salle = new Salle();
        
        stock_seances = new Seance[tableau.getRowCount()][tableau.getColumnCount()];//Initialisation des tableaux de séances avec nb de lignes et colonne de la grille
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


                            tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                            stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                        }
                    }

                }
            }

        }
        
        //pan.add(tableau.getTableHeader(),BorderLayout.PAGE_START);
        pan.add(tableau);
    }

}
