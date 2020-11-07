/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
   
public class Ed {
    private Integer id_ed;
    private String name;
    private String description;

    public Integer getId_ed() {
        return id_ed;
    }

    public void setId_ed(Integer id_ed) {
        this.id_ed = id_ed;
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

    @Override
    public String toString() {
        return "Eds{" + "id_ed=" + id_ed + ", name=" + name + ", description=" + description + '}';
    }
    
    
}
