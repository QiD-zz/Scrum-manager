package views;

import blackmine.VAKIOT;
import datamodels.Member;
import datamodels.ProjectModel;
import datamodels.Task;
import javax.swing.*;

/**
 *
 * @author Hanne Korhonen 90404, Julius Torkkeli
 */
public class TaskAddPanel extends JPanel{

    private ProjectModel projectModel;

    private JTextField taskname;
    private JTextArea descr;
    private JScrollPane descrScrollPane;
    private JComboBox members, priority, state;
    private JSpinner workHours;
    private JPanel panel, panel2;
 
    public TaskAddPanel(ProjectModel pModel){
        this.projectModel = pModel;
        this.initComponents();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(taskname);
        this.add(descrScrollPane);
        this.add(panel);
        this.add(panel2);
    }
    private void initComponents() {
        
        taskname = new JTextField(20);
        taskname.setBorder(BorderFactory.createTitledBorder("Task name:"));

        members = new JComboBox(projectModel.getMembersTable());
        members.setBorder(BorderFactory.createTitledBorder("Assignee:"));

        descr = new JTextArea(3, 20);
        
        priority = new JComboBox(VAKIOT.PRIORITEETIT);
        priority.setSelectedIndex(1); // Oletusarvona prioriteetti Medium
        priority.setBorder(BorderFactory.createTitledBorder("Priority:"));
        
        state = new JComboBox(VAKIOT.STATES);
        state.setSelectedIndex(2); // Oletusarvoksi Pending
        state.setBorder(BorderFactory.createTitledBorder("State:"));
        
        descr.setLineWrap(true);
        
        descrScrollPane = new JScrollPane(descr);
        descrScrollPane.setBorder(BorderFactory.createTitledBorder("Task description:"));
        descrScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        int INITIAL = 0;
        int MIN = 0;
        int MAX = 999;
        int STEP = 1;
        workHours = new JSpinner(new SpinnerNumberModel(INITIAL, MIN, MAX, STEP));
        workHours.setBorder(BorderFactory.createTitledBorder("Estimated working hours required:"));

        panel = new JPanel();
        panel.add(priority);
        panel.add(state);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
        panel2.add(members);
        panel2.add(workHours);

    }
    
    public Task getTask() {

        VAKIOT.Priority PRIORITY = null;
        String selected = (String) priority.getSelectedItem();
        
        if (selected != null){
            if (priority.getSelectedItem().equals(VAKIOT.HIGH)) {
                PRIORITY = VAKIOT.Priority.HIGH;
            } else if (priority.getSelectedItem().equals(VAKIOT.MEDIUM)) {
                PRIORITY = VAKIOT.Priority.MEDIUM;
            } else {
                PRIORITY = VAKIOT.Priority.LOW;
            }
        }
        
        VAKIOT.State STATE = null;
        selected = (String) state.getSelectedItem();
        if (selected != null){
            if (state.getSelectedItem().equals(VAKIOT.COMPLETE)) {
                STATE = VAKIOT.State.COMPLETE;
            } else if (state.getSelectedItem().equals(VAKIOT.IN_PROGRESS)) {
                STATE = VAKIOT.State.IN_PROGRESS;
            } else {
                STATE = VAKIOT.State.PENDING;
            }
        }

        String TASKNAME = taskname.getText();
        Member ASSIGNEE = (Member)members.getSelectedItem();
        String DESCRIPTION = descr.getText();
        int WORKHOURS = (Integer) workHours.getValue();
        
        //(String taskname, String descr, Member assignee, Priority priority, int estimateHours)
        return new Task(TASKNAME, DESCRIPTION, ASSIGNEE, PRIORITY, STATE, WORKHOURS);
    }
}