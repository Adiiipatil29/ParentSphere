// File Name: ConnectionRequestModel.java
package com.parentsphere.connect;

public class ConnectionRequestModel {
    private String requestId;
    private String classId;
    private String parentId;
    private String status;

    public ConnectionRequestModel() {
    }

    public ConnectionRequestModel(String requestId, String classId, String parentId, String status) {
        this.requestId = requestId;
        this.classId = classId;
        this.parentId = parentId;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
