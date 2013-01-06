/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class TaskTransferable implements Transferable {

        public TaskTransferable(Task[] tieto) {
            data = tieto;
        }       
        
        private Task[] data;


        @Override
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor tuettu = null;
            try {
               //tuettu = new DataFlavor(Class.forName("datamodels.Task"), "Task");
               tuettu = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
               ";class=datamodels.Task");
                // tuettu =  new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace(System.err);
            }
            
            DataFlavor[] temp = {tuettu};
            return temp;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            if (flavor.getPrimaryType().equals(DataFlavor.javaJVMLocalObjectMimeType)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            //System.out.println("Getting transfer data");
            //System.out.println("flavor:" + flavor.toString());
            if (flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)) {
                //System.out.println("Wut?");
                return data;
            } else {
                //System.out.println("And we haaave aaan errooor!");
                throw new UnsupportedFlavorException(flavor);
            }
        }
        
    
    
}
