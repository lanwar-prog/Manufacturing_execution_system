/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.InputStream;
import java.util.Date;

public class Detail {
    private Integer id_detail;
    private String name;
    private String description;
    private Date created;
    private InputStream image;

    public Integer getId_detail() {
        return id_detail;
    }

    public void setId_detail(Integer id_detail) {
        this.id_detail = id_detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Detail{" + "id_detail=" + id_detail + ", name=" + name + ", description=" + description + ", created=" + created + ", image=" + image + '}';
    }
}
