package com.buildacomputer.FirebaseAdapters;

public class CompParts {
    private String name;
    private String description;
    private int part_type;
    private String picture;
    private int id;
    private String size;
    private String memoryType;
    private String heatGen;
    private String heatCool;
    private String powerUse;
    private String powerSupply;

    // Should probably be used for BuildsPartsRecyclerActivity, when we get to calculating
    // a build's power,cooling, etc.
    public CompParts(String name, String description, int part_type, String picture,
                     String size, String memoryType, String heatGen, String heatCool,
                     String powerUse, String powerSupply) {
        this.name = name;
        this.description = description;
        this.part_type = part_type;
        this.picture = picture;
        this.size = size;
        this.memoryType = memoryType;
        this.heatGen = heatGen;
        this.heatCool = heatCool;
        this.powerUse = powerUse;
        this.powerSupply = powerSupply;
    }

    // Used in PartsRecyclerActivity, which doesn't need to check for any calculations.
    public CompParts(String name, int part_type, String picture) {
        this.name = name;
        this.part_type = part_type;
        this.picture = picture;
    }

    // Currently used in BuildPartsRecyclerActivity, to grab the part's ID to save into a build.
    public CompParts(String name, int id, int part_type, String picture) {
        this.name = name;
        this.id = id;
        this.part_type = part_type;
        this.picture = picture;
    }

    public CompParts() {
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

    public int getPartType() {
        return part_type;
    }

    public void setPartType(int part_type) {
        this.part_type = part_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getHeatGen() {
        return heatGen;
    }

    public void setHeatGen(String heatGen) {
        this.heatGen = heatGen;
    }

    public String getHeatCool() {
        return heatCool;
    }

    public void setHeatCool(String heatCool) {
        this.heatCool = heatCool;
    }

    public String getPowerUse() {
        return powerUse;
    }

    public void setPowerUse(String powerUse) {
        this.powerUse = powerUse;
    }

    public String getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(String powerSupply) {
        this.powerSupply = powerSupply;
    }
}
