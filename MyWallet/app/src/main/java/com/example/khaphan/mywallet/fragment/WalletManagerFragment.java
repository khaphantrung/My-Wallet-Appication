package com.example.khaphan.mywallet.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khaphan.mywallet.StringFormat;
import com.example.khaphan.mywallet.adapter.ListWalletManagerAdapter;
import com.example.khaphan.mywallet.R;
import com.example.khaphan.mywallet.ReportDialog;
import com.example.khaphan.mywallet.database.WalletDatabase;
import com.example.khaphan.mywallet.object.Item;
import com.example.khaphan.mywallet.object.MyImageView;
import com.samsistemas.calendarview.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kha.phan on 6/17/2016.
 */
public class WalletManagerFragment extends Fragment {

    private CalendarView mCalendarView;
    private String mDateSelected;
    private Button mBtnAddNew, mBtnDetailReport;
    private WalletDatabase mWalletDatabase;
    private ListView mListViewExchange;
    private ListWalletManagerAdapter mAdapterExchange;
    private ArrayList<Item> arrayListItem = new ArrayList<Item>();

    private View mItemListview;
    private View mItemLongClick;
    private Handler mTimerHandler = null;
    private static int VISIBILITY_TIMEOUT = 2000;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_wallet_manager, parent, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWalletDatabase = new WalletDatabase(getActivity());
        getWiget(view);
        setupCalendar();
        setupListview();
        addEvent();

    }

    private void getWiget(View view) {
        mCalendarView = (CalendarView) view.findViewById(R.id.calendar_view);
        mBtnAddNew = (Button) view.findViewById(R.id.btn_add_new);
        mBtnDetailReport = (Button) view.findViewById(R.id.btn_detail_report);
        mListViewExchange = (ListView) view.findViewById(R.id.lv_item_exchange);
    }

    private void addEvent() {
        mBtnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.silde_out_right, R.animator.slide_in_right);
                ft.addToBackStack(null);
                AddNewFragment frag = new AddNewFragment();
                frag.setTitle("Add New");
                ft.replace(R.id.layout_fragment, frag);
                ft.commit();
            }
        });
        mBtnDetailReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = "";
                if (mDateSelected != null) date = mDateSelected;
                else
                    date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                ReportDialog customDialog = new ReportDialog(getActivity(), date);
                customDialog.show();
            }
        });
        mListViewExchange.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

                if (mItemListview != null) {
                    view.findViewById(R.id.layout_info).animate().translationX(0);
                    view.findViewById(R.id.layout_mod).animate().translationX(view.getWidth() + view.findViewById(R.id.layout_info).getWidth());
                }
                view.findViewById(R.id.layout_info).animate().translationX(0 - view.findViewById(R.id.layout_mod).getWidth());
                view.findViewById(R.id.layout_mod).animate().translationX(view.getWidth() - view.findViewById(R.id.layout_info).getWidth());

                mItemListview = view;
                mItemLongClick = view;
                mTimerHandler = new Handler();
                mTimerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.findViewById(R.id.layout_info).animate().translationX(0);
                            view.findViewById(R.id.layout_mod).animate().translationX(view.getWidth() + view.findViewById(R.id.layout_info).getWidth());
                        }
                        mItemListview = null;
                        mTimerHandler = null;
                    }
                }, VISIBILITY_TIMEOUT);
                return false;
            }
        });
        mListViewExchange.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mItemLongClick != null) {
                    mItemLongClick.findViewById(R.id.layout_info).animate().translationX(0);
                    mItemLongClick.findViewById(R.id.layout_mod).animate().translationX(mItemLongClick.getWidth() + mItemLongClick.findViewById(R.id.layout_info).getWidth());
                }
            }
        });
    }

    private void setupCalendar() {
        mCalendarView.setIsOverflowDateVisible(true);
        mCalendarView.setCurrentDay(new Date(System.currentTimeMillis()));
        mCalendarView.setBackButtonColor(R.color.colorAccent);
        mCalendarView.setNextButtonColor(R.color.colorAccent);
        mCalendarView.setFirstDayOfWeek(1);
        mCalendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()));
        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                arrayListItem = new ArrayList<Item>();
                ArrayList<Item> arrayItemNew = mWalletDatabase.getAllItemByDate(df.format(date).toString());
                arrayListItem = arrayItemNew;
                mDateSelected = df.format(date);
                mAdapterExchange.updateArraylist(arrayListItem);
            }
        });
        mCalendarView.setOnMonthChangedListener(new CalendarView.OnMonthChangedListener() {
            @Override
            public void onMonthChanged(@NonNull Date monthDate) {
                Date dDate = Calendar.getInstance().getTime();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(dDate);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                if (date.equals(df.format(monthDate))) {
                    arrayListItem = new ArrayList<Item>();
                    ArrayList<Item> arrayItemNew = mWalletDatabase.getAllItemByDate(date);
                    arrayListItem = arrayItemNew;

                    mAdapterExchange.updateArraylist(arrayListItem);
                } else {
                    arrayListItem = new ArrayList<Item>();
                    ArrayList<Item> arrayItemNew = mWalletDatabase.getAllItem();
                    for (Item item : arrayItemNew) {
                        String dateItem = StringFormat.dateToMonth(item.getDate());
                        String month = StringFormat.dateToMonth(df.format(monthDate));
                        if (dateItem.equals(month)){
                            arrayListItem.add(item);
                        }
                    }
                    mAdapterExchange.updateArraylist(arrayListItem);
                }
                mDateSelected = df.format(monthDate);

            }
        });
    }
    private void setupListview(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        if (mWalletDatabase!=null) {
            arrayListItem = mWalletDatabase.getAllItemByDate(date);
        }

        mAdapterExchange = new ListWalletManagerAdapter(getContext(), arrayListItem, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.silde_out_right, R.animator.slide_in_right);
                ft.addToBackStack(null);
                MyImageView imgEdit = (MyImageView) v;
                AddNewFragment frag= new AddNewFragment();
                frag.setTitle("Edit Item");
                frag.setItem(arrayListItem.get(imgEdit.getIndexItem()));
                ft.replace(R.id.layout_fragment, frag);
                ft.commit();
                Toast.makeText(getActivity(),"edit", Toast.LENGTH_SHORT).show();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"delete", Toast.LENGTH_SHORT).show();
                MyImageView imgDelete = (MyImageView) v;
                int indexDelete = arrayListItem.get(imgDelete.getIndexItem()).getIdItem();
                mWalletDatabase.deleteItem(indexDelete);
                arrayListItem.remove(imgDelete.getIndexItem());
                mAdapterExchange.notifyDataSetChanged();
            }
        });
        mListViewExchange.setAdapter(mAdapterExchange);
    }
}
