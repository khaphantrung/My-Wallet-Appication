package com.example.khaphan.mywallet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khaphan.mywallet.activity.MainActivity;
import com.example.khaphan.mywallet.database.WalletDatabase;
import com.example.khaphan.mywallet.fragment.DetailReportFragment;
import com.example.khaphan.mywallet.object.Item;

import java.util.ArrayList;

/**
 * Created by kha.phan on 6/22/2016.
 */
public class ReportDialog extends Dialog {

    private Activity mActivity;
    private String mDate;
    private int mMonth=1;
    private String mYear;
    private WalletDatabase mWalletDatabase;
    private TextView mTextTotal, mTextIncome, mTextExpense, mTextTitle;
    private ImageView mImgShowDetailReport;

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

        showSummaryReport();
        addEvent();

    }
    private void showSummaryReport(){
        mTextTitle.setText("Summary for " + StringFormat.intToMonth(mMonth) +" "+ mYear);
        int income=0,expense=0;
        mWalletDatabase = new WalletDatabase(mActivity);
        ArrayList<Item> arrayList = mWalletDatabase.getAllItem();

        for (int i=0; i<arrayList.size(); i++){
            String month= StringFormat.dateToMonth(arrayList.get(i).getDate());
            String year = StringFormat.dateToYear(arrayList.get(i).getDate());
            if(mMonth==Integer.parseInt(month)&&mYear.equals(year)){
                if (arrayList.get(i).getTypeItem().equals("Income")) {
                    income += Integer.parseInt(arrayList.get(i).getValue());
                }
                else expense+= Integer.parseInt(arrayList.get(i).getValue());
            }
        }
        mTextIncome.setText(StringFormat.toFormatThousand(income + "") + " VND");
        mTextExpense.setText(StringFormat.toFormatThousand(expense + "") + " VND");
        int total=0;
        String stringTotal="";
        if(income>=expense) {
            total=income-expense;
            stringTotal =  StringFormat.toFormatThousand(total+"");
        }
        else  {
            total = expense - income;
            stringTotal =  "-"+StringFormat.toFormatThousand(total+"");
        }
        mTextTotal.setText(stringTotal + "VND");
    }
    private void getWidgets(){
        mTextTotal = (TextView) findViewById(R.id.text_total);
        mTextIncome = (TextView) findViewById(R.id.text_income_report);
        mTextExpense = (TextView) findViewById(R.id.text_expense_report);
        mTextTitle = (TextView) findViewById(R.id.text_title_report);
        mImgShowDetailReport= (ImageView) findViewById(R.id.img_detail_report);
    }
    private void addEvent(){
        mImgShowDetailReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                FragmentTransaction ft = ((MainActivity)mActivity).getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.silde_out_right, R.animator.slide_in_right);
                ft.addToBackStack(null);
                DetailReportFragment fragment = new DetailReportFragment();
                fragment.setMonthReport(mDate);
                ft.replace(R.id.layout_fragment, fragment);
                ft.commit();
            }
        });
    }



}
