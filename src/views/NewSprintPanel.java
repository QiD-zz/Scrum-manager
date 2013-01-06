/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author Kuisma Kuusniemi 
 * Julius copas Kuisman NewTaskPanelista
 *
 */
public class NewSprintPanel extends JPanel{
  
    private JPanel startDatePanel, endDatePanel;
    
    private SpinnerModel startDayModel, endDayModel;
    private SpinnerModel startMonthModel, endMonthModel;
    private SpinnerModel startYearModel, endYearModel;
    
    private JSpinner startDay, endDay;
    private JSpinner startMonth, endMonth;
    private JSpinner startYear, endYear;
    
    public NewSprintPanel() {
        this.initComponents();
        this.setPreferredSize(new Dimension(300, 240));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        startDatePanel.add(startDay);
        startDatePanel.add(startMonth);
        startDatePanel.add(startYear);
        
        endDatePanel.add(endDay);
        endDatePanel.add(endMonth);
        endDatePanel.add(endYear);
        this.add(startDatePanel);
        this.add(endDatePanel);

    }

    private void initComponents() {

        startDatePanel = new JPanel();
        startDatePanel.setPreferredSize(new Dimension(300, 75));
        startDatePanel.setBorder(BorderFactory.createTitledBorder("Tehtävän alkamispäivämäärä"));
 
        startDayModel = new SpinnerNumberModel(15, 1, 31, 1);
        startDay = new JSpinner(startDayModel);
        startDay.setPreferredSize(new Dimension(80, 40));
        startDay.setBorder(BorderFactory.createTitledBorder("Päivä"));
        startMonthModel = new SpinnerNumberModel(6, 1, 12, 1);
        startMonth = new JSpinner(startMonthModel);
        startMonth.setPreferredSize(new Dimension(80, 40));
        startMonth.setBorder(BorderFactory.createTitledBorder("Kuukausi"));
        startYearModel = new SpinnerNumberModel(2012, 1900, 2100, 1);
        startYear = new JSpinner(startYearModel);
        startYear.setPreferredSize(new Dimension(80, 40));
        startYear.setBorder(BorderFactory.createTitledBorder("Vuosi"));
        Calendar now = Calendar.getInstance();
        startDayModel.setValue(now.get(Calendar.DAY_OF_MONTH));
        startMonthModel.setValue(now.get(Calendar.MONTH));
        startYearModel.setValue(now.get(Calendar.YEAR));
        
        endDatePanel = new JPanel();
        endDatePanel.setPreferredSize(new Dimension(300, 75));
        endDatePanel.setBorder(BorderFactory.createTitledBorder("Tehtävän loppumispäivämäärä"));
 
        endDayModel = new SpinnerNumberModel(15, 1, 31, 1);
        endDay = new JSpinner(endDayModel);
        endDay.setPreferredSize(new Dimension(80, 40));
        endDay.setBorder(BorderFactory.createTitledBorder("Päivä"));
        endMonthModel = new SpinnerNumberModel(6, 1, 12, 1);
        endMonth = new JSpinner(endMonthModel);
        endMonth.setPreferredSize(new Dimension(80, 40));
        endMonth.setBorder(BorderFactory.createTitledBorder("Kuukausi"));
        endYearModel = new SpinnerNumberModel(2012, 1900, 2100, 1);
        endYear = new JSpinner(endYearModel);
        endYear.setPreferredSize(new Dimension(80, 40));
        endYear.setBorder(BorderFactory.createTitledBorder("Vuosi"));
        Calendar end = Calendar.getInstance();
        endDayModel.setValue(end.get(Calendar.DAY_OF_MONTH));
        endMonthModel.setValue(end.get(Calendar.MONTH));
        endYearModel.setValue(end.get(Calendar.YEAR));

    }
    

    
    public Date getStartdate() {
        if (startDatePanel != null) {
            Calendar now = Calendar.getInstance();
            now.set((Integer)(startYearModel.getValue()), (Integer)(startMonthModel.getValue()), (Integer)(startDayModel.getValue()));
            return now.getTime();
        } else {
            return new Date();
        }
        
    }
    
    public Date getEnddate() {
        if (endDatePanel != null) {
            Calendar end = Calendar.getInstance();
            end.set((Integer)(endYearModel.getValue()), (Integer)(endMonthModel.getValue()), (Integer)(endDayModel.getValue()));
            return end.getTime();
        } else {
            return new Date();
        }
        
    }
}

