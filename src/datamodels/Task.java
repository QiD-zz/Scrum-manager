/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import blackmine.VAKIOT.Priority;
import blackmine.VAKIOT.State;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author juliustorkkeli, Hanne Korhonen 90404
 */
public class Task implements Serializable {

    private int progressValue;
    private String taskname, descr;
    private Member assignee;
    private Priority priority;
    private State state;
    private Date startDate, endDate;
    private int estimatedWorkHours, workHoursDone;
    
 
    public Task(String tn){
       taskname = tn;
       progressValue = 0;
       this.priority = Priority.MEDIUM;
       this.state = State.PENDING;
    }
    public Task(String taskname, String descr, Member assignee, Priority priority, State state, int estimateHours){
        this.taskname = taskname;
        this.descr = descr;
        this.assignee = assignee;
        this.priority = priority;
        this.state = state;
        this.estimatedWorkHours = estimateHours;
        
        workHoursDone = 0;
        progressValue = 0;
        
        startDate = Calendar.getInstance().getTime();
        
    }
    
    /**
     * @return the taskname
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname the taskname to set
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * @return the assignee
     */
    public Member getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(Member assignee) {
        /*
         * Tämä ei nyt taas kaikkia suunnittelumalleja miellytä, mutta samalla
         * kun tehtävälle asetetaan tekijä, lisätään tehtävä tekijän työlistalle
         */
        
        if (assignee != null){
            
            if(this.assignee != null){
                this.assignee.removeTask(this);
            }
            this.assignee = assignee;
            assignee.addTask(this);
        }
        else{
            if (this.assignee != null){
                this.assignee.removeTask(this);
            }
            this.assignee = null;
            
        }
    }

    /**
     * @return the priority
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
        if (state == State.COMPLETE){
            progressValue = 100;
            setEndDate(Calendar.getInstance().getTime());
        }
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public int getProgress() {
        /*
        double luku = (double)100/(double)estimatedWorkHours;
        double tempProgress = luku*workHoursDone;
        if (tempProgress % 1 > 0.5)
            progressValue = (int)tempProgress +1;
        else
            progressValue = (int)tempProgress;
        return progressValue;
        * 
        */
        return progressValue;
    }
    
    public void setProgress(int value) {
        if (value >= 0 && value <= 100) {
            this.progressValue = value;
        } else if (value > 100){
            this.progressValue = 100;
        } else {
            this.progressValue = 0;
        }
        if (progressValue == 100) 
        {
            this.state = State.COMPLETE;
            setEndDate(Calendar.getInstance().getTime());
        } 
    }
    
    public void setTaskWorkEstimation(int hours) 
    {     
        if (hours > 0)
        {
            this.estimatedWorkHours = hours;
        }      
    }
    
    public int getTaskWorkEstimation()
    {
        return this.estimatedWorkHours;
    }
    
    public void setTaskWorkDone(int hours) 
    {     
        /*
        if (hours >= 0 && hours <= estimatedWorkHours)
        {
            this.workHoursDone = hours;
        }

        if (workHoursDone == estimatedWorkHours) 
        {
            this.state = State.COMPLETE;
        } 
        * 
        */
        this.workHoursDone = hours;
    }
    
    public int getTaskWorkDone()
    {
        return this.workHoursDone;
    }
    
    
    @Override
    public String toString(){
        return taskname;
    }
}
