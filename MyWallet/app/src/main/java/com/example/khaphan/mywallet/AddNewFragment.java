package com.example.khaphan.mywallet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kha.phan on 6/17/2016.
 */
public class AddNewFragment extends Fragment {
    private TextView mIncome, mExpense, mTextTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_addnew, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWidget(view);
        addEvent();
    }
    private void getWidget(View view){
        mTextTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mTextTitle.setText("Add New");
        mIncome = (TextView) view.findViewById(R.id.text_income);
        mExpense = (TextView) view.findViewById(R.id.text_expense);
    }
    private void addEvent(){
        mIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpense.setBackgroundResource(R.drawable.round_textview_disable);
                mExpense.setTextColor(getActivity().getResources().getColor(R.color.black));
                mIncome.setBackgroundResource(R.drawable.round_textview_income);
                mIncome.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
        });
        mExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIncome.setBackgroundResource(R.drawable.round_textview_disable);
                mIncome.setTextColor(getActivity().getResources().getColor(R.color.black));
                mExpense.setBackgroundResource(R.drawable.round_textview_expense);
                mExpense.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
        });
    }
}
