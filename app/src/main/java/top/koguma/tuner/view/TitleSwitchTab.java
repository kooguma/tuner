package top.koguma.tuner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

    private Drawable mTabDrawableNormal;
    private Drawable mTabDrawableSelected;

    private int mTextSize;//标题文字大小

    private static final int TAB0 = 0;
    private static final int TAB1 = 1;
    private static final int TAB2 = 2;

    private int mCurPos = TAB0;

    private int[] mTitles;

    private TextView mTab1;
    private TextView mTab2;
    private TextView mTab3;

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

        mTabDrawableNormal = getResources().getDrawable(R.drawable.bg_login_tab_normal);
        mTabDrawableSelected = getResources().getDrawable(R.drawable.bg_login_tab_selected);

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
            LayoutParams tabParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        tabParams.weight = 1;
        LayoutParams spaceParams = new LayoutParams(DisplayUtils.dip2px(getContext(), 1),
            LayoutParams.MATCH_PARENT);
        spaceParams.leftMargin = DisplayUtils.dip2px(getContext(),16);
        spaceParams.topMargin = DisplayUtils.dip2px(getContext(),8);
        spaceParams.rightMargin = DisplayUtils.dip2px(getContext(),16);
        spaceParams.bottomMargin = DisplayUtils.dip2px(getContext(),8);
        mTab1 = getTextView(TAB0);
        mTab2 = getTextView(TAB1);
        mTab3 = getTextView(TAB2);
        View spaceLine1 = getSpacingView();
        View spaceLine2 = getSpacingView();
        addView(mTab1, tabParams);
        addView(spaceLine1, spaceParams);
        addView(mTab2, tabParams);
        addView(spaceLine2, spaceParams);
        addView(mTab3, tabParams);
        initTab();
    }

    private TextView getTextView(int index) {
        final TextView t = new TextView((getContext()));
        t.setTextSize(mTextSize);
        t.setText(mTitles[index]);
        t.setGravity(Gravity.CENTER);
        t.setPadding(
            DisplayUtils.dip2px(getContext(), 20),
            DisplayUtils.dip2px(getContext(), 7),
            DisplayUtils.dip2px(getContext(), 20),
            DisplayUtils.dip2px(getContext(), 7));
        t.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                selectTab(t);
            }
        });
        return t;
    }

    private View getSpacingView() {
        final View v = new View(getContext());
        v.setBackgroundColor(Color.WHITE);
        return v;
    }

    private void selectTab(TextView textView) {
        if (textView.equals(mTab1)) {
            mCurPos = TAB0;
            updateTab(mTab1, true);
            updateTab(mTab2, false);
            updateTab(mTab3, false);
        } else if (textView.equals(mTab2)) {
            mCurPos = TAB1;
            updateTab(mTab2, true);
            updateTab(mTab1, false);
            updateTab(mTab3, false);
        } else {
            mCurPos = TAB2;
            updateTab(mTab3, true);
            updateTab(mTab1, false);
            updateTab(mTab2, false);
        }

        if (mOnTabChangeListener != null) {
            mOnTabChangeListener.onTabChange(mCurPos);
        }
    }

    private void initTab() {
        updateTab(mTab1, true);
        updateTab(mTab2, false);
        updateTab(mTab3, false);
    }

    private void updateTab(TextView textView, boolean isSelected) {
        if (isSelected) {
            textView.setTextColor(mTextColorSelected);
            textView.setBackgroundDrawable(mTabDrawableSelected);
        } else {
            textView.setTextColor(mTextColorNormal);
            textView.setBackgroundDrawable(mTabDrawableNormal);
        }
    }

    public interface OnTabChangeListener {
        void onTabChange(int curPos);
    }

    public void setTabSelected(int position) {
        if (position == TAB0) {
            selectTab(mTab1);
        } else if (position == TAB1) {
            selectTab(mTab2);
        } else {
            selectTab(mTab3);
        }
    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        mOnTabChangeListener = listener;
    }

}
