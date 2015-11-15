package com.example.lin.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by lin on 15-11-15.
 */
public class ProgressView extends View {

    private Bitmap loadingBitmap;
    private String tag = "ProgressView";
    private int progressType = -1;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float toDegree = Float.MAX_VALUE;
    private float fromDegree = Float.MAX_VALUE;

    public void setProgressType(int type) {
        toDegree = Float.MAX_VALUE;
        fromDegree = Float.MAX_VALUE;
        progressType = type;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (progressType == -1) {
            return ;
        }

        if (loadingBitmap == null) {
            loadingBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.loading);
        }

        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight() / 2;
        float loadingImageWidth = loadingBitmap.getWidth() / 2;
        float loadingImageHeight = loadingBitmap.getHeight() / 2;

        float raduis = loadingImageWidth * 5;

        if (progressType == 1) {
            drawBitmapClockwise(canvas, centerX, centerY, raduis);
        }
        else if (progressType == 2) {
            drawSector(canvas, centerX, centerY, raduis);
        }

        //Log.d(tag, "fromDegree:" + fromDegree + ", toDegree:" + toDegree);

        invalidate();
    }


    private void drawSector(Canvas canvas, float centerX, float centerY, float raduis) {
        if (fromDegree == Float.MAX_VALUE && toDegree == Float.MAX_VALUE) {
            fromDegree = -90;
            toDegree = -90;
        }

        toDegree += 2;
        if (toDegree >= 45) {
            fromDegree += 2;
        }

        canvas.save();
        getSector(canvas, centerX, centerY, raduis, fromDegree, toDegree);
        drawProgressBitmap(canvas, centerX, centerY);
        canvas.restore();

    }


    private void drawBitmapClockwise(Canvas canvas, float centerX, float centerY, float raduis) {
        if (fromDegree == Float.MAX_VALUE && toDegree == Float.MAX_VALUE) {
            fromDegree = -90;
            toDegree = -90;
        }

        toDegree += 2;
        if (toDegree == 0) {
            fromDegree = 0;
        }
        else if (toDegree == 90) {
            fromDegree = 90;
        }
        else if (toDegree == 180) {
            fromDegree = 180;
        }
        else if (toDegree == 270) {
            fromDegree = -90;
            toDegree = -90;
        }

        if (fromDegree == -90 && toDegree < 0) {

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, fromDegree, toDegree);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();
        }
        else if (fromDegree == 0 && toDegree < 90) {
            canvas.save();
            getSector(canvas, centerX, centerY, raduis, -90, 0);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, fromDegree, toDegree);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();
        }
        else if (fromDegree == 90 && toDegree < 180) {
            canvas.save();
            getSector(canvas, centerX, centerY, raduis, -90, 0);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, 0, 90);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, fromDegree, toDegree);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();
        }
        else if (fromDegree == 180 && toDegree < 270) {
            canvas.save();
            getSector(canvas, centerX, centerY, raduis, -90, 0);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, 0, 90);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, 90, 180);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();

            canvas.save();
            getSector(canvas, centerX, centerY, raduis, fromDegree, toDegree);
            drawProgressBitmap(canvas, centerX, centerY);
            canvas.restore();
        }
    }

    private Path pathTriangle = new Path();

    private void getSector(Canvas canvas, float originX, float originY, float radius, double fromDegree, double toDegree) {
        pathTriangle.reset();
        pathTriangle.moveTo(originX, originY);

        float vXFrom = (float) (Math.cos(fromDegree * Math.PI / 180) * radius) + originX;
        float vYFrom = (float) (Math.sin(fromDegree * Math.PI / 180) * radius) + originY;

        float vXTo = (float) (Math.cos(toDegree * Math.PI / 180) * radius) + originX;
        float vYTo = (float) (Math.sin(toDegree * Math.PI / 180) * radius) + originY;

        pathTriangle.lineTo(vXFrom, vYFrom);
        pathTriangle.lineTo(vXTo, vYTo);
        pathTriangle.close();
        canvas.clipPath(pathTriangle);
    }


    private void drawProgressBitmap(Canvas canvas, float centerX, float centerY) {
        float drawBitmapTop = centerY - loadingBitmap.getHeight()/2;
        float drawBitmapLeft = centerX - loadingBitmap.getWidth() /2;
        canvas.drawBitmap(loadingBitmap, drawBitmapLeft, drawBitmapTop, null);

    }


}
