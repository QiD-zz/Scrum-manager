/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import datamodels.Member;
import datamodels.ProjectModel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Kuisma Kuusniemi
 *
 */
public class NewTaskPanel extends JPanel{

    private ProjectModel projectModel;

    private JTextField taskname;
    private JTextArea descr;
    private JComboBox assignee;
    private JComboBox members;
    private String[] priorityt = {"High","Medium","Low"};
    private JComboBox priority;
   // tarviiko tätä luodessa? --> private JComboBox state;
  //  private JTextField startDate;
    private JPanel startDatePanel;
    private JScrollPane descrJsc;
    
    private SpinnerModel startDayModel;
    private SpinnerModel startMonthModel;
    private SpinnerModel startYearModel;
    
    private JSpinner startDay;
    private JSpinner startMonth;
    private JSpinner startYear;
    
    private ProjectModel model;
    
    public NewTaskPanel(ProjectModel m) {
        
        model = m;
        this.initComponents();
        this.setPreferredSize(new Dimension(300, 240));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(taskname);
        this.add(assignee);
        //this.add(members);
        this.add(descrJsc);
        this.add(priority);
        
        startDatePanel.add(startDay);
        startDatePanel.add(startMonth);
        startDatePanel.add(startYear);
        this.add(startDatePanel);
       // startDate.setText(now.toString());
    }

    private void initComponents() {
        taskname = new JTextField(10);
        taskname.setBorder(BorderFactory.createTitledBorder("Task name"));
        //members = new JComboBox(projectModel.getMembersTable());
        //members.setBorder(BorderFactory.createTitledBorder("Assignee"));

        //assignee = new JTextField(10);
        //assignee.setBorder(BorderFactory.createTitledBorder("Assignee"));
        
        assignee = new JComboBox();
        ArrayList<Member> members = model.getMembers();
        for (int i = 0 ; i < members.size() ; ++i){
            assignee.addItem(members.get(i));
        }
        
        assignee.setBorder(BorderFactory.createTitledBorder("Assignee"));
        descr = new JTextArea(5, 15);
        descrJsc = new JScrollPane(descr);
        descr.setBorder(BorderFactory.createTitledBorder("Description"));
        priority = new JComboBox(priorityt);
        priority.setBorder(BorderFactory.createTitledBorder("Tärkeys"));
        startDatePanel = new JPanel();
        startDatePanel.setPreferredSize(new Dimension(300, 75));
        startDatePanel.setBorder(BorderFactory.createTitledBorder("Tehtävän alkamispäivämäärä"));
 
        startDayModel = new SpinnerNumberModel(15, 1, 31, 1);
        startDay = new JSpinner(startDayModel);
        startDay.setPreferredSize(new Dimension(80, 40));
        startDay.setBorder(BorderFactory.createTitledBorder("Päivä"));
        startMonthModel = new SpinnerNumberModel(6, 1, 12, 1);
        startMonth = new JSpinner(startMonthModel);
        startMonth.setPreferredSize(new Dimension(80, 40));
        startMonth.setBorder(BorderFactory.createTitledBorder("Kuukausi"));
        startYearModel = new SpinnerNumberModel(2012, 1900, 2100, 1);
        startYear = new JSpinner(startYearModel);
        startYear.setPreferredSize(new Dimension(80, 40));
        startYear.setBorder(BorderFactory.createTitledBorder("Vuosi"));
        Calendar now = Calendar.getInstance();
        startDayModel.setValue(now.get(Calendar.DAY_OF_MONTH));
        startMonthModel.setValue(now.get(Calendar.MONTH));
        startYearModel.setValue(now.get(Calendar.YEAR));
    }
    
    public String getTaskName() {
        if (taskname != null) {
            return taskname.getText();
        } else {
            return "no taskname";
        }
        
    }
    
    public Member getAssignee() {
        //return (String)members.getSelectedItem();
        
        if (assignee != null) {
            return (Member)assignee.getSelectedItem();
        } else {
            return null;
        }
    }
    
    public String getDescription() {
        if (descr != null) {
            return descr.getText();
        } else {
            return "no description";
        }
        
    }
    
    public String getPriority() {
        return priorityt[priority.getSelectedIndex()];
    }
    
    public Date getStartdate() {
        if (startDatePanel != null) {
            Calendar now = Calendar.getInstance();
            now.set((Integer)(startDayModel.getValue()), (Integer)(startMonthModel.getValue()), (Integer)(startYearModel.getValue()));
            return now.getTime();
        } else {
            return new Date();
        }
        
    }
}

