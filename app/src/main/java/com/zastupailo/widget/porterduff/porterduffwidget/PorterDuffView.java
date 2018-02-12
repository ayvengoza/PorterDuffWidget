package com.zastupailo.widget.porterduff.porterduffwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ang on 12.02.18.
 */

public class PorterDuffView extends View {

    private int mDefaultBitmapWidth;
    private int mDefaultBitmapHeight;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.CLEAR;
    private Xfermode mXfermode = new PorterDuffXfermode(mPorterDuffMode);

    public PorterDuffView(Context context) {
        super(context);
        initDefaultBitmapDimens();
    }

    public PorterDuffView(Context context, AttributeSet attrs){
        super(context, attrs);
        initDefaultBitmapDimens();
    }

    public PorterDuffView(Context context, AttributeSet attrs, int defStypeAttr){
        super(context, attrs, defStypeAttr);
        initDefaultBitmapDimens();
    }

    public PorterDuffView(Context context, AttributeSet attrs, int defStypeAttr, int defStyleRes){
        super(context, attrs, defStypeAttr, defStyleRes);
        initDefaultBitmapDimens();
    }

    public void setPorterDuffMode(PorterDuff.Mode mode){
        if(mode == mPorterDuffMode){
            return;
        }
        mPorterDuffMode = mode;
        mXfermode = new PorterDuffXfermode(mode);
        mBitmap = null;
        invalidate();
    }

    public void initDefaultBitmapDimens(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.shape1, options);
        mDefaultBitmapWidth = options.outWidth;
        mDefaultBitmapHeight = options.outHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mBitmap == null){
            createBitmap();
        }
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(changed){
            mBitmap = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // width calculate
        int width;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            width = widthMeasureSpec;
        } else {
            width = getPaddingLeft() + getPaddingRight() + mDefaultBitmapWidth;
            if(specMode == MeasureSpec.AT_MOST){
                width = Math.min(width, specSize);
            }
        }

        //height calculate
        int height;
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            height = heightMeasureSpec;
        } else {
            height = getPaddingBottom() + getPaddingTop() + mDefaultBitmapHeight;
            if(specMode == MeasureSpec.AT_MOST){
                height = Math.min(height, specSize);
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof PorterDuffSavedState)){
            super.onRestoreInstanceState(state);
            return;
        }

        final PorterDuffSavedState ourState = (PorterDuffSavedState) state;
        super.onRestoreInstanceState(ourState.getSuperState());
        setPorterDuffMode(ourState.mode);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final PorterDuffSavedState ourState = new PorterDuffSavedState(superState);
        ourState.mode = mPorterDuffMode;
        return ourState;
    }

    private void createBitmap(){
        final int width = getWidth();
        final int height = getHeight();
        final Rect rect = new Rect();
        final int minDimen = Math.min(width, height);
        rect.right = minDimen;
        rect.bottom = minDimen;
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(mBitmap);

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.shape1);
        canvas.drawBitmap(b, null, rect, mPaint);

        b = BitmapFactory.decodeResource(getResources(), R.drawable.shape2);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(b, null, rect, mPaint);

        mPaint.setXfermode(null);
    }

    private static class PorterDuffSavedState extends BaseSavedState{
        public PorterDuff.Mode mode;

        public PorterDuffSavedState(Parcel source) {
            super(source);
            mode = (PorterDuff.Mode) source.readSerializable();
        }

        public PorterDuffSavedState(Parcelable superState){
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSerializable(mode);
        }
    }
}
