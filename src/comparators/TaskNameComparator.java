/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import datamodels.Task;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class TaskNameComparator implements Comparator<Task> {

    @Override
    public int compare(Task eka, Task toka) {
        String ekanNimi = eka.getTaskname();
        String tokanNimi = toka.getTaskname();
        return ekanNimi.compareToIgnoreCase(tokanNimi);
    }
    
}
