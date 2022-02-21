package com.cloud.leasing.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DepositCheckBean implements Serializable {

    private int resumeId;

    private String coordinateMatter;

    private String dateTime;

    private ArrayList<AddDepositBean> depositCheckInfoList;

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getCoordinateMatter() {
        return coordinateMatter;
    }

    public void setCoordinateMatter(String coordinateMatter) {
        this.coordinateMatter = coordinateMatter;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<AddDepositBean> getDepositCheckInfoList() {
        return depositCheckInfoList;
    }

    public void setDepositCheckInfoList(ArrayList<AddDepositBean> depositCheckInfoList) {
        this.depositCheckInfoList = depositCheckInfoList;
    }
}
