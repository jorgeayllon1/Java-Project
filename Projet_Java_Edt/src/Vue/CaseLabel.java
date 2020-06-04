/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Wang David
 */
public class CaseLabel extends DefaultTableCellRenderer{
    
    JLabel label = new JLabel();
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        /**
         * Fixer la couleur de fond de la première colonne en jaune
         */
        
        
        Font  f3  = new Font(Font.DIALOG,  Font.BOLD, 15);
        if (row == 0) {
            Color clr = new Color(255, 255, 240);
            component.setBackground(clr);
            component.setFont(f3);
            
        } else {
            Color clr = new Color(255, 255, 255);
            component.setBackground(clr);
        }
        for(int i=0; i < table.getRowCount(); i++) {
         for(int j=0; j < table.getColumnCount(); j++) {
            String str = table.getValueAt(i,j).toString();
            if(str.trim().length() != 0) {
                
                
                
               
            }
         }
      }
        
        return component;
    }
    
}
