package com.example.khaphan.mywallet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kha.phan on 6/16/2016.
 */
public class PasscodeLock extends AppCompatActivity {
    private TextView mTextTitle, mTextInput;
    private GridView mGridViewInputPass;
    private GridPassAdapter mAdapterGridPass;
    private RadioButton mPasscode1,mPasscode2,mPasscode3,mPasscode4,mPasscode5;
    private static String[] sStrPass = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", ""};
    private DataSharedPreferences mData;
    private int mNumInput=0;
    private String mNewPasscode;
    private String mPassInput="";
    private String mPasscode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcodelock);
        getWidgets();
        mTextTitle.setText("Passcode Lock");
        mData = new DataSharedPreferences(this);
        mPasscode = mData.getPreferencesString("passcode");
        if(mPasscode.length()<5) {
            mTextInput.setText("Enter New Passcode");
        }
    }
    private void getWidgets(){
        mTextTitle = (TextView) findViewById(R.id.toolbar_title);
        mTextInput = (TextView) findViewById(R.id.text_input);
        mPasscode1 = (RadioButton) findViewById(R.id.radioButton1);
        mPasscode2 = (RadioButton) findViewById(R.id.radioButton2);
        mPasscode3 = (RadioButton) findViewById(R.id.radioButton3);
        mPasscode4= (RadioButton) findViewById(R.id.radioButton4);
        mPasscode5 = (RadioButton) findViewById(R.id.radioButton5);

        mGridViewInputPass = (GridView) findViewById(R.id.grid_pass);
        mAdapterGridPass = new GridPassAdapter(this, sStrPass, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login(((Button) v).getText().toString());
                //  Toast.makeText(getApplicationContext(),inputPass.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        mGridViewInputPass.setAdapter(mAdapterGridPass);
    }
    private void login(String passCodeInput){
        mPassInput = mPassInput + passCodeInput;
        mNumInput++;
        switch (mNumInput) {
            case 1:
                mPasscode1.setChecked(true);
                break;
            case 2:
                mPasscode2.setChecked(true);
                break;
            case 3:
                mPasscode3.setChecked(true);
                break;
            case 4:
                mPasscode4.setChecked(true);
                break;
            case 5:
                mPasscode5.setChecked(true);
                break;
        }
        if(mNumInput == 5) {
            if(mTextInput.getText().equals("Enter Your Passcode")) {
                if (checkPass(mPassInput)) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    wrongPassDialog();
                }
            } else if(mTextInput.getText().equals("Enter New Passcode")) {
                mNewPasscode = mPassInput;
                reInput();
                mTextInput.setText("Re Input Passcode");
            } else if(mTextInput.getText().equals("Re Input Passcode")){
                if(mNewPasscode.equals(mPassInput)) {
                    mData.setPreferencesString("passcode", mPassInput);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    wrongPassDialog();
                }
            }
        }
    }
    private void reInput(){
        mNumInput = 0;
        mPassInput = "";
        mPasscode1.setChecked(false);
        mPasscode2.setChecked(false);
        mPasscode3.setChecked(false);
        mPasscode4.setChecked(false);
        mPasscode5.setChecked(false);
    }
    private void wrongPassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(R.layout.dialog_wrongpass);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reInput();
            }
        });
        builder.create().show();
    }
    private boolean checkPass(String passInput){
        if(passInput.equals(mPasscode)) return true;
        return false;
    }
}
