/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import javax.swing.JPanel;
import views.SprintBurnUpChart;

/**
 *
 * @author juliustorkkeli, Hanne Korhonen 90404
 */
public class Sprint extends Observable implements Serializable {
    
    private int id; // esim. järjestysnumero
    private TaskListModel tasks;
    //private ArrayList<Task> tasks;
    private Date startDate, endDate;
    private boolean active;
    
    /*
     * Constructorin rakenne riippuu millaisessa datapaketissa meidän
     * dialogi kerää datan
     */
    public Sprint(int i, Date start, Date end){
        id = i;
        tasks = new TaskListModel();
        //tasks = new ArrayList<Task>();
        startDate = start;
        endDate = end;
        active = true;
    }
    
    public Sprint(SprintData s){
        id = s.getId();
        tasks = new TaskListModel(s.getTasks());
        startDate = s.getStartDate();
        endDate = s.getEndDate();
    }
    
    public void addTask(Task t){
        tasks.addTask(t);
        //setChanged();
        //notifyObservers(this);
    }
    
    public Task removeTask(Task t){
        
        return tasks.removeTask(t);
        /*
        int index = tasks.indexOf(t);
        Task tmp = null;
        
        if (index > 0){
            tmp = tasks.remove(index);
        }
        
        setChanged();
        notifyObservers(this);
        return tmp;
        * 
        */

    }



    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks.getTasks();
    }
    
    public void setTasks(ArrayList<Task> t){
        tasks = new TaskListModel(t);
        tasks.setSprintList(true);
    }
    
    @Override
    public String toString(){
        return Integer.toString(id);
    }
    
    public TaskListModel getTableModel(){
        return tasks;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    public JPanel SprintBurnUpChart(){
        SprintBurnUpChart chart = new SprintBurnUpChart(this);
        return chart;
    }
    public String getFormattedEndDate(){
        String date = null;
        if(endDate != null){
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            int day = c.get(Calendar.DATE);
            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            date = day + "." + month + "." + year;
        }
        return date;
    }
     public String getFormattedStartDate(){
        String date = null;
        if(startDate != null){
            Calendar c = Calendar.getInstance();
            c.setTime(startDate);
            int day = c.get(Calendar.DATE);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            date = day + "." + month + "." + year;
        }
        return date;
    }


}
