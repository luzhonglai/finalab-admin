package com.bytetcp.finalab.serve.course.domain;

public class InstanceRunRecode {
    private static final long serialVersionUID = 1L;

    private long id;
    private long courseId;
    private String instanceId;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InstanceRunRecode{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", instanceId='" + instanceId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
