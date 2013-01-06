/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author juliustorkkeli
 * 
 * Luokan ainoa tarkoitus on saada sprinttidata seriolisoitavaan muotoon
 */
public class SprintData implements Serializable{
    
    private int id;
    private ArrayList<Task> tasks;
    private Date startDate, endDate;
    
    public SprintData(Sprint sprint){
        id = sprint.getId();
        tasks = sprint.getTasks();
        startDate = sprint.getStartDate();
        endDate = sprint.getEndDate();
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
}
