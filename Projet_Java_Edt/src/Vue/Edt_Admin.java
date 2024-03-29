package Vue;

import Controlleur.MajControleur;
import Modele.*;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

/**
 * @author Wang David
 */

public class Edt_Admin extends Edt {

    //////////////Attrobuts/////////////////
    private final JPanel panel_recherche = new JPanel();
    private final JPanel boutons_search = new JPanel();
    private final JPanel infos = new JPanel();

    private JButton chercher_utilisateur = null;
    private JButton chercher_salle = null;
    private JButton chercher_groupe = null;
    private JButton chercher_promotion = null;
    private JButton maj = new JButton("Mise à jour");

    private Utilisateur user = new Utilisateur();
    private Enseignant prof = new Enseignant();
    private Etudiant etudiant = new Etudiant();
    private Groupe groupe = new Groupe();
    private Salle salle = new Salle();

    private GroupeDAO groupeDao = new GroupeDAO();
    private EnseignantDAO profDao = new EnseignantDAO();
    private SeanceDao seanceDao = new SeanceDao();
    private EtudiantDao etudiantDao = new EtudiantDao();
    private SalleDAO salleDao = new SalleDAO();

    private ArrayList<Seance> mes_seances = new ArrayList();
    private ArrayList<Integer> mes_id = new ArrayList();
    Seance[][] stock_seances = null;  //Tableaux pour stocker les seances

    ///Mise à jour des données///
    private JPanel content2 = new JPanel(new GridLayout(0, 1));
    private MajControleur majControleur = new MajControleur();
    
    private int droit=0;


    public Edt_Admin() {
    }


    /**Constructeur qui va afficher l'interface de l'utilisateur
     * 
     * @param user 
     */
    public Edt_Admin(Utilisateur user) {
        super(user);

        this.droit = user.getDroit();
        this.annule.setVisible(false);
        this.semaine.setVisible(false);
        this.summary.setVisible(false);
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


    //////////////////////////////////////////////////////////----------------RECHERCHE-----------------/////////////////////////////////////////////////////////////////

    /**
     * Recherche de l'emploi du temps d'un groupe
     *
     * @param nom_groupe
     * @param semaine
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
                //recup_info.setText(s);
            } else System.out.println("Pas de séance cette semaine");
        } else {
            System.out.println("Ce groupe n'existe pas");
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Erreur lors de la saisie", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }


    }

    /**
     * Recherche l'emplois du temps d'une promo pour une semaine choisie
     * @param anne_promotion
     * @param semaine
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
     * @param nom_salle
     * @param semaine
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
            

        
        try
        {
            if (id_salle != 0) {/// Si le id_groupe = 0 alors le groupe n'existe pas

            ArrayList<Seance> lesseances = salleDAO.lesSeances(id_salle, numero_semaine);// ICI LES SEANCES
            String s = "<html><p>Les séances pour la salle " + nom_salle + " dans la semaine " + semaine + "  sont : <br>";

                if (!lesseances.isEmpty()) {/// Si le nombre de seance = 0 alors il n'y a pas d'emplois du temps

                    System.out.println("Les seances sont :");

                    for (int i = 0; i < lesseances.size(); i++) {
                        System.out.println(lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                                + lesseances.get(i).getCours().getNom());
                        s += lesseances.get(i).getHeureDebut() + " " + lesseances.get(i).getHeureFin() + " " + lesseances.get(i).getCours().getID() + " "
                                + lesseances.get(i).getCours().getNom() + "<br>";
                    }
                    s += "</p></html>";
                    //recup_info.setText(s);
                } else System.out.println("Pas de séance cette semaine");
            } else {
                System.out.println("Cette salle n'existe pas");
                JOptionPane stop = new JOptionPane();
                stop.showMessageDialog(null, "Cette salle n'existe pas", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            System.out.println("erreur");
        }

        

    }

    /**Affiche le panel de recherche pour un user
     * 
     * @param panel 
     */
    public void afficherPanelUser(JPanel panel) {

        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à ajouter dans le bas
        JButton enlever = new JButton("ENLEVER");
        panel.add(enlever, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user :");
        JRadioButton radio_prof = new JRadioButton("Enseignant");
        JRadioButton radio_eleve = new JRadioButton("Etudiant");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radio_prof);
        bg.add(radio_eleve);
        JLabel text_user = new JLabel("");

        radio_prof.addActionListener((ActionEvent e) -> {
            if (radio_prof.isSelected()) {
                text_user.setText("Nom prof : ");
            }
        } //Si clique sur prof
        );

        radio_eleve.addActionListener((ActionEvent e) -> {
            if (radio_eleve.isSelected()) {
                text_user.setText("Nom etudiant : ");
            }
        } //Si clique sur etudiant
        );
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80, 30));

        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[] = new String[52];


        for (int i = 0; i < 52; i++) //Initialisation des numéros de semaine
        {
            semaine[i] = "" + (int) (i + 1);

        }
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");

        chercher.addActionListener((ActionEvent e) -> {
            String semaine_str = (String) list_semaine.getSelectedItem();
            
            if (radio_prof.isSelected()) //Si prof
            {
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe
                
                if (existe == true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf(prof, int_semaine, centre); //On affiche l'edt du prof en question
                }
                
            } else if (radio_eleve.isSelected()) //Si etudiant
            {
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur
                etudiant = etudiantDao.trouverEleveAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = etudiantDao.siExiste(field_nom.getText()); //On vérifie s'il existe
                
                if (existe == true)//S'il existe dans la bdd
                {
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineEleve(etudiant, int_semaine, centre); //On affiche l'edt du prof en question
                }
            }
        } //Si clique sur bouton chercher
        );


