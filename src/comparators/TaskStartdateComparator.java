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
public class TaskStartdateComparator implements Comparator<Task> {

    @Override
    public int compare(Task eka, Task toka) {

        Date ekanAlku = eka.getStartDate();
        Date tokanAlku = toka.getStartDate();
      //  System.out.println(eka.getTaskname()+": "+ekanAlku+" "+toka.getTaskname()+": "+ tokanAlku);
        if (ekanAlku != null && tokanAlku != null) {
            if (ekanAlku.equals(tokanAlku)) {
                return 0;
            } else if (ekanAlku.before(tokanAlku)) {
            return -1;
            } else {
            return 1;
            }
        } else if (ekanAlku == null && tokanAlku != null) {
            return 1;
        } else if (ekanAlku != null && tokanAlku == null) {
            return -1;
        } else {
            return 0;
        }
        
    }
    
}
