package com.example.pfairplayservice.tempenum;

public enum Positon {
    ST(1),
    CAM(2),
    CM(3),
    WF(4),
    WB(5),
    CB(6),
    GK(7);





    private int position;

    Positon(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
