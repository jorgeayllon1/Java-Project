
package Vue;

import Modele.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.UtilDateModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Wang David
 */
public class Recap extends JFrame implements ActionListener{

    JButton voir_recap = new JButton("Chercher");

    public Recap(){}

    
    /**Constructeur pour le recap d'un etudiant 
     * 
     * @param etudiant 
     */

    public Recap(Etudiant etudiant)
    {
        super("Votre récapitulatif de cours");

        this.setSize(800,600); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);

        Edt_Etudiant edt_etudiant = new Edt_Etudiant();

        JPanel content = new JPanel(new BorderLayout());
        JPanel haut = new JPanel(new FlowLayout());
        JPanel centre = new JPanel(new GridLayout(0,1));
        JTextPane mon_recap = new JTextPane();
        JScrollPane scroll = new JScrollPane(mon_recap);
        
        StyledDocument d = mon_recap.getStyledDocument();
        SimpleAttributeSet au_milieu = new SimpleAttributeSet();
        StyleConstants.setAlignment(au_milieu, StyleConstants.ALIGN_CENTER);
        d.setParagraphAttributes(0, d.getLength(), au_milieu, false);

        JLabel text_debut = new JLabel("Date début : ");
        JLabel text_fin = new JLabel("Date fin : ");

        
        JSpinner debutSpinner = new JSpinner(new SpinnerDateModel()); 
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(debutSpinner, "E dd/MM/yyyy"); 
        debutSpinner.setEditor(dateEditor); 
        JSpinner finSpinner = new JSpinner(new SpinnerDateModel()); 
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(finSpinner, "E dd/MM/yyyy"); 
        finSpinner.setEditor(dateEditor2); 


        JTextField field_debut = new JTextField();
        field_debut.setPreferredSize(new Dimension(100,30));
        JTextField field_fin = new JTextField();
        field_fin.setPreferredSize(new Dimension(100,30));

        haut.add(text_debut);
        haut.add(debutSpinner);
        
        haut.add(text_fin);
        haut.add(finSpinner);

        haut.add(voir_recap);
        this.voir_recap.addActionListener((ActionEvent e) -> {
            
            java.util.Date date = (java.util.Date)debutSpinner.getValue();   
            java.sql.Date begin = new java.sql.Date(date.getTime());

            
            java.util.Date date2 = (java.util.Date)debutSpinner.getValue();   
            java.sql.Date end = new java.sql.Date(date2.getTime());



            String donnees=edt_etudiant.voirrecap(begin, end,etudiant);
            mon_recap.setText(donnees);
        });

        centre.add(scroll);

        content.add(haut,BorderLayout.NORTH);
        content.add(centre, BorderLayout.CENTER);
        this.add(content);

        this.setVisible(true);
    }

    /**Constructeur pour le recap d'un prof
     * 
     * @param prof 
     */

    public Recap(Enseignant prof)
    {
        super("Votre récapitulatif de cours");

        this.setSize(800,600); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);

        Edt_Enseignant edt_enseignant = new Edt_Enseignant();

        JPanel content = new JPanel(new BorderLayout());
        JPanel haut = new JPanel(new FlowLayout());
        JPanel centre = new JPanel(new GridLayout(0,1));
        JTextPane mon_recap = new JTextPane();
        JScrollPane scroll = new JScrollPane(mon_recap);
        
        StyledDocument d = mon_recap.getStyledDocument();
        SimpleAttributeSet au_milieu = new SimpleAttributeSet();
        StyleConstants.setAlignment(au_milieu, StyleConstants.ALIGN_CENTER);
        d.setParagraphAttributes(0, d.getLength(), au_milieu, false);

        JLabel text_debut = new JLabel("Date début : ");
        JLabel text_fin = new JLabel("Date fin : ");
        JSpinner debutSpinner = new JSpinner(new SpinnerDateModel()); 
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(debutSpinner, "E dd/MM/yyyy"); 
        debutSpinner.setEditor(dateEditor); 
        JSpinner finSpinner = new JSpinner(new SpinnerDateModel()); 
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(finSpinner, "E dd/MM/yyyy"); 
        finSpinner.setEditor(dateEditor2); 

        
        JLabel text_gp = new JLabel("Nom du groupe :");
        JTextField field_groupe = new JTextField();
        field_groupe.setPreferredSize(new Dimension(100,30));

        haut.add(text_debut);
        haut.add(debutSpinner);
        
        haut.add(text_fin);
        haut.add(finSpinner);
        
        haut.add(text_gp);
        haut.add(field_groupe);

        haut.add(voir_recap);
        this.voir_recap.addActionListener((ActionEvent e) -> {
            String td = field_groupe.getText();
            java.util.Date date = (java.util.Date)debutSpinner.getValue();   
            java.sql.Date begin = new java.sql.Date(date.getTime());

            
            java.util.Date date2 = (java.util.Date)debutSpinner.getValue();   
            java.sql.Date end = new java.sql.Date(date2.getTime());

            String donnees=edt_enseignant.voirrecap(td,begin,end,prof);
            mon_recap.setText(donnees);
        });



        centre.add(scroll);

        content.add(haut,BorderLayout.NORTH);
        content.add(centre, BorderLayout.CENTER);
        this.add(content);


        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }



}
