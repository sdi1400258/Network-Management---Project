package com.android.smartevacuation.model;

public class Location {
    public int x,y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getNormalizedX() {
        return x;
    }

    public int getNormalizedY() {
        return y;
    }
}
