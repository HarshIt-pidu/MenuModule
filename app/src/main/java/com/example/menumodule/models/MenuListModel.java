package com.example.menumodule.models;

public class MenuListModel {
    private String id;
    private String name;
    private String description;
    private String veg;
    private String nonveg;
    private String egg;
    private String dinein;
    private String pickup;
    private String delivery;
    private String allday;
    private String starttime;
    private String endtime;
    private String superappvisible;
    private String disable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getNonveg() {
        return nonveg;
    }

    public void setNonveg(String nonveg) {
        this.nonveg = nonveg;
    }

    public String getEgg() {
        return egg;
    }

    public void setEgg(String egg) {
        this.egg = egg;
    }

    public String getDinein() {
        return dinein;
    }

    public void setDinein(String dinein) {
        this.dinein = dinein;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getAllday() {
        return allday;
    }

    public void setAllday(String allday) {
        this.allday = allday;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSuperappvisible() {
        return superappvisible;
    }

    public void setSuperappvisible(String superappvisible) {
        this.superappvisible = superappvisible;
    }

    public String getDisable() {
        return disable;
    }

    public MenuListModel(String id, String name, String description, String veg, String nonveg, String egg, String dinein, String pickup, String delivery, String allday, String starttime, String endtime, String superappvisible, String disable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.veg = veg;
        this.nonveg = nonveg;
        this.egg = egg;
        this.dinein = dinein;
        this.pickup = pickup;
        this.delivery = delivery;
        this.allday = allday;
        this.starttime = starttime;
        this.endtime = endtime;
        this.superappvisible = superappvisible;
        this.disable = disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }
}
