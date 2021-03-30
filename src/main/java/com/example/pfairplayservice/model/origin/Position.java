package com.example.pfairplayservice.model.origin;

public enum Position {
    NONE(0),
    ST(1),
    CAM(2),
    CM(3),
    WF(4),
    WB(5),
    CB(6),
    GK(7);


    private Integer position;

    Position(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
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
