package ib2.spiru.stars;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

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

}
