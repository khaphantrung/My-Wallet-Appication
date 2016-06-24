package com.example.khaphan.mywallet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khaphan.mywallet.R;
import com.example.khaphan.mywallet.StringFormat;
import com.example.khaphan.mywallet.object.Item;
import com.example.khaphan.mywallet.object.MyImageView;

import java.util.ArrayList;

/**
 * Created by kha.phan on 6/17/2016.
 */
public class ListWalletManagerAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Item> mArrayItem;
    private View.OnClickListener mListenerEdit;
    private View.OnClickListener mListenerDelete;

    public ListWalletManagerAdapter(Context mContext, ArrayList<Item> mArrayItem, View.OnClickListener mListenerEdit, View.OnClickListener mListenerDelete) {
        this.mContext = mContext;
        this.mArrayItem = mArrayItem;
        this.mListenerEdit = mListenerEdit;
        this.mListenerDelete = mListenerDelete;
    }

    @Override
    public int getCount() {
        return mArrayItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Item item = mArrayItem.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview_walletmanager, null);
        }
        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_category_item);
        String iconName = "ic_category_"+item.getIdCategory();
        int id = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        imgIcon.setImageResource(id);
        TextView textName = (TextView) view.findViewById(R.id.text_name_item);
        textName.setText(item.getNameItem());

        TextView textDate = (TextView) view.findViewById(R.id.text_date_item);
        textDate.setText(item.getDate());

        TextView textCost = (TextView) view.findViewById(R.id.text_cost_item);
        textCost.setText(StringFormat.toFormatThousand(item.getValue()) + " VND");
        if (textCost.equals("0,000 VND")) textCost.setText("0");
        if(item.getTypeItem().equals("Income")) {
            textCost.setTextColor(Color.parseColor("#00FF7F"));
        }
        else textCost.setTextColor(Color.parseColor("#FF4081"));
        MyImageView imgEdit = (MyImageView) view.findViewById(R.id.img_edit_item);
        imgEdit.setOnClickListener(mListenerEdit);
        imgEdit.setIndexItem(position);
        MyImageView imgDelete = (MyImageView) view.findViewById(R.id.img_delete_item);
        imgDelete.setOnClickListener(mListenerDelete);
        imgDelete.setIndexItem(position);

        return view;
    }

    public void updateArraylist(ArrayList<Item> arrayList){
        mArrayItem = arrayList;
        notifyDataSetChanged();
//        notifyAll();
    }

}
