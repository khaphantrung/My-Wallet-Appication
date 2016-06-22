package com.example.khaphan.mywallet;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khaphan.mywallet.database.WalletDatabase;
import com.example.khaphan.mywallet.object.Item;
import com.example.khaphan.mywallet.object.MyImageView;
import com.samsistemas.calendarview.widget.CalendarView;
import com.samsistemas.calendarview.widget.DayView;

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
    private Button mBtnAddNew, mBtnDetailReport;
    private WalletDatabase mWalletDatabase;
    private ListView mListViewExchange;
    private ListWalletManagerAdapter mAdapterExchange;
    private ArrayList<Item> arrayListItem = new ArrayList<Item>();

    private View mItemListview;
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
                AddNewFragment frag= new AddNewFragment();
                frag.setTitle("Add New");
                ft.replace(R.id.layout_fragment, frag);
                ft.commit();
            }
        });
        mBtnDetailReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                ReportDialog customDialog = new ReportDialog(getActivity(),date);
                customDialog.show();
            }
        });
        mListViewExchange.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

                view.findViewById(R.id.layout_info).animate().translationX(0 - view.findViewById(R.id.layout_mod).getWidth());
                view.findViewById(R.id.layout_mod).animate().translationX(view.getWidth() - view.findViewById(R.id.layout_info).getWidth());

                mItemListview = view;
                mTimerHandler = new Handler();
                mTimerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.findViewById(R.id.layout_info).animate().translationX(0);
                            view.findViewById(R.id.layout_mod).animate().translationX(view.getWidth()+view.findViewById(R.id.layout_info).getWidth());
                        }
                        mItemListview = null;
                        mTimerHandler = null;
                    }
                }, VISIBILITY_TIMEOUT);
                return false;
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
//                for(int i=0; i<arrayListItem.size(); i++){
//                    arrayListItem.remove(i);
//                }
                arrayListItem = new ArrayList<Item>();
                ArrayList<Item> arrayItemNew = mWalletDatabase.getAllItemByDate( df.format(date).toString());
                arrayListItem = arrayItemNew;
//                for(int i=0; i<arrayItemNew.size(); i++){
//                    Log.d("@@@1+", "array item new: " +arrayItemNew.get(i).getNameItem());
//                    arrayListItem.add(arrayItemNew.get(i));
//                }
                mAdapterExchange.updateArraylist(arrayListItem);
                Toast.makeText(getActivity(), df.format(date).toString(), Toast.LENGTH_SHORT).show();
            }
        });
//        final DayView dayView = mCalendarView.findViewByDate(new Date(System.currentTimeMillis()));
//        if (null != dayView)
//            Toast.makeText(getActivity(), "Today is: " + dayView.getText().toString() + "/" + mCalendarView.getCurrentMonth() + "/" + mCalendarView.getCurrentYear(), Toast.LENGTH_SHORT).show();

    }
    private void setupListview(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Log.d("+@@+2ngay", "setupListview: " + date);
        if (mWalletDatabase!=null) {
            arrayListItem = mWalletDatabase.getAllItemByDate(date);
            Log.d("notnul+", "database " );
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
                arrayListItem.remove(indexDelete);
                mAdapterExchange.notifyDataSetChanged();
            }
        });
        mListViewExchange.setAdapter(mAdapterExchange);
    }
}
