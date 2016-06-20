package com.example.khaphan.mywallet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.samsistemas.calendarview.widget.CalendarView;
import com.samsistemas.calendarview.widget.DayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kha.phan on 6/17/2016.
 */
public class WalletManagerFragment extends Fragment {

    private CalendarView mCalendarView;
    private Button mBtnAddNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_wallet_manager, parent, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWiget(view);
        addEvent();
        setupCalendar();
    }

    private void getWiget(View view) {
        mCalendarView = (CalendarView) view.findViewById(R.id.calendar_view);
        mBtnAddNew = (Button) view.findViewById(R.id.btn_add_new);

    }

    private void addEvent() {
        mBtnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.animator.slide_in_left,R.animator.silde_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.layout_fragment, new AddNewFragment());
                ft.commit();
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
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                Toast.makeText(getActivity(), df.format(date).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final DayView dayView = mCalendarView.findViewByDate(new Date(System.currentTimeMillis()));
        if (null != dayView)
            Toast.makeText(getActivity(), "Today is: " + dayView.getText().toString() + "/" + mCalendarView.getCurrentMonth() + "/" + mCalendarView.getCurrentYear(), Toast.LENGTH_SHORT).show();

    }
}
