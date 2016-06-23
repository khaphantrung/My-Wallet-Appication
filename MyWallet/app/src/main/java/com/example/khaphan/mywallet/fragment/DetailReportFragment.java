package com.example.khaphan.mywallet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khaphan.mywallet.R;
import com.example.khaphan.mywallet.StringFormat;
import com.example.khaphan.mywallet.adapter.ListDetailReportAdapter;
import com.example.khaphan.mywallet.database.WalletDatabase;
import com.example.khaphan.mywallet.object.Category;
import com.example.khaphan.mywallet.object.CategoryReport;
import com.example.khaphan.mywallet.object.Item;

import java.util.ArrayList;

/**
 * Created by kha.phan on 6/23/2016.
 */
public class DetailReportFragment extends Fragment {

    private String dateReport;
    private ImageView mImgBack;
    private TextView mTextTitle;
    private ListView mLvReport;
    private ArrayList arrayListReport = new ArrayList();
    private ListDetailReportAdapter mDetailReportAdapter;
    private WalletDatabase mWalletDatabase;



    public void setMonthReport(String dateReport) {
        this.dateReport = dateReport;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_detail_report, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWalletDatabase = new WalletDatabase(getActivity());
        getWidgets(view);
        addEvent();
        setTitleToolbar();
        createArrayListReport();
        setupReportListView();
    }

    private void getWidgets(View view) {
        mImgBack = (ImageView) view.findViewById(R.id.img_back);
        mTextTitle = (TextView) view.findViewById(R.id.text_toolbar_title);
        mLvReport = (ListView) view.findViewById(R.id.lv_detail_report);

    }

    private void addEvent() {
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_left);
                ft.addToBackStack(null);
                ft.replace(R.id.layout_fragment, new WalletManagerFragment());
                ft.commit();
            }
        });
    }

    private void setTitleToolbar() {
        int month = Integer.parseInt(StringFormat.dateToMonth(dateReport));
        String year = StringFormat.dateToYear(dateReport);
        mTextTitle.setText("Detail for " + StringFormat.intToMonth(month) + " " + year);
    }

    private void createArrayListReport() {
        ArrayList<Category> arrayCategory = mWalletDatabase.getAllCategory();
        for (Category category : arrayCategory) {
            ArrayList<Item> arrayItem = mWalletDatabase.getAllItemByCategory(category.getIdCategory());
            int income = 0, expense = 0, numberItem=0;
            for (Item item : arrayItem) {
                String month= StringFormat.dateToMonth(item.getDate());
                String year = StringFormat.dateToYear(item.getDate());
                String monthReport= StringFormat.dateToMonth(dateReport);
                String yearReport= StringFormat.dateToYear(dateReport);
                if(month.equals(monthReport)&&year.equals(yearReport)){
                    if (item.getTypeItem().equals("Income")) {
                        income += Integer.parseInt(item.getValue());
                    } else expense += Integer.parseInt(item.getValue());
                }
                numberItem++;
            }
            int value=0;
            String type="Income";
            if(income>=expense) {
                value = income-expense;
                type="Income";
            }
            else {
                value = expense-income;
                type = "Expense";
            }
            if(numberItem>0) {
                CategoryReport categoryReport = new CategoryReport(category.getIdCategory(), value, type, category.getNameCategory());
                arrayListReport.add(categoryReport);
                for (Item item : arrayItem) {
                    String month = StringFormat.dateToMonth(item.getDate());
                    String year = StringFormat.dateToYear(item.getDate());
                    String monthReport = StringFormat.dateToMonth(dateReport);
                    String yearReport = StringFormat.dateToYear(dateReport);
                    if (month.equals(monthReport) && year.equals(yearReport)) {
                        arrayListReport.add(item);
                    }
                }
            }
        }
        Log.d("+++1@@+ arrayListReport", "createArrayListReport: item");
        for (int i=0; i<arrayListReport.size(); i++) {
            Log.d("+++1@@+ arrayListReport", "createArrayListReport: item" +arrayListReport.get(i).toString());
        }
    }
    private void setupReportListView(){
        mDetailReportAdapter = new ListDetailReportAdapter(getContext(),arrayListReport);
        mLvReport.setAdapter(mDetailReportAdapter);
    }
}