        haut.add(text_enlever);
        haut.add(radio_prof);
        haut.add(radio_eleve);
        haut.add(text_user);
        haut.add(field_nom);

        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);

    }
    
    /**Affiche le panel de recherche pour un groupe
     * 
     * @param panel 
     */
    public void afficherPanelGroupe(JPanel panel) {
        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à ajouter dans le bas
        JButton groupe = new JButton("GROUPE");
        panel.add(groupe, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_promo = new JLabel("Veuillez choisir la promo :");


        PromotionDAO pDao = new PromotionDAO();
        Promotion p = new Promotion();
        Integer[] liste_promo = new Integer[pDao.getTaille("promotion")];
        for (int i = 1; i < pDao.getTaille("promotion") + 1; i++) {
            p = pDao.find(i);
            liste_promo[i - 1] = p.getAnnee();

        }
        JComboBox jcombo_promo = new JComboBox(liste_promo);
        JLabel text_groupe = new JLabel("Veuillez choisir le groupe");
        JComboBox jcombo_groupe = new JComboBox();

        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[] = new String[52];


        for (int i = 0; i < 52; i++) //Initialisation des numéros de semaine
        {
            semaine[i] = "" + (int) (i + 1);

        }
        JComboBox list_semaine = new JComboBox(semaine);

        ////FAIRE UN BOUTON APPLIQUER LE FILTRE///

        jcombo_promo.addActionListener((ActionEvent e) -> {
            Promotion p1 = new Promotion();
            int annee_promo = (Integer) jcombo_promo.getSelectedItem();
            ArrayList<Groupe> mes_groupes = new ArrayList();
            p1 = pDao.trouverPromoAvecAnnee(annee_promo);
            mes_groupes = pDao.allGroupes(p1);
            String[] liste_groupe = new String[mes_groupes.size()]; //Liste de groupe de taille nombre de groupe dans cette promo
            for (int j = 0; j < mes_groupes.size(); j++) {
                liste_groupe[j] = mes_groupes.get(j).getNom();
                
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(liste_groupe);
            jcombo_groupe.setModel(model);
        });

        JButton chercher = new JButton("Chercher");

        chercher.addActionListener((ActionEvent e) -> {
            String semaine_str = (String) list_semaine.getSelectedItem();
            String groupe_str = (String) jcombo_groupe.getSelectedItem();
            Groupe groupe1 = new Groupe();
            rechercher_groupe(groupe_str, semaine_str); //On recherhce et controle s'il y a cet utilisateur         
            int id_groupe = groupeDao.idCelonNom(groupe_str);
            groupe1 = groupeDao.find(id_groupe);
            int int_semaine = Integer.valueOf(semaine_str); //Cast en int
            afficherDateEdt(int_semaine);
            afficherEdtSemaineGroupe(groupe1, int_semaine, centre); //On affiche l'edt du prof en question
        } //Si clique sur bouton chercher
        );


        haut.add(text_promo);
        haut.add(jcombo_promo);

        haut.add(text_groupe);
        haut.add(jcombo_groupe);

        haut.add(text_semaine);
        haut.add(list_semaine);

        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);
    }

    /**Affiche le panel de recherche pour une promo
     * 
     * @param panel 
     */
    public void afficherPanelPromo(JPanel panel) {

    }

    /**Affiche le panel de recherche pour une salle
     * 
     * @param panel 
     */
    public void afficherPanelSalle(JPanel panel) {
        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à ajouter dans le bas
        JButton btn_salle = new JButton("SALLE");
        panel.add(btn_salle, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_salle = new JLabel("Veuillez entrer le nom de la salle :");

        JTextField field_salle = new JTextField();
        field_salle.setPreferredSize(new Dimension(80, 30));

        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[] = new String[52];


        for (int i = 0; i < 52; i++) //Initialisation des numéros de semaine
        {
            semaine[i] = "" + (int) (i + 1);

        }
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");

        chercher.addActionListener((ActionEvent e) -> {
            String semaine_str = (String) list_semaine.getSelectedItem();
            String nom_salle = field_salle.getText();
            rechercher_salle(nom_salle, semaine_str);
            
            int id_salle = salleDao.idCelonNom(nom_salle);
            salle = salleDao.find(id_salle);
            int int_semaine = Integer.valueOf(semaine_str); //Cast en int
            afficherDateEdt(int_semaine);
            afficherEdtSemaineSalle(salle, int_semaine, centre); //On affiche l'edt du prof en question
        } //Si clique sur bouton chercher
        );

        haut.add(text_salle);
        haut.add(field_salle);

        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);
    }

    ////////////////////////////////////////////////////////////////////-------ACTIONS-------//////////////////////////////////////////////////////////////////

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.mes_cours) {
            JOptionPane stop = new JOptionPane();
            stop.showMessageDialog(null, "Vous n'etes ni etudiant ni enseignant", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }

        if (e.getSource() == this.rechercher) {
            suppPanel(this.panel);
            suppPanel(this.content2);
            suppPanel(this.content);

            JPanel panel_user = new JPanel(new BorderLayout());
            JPanel panel_groupe = new JPanel(new BorderLayout());
            JPanel panel_modifier = new JPanel(new BorderLayout());
            JPanel panel_ajouter = new JPanel(new BorderLayout());


            this.afficherPanelUser(panel_user);
            this.afficherPanelGroupe(panel_groupe);
            this.afficherPanelPromo(panel_modifier);
            this.afficherPanelSalle(panel_ajouter);


            JTabbedPane bar_onglets = new JTabbedPane(JTabbedPane.TOP);
            Font font = new Font("Script", Font.CENTER_BASELINE, 18);
            bar_onglets.setFont(font);


            bar_onglets.addTab("Utilisateur", panel_user);
            bar_onglets.addTab("Groupe", panel_groupe);
            bar_onglets.addTab("Promotion", panel_modifier);
            bar_onglets.addTab("Salle", panel_ajouter);

            ajoutPanel(content, bar_onglets);
            ajoutPanel(panel, content);
            panel.setVisible(true);

        }

        if (e.getSource() == this.maj) {
            System.out.println(user.getDroit());
            
            if(this.droit==1) //Si admin
            {
                ///Méthode affichage maj
                afficherInterfaceMaj();
            }
            else //Si référent
            {
                JOptionPane stop = new JOptionPane();
                stop.showMessageDialog(null, "Vous n'avez pas accès aux mises à jour, vous etes referent", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
            
        }


        if (e.getSource() == this.report) {
            Report report = new Report(this.user);
        }

        if (e.getSource() == this.logout) {
            this.dispose();
        }
        

    }


    //////////////////////////////////////////////----------------------------MISE A JOUR DES DONNEES----------------------------------//////////////////////////////////////

    
    /**Affiche l'interface mise à jour
     * 
     */
    public void afficherInterfaceMaj() {
        suppPanel(this.panel);
        suppPanel(this.content2);
        suppPanel(this.content);

        JPanel panel_enlever = new JPanel(new BorderLayout());
        JPanel panel_affecter = new JPanel(new BorderLayout());
        JPanel panel_modifier = new JPanel(new BorderLayout());
        JPanel panel_ajouter = new JPanel(new BorderLayout());
        JPanel panel_annuler = new JPanel(new BorderLayout());
        JPanel panel_valider = new JPanel(new BorderLayout());
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

        ajoutPanel(content2, bar_onglets);
        ajoutPanel(panel, content2);
        panel.setVisible(true);

    }

    /**Affiche le panel de recherche pour enlever
     * 
     * @param panel 
     */
    public void afficherPanelEnlever(JPanel panel) {


        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à ajouter dans le bas
        JButton enlever = new JButton("ENLEVER");
        panel.add(enlever, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user à enlever :");
        JRadioButton radio_prof = new JRadioButton("Enseignant");
        JRadioButton radio_groupe = new JRadioButton("Groupe");
        JRadioButton radio_salle = new JRadioButton("Salle");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radio_prof);
        bg.add(radio_groupe);
        bg.add(radio_salle);
        JLabel text_user = new JLabel("");

        radio_prof.addActionListener((ActionEvent e) -> {
            if (radio_prof.isSelected()) {
                text_user.setText("Nom prof : ");
            }
        } //Si clique sur prof
        );

        radio_groupe.addActionListener((ActionEvent e) -> {
            if (radio_groupe.isSelected()) {
                text_user.setText("Nom groupe : ");
            }
        } //Si clique sur etudiant
        );
        radio_salle.addActionListener((ActionEvent e) -> {
            if (radio_salle.isSelected()) {
                text_user.setText("Nom salle : ");
            }
        } //Si clique sur etudiant
        );
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80, 30));

        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[] = new String[52];


        for (int i = 0; i < 52; i++) //Initialisation des numéros de semaine
        {
            semaine[i] = "" + (int) (i + 1);

        }
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");


        chercher.addActionListener((ActionEvent e) -> {
            String semaine_str = (String) list_semaine.getSelectedItem();
            
            if (radio_prof.isSelected()) //Si prof
            {
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe
                
                if (existe == true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf(prof, int_semaine, centre); //On affiche l'edt du prof en question
                }
                
            } else if (radio_groupe.isSelected()) //Si groupe
            {
                rechercher_groupe(field_nom.getText(), semaine_str); //On recherhce et controle 
                String groupe_str = field_nom.getText();
                int id_groupe = groupeDao.idCelonNom(groupe_str);
                groupe = groupeDao.find(id_groupe);
                boolean existe = groupeDao.siExiste(groupe_str);
                if(existe == true)
                {
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineGroupe(groupe, int_semaine, centre); //On affiche l'edt 
                    
                }
                
                
            }else if (radio_salle.isSelected()) //Si salle
            {
                rechercher_salle(field_nom.getText(), semaine_str); //On recherhce et controle 
                String salle_str = field_nom.getText();
                int id_salle = salleDao.idCelonNom(salle_str);
                salle = salleDao.find(id_salle);
                boolean existe = salleDao.siExiste(salle_str);
                if(existe == true)
                {
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineSalle(salle, int_semaine, centre); //On affiche l'edt 
                    
                }
                
                
            }
            
            for (MouseListener element : tableau.getMouseListeners()) tableau.removeMouseListener(element);
            
            tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                    int col = tableau.columnAtPoint(evt.getPoint());
                    
                    if (stock_seances[row][col] != null && (stock_seances[row][col].getEtat() == 2 || stock_seances[row][col].getEtat() == 3)) //Si il y a une séance dans la cellule
                    {
                        System.out.println(stock_seances[row][col].getID());
                        JFrame frame = new JFrame();
                        
                        int reponse = JOptionPane.showConfirmDialog(frame, "Voulez-vous enlever cette séance : \n"
                                + "Nom séance : " + stock_seances[row][col].getCours().getNom()
                                + " Date séance : " + stock_seances[row][col].getDate());
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        
                        
                        if (reponse == 0 && radio_prof.isSelected()) { //Enlever prof
                            majControleur.enleverEnseignantdeSeance(stock_seances[row][col]);
                        } else if (reponse == 0 && radio_groupe.isSelected()) { //Enlever élève
                            String nom_groupe = field_nom.getText();
                            majControleur.enleverGroupeSeance(stock_seances[row][col],nom_groupe);
                        }
                        else if(reponse==0 && radio_salle.isSelected()){ //Enlever salle
                            majControleur.enleverSalledeSeance(stock_seances[row][col]);
                        }
                        
                    }
                    else
                    {
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Aucune séance à enlever", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                    

                }
                
            });
        } //Si clique sur bouton chercher
        );


        haut.add(text_enlever);
        haut.add(radio_prof);
        haut.add(radio_groupe);
        haut.add(radio_salle);
        haut.add(text_user);
        haut.add(field_nom);

        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);

    }

    /**Affiche le panel de recherche pour affecter
     * 
     * @param panel 
     */
    public void afficherPanelAffecter(JPanel panel) {

        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à ajouter dans le bas
        JButton affecter = new JButton("AFFECTER");
        panel.add(affecter, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Que voulez-vous affecter :");
        JRadioButton radio_prof = new JRadioButton("Enseignant");
        JRadioButton radio_groupe = new JRadioButton("Groupe");
        JRadioButton radio_salle = new JRadioButton("Salle");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radio_prof);
        bg.add(radio_groupe);
        bg.add(radio_salle);
        JLabel text_user = new JLabel("");
        JLabel id_seance = new JLabel("ID seance");
        JTextField field_id = new JTextField();
        field_id.setPreferredSize(new Dimension(40,30));

        radio_prof.addActionListener(new ActionListener() { //Si clique sur prof
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radio_prof.isSelected()) {
                    text_user.setText("Nom prof : ");
                }

            }
        });

        radio_groupe.addActionListener(new ActionListener() { //Si clique sur etudiant
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radio_groupe.isSelected()) {
                    text_user.setText("Nom groupe : ");
                }

            }
        });
        radio_salle.addActionListener(new ActionListener() { //Si clique sur salle
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radio_salle.isSelected()) {
                    text_user.setText("Nom salle : ");
                }

            }
        });
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80, 30));


        JButton chercher = new JButton("Chercher");

        chercher.addActionListener(new ActionListener() { //Si clique sur bouton chercher
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    SeanceDao sDao = new SeanceDao();
                    Seance s = new Seance();
                    String str_id_seance = field_id.getText();


                    int int_id_seance = Integer.parseInt(str_id_seance);




                    boolean seance_existe = sDao.siExiste(int_id_seance);
                        if(seance_existe==false)
                        {
                            JOptionPane stop = new JOptionPane();
                            stop.showMessageDialog(null, "Seance id inexisant", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        }
                        s = sDao.find(int_id_seance);
                    int int_semaine = s.getSemaine();
                    String semaine_str = Integer.toString(int_semaine);


                    if(seance_existe==true)
                    {
                        if (radio_prof.isSelected()) //Si prof
                        {
                            rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur
                            prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                            boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe

                            if (existe == true)//S'il existe dans la bdd
                            {

                                afficherDateEdt(int_semaine);
                                afficherEdtManque(s,centre);
                            }

                        } else if (radio_groupe.isSelected()) //Si groupe
                        {
                            rechercher_groupe(field_nom.getText(), semaine_str); //On recherhce et controle s'il y a cet utilisateur
                            String groupe_str = field_nom.getText();
                            int id_groupe = groupeDao.idCelonNom(groupe_str);
                            groupe = groupeDao.find(id_groupe);
                            boolean existe = groupeDao.siExiste(groupe_str);
                            if(existe == true)
                            {

                                afficherDateEdt(int_semaine);
                                afficherEdtManque(s,centre);

                            }


                        }else if(radio_salle.isSelected()) //Si salle
                        {
                            rechercher_salle(field_nom.getText(), semaine_str); 
                            String salle_str = field_nom.getText();
                            int id_salle = salleDao.idCelonNom(salle_str);
                            salle = salleDao.find(id_salle);
                            boolean existe = salleDao.siExiste(salle_str);
                            if(existe == true)
                            {
                                afficherDateEdt(int_semaine);
                                afficherEdtManque(s,centre);

                            }
                        }
                    }
                    else
                    {
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Seance id erreur", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(NumberFormatException excp)
                {
                    System.out.println("Erreur seance id");
                    JOptionPane stop = new JOptionPane();
                    stop.showMessageDialog(null, "Seance id erreur", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
                

                


                for (MouseListener element : tableau.getMouseListeners()) tableau.removeMouseListener(element);

                tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                        int col = tableau.columnAtPoint(evt.getPoint());
                        try
                        {
                            if (stock_seances[row][col] != null || (stock_seances[row][col].getEtat() == 0)) //Si ily a seance ou qu'il n'y pas de salle/prof/groupe
                            {

                                String nom = field_nom.getText();
                                JFrame frame = new JFrame();

                                if(radio_prof.isSelected())
                                {
                                    if(seanceDao.siProf(stock_seances[row][col])==false)
                                    {
                                        int reponse = JOptionPane.showConfirmDialog(frame, "Voulez-vous affecter un enseignant : \n");
                                        if(reponse==0)
                                        {
                                            majControleur.affecterEnseignatSeance(stock_seances[row][col],nom );
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane stop = new JOptionPane();
                                        stop.showMessageDialog(null, "Il ya déjà un enseignant affecté", "ERREUR", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                }
                                else if(radio_groupe.isSelected())
                                {
                                    if(seanceDao.siGroupe(stock_seances[row][col])==false)
                                    {
                                        int reponse = JOptionPane.showConfirmDialog(frame, "Voulez-vous affecter un groupe : \n");
                                        if(reponse==0)
                                        {
                                            majControleur.affecterGroupeSeance(stock_seances[row][col], nom);
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane stop = new JOptionPane();
                                        stop.showMessageDialog(null, "Il ya déjà un groupe affecté", "ERREUR", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                }
                                else if(radio_salle.isSelected())
                                {
                                    if(seanceDao.siSalle(stock_seances[row][col])==false)
                                    {
                                        int reponse = JOptionPane.showConfirmDialog(frame, "Voulez-vous affecter une salle : \n");
                                        if(reponse==0)
                                        {
                                            majControleur.affecterSalleSeance(stock_seances[row][col], nom);
                                        }
                                    }
                                    else
                                    {
                                        JOptionPane stop = new JOptionPane();
                                        stop.showMessageDialog(null, "Il ya déjà une salle affecté", "ERREUR", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
                                }

                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


                            }
                            else if(stock_seances[row][col] == null)
                            {
                                JOptionPane stop = new JOptionPane();
                                stop.showMessageDialog(null, "Aucune séance à affecter", "ERREUR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        catch(NullPointerException e)
                        {
                            System.out.println("Seance vide");
                            JOptionPane stop = new JOptionPane();
                            stop.showMessageDialog(null, "Aucune séance à affecter", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        }

                        


                    }

                });


            }
        });


        haut.add(text_enlever);
        haut.add(radio_prof);
        haut.add(radio_groupe);
        haut.add(radio_salle);
        haut.add(text_user);
        haut.add(field_nom);
        haut.add(id_seance);
        haut.add(field_id);

        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);

    }

    /**Affiche le panel de recherche pour modifier
     * 
     * @param panel 
     */
    public void afficherPanelModifier(JPanel panel) {
        ///Panel centre on  dans le panel centre
        JPanel centre = new JPanel(new FlowLayout());
        JTextArea donnees_seance = new JTextArea();
        donnees_seance.setPreferredSize(new Dimension(600, 200));
        centre.add(donnees_seance);

        JLabel text_cours = new JLabel("Id du cours remplaçant : ");
        JTextField field_cours = new JTextField();
        field_cours.setPreferredSize(new Dimension(30, 30));
        String liste[] = new String[]{"C. interactif", "C. magistral", "TD", "TP", "Projet", "Soutien"};
        JLabel text_type = new JLabel("Type du cours : ");
        JComboBox liste_type = new JComboBox(liste);
        JButton nom_cours = new JButton("Modifier");

        centre.add(text_cours);
        centre.add(field_cours);
        centre.add(text_type);
        centre.add(liste_type);
        centre.add(nom_cours);

        

        nom_cours.setVisible(false);

        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre


        ///Bouton à ajouter dans le bas
        JButton modifier = new JButton("MODIFIER");
        panel.add(modifier, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_seance = new JLabel("Veuillez entrer l'id de la séance à modifier :");

        JTextField field_seance = new JTextField();
        field_seance.setPreferredSize(new Dimension(80, 30));

        JButton chercher = new JButton("Chercher");

        chercher.addActionListener(new ActionListener() { //Si clique sur bouton chercher
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    String seance_str = field_seance.getText(); //On get l'id de la seance
                    int id_seance = Integer.parseInt(seance_str); //Cast en int
                    SeanceDao sDao = new SeanceDao();
                    boolean existe = sDao.siExiste(id_seance);
                    if (existe == true) //Si existe
                    {
                        Seance s = sDao.find(id_seance); //On crée l'objet, c'est avec cet objet qu'on va modifier ses données
                        String donnees = "Nom du cours : " + s.getCours().getNom()
                                + "<br> Date du cours : " + s.getDate()
                                + "<br>Type du cours : " + s.getType().getNom();
                        donnees_seance.setText(donnees);

                        String id_seance_str = Integer.toString(s.getCours().getID());
                        field_cours.setText(id_seance_str);
                        nom_cours.setVisible(true);
                        for(int i=0;i<liste.length;i++)
                        {
                            if(liste[i].equals(s.getType().getNom()))
                            {
                                liste_type.setSelectedItem(s.getType().getNom());
                            }
                        }

                    } else {
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Seance inconnue", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(NumberFormatException excep)
                {
                    JOptionPane stop = new JOptionPane();
                    stop.showMessageDialog(null, "Seance inconnue", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }

                


            }
        });


        nom_cours.addActionListener((ActionEvent e) -> {
            String cours_str = field_cours.getText(); //On get la saisie dans le field
            String choisi = (String) liste_type.getSelectedItem();
            if (cours_str.equals("")) //Si vide
            {
                JOptionPane stop = new JOptionPane();
                stop.showMessageDialog(null, "Champ vide ", "ERREUR", JOptionPane.ERROR_MESSAGE);
            } else 
            {
                try{
                    
                    int cours_id = Integer.parseInt(cours_str);
                    CoursDao cDao = new CoursDao();
                    Cours c = new Cours();
                    boolean existe = cDao.siExiste(cours_id);
                    c = cDao.find(cours_id); //On trouve le cours

                    if (existe == true) {
                        String seance_str = field_seance.getText(); //On get l'id de la seance
                        int id_seance = Integer.parseInt(seance_str); //Cast en int
                        SeanceDao sDao = new SeanceDao();
                        Seance s = sDao.find(id_seance); //On crée l'objet, c'est avec cet objet qu'on va modifier ses données
                        majControleur.modifierSeance(s, c.getNom(), choisi);
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Cours modifié en : " + c.getNom() + " et type modifié en : " + choisi, "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        JOptionPane stop = new JOptionPane();
                        stop.showMessageDialog(null, "Cours id inconnu ", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(NumberFormatException excepti)
                {
                    JOptionPane stop = new JOptionPane();
                    stop.showMessageDialog(null, "Cours id inconnu ", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });


        haut.add(text_seance);
        haut.add(field_seance);
        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);
    }

    /**Affiche le panel de recherche pour ajouter
     * 
     * @param panel 
     */
    public void afficherPanelAjouter(JPanel panel) {
        JButton test = new JButton("ajouter");
        panel.add(test);
    }

    /**Affiche le panel de recherche pour annuler
     * 
     * @param panel 
     */
    public void afficherPanelAnnuler(JPanel panel) {


        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à ajouter dans le bas
        JButton annuler = new JButton("ANNULER");
        panel.add(annuler, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user :");
        JRadioButton radio_prof = new JRadioButton("Enseignant");
        JRadioButton radio_eleve = new JRadioButton("Groupe");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radio_prof);
        bg.add(radio_eleve);
        JLabel text_user = new JLabel("");

        radio_prof.addActionListener((ActionEvent e) -> {
            if (radio_prof.isSelected()) {
                text_user.setText("Nom prof : ");
            }
        } //Si clique sur prof
        );

        radio_eleve.addActionListener((ActionEvent e) -> {
            if (radio_eleve.isSelected()) {
                text_user.setText("Nom groupe : ");
            }
        } //Si clique sur etudiant
        );
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80, 30));

        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[] = new String[52];


        for (int i = 0; i < 52; i++) //Initialisation des numéros de semaine
        {
            semaine[i] = "" + (int) (i + 1);

        }
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");

        chercher.addActionListener((ActionEvent e) -> {
            String semaine_str = (String) list_semaine.getSelectedItem();
            
            if (radio_prof.isSelected()) //Si prof
            {
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe
                
                if (existe == true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf(prof, int_semaine, centre); //On affiche l'edt du prof en question
                }
                
            } else if (radio_eleve.isSelected()) //Si etudiant
            {
                rechercher_groupe(field_nom.getText(), semaine_str); //On recherhce et controle s'il y a cet utilisateur
                String groupe_str = field_nom.getText();
                int id_groupe = groupeDao.idCelonNom(groupe_str);
                groupe = groupeDao.find(id_groupe);
                boolean existe = groupeDao.siExiste(groupe_str);
                if(existe == true)
                {
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineGroupe(groupe, int_semaine, centre); //On affiche l'edt du prof en question

                }
            }
            //tableau.removeMouseListener(tableau.getMouseListeners()[0]);
            for (MouseListener element : tableau.getMouseListeners()) tableau.removeMouseListener(element);
            
            tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                    int col = tableau.columnAtPoint(evt.getPoint());
                    
                    try
                    {
                        
                        if (stock_seances[row][col] != null) //Si il y a une séance dans la cellule
                        {
                            Date today = new Date(System.currentTimeMillis());
                            if (stock_seances[row][col].getDate().compareTo(today) > 0) //Si la séance selectionnée vennat après la date actuelle
                            {
                                System.out.println(stock_seances[row][col].getID());
                                JFrame frame = new JFrame();
                                JOptionPane.showConfirmDialog(frame, "Voulez-vous annuler cette séance : \n"
                                        + "Nom séance : " + stock_seances[row][col].getCours().getNom()
                                        + " Date séance : " + stock_seances[row][col].getDate());
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                majControleur.annulerSeance(stock_seances[row][col]);
                            }
                            else
                            {
                                JOptionPane stop = new JOptionPane();
                                stop.showMessageDialog(null, "Vous ne pouvez qu'annuler une séance après la date d'aujourd'hui", "ERREUR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane stop = new JOptionPane();
                            stop.showMessageDialog(null, "Aucune séance à annuler", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println("Erreur senace");
                    }
                    
                    
                    
                }
            });
        } //Si clique sur bouton chercher
        );

        haut.add(text_enlever);
        haut.add(radio_prof);
        haut.add(radio_eleve);
        haut.add(text_user);
        haut.add(field_nom);

        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);
    }

    /**Affiche le panel de recherche pour valider
     * 
     * @param panel 
     */
    public void afficherPanelValider(JPanel panel) {

        ///Panel centre on ajoute la grille dans le panel centre
        JPanel centre = new JPanel(new GridLayout(0, 1));
        this.afficherGrille(centre);
        panel.add(centre, BorderLayout.CENTER); //On ajoute le panel au centre

        ///Bouton à valider dans le bas
        JButton valider = new JButton("VALIDER");
        panel.add(valider, BorderLayout.SOUTH);

        ///Panel haut grid layout pour prendre toute la largeur
        JPanel haut = new JPanel(new FlowLayout());
        JLabel text_enlever = new JLabel("Veuillez choisir quel type d'user :");
        JRadioButton radio_prof = new JRadioButton("Enseignant");
        JRadioButton radio_eleve = new JRadioButton("Groupe");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radio_prof);
        bg.add(radio_eleve);
        JLabel text_user = new JLabel("");

        radio_prof.addActionListener((ActionEvent e) -> {
            if (radio_prof.isSelected()) {
                text_user.setText("Nom prof : ");
            }
        } //Si clique sur prof
        );

        radio_eleve.addActionListener((ActionEvent e) -> {
            if (radio_eleve.isSelected()) {
                text_user.setText("Nom groupe : ");
            }
        } //Si clique sur etudiant
        );
        JTextField field_nom = new JTextField();
        field_nom.setPreferredSize(new Dimension(80, 30));

        JLabel text_semaine = new JLabel("Veuillez choisir quelle semaine : ");
        String semaine[] = new String[52];


        for (int i = 0; i < 52; i++) //Initialisation des numéros de semaine
        {
            semaine[i] = "" + (int) (i + 1);

        }
        JComboBox list_semaine = new JComboBox(semaine);

        JButton chercher = new JButton("Chercher");

        chercher.addActionListener((ActionEvent e) -> {
            String semaine_str = (String) list_semaine.getSelectedItem();
            
            if (radio_prof.isSelected()) //Si prof
            {
                rechercher_utilisateur(field_nom.getText(), semaine_str, 1); //On recherhce et controle s'il y a cet utilisateur
                prof = profDao.trouverProfAvecNom(field_nom.getText()); //On instancie l'objet prof
                boolean existe = profDao.siExiste(field_nom.getText()); //On vérifie s'il existe
                
                if (existe == true)//S'il existe dans la bdd
                {
                    
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineProf(prof, int_semaine, centre); //On affiche l'edt du prof en question
                }
                
            } else if (radio_eleve.isSelected()) //Si etudiant
            {
                rechercher_groupe(field_nom.getText(), semaine_str); //On recherhce et controle s'il y a cet utilisateur
                String groupe_str = field_nom.getText();
                int id_groupe = groupeDao.idCelonNom(groupe_str);
                groupe = groupeDao.find(id_groupe);
                boolean existe = groupeDao.siExiste(groupe_str);
                if(existe == true)
                {
                    int int_semaine = Integer.valueOf(semaine_str); //Cast en int
                    afficherDateEdt(int_semaine);
                    afficherEdtSemaineGroupe(groupe, int_semaine, centre); //On affiche l'edt du prof en question

                }
            }
            
            for (MouseListener element : tableau.getMouseListeners()) tableau.removeMouseListener(element);
            
            tableau.addMouseListener(new java.awt.event.MouseAdapter() { //Si clique sur une cellule du tableau
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = tableau.rowAtPoint(evt.getPoint()); //Get les coord du click
                    int col = tableau.columnAtPoint(evt.getPoint());
                    
                    try
                    {
                        if (stock_seances[row][col] != null) //Si il y a une séance dans la cellule
                        {
                            Date today = new Date(System.currentTimeMillis());
                            if (stock_seances[row][col].getDate().compareTo(today) < 0 &&stock_seances[row][col].getEtat()!=2 ) //Si la séance selectionnée venaat après la date actuelle
                            {
                                System.out.println(stock_seances[row][col].getID());
                                JFrame frame = new JFrame();
                                JOptionPane.showConfirmDialog(frame, "Voulez-vous valider cette séance : \n"
                                        + "Nom séance : " + stock_seances[row][col].getCours().getNom()
                                        + " Date séance : " + stock_seances[row][col].getDate());
                                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                majControleur.validerSeance(stock_seances[row][col]);
                            }
                            else if(stock_seances[row][col].getEtat()==2)
                            {
                                JOptionPane stop = new JOptionPane();
                                stop.showMessageDialog(null, "Cette séance est déjà validée", "ERREUR", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane stop = new JOptionPane();
                                stop.showMessageDialog(null, "Vous ne pouvez valider qu'une seance une fois celle-ci terminée", "ERREUR", JOptionPane.ERROR_MESSAGE);
                            }
                            
                            
                        }
                        else
                        {
                            JOptionPane stop = new JOptionPane();
                            stop.showMessageDialog(null, "Aucune séance", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println("Erreur seance ");
                    }
                    
                    
                    
                }
            });
        } //Si clique sur bouton chercher
        );


        haut.add(text_enlever);
        haut.add(radio_prof);
        haut.add(radio_eleve);
        haut.add(text_user);
        haut.add(field_nom);

        haut.add(text_semaine);
        haut.add(list_semaine);
        haut.add(chercher);

        panel.add(haut, BorderLayout.NORTH);
    }

    /**Affiche le panel de recherche pour deplacer
     * 
     * @param panel 
     */
    public void afficherPanelDeplacer(JPanel panel) {
        JButton test = new JButton("deplacer");
        panel.add(test);
    }


    /////////////////////////////////////////////-------------------METHODES D'AFFICHAGE D'EDT--------------------////////////////////////////////////////////////////////

    /**Méthode qui va afficher l'edt d'une semaine avec la seance qui manque
     * soit un prof soit un gp soit soit une salle
     * @param seance_manquante
     * @param pan
     */
    public void afficherEdtManque(Seance seance_manquante, JPanel pan)
    {
        suppPanel(pan);
        ///Clear le tableau
        for (int i = 1; i < tableau.getRowCount(); i++) {
            for (int j = 1; j < tableau.getColumnCount(); j++) {
                tableau.getModel().setValueAt("", i, j);
            }
        }

        seanceDao = new SeanceDao();
        prof = new Enseignant();

        salle = new Salle();
        stock_seances = new Seance[tableau.getRowCount()][tableau.getColumnCount()];//Initialisation des tableaux de séances avec nb de lignes et colonne de la grille

        
            salle = etudiantDao.trouverSalle(seance_manquante);
            java.util.Date date = seance_manquante.getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
            for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                if (dayOfWeek == jour_semaine) {

                    String str = seance_manquante.getHeureDebut().toString();
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

                            prof = seanceDao.trouverEnseignant(seance_manquante);
                            groupe = seanceDao.trouverGroupe(seance_manquante);

                            switch (seance_manquante.getEtat()) {
                                case 0:
                                case 1:
                                    {
                                        String myString =
                                                "<html><p>"  +seance_manquante.getID()+ seance_manquante.getCours().getNom();
                                        if(seanceDao.siProf(seance_manquante)==false)
                                        {
                                            myString+="<br>Manque PROF</p></html>";
                                        }
                                        else if(seanceDao.siGroupe(seance_manquante)==false)
                                        {
                                            myString+="<br>Manque GROUPE</p></html>";
                                        }
                                        else if(seanceDao.siSalle(seance_manquante)==false)
                                        {
                                            myString+="<br>Manque SALLE</p></html>";
                                        }       tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = seance_manquante; //Ajout dan sles tableaux
                                        break;
                                    }
                                case 2:
                                    {
                                        String myString =
                                                "<html><p>" +seance_manquante.getID() + seance_manquante.getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Valide</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = seance_manquante; //Ajout dan sles tableaux
                                        break;
                                    }
                                case 3:
                                    {
                                        String myString =
                                                "<html><p>" +seance_manquante.getID() + seance_manquante.getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Annulé</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = seance_manquante; //Ajout dan sles tableaux
                                        break;
                                    }
                                default:
                                    break;
                            }
                        }
                    }

                }
            }

        
        pan.add(tableau);
    }
    
    /**Affiche edt selon prof
     * 
     * @param prof
     * @param semaine
     * @param pan 
     */
    public void afficherEdtSemaineProf(Enseignant prof, int semaine, JPanel pan) {

        suppPanel(pan);
        ///Clear le tableau
        for (int i = 1; i < tableau.getRowCount(); i++) {
            for (int j = 1; j < tableau.getColumnCount(); j++) {
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

                            switch (mes_seances.get(i).getEtat()) {
                                case 0:
                                case 1:
                                    {
                                        String myString =
                                                "<html><p>" + mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom() + "<br>Groupe :" +
                                                groupe.getNom();
                                        if(seanceDao.siProf(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque PROF</p></html>";
                                        }
                                        else if(seanceDao.siGroupe(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque GROUPE</p></html>";
                                        }
                                        else if(seanceDao.siSalle(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque SALLE</p></html>";
                                        }       tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 2:
                                    {
                                        String myString =
                                                "<html><p>" + mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom() + " "
                                                + mes_seances.get(i).getType().getNom() + "<br>Groupe :" +
                                                groupe.getNom()
                                                + "<br>Salle :" +
                                                salle.getNom() + " Site :" +
                                                salle.getSite().getNom() + "Valide</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 3:
                                    {
                                        String myString =
                                                "<html><p>" + mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom() + " "+ mes_seances.get(i).getType().getNom()+ "<br>Groupe :" +
                                                groupe.getNom()
                                                + "<br>Salle :" +
                                                salle.getNom() + " Site :" +
                                                salle.getSite().getNom() + " Annulé</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                default:
                                    break;
                            }


                        }
                    }

                }
            }

        }

        //pan.add(tableau.getTableHeader(),BorderLayout.PAGE_START);
        pan.add(tableau);
    }

    /**Affiche edt selon etudiant
     * 
     * @param etudiant
     * @param semaine
     * @param pan 
     */
    public void afficherEdtSemaineEleve(Etudiant etudiant, int semaine, JPanel pan) {
        suppPanel(pan);
        ///Clear le tableau
        for (int i = 1; i < tableau.getRowCount(); i++) {
            for (int j = 1; j < tableau.getColumnCount(); j++) {
                tableau.getModel().setValueAt("", i, j);
            }
        }

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
        stock_seances = new Seance[tableau.getRowCount()][tableau.getColumnCount()];//Initialisation des tableaux de séances avec nb de lignes et colonne de la grille

        for (int i = 0; i < mes_seances.size(); i++) //On parcourt toutes séances relatives à cet etudiant
        {
            salle = etudiantDao.trouverSalle(mes_seances.get(i));
            java.util.Date date = mes_seances.get(i).getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
            for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                if (dayOfWeek == jour_semaine) {

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
                            switch (mes_seances.get(i).getEtat()) {
                                case 0:
                                case 1:
                                    {
                                        String myString =
                                                "<html><p>"  +mes_seances.get(i).getID()+ mes_seances.get(i).getCours().getNom();
                                        if(seanceDao.siProf(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque PROF</p></html>";
                                        }
                                        else if(seanceDao.siGroupe(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque GROUPE</p></html>";
                                        }
                                        else if(seanceDao.siSalle(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque SALLE</p></html>";
                                        }       tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 2:
                                    {
                                        String myString =
                                                "<html><p>" +mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom()+ " "+ mes_seances.get(i).getType().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Valide</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 3:
                                    {
                                        String myString =
                                                "<html><p>" +mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom()+ " "+ mes_seances.get(i).getType().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Annulé</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                default:
                                    break;
                            }

                        }
                    }

                }
            }

        }
        pan.add(tableau);

    }

    /**Affiche edt selon groupe
     * 
     * @param groupe
     * @param semaine
     * @param pan 
     */
    public void afficherEdtSemaineGroupe(Groupe groupe, int semaine, JPanel pan) {
        suppPanel(pan);
        ///Clear le tableau
        for (int i = 1; i < tableau.getRowCount(); i++) {
            for (int j = 1; j < tableau.getColumnCount(); j++) {
                tableau.getModel().setValueAt("", i, j);
            }
        }
        //Récupération données groupe
        groupeDao = new GroupeDAO();
        groupe = groupeDao.find(groupe.getId());
        System.out.println(" Groupe :" + groupe.getNom() + " Promotion :" + groupe.getPromo().getAnnee());

        ///Affichage des séances relatives à cet eleve

        mes_id = new ArrayList();
        mes_id = groupeDao.trouverIdSeance(groupe);


        mes_seances = new ArrayList();
        mes_seances = groupeDao.trouverAllSeancesSemaine(groupe.getId(), semaine); //On recup toutes les  séances relatives à cet etudiant dans cette semaine

        seanceDao = new SeanceDao();
        prof = new Enseignant();

        salle = new Salle();
        stock_seances = new Seance[tableau.getRowCount()][tableau.getColumnCount()];//Initialisation des tableaux de séances avec nb de lignes et colonne de la grille

        for (int i = 0; i < mes_seances.size(); i++) //On parcourt toutes séances relatives à cet etudiant
        {
            salle = etudiantDao.trouverSalle(mes_seances.get(i));
            java.util.Date date = mes_seances.get(i).getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
            for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                if (dayOfWeek == jour_semaine) {

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
                            groupe = seanceDao.trouverGroupe(mes_seances.get(i));

                            switch (mes_seances.get(i).getEtat()) {
                                case 0:
                                case 1:
                                    {
                                        String myString =
                                                "<html><p>" +mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom();
                                        if(seanceDao.siProf(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque PROF</p></html>";
                                        }
                                        else if(seanceDao.siGroupe(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque GROUPE</p></html>";
                                        }
                                        else if(seanceDao.siSalle(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque SALLE</p></html>";
                                        }       tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 2:
                                    {
                                        String myString =
                                                "<html><p>"  +mes_seances.get(i).getID()+ mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Valide</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 3:
                                    {
                                        String myString =
                                                "<html><p>"  +mes_seances.get(i).getID()+ mes_seances.get(i).getCours().getNom() + "<br>Prof :" +
                                                prof.getNom() + "<br>Salle :" +
                                                salle.getNom() + "<br>Site :" +
                                                salle.getSite().getNom() + "Annulé</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                default:
                                    break;
                            }
                        }
                    }

                }
            }

        }
        pan.add(tableau);
    }

    /**Affiche edt selon salle
     * 
     * @param salle
     * @param semaine
     * @param pan 
     */
    public void afficherEdtSemaineSalle(Salle salle, int semaine, JPanel pan) {
        suppPanel(pan);
        ///Clear le tableau
        for (int i = 1; i < tableau.getRowCount(); i++) {
            for (int j = 1; j < tableau.getColumnCount(); j++) {
                tableau.getModel().setValueAt("", i, j);
            }
        }
        mes_seances = new ArrayList();
        mes_seances = salleDao.lesSeances(salle.getID(), semaine); //On recup toutes les  séances relatives à cet etudiant dans cette semaine

        seanceDao = new SeanceDao();
        prof = new Enseignant();

        stock_seances = new Seance[tableau.getRowCount()][tableau.getColumnCount()];//Initialisation des tableaux de séances avec nb de lignes et colonne de la grille

        System.out.println(mes_seances.size());
        for (int i = 0; i < mes_seances.size(); i++) //On parcourt toutes séances relatives à cet etudiant
        {
            java.util.Date date = mes_seances.get(i).getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
            for (int jour_semaine = 2; jour_semaine < 7; jour_semaine++) {
                if (dayOfWeek == jour_semaine) {
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
                            groupe = seanceDao.trouverGroupe(mes_seances.get(i));

                            switch (mes_seances.get(i).getEtat()) {
                                case 0:
                                case 1:
                                    {
                                        String myString =
                                                "<html><p>" +mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom();
                                        if(seanceDao.siProf(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque PROF</p></html>";
                                        }
                                        else if(seanceDao.siGroupe(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque GROUPE</p></html>";
                                        }
                                        else if(seanceDao.siSalle(mes_seances.get(i))==false)
                                        {
                                            myString+="<br>Manque SALLE</p></html>";
                                        }       tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 2:
                                    {
                                        String myString =
                                                "<html><p>" +mes_seances.get(i).getID() + mes_seances.get(i).getCours().getNom()
                                                + "<br>Prof :" +
                                                prof.getNom() + "<br>Groupe : " + groupe.getNom() + " Valide</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                case 3:
                                    {
                                        String myString =
                                                "<html><p>" +mes_seances.get(i).getID()+ mes_seances.get(i).getCours().getNom()
                                                + "<br>Prof :" +
                                                prof.getNom() + "<br>Groupe : " + groupe.getNom() + "Annulé</p></html>";
                                        tableau.getModel().setValueAt(myString, ligne_semaine, colonne_semaine);
                                        stock_seances[ligne_semaine][colonne_semaine] = mes_seances.get(i); //Ajout dan sles tableaux
                                        break;
                                    }
                                default:
                                    break;
                            }

                        }
                    }

                }
            }

        }
        pan.add(tableau);
    }

}
