package com.parentsphere.connect;
public class RequestModel {
    private String requestId;
    private String parentId;
    private String classId;
    private String status;

    // Default constructor for Firebase
    public RequestModel() {}

    public RequestModel(String requestId, String parentId, String classId, String status) {
        this.requestId = requestId;
        this.parentId = parentId;
        this.classId = classId;
        this.status = status;
    }

    // Getters and setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
