package top.koguma.tuner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import top.koguma.tuner.R;
import top.koguma.tuner.utils.DisplayUtils;

public class TitleSwitchTab extends LinearLayout {

    private int mTextColorNormal; //未选中时标题的颜色
    private int mTextColorSelected;

    private Drawable mTabRightDrawableNormal;
    private Drawable mTabRightDrawableSelected;
    private Drawable mTabLeftDrawableNormal;
    private Drawable mTabLeftDrawableSelected;

    private int mTextSize;//标题文字大小


    private static final int TAB_LEFT = 0;
    private static final int TAB_RIGHT = 1;

    private int mCurPos = TAB_LEFT;
    private int[] mTitles;

    private TextView mLeftTab;
    private TextView mRightTab;

    private OnTabChangeListener mOnTabChangeListener;

    public TitleSwitchTab(Context context) {
        this(context, null);
    }

    public TitleSwitchTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleSwitchTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleSwitchTab);
        mTextColorNormal = a.getColor(R.styleable.TitleSwitchTab_textColorNormal,
                getResources().getColor(android.R.color.white));
        mTextColorSelected = a.getColor(R.styleable.TitleSwitchTab_textColorSelected,
                getResources().getColor(android.R.color.white));

        mTabRightDrawableNormal = getResources().getDrawable(R.drawable.bg_login_tab_right_normal);
        mTabRightDrawableSelected = getResources().getDrawable(R.drawable.bg_login_tab_right_selected);

        mTabLeftDrawableNormal = getResources().getDrawable(R.drawable.bg_login_tab_left_normal);
        mTabLeftDrawableSelected = getResources().getDrawable(R.drawable.bg_login_tab_left_selected);

        mTextSize = a.getDimensionPixelSize(R.styleable.TitleSwitchTab_textSize, 16);

        a.recycle();

    }

    public void setTabs(int[] titles) {
        mTitles = titles;
        initTabView();
    }


    private void initTabView() {
        setOrientation(HORIZONTAL);
        removeAllViews();
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        mLeftTab = getTextView(TAB_LEFT);
        mRightTab = getTextView(TAB_RIGHT);
        addView(mLeftTab, params);
        addView(mRightTab, params);

        initTab();
    }


    private TextView getTextView(int index) {
        final TextView t = new TextView((getContext()));
        t.setTextSize(mTextSize);
        t.setText(mTitles[index]);
        t.setGravity(Gravity.CENTER);
        t.setPadding(
                DisplayUtils.dip2px(getContext(), 34),
                DisplayUtils.dip2px(getContext(), 7),
                DisplayUtils.dip2px(getContext(), 34),
                DisplayUtils.dip2px(getContext(), 7));
        t.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                selectTab(t);
            }
        });
        return t;
    }

    private void selectTab(TextView textView) {
        if (textView.equals(mLeftTab)) {
            mCurPos = TAB_LEFT;
            updateTab(mLeftTab, true);
            updateTab(mRightTab, false);
        } else {
            mCurPos = TAB_RIGHT;
            updateTab(mRightTab, true);
            updateTab(mLeftTab, false);
        }

        if (mOnTabChangeListener != null) {
            mOnTabChangeListener.onTabChange(mCurPos);
        }
    }


    private void initTab() {
        updateTab(mLeftTab, true);
        updateTab(mRightTab, false);
    }

    private void updateTab(TextView textView, boolean isSelected) {
        if (textView.equals(mLeftTab)) {
            if (isSelected) {
                textView.setTextColor(mTextColorSelected);
                textView.setBackgroundDrawable(mTabLeftDrawableSelected);
            } else {
                textView.setTextColor(mTextColorNormal);
                textView.setBackgroundDrawable(mTabLeftDrawableNormal);
            }
        } else {
            if (isSelected) {
                textView.setTextColor(mTextColorSelected);
                textView.setBackgroundDrawable(mTabRightDrawableSelected);
            } else {
                textView.setTextColor(mTextColorNormal);
                textView.setBackgroundDrawable(mTabRightDrawableNormal);
            }
        }
    }

    public interface OnTabChangeListener {
        void onTabChange(int curPos);
    }

    public void setTabSelected(int position) {
        if (position == TAB_LEFT) {
            selectTab(mLeftTab);
        } else {
            selectTab(mRightTab);
        }
    }


    public void setOnTabChangeListener(OnTabChangeListener listener) {
        mOnTabChangeListener = listener;
    }

}
