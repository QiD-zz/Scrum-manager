/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author juliustorkkeli
 */

public class TaskListModel extends AbstractTableModel {

    private ArrayList<Task> tasks;
    private boolean isPrintList;
    
    public TaskListModel(){
        tasks = new ArrayList<Task>();
        isPrintList = false;
    }
    
    public TaskListModel(ArrayList<Task> list){
        tasks = list;
        isPrintList = false;
    }
    
    @Override
    public String getColumnName(int column) {
        return "Task";
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return true;
    }
    
    
    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() { 
            return 1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return tasks.get(row);
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        Task newValue = (Task)value;
        editTask(row, newValue);
    }
    
    public Task getTask(int index){
        
        if (index >= 0 && index < tasks.size())
            return tasks.get(index);
        else
            return null;
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }
    
    public void moveTask(int source, int target){
        
        if(source >= 0 && source < tasks.size()){
            //if target index was NOT the last index of the list
            if (target < tasks.size() && target >= 0)
                tasks.add(target, tasks.remove(source));
            else if (target == -1)//target WAS the last index
                tasks.add(tasks.remove(source));
            
            fireTableStructureChanged();
        }
        
    }
    
    public Task removeTask(Task task){
        
        int index = tasks.indexOf(task);
        Task tmp = null;
        
        if (index >= 0){
            tmp = tasks.remove(index);
            fireTableRowsDeleted(index, index);
        }
        return tmp;

    }
    
    public Task removeTask(int index){
        
        Task tmp = null;
        if (index < tasks.size() && index >= 0){
            tmp = tasks.remove(index);
            fireTableRowsDeleted(index, index);
        }
        return tmp;
    }
    
    public void addTask(Task task){
        if (isPrintList && task.getState() == null)
            task.setState(blackmine.VAKIOT.State.PENDING);
        tasks.add(task);
        fireTableStructureChanged();     
    }
    
    public void addTaskAt(Task task, int index){
        if (index < tasks.size() - 1 && index >= 0){
            if (isPrintList && task.getState() == null)
                task.setState(blackmine.VAKIOT.State.PENDING);
            tasks.add(index, task);
            fireTableStructureChanged();
        }
        else{
            addTask(task);
        }
    }
    
    public void editTask(int index, Task newTaskInfo){
        
        if (index < tasks.size() - 1 && index >= 0){
            tasks.set(index, newTaskInfo);
            fireTableRowsUpdated(index, index);
        }
    }
    
    public void clear(){
        int size = tasks.size();
        tasks.clear();
        fireTableRowsDeleted(0, size -1);
    }
    
    public void setSprintList(boolean b){
        isPrintList = b;
    }
    
    public boolean isSprintList(){
        return isPrintList;
    }
    
    public void setNewProject(ArrayList<Task> list){
        tasks = list;
    }

    
}
