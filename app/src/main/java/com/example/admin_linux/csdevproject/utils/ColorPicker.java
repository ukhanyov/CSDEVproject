package com.example.admin_linux.csdevproject.utils;

import android.graphics.Color;

import com.example.admin_linux.csdevproject.R;

import java.util.Random;

public class ColorPicker {

    // Maybe change color rotation (amount of available colors)
    public static int pickRandomColor() {
        Random r = new Random();
        int value = r.nextInt(5);

        switch (value) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GRAY;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.RED;
            case 5:
                return Color.YELLOW;
            default:
                return Color.GREEN;

        }
    }
}
