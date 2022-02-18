package com.cloud.leasing.bean;

import java.io.Serializable;

public class ParamMaintenanceBean implements Serializable {

    private int id;
    private int resumeId;
    private String mtnceName;
    private String mtnceSystem;
    private String mtncePosition;
    private String needingAttention;
    private String refPicture;
    private String realPicture;
    private String isAbnormal;
    private String exceptionDetails;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getMtnceName() {
        return mtnceName;
    }

    public void setMtnceName(String mtnceName) {
        this.mtnceName = mtnceName;
    }

    public String getMtnceSystem() {
        return mtnceSystem;
    }

    public void setMtnceSystem(String mtnceSystem) {
        this.mtnceSystem = mtnceSystem;
    }

    public String getMtncePosition() {
        return mtncePosition;
    }

    public void setMtncePosition(String mtncePosition) {
        this.mtncePosition = mtncePosition;
    }

    public String getNeedingAttention() {
        return needingAttention;
    }

    public void setNeedingAttention(String needingAttention) {
        this.needingAttention = needingAttention;
    }

    public String getRefPicture() {
        return refPicture;
    }

    public void setRefPicture(String refPicture) {
        this.refPicture = refPicture;
    }

    public String getRealPicture() {
        return realPicture;
    }

    public void setRealPicture(String realPicture) {
        this.realPicture = realPicture;
    }

    public String getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(String isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
