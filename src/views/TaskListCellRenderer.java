
package views;

import blackmine.VAKIOT;
import datamodels.Member;
import datamodels.Task;
import datamodels.TaskListModel;
import java.awt.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rami
 */
public class TaskListCellRenderer extends DefaultTableCellRenderer {

    private boolean selected = false;
    private Task taskListItem = null;
    private Font font;
    private boolean isThisSprintList;
    /**
     * tekee uuden tasklistrendererin, jolle annetaan koko ja fontti.
     */
    public TaskListCellRenderer()
    {
        super();
        setOpaque(true);
        font = new Font("Helvetica", Font.PLAIN, 12);  
        this.setPreferredSize(new Dimension(250, 50));        
        this.setMinimumSize(new Dimension(250, 50));
    }
    
    /**
     * metodi, joka hakee soluun kuuluvan renderöintikomponentin
     * @param table taulu jonka soluja piirrellään
     * @param value taulun solun arvo
     * @param isSelected onko solu valittuna
     * @param hasFocus onko siinä focus
     * @param row mikä rivi
     * @param column mikä sarake
     * @return palautetaan renderöijä
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    {
        if(value instanceof Task)
        {
            TaskListModel model = (TaskListModel)table.getModel();
            if (model.isSprintList()) {
                isThisSprintList = true;
            }
            
            taskListItem = (Task) value;
            selected = isSelected;
            table.setRowHeight(row, 50);
            if (isThisSprintList && taskListItem.getAssignee() != null) {
                font = new Font("Helvetica", Font.PLAIN, 11);
            } else {
                font = new Font("Helvetica", Font.PLAIN, 12);
            }
            return this;
        }
        else
        {
            taskListItem = null;
            return super.getTableCellRendererComponent(table,  value,  isSelected,  hasFocus,  row,  column);
        }
    }
    /**
     * metodi, joka piirtelee taulun soluja
     * @param g grafiikkaolio
     */
    @Override
    public void paint(Graphics g) 
    {
        if(taskListItem == null)
        {
            super.paint(g);
        }
        else
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (taskListItem.getState() == VAKIOT.State.COMPLETE) {
                g2.setColor(Color.white);
            } else {
                if(taskListItem.getPriority() == VAKIOT.Priority.HIGH) {
                    g2.setColor(Color.RED);
                }                
                else if (taskListItem.getPriority() == VAKIOT.Priority.MEDIUM) {
                    g2.setColor(Color.yellow);
                }                 
                else {
                    g2.setColor(Color.green);
                }
                    
            }
            
            g2.fillRect(0, 0,getWidth(), getHeight());
        
           
            g2.setFont(font);
            g2.setColor(Color.black);
            FontMetrics fmt = g2.getFontMetrics();
     
            g2.drawString("Task name: " + taskListItem.getTaskname(),
                    2, fmt.getAscent()+3);
            
            Member assignee = taskListItem.getAssignee();
      
            if (taskListItem.getState() == VAKIOT.State.IN_PROGRESS) {
                 
                g2.drawString("This task is in progress.", 2, 
                         fmt.getHeight()+fmt.getAscent()+3);   
                } else if (taskListItem.getState() == VAKIOT.State.COMPLETE) {
                  
                    g2.drawString("This task is completed.", 2, 
                         fmt.getHeight()+fmt.getAscent()+3);  
                } else {
                 
                    g2.drawString("This task is pending.", 2, 
                         fmt.getHeight()+fmt.getAscent()+3); 
                }
  
            if (isThisSprintList && taskListItem.getState() == VAKIOT.State.IN_PROGRESS) {
                
                    
           
                g2.drawString("Task is assigned to "+assignee,
                    2, fmt.getHeight()+fmt.getAscent()+3+fmt.getAscent()+3);
            }
            
      
            g2.setColor(Color.black);
            
            g2.drawRect(140, 5, 100, 40);
            g2.fillRect(140, 5, taskListItem.getProgress(), 40);
            
            if (taskListItem.getProgress() == 100) {
                font = new Font("Helvetica", Font.BOLD, 18);
                g2.setFont(font);
                g2.setColor(Color.green);
                g2.drawString("Completed", 143, 35);
            } else {
                font = new Font("Helvetica", Font.BOLD, 20);
                g2.setFont(font);
                g2.setColor(Color.blue);
                g2.drawString(taskListItem.getProgress()+"% done", 146, 35);
            }
                  
            
            if (selected) {
                g2.setColor(new Color(200,200,200,120));
                g2.fillRect(0, 0,getWidth(), getHeight());
            }
        }
    }    
}
