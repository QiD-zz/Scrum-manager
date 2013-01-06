/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import blackmine.VAKIOT;
import comparators.TaskNameComparator;
import comparators.TaskPriorityComparator;
import comparators.TaskStartdateComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 *
 * @author juliustorkkeli, Hanne Korhonen 90404
 */
public class ProjectModel extends Observable {
    
    private String name;
    private TaskListModel tasks;
    //private ArrayList<Task> tasks;
    private ArrayList<Sprint> sprints;
    private ArrayList<Sprint> sprintsThatHasEnded;
    private ArrayList<Member> members;
    private String taskListLastSortedBy = "";
    private String sprintListLastSortedBy = "";

    private int sprintId;

    public ProjectModel(String n){
        
        name = n;
        tasks = new TaskListModel();
        sprints = new ArrayList<Sprint>();
        members = new ArrayList<Member>();
        sprintsThatHasEnded = new ArrayList<Sprint>();
        sprintId = 1;
    }
    
    public ProjectModel(ProjectData data){
        name = data.getName();
        tasks = new TaskListModel(data.getTasks());
        sprints = new ArrayList<Sprint>();
        for (int i = 0 ; i < data.getSprints().size() ; ++i){
            Sprint newSprint = new Sprint(data.getSprints().get(i));
            sprints.add(newSprint);
        }
        members = data.getMembers();
        sprintId = 1;
    }
    
    public void addTask(Task t){
        tasks.addTask(t);
        setChanged();
        notifyObservers(this);
    }
    
