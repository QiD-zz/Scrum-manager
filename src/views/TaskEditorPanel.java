/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class TaskEditorPanel extends JPanel{

    private ProjectModel projectModel;

    private JTextField taskname;
    private JTextArea descr;
    private JScrollPane descrScrollPane;
    private JComboBox members, priority, state;
    private JSlider progressEstimation;
    private JSpinner workHoursDone;
    private JPanel panel, panel2;

    private Task currentTask;
 
    public TaskEditorPanel(ProjectModel pModel, boolean descrVisible){
        this.projectModel = pModel;
        this.initComponents();
        //this.setPreferredSize(new Dimension(300, 240));
        //this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(taskname);
        if (descrVisible)
            this.add(descrScrollPane);
        //this.add(members);
        //this.add(priority);
        //this.add(state);
        this.add(panel);
        //this.add(workHoursDone);
        this.add(panel2);
        this.add(progressEstimation);
    }
    private void initComponents() {
        taskname = new JTextField(20);
        taskname.setBorder(BorderFactory.createTitledBorder("Task name:"));

        members = new JComboBox(projectModel.getMembersTable());
        members.setBorder(BorderFactory.createTitledBorder("Assignee:"));

        descr = new JTextArea(3, 20);
        //descr.setBorder(BorderFactory.createTitledBorder("Description:"));
        
        priority = new JComboBox(VAKIOT.PRIORITEETIT);
        priority.setBorder(BorderFactory.createTitledBorder("Priority:"));
        
        state = new JComboBox(VAKIOT.STATES);
        state.setBorder(BorderFactory.createTitledBorder("State:"));
        
        descr.setLineWrap(true);
        
        descrScrollPane = new JScrollPane(descr);
        descrScrollPane.setBorder(BorderFactory.createTitledBorder("Task description:"));
        descrScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        progressEstimation = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        progressEstimation.setMajorTickSpacing(10);
        progressEstimation.setMinorTickSpacing(1);
        progressEstimation.setPaintTicks(true);
        progressEstimation.setPaintLabels(true);
        progressEstimation.setBorder(BorderFactory.createTitledBorder("Progress Estimation: (%)"));
        
        int INITIAL = 0;
        int MIN = 0;
        int MAX = 999;
        int STEP = 1;
        workHoursDone = new JSpinner(new SpinnerNumberModel(INITIAL, MIN, MAX, STEP));
        workHoursDone.setBorder(BorderFactory.createTitledBorder("Work hours done:"));

        panel = new JPanel();
        //panel.add(members);
        panel.add(priority);
        panel.add(state);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.LINE_AXIS));
        panel2.add(members);
        panel2.add(workHoursDone);

    }
    
    public Task getTask() {
        //laitoin tämän if-elsen kun string ei halunnut kääntyä priorityksi t: kuisma
        VAKIOT.Priority prioriteetti = null;
        String selected = (String) priority.getSelectedItem();
        
        if (selected != null){
            if (priority.getSelectedItem().equals(VAKIOT.HIGH)) {
                prioriteetti = VAKIOT.Priority.HIGH;
            } else if (priority.getSelectedItem().equals(VAKIOT.MEDIUM)) {
                prioriteetti = VAKIOT.Priority.MEDIUM;
            } else {
                prioriteetti = VAKIOT.Priority.LOW;
            }
        }
        
        VAKIOT.State tila = null;
        selected = (String) state.getSelectedItem();
        if (selected != null){
            if (state.getSelectedItem().equals(VAKIOT.COMPLETE)) {
                tila = VAKIOT.State.COMPLETE;
            } else if (state.getSelectedItem().equals(VAKIOT.IN_PROGRESS)) {
                tila = VAKIOT.State.IN_PROGRESS;
            } else {
                tila = VAKIOT.State.PENDING;
            }
        }

        currentTask.setTaskname(taskname.getText());
        currentTask.setAssignee((Member)members.getSelectedItem());
        currentTask.setDescr(descr.getText());
        currentTask.setPriority(prioriteetti);
        currentTask.setState(tila);
        if (tila == VAKIOT.State.COMPLETE)
            currentTask.setProgress(100);
        else
            currentTask.setProgress(progressEstimation.getValue());
        currentTask.setTaskWorkDone((Integer)workHoursDone.getValue());

        
        return currentTask;
    }

    public void setTask(Task task) {
        currentTask = task;
        
        taskname.setText(currentTask.getTaskname());
        descr.setText(currentTask.getDescr());
        members.setSelectedItem(currentTask.getAssignee());
        progressEstimation.setValue(currentTask.getProgress());
        workHoursDone.setValue(currentTask.getTaskWorkDone());
  
        VAKIOT.State state_ = currentTask.getState();
        VAKIOT.Priority prio_ = currentTask.getPriority();
        String stateValue;
        String prioValue;
        
        switch (state_){
            case PENDING : stateValue = VAKIOT.PENDING;
                break;
            case IN_PROGRESS : stateValue = VAKIOT.IN_PROGRESS;
                break;
            case COMPLETE : stateValue = VAKIOT.COMPLETE;
                break;
            default: stateValue = null;
                break;
        }
        
        switch (prio_){
            case HIGH : prioValue = VAKIOT.HIGH;
                break;
            case MEDIUM : prioValue = VAKIOT.MEDIUM;
                break;
            case LOW : prioValue = VAKIOT.LOW;
                break;
            default: prioValue = null;
                break;
        }
        priority.setSelectedItem(prioValue);
        state.setSelectedItem(stateValue);
        
    }
}
