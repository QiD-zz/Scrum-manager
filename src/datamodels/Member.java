/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datamodels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Hanne Korhonen 90404
 */
public class Member implements Serializable {
    private String name;
    private ArrayList<Task> tasks;

    public Member(String name){
        this.name = name;
        //tasks = new TaskListModel();
        tasks = new ArrayList<Task>();
    }
    public String getMemberName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void addTask(Task t){
        if (!tasks.contains(t)){
            tasks.add(t);
        }
    }
    public Task removeTask(Task t){
        //return tasks.remove(t);
        int index = tasks.indexOf(t);
        Task tmp = null;
        
        if (index >= 0){
            tmp = tasks.remove(index);
        }
        
        //setChanged();
        //notifyObservers(this);
        return tmp;
    }
    public ArrayList<Task> getTasks(){
        return tasks;
    }

    @Override
    public String toString(){
        return name;
    }

}
