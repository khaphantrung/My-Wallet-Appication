package com.example.khaphan.mywallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.khaphan.mywallet.database.WalletDatabase;
import com.example.khaphan.mywallet.object.Item;

import java.util.ArrayList;

/**
 * Created by kha.phan on 6/22/2016.
 */
public class ReportDialog extends Dialog {

    private Activity mActivity;
    private String mDate;
    private int mMonth;
    private String mYear;
    private WalletDatabase mWalletDatabase;
    private TextView mTextTotal, mTextIncome, mTextExpense, mTextTitle;

    public ReportDialog(Activity activity,String date) {
        super(activity);
        this.mActivity = activity;
        mDate = date;
        mMonth = Integer.parseInt(StringFormat.dateToMonth(date));
        mYear = StringFormat.dateToYear(date);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        getWidgets();

        mTextTitle.setText("Summary for "+ intToMonth(mMonth));
        int income=0,expense=0;
        mWalletDatabase = new WalletDatabase(mActivity);
        Log.d("@@_3++Month", "Month: "+ mMonth);
        ArrayList<Item> arrayList = mWalletDatabase.getAllItem();

        for (int i=0; i<arrayList.size(); i++){
            String month= StringFormat.dateToMonth(arrayList.get(i).getDate());
            if(mMonth==Integer.parseInt(month)){
                if (arrayList.get(i).getTypeItem().equals("Income")) {
                    income += Integer.parseInt(arrayList.get(i).getValue());
                }
                else expense+= Integer.parseInt(arrayList.get(i).getValue());
            }
        }
        Log.d("@@_3++income", "onCreate: " + StringFormat.toFormatThousand(income + "") + " VND");
        mTextIncome.setText(StringFormat.toFormatThousand(income+"") + " VND");
        mTextExpense.setText(StringFormat.toFormatThousand(expense+"") + " VND");
        mTextTotal.setText(StringFormat.toFormatThousand((income - expense)+"")+ "VND");
    }
    private void getWidgets(){
        mTextTotal = (TextView) findViewById(R.id.text_total);
        mTextIncome = (TextView) findViewById(R.id.text_income_report);
        mTextExpense = (TextView) findViewById(R.id.text_expense_report);
        mTextTitle = (TextView) findViewById(R.id.text_title_report);
    }

    private String intToMonth (int num) {
        String month = "wrong";
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }
}
