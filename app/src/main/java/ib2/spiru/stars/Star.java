package ib2.spiru.stars;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

import ib2.spiru.R;

public class Star {

    private float x;
    private float y;
    private float radius = 10.0f;

    public Star() {
        Random random = new Random();
        setPoint(random.nextFloat(), random.nextFloat());
        this.radius = 3 + random.nextFloat()*5;
    }

    public Star(float x, float y) {
        setPoint(x, y);
    }

    private void setPoint(float x, float y) {
        this.x = x;
        if (this.x > 1) this.x = 1;
        if (this.x < 0) this.x = 0;
        this.y = y;
        if (this.y > 1) this.y = 1;
        if (this.y < 0) this.y = 0;
    }

    public void draw(Canvas canvas, int width, int height) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        int opacity = (int) (255*(1 - y));
        p.setColor(0x00FFFFFF | (opacity << 24));
        canvas.drawCircle(x * width, y * height, radius, p);
    }

    public static void onDraw(View view, Canvas canvas) {
        view.setBackgroundResource(R.drawable.bg_gradient);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.heightPixels = 1980;
        displayMetrics.widthPixels = 1080;
        ((Activity) view.getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        for (int i = 0; i < 100; i++) {
            Star s = new Star();
            s.draw(canvas, width, height);
        }
    }

}
