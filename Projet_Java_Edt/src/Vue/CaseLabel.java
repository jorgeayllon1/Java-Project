
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
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        
        Font  mon_font  = new Font(Font.DIALOG,  Font.BOLD, 16);
        if (row == 0) {
            Color ma_couleur = new Color(204, 255, 153);
            comp.setBackground(ma_couleur);
            comp.setFont(mon_font);
            
        } else {
            Color ma_couleur = new Color(242, 242, 242);
            comp.setBackground(ma_couleur);
        }
        for(int i=0; i < table.getRowCount(); i++) {
         for(int j=0; j < table.getColumnCount(); j++) {
            String str = table.getValueAt(i,j).toString();
            if(str.trim().length() != 0) {

            }
         }
      }

        return comp;
    }
    

    
}
