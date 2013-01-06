/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import blackmine.VAKIOT;
import datamodels.Sprint;
import datamodels.Task;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;
import java.util.Collections;

/**
 *
 * @author Hanne Korhonen 90404
 */
public class SprintBurnUpChart extends JPanel {
    /**
     * Vakioita joiden avulla voidaan muuttaa suhteellisen helposti miltä koordinaatisto
     * näyttää
     */

    private static int XO = 60;

    private static int LEVEYS = 500;
    private int KORKEUS = 300;

    private static int FRAME_WIDTH = 600;
    private static int FRAME_HEIGHT = 400;

    private static int VALIT = 5;
    private static int YLA = 5;

    private int id;
    private int tasksDone;
    private int tasksLkm;
    private long diffDays;

    private ArrayList<Task> tasks;
    private Sprint sprint;
    private ArrayList<Date> dates;
    private String[] customedDates;

    //Taulukot, joihin laitetaan x ja y pisteiden arvot.
    private long[] x;
    private int[] y;

    public SprintBurnUpChart(Sprint sp){
        sprint = sp;
        id = sprint.getId();
        tasks = sprint.getTasks();
        tasksLkm = tasks.size();
        tasksDone = 0;
        customedDates = new String[6];
        this.makeDates();
        this.getData();
        this.makePoints();
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    }
    private void getData(){
        dates = new ArrayList();
        int j = 0;
        boolean flag = true;
        for(int i = 0; i < tasksLkm; i++){
            System.out.println(tasks.get(i).getState());
            if(tasks.get(i).getState().equals(VAKIOT.State.COMPLETE)){
                tasksDone++;
                dates.add(tasks.get(i).getEndDate());
            }
        }
        Collections.sort(dates);
        System.out.println();
        //Tässä jätetään pois kaikki taskit jotka on saatu sprintin päättymisen jälkeen valmiiksi.
        while(flag){
            if(j >= dates.size())
                flag = false;
            else if(sprint.getEndDate().before(dates.get(j))){
                dates.remove(j);
                j--;
                tasksDone--;
            }
            j++;
        }
    }

    private void makePoints() {
        x = new long[tasksDone + 1];
        y = new int[tasksDone + 1];
        x[0] = XO;
        y[0] = KORKEUS + YLA;
        int xk = 0;
        int yk;
        int ind = 1;
        //System.out.println(sprint.getStartDate());
        //System.out.println(dates.get(0));
        //System.out.println("sprint.getStartDate().before(dates.get(i) " + (sprint.getStartDate().before(dates.get(0))));
        if (diffDays != 0 && tasks.size() != 0) {
            xk = LEVEYS / (int) diffDays;
            yk = KORKEUS / tasks.size();
            for (int i = 0; i < dates.size(); i++) {
                if (sprint.getStartDate().after(dates.get(i))) {
                    x[ind] = x[ind-1];
                    y[ind] = y[ind-1] - yk;
                }
                else if(sprint.getStartDate().before(dates.get(i)) || sprint.getEndDate().after(dates.get(i))){
                    x[ind] = XO + xk * this.calculate(i);
                    y[ind] = y[ind-1] - yk;
                }
                ind++;
            }
        }
        System.out.println(dates);
        for(int i = 0; i < x.length; i++){
            System.out.println(x[i] + "," + y[i]);
        }
    }
    private long calculate(int id){
        Date ad = dates.get(id);
        Date ed = sprint.getStartDate();
        return this.compareDates(ed, ad);
    }

    private void makeDates(){
        Date ad = sprint.getStartDate();
        Date ed = sprint.getEndDate();
        diffDays = compareDates(ad,ed);
        System.out.println(diffDays);

        int h = (int)diffDays/ VALIT;
        System.out.println(h);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(ad);
        //int day = c.get(Calendar.DATE);
        customedDates[0] = sprint.getFormattedStartDate();
        for(int i = 0; i < VALIT; i ++) {
            cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE) + h);
            int day = cal1.get(Calendar.DATE);
            int month = cal1.get(Calendar.MONTH);
            int year = cal1.get(Calendar.YEAR);
            String date = day + "." + month + "." + year;
            customedDates[i+1] = date;
            System.out.println("UUSI: " + date);
        }
        customedDates[5] = sprint.getFormattedEndDate();
        //cal2.set(2007, 5, 3);
    }
    private long compareDates(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);
        System.out.println(d1.toString());
        System.out.println(d2.toString());
        long milis1 = cal1.getTimeInMillis();
        long milis2 = cal2.getTimeInMillis();
        long diff = milis2 - milis1;
        long dD = diff / (24 * 60 * 60 * 1000);
        return dD;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        String errorMessage = "";
        //Piirretään tausta.
        graphics.setColor(Color.white);
        graphics.fillRect(XO, YLA, LEVEYS, KORKEUS);
        //graphics.setColor(Color.LIGHT_GRAY);
        int viiva = KORKEUS + YLA;
        graphics.setColor(Color.black);
        graphics.drawString("Sprint " + id, 0 ,10);
        if (tasks.size() != 0) {
            int yk = KORKEUS / tasks.size();
            if (diffDays != 0) {
                //piirretään horisontaalit viivat
                for (int i = 0; i <= tasks.size(); i++) {
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.drawLine(XO, viiva, (LEVEYS + XO), viiva);
                    //Piirretään Taskien lukumäärä vasemmalle
                    graphics.setColor(Color.DARK_GRAY);
                    graphics.drawString("" + i, XO - 10, viiva + 5);
                    viiva = viiva - yk;
                }
                //Piirretään pystysuuntaiset viivat
                viiva = XO;
                System.out.println("DiffDays: " +diffDays);
                int xk = LEVEYS / (int)diffDays;
                System.out.println("xk: " + xk);
                for (int i = 0; i < diffDays ; i++) {
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.drawLine(viiva, KORKEUS + YLA, viiva, YLA);
                    viiva = viiva + xk;
                }
                graphics.drawLine(viiva, KORKEUS + YLA, viiva, YLA);
                graphics.drawLine(XO + LEVEYS, KORKEUS + YLA, XO + LEVEYS, YLA);

                //Piirretään päivämäärät
                graphics.setColor(Color.DARK_GRAY);
                viiva = XO;
                int h = (int)diffDays/ VALIT;
                for (int i = 0; i < customedDates.length; i++) {
                    graphics.drawString(customedDates[i], viiva - 17, KORKEUS + 25);
                    graphics.drawLine(viiva, KORKEUS + YLA, viiva, KORKEUS);
                    viiva = viiva + xk*h;
                }
                //Piirretään käyrä
                graphics.setColor(Color.green);
                for (int i = 1; i < x.length; i++) {
                    graphics.setColor(Color.green);
                    if (i < x.length - 1 && x[i] == x[i + 1]) {
                        graphics.drawLine((int) x[i - 1], y[i - 1], (int) x[i + 1], y[i + 1]);
                        i++;
                    } else {
                        graphics.drawLine((int) x[i - 1], y[i - 1], (int) x[i], y[i]);
                    }
                }
                //graphics.drawLine((int) x[x.length - 1], y[y.length - 1], 600-XO, y[y.length - 1]);
            } else {
                errorMessage = "Your Sprint is in one day. We can't draw a chart";
            }
        } else {
            errorMessage = "You don't have any tasks in this sprint!";
        }
        graphics.setColor(Color.red);
        graphics.drawString(errorMessage, 100, 50);
    }
}
