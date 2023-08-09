/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Seka
 */
@Entity
public class Assignee implements Serializable {
    @Id
    private int id;
    private String names;

    public Assignee() {
    }

    public Assignee(int id) {
        this.id = id;
    }

    public Assignee(int id, String names) {
        this.id = id;
        this.names = names;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    
    
}
