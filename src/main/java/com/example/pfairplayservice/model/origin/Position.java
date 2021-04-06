package com.example.pfairplayservice.model.origin;

public enum Position {

    NONE(0),
    FW(1),
    MF(2),
    DF(3),
    GK(4);


    private int position;

    Position(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public static Position from(int position) {

        for (Position c : Position.values()) {
            if (c.getPosition() == position) {
                return c;
            }
        }
        return null;
    }

}
