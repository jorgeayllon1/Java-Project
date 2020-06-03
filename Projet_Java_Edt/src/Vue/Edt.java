/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;
import Controlleur.Recherche.RechercheControleur;
import Modele.*;

import java.awt.*;
import static java.awt.Color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

/**
 * @author Wang David
 */
public class Edt extends JFrame implements ActionListener {

    protected final JToolBar menu = new JToolBar();
    protected final JToolBar semaine = new JToolBar(JToolBar.CENTER);
    
    protected JLabel info;
    
    protected Graphics ligneh = getGraphics();
    protected JPanel panel;
    protected JPanel grille_edt = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel content = null;
    
    protected JButton rechercher = new JButton("Rechercher");
    protected JButton annule = new JButton("Cours annulé(s)");
    protected JButton summary = new JButton("Récapitulatif des cours");
    protected JButton mes_cours = new JButton("Cours");
    protected JButton report = new JButton("Reporting");
    
    protected int num_semaine;
    protected ArrayList<JButton> week_button;
    
    protected GridBagConstraints grille = new GridBagConstraints();
    protected Font f = null;
    
    protected JLabel recup_info=null;


    public Edt() {
    }

    public Edt(Utilisateur user) {
        super("Votre emploi du temps - " + user.getNom().toUpperCase() + " " + user.getPrenom().toUpperCase());

        this.setSize(1415, 805); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermé
        infoBase();

    }
    
