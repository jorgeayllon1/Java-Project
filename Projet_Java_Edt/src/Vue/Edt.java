/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;


import Modele.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 *
 * @author Wang David
 */

//Il faudra la rendre abstraite plus tard 
public class Edt extends JFrame implements Observer, ActionListener{
    
    private final JToolBar menu = new JToolBar();
    private JLabel info;
    private Graphics ligneh = getGraphics();
    protected JPanel panel;
    private final JButton rechercher=new JButton("Rechercher");
    
    public Edt(){}
    public Edt(Utilisateur user)
    {
        super("Votre emploi du temps - " + user.getNom().toUpperCase()+" "+user.getPrenom().toUpperCase());
        
        this.setSize(1400,800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermé
        
        menu.add(new JButton("Cours"));
        //menu.add(new JButton("Rechercher"));
        menu.add(this.rechercher);
        menu.add(new JButton("Cours annulé(s)"));
        menu.add(new JButton("Reporting"));
      
        panel = (JPanel)this.getContentPane();
        panel.setLayout(new BorderLayout());
        
        panel.add(this.menu, BorderLayout.NORTH);
        
        String mesInfos = "HYPERPLANNING 2019-2020" ;
        
        info = new JLabel(mesInfos);
        panel.add(this.info, BorderLayout.SOUTH);

        this.setVisible(true);

        rechercher.addActionListener(this);
   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("je suis la");
    }
    
    public void update(Observable o,Object obj)
    {
        
    }
    
    public JPanel getPanel()
    {
        return this.panel;
    }
    
}
