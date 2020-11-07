/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

public class Workplace {
    private Integer id_workplace;
    private String name;
    private Date created;

    public Integer getId_workplace() {
        return id_workplace;
    }

    public void setId_workplace(Integer id_workplace) {
        this.id_workplace = id_workplace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Workplace{" + "id_workplace=" + id_workplace + ", name=" + name + ", created=" + created + '}';
    }
    
}