    public Task removeTask(Task t){
        Member tmp = t.getAssignee();
        if (tmp != null){
            tmp.removeTask(t);
        }
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
    
    public Task removeTaskFromSprint(Task t, Sprint s){
        
        int index = sprints.indexOf(s);
        Task tmp = null;
        
        if (index > 0){
            tmp = sprints.get(index).removeTask(t);
        }
        
        //setChanged();
        //notifyObservers(this);
        return tmp;
        
    }
    
    public void addSprint(Sprint s){
        sprints.add(s);
        sprintId++;
        setChanged();
        notifyObservers(this);
    }
    
    public Sprint removeSprint(Sprint s){
        
        int index = sprints.indexOf(s);
        Sprint tmp = null;
        
        if (index >= 0){

            ArrayList<Task> tmpTaskList = new ArrayList(s.getTasks());
            tmp = sprints.remove(index);
            for (int i = 0 ; i < tmpTaskList.size() ; ++i){
                Task tmpTask = tmpTaskList.get(i);
                if (!tmpTask.getState().equals(blackmine.VAKIOT.State.COMPLETE)) {
                    tasks.addTask(tmp.removeTask(tmpTask));
                    
                }
                
            }
            
        }
        
        setChanged();
        notifyObservers(this);
        sprintsThatHasEnded.add(tmp);
        return tmp;
    }
    
    public void addMember(Member m){
        members.add(m);
        setChanged();
        notifyObservers(this);
    }
    
    public Member removeMember(Member m){
        int index = members.indexOf(m);
        Member tmp = null;
        
        if (index >= 0){
            tmp = members.remove(index);
            /*
             * if member had tasks assigned they must be removed
             */
            ArrayList<Task> tasklist = tmp.getTasks();
            
            for (int i = 0 ; i < tasklist.size() ; ++i){
                tasklist.get(i).setAssignee(null);
            }
        }
        
        setChanged();
        notifyObservers(this);
        return tmp;
    }
    
    public void moveTaskToSprint(Task task, Sprint sprint) throws Exception{
        
        /*
         * Removes the task from tasks and adds to sprints
         * task list
         */
        
        Task tmp = tasks.removeTask(task);
        int sprintIndex = sprints.indexOf(sprint);
        
        if (tmp != null && sprintIndex != -1){
            sprints.get(sprintIndex).addTask(tmp);
        }
        else{
            throw new Exception("no such task or sprint");
        }

        //setChanged();
        //notifyObservers(this);
        
    }
    
    public void moveTaskFromSprint(Task task, Sprint sprint) throws Exception{
        
        /*
         * Removes the task from the sprint and
         * returns it to projects task list (hopefully)
         */

        int sprintIndex = sprints.indexOf(sprint);
 
        if (sprintIndex != -1){
            Task tmpTask = sprints.get(sprintIndex).removeTask(task);
            if (tmpTask != null){
                tasks.addTask(tmpTask);
            }
            else {
                throw new Exception("no such task");
            }
        }
        else{
            throw new Exception("no such sprint");
        }
        //setChanged();
        //notifyObservers(this);
    }
    
    @Override
    public String toString(){
        return name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    /**
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks.getTasks();
    }
    
    /**
     * Metodi, joka palauttaa tehtävä-listasta tietyssä paikassa olevan tehtävän.
     * @param index monesko Task halutaan
     * @return tasks-listan Task numero index.
     */
    public Task getTask(int index) {
        return tasks.getTask(index);
    }

    /**
     * @return the sprints
     */
    public ArrayList<Sprint> getSprints() {
        return sprints;
    }
    
    public Sprint getSprint (int id){
        return sprints.get(id);
    }
    
    public void setSprint (Sprint uusi, int id){
        sprints.set(id, uusi);
    }
    /**
     * Metodi, joka palauttaa sprintit, jotka on saatu valmiiksi tai muuten vain
     * lopetettu
     * @return listan loppuneista sprinteistä
     */
    public ArrayList<Sprint> getCompletedSprints() {
        return sprintsThatHasEnded;
    }

    /**
     * @return the members
     */
    public ArrayList<Member> getMembers() {
        return members;
    }
    public Object[] getMembersTable(){
        /*
        String[] taulu = new String[members.size() + 1];
        taulu[0] = "No assignee";
        for(int i = 0; i < members.size(); i++) {
            taulu[i+1] = members.get(i).toString();
        }
        return taulu;
        * 
        */
        
        return members.toArray();
    }
    
    public TaskListModel getTableModel(){
        return tasks;
    }

    public TaskListModel getSprintTableModel(int id){
        return sprints.get(id).getTableModel();
    }
    
    public int getSprintCount(){
        return sprints.size();
    }
    
    public int getSprintId() {
        return sprintId;
    }
    
    public void listSorting(String sortType, boolean isTaskList, int sprintId) {
        
        if (isTaskList) {
            if (sortType.equals(VAKIOT.SORTBYPRIORITY)) {
                if (taskListLastSortedBy.equals(VAKIOT.SORTBYPRIORITY)) {
                    Collections.reverse(tasks.getTasks());
                } else {
                    Collections.sort(tasks.getTasks(), new TaskPriorityComparator());
                    taskListLastSortedBy = VAKIOT.SORTBYPRIORITY;
                }        
            }
        
            if (sortType.equals(VAKIOT.SORTBYDATE)) {
                if (taskListLastSortedBy.equals(VAKIOT.SORTBYDATE)) {
                    Collections.reverse(tasks.getTasks());
                } else {
                    Collections.sort(tasks.getTasks(), new TaskStartdateComparator());
                    taskListLastSortedBy = VAKIOT.SORTBYDATE;
                }  
            
            }
        
            if (sortType.equals(VAKIOT.SORTBYNAME)) {
                if (taskListLastSortedBy.equals(VAKIOT.SORTBYNAME)) {
                    Collections.reverse(tasks.getTasks());
                } else {
                    Collections.sort(tasks.getTasks(), new TaskNameComparator());
                    taskListLastSortedBy = VAKIOT.SORTBYNAME;
                }  
            
            }
        } else {
            if (sortType.equals(VAKIOT.SORTSPRINTBYNAME)) {
                Sprint tmp = sprints.get(sprintId);
                TaskListModel tmpModel = tmp.getTableModel();
                
                if (sprintListLastSortedBy.equals(VAKIOT.SORTSPRINTBYNAME)) {
                    Collections.reverse(tmpModel.getTasks());
                } else {
                    Collections.sort(tmpModel.getTasks(), new TaskNameComparator());
                    sprintListLastSortedBy = VAKIOT.SORTSPRINTBYNAME;
                }      
            }  
            
            if (sortType.equals(VAKIOT.SORTSPRINTBYDATE)) {
                Sprint tmp = sprints.get(sprintId);
                TaskListModel tmpModel = tmp.getTableModel();
                
                if (sprintListLastSortedBy.equals(VAKIOT.SORTSPRINTBYDATE)) {
                    Collections.reverse(tmpModel.getTasks());
                } else {
                    Collections.sort(tmpModel.getTasks(), new TaskStartdateComparator());
                    sprintListLastSortedBy = VAKIOT.SORTSPRINTBYDATE;
                }
            }
            
            if (sortType.equals(VAKIOT.SORTSPRINTBYPRIORITY)) {
                Sprint tmp = sprints.get(sprintId);
                TaskListModel tmpModel = tmp.getTableModel();
                
                if (sprintListLastSortedBy.equals(VAKIOT.SORTSPRINTBYPRIORITY)) {
                    Collections.reverse(tmpModel.getTasks());
                } else {
                    Collections.sort(tmpModel.getTasks(), new TaskPriorityComparator());
                    sprintListLastSortedBy = VAKIOT.SORTSPRINTBYPRIORITY;
                }
            }
        }
        
        
        
        setChanged();
        notifyObservers(this);
    }
    
    
}