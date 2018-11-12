package com.example.admin_linux.csdevproject.utils;

import android.graphics.Color;

import java.util.Random;

public class ColorPicker {

    // Maybe change color rotation (amount of available colors)
    public static int pickRandomColor() {
        Random r = new Random();
        int value = r.nextInt(10);

        switch (value) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.DKGRAY;
            case 4:
                return Color.GRAY;
            case 5:
                return Color.GREEN;
            case 6:
                return Color.LTGRAY;
            case 7:
                return Color.MAGENTA;
            case 8:
                return Color.RED;
            case 9:
                return Color.YELLOW;
            default:
                return Color.WHITE;

        }
    }
}
