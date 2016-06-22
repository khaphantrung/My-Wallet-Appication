package com.example.khaphan.mywallet;

import android.util.Log;

/**
 * Created by kha.phan on 6/22/2016.
 */
public class StringFormat {

    public static String toFormatThousand(String price)
    {

        int cof=0;
        int numThoudsand=0;
        String str1,str2="",strPriceThousand="";
        for (int i= price.length()-1; i>=0; i--){
            cof++;
            if(cof==3){
                numThoudsand++;
                str1=price.substring(price.length()-numThoudsand*3,price.length()- (numThoudsand*3-3));
                Log.d("+@@7_str1", "toFormatThousand: " +str1);
                strPriceThousand = ","+str1 + strPriceThousand;
                Log.d("+@@7_pricethousand", "toFormatThousand: " +strPriceThousand);
                cof = 0;
            }
        }
        Log.d("+@@7_", "toFormatThousand: cof" +cof);
        if (cof!=0){
            if (numThoudsand!=0){
                Log.d("+@@7_price", "toFormatThousand: price "+price);
                str2 = price.substring(0,price.length()-notFormatThousand(strPriceThousand).length());
                strPriceThousand = str2+strPriceThousand;
                Log.d("+@@7_", "toFormatThousand: spilt  " +str2 );
            }
            else {
                strPriceThousand = price;
                strPriceThousand = strPriceThousand.split("-")[0];
            }
        }
        else {

            strPriceThousand = strPriceThousand.substring(1);
            strPriceThousand = strPriceThousand.split("-")[0];
        }
        Log.d("+@@7_pricethousand", "toFormatThousand: final + " +strPriceThousand);
        return strPriceThousand;
    }
    public static String notFormatThousand(String price){
        String spl[] = price.split(",");
        String notFormat = "";
        for (int i = 0; i < spl.length; i++){
            notFormat = notFormat + spl[i];
        }
        Log.d("+@@7_", "toFormatThousand: notformat  " +notFormat);
        return notFormat;
    }
    public static String dateToMonth(String date){
        String month="";
        month= date.split("-")[1];
        return month;
    }
    public static String dateToYear(String date){
        String month="";
        month= date.split("-")[0];
        return month;
    }
}
