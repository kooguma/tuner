package top.koguma.tuner.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ToningIndicator extends View {

    private final static String TAG = "ToningIndicator";

    private float mExpectedFrequency = 443f;

    private int mWidth;
    private int mHeight;

    private float mScaleWidth;
    private int mContentPadding = 60;

    private Paint mPaint;
    private Paint mTextPain;
    private Paint mRectPain;

    private float mStrokeWidth;

    //todo: define it in attr
    private float mBigScaleLength = 20;
    private float mSmallScaleLength = 10;

    private final static int BIG_SCALE_COUNT = 11;
    private final static int SMALL_SCALE_COUNT = 4;
    private final static int SCALE_COUNT = 51;
    private final static int RECT_SIDE_LENGTH = 20;

    private Indicator mIndicator;

    private float mDelta;

    private OnFrequencyChangedListener mListener;

    public interface OnFrequencyChangedListener {
        void OnFrequencyChanged(float frequency);
    }

    public void setOnFrequencyChangedListener(OnFrequencyChangedListener listener) {
        mListener = listener;
    }

    public ToningIndicator(Context context) {
        this(context, null);
    }

    public ToningIndicator(Context context,
                           @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToningIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(mStrokeWidth = 2f);

        mTextPain = new Paint();
        mTextPain.setColor(Color.WHITE);
        mTextPain.setTextSize(20f);
        mTextPain.setTextAlign(Paint.Align.CENTER);

        mRectPain = new Paint();
        mRectPain.setColor(Color.parseColor("#33FFFFFF"));
        mRectPain.setAntiAlias(true);
        mRectPain.setStyle(Paint.Style.FILL);

        mIndicator = new Indicator();
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        //calculate each scale's width
        mScaleWidth = (float) (mWidth - mContentPadding * 2) / (SCALE_COUNT - 1);

    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // private int measuredWidth(int measureSpec){
    //     int result;
    //     int specMode = MeasureSpec.getMode(measureSpec);
    //     int specSize = MeasureSpec.getSize(measureSpec);
    // }
    //
    // private int measureHeight(int measureSpec){
    //     int result;
    //     int specMode = MeasureSpec.getMode(measureSpec);
    //     int specSize = MeasureSpec.getSize(measureSpec);
    // }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw the top and bottom lines
        canvas.drawLine(0, 0, mWidth, 0, mPaint);
        canvas.drawLine(0, mHeight, mWidth, mHeight, mPaint);

        //draw the scale
        final int scaleStartX = mContentPadding;
        final int scaleStopX = mWidth - mContentPadding;
        final int bigScaleHeight = 6;
        final int smallScaleInt = 3;
        final int scaleStartY = mHeight - 80;
        int initScale = -150;

        for (int i = 0; i < SCALE_COUNT; i++) {
            final boolean isBigScale = i % 5 == 0;
            final float scaleHeight = isBigScale ? mBigScaleLength : mSmallScaleLength;
            final float startX = scaleStartX + i * mScaleWidth;
            final float startY = isBigScale
                                 ? scaleStartY
                                 : scaleStartY + (mBigScaleLength - mSmallScaleLength);
            canvas.drawLine(startX, startY, startX, startY + scaleHeight,
                mPaint);

            //todo:
            if (initScale == 0 && isBigScale) {
                canvas.drawRect(
                    startX - RECT_SIDE_LENGTH, 0, startX + RECT_SIDE_LENGTH,
                    mHeight, mRectPain);
            }

            if (isBigScale) {
                //draw the text
                final String text = initScale > 0
                                    ? "+" + String.valueOf(initScale)
                                    : String.valueOf(initScale);
                canvas.drawText(text, startX, startY + scaleHeight + 30, mTextPain);
                initScale += 30;
            }

        }

        //draw the indicator at the top layer
        mIndicator.pivotX = mContentPadding + mDelta;
        mIndicator.pivotStartY = 0;
        mIndicator.pivotStopY = mHeight;
        mIndicator.draw(canvas);

    }

    public void setFrequency(float frequency) {
        final float delta = frequency - mExpectedFrequency;
        if (mDelta != delta && mListener != null) {
            mListener.OnFrequencyChanged(frequency);
        }
    }

    class Indicator {

        final static int TRIANGLE_SIDE_LENGTH = 10;

        float pivotX;
        float pivotStartY;
        float pivotStopY;

        Paint pivotPaint;

        Path path;

        public Indicator() {
            pivotPaint = new Paint();
            pivotPaint.setColor(Color.parseColor("#6687CEFF"));
            pivotPaint.setAntiAlias(true);
            pivotPaint.setStyle(Paint.Style.FILL);
            pivotPaint.setStrokeWidth(3f);
            path = new Path();
        }

        void draw(Canvas canvas) {
            //draw the triangles
            path.moveTo(pivotX - TRIANGLE_SIDE_LENGTH, pivotStartY);
            path.lineTo(pivotX + TRIANGLE_SIDE_LENGTH, pivotStartY);
            path.lineTo(pivotX, pivotStartY + TRIANGLE_SIDE_LENGTH);
            path.close();
            canvas.drawPath(path, pivotPaint);
            path.moveTo(pivotX - TRIANGLE_SIDE_LENGTH, pivotStopY);
            path.lineTo(pivotX + TRIANGLE_SIDE_LENGTH, pivotStopY);
            path.lineTo(pivotX, pivotStopY - TRIANGLE_SIDE_LENGTH);
            path.close();
            canvas.drawPath(path, pivotPaint);
            //draw the pivot line
            canvas.drawLine(pivotX, pivotStartY, pivotX, pivotStopY, pivotPaint);
        }

        @Override public String toString() {
            return "Indicator{" +
                "pivotX=" + pivotX +
                ", pivotStartY=" + pivotStartY +
                ", pivotStopY=" + pivotStopY +
                '}';
        }
    }
}
