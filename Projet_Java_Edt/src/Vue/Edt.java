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
    
    public Edt(Utilisateur user)
    {
        super("Votre emploi du temps - " + user.getNom().toUpperCase()+" "+user.getPrenom().toUpperCase());
        
        this.setSize(1400,800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermé
        
        menu.add(new JButton("Cours"));
        menu.add(new JButton("Rechercher"));
        menu.add(new JButton("Cours annulé(s)"));
        menu.add(new JButton("Reporting"));
      
        JPanel panel = (JPanel)this.getContentPane();
        panel.setLayout(new BorderLayout());
        
        panel.add(this.menu, BorderLayout.NORTH);
        
        String mesInfos = "HYPERPLANNING 2019-2020" ;
        
        info = new JLabel(mesInfos);
        panel.add(this.info, BorderLayout.SOUTH);
        
        JPanel grille_edt = new JPanel(new GridLayout(7,6,1,1));
        
        for(int i=1;i<8;i++)
        {
            grille_edt.add(new JLabel("8h-10h"));
            for(int j=1;j<7;j++)
            {
                grille_edt.add(new JLabel(String.valueOf(i*j),JLabel.CENTER));

            }
            
        }
        panel.add(grille_edt);
  
        this.setVisible(true);
        //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
        DAO<Utilisateur> etudiantDao = new EtudiantDao(); //********************
        Etudiant etudiant =(Etudiant) etudiantDao.find(user.getID());//*****************
        System.out.println(etudiant.getNumEtudiant());
        
        //Récupération données groupe
        DAO<Groupe> groupeDao = new GroupeDAO();
        Groupe groupe = groupeDao.find(etudiant.getIdGroupe());
        System.out.println(groupe.getNom());
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void update(Observable o,Object obj)
    {
        
    }
    
}
