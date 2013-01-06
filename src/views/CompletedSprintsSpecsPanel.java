/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datamodels.Sprint;
import java.awt.Dimension;
import javax.swing.*;

/**
 * Tämä paneeli näyttää joitakin tietoja lopetetusta sprintistä.
 * @author Kuisma Kuusniemi
 */
public class CompletedSprintsSpecsPanel extends JPanel {
    
    private Sprint sprint;
    private JLabel startDate;
    private JLabel endDate;
    private JPanel namesOfCompletedTasks;
    
    public CompletedSprintsSpecsPanel(Sprint s) {
        sprint = s;
        startDate = new JLabel();
        startDate.setBorder(BorderFactory.createTitledBorder("Start date of the sprint"));
        startDate.setText(sprint.getStartDate().toString());
        
        endDate = new JLabel();
        endDate.setBorder(BorderFactory.createTitledBorder("End date of the sprint"));
        endDate.setText(sprint.getEndDate().toString());
        
        namesOfCompletedTasks = new JPanel();
        namesOfCompletedTasks.setLayout(new BoxLayout(namesOfCompletedTasks, BoxLayout.Y_AXIS));
        namesOfCompletedTasks.setBorder(BorderFactory.createTitledBorder("Completed tasks:"));
        for (int i = 0; i < sprint.getTasks().size(); i++) {
            JLabel temp = new JLabel(sprint.getTasks().get(i).getTaskname());
          //  temp.setPreferredSize(new Dimension(50, 20));
            namesOfCompletedTasks.add(temp);
            
        }
        
        JScrollPane jsc = new JScrollPane(namesOfCompletedTasks);
        jsc.setPreferredSize(new Dimension(45, 100));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(startDate);
        this.add(endDate);
        this.add(jsc);
    }
    
}
