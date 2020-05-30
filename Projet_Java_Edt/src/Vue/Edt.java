/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;


import Controlleur.Recherche.RechercheControleur;
import Modele.*;

import java.awt.*;
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
    protected  JButton rechercher = new JButton("Rechercher");
    protected  JButton annule = new JButton("Cours annulé(s)");
    protected  JButton recap = new JButton("Récapitulatif des cours");
    protected RechercheControleur control_recherche;
    protected int num_semaine;


    public Edt() {
    }

    public Edt(Utilisateur user) {
        super("Votre emploi du temps - " + user.getNom().toUpperCase() + " " + user.getPrenom().toUpperCase());

        this.setSize(1400, 800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermé

        
        ImageIcon cours_icon =  new ImageIcon(new ImageIcon("src/Icones/book.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menu.add(new JButton("Cours", cours_icon));
        
        ImageIcon search_icon =  new ImageIcon(new ImageIcon("src/Icones/search.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        rechercher = new JButton("Rechercher",search_icon);
        menu.add(this.rechercher);
        
        ImageIcon delete_icon =  new ImageIcon(new ImageIcon("src/Icones/quit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.annule = new JButton("Cours annulé(s)", delete_icon);
        menu.add(this.annule);
        
        ImageIcon report_icon =  new ImageIcon(new ImageIcon("src/Icones/report.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menu.add(new JButton("Reporting",report_icon));
        
        ImageIcon recap_icon =  new ImageIcon(new ImageIcon("src/Icones/news-admin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.recap = new JButton("Récapitulatif des cours", recap_icon);
        menu.add(this.recap);

        panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());
     
        ///Affiche le numéro de semaine
        Calendar cal = new GregorianCalendar();
        Date d = new Date();   
        cal.setTime(d);     
        System.out.println("Week number:" + 
        cal.get(Calendar.WEEK_OF_YEAR));
        
        this.num_semaine= cal.get(Calendar.WEEK_OF_YEAR);
        
        
        JLabel week = new JLabel("SEMAINE");
        semaine.add(week);
        ArrayList<JButton> week_button=new ArrayList();
        for(int i=0;i<52;i++)
        {
            week_button.add(new JButton(Integer.toString(i+1)));
            semaine.add(week_button.get(i));
            if(i==num_semaine)
            {
                week_button.get(i-1).setBackground(Color.red);
                week_button.get(i-1).setOpaque(true);
            }
        }
        JPanel toolbars = new JPanel( new GridLayout(0, 1) );
        toolbars.add(menu);
        toolbars.add(semaine);
        
        panel.add(toolbars, BorderLayout.NORTH);

        String mesInfos = "HYPERPLANNING 2019-2020";

        info = new JLabel(mesInfos);
        panel.add(this.info, BorderLayout.SOUTH);

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
