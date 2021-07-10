package com.example.pfairplayservice.model.enumfield;

public enum Status {

    ONGOING(0),
    COMPLETE(1),
    HiDE(2),
    SCORED(3);

    private int status;

    Status(int status) {
        this.status = status;

    }

    public int getStatus() {
        return status;
    }

    public static Status from(int status) {
        for (Status s : Status.values()) {
            if (s.status == status) {
                return s;
            }
        }
        return null;
    }
}