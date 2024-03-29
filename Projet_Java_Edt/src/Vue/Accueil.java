
package Vue;

import java.awt.*;
import javax.swing.*;
import Modele.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import Controlleur.*;
import java.util.*;

/**
 *
 * @author Wang David
 */

/** Page connexion*/
public class Accueil extends JFrame implements ActionListener {
      
    private final JButton connexion;
    private final JTextField id;
    private final JPasswordField mdp;
    private final JLabel label_id,label_mdp;
    private AccueilControleur control_accueil;
    Utilisateur user = new Utilisateur();
    
    /**Construcetur initialisant les objets graphiques*/
    public Accueil()
    {
       
        super("Connectez-vous");

        this.setSize(400,400); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);
        
        JPanel panel = (JPanel)this.getContentPane();
        
        panel.setLayout(new GridLayout(3,1,20,20));
        
        this.id = new JTextField();
        id.setPreferredSize(new Dimension(150,30));
        this.mdp = new JPasswordField();
        mdp.setPreferredSize(new Dimension(120,30));
        ImageIcon login_icon = new ImageIcon(new ImageIcon("src/Icones/login.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        this.connexion = new JButton("Se connecter",login_icon);
        this.connexion.setPreferredSize(new Dimension(150,50));
        this.label_id = new JLabel("Mail :");
        this.label_mdp = new JLabel("Mot de Passe :");
        
        
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        panel1.add(label_id);
        panel1.add(id);
        
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        panel2.add(label_mdp);
        panel2.add(mdp);
        
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
        panel3.add(connexion);
        
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        
        connexion.addActionListener(this);  //Si on clique on va vers le controleur AccueilControleur

        this.setVisible(true); //Affichage
         
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.control_accueil = new AccueilControleur(); //Création objet
        this.control_accueil.control_accueil(this.id.getText(),this.mdp.getText()); //On envoie les données des champs remplies ou pas
           
    }
    


}
