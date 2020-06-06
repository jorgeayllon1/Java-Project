/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Wang David
 */
public class TableLabel extends DefaultTableModel{
    
    
    
    Object[][] row = {{new JLabel("8h-10h"), "", "","","","",""},
                      {new JLabel("10h-12h"), "", "","","","",""},
                      {new JLabel("12h-14h"), "", "","","","",""},
                      {new JLabel("14h-16h"), "", "","","","",""},
                      {new JLabel("16h-18h"), "", "","","","",""},
                      {new JLabel("18h-20h"), "", "","","","",""},
            
    };
 
    Object[] col = {"Horaires", "Lundi", "Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
 
    public TableLabel (){
 

        for(Object c: col)
            this.addColumn(c);
 

        for(Object[] r: row)
            addRow(r);
 
    }
 
    @Override
 
    public Class getColumnClass(int columnIndex) {
        if(columnIndex == 0)return getValueAt(0, columnIndex).getClass();
 
        else return super.getColumnClass(columnIndex);
 
    }
    
}
