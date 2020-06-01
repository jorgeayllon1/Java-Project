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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * @author Wang David
 */

//Il faudra la rendre abstraite plus tard 
public class Edt extends JFrame implements ActionListener {

    protected final JToolBar menu = new JToolBar();
    protected final JToolBar semaine = new JToolBar(JToolBar.CENTER);
    protected JLabel info;
    protected Graphics ligneh = getGraphics();
    protected JPanel panel;
    protected JButton rechercher = new JButton("Rechercher");
    protected JButton annule = new JButton("Cours annulé(s)");
    protected JButton summary = new JButton("Récapitulatif des cours");
    protected JButton mes_cours = new JButton("Cours");
    protected JButton report = new JButton("Reporting");
    protected RechercheControleur control_recherche;
    protected int num_semaine;
    protected ArrayList<JButton> week_button;


    public Edt() {
    }

    public Edt(Utilisateur user) {
        super("Votre emploi du temps - " + user.getNom().toUpperCase() + " " + user.getPrenom().toUpperCase());

        this.setSize(1400, 800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermé
        infoBase();


        


    }
    
    public void infoBase()
    {
        this.menu.setBackground(new java.awt.Color(179, 204, 204));
        this.menu.setOpaque(true);
        
        this.semaine.setBackground(new java.awt.Color(224, 235, 235));
        this.semaine.setOpaque(true);
        
  
        ImageIcon cours_icon = new ImageIcon(new ImageIcon("src/Icones/book.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        mes_cours = new JButton("Cours", cours_icon);
        mes_cours.setPreferredSize(new Dimension(150,50));
        Font f = mes_cours.getFont();
        mes_cours.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        menu.add(this.mes_cours);

        ImageIcon search_icon = new ImageIcon(new ImageIcon("src/Icones/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        rechercher = new JButton("Rechercher", search_icon);
        rechercher.setPreferredSize(new Dimension(150,50));
        f = rechercher.getFont();
        rechercher.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        menu.add(this.rechercher);

        ImageIcon delete_icon = new ImageIcon(new ImageIcon("src/Icones/quit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.annule = new JButton("Cours annulé(s)", delete_icon);
        annule.setPreferredSize(new Dimension(150,50));
         f = annule.getFont();
        annule.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        menu.add(this.annule);

        ImageIcon report_icon = new ImageIcon(new ImageIcon("src/Icones/report.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.report =  new JButton("Reporting", report_icon);
        f = report.getFont();
        report.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        report.setPreferredSize(new Dimension(150,50));
        menu.add(report);

        ImageIcon recap_icon = new ImageIcon(new ImageIcon("src/Icones/news-admin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.summary = new JButton("Récapitulatif des cours", recap_icon);
        f = summary.getFont();
        summary.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        summary.setPreferredSize(new Dimension(200,50));
        menu.add(this.summary);

        panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());
        this.panel.setBackground(new java.awt.Color(112, 219, 219));
        this.panel.setOpaque(true);

        ///Affiche le numéro de semaine
        Calendar cal = new GregorianCalendar();
        Date d = new Date();
        cal.setTime(d);
        System.out.println("Week number:" +
                cal.get(Calendar.WEEK_OF_YEAR));

        this.num_semaine = cal.get(Calendar.WEEK_OF_YEAR);

        JLabel week = new JLabel("SEMAINE");
        f = week.getFont();
        week.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        semaine.add(week);
        this.week_button = new ArrayList();
        for (int i = 0; i < 52; i++) {
            week_button.add(new JButton(Integer.toString(i + 1)));
            semaine.add(week_button.get(i));
            if (i == num_semaine) {
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

    public void update(Observable o, Object obj) {

    }

    public JPanel getPanel() {
        return this.panel;
    }

}
