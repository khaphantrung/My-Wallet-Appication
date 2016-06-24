package com.example.khaphan.mywallet.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khaphan.mywallet.R;
import com.example.khaphan.mywallet.StringFormat;
import com.example.khaphan.mywallet.database.WalletDatabase;
import com.example.khaphan.mywallet.object.Category;
import com.example.khaphan.mywallet.object.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by kha.phan on 6/17/2016.
 */
public class AddNewFragment extends Fragment implements View.OnClickListener {

    private TextView mTextIncome, mTextExpense, mTextTitle, mTextAnimationIncome,mTextAnimationExpense ;
    private String title;
    private TextView mTextInteger, mTextDecimal, mTextCurrencyUnit;
    private TextView mKb1, mKb2, mKb3, mKb4, mKb5, mKb6, mKb7, mKb8, mKb9, mKb0, mKbComma, mKbOk, mKbClear;
    private TextView mEditNote, mTextDate, mTextCategory;
    private ImageView mImgBack, mImgDate, mImgCategory, mImgShowCategory;
    private boolean mDecimalEvent = false;
    private int mIndexDecimal = 0;
    private WalletDatabase mWalletDatabase;
    private Item item;
    private Category mCategory;
    private String mType = "Income";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_addnew, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mWalletDatabase = new WalletDatabase(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWidget(view);
        addEvent();
        mTextTitle.setText(title);
        if (title.equals("Add New")) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            mTextDate.setText(date);
        } else {
            mEditNote.setText(item.getNameItem());
            mTextDate.setText(item.getDate());
            if (item.getTypeItem().equals("Income")) {
                selectTypeIncome();
            } else selectTypeExpense();
            int idCategory = item.getIdCategory();
            switch (idCategory) {
                case 0:
                    mImgCategory.setImageResource(R.drawable.ic_category_0);
                    mTextCategory.setText("Other");
                    break;
                case 1:
                    mImgCategory.setImageResource(R.drawable.ic_category_1);
                    mTextCategory.setText("Market");
                    break;
                case 2:
                    mImgCategory.setImageResource(R.drawable.ic_category_2);
                    mTextCategory.setText("Cinema");
                    break;
            }
            String value = item.getValue();
            mTextDecimal.setText("," + value.substring(value.length() - 3));

            value = value.substring(0, value.length() - 3);
            value = StringFormat.toFormatThousand(value);
            Log.d("+-+value+-+", "value: " + value);
            mTextInteger.setText(value);


        }
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void getWidget(View view) {
        mTextTitle = (TextView) view.findViewById(R.id.text_toolbar_title);
        mImgBack = (ImageView) view.findViewById(R.id.img_back);
        mTextIncome = (TextView) view.findViewById(R.id.text_income);
        mTextExpense = (TextView) view.findViewById(R.id.text_expense);
        mTextAnimationIncome = (TextView) view.findViewById(R.id.text_animation_icome);
        mTextAnimationExpense =  (TextView) view.findViewById(R.id.text_animation_expense);
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
        mImgCategory = (ImageView) view.findViewById(R.id.img_category);
        mImgShowCategory = (ImageView) view.findViewById(R.id.img_show_category);
        mTextCategory = (TextView) view.findViewById(R.id.text_category);
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
        mTextIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTypeIncome();
            }
        });
        mTextExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTypeExpense();
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
        mImgShowCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemCatelory();
            }
        });
        mKbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.equals("Add New")) saveNewItem();
                else editItem();
            }
        });

    }

    private void editItem() {
        String note = mEditNote.getText().toString();
        String date = mTextDate.getText().toString();
        String value = StringFormat.notFormatThousand(mTextInteger.getText() + mTextDecimal.getText().toString().substring(1));

        String nameCategory = mTextCategory.getText().toString();
        int idCategory = 0;

        switch (nameCategory) {
            case "Others":
                idCategory = 0;
                break;
            case "Market":
                idCategory = 1;
                break;
            case "Cinema":
                idCategory = 2;
                break;
        }
        item = new Item(item.getIdItem(), mType, note, date, value, idCategory);
        if (mWalletDatabase.updateItem(item)) {
            Toast.makeText(getActivity(), "edit success", Toast.LENGTH_SHORT).show();
        }

    }

    private void selectTypeIncome() {
//        mTextExpense.setBackgroundResource(R.drawable.bg_textview_disable);
        mTextAnimationExpense.setBackgroundResource(R.drawable.bg_textview_disable);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.animator.anim_in_textview_addnew);
        mTextAnimationIncome.startAnimation(hyperspaceJumpAnimation);
        mTextAnimationIncome.setBackgroundResource(R.drawable.bg_textview_income);
        mTextIncome.setTextColor(getActivity().getResources().getColor(R.color.white));
        mTextExpense.setTextColor(getActivity().getResources().getColor(R.color.black));
        mTextInteger.setTextColor(getActivity().getResources().getColor(R.color.SpringGreen1));
        mTextDecimal.setTextColor(getActivity().getResources().getColor(R.color.SpringGreen1));
        mTextCurrencyUnit.setTextColor(getActivity().getResources().getColor(R.color.SpringGreen1));
        mType = "Income";
    }

    private void selectTypeExpense() {
        mTextAnimationIncome.setBackgroundResource(R.drawable.bg_textview_disable);

        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.animator.anim_ex_textview_addnew);
        mTextAnimationExpense.startAnimation(hyperspaceJumpAnimation);
        mTextAnimationExpense.setBackgroundResource(R.drawable.bg_textview_expense);
        mTextExpense.setTextColor(getActivity().getResources().getColor(R.color.white));
        mTextIncome.setTextColor(getActivity().getResources().getColor(R.color.black));
        mTextInteger.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
        mTextDecimal.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
        mTextCurrencyUnit.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
        mType = "Expense";
    }

    private void saveNewItem() {
        boolean insertCategory = true;
        int idItem = 0;
        String note = mEditNote.getText().toString();
        String date = mTextDate.getText().toString();
        String value = StringFormat.notFormatThousand(mTextInteger.getText() + mTextDecimal.getText().toString().substring(1));
        Log.d("+@@_value", "saveNewItem: value" + value);

        String nameCategory = mTextCategory.getText().toString();
        String iconCategory = mTextCategory.getText().toString();
        int idCategory = 0;
        switch (nameCategory) {
            case "Others":
                idCategory = 0;
                break;
            case "Market":
                idCategory = 1;
                break;
            case "Cinema":
                idCategory = 2;
                break;
        }


        ArrayList<Item> arrayItem = mWalletDatabase.getAllItem();
        if (arrayItem.size()==0) idItem = 1;
        else {
            idItem = arrayItem.get(arrayItem.size() - 1).getIdItem() + 1;
        }
        if (!mWalletDatabase.isCategory(idCategory)) {
            mCategory = new Category(idCategory, nameCategory, iconCategory);
            if (!mWalletDatabase.insertCategory(mCategory)) insertCategory = false;
        }
        //   Log.d("@33+++++++++", mCategory.toString());
        if (insertCategory) {
            item = new Item(idItem, mType, note, date, value, idCategory);

            if (mWalletDatabase.insertItem(item)) {
                Toast.makeText(getActivity(), "Add item success", Toast.LENGTH_SHORT).show();
            }
        }

//        Log.d("@33+++++++++", item.toString());


//        Log.d("@@4walletdatabase", "date" +date);
//        ArrayList<Item> arrayItemDate = mWalletDatabase.getAllItemByDate(date);
//        for (Item item : arrayItemDate) {
//
//            Log.d("@@4walletdatabase","by date" +item.toString());
//        }
//        ArrayList<Category> arrayCategory = mWalletDatabase.getAllCategory();
//        for (Category category : arrayCategory) {
//            Log.d("@@4walletdatabase", category.toString());
//        }

    }

    private void showItemCatelory() {
        PopupMenu popup = new PopupMenu(getActivity(), mImgCategory);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.fragment_addnew_category, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_cinema:

                        mImgCategory.setImageResource(R.drawable.ic_category_2);
                        mTextCategory.setText("Cinema");
                        break;
                    case R.id.item_market:
                        mImgCategory.setImageResource(R.drawable.ic_category_1);
                        mTextCategory.setText("Market");
                        break;
                    case R.id.item_others:
                        mImgCategory.setImageResource(R.drawable.ic_category_0);
                        mTextCategory.setText("Other");
                        break;
                }
                return true;
            }
        });

        popup.show();
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
                decimal = "," + key + "00";
                mTextDecimal.setText(decimal);
                mIndexDecimal++;
            } else if (mIndexDecimal == 1) {
                Log.d("1++++decimal", "addPrice: " + decimal.substring(0, 1));
                decimal = "," + decimal.substring(0, 1) + key + "0";
                mTextDecimal.setText(decimal);

                mIndexDecimal++;
            } else if (mIndexDecimal == 2) {
                decimal = "," + decimal.substring(0, 2) + key;
                mTextDecimal.setText(decimal);
                Log.d("++++decimal", "addPrice: " + decimal);
                mIndexDecimal++;
            } else return;
        } else {
            if (mTextInteger.getText().toString().length() == 7) {
                mTextInteger.setText("100,000");
                Toast.makeText(getActivity(), "max is 100 milion", Toast.LENGTH_SHORT).show();
            } else {
                String integer = mTextInteger.getText().toString();
                if (integer.equals("0")) mTextInteger.setText(key + "");
                else {
                    integer += key;
                    Log.d("+@@7_interger", "addPrice: " + integer);
                    integer = StringFormat.notFormatThousand(integer);
                    integer = StringFormat.toFormatThousand(integer);
                    mTextInteger.setText(integer);
                }
            }
        }
    }

    private void clear() {
        if (mDecimalEvent) {
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
        } else {
            int leng = mTextInteger.getText().toString().length();
            if (leng == 1) mTextInteger.setText("0");
            else {
                String value = mTextInteger.getText().toString().substring(0, leng - 1);
                value = StringFormat.notFormatThousand(value);
                value = StringFormat.toFormatThousand(value);
                mTextInteger.setText(value);
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
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            String stringMonth= month+"";
            String stringDay=day+"";
            if((month+"").length()==1) stringMonth = "0"+stringMonth;
            if((day+"").length()==1) stringDay = "0"+stringDay;
            mTextDate.setText(year + "-" + stringMonth + "-" + stringDay);
        }

    }
}