    ///Affichage menu de base (cours, recherche, reporting...)
    public void infoBase()
    {
        this.menu.setBackground(new java.awt.Color(179, 204, 204));
        this.menu.setOpaque(true);
        
        this.semaine.setBackground(new java.awt.Color(224, 235, 235));
        this.semaine.setOpaque(true);
        
  
        ImageIcon cours_icon = new ImageIcon(new ImageIcon("src/Icones/book.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        mes_cours = new JButton("Cours", cours_icon);
        this.iconFont(mes_cours);

        ImageIcon search_icon = new ImageIcon(new ImageIcon("src/Icones/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        rechercher = new JButton("Rechercher", search_icon);
        this.iconFont(rechercher);

        ImageIcon delete_icon = new ImageIcon(new ImageIcon("src/Icones/quit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.annule = new JButton("Cours annulé(s)", delete_icon);
        this.iconFont(annule);

        ImageIcon report_icon = new ImageIcon(new ImageIcon("src/Icones/report.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.report =  new JButton("Reporting", report_icon);
        this.iconFont(report);

        ImageIcon recap_icon = new ImageIcon(new ImageIcon("src/Icones/news-admin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.summary = new JButton("Récapitulatif des cours", recap_icon);
        this.iconFont(summary);

        panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());
        this.panel.setBackground(new java.awt.Color(112, 219, 219));
        this.panel.setOpaque(true);

        ///Affiche le numéro de semaine
        Calendar cal = new GregorianCalendar();
        Date d = new Date();
        cal.setTime(d);
        System.out.println("Week number:" + cal.get(Calendar.WEEK_OF_YEAR));
        this.num_semaine = cal.get(Calendar.WEEK_OF_YEAR); //Numéro de semaine actuel

        JLabel week = new JLabel("SEMAINE");
        f = week.getFont();
        week.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        semaine.add(week);
        this.week_button = new ArrayList();
        //On ajoute les 52 boutons pour chaque numéro de semaine
        for (int i = 0; i < 52; i++) {
            week_button.add(new JButton(Integer.toString(i + 1))); //Dans la arrayList
            semaine.add(week_button.get(i)); //Dans la toolbar
            if (i == num_semaine) { //Si égal à la semaine actuelle
                week_button.get(i - 1).setBackground(Color.red);
                week_button.get(i - 1).setOpaque(true);
            }
        }
        JPanel toolbars = new JPanel(new GridLayout(0, 1));
        toolbars.add(menu);
        toolbars.add(semaine);

        panel.add(toolbars, BorderLayout.NORTH);

        String mesInfos = "HYPERPLANNING 2019-2020";
        
        info = new JLabel(mesInfos);
        info.setPreferredSize(new Dimension(20,50));
        panel.add(info, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    
    
    //Méthode de personnalisation font
    public void iconFont(JButton j)
    {
        j.setPreferredSize(new Dimension(200,50));
        Font f = mes_cours.getFont();
        j.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        menu.add(j);
    }

    //Getter panel
    public JPanel getPanel() {
        return this.panel;
    }
    
    //Méthode affichage grille edt
    public void afficherGrille()
    {
        grille_edt = new JPanel(new GridBagLayout()){protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y=145;
        g.drawLine(70,0, 70, 800);
        g.drawLine(0, 55, 1400, 55);
        for(int j=y;j<805;j+=95)
        {
            g.drawLine(0, j, 1415, j);
        }
        int x=310;
        for(int i=x;i<1415;i+=220)
        {
            g.drawLine(i, 0, i, 805);
        }
        
        
        }};

        grille = new GridBagConstraints();

        grille.fill = GridBagConstraints.HORIZONTAL;
        grille.gridx = 0;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        
        

        grille_edt.add(new JLabel("Heures "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(65, 95);
                                    }},  grille);
        

        grille.gridx = 1;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        
        
        grille_edt.add(new JLabel("Lundi "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(115, 95);
                                    }}, grille);

        grille.gridx = 2;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        grille_edt.add(new JLabel("Mardi "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(115, 95);
                                    }}, grille);

        grille.gridx = 3;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        grille_edt.add(new JLabel("Mercredi "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(115, 95);
                                    }}, grille);

        grille.gridx = 4;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        grille_edt.add(new JLabel("jeudi "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(115, 95);
                                    }}, grille);

        grille.gridx = 5;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        grille_edt.add(new JLabel("Vendredi "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(115, 95);
                                    }}, grille);

        grille.gridx = 6;
        grille.gridy = 0;
        grille.weightx = 1;
        grille.weighty = 1;
        grille_edt.add(new JLabel("Samedi "){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(115, 95);
                                    }}, grille);

        int j = 8;
        int k = j + 2;
        for (int i = 0; i < 6; i++) {

            grille.gridx = 0;
            grille.gridy = i+1;
            grille.weightx = 1;
            grille.weighty = 1;
            grille_edt.add(new JLabel(j + "H-" + k + "H"){@Override
                                    public Dimension getPreferredSize() {
                                        return new Dimension(65, 95);
                                    }}, grille);
            j += 2;
            k += 2;
        }
    }
    
    
    
    //Méthode de recherche selon nom user
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
                
                String s = "<html><p>Les informations de la personne sont :\n" +  "<br>" +leusersouhaiter.getID() + " " + leusersouhaiter.getNom() + " " + leusersouhaiter.getPrenom()
                        + " " + leusersouhaiter.getMail() + " " + leusersouhaiter.getDroit() + "<br>" + "Son emploi du temps pour la semaine "+semaine+" est le suivant : <br> ";
                for (Seance uneseance :
                        lesSeances) {
                    System.out.println(uneseance.getID() + " " + uneseance.getSemaine() + " " + uneseance.getDate().toString() + " " + uneseance.getHeureDebut()
                            + " " + uneseance.getHeureFin() + " " + uneseance.getCours().getNom() + " " + uneseance.getType().getNom());
                    s+=uneseance.getID() + " " + uneseance.getSemaine() + " " + uneseance.getDate().toString() + " " + uneseance.getHeureDebut()
                            + " " + uneseance.getHeureFin() + " " + uneseance.getCours().getNom() + " " + uneseance.getType().getNom() +"<br>";
                }
                s+="</p></html>";
                
                recup_info.setText(s);
            } else System.out.println("Accés non autorisé");
        } else
            System.out.println("Personne non trouvé dans la BDD : " + nom);

    }

}
