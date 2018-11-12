package com.example.admin_linux.csdevproject.utils;

import android.graphics.Color;

import com.example.admin_linux.csdevproject.R;

import java.util.Random;

public class ColorPicker {

    // Maybe change color rotation (amount of available colors)
    public static int pickRandomColor() {
        Random r = new Random();
        int value = r.nextInt(9);

        switch (value) {
            case 0:
                return R.color.green_100;
            case 1:
                return R.color.green_200;
            case 2:
                return R.color.green_300;
            case 3:
                return R.color.green_400;
            case 4:
                return R.color.green_500;
            case 5:
                return R.color.green_600;
            case 6:
                return R.color.green_700;
            case 7:
                return R.color.green_800;
            case 8:
                return R.color.green_900;
            default:
                return R.color.green_def;

        }
    }
}
