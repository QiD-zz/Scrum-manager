/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import blackmine.VAKIOT;
import datamodels.MainModel;
import datamodels.ProjectModel;
import datamodels.Sprint;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Paneeli, joka näyttää ne listana ne sprinti, jotka ovat päättyneet.
 * MVC:tä hieman rikkoen tässä paneelissa JList, jolla on oma mouselistener,
 * joka kuuntelee ainoastaan tuplaklikkauksia ja avaa niistä dialogin.
 * @author Kuisma Kuusniemi
 */
public class CompletedSprintsPanel extends JPanel 
{
    private ArrayList<Sprint> sprintit;
    private ProjectModel project;
    private JList lista;
    private DefaultListModel listaModel;
    
    public CompletedSprintsPanel(ProjectModel p) 
    {
        project = p;
        sprintit = project.getCompletedSprints();
        this.setPreferredSize(new Dimension(300, 300));
        this.initComponents();
      
      //  this.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    private void initComponents() {
        listaModel = new DefaultListModel();
        for (int i = 0; i < sprintit.size(); i++) {
            listaModel.addElement(sprintit.get(i));
            
        }
        lista = new JList(listaModel);
        lista.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    JList listTmp = (JList)e.getSource();
                    System.out.println(listTmp.getSelectedIndex());
                    Sprint tempSprint = (Sprint)listTmp.getModel().getElementAt(listTmp.getSelectedIndex());
                    CompletedSprintsSpecsPanel specs = new CompletedSprintsSpecsPanel(tempSprint);
                    JOptionPane.showMessageDialog(lista, specs, "Sprint #"+tempSprint.getId() , JOptionPane.PLAIN_MESSAGE);
                }
                
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) {  }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {  }
        });
        
        JScrollPane jsc = new JScrollPane(lista);
        CompletedSprintListCellRenderer rend = new CompletedSprintListCellRenderer();
        lista.setCellRenderer(rend);
        jsc.setBorder(BorderFactory.createTitledBorder("Completed Sprints"));
        jsc.setPreferredSize(new Dimension(260, 250));
        this.add(jsc);
        
        JLabel selite = new JLabel("Double-click a sprint to see additional info.");
        this.add(selite);
    }
}
