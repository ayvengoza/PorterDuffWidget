package com.zastupailo.widget.porterduff.porterduffwidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
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
}
