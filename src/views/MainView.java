/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import blackmine.VAKIOT;
import controllers.ComboBoxListener;
import controllers.MainController;
import controllers.TableMouseListener;
import controllers.TableTransferHandler;
import controllers.TaskListEditor;
import datamodels.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Pääikkuna
 * @authors juliustorkkeli, Kuisma Kuusniemi, Hanne Korhonen
 */
public class MainView extends JFrame implements Observer{

  /* 
    private int indeksi = 2;*/
    private JPanel oikea;
    private JPanel vasen;
    private JPanel vasenYla;
    private JPanel vasenAla;
    private JPanel oikeaYla;
    private JPanel keskikaista;
    private JScrollPane taskSp;
    private JTabbedPane tabit;
    private TableTransferHandler dragTransferHandleri;
    
    private JTable taskList;
    private TaskListModel taskModel;
    
    private JComboBox projectComboBox;
    private ComboBoxListener cbListener;
    
    private JButton lisaaProjekti;
    private JButton suljeProjekti;
    private JButton lisaaTask;
    private JButton siirraSprinttiin;
    private JButton poistaSprintista;
    private JButton uusiSprintti;
    private JButton poistaSprintti;
    
    private JPopupMenu popupMenu;
    private JPopupMenu sprintPopupMenu;
    
    private JMenuBar mBar;
    private JMenu file;
    private JMenu members;
    private JMenu stats;
    private JMenuItem newProject;
    private JMenuItem openProject;
    private JMenuItem saveProject;
    private JMenuItem exit;
    private JMenuItem addMember;
    private JMenuItem removeMember;
    private JMenuItem showMemberStats;
    private JMenuItem completedSprint;
    private JRadioButtonMenuItem isoinIkkuna;
    private JRadioButtonMenuItem isoIkkuna;
    private JRadioButtonMenuItem normaaliIkkuna;
    private JRadioButtonMenuItem pieniIkkuna;
    private ButtonGroup ikkunanKoot;
    private JMenu windowSize;

