/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackmine;

import datamodels.MainModel;
import views.MainView;

/**
 *
 * @author juliustorkkeli
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainModel model = new MainModel();
        new MainView(model).setVisible(true);
    }
}
