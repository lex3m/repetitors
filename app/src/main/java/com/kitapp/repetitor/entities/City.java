package com.kitapp.repetitor.entities;

/**
 * Created by denis on 9/20/17.
 */

public class City {
    private int id;
    private String name;
    private String type;
    private int region_id;

    public City(int id, String name, String type, int region_id) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.region_id = region_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    @Override
    public String toString() {
        return name;
    }
}


