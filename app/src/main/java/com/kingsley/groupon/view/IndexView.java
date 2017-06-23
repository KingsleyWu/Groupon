package com.kingsley.groupon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * class name : Groupon
 * author : Kingsley
 * created date : on 2017/6/21 19:17
 * file change date : on 2017/6/21 19:17
 * version: 1.0
 */

public class IndexView extends View {
    private String[] words = {"热","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};


    //width&height
    private int itemWidth;
    private int itemHeight;
    //paint
    private Paint paint;
    private int touchIndex = -1;

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics());
        paint.setTextSize(size);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /**
     * 1.
     * 用于设定View的尺寸
     * 只有当View的onMeasure设定的尺寸逻辑不能满足实际要求才有必要进行改写
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight()/words.length;
    }

    /**
     * 2.
     * 用于控制子控件的摆放
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 3.
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {
            if (touchIndex == i){
                paint.setColor(Color.BLACK);
            }else {
                paint.setColor(Color.WHITE);
            }
            String word = words[i];
            Rect rect = new Rect();
            //获取文字的宽高
            paint.getTextBounds(word,0,1,rect);

            int wordWidth = rect.width();
            int wordHeight = rect.height();

            float wordX = itemWidth/2 - wordWidth/2;
            float wordY = itemHeight /2 +wordHeight/2 +i*itemHeight;
            canvas.drawText(word,wordX,wordY,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y/itemHeight);
                if (touchIndex != index){
                    touchIndex = index;
                    if (onIndexChangeListener != null && touchIndex < words.length && touchIndex >=0){
                        onIndexChangeListener.onIndexChange(words[touchIndex]);
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    public interface OnIndexChangeListener{
        void onIndexChange(String word);
    }
    private OnIndexChangeListener onIndexChangeListener;

    public void setOnIndexChangeListener(OnIndexChangeListener onIndexChangeListener) {
        this.onIndexChangeListener = onIndexChangeListener;
    }
}
