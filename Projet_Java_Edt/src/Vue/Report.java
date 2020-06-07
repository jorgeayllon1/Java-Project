
package Vue;


import Modele.*;
import javax.swing.*;
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
public class Report extends JFrame{
    
    public Report(){}
    
    public Report(Etudiant etudiant)
    {
        super("Votre reporting");

        this.setSize(1000,800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        CoursDao coursDao = new CoursDao();

        for (int i = 1; i < coursDao.getTaille("cours"); i++) {
            String nom_cours = coursDao.find(i).getNom();
            int nombre_seance = coursDao.nombreDeSeance(i);
            dataset.setValue(nom_cours, nombre_seance);
        }

        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        GroupeDAO groupeDAO = new GroupeDAO();

        for (int i = 1; i < groupeDAO.getTaille("groupe"); i++) {
            String nom_groupe = groupeDAO.find(i).getNom();
            int nombre_seance = groupeDAO.nombreDeSeance(i);
            dataset1.setValue(nombre_seance, nom_groupe, "");
        }

        DefaultPieDataset dataset2 = new DefaultPieDataset();
        SiteDAO siteDAO = new SiteDAO();

        for (int i = 1; i < siteDAO.getTaille("site"); i++) {
            String nom_site = siteDAO.find(i).getNom();
            int capacite = siteDAO.capaciteTot(i);
            dataset2.setValue(nom_site, capacite);
        }

// create a chart...
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Taux de cours",
                dataset,
                true, // legend?
                true, // tooltips?
                false // URLs?
        );
        JFreeChart chart1 = ChartFactory.createBarChart("Nombre de cours par Seance", // chart title
                "Nom TD", // domain axis label
                "Nombre cours", // range axis label
                dataset1, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );
        JFreeChart chart2 = ChartFactory.createPieChart3D(
                "Capacite des Salles",
                dataset2,
                true, // legend?
                true, // tooltips?
                false // URLs?
        );


        ChartPanel panel = new ChartPanel(chart);
        this.add(panel);
        this.setVisible(true);
    }
    
    public Report(Enseignant prof)
    {
        super("Votre reporting");

        this.setSize(1000,800); //Taille
        this.setLocationRelativeTo(null); //Centre
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Stop run quand la dernière fenetre est fermée
        this.setResizable(false);
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        CoursDao coursDao = new CoursDao();

        for (int i = 1; i < coursDao.getTaille("cours"); i++) {
            String nom_cours = coursDao.find(i).getNom();
            int nombre_seance = coursDao.nombreDeSeance(i);
            dataset.setValue(nom_cours, nombre_seance);
        }

        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        GroupeDAO groupeDAO = new GroupeDAO();

        for (int i = 1; i < groupeDAO.getTaille("groupe"); i++) {
            String nom_groupe = groupeDAO.find(i).getNom();
            int nombre_seance = groupeDAO.nombreDeSeance(i);
            dataset1.setValue(nombre_seance, nom_groupe, "");
        }

        DefaultPieDataset dataset2 = new DefaultPieDataset();
        SiteDAO siteDAO = new SiteDAO();

        for (int i = 1; i < siteDAO.getTaille("site"); i++) {
            String nom_site = siteDAO.find(i).getNom();
            int capacite = siteDAO.capaciteTot(i);
            dataset2.setValue(nom_site, capacite);
        }

// create a chart...
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Taux de cours",
                dataset,
                true, // legend?
                true, // tooltips?
                false // URLs?
        );
        JFreeChart chart1 = ChartFactory.createBarChart("Nombre de cours par Seance", // chart title
                "Nom TD", // domain axis label
                "Nombre cours", // range axis label
                dataset1, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );
        JFreeChart chart2 = ChartFactory.createPieChart3D(
                "Capacite des Salles",
                dataset2,
                true, // legend?
                true, // tooltips?
                false // URLs?
        );


        ChartPanel panel = new ChartPanel(chart);
        this.add(panel);
        this.setVisible(true);
    }
    
    
    
    
    
}