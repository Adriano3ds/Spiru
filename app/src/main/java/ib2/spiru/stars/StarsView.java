package ib2.spiru.stars;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class StarsView extends View {

    public StarsView(Context context) {
        super(context);
    }

    public StarsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StarsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Star.onDraw(this, canvas);
    }
}
