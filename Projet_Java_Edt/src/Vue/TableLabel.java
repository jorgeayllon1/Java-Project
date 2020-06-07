
package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wang David
 */
public class TableLabel extends DefaultTableModel{
    
    
    
    Object[][] ligne = {{new JLabel("8h-10h"), "", "","","","",""},
                      {new JLabel("10h-12h"), "", "","","","",""},
                      {new JLabel("12h-14h"), "", "","","","",""},
                      {new JLabel("14h-16h"), "", "","","","",""},
                      {new JLabel("16h-18h"), "", "","","","",""},
                      {new JLabel("18h-20h"), "", "","","","",""},
            
    };
 
    Object[] colonne = {"Horaires", "Lundi", "Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
 
    public TableLabel (){
 

        for(Object c: colonne)
            this.addColumn(c);
 

        for(Object[] l: ligne)
            addRow(l);
 
    }
 
    @Override
 
    public Class getColumnClass(int columnIndex) {
        if(columnIndex == 0)return getValueAt(0, columnIndex).getClass();
 
        else return super.getColumnClass(columnIndex);
 
    }
    
}
