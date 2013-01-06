/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackmine;

import java.io.Serializable;

/**
 *
 * @author  Julius Torkkeli, Kuisma Kuusniemi, Hanne Korhonen
 */
public class VAKIOT {
    
    
    
    public static enum Priority implements Serializable {
    HIGH, MEDIUM, LOW 
    }
    
    public static enum State implements Serializable {
        COMPLETE, IN_PROGRESS, PENDING
    }

    /*leiskan koon määrittelevät luvut*/
    public static final int NORMALWIDTHOFTHEWINDOW = 650;
    public static final int NORMALHEIGHTOFTHEWINDOW = 500;
    public static final int NORMALWIDTHOFTHELISTPANELS = 300;
    public static final int NORMALHEIGHTOFTHESPRINTLIST = 430;
    public static final int NORMALWIDTHOFTHEMIDPANEL = 50;
    
    public static final int WIDEWIDTHOFTHEWINDOW = 1200;
    public static final int WIDEHEIGHTOFTHEWINDOW = 700;
    public static final int WIDEWIDTHOFTHELISTPANELS = 550;
    public static final int WIDEHEIGHTOFTHESPRINTLIST = 630;
    public static final int WIDEWIDTHOFTHEMIDPANEL = 100;
    
    public static final int BIGWIDTHOFTHEWINDOW = 800;
    public static final int BIGHEIGHTOFTHEWINDOW = 600;
    public static final int BIGWIDTHOFTHELISTPANELS = 350;
    public static final int BIGHEIGHTOFTHESPRINTLIST = 530;
    public static final int BIGWIDTHOFTHEMIDPANEL = 100;
    
    public static final int SMALLWIDTHOFTHEWINDOW = 600;
    public static final int SMALLHEIGHTOFTHEWINDOW = 400;
    public static final int SMALLWIDTHOFTHELISTPANELS = 275;
    public static final int SMALLHEIGHTOFTHESPRINTLIST = 330;
    public static final int SMALLWIDTHOFTHEMIDPANEL = 50;
    
    public static String BIGWINDOWSETTINGS = "big window";
    public static String NORMALWINDOWSETTINGS = "normal window";
    public static String SMALLWINDOWSETTINGS = "small window";
    public static String WIDEWINDOWSETTINGS = "WIDE SCREEN, WOW";
    
    
    public static String NEWPROJECT = "newProject";
    public static String CLOSEPROJECT = "Close Project";
    public static String NEWSPRINT = "newSprint";
    public static String NEWTASK = "newTask";
    
    public static String ADDMEMBER = "addMember";
    public static String REMOVEMEMBER = "removeMember";
    public static String SHOWMEMBERSTATS = "show member stats";
    
    public static String REMOVETASK = "removeTask";
    public static String REMOVESPRINT = "removeSprint";
    
    public static String SAVEPROJECT = "saveProject";
    public static String OPENPROJECT = "openProject";
    
    public static String EXIT = "SYNTAX ERROR! PROGRAM EXIT!";
    
  //  public static String SAVEPROJECT = "saveProject";
    //public static String OPENPROJECT = "openProject";
    
    public static String REMOVETASKFROMSPRINT = "removeTaskFromSprint";
    public static String MOVETASKFROMSPRINT = "moveTaskFromSprint";
    public static String MOVETASKTOSPRINT = "moveTaskToSprint";
    
    public static String EDITTASK = "editTask";
    public static String EDITSPRINTTASK = "editSprintTask";
    
    public static String LEFT = "<<";
    public static String RIGHT = ">>";
    
    public static String ERROR = "Error!";
    public static String SORTBYPRIORITY = "prioritySorting";
    public static String[] PRIORITEETIT = {"High","Medium","Low"};
    public static String[] STATES = {"Complete","Under Progress","Pending"};
    public static String HIGH = "High";
    public static String MEDIUM = "Medium";
    public static String LOW = "Low";
    public static String COMPLETE = "Complete";
    public static String IN_PROGRESS = "Under Progress";
    public static String PENDING = "Pending";
    public static String SORTBYDATE = "dateSorting";
    public static String SORTBYNAME = "sorttaaBaiNeimiiyO!";
    public static String SORTSPRINTBYNAME = "sprinttiiSorttaaNimellae";
    public static String SORTSPRINTBYPRIORITY = "sprintPrioritySorting";
    public static String SORTSPRINTBYDATE = "sprintDateSorting";
    
    public static String SHOWCOMPLETEDSPRINTS = "Show completed sprints";
    public static String SHOWSPRINTCHART = "Show Burn-Up -Chart";
    
    public static int THISISTASKLIST = -2;
}
