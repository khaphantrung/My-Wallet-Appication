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
import com.example.khaphan.mywallet.object.CategoryReport;
import com.example.khaphan.mywallet.object.Item;

import java.util.ArrayList;

/**
 * Created by kha.phan on 6/23/2016.
 */
public class ListDetailReportAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList arrayCategoryReport;

    public ListDetailReportAdapter(Context mContext, ArrayList arrayList) {
        this.mContext = mContext;
        this.arrayCategoryReport = arrayList;
    }

    @Override
    public int getCount() {
        return arrayCategoryReport.size();
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
        try {
            CategoryReport categoryReport = (CategoryReport)arrayCategoryReport.get(position);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_listview_category_detailreport, null);
            }
            ImageView imgIcon =(ImageView) view.findViewById(R.id.img_category_item_report);
            String iconName = "ic_category_"+categoryReport.getIdCategory();
            int id = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
            imgIcon.setImageResource(id);
            TextView textNameCategory = (TextView) view.findViewById(R.id.text_category_name_report);
            textNameCategory.setText(categoryReport.getNameCategory());

            TextView textValue = (TextView) view.findViewById(R.id.text_category_value_report);
            textValue.setText(StringFormat.toFormatThousand(categoryReport.getValue()+"") + " VND");

            if(categoryReport.getType().equals("Income")){
                textValue.setTextColor(Color.parseColor("#00FF7F"));
            }
            else textValue.setTextColor(Color.parseColor("#FF4081"));
            return view;

        }catch (ClassCastException e){
            Item item = (Item)arrayCategoryReport.get(position);
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_listview_item_detailreport, null);
            }
            TextView textNameItem= (TextView)view.findViewById(R.id.text_name_item_report);
            textNameItem.setText(item.getNameItem());

            TextView textDateItem = (TextView) view.findViewById(R.id.text_date_item_report);
            textDateItem.setText(item.getDate());

            TextView textValueItem = (TextView) view.findViewById(R.id.text_value_item_report);
            textValueItem.setText(StringFormat.toFormatThousand(item.getValue()) + " VND");

            if(item.getTypeItem().equals("Income")){
                textValueItem.setTextColor(Color.parseColor("#00FF7F"));
            }
            else textValueItem.setTextColor(Color.parseColor("#FF4081"));
            return view;
        }

    }
}
