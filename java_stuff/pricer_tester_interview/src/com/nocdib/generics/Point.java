package com.nocdib.generics;

public class Point {

    int x, y;

    public Point() {
        this(0,0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point p) {
        return ((this.x == p.x) && (this.y == p.y));
    }

    @Override
    public String toString() {
        return "(" + this.x + ", "+ this.y + ")";
    }
}
