package com.hiaryabeer.transferapp.Models;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

import com.hiaryabeer.transferapp.R;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.appSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GeneralMethod {
    public  Context myContext;
    public RoomAllData my_dataBase;
    public appSettings settings;
    List<appSettings> appSettingsList=new ArrayList<>();
    public GeneralMethod(Context context) {
        this.myContext=context;
        my_dataBase= RoomAllData.getInstanceDataBase(myContext);
    }

    public  static  void showSweetDialog(Context context, int type, String title, String content){
        switch ( type){
            case 0://Error Type
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .show();
                break;
            case 1://Succes Type
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .show();
                break;
            case 3://warning Type
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(title)
                        .setContentText(content)
                        .show();
                break;

        }
    }

    public String getCurentTimeDate(int flag){
        String dateCurent="",timeCurrent,dateTime="";
        Date currentTimeAndDate;
        SimpleDateFormat dateFormat, timeformat;
        currentTimeAndDate = Calendar.getInstance().getTime();
        if(flag==1)// return date
        {

            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateCurent = dateFormat.format(currentTimeAndDate);
           dateTime=convertToEnglish(dateCurent);

        }
        else {
            if(flag==2)// return time
            {
                timeformat = new SimpleDateFormat("hh:mm");
                dateCurent = timeformat.format(currentTimeAndDate);
                dateTime=convertToEnglish(dateCurent);
            }
        }
        return dateTime;

    }
    public static String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }
    public boolean validateNotEmpty(EditText editText) {
        if(!editText.getText().toString().trim().equals(""))
        {
            editText.setError(null);
            return true;
        }
        else {
            editText.setError(myContext.getResources().getString(R.string.reqired_filled));
            editText.requestFocus();
            return false;
        }

    }
    public boolean validateNotZero(EditText editText) {
        if(!editText.getText().toString().trim().equals("0") &&Integer.parseInt(editText.getText().toString().trim())!=0)
        {
            editText.setError(null);
            return true;
        }
        else {
            editText.setError(myContext.getResources().getString(R.string.invaledZero));
            editText.requestFocus();
            return false;
        }

    }


    public void openSettingDialog() {
        final Dialog dialog = new Dialog(myContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip= dialog.findViewById(R.id.ipEditText);
        final EditText conNO= dialog.findViewById(R.id.cono);
//        conNO.setEnabled(false);
        final EditText years=dialog.findViewById(R.id.storeNo_edit);
        years.setEnabled(false);
        final CheckBox qtyUP=(CheckBox)dialog.findViewById(R.id.qtycheck);
        final EditText usernum= dialog.findViewById(R.id.usernumber);
        usernum.setEnabled(false);
       // usernum.setText(SET_userNO);
        appSettingsList=new ArrayList<>();

        try {
            appSettingsList=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
        if(appSettingsList.size()!=0) {

            ip.setText(appSettingsList.get(0).getIP());
            conNO.setText(appSettingsList.get(0).getCompanyNum());
            years.setText(appSettingsList.get(0).getYears());
            if (appSettingsList.get(0).getUpdateQTY().equals("1"))
                qtyUP.setChecked(true);
        }
        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP=ip.getText().toString().trim();
                final String SET_conNO=conNO.getText().toString().trim();
                final String SET_years=years.getText().toString().trim();
                usernum.setText("6");

//                if(qtyUP.isChecked())
//                    SET_qtyup="1";
//                else
//                    SET_qtyup="0";

                settings = new appSettings();
                settings.setIP(SET_IP);
                settings.setCompanyNum(SET_conNO);
                settings.setUpdateQTY(SET_years);
                settings.setYears("2021");
                settings.setUserNumber("6");
//                saveData(settings);
                my_dataBase.settingDao().insert(settings);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    private void deletesettings(){
        if(appSettingsList.size()!=0)
            my_dataBase.settingDao().deleteALL();
    }

}
