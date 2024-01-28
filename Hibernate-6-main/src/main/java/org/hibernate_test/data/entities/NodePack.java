package org.hibernate_test.data.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "\"TREES\"")
public class NodePack {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "parent_id")
    private int parent_id;
    public NodePack(int id, int parent_id){this.id = id; this.parent_id = parent_id;}
    public NodePack(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }


}
