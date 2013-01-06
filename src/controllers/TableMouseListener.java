/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import views.MainView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;

/**
 *
 * @author juliustorkkeli
 */
public class TableMouseListener extends MouseAdapter implements MouseListener {

    private MainView view;
    private JTable table;
    
    public TableMouseListener(MainView v){
        view = v;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        maybeShowPopup(me);       
    }

    @Override
    public void mouseClicked(MouseEvent me) { 
        maybeShowPopup(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        maybeShowPopup(me);     
    }
    
    private void maybeShowPopup(MouseEvent me){
        if (me.isPopupTrigger()){
            view.showTablePopup(me);
        } 
    }

    
}
