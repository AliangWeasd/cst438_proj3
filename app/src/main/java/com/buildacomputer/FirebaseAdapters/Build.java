package com.buildacomputer.FirebaseAdapters;

public class Build {
    private String name;
    private String email;
    private int caseID;
    private int moboID;
    private int cpuID;
    private int gpuID;
    private int storageID;
    private int memoryID;
    private int psuID;
    private int coolingId;

    public Build(String name, String email,
                 int caseID, int moboID, int cpuID, int gpuID,
                 int storageID, int memoryID, int psuID, int coolingId) {
        this.name = name;
        this.email = email;
        this.caseID = caseID;
        this.moboID = moboID;
        this.cpuID = cpuID;
        this.gpuID = gpuID;
        this.storageID = storageID;
        this.memoryID = memoryID;
        this.psuID = psuID;
        this.coolingId = coolingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public int getMoboID() {
        return moboID;
    }

    public void setMoboID(int moboID) {
        this.moboID = moboID;
    }

    public int getCpuID() {
        return cpuID;
    }

    public void setCpuID(int cpuID) {
        this.cpuID = cpuID;
    }

    public int getGpuID() {
        return gpuID;
    }

    public void setGpuID(int gpuID) {
        this.gpuID = gpuID;
    }

    public int getStorageID() {
        return storageID;
    }

    public void setStorageID(int storageID) {
        this.storageID = storageID;
    }

    public int getMemoryID() {
        return memoryID;
    }

    public void setMemoryID(int memoryID) {
        this.memoryID = memoryID;
    }

    public int getPsuID() {
        return psuID;
    }

    public void setPsuID(int psuID) {
        this.psuID = psuID;
    }

    public int getCoolingId() {
        return coolingId;
    }

    public void setCoolingId(int coolingId) {
        this.coolingId = coolingId;
    }
}
