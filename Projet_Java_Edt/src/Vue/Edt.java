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
import javax.swing.table.TableColumn;


/**
 * @author Wang David
 */
public class Edt extends JFrame implements ActionListener {

    protected final JToolBar menu = new JToolBar();
    protected final JToolBar semaine = new JToolBar(JToolBar.CENTER);
    
    protected JLabel info;
    protected JPanel panel = new JPanel();
    protected JPanel panel_edt = new JPanel(new GridLayout(0, 1));

    JPanel content = null;
    
    protected JButton rechercher = new JButton("Rechercher");
    protected JButton annule = new JButton("Cours annulé(s)");
    protected JButton summary = new JButton("Récapitulatif des cours");
    protected JButton mes_cours = new JButton("Cours");
    protected JButton report = new JButton("Reporting");
    
    protected int num_semaine;
    protected ArrayList<JButton> week_button;

    protected Font f = null;
    
    protected JLabel recup_info=null;
    protected JTable tableau;
    protected JScrollPane scroll;


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

        //panel.add(toolbars, BorderLayout.NORTH);
        this.add(toolbars, BorderLayout.NORTH);

        String mesInfos = "HYPERPLANNING 2019-2020";
        
        info = new JLabel(mesInfos);
        info.setPreferredSize(new Dimension(20,50));
        //panel.add(info, BorderLayout.SOUTH);
        this.add(info, BorderLayout.SOUTH);

        //panel = (JPanel) this.getContentPane();
        //panel.setLayout(new BorderLayout());
        this.add(panel);
        panel.setLayout(new OverlayLayout(panel));
        this.panel.setBackground(new java.awt.Color(112, 219, 219));
        this.panel.setOpaque(true);

        

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
    
        public void afficherGrille()
        {
            Object[][] data =
            {
                {"Horaires", "Lundi", "Mardi","Mercredi","Jeudi","Vendredi","Samedi"},
                {"8h-10h", "", "","","","",""},
                {"10h-12h", "", "","","","",""},
                {"12h-14h", "", "","","","",""},
                {"14h-16h", "", "","","","",""},
                {"16h-18h", "", "","","","",""},
                {"18h-20h", "", "","","","",""}
            };

        String  title[] = {"Horaires", "Lundi", "Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
        
        tableau = new JTable(data, title);
        tableau.setRowHeight(90);
        tableau.setDefaultRenderer(Object.class, new CaseLabel());
        
        tableau.setShowGrid(true);

        
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
