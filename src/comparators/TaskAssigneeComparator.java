/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import datamodels.Member;
import datamodels.Task;
import java.util.Comparator;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class TaskAssigneeComparator implements Comparator<Task> {

    @Override
    public int compare(Task eka, Task toka) {
        String ekanTekija = eka.getAssignee().toString();
        String tokamTekija = toka.getAssignee().toString();
        return ekanTekija.compareToIgnoreCase(tokamTekija);
    }
    
}
