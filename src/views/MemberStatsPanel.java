/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import blackmine.VAKIOT;
import datamodels.Member;
import datamodels.Task;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author juliustorkkeli
 */
public class MemberStatsPanel extends JPanel {
    
    public MemberStatsPanel(Member member){
        
        ArrayList<Task> tasks = member.getTasks();
        
        
        
        if (tasks.size() > 0){
            this.add(new JLabel(member.getMemberName() + " is assigned to following tasks:"));
            String taskList[] = new String[tasks.size()];
            
            for (int i = 0 ; i < tasks.size() ; ++i){
                
                String NAME = tasks.get(i).getTaskname();
                VAKIOT.State state = tasks.get(i).getState();
                String STATEVALUE;
                
                switch (state){
                    case PENDING : STATEVALUE = VAKIOT.PENDING;
                    break;
                    case IN_PROGRESS : STATEVALUE = VAKIOT.IN_PROGRESS;
                    break;
                    case COMPLETE : STATEVALUE = VAKIOT.COMPLETE;
                    break;
                    default: STATEVALUE = null;
                    break;
                }
                
                String listObject = NAME + " which is " + STATEVALUE;
                taskList[i] = listObject;
            }
            
            JList list = new JList(taskList);
            JScrollPane sp = new JScrollPane(list);
            this.add(sp);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }
        else{
            this.add(new JLabel(member.getMemberName() + " is not assigned to any task"));
        }
    }
}
