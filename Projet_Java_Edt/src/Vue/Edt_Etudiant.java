/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Wang David
 */
public class Edt_Etudiant extends Edt{
    
    public Edt_Etudiant(){}
    
    public Edt_Etudiant(Utilisateur user, Etudiant etudiant)
    {
        
        super(user);
        
        
       //On cree un nouveau etudiant avec l'id de l'utilisateur car id_utilisateur clé etrangere dans etudiant
        EtudiantDao etudiantDao = new EtudiantDao(); //********************
        etudiant =(Etudiant) etudiantDao.find(etudiant.getID());//*****************
        System.out.print("Numero etudiant :"+etudiant.getNumEtudiant());
        
        //Récupération données groupe
        GroupeDAO groupeDao = new GroupeDAO();
        Groupe groupe = groupeDao.find(etudiant.getGroupe().getId());
        System.out.println(" Groupe :" +groupe.getNom() + " Promotion :" + groupe.getPromo().getAnnee());
        
        ///Affichage des séances relatives à cet eleve
        
        ArrayList<Integer> mes_id = new ArrayList();
        mes_id = groupeDao.trouverIdSeance(groupe);
        
        
        ArrayList<Seance> mes_seances = new ArrayList();
        mes_seances = groupeDao.trouverAllSeances(mes_id);
        
        System.out.println("Nombre de séances prevues pour cet eleve : " +mes_seances.size());
        Salle salle =new Salle();
        for(int i=0;i<mes_seances.size();i++)
        {
            System.out.println("Mes seances: \n"
                    +"Intitule du cours : " + mes_seances.get(i).getCours().getNom()
                    +"\n date : " +mes_seances.get(i).getDate()
                    +"\nheure debut : "  + mes_seances.get(i).getHeureDebut()
                    +"\nheure fin : " +mes_seances.get(i).getHeureFin()
                    +"\nType :" + mes_seances.get(i).getType().getNom());
            salle = etudiantDao.trouverSalle(mes_seances.get(i));
            System.out.println("Salle : " + salle.getNom() + " Capacite : " + salle.getCapacite() + " Site : " + salle.getSite().getNom()  );
        }
        
        JPanel grille_edt = new JPanel(new GridBagLayout());
        
        GridBagConstraints grille = new GridBagConstraints();
        grille.fill = GridBagConstraints.HORIZONTAL;
        grille.gridx=0;
        grille.gridy=0;
        grille.weightx=0.1;
        grille.weighty=0.1;
        
        grille_edt.add(new JLabel("Heures "),grille);
        
        grille.gridx=1;
        grille.gridy=0;
        grille.weightx=0.15;
        grille.weighty=0.1;
        grille_edt.add(new JLabel("Lundi "),grille);
        
        grille.gridx=2;
        grille.gridy=0;
        grille.weightx=0.1;
        grille.weighty=0.1;
        grille_edt.add(new JLabel("Mardi "),grille);
        
        grille.gridx=3;
        grille.gridy=0;
        grille.weightx=0.1;
        grille.weighty=0.1;
        grille_edt.add(new JLabel("Mercredi "),grille);
        
        grille.gridx=4;
        grille.gridy=0;
        grille.weightx=0.1;
        grille.weighty=0.1;
        grille_edt.add(new JLabel("jeudi "),grille);
        
        grille.gridx=5;
        grille.gridy=0;
        grille.weightx=0.1;
        grille.weighty=0.1;
        grille_edt.add(new JLabel("Vendredi "),grille);
        
        grille.gridx=6;
        grille.gridy=0;
        grille.weightx=0.1;
        grille.weighty=0.1;
        grille_edt.add(new JLabel("Samedi "),grille);
        
        int j=8;
        int k =j+2;
        for(int i=0;i<6;i++)
        {
            
            grille.gridx=0;
            grille.gridy=i+1;
            grille.weightx=0.1;
            grille.weighty=0.15;
            grille_edt.add(new JLabel(j+"H-" +k+"H"),grille);
            j+=2;
            k+=2;
        }
        
        /*Date date = new Date(mes_seances.get(1).getHeureDebut().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("h");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);  //1 sunday 2 monday 3 tuesday...
        System.out.println(dayOfWeek);   */
      
        for(int i=0;i<mes_seances.size();i++) //On parcourt toutes séances relatives à cet etudiant
        {
            if(mes_seances.get(i).getSemaine()==this.num_semaine)//Si il y a un cours dans la semaine actuelle
            {
                    Date date = mes_seances.get(i).getDate();
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK); //On get le jour de la semaine 1 sunday 2 monday 3 tuesday...
                    for(int jour_semaine=2;jour_semaine<7;jour_semaine++)
                    {
                            if(dayOfWeek==jour_semaine) //Si c un vendredi
                            {                       
                            /*SimpleDateFormat sdf = new SimpleDateFormat("h");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String str = sdf.format(mes_seances.get(i).getHeureDebut()); //On stocke dans un string l'entier de l'heure de début*/
                            String str = mes_seances.get(i).getHeureDebut().toString();
                            char str2 = str.charAt(11);
                            char str3 = str.charAt(12);
                            StringBuilder str4 = new StringBuilder();
                                if(str2=='0')
                                {
                                    str4.append(str3);
                                }
                                else
                                {
                                    str4.append(str2).append(str3);
                                }

                                int n=0;
                                String heure="";
                                for(int m=0;m<7;m++)
                                {
                                    if(m==0)
                                    {
                                        heure = Integer.toString(m+8+n);
                                    }
                                    else
                                    {
                                        if((m+n)%2==0)
                                        {
                                            n+=2;
                                            heure = Integer.toString(m+8+n);
                                        }
                                        else if((m+n)%2!=0)
                                        {
                                            n++;
                                            heure = Integer.toString(m+8+n);        
                                        }
                                    }

                                    grille.gridx=jour_semaine-1;
                                    if(str4.toString().equals( heure )) //Si ca commence à 10h
                                    {
                                        if(heure.contains("8"))
                                            grille.gridy=1;
                                        if(heure.contains("10"))
                                            grille.gridy=2;                             
                                        if(heure.contains("12"))
                                           grille.gridy=3;
                                        if(heure.contains("14"))
                                            grille.gridy=4;
                                        if(heure.contains("16"))
                                            grille.gridy=5;
                                        if(heure.contains("18"))
                                            grille.gridy=6;
                                        if(heure.contains("20"))
                                            grille.gridy=7;

                                        grille_edt.add(new JLabel(mes_seances.get(i).getCours().getNom()+ "\n"+salle.getNom()+"\n"+salle.getSite().getNom()),grille);
                                    }
                            }

                        }
                    }
                    
            }
        }
        
        
        this.panel.add(grille_edt);
  
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }
    
    
    
}
