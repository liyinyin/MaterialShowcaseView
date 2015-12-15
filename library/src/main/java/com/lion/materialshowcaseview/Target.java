package com.lion.materialshowcaseview;

import android.graphics.Point;

public interface Target {
    Target NONE = new Target() {
        @Override
        public Point getPoint() {
            return new Point(1000000, 1000000);
        }

        @Override
        public int getRadius() {
            return 200;
        }

        @Override
        public Point getFinalPoint(int radius) {
            return getPoint();
        }
    };

    Point getPoint();

    int getRadius();

    Point getFinalPoint(int radius);
}
