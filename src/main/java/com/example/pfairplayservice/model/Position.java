package com.example.pfairplayservice.model;

public enum Position {
    ST(1),
    CAM(2),
    CM(3),
    WF(4),
    WB(5),
    CB(6),
    GK(7);


    private int position;

    Position(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public static Position from(Integer position) {
        if(position == null) {
            return null;
        }
        for (Position c : Position.values()) {
            if (c.getPosition() == position) {
                return c;
            }
        }
        return null;
    }

}
