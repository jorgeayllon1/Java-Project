
package Vue;

import Modele.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.*;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import org.jdatepicker.impl.UtilDateModel;

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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);

        Edt_Etudiant edt_etudiant = new Edt_Etudiant();

        JPanel content = new JPanel(new BorderLayout());
        JPanel haut = new JPanel(new FlowLayout());
        JPanel centre = new JPanel(new GridLayout(0,1));
        JTextArea mon_recap = new JTextArea();
        JScrollPane scroll = new JScrollPane(mon_recap);

        JLabel text_debut = new JLabel("Date début : ");
        JLabel text_fin = new JLabel("Date fin : ");
        UtilDateModel model = new UtilDateModel();

 

        
        /*UtilDateModel mon_modele = new UtilDateModel();
        Properties prop = new Properties();
        prop.put("text.today", "Auj");
        prop.put("text.month", "Mois");
        prop.put("text.year", "Annee");
        JDatePanelImpl debutPanel = new JDatePanelImpl(mon_modele,prop);
        JDatePickerImpl debutPicker = new JDatePickerImpl(debutPanel, new DateFormat());
        
        
        
        JDatePanelImpl finPanel = new JDatePanelImpl(mon_modele,prop);
        JDatePickerImpl finPicker = new JDatePickerImpl(finPanel, new DateFormat());

        voir_recap = new JButton("Chercher");
        
        
        

        java.sql.Date debut = (java.sql.Date) debutPicker.getModel().getValue();
        java.sql.Date fin = (java.sql.Date) finPicker.getModel().getValue();
        
        
        String str = debutPicker.getJFormattedTextField().getText();
        System.out.println(str);*/

        JTextField field_debut = new JTextField();
        field_debut.setPreferredSize(new Dimension(100,30));
        JTextField field_fin = new JTextField();
        field_fin.setPreferredSize(new Dimension(100,30));



        haut.add(text_debut);
        haut.add(field_debut);
        haut.add(text_fin);
        haut.add(field_fin);
        haut.add(voir_recap);
        this.voir_recap.addActionListener((ActionEvent e) -> {
            String str_debut = field_debut.getText();
            System.out.println(str_debut);
            Date debut = Date.valueOf(str_debut);
            System.out.println(debut);
            String str_fin = field_fin.getText();
            Date fin = Date.valueOf(str_fin);

            String donnees=edt_etudiant.voirrecap(debut, fin,etudiant);
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);

        Edt_Enseignant edt_enseignant = new Edt_Enseignant();

        JPanel content = new JPanel(new BorderLayout());
        JPanel haut = new JPanel(new FlowLayout());
        JPanel centre = new JPanel(new GridLayout(0,1));
        JTextArea mon_recap = new JTextArea();
        JScrollPane scroll = new JScrollPane(mon_recap);

        JLabel text_debut = new JLabel("Date début : ");
        JLabel text_fin = new JLabel("Date fin : ");
        UtilDateModel model = new UtilDateModel();



        JTextField field_debut = new JTextField();
        field_debut.setPreferredSize(new Dimension(100,30));
        JTextField field_fin = new JTextField();
        field_fin.setPreferredSize(new Dimension(100,30));



        haut.add(text_debut);
        haut.add(field_debut);
        haut.add(text_fin);
        haut.add(field_fin);
        haut.add(voir_recap);
        this.voir_recap.addActionListener((ActionEvent e) -> {
            String str_debut = field_debut.getText();
            System.out.println(str_debut);
            Date debut = Date.valueOf(str_debut);
            System.out.println(debut);
            String str_fin = field_fin.getText();
            Date fin = Date.valueOf(str_fin);

            String donnees=edt_enseignant.voirrecap("TD10",debut, fin,prof);
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
