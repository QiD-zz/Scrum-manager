/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author juliustorkkeli
 */
public class ProjectData implements Serializable {
    
    private String name;
    private ArrayList<Task> tasks;
    private ArrayList<SprintData> sprints;
    private ArrayList<Member> members;
    
    public ProjectData(ProjectModel model){
        
        name = model.getName();
        tasks = model.getTasks();
        sprints = new ArrayList<SprintData>();
        for (int i = 0 ; i < model.getSprintCount() ; ++i){
            sprints.add(new SprintData(model.getSprint(i)));
        }
        members = model.getMembers();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * @return the sprints
     */
    public ArrayList<SprintData> getSprints() {
        return sprints;
    }

    /**
     * @return the members
     */
    public ArrayList<Member> getMembers() {
        return members;
    }
    
    
}
