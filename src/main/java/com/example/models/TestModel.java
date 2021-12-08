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
/*        System.out.println("--------------------");
        System.out.println(duration + "  " + testModel.duration);
        System.out.println(method + "  " + testModel.method);
        System.out.println(name + "  " + testModel.name);
        System.out.println(startTime + "  " + testModel.startTime);
        System.out.println(endTime + "  " + testModel.endTime);
        System.out.println(status + "  " + testModel.status);
        System.out.println("--------------------");*/


/*        if (testModel.endTime.isEmpty()) {
            testModel.endTime = "null";
            System.out.println(endTime.equals(testModel.endTime));
        }*/
        System.out.println(endTime + "  " + testModel.endTime);
        return duration.equals(testModel.duration) && method.equals(testModel.method) && name.equals(testModel.name) &&
                startTime.equals(testModel.startTime) &&
                endTime != null ? endTime.equals(testModel.endTime) : testModel.endTime == null
                && status.equals(testModel.status.toLowerCase());
    }
}

