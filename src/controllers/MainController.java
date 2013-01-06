/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import blackmine.VAKIOT;
import datamodels.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import views.*;

/**
 *
 * @author juliustorkkeli, Kuisma Kuusniemi, Hanne Korhonen
 */
public class MainController implements ActionListener, WindowListener {

    private MainModel model;
    private MainView view;
    private ProjectModel project;
    private JFileChooser jfc;
    
    private boolean doNotClose;
    
    public MainController(MainModel m, MainView mv){
        model = m;
        view = mv;
        project = null;
        
        String[] newExtensions = {"black","bmine","scrum"};   
        jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Scrum "
                + "Project extensions (.black, .bmine, .scrum)", newExtensions ));
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        String cmd = ae.getActionCommand();
        
        // move a task to sprintlist
        if (cmd.equals(VAKIOT.RIGHT)){
            
            project = model.getActiveProject();
            
            try {
                if (view.getSelectedTasklistIndex() != -1 && view.getActiveSprint() != -1) {
                    Sprint sprint = project.getSprint(view.getActiveSprint());
                    project.moveTaskToSprint(view.getActiveTask(), sprint);
                }
                        
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                //System.out.println(ex.getMessage());
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //move a task from sprint list back to project task list
        else if (cmd.equals(VAKIOT.LEFT)){
            
            project = model.getActiveProject();
            
            try {
                if (view.getSelectedSprintIndex() != -1) {
                    Sprint sprint = project.getSprint(view.getActiveSprint());
                    project.moveTaskFromSprint(view.getActiveSprintTask(), sprint);
                }
   
   
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if (cmd.equals(VAKIOT.NEWSPRINT)){
              
            NewSprintPanel sprintPanel = new NewSprintPanel();
            int valinta = JOptionPane.showConfirmDialog(view,sprintPanel, "Uusi sprintti",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if (valinta == 0) {
                project = model.getActiveProject();
                Sprint newSprint = new Sprint(project.getSprintId(), sprintPanel.getStartdate(),
                        sprintPanel.getEnddate());
                if(newSprint.getStartDate().before(newSprint.getEndDate()))
                    project.addSprint(newSprint);
                else
                    JOptionPane.showMessageDialog(view, "We were unable to make sprint. Make sure that sprint's startdate is before the enddate!", "Warning", JOptionPane.PLAIN_MESSAGE);
                    
            }
            
        }
        
        else if (cmd.equals(VAKIOT.REMOVESPRINT)){
 
            project = model.getActiveProject();
            if (project.getSprintCount()>0) {
                Sprint sprint = project.getSprint(view.getActiveSprint());
                project.removeSprint(sprint);
            }           
        }

        else if(cmd.equals(VAKIOT.SHOWSPRINTCHART)) {
            project = model.getActiveProject();
            Sprint sprint = project.getSprint(view.getActiveSprint());

            JOptionPane.showMessageDialog(view,sprint.SprintBurnUpChart(), "Burn-Up -Chart", JOptionPane.PLAIN_MESSAGE);

       }
        
        else if (cmd.equals(VAKIOT.SAVEPROJECT)){        
            
            jfc.setCurrentDirectory(new File("C:\\"));
            int valinta = jfc.showSaveDialog(jfc);
        
            if (valinta == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile() != null) {
 
                String url = jfc.getSelectedFile().getPath();
                String urlWithEnding;
            
                if (url.endsWith(".black") || url.endsWith(".bmine")
                    || url.endsWith(".scrum")) {
                    
                    urlWithEnding = url;
                } else {
                    urlWithEnding = url + ".scrum";
                }
                try {
                    model.saveProject(urlWithEnding);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        }
        
        else if (cmd.equals(VAKIOT.OPENPROJECT)){
            
            int valinta = jfc.showOpenDialog(jfc);
            
            if (valinta == JFileChooser.APPROVE_OPTION && jfc.getSelectedFile() != null){
                String url = jfc.getSelectedFile().getPath();
                try {
                    model.loadProject(url);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        /*
         * removes a task completely from project
         */
        else if (cmd.equals(VAKIOT.REMOVETASK)){
            
            project = model.getActiveProject();
            
            try {
                
                project.removeTask(view.getActiveTask());
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        /*
         * removes a task from a sprint
         */ 
        else if (cmd.equals(VAKIOT.REMOVETASKFROMSPRINT)){
            project = model.getActiveProject();
            
            try {
                
                Sprint sprint = project.getSprint(view.getActiveSprint());
                project.removeTaskFromSprint(view.getActiveSprintTask(), sprint);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (cmd.equals(VAKIOT.NEWPROJECT)){
            /*
             * Uuden projektin lisäys. Jos projekti halutaan aktiiviseksi
             * asetetaan se aktiiviseksi.
             */
            NewProjectPanel projectPanel = new NewProjectPanel();
            int valinta = JOptionPane.showConfirmDialog(view,projectPanel, "Uusi projekti",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
         
            if (valinta == 0) {
                try {
                    ProjectModel newProject = new ProjectModel(projectPanel.getProjectName());
                    newProject.addObserver(view);
                    
                    model.addProject(newProject, projectPanel.getActivity());
     
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (cmd.equals(VAKIOT.CLOSEPROJECT))
        {
            int valinta = JOptionPane.showConfirmDialog(view, "Do you"
                    + " really want to close the project? All unsaved information"
                    + "will be lost.", "R U Sure????",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            
            if (valinta == 0) {
                project = model.getActiveProject();
                
                try {
                    model.removeProject(project);
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         
        }
        else if (cmd.equals(VAKIOT.NEWTASK)){
            project = model.getActiveProject();
            TaskAddPanel taskPanel = new TaskAddPanel(project);
            int valinta = JOptionPane.showConfirmDialog(view,taskPanel, 
                    "New Task:",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            System.out.println(valinta);
            if (valinta == 0) {
                project.addTask(taskPanel.getTask());
               
            }
        }
        
        else if (cmd.equals(VAKIOT.EDITTASK)){
            project = model.getActiveProject();
            
            int selectedTask = view.getSelectedTasklistIndex();
            
            int valinta = 666;
            TaskEditorPanel editPanel = new TaskEditorPanel(project, true);
            
            if (selectedTask != -1) {            
                editPanel.setTask(project.getTask(selectedTask));
                valinta = JOptionPane.showConfirmDialog(view, editPanel, "Edit task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);         
            }
            
            if (valinta == 0){
                Task newTaskInfo = editPanel.getTask();
                project.getTableModel().editTask(selectedTask, newTaskInfo);
            }      
        }
        
        else if(cmd.equals(VAKIOT.EDITSPRINTTASK)){
            project = model.getActiveProject();
            
            int selectedTask = view.getSelectedSprintIndex();
            int selectedSprint = view.getActiveSprint();
            TaskEditorPanel editPanel = new TaskEditorPanel(project, true);
            int valinta = 666;
            
            if (selectedTask != -1) {
                 editPanel.setTask(project.getSprint(selectedSprint).getTableModel().getTask(selectedTask));           
                valinta = JOptionPane.showConfirmDialog(view, editPanel, "Edit task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);           
            }
           
            if (valinta == 0){
                Task newTaskInfo = editPanel.getTask();
                project.getSprintTableModel(selectedSprint).editTask(selectedTask, newTaskInfo);
            } 
        }
        
        else if (cmd.equals(VAKIOT.SHOWCOMPLETEDSPRINTS)) {
            project = model.getActiveProject();
            ArrayList list = project.getCompletedSprints();
            if (list.size() > 0) {
                CompletedSprintsPanel sprintPanel = new CompletedSprintsPanel(project);
                JOptionPane.showMessageDialog(view,sprintPanel, 
                    "List of completed sprints",JOptionPane.PLAIN_MESSAGE);
            }
            else 
            {
                 JOptionPane.showMessageDialog(view, "No completed sprints!", 
                         "No sprints", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
        
        /*
         * moves a task from a sprint back to product list
         */
        else if (cmd.equals(VAKIOT.MOVETASKFROMSPRINT)){
             project = model.getActiveProject();
            
            try {
                
                Sprint sprint = project.getSprint(view.getActiveSprint());
                project.moveTaskFromSprint(view.getActiveSprintTask(), sprint);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex, VAKIOT.ERROR , JOptionPane.ERROR_MESSAGE);
                System.out.println(ex.getMessage());
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // etc..
        else if (cmd.equals(VAKIOT.ADDMEMBER)) {
            project = model.getActiveProject();
            String memberName = JOptionPane.showInputDialog("Give new member's name:");
            Member newMember = new Member(memberName);
            if (newMember != null) {
                project.addMember(newMember);
            }
        } else if (cmd.equals(VAKIOT.REMOVEMEMBER)) {           
            project = model.getActiveProject();
            Object[] tyypit = project.getMembers().toArray();
            try{
                Object rmvMember = JOptionPane.showInputDialog(view, "Choose the member you want to remove.", "Remove member", JOptionPane.PLAIN_MESSAGE, null, tyypit, tyypit[0]);
                if (rmvMember != null) {
                    project.removeMember((Member) rmvMember);
                }
            }
            catch(ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(view, "There is no members in this project!");
            }
            
        }
        
        else if (cmd.equals(VAKIOT.SHOWMEMBERSTATS)){
            
            project = model.getActiveProject();
            Object[] tyypit = project.getMembers().toArray();
            Object member = null;
            try {
                member = JOptionPane.showInputDialog(view,
                    "Whose statistics would you like to see?", 
                    "Member statistics", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null, tyypit, 
                    tyypit[0]);
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(view, "There is no members in this project"
                        , "No members",JOptionPane.INFORMATION_MESSAGE);
            }
            
            
            if (member != null) {
                JOptionPane.showMessageDialog(view, new MemberStatsPanel((Member)member));
            }
        }
        
        else if (cmd.equals(VAKIOT.SORTBYPRIORITY)) {
            project = model.getActiveProject();
            
            if (project != null) {
                project.listSorting(VAKIOT.SORTBYPRIORITY, true, VAKIOT.THISISTASKLIST);
            }
            
        }
        
        else if (cmd.equals(VAKIOT.SORTBYDATE)) {
            project = model.getActiveProject();
            
            if (project != null) {
                project.listSorting(VAKIOT.SORTBYDATE, true, VAKIOT.THISISTASKLIST);
            }
            
        }
        
        else if (cmd.equals(VAKIOT.SORTBYNAME)) {
            project = model.getActiveProject();
            
            if (project != null) {
                project.listSorting(VAKIOT.SORTBYNAME, true, VAKIOT.THISISTASKLIST);
            }
            
        }
        
        else if (cmd.equals(VAKIOT.SORTSPRINTBYNAME)) {
            project = model.getActiveProject();
            
            if (project != null) {
                
                project.listSorting(VAKIOT.SORTSPRINTBYNAME, false, view.getActiveSprint());
            }      
        }
        else if (cmd.equals(VAKIOT.SORTSPRINTBYDATE)) {
            project = model.getActiveProject();
            
            if (project != null) {
                
                project.listSorting(VAKIOT.SORTSPRINTBYDATE, false, view.getActiveSprint());
            }          
        }
        else if (cmd.equals(VAKIOT.SORTSPRINTBYPRIORITY)) {
            project = model.getActiveProject();
            
            if (project != null) {
                
                project.listSorting(VAKIOT.SORTSPRINTBYPRIORITY, false, view.getActiveSprint());
            }          
        }
        else if (cmd.equals(VAKIOT.EXIT))
        {
            int valinta = JOptionPane.showConfirmDialog(null, "Do you really want to"
                      + " exit? All unsaved information will be lost.","Really???", JOptionPane.OK_CANCEL_OPTION,
                      JOptionPane.QUESTION_MESSAGE);
 
                if (valinta == JOptionPane.OK_OPTION) {                 
                    view.dispose();
                }           
        }
        else if (cmd.equals(VAKIOT.BIGWINDOWSETTINGS) ||cmd.equals(VAKIOT.NORMALWINDOWSETTINGS) ||
                cmd.equals(VAKIOT.SMALLWINDOWSETTINGS) || cmd.equals(VAKIOT.WIDEWINDOWSETTINGS))
        {
            view.setWindowSize();
        }
   }

    @Override
    public void windowOpened(WindowEvent e) {  }

    @Override
    public void windowClosing(WindowEvent e) {
        int valinta = JOptionPane.showConfirmDialog(null, "Do you really want to"
                  + " exit? All unsaved information will be lost.","Really???", JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.QUESTION_MESSAGE);
 
            if (valinta == JOptionPane.OK_OPTION) {                 
                    view.dispose();
            } 
        }

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}
