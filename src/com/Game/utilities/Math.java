package com.Game.utilities;

public class Math {

    public static float lerp(float point1, float point2, float alpha) {
        return point1 + alpha * (point2 - point1);
    }

    public static float bound(float min, float max, float current) {
        if(current < min) return min;
        else if(current > max) return max;
        else return current;
    }

}
