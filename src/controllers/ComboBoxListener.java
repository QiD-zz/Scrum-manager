/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import datamodels.MainModel;
import datamodels.ProjectModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author uitu
 * 
 * Tämä kuuntelija kuuntelee vain ja ainoastaan päänäkymässä olevaa comboboxia,
 * josta voi vaihtaa aktiivista projektia.
 */
public class ComboBoxListener implements ItemListener {

    
    private MainModel model;
    
    public ComboBoxListener(MainModel m){
        model = m;
    }
    
  /*  @Override
    public void actionPerformed(ActionEvent e) {
        
        JComboBox source = (JComboBox)e.getSource();
        ProjectModel project = (ProjectModel)source.getSelectedItem();
        try {
            model.setActive(project);
        } catch (Exception ex) {
            Logger.getLogger(ComboBoxListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/

    @Override
    public void itemStateChanged(ItemEvent e) {
        
        JComboBox source = (JComboBox)e.getSource();
        if (source.getItemCount() > 0) {
            ProjectModel project = (ProjectModel)source.getSelectedItem();
            try {
                model.setActive(project);
            } catch (Exception ex) {
                Logger.getLogger(ComboBoxListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
