package ib2.spiru.stars;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Random;

import ib2.spiru.R;

public class StarsView extends View {

    float[][] points = new float[50][2];
    public StarsView(Context context) {
        super(context);
    }

    public StarsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundResource(R.drawable.bg_gradient);
    }

    public StarsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        for (int i = 0; i < 200; i++) {
            Star s = new Star();
            s.draw(canvas, width, height);
        }
    }
}
