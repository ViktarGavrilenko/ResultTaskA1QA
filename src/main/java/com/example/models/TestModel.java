package com.example.models;

public class TestModel {
    public String duration;
    public String method;
    public String name;
    public String startTime;
    public String endTime;
    public String status;

    public String getStartTime() {
        return startTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        TestModel testModel = (TestModel) obj;

        return duration.equals(testModel.duration) && method.equals(testModel.method) && name.equals(testModel.name) &&
                startTime.equals(testModel.startTime) && (endTime != null && !endTime.equals("")) ?
                endTime.equals(testModel.endTime) : testModel.endTime == null
                && status.equalsIgnoreCase(testModel.status);
    }
}