/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import datamodels.Task;
import datamodels.TaskListModel;
import datamodels.TaskTransferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class TableTransferHandler extends TransferHandler
{
    private int index = -1;
    private int osumaIndeksi = -1;
    private int rivienLkm;
    private boolean siirretty = false;
    private JTable lahtoTaulu;
    private JTable tuloTaulu;

    private boolean eiOsunutTauluun = false;
    
    //private boolean SiirtoTaulunSisalla;

    public TableTransferHandler()
    {
        super();
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        info.setShowDropLocation(true); 
        if (!(info.isDrop())) {
            return false;
        }
        
        /* Tämä ei vaikuttanut mitenkään. Koko canImport-funktiota
         * ei kutsuta, mikäli ei olla JTablen (tai kenties muun sopivan
         * komponentin) päällä. 
         * En tosin ymmärrä miksi.
         * 
         * 
        if (info.getComponent().getClass() != javax.swing.JTable.class){
            System.err.println("not import (not jtable)");
            return false;
        }
        * 
        */

        try {

            if (!info.isDataFlavorSupported(new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
               ";class=datamodels.Task"))) {
            //if (!info.isDataFlavorSupported(new DataFlavor(Class.forName("datamodels.Task"), "Task"))) {
                System.out.println("tänne kosahti");
                return false;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.err);
        }
        
        return true;
    }

    /**
     * metodi, joka luo transferable-olion, joka liikkuu drag and dropissa.
     * @param c komponentti, josta raahaus aloitetaan.
     * @return raahattavan asian transferable-oliona.
     */
    @Override
    protected Transferable createTransferable(JComponent c) {           

        lahtoTaulu = (JTable)c;            
        index = lahtoTaulu.getSelectedRow();
        rivienLkm = lahtoTaulu.getSelectedRowCount();
        Task[] itemit = new Task[rivienLkm];

        for (int i = 0; i < itemit.length; i++) {
            TaskListModel model = (TaskListModel) lahtoTaulu.getModel();
            itemit[i] = (Task)model.getTask(index+i);               
        }

        System.out.println("Create transferable:");
        return new TaskTransferable(itemit);
    }


    /**
     * We support only move actions.
    */
    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }

    /**
     * täällä tehdään drag and dropin varsinainen asioiden siirto
     * @param info transfersupport-olio, joka 
     * @return 
     */
    @Override
        public boolean importData(TransferHandler.TransferSupport info) {
            System.out.println("Import data");
            if (!info.isDrop()) {
                return false;
            }
 
            TransferHandler.DropLocation tiputusPaikka = info.getDropLocation();
                    
            
            tuloTaulu = (JTable)info.getComponent(); // get target list
            tuloTaulu.setRowHeight(50);
            TaskListModel tableModel = (TaskListModel)tuloTaulu.getModel();

            // Get the task that is being dropped.
            Transferable t = info.getTransferable();
            Task[] data;
            // get data
            try {
                DataFlavor objectFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
                data = (Task[])t.getTransferData(objectFlavor);
            }
            catch (Exception e) { 
                System.out.println("umfta");
                return false;
                
            }

            /*
             * Katsotaan mihin kohtaa listaa halutaan tiputtaa. Ensin etsitään
             * info-oliosta pudotuspaikka. Sitten tutkitaan mille riville pudotuspaikka osui.
             * Tässä käytetään listan getFixedCellHeight(), joka on määritely ikkunan rakentajassa.
            */               
            
        //    osumaIndeksi = -1;
            System.out.println("Osumapaikka x: "+tiputusPaikka.getDropPoint().getX()+" Osumapaikka y: "+tiputusPaikka.getDropPoint().getY());

            for (int i = 0; i<tableModel.getRowCount(); i++) {
                if (( i*tuloTaulu.getRowHeight()) < tiputusPaikka.getDropPoint().getY() && 
                   tiputusPaikka.getDropPoint().getY() < ( (i+1)*tuloTaulu.getRowHeight())) {
                    osumaIndeksi = i;
                }
            }
           System.out.println("Rivien korkeus: "+tuloTaulu.getRowHeight()*tuloTaulu.getRowCount()); 
           System.out.println("Osumaindeksi on: "+osumaIndeksi); 
        
           /*
            * Julius testaili

           if (osumaIndeksi == -1){
               for (int i = 0; i < data.length; i++) {
                   tableModel.addTask(data[i]);
               }
           }else{
               for (int i = 0; i < data.length; i++) {
                   tableModel.addTaskAt(data[i], osumaIndeksi+i);
               }
           }
           
           if (lahtoTaulu == tuloTaulu){
               SiirtoTaulunSisalla = true;
           }
           else{
               SiirtoTaulunSisalla = false;
           }
           * 
           */
           
           
           
           //Vector v = null;
            //Tarkistetaan vielä onko tiputuspaikka listan indeksien ulkopuolella, jolloin tiputetaan
            //uusi elementti listan viimeiseksi. Muussa tapauksessa elementti lisätään indeksin määrittämään kohtaan.
            for (int i = 0; i < data.length; i++) {
                System.out.println(data[i]);
                tableModel.addTask(data[i]);
            }
            
            if (!lahtoTaulu.equals(tuloTaulu)) {
                if (tiputusPaikka.getDropPoint().getY()>(tableModel.getRowCount()-rivienLkm)*tuloTaulu.getRowHeight()) {
                    System.out.println("Tiputuspaikka oli listan ulkopuolellA");
                }else {
                    if (osumaIndeksi != -1) {
                        //tableModel.moveRow(tableModel.getRowCount()-rivienLkm, tableModel.getRowCount()-1, osumaIndeksi);
                        for (int i = 0; i < rivienLkm; i++) {
                            tableModel.moveTask(tableModel.getRowCount()-1, osumaIndeksi);
                        }
                        
                    }
                }
               
                
                siirretty = true;
            } else {
                if (tiputusPaikka.getDropPoint().getY() > (tableModel.getRowCount()-rivienLkm)*tuloTaulu.getRowHeight()) {
                    siirretty = true;
                } else {
                    System.out.print("false on");
                    siirretty = false;
                    //  tableModel.moveRow(tableModel.getRowCount()-1,  tableModel.getRowCount(), osumaIndeksi);
                    //  tableModel.removeRow(index);
                }
            }
            System.out.println("Import data succesful");
            return true;
        }

        /**
         * täällä poistetaan lähtötaulusta ne alkiot, jotka siirrettiin toisaalle
         * @param c taulu, josta alkioita siirrellään
         * @param data jota siirreltiin
         * @param action mitä käskyjä DnD on saanut. Tässä tapauksessa se on aina move,
         * joten tämä on turhahko parametri tässä ohjelmassa.
         */    
    @Override
        protected void exportDone(JComponent c, Transferable data, int action) {
        System.out.println("export done");
            // drop successful
            JTable source = (JTable)c;
            TaskListModel tableModel  = (TaskListModel)source.getModel();

         
          
            if(tuloTaulu != null){
        
                if (!eiOsunutTauluun) {
                    for (int i = 0; i < rivienLkm; i++) {
                        tableModel.removeTask(index);  
                        System.out.println("Poistettiin: "+i);
                    }

                    if (!siirretty) {
                        TaskListModel tuloModel = (TaskListModel)tuloTaulu.getModel();
                        for (int i = 0; i < rivienLkm; i++) {
                            System.out.println("Siirretään "+tuloModel.getTask(tuloModel.getRowCount()-1));
                            tuloModel.moveTask(tuloModel.getRowCount()-1, osumaIndeksi);

                        }

                    }
                }
            }

          
            tuloTaulu = null;
            lahtoTaulu = null;
            siirretty = false;
            eiOsunutTauluun = false;
            index = -1;
        }
        
        @Override
        public Icon getVisualRepresentation(Transferable t) {
         
            return super.getVisualRepresentation(t);
        }
        
}
