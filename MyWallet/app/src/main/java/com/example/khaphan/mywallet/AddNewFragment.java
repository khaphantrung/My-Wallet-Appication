package com.example.khaphan.mywallet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by kha.phan on 6/17/2016.
 */
public class AddNewFragment extends Fragment implements View.OnClickListener {
    private static final int SHOW_DATE_PICKER = 1;
    private TextView mTextIncome, mTextExpense, mTextTitle;
    private TextView mTextInteger, mTextDecimal, mTextCurrencyUnit;
    private TextView mKb1, mKb2, mKb3, mKb4, mKb5, mKb6, mKb7, mKb8, mKb9, mKb0, mKbComma, mKbOk, mKbClear;
    private TextView mEditNote, mTextDate, mTextCatelory;
    private ImageView mImgDate;
    private boolean mDecimalEvent = false;
    private int mIndexDecimal = 0;
    private int mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener mMyDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mTextDate.setText(year+"-"+monthOfYear+1+"-"+dayOfMonth);
        }
    };

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

    private void getWidget(View view) {
        mTextTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mTextTitle.setText("Add New");
        mTextIncome = (TextView) view.findViewById(R.id.text_income);
        mTextExpense = (TextView) view.findViewById(R.id.text_expense);
        mKb0 = (TextView) view.findViewById(R.id.text_kb0);
        mKb1 = (TextView) view.findViewById(R.id.text_kb1);
        mKb2 = (TextView) view.findViewById(R.id.text_kb2);
        mKb3 = (TextView) view.findViewById(R.id.text_kb3);
        mKb4 = (TextView) view.findViewById(R.id.text_kb4);
        mKb5 = (TextView) view.findViewById(R.id.text_kb5);
        mKb6 = (TextView) view.findViewById(R.id.text_kb6);
        mKb7 = (TextView) view.findViewById(R.id.text_kb7);
        mKb8 = (TextView) view.findViewById(R.id.text_kb8);
        mKb9 = (TextView) view.findViewById(R.id.text_kb9);
        mKbComma = (TextView) view.findViewById(R.id.text_kb_comma);
        mKbClear = (TextView) view.findViewById(R.id.text_kb_clear);
        mKbOk = (TextView) view.findViewById(R.id.text_kb_ok);
        mTextDecimal = (TextView) view.findViewById(R.id.text_decimal_part);
        mTextInteger = (TextView) view.findViewById(R.id.text_integer_part);
        mTextCurrencyUnit = (TextView) view.findViewById(R.id.text_currency_unit);
        mImgDate = (ImageView) view.findViewById(R.id.img_date);
        mEditNote = (EditText) view.findViewById(R.id.edit_note);
        mTextDate = (TextView) view.findViewById(R.id.text_date);
        mYear = Calendar.getInstance().get(Calendar.YEAR);

        mMonth = Calendar.getInstance().get(Calendar.MONTH);
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private void addEvent() {
        mTextIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextExpense.setBackgroundResource(R.drawable.bg_textview_disable);
                mTextExpense.setTextColor(getActivity().getResources().getColor(R.color.black));
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.animator.anim_in_textview_addnew);
                mTextIncome.startAnimation(hyperspaceJumpAnimation);
                mTextIncome.setBackgroundResource(R.drawable.bg_textview_income);
                mTextIncome.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTextInteger.setTextColor(getActivity().getResources().getColor(R.color.SpringGreen1));
                mTextDecimal.setTextColor(getActivity().getResources().getColor(R.color.SpringGreen1));
                mTextCurrencyUnit.setTextColor(getActivity().getResources().getColor(R.color.SpringGreen1));
            }
        });
        mTextExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextIncome.setBackgroundResource(R.drawable.bg_textview_disable);
                mTextIncome.setTextColor(getActivity().getResources().getColor(R.color.black));
                Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.animator.anim_ex_textview_addnew);
                mTextExpense.startAnimation(hyperspaceJumpAnimation);
                mTextExpense.setBackgroundResource(R.drawable.bg_textview_expense);
                mTextExpense.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTextInteger.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                mTextDecimal.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                mTextCurrencyUnit.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
            }
        });
        mKb0.setOnClickListener(this);
        mKb1.setOnClickListener(this);
        mKb2.setOnClickListener(this);
        mKb3.setOnClickListener(this);
        mKb4.setOnClickListener(this);
        mKb5.setOnClickListener(this);
        mKb6.setOnClickListener(this);
        mKb7.setOnClickListener(this);
        mKb8.setOnClickListener(this);
        mKb9.setOnClickListener(this);
        mKbComma.setOnClickListener(this);
        mKbClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        mImgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

    }



    @Override
    public void onClick(View v) {
        String tittle = ((TextView) v).getText().toString();
        switch (tittle) {
            case "1":
                addPrice(1);
                break;
            case "2":
                addPrice(2);
                break;
            case "3":
                addPrice(3);
                break;
            case "4":
                addPrice(4);
                break;
            case "5":
                addPrice(5);
                break;
            case "6":
                addPrice(6);
                break;
            case "7":
                addPrice(7);
                break;
            case "8":
                addPrice(8);
                break;
            case "9":
                addPrice(9);
                break;
            case "0":
                addPrice(0);
                break;
            case ",":
                mDecimalEvent = true;
                break;
        }
    }

    private void addPrice(int key) {
        if (mDecimalEvent) {
            String decimal = mTextDecimal.getText().toString().substring(1);

            if (mIndexDecimal == 0) {
                decimal = ","+key + "00";
                mTextDecimal.setText(decimal);
                mIndexDecimal++;
            } else if (mIndexDecimal == 1) {
                Log.d("1++++decimal", "addPrice: " + decimal.substring(0, 1));
                decimal = ","+decimal.substring(0, 1) + key + "0";
                mTextDecimal.setText(decimal);

                mIndexDecimal++;
            } else if (mIndexDecimal == 2) {
                decimal = ","+decimal.substring(0, 2) + key;
                mTextDecimal.setText(decimal);
                Log.d("++++decimal", "addPrice: " + decimal);
                mIndexDecimal++;
            } else return;
        } else {
            if(mTextInteger.getText().toString().length()==9){
                mTextInteger.setText("100000000");
                Toast.makeText(getActivity(),"max is 100 billion",Toast.LENGTH_SHORT).show();
            }
            else {
                String integer = mTextInteger.getText().toString();
                if (integer.equals("0")) mTextInteger.setText(key + "");
                else mTextInteger.setText(integer + key);
            }
        }
    }
    private void clear(){
        if(mDecimalEvent){
            switch (mIndexDecimal) {
                case 3:
                    mTextDecimal.setText(mTextDecimal.getText().toString().substring(0, 3) + "0");
                    mIndexDecimal--;
                    break;
                case 2:
                    mTextDecimal.setText(mTextDecimal.getText().toString().substring(0, 2) + "00");
                    mIndexDecimal--;
                    break;
                case 1:
                    mTextDecimal.setText(",000");
                    mIndexDecimal--;
                    mDecimalEvent = false;
                    break;
            }
        }
        else {
            int leng = mTextInteger.getText().toString().length();
            if(leng==1) mTextInteger.setText("0");
            else {
                mTextInteger.setText(mTextInteger.getText().toString().substring(0,leng-1));
            }

        }
    }

    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            mTextDate.setText(year+"-"+month+"-"+day);
        }

    }
}
