package com.example.admin_linux.csdevproject.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

public class RoundCorners implements Transformation {

    private final int radius;  // dp
    private final int margin;  // dp
    private final String KEY;


    /**
     * Creates rounded transformation for all corners.
     *
     * @param radius radius is corner radii in dp
     * @param margin margin is the board in dp
     */
    public RoundCorners(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
        KEY = "rounded_" + radius + margin;
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // Uses native method to draw symmetric rounded corners
        canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin,
                source.getHeight() - margin), radius, radius, paint);


        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return KEY;
    }
}

