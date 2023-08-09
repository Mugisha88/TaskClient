/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackingBean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import model.Assignee;
import model.Project;
import model.Task;
import model.Users;

/**
 *
 * @author Seka
 */
@ManagedBean(name = "BB")
@SessionScoped
public class BackingBean implements Serializable{
    private Users user;
    private Assignee assignee;
    private Project projects;
    private Task task;
    public boolean isLogged = false;
    private Part uploadedFile;
    private String text;
    private int projectID;
    private int assigneeID;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(int assigneeID) {
        this.assigneeID = assigneeID;
    }
    
    
    public BackingBean() {
        user = new Users();
        assignee = new Assignee();
        projects = new Project();
        task = new Task();
       
    }
    public void upload() {

        if (null != uploadedFile) {
            try {
                InputStream is = uploadedFile.getInputStream();
                text = new Scanner(is).useDelimiter("\\A").next();
            } catch (IOException ex) {
            }
        }
    }
    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProjects() {
        return projects;
    }

    public void setProjects(Project projects) {
        this.projects = projects;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }
    
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public String aunthenticate() {
        Users result = ClientBuilder.newClient()
                .target("http://localhost:8080/TaskServer/api/auth")
                .request().post(Entity.json(user), Users.class);
        if (result != null) {
            isLogged = true;
            return "Home.xhtml";
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("auth-frm", new FacesMessage("Auth failed"));
            return null;
        }
    }
     public List<Assignee> getAllAssignees() {
        return ClientBuilder.newClient()
                .target("http://localhost:8080/TaskServer/api/assignee")
                .request().get(new GenericType<List<Assignee>>() {
                });
    }
     public List<Task> getAllTasks() {
        return ClientBuilder.newClient()
                .target("http://localhost:8080/TaskServer/api/task")
                .request().get(new GenericType<List<Task>>() {
                });
    }
     public List<Project> getAllProjects() {
        return ClientBuilder.newClient()
                .target("http://localhost:8080/TaskServer/api/project")
                .request().get(new GenericType<List<Project>>() {
                });
    }
     public String createTask() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
         //System.out.println(task.getStartDate());
        Assignee assign = new Assignee(assigneeID);
        Project pro = new Project(projectID);
        task.setAssignees(assign);
        task.setProjects(pro);
        ClientBuilder.newClient().target("http://localhost:8080/TaskServer/api/task")
                .request().post(Entity.json(task));
        
        return "Display.xhtml";
    }
}
