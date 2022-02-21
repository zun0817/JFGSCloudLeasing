package com.cloud.leasing.bean;

import java.io.Serializable;

public class CityBean implements Serializable {

    private Integer id;
    private String areaName;
    private String areaFullName;
    private Integer level;
    private Object belongAreaName;
    private String areaCode;
    private Integer superiorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaFullName() {
        return areaFullName;
    }

    public void setAreaFullName(String areaFullName) {
        this.areaFullName = areaFullName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Object getBelongAreaName() {
        return belongAreaName;
    }

    public void setBelongAreaName(Object belongAreaName) {
        this.belongAreaName = belongAreaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }
}
