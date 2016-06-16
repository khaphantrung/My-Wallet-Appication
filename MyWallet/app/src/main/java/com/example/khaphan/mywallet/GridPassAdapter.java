package com.example.khaphan.mywallet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Created by kha.phan on 6/16/2016.
 */
public class GridPassAdapter extends BaseAdapter {
    private Activity mContext;
    private String[] mStrPass;
    private View.OnClickListener mListener;

    public GridPassAdapter(Activity mContext, String[] textPass, View.OnClickListener listener) {
        super();
        this.mContext = mContext;
        this.mStrPass = textPass;
        this.mListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_gridpass, null);
        }
        Button button = (Button) view.findViewById(R.id.button_pass);
        button.setText(mStrPass[position]);
        button.setOnClickListener(mListener);
        if (position == 9 || position == 11) {
            button.setVisibility(Button.GONE);
        }
        return view;
    }
    @Override
    public int getCount() {
        return mStrPass.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
