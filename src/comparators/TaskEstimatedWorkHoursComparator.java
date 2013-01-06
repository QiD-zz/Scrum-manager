/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import datamodels.Task;
import java.util.Comparator;

/**
 *
 * @author Kuisma Kuusniemi
 */
public class TaskEstimatedWorkHoursComparator implements Comparator<Task> {

    @Override
    public int compare(Task eka, Task toka) {
        Integer ekanAikaArvio = eka.getTaskWorkEstimation();
        Integer tokanAikaArvio = toka.getTaskWorkEstimation();
        return ekanAikaArvio.compareTo(tokanAikaArvio);
    }    
}
