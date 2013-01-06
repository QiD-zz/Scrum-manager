/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import datamodels.ProjectModel;
import datamodels.Task;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import views.TaskEditorPanel;

/**
 *
 * 
 */
public class TaskListEditor extends AbstractCellEditor implements TableCellEditor {

    private TaskEditorPanel editor;
    
    public TaskListEditor(ProjectModel pModel){
        editor = new TaskEditorPanel(pModel, false);
    }
    @Override
    public Object getCellEditorValue() {
        return editor.getTask();
    }

    @Override
    public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            int clickCount = 2;
            return ((MouseEvent)evt).getClickCount() >= clickCount;
        }
        return true;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int col) {

        jtable.setRowHeight(row, 240);
        editor.setTask((Task)o);
        return editor;
   
    }
    
}