    private MainController maintControl;
    private TableMouseListener mouseListener;
    private MainModel model;
    
    
    /**
     * rakennin
     * @param m saa parametrina ohjelman mallin
     */
    public MainView(MainModel m){
        super("BLACKMINE");
        model = m;
        model.addObserver(this);       
        this.addMenubar();
        
        vasen = new JPanel();
        oikea = new JPanel();
        
        keskikaista = new JPanel();
        keskikaista.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHEMIDPANEL,VAKIOT.NORMALHEIGHTOFTHEWINDOW));
        siirraSprinttiin = new JButton(VAKIOT.RIGHT);
        siirraSprinttiin.setToolTipText("Move selected task from tasklist to sprint by"
                + "clicking this button.");
        poistaSprintista = new JButton(VAKIOT.LEFT);
        poistaSprintista.setToolTipText("Move selected task from sprint back to"
                + " tasklist by clicking this button.");
        keskikaista.setLayout(new GridLayout(11, 1));
      /*
       * Pistin nämä napit keskelle nyt tähän tyyliin lisäämällä tyhjiä jpaneeleja
       * keskikaistalle gridlayouttiin, jolloin napit tulee kivasti listojen väliin. 
        */
        for (int i = 0; i < 4; i++) {
            keskikaista.add(new JPanel());
        }
        keskikaista.add(siirraSprinttiin);
        keskikaista.add(poistaSprintista);    
        
        vasen.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS,
                VAKIOT.NORMALHEIGHTOFTHEWINDOW));
        oikea.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS,
                VAKIOT.NORMALHEIGHTOFTHEWINDOW));
        vasenYla = new JPanel();
        vasenYla.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS, 80));
        vasenAla = new JPanel();
        
        tabit = new JTabbedPane();
        tabit.setTabPlacement(JTabbedPane.TOP);
        tabit.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS,
                VAKIOT.NORMALHEIGHTOFTHESPRINTLIST));
        tabit.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
         
 
        projectComboBox = new JComboBox();
            
        uusiSprintti = new JButton("Add sprint");
        poistaSprintti = new JButton("Remove sprint");
               
        oikeaYla = new JPanel(new FlowLayout(FlowLayout.CENTER));
        oikeaYla.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS, 50));
        oikeaYla.add(uusiSprintti);
        oikeaYla.add(poistaSprintti);
        oikea.add(oikeaYla);
        oikea.add(tabit);
        
        taskList = new JTable();
        taskSp = new JScrollPane(taskList);
        taskSp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        lisaaProjekti = new JButton("New project");
        lisaaTask = new JButton("New task");
        suljeProjekti = new JButton(VAKIOT.CLOSEPROJECT);
        
        vasenAla = new JPanel();
        
        vasen.setLayout(new BorderLayout());
        vasenYla.add(lisaaProjekti);
        vasenYla.add(suljeProjekti);
        vasenYla.add(lisaaTask);
        vasenYla.add(projectComboBox);
        vasen.add(vasenYla,BorderLayout.PAGE_START);
        vasen.add(taskSp,BorderLayout.CENTER);
      //  vasen.add(taskList,BorderLayout.CENTER);
        vasen.add(vasenAla,BorderLayout.PAGE_END);
       
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.getContentPane().add(vasen);
        this.getContentPane().add(keskikaista);
        this.getContentPane().add(oikea);
        this.getContentPane().setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHEWINDOW,
                VAKIOT.NORMALHEIGHTOFTHEWINDOW));
      //  this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.pack();
        
        this.setControllersAndHandlers();
        this.createPopupMenus();
        this.setResizable(false);
        /*
         * Siirsin kaikki debuggaustaulut ja taskit tänne, joten ne voi helposti
         * kerralla poistaa, kun projektin implementointi valmistuu.
         */
        //this.lisaaDebuggausTaskitTauluihin();

        taskList.setModel(new TaskListModel());
        taskList.getColumnModel().getColumn(0).setCellRenderer(new TaskListCellRenderer());
        taskList.setAutoCreateColumnsFromModel(false);
        
        setButtonsEnabled(false);
        
    }
    
    public TableTransferHandler getDragTransferHandler() {
        return dragTransferHandleri;
    }
    
    public JTabbedPane getTabit() {
        return tabit;
    }

    public int getActiveSprint(){

        return tabit.getSelectedIndex();

    }
    
    public Task getActiveTask(){
        /*
         * Mistä taskista on kyse (vasen lista)
         * 
         */
        
        TaskListModel tmp = (TaskListModel) taskList.getModel();
        return tmp.getTask(taskList.getSelectedRow());
    }
    
    public int getSelectedTasklistIndex() {
        return taskList.getSelectedRow();
    }
    
    public int getSelectedSprintIndex() {
        JTable tmpTable = this.getActiveSprintTable();       
        
        return tmpTable.getSelectedRow();
    }
    
    
    
    private JTable getActiveSprintTable() {
        JScrollPane scrollpane = (JScrollPane) tabit.getComponentAt(tabit.getSelectedIndex());
        JViewport viewport = scrollpane.getViewport(); 
        JTable tmpTable = (JTable) viewport.getView();       
        
        return tmpTable;
    }
    
    public Task getActiveSprintTask(){
        /*
         * Mistä taskista on kyse (oikea lista)
         */
        JTable tmpTable = this.getActiveSprintTable();
        TaskListModel tmpModel = (TaskListModel) tmpTable.getModel();
        
        return tmpModel.getTask(tmpTable.getSelectedRow());
        
    }

    @Override
    public void update(Observable o, Object o1) {
        
        //catch the project
        ProjectModel project = (ProjectModel)o1;
        
        if (project != null){
            //System.out.println("update");
            setButtonsEnabled(true);
            
            ArrayList<Sprint> sprints = project.getSprints();
            ArrayList<Member> members2 = project.getMembers();
            ArrayList<ProjectModel> projects = model.getProjects();
            
            projectComboBox.removeItemListener(cbListener);
            projectComboBox.removeAllItems();
            for (int i = 0 ; i < projects.size() ; ++i){
                projectComboBox.addItem(projects.get(i));
            }
            projectComboBox.setSelectedItem(project);
            projectComboBox.addItemListener(cbListener);
            
            taskList.setModel(project.getTableModel());

            taskList.getColumnModel().getColumn(0).setCellEditor(new TaskListEditor(project));

            
            tabit.removeAll();
            
            for (int i = 0 ; i < sprints.size() ; ++i){
                 
                TaskListModel sprintModel = sprints.get(i).getTableModel();
                JTable newTable = new JTable(sprintModel);
                sprintModel.setSprintList(true); 
                newTable.setTransferHandler(dragTransferHandleri);
                newTable.setFillsViewportHeight(true);
                newTable.setDragEnabled(true);
                newTable.addMouseListener(mouseListener);
                newTable.getColumnModel().getColumn(0).setCellRenderer(new TaskListCellRenderer());
                newTable.getColumnModel().getColumn(0).setCellEditor(new TaskListEditor(project));
                newTable.setAutoCreateColumnsFromModel(false);
                
                
                JScrollPane sc = new JScrollPane(newTable);
                sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                tabit.add( sprints.get(i).getId() + ". Sprint", sc);
            }
            
            
        }
        else {
            taskList.setModel(new TaskListModel());
            tabit.removeAll();
            setButtonsEnabled(false);
            projectComboBox.removeAllItems();
            //System.out.println("There is no project");
        }

    }

    private void addMenubar() {
        mBar = new JMenuBar();
        file = new JMenu("File");
        stats = new JMenu("Stats");
        newProject = new JMenuItem("New Project");
        openProject = new JMenuItem("Open Project");
        saveProject = new JMenuItem("Save Project");
        exit = new JMenuItem("Exit Program");
        file.add(newProject);
        file.add(openProject);
        file.add(saveProject);
        file.add(exit);
        mBar.add(file);
        
        isoinIkkuna = new JRadioButtonMenuItem("1200*700");
        isoIkkuna = new JRadioButtonMenuItem("800*600");
        normaaliIkkuna = new JRadioButtonMenuItem("650*500", true);
        pieniIkkuna = new JRadioButtonMenuItem("600*400");
        ikkunanKoot = new ButtonGroup();
        ikkunanKoot.add(isoinIkkuna);
        ikkunanKoot.add(isoIkkuna);
        ikkunanKoot.add(normaaliIkkuna);
        ikkunanKoot.add(pieniIkkuna);
       
        windowSize = new JMenu("Window size");
        windowSize.add(isoinIkkuna);
        windowSize.add(isoIkkuna);
        windowSize.add(normaaliIkkuna);
        windowSize.add(pieniIkkuna);
                
        completedSprint = new JMenuItem("Show completed sprints");
        stats.add(completedSprint);
        mBar.add(stats);
        
        members = new JMenu("Members");
        addMember = new JMenuItem("New Member");
        removeMember = new JMenuItem("Remove Member");
        showMemberStats = new JMenuItem("Show Member Stats");
        members.add(addMember);
        members.add(removeMember);
        members.add(showMemberStats);
        mBar.add(members); 
        mBar.add(windowSize);
        
        this.setJMenuBar(mBar);
        
    }

    private void setControllersAndHandlers() {
        
        maintControl = new MainController(model, this);
        mouseListener = new TableMouseListener(this);
        this.addWindowListener(maintControl);
        siirraSprinttiin.addActionListener(maintControl);
        poistaSprintista.addActionListener(maintControl);
        
        lisaaProjekti.addActionListener(maintControl);
        lisaaProjekti.setActionCommand(VAKIOT.NEWPROJECT);
        
        suljeProjekti.addActionListener(maintControl);
        suljeProjekti.setActionCommand(VAKIOT.CLOSEPROJECT);
        
        uusiSprintti.addActionListener(maintControl);    
        poistaSprintti.addActionListener(maintControl);
        uusiSprintti.setActionCommand(VAKIOT.NEWSPRINT);
        poistaSprintti.setActionCommand(VAKIOT.REMOVESPRINT);
        lisaaTask.setActionCommand(VAKIOT.NEWTASK);
        lisaaTask.addActionListener(maintControl);
        
        saveProject.addActionListener(maintControl);
        saveProject.setActionCommand(VAKIOT.SAVEPROJECT);
        
        openProject.addActionListener(maintControl);
        openProject.setActionCommand(VAKIOT.OPENPROJECT);
        
        exit.addActionListener(maintControl);
        exit.setActionCommand(VAKIOT.EXIT);
        
        addMember.addActionListener(maintControl);
        addMember.setActionCommand(VAKIOT.ADDMEMBER);
        
        removeMember.addActionListener(maintControl);
        removeMember.setActionCommand(VAKIOT.REMOVEMEMBER);
        
        showMemberStats.addActionListener(maintControl);
        showMemberStats.setActionCommand(VAKIOT.SHOWMEMBERSTATS);
        
        completedSprint.addActionListener(maintControl);
        completedSprint.setActionCommand(VAKIOT.SHOWCOMPLETEDSPRINTS);
        
        dragTransferHandleri = new TableTransferHandler();
        taskList.setDragEnabled(true);
        taskList.setTransferHandler(dragTransferHandleri);
        taskList.addMouseListener(mouseListener);
        taskList.setFillsViewportHeight(true);
        
        cbListener = new ComboBoxListener(model);
     //   projectComboBox.addActionListener(cbListener);
        projectComboBox.addItemListener(cbListener);
        
        isoinIkkuna.addActionListener(maintControl);
        isoinIkkuna.setActionCommand(VAKIOT.WIDEWINDOWSETTINGS);
        isoIkkuna.addActionListener(maintControl);
        isoIkkuna.setActionCommand(VAKIOT.BIGWINDOWSETTINGS);
        pieniIkkuna.addActionListener(maintControl);
        pieniIkkuna.setActionCommand(VAKIOT.SMALLWINDOWSETTINGS);
        normaaliIkkuna.addActionListener(maintControl);
        normaaliIkkuna.setActionCommand(VAKIOT.NORMALWINDOWSETTINGS);
    }
    
    private void lisaaDebuggausTaskitTauluihin() {
        
        ProjectModel testProject = new ProjectModel("TEST");
        
        try {
            model.addProject(testProject, true);
            testProject.addObserver(this);
        } catch (Exception ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
        Task eka = new Task("eka");
        Task toka = new Task("toka");        
        Task kolmas = new Task("kolmas");
        Task neljas = new Task("Neljäs");
        toka.setPriority(VAKIOT.Priority.MEDIUM);
        kolmas.setPriority(VAKIOT.Priority.HIGH);
        neljas.setState(VAKIOT.State.COMPLETE);
        kolmas.setEndDate(new Date(2012, 7,20));
        neljas.setEndDate(new Date(2012, 7,26));
        neljas.setPriority(VAKIOT.Priority.HIGH);
        //eka.setAssignee(new Member("Jokke"));
        eka.setState(VAKIOT.State.IN_PROGRESS);
        kolmas.setState(VAKIOT.State.COMPLETE);
        eka.setTaskWorkEstimation(150);
        eka.setTaskWorkDone(75);
        kolmas.setTaskWorkEstimation(150);
        kolmas.setTaskWorkDone(150);
        eka.setStartDate(new Date(1950, 11,11));
        toka.setStartDate(new Date(1980,3,3));
        kolmas.setStartDate(new Date(1980,3,3));
        
        testProject.addTask(eka);
        testProject.addTask(toka);
        testProject.addTask(kolmas);
        testProject.addTask(neljas);
        
        testProject.addSprint(new Sprint(1, new Date(2012, 7,15), new Date(2012, 8,15)));
         
        
    }
    
    private void createPopupMenus(){
        
        popupMenu = new JPopupMenu();
        sprintPopupMenu = new JPopupMenu();
        
        JMenuItem edit = new JMenuItem("Edit task");
        edit.setActionCommand(VAKIOT.EDITSPRINTTASK);
        edit.addActionListener(maintControl);
        
        JMenuItem edit2 = new JMenuItem("Edit task");
        edit2.setActionCommand(VAKIOT.EDITTASK);
        edit2.addActionListener(maintControl);
        
        JMenuItem removeFromSprint = new JMenuItem("Remove task from this sprint");
        removeFromSprint.setActionCommand(VAKIOT.MOVETASKFROMSPRINT);
        removeFromSprint.addActionListener(maintControl);
        
        JMenuItem remove = new JMenuItem("Remove task");
        remove.setActionCommand(VAKIOT.REMOVETASK);
        remove.addActionListener(maintControl);

        JMenuItem sprintChart = new JMenuItem("Show Burn-Up -Chart");
        sprintChart.setActionCommand(VAKIOT.SHOWSPRINTCHART);
        sprintChart.addActionListener(maintControl);
        
        JMenuItem sortByPriority = new JMenuItem("priority");
        sortByPriority.setActionCommand(VAKIOT.SORTBYPRIORITY);
        sortByPriority.addActionListener(maintControl);
        
        JMenuItem sortByDate = new JMenuItem("date");
        sortByDate.setActionCommand(VAKIOT.SORTBYDATE);
        sortByDate.addActionListener(maintControl);
        
        JMenuItem sortByName = new JMenuItem("name");
        sortByName.setActionCommand(VAKIOT.SORTBYNAME);
        sortByName.addActionListener(maintControl);
        
        JMenuItem sortSprintByName = new JMenuItem("name");
        sortSprintByName.setActionCommand(VAKIOT.SORTSPRINTBYNAME);
        sortSprintByName.addActionListener(maintControl);
        
        JMenuItem sortSprintByDate = new JMenuItem("date");
        sortSprintByDate.setActionCommand(VAKIOT.SORTSPRINTBYDATE);
        sortSprintByDate.addActionListener(maintControl);
        
        JMenuItem sortSprintByPriority = new JMenuItem("priority");
        sortSprintByPriority.setActionCommand(VAKIOT.SORTSPRINTBYPRIORITY);
        sortSprintByPriority.addActionListener(maintControl);
        
        JMenu taskListsubMenu = new JMenu("Sort by");
        JMenu sprintSubMenu = new JMenu("Sort by");
        
        taskListsubMenu.add(sortByPriority);
        taskListsubMenu.add(sortByDate);
        taskListsubMenu.add(sortByName);
        
        sprintSubMenu.add(sortSprintByPriority);
        sprintSubMenu.add(sortSprintByDate);
        sprintSubMenu.add(sortSprintByName);
        
        sprintPopupMenu.add(edit);
        sprintPopupMenu.add(removeFromSprint);
        sprintPopupMenu.add(sprintSubMenu);
        sprintPopupMenu.add(sprintChart);
        
        popupMenu.add(edit2);
        popupMenu.add(remove);
        popupMenu.add(taskListsubMenu);
        
        
    }
    
    public void showTablePopup(MouseEvent me){
        
        JTable source = (JTable) me.getSource();
        TaskListModel sourceModel = (TaskListModel) source.getModel();
        
        if (sourceModel.isSprintList()){
            sprintPopupMenu.show(source, me.getX(), me.getY());
        }
        else{
            popupMenu.show(source, me.getX(), me.getY());
        }
        
        
    }
    
    private void setButtonsEnabled(boolean state) {
        suljeProjekti.setEnabled(state);
        lisaaTask.setEnabled(state);
        siirraSprinttiin.setEnabled(state);
        poistaSprintista.setEnabled(state);
        poistaSprintti.setEnabled(state);
        uusiSprintti.setEnabled(state);
        
        popupMenu.setEnabled(state);
        sprintPopupMenu.setEnabled(state);
        
        members.setEnabled(state);
        stats.setEnabled(state);
        
         saveProject.setEnabled(state);

        projectComboBox.setEnabled(state);
    }

    public void setWindowSize() {
        if (isoIkkuna.isSelected()) {
            System.out.println("tultiin tänne");
            this.getContentPane().setPreferredSize(new Dimension(VAKIOT.BIGWIDTHOFTHEWINDOW,
                    VAKIOT.BIGHEIGHTOFTHEWINDOW));
            vasen.setPreferredSize(new Dimension(VAKIOT.BIGWIDTHOFTHELISTPANELS,
                    VAKIOT.BIGHEIGHTOFTHEWINDOW));
            oikea.setPreferredSize(new Dimension(VAKIOT.BIGWIDTHOFTHELISTPANELS
                    ,VAKIOT.BIGHEIGHTOFTHEWINDOW));
      
            keskikaista.setPreferredSize(new Dimension(VAKIOT.BIGWIDTHOFTHEMIDPANEL
                    ,VAKIOT.BIGHEIGHTOFTHEWINDOW));
       
            tabit.setPreferredSize(new Dimension(VAKIOT.BIGWIDTHOFTHELISTPANELS,
                    VAKIOT.BIGHEIGHTOFTHESPRINTLIST));
      
            this.pack();
        } else if (normaaliIkkuna.isSelected()) {
            this.getContentPane().setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHEWINDOW,
                    VAKIOT.NORMALHEIGHTOFTHEWINDOW));
            vasen.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS,
                    VAKIOT.NORMALHEIGHTOFTHEWINDOW));
            oikea.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS
                    ,VAKIOT.NORMALHEIGHTOFTHEWINDOW));
      
            keskikaista.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHEMIDPANEL
                    ,VAKIOT.NORMALHEIGHTOFTHEWINDOW));
       
            tabit.setPreferredSize(new Dimension(VAKIOT.NORMALWIDTHOFTHELISTPANELS,
                    VAKIOT.NORMALHEIGHTOFTHESPRINTLIST));
      
            this.pack();
        } else if (isoinIkkuna.isSelected()) {
            this.getContentPane().setPreferredSize(new Dimension(VAKIOT.WIDEWIDTHOFTHEWINDOW,
                    VAKIOT.WIDEHEIGHTOFTHEWINDOW));
            vasen.setPreferredSize(new Dimension(VAKIOT.WIDEWIDTHOFTHELISTPANELS,
                    VAKIOT.WIDEHEIGHTOFTHEWINDOW));
            oikea.setPreferredSize(new Dimension(VAKIOT.WIDEWIDTHOFTHELISTPANELS
                    ,VAKIOT.WIDEHEIGHTOFTHEWINDOW));
      
            keskikaista.setPreferredSize(new Dimension(VAKIOT.WIDEWIDTHOFTHEMIDPANEL
                    ,VAKIOT.WIDEHEIGHTOFTHEWINDOW));
       
            tabit.setPreferredSize(new Dimension(VAKIOT.WIDEWIDTHOFTHELISTPANELS,
                    VAKIOT.WIDEHEIGHTOFTHESPRINTLIST));
      
            this.pack();
        } else {
            this.getContentPane().setPreferredSize(new Dimension(VAKIOT.SMALLWIDTHOFTHEWINDOW,
                    VAKIOT.SMALLHEIGHTOFTHEWINDOW));
            vasen.setPreferredSize(new Dimension(VAKIOT.SMALLWIDTHOFTHELISTPANELS,
                    VAKIOT.SMALLHEIGHTOFTHEWINDOW));
            oikea.setPreferredSize(new Dimension(VAKIOT.SMALLWIDTHOFTHELISTPANELS
                    ,VAKIOT.SMALLHEIGHTOFTHEWINDOW));
      
            keskikaista.setPreferredSize(new Dimension(VAKIOT.SMALLWIDTHOFTHEMIDPANEL
                    ,VAKIOT.SMALLHEIGHTOFTHEWINDOW));
       
            tabit.setPreferredSize(new Dimension(VAKIOT.SMALLWIDTHOFTHELISTPANELS,
                    VAKIOT.SMALLHEIGHTOFTHESPRINTLIST));
      
            this.pack();
        }
    }
}
