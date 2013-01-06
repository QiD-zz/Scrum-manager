/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datamodels.Sprint;
import java.awt.*;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @autho  Kuisma Kuusniemi
 */
public class CompletedSprintListCellRenderer extends DefaultListCellRenderer {
    
    private Sprint sprintlistitem;
    private Font font;
    
    private final int WHERETHETEXTSTARTSINTHECELL = 60;
    
    public CompletedSprintListCellRenderer()
    {
        super();
        setOpaque(true);
        font = new Font("Helvetica", Font.PLAIN, 12);  
        this.setPreferredSize(new Dimension(250, 50));        
        this.setMinimumSize(new Dimension(250, 50));
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        
        if(value instanceof Sprint) {
            sprintlistitem = (Sprint)value;
            return this;
        }
        else
        {
            sprintlistitem = null;
        }
            return super.getListCellRendererComponent(list,  value, index,  isSelected,  cellHasFocus);
        }
    
    @Override
    public void paint(Graphics g) 
    {
        if(sprintlistitem == null)
        {
            super.paint(g);
        }
        else
        {

            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setFont(font);
            g2.setColor(Color.black);
            g2.drawRect(0, 0, 249, 49);
            FontMetrics fmt = g2.getFontMetrics();
     
         /*   g2.drawString("Start date: " + sprintlistitem.getFormattedStartDate(),
                    2, fmt.getAscent()+3);
            g2.drawString("End date: " + sprintlistitem.getFormattedEndDate(),
                    2, fmt.getHeight()+fmt.getAscent()+3);*/
            g2.drawString("Sprint Id: " + sprintlistitem.getId(),
                    WHERETHETEXTSTARTSINTHECELL, fmt.getHeight()+fmt.getAscent()-5);
            g2.drawString("Completed tasks: " + sprintlistitem.getTasks().size(),
                    WHERETHETEXTSTARTSINTHECELL, fmt.getHeight()*2+fmt.getAscent()-5);
        }
    }
}

    
    
    

