package Vue;

import Modele.CoursDao;
import Modele.SalleDAO;
import Modele.SeanceDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

class NotesJframe {
    /**
     * The starting point for the demo.
     *
     * @param args ignored.
     */
    public static void main(String[] args) {
        // create a dataset...
        DefaultPieDataset dataset = new DefaultPieDataset();
/*
        SeanceDao seanceDao = new SeanceDao();

        for (int i = 1; i < seanceDao.getTaille("seance"); i++) {
            String id_seance = String.valueOf(seanceDao.find(i).getID());
            int nombreEleve = seanceDao.nombreEleve(seanceDao.find(i));
            dataset.setValue(id_seance, nombreEleve);
        }

 */
        CoursDao coursDao = new CoursDao();

        for (int i = 1; i < coursDao.getTaille("cours"); i++) {
            String nom_cours = coursDao.find(i).getNom();
            int nombre_seance = coursDao.nombreDeSeance(i);
            dataset.setValue(nom_cours, nombre_seance);
        }

// create a chart...
        JFreeChart chart = ChartFactory.createPieChart3D(
                "Nombre de Seance par Cours",
                dataset,
                true, // legend?
                true, // tooltips?
                false // URLs?
        );
// create and display a frame...
        ChartFrame frame = new ChartFrame("First", chart);
        frame.pack();
        frame.setVisible(true);
    }
}


