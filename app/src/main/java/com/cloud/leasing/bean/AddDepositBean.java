package com.cloud.leasing.bean;

import java.io.Serializable;

public class AddDepositBean implements Serializable {

    private String checkJson;

    public String getCheckJson() {
        return checkJson;
    }

    public void setCheckJson(String checkJson) {
        this.checkJson = checkJson;
    }
}
