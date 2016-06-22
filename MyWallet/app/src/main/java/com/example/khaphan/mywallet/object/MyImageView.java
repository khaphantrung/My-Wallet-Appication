package com.example.khaphan.mywallet.object;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by kha.phan on 6/22/2016.
 */
public class MyImageView extends ImageView {

    private int indexItem;

    public int getIndexItem() {
        return indexItem;
    }

    public void setIndexItem(int indexItem) {
        this.indexItem = indexItem;
    }

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
