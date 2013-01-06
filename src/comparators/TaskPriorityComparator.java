/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import blackmine.VAKIOT;
import datamodels.Task;
import java.util.Comparator;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class TaskPriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task eka, Task toka) {
        int ekanArvo = (eka.getPriority() == VAKIOT.Priority.HIGH) ? 2 :
                (eka.getPriority() == VAKIOT.Priority.MEDIUM) ? 1 : 0;
        int tokanArvo = (toka.getPriority() == VAKIOT.Priority.HIGH) ? 2 :
                (toka.getPriority() == VAKIOT.Priority.MEDIUM) ? 1 : 0;
        System.out.println("Ekan arvo: "+ekanArvo+" Tokan arvo: "+ tokanArvo);
        if (ekanArvo == tokanArvo) {
            return 0;
        } else if (ekanArvo > tokanArvo) {
            return -1;
        } else {
            return 1;
        }
    }
    
}
