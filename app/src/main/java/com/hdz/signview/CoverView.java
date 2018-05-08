package com.hdz.signview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Created by hdz on 2017/6/13.
 */

public class CoverView<T> extends ViewGroup {


    public static final int STYLE_LEFT_TO_RIGHT = 0;
    public static final int STYLE_RIGHT_TO_LEFT = 1;

    //图片的直径
    private int itemDia;

    //覆盖的宽度
    private int coverWidth = 0;

    //最终显示的图片数量
    private int showCount = 0;

    private List<T> data;
    private List<T> showData = new ArrayList<>();

    private CoverAdapter<T> adapter;

    private List<ImageView> imageViewList = new ArrayList<>();

    private int displayStyle;

    public CoverView(Context context) {
        this(context, null, 0);
    }

    public CoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CoverView);
        coverWidth = (int) array.getDimension(R.styleable.CoverView_coverWidth, 0);
        itemDia = (int) array.getDimension(R.styleable.CoverView_itemDia, 50);
        displayStyle = array.getInt(R.styleable.CoverView_display_style, STYLE_LEFT_TO_RIGHT);
        //两个图片重叠的宽度不应该大于图片的直径
        if (coverWidth >= itemDia) {
            coverWidth = 0;
        }
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (data == null || data.isEmpty()) return;
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        int maxShowCounts = (width - coverWidth) / (itemDia - coverWidth);
        showData.clear();
        if (maxShowCounts < data.size()) {
            for (int i = 0; i < maxShowCounts; i++) {
                showData.add(data.get(i));
            }
        } else {
            showData.addAll(data);
        }
        showCount = showData.size();
//        int totalWidth = itemDia * showCount - coverWidth * (showCount - 1) + getPaddingLeft() + getPaddingRight();
        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(totalWidth, itemDia + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (showData == null || adapter == null) return;
        if (displayStyle == STYLE_RIGHT_TO_LEFT) {
            Collections.reverse(showData);
        }
        for (int i = 0; i < showCount; i++) {
            ImageView childView = (ImageView) getChildAt(i);
            adapter.onDisplayImage(getContext(), childView, showData.get(i));
            int left;
            if (displayStyle == STYLE_RIGHT_TO_LEFT) {
                left = getPaddingLeft() + (itemDia - coverWidth) * (showCount - i - 1);
            } else {
                left = getPaddingLeft() + (itemDia - coverWidth) * i;
            }
            int right = left + itemDia;
            int top = getPaddingTop();
            int bottom = getPaddingTop() + itemDia;
            childView.layout(left, top, right, bottom);
        }
    }

    public void setData(List<T> list) {
        if (list == null || list.isEmpty()) {
            setVisibility(View.GONE);
            return;
        } else {
            setVisibility(View.VISIBLE);
        }
        data = list;

        for (int i = 0; i < data.size(); i++) {
            ImageView iv = getImageView(i);
            if (iv == null) {
                return;
            }
            addView(iv, generateDefaultLayoutParams());
        }
        requestLayout();
    }

    private ImageView getImageView(final int position) {
        if (position < imageViewList.size()) {
            return imageViewList.get(position);
        } else {
            if (adapter != null) {
                ImageView iv = adapter.generateImageView(getContext());
                imageViewList.add(iv);
                return iv;
            } else {
                return null;
            }
        }
    }

    public void setAdapter(CoverAdapter<T> adapter) {
        this.adapter = adapter;
    }
}
