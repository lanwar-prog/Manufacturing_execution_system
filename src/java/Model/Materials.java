/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Materials {
    
    private Integer id_material;
    private String name;
    private Integer number;
    private Integer ed_id;
    private Integer supplier_id;
    private String ed_name;
    private String s_name;
    private String s_city;

    public Integer getId_material() {
        return id_material;
    }

    public void setId_material(Integer id_material) {
        this.id_material = id_material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getEd_id() {
        return ed_id;
    }

    public void setEd_id(Integer ed_id) {
        this.ed_id = ed_id;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }



    public String getEd_name() {
        return ed_name;
    }

    public void setEd_name(String ed_name) {
        this.ed_name = ed_name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_city() {
        return s_city;
    }

    public void setS_city(String s_city) {
        this.s_city = s_city;
    }

    @Override
    public String toString() {
        return "Materials{" + "id_material=" + id_material + ", name=" + name + ", number=" + number + ", ed_id=" + ed_id + ", supplier_id=" + supplier_id + ", ed_name=" + ed_name + ", s_name=" + s_name + ", s_city=" + s_city + '}';
    }
    
    
}
