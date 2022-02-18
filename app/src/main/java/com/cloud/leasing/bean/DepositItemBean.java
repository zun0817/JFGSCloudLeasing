package com.cloud.leasing.bean;

import java.io.Serializable;

public class DepositItemBean implements Serializable {

    private String checkName;
    private int deviceStatus;
    private String exceptionDetails;
    private String coordinateMatter;

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public String getCoordinateMatter() {
        return coordinateMatter;
    }

    public void setCoordinateMatter(String coordinateMatter) {
        this.coordinateMatter = coordinateMatter;
    }
}
