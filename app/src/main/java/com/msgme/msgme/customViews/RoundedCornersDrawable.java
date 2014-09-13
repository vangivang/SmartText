package com.msgme.msgme.customViews;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

/**
 * shows a bitmap as if it had rounded corners. based on :
 * http://rahulswackyworld.blogspot.co.il/2013/04/android-drawables-with-rounded_7.html
 */
public class RoundedCornersDrawable extends BitmapDrawable {

    private final BitmapShader bitmapShader;
    private final Paint p;
    private final RectF rect;
    private final float borderRadius;

    public RoundedCornersDrawable(final Resources resources, final Bitmap bitmap, final float borderRadius) {
        super(resources, bitmap);
        bitmapShader = new BitmapShader(getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        final Bitmap b = getBitmap();
        p = getPaint();
        p.setAntiAlias(true);
        p.setShader(bitmapShader);
        final int w = b.getWidth(), h = b.getHeight();
        rect = new RectF(0, 0, w, h);
        this.borderRadius = borderRadius < 0 ? 0.15f * Math.min(w, h) : borderRadius;
    }

    @Override
    public void draw(final Canvas canvas) {
        canvas.drawRoundRect(rect, borderRadius, borderRadius, p);
    }
}