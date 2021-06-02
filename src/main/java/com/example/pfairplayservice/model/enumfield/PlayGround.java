package com.example.pfairplayservice.model.enumfield;

public enum PlayGround {

    옥녀봉(0),
    충남대(1),
    목원대(2),
    을미기(3);

    private int groundNumber;

    PlayGround(int groundNumber) {
        this.groundNumber = groundNumber;
    }

    public int getGroundNumber() {
        return groundNumber;
    }

    public static PlayGround from(int groundNumber) {
        for (PlayGround pg : PlayGround.values()) {
            if (pg.groundNumber == groundNumber) {
                return pg;
            }
        }
        return null;
    }

}
