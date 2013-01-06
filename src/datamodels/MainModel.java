/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodels;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JOptionPane;

/**
 *
 * @author juliustorkkeli
 */
public class MainModel extends Observable {
    
    private ArrayList<ProjectModel> projects;
    private ProjectModel activeProject;
    
    public MainModel (){
        
        projects = new ArrayList<ProjectModel>();
        activeProject = null;
        
    }
    
    public void loadProject(String url) throws Exception{
        
        ProjectData projectData = null;
        
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream(url));
        projectData = (ProjectData)ois.readObject();
        ois.close();
        
        if (projectData != null){
            addProject(new ProjectModel(projectData),true);
        }
        else{
            throw new Exception("invalid file or project already exists");
        }
        
    }
    
    public void saveProject(String url) throws Exception{
        
        FileOutputStream fos = new FileOutputStream(new File(url));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        ProjectData projectData = new ProjectData(activeProject);
        oos.writeObject(projectData);
        oos.close();
    }
    
    public void addProject(ProjectModel p, boolean active) throws Exception {

        boolean uusiProjektiAvataan = true;
        for (int i = 0 ; i < getProjects().size() ; ++i){
            if (p.getName().equals(projects.get(i).getName())) {
                //overwrite-dialogi-versio, jos sama nimi.
                int valinta = JOptionPane.showConfirmDialog(null, "Same project"
                      + " already exists. Overwrite?","Question", JOptionPane.OK_CANCEL_OPTION,
                      JOptionPane.QUESTION_MESSAGE);
 
                if (valinta == JOptionPane.OK_OPTION) {
                    projects.remove(i);                  
                } else {
                    uusiProjektiAvataan = false;
                }
            }
            
        }
        
 
        if (uusiProjektiAvataan) {
            getProjects().add(p);
            if (projects.size() == 1) {
                activeProject = p;
            }
            
            if (active) {
                activeProject = p;
            }
        }
        
        
        
       
        setChanged();
        notifyObservers(activeProject);
    }
    
    public void removeProject(ProjectModel p) throws Exception{
        if (!projects.remove(p)){
            throw new Exception("No such project");
        }
        
        if (projects.size() == 0) {
            activeProject = null;
        } else {
            activeProject = projects.get(0);
        }
       // activeProject = null;
        
        setChanged();
        notifyObservers(activeProject);
    }
    
    public void setActive(int index){
        
        if (index <= 0 && index < getProjects().size()){
            activeProject = getProjects().get(index);
        }
        else {
            throw new IndexOutOfBoundsException();
        }
        
        setChanged();
        notifyObservers(activeProject);
    }
    
    public void setActive (ProjectModel p) throws Exception{
        
        boolean found = false;
        
        for (int i = 0 ; i < getProjects().size() ; ++i){
            if (p == getProjects().get(i)){
                activeProject = p;
                found = true;
            }   
        }
        
        if (!found)
            throw new Exception("No such project");
        
        setChanged();
        notifyObservers(activeProject);
    }
    
    public ProjectModel getActiveProject(){
        return activeProject;
    }

    /**
     * @return the projects
     */
    public ArrayList<ProjectModel> getProjects() {
        return projects;
    }

}
