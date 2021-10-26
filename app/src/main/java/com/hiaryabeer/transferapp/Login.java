package com.hiaryabeer.transferapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;

import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.hiaryabeer.transferapp.Models.ImportData;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
TextView login,settings;
    public appSettings setting;
    List<appSettings> appSettings;
    public String COMPANYNO;
    public  String SET_qtyup;
    GeneralMethod generalMethod;
    public RoomAllData my_dataBase;
    public  static   TextView getListCom,selectedCompany;
    private String selectedCom;
    private String cono,coYear;
     ListView listCompany;

    public static EditText itemKintText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        my_dataBase = RoomAllData.getInstanceDataBase(Login.this);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch (id){
                case R.id.login:
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    openSettingDialog();
                    break;

            }
        }
    };
    public void showCompanyDialog(View view) {
        if(view.getId()==R.id.companyName)
        {
            if(existCoNo(2))
                showCompany(Login.this);
            else {
                openSettingDialog();
                //generalMethod.openSettingDialog();
            }
        }
    }
    private void showCompany(final Context context1) {

        final Dialog dialog = new Dialog(context1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.zone_search);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
       appSettings settings;
       ImportData importData=new ImportData(Login.this);
        ArrayList<String> nameOfEngi=new ArrayList<>();
   listCompany = dialog.findViewById(R.id.listViewEngineering);
        importData.getCompanyInfo();

        getListCom=dialog.findViewById(R.id.getListCom);

        getListCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()!=0)
                {
                    if(editable.toString().equals("fill"))
                    {
                        if( importData.companyInList.size()!=0) {
                            for (int i = 0; i < importData.companyInList.size(); i++) {
                                nameOfEngi.add(importData.companyInList.get(i).getCoNo() + "\t\t" + importData.companyInList.get(i).getCoYear() + "\t\t" + importData.companyInList.get(i).getCoNameA());
                            }

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (Login.this, android.R.layout.simple_list_item_1, nameOfEngi) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    /// Get the Item from ListView
                                    View view = super.getView(position, convertView, parent);

                                    TextView tv = (TextView) view.findViewById(android.R.id.text1);

                                    // Set the text size 25 dip for ListView each item
                                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                                    tv.setTextColor(getResources().getColor(R.color.text_color));
                                    // Return the view
                                    return view;
                                }
                            };

                            // DataBind ListView with items from ArrayAdapter
                            listCompany.setAdapter(arrayAdapter);


                        }
                    }

                }

            }
        });


        final int[] rowZone = new int[1];
        selectedCom="";

        listCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cono="";coYear="";
                rowZone[0] =position;
                listCompany.requestFocusFromTouch();
                listCompany.setSelection(position);
                selectedCom =importData.companyInList.get(position).getCoNameA();
                cono=importData.companyInList.get(position).getCoNo();
                coYear=importData.companyInList.get(position).getCoYear();

            }
        });


        Button btn_send = dialog.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCompany.setText(selectedCom);

                updateCono(cono,coYear);

                dialog.dismiss();


            }
        });
        dialog.show();

    }
    private boolean existCoNo(int flag ) {
        // ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        String  CONO="";
        try {
            if(flag==1)
            {
                CONO=my_dataBase.settingDao().getCono();
            }else {
                if(flag==2)
                    CONO=my_dataBase.settingDao().getIpAddress();
            }

            Log.e("CONO",""+CONO);
            if(CONO!=null)
            {
                if(CONO.length()!=0)
                {
                    return true;
                }

            }
        }catch (Exception e){
            return  false;
        }


        return  false;


    }
    private void updateCono(String cono, String coYear) {
        my_dataBase.settingDao().updateCompanyInfo(cono);
        my_dataBase.settingDao().updateCompanyYear(coYear);

        //Log.e("updateCono",""+up);
    }

    public void init(){
        selectedCompany=findViewById(R.id.selectedCompany);
      //  itemKintText1 = findViewById(R.id.itemKintTextRE);
     login=findViewById(R.id.login) ;
     login.setOnClickListener(onClickListener );
       settings=findViewById(R.id.setting) ;
       settings.setOnClickListener(onClickListener );

    }
    private void openSettingDialog() {
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ip_setting_dialog);
        dialog.show();


        final EditText ip= dialog.findViewById(R.id.ipEditText);
        final TextView editip= dialog.findViewById(R.id.editip);
        // ip.setEnabled(false);
        final EditText conNO= dialog.findViewById(R.id.cono);
        final EditText years=dialog.findViewById(R.id.storeNo_edit);
        final CheckBox qtyUP=(CheckBox)dialog.findViewById(R.id.qtycheck);
        final EditText usernum= dialog.findViewById(R.id.usernumber);
        final EditText deviceId= dialog.findViewById(R.id.deviceId);




        // conNO.setEnabled(false);
        //  years.setEnabled(false);
        //  usernum.setEnabled(false);
        editip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!ip.getText().toString().equals("")) {
                    final Dialog dialog1 = new Dialog(Login.this);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setCancelable(false);
                    dialog1.setContentView(R.layout.passworddailog);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog1.getWindow().getAttributes());
                    ip.setEnabled(true);
                    lp.gravity = Gravity.CENTER;
                    dialog1.getWindow().setAttributes(lp);


                    EditText editText=dialog1.findViewById(R.id.passwordd);
                    Button donebutton=dialog1.findViewById(R.id.done);
                    Button cancelbutton=dialog1.findViewById(R.id.cancel);
                    donebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(editText.getText().toString().trim().equals("304555"))
                            {
                                ip.setEnabled(true);
                                dialog1.dismiss();
                            }
                        }
                    });
                    cancelbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                         //   ip.setEnabled(false);
                            dialog1.dismiss();
                        }
                    });





                    dialog1.show();
                }else {
                    Log.e("dd","dd");
                    ip.setEnabled(true);
                    ip.requestFocus();
                }}

        });
        getDataZone();
        if(appSettings.size()!=0) {

            ip.setText(appSettings.get(0).getIP());
            conNO.setText(appSettings.get(0).getCompanyNum());
            COMPANYNO=appSettings.get(0).getCompanyNum();
            usernum.setText(appSettings.get(0).getUserNumber());
            years.setText(appSettings.get(0).getYears());
            try {
                deviceId.setText(appSettings.get(0).getDeviceId());
            }catch ( Exception e){
                Log.e("deviceId",""+e.getMessage());
            }

            if (appSettings.get(0).getUpdateQTY().equals("1"))
                qtyUP.setChecked(true);
        }
else{
          //  if(ip.getText().toString().equals(""))
                ip.setEnabled(true);
                // usernum.setText(SET_userNO);

         //   else ip.setEnabled(false);
        }

        //****************************
        dialog.findViewById(R.id.saveSetting).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                deletesettings();
                final String SET_IP = ip.getText().toString();
                final String SET_conNO = conNO.getText().toString();
                COMPANYNO = conNO.getText().toString();
                final String SET_years = years.getText().toString();
                String device_Id = deviceId.getText().toString().trim();
                //usernum.setText(SET_userNO);

                if (qtyUP.isChecked())
                    SET_qtyup = "1";
                else
                    SET_qtyup = "0";

                setting = new appSettings();
                setting.setIP(SET_IP);
                setting.setCompanyNum(SET_conNO);
                setting.setUpdateQTY(SET_qtyup);
                setting.setYears(SET_years);
                setting.setUserNumber(usernum.getText().toString().trim());
                setting.setDeviceId(deviceId.getText().toString().trim());
                Log.e("setting", "==" + setting.getDeviceId());

                if (deviceId.getText().toString().trim().length() != 0) {
                    if (ip.getText().toString().trim().length() != 0) {
                        if (conNO.getText().toString().trim().length() != 0) {
                            if (years.getText().toString().trim().length() != 0) {


                                saveData(setting);
                                dialog.dismiss();
                                my_dataBase.itemDao().dELETEAll();

                            } else {
                                years.setError("Empty");
                            }
                        } else {
                            conNO.setError("Empty");
                        }

                    } else {
                        ip.setError("Empty");
                    }

                } else
                    deviceId.setError("Empty");
            }
        });
        dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    private void getDataZone() {
        appSettings=new ArrayList();
        try {
            appSettings=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
    }
    private void deletesettings(){
        if(appSettings.size()!=0)
            my_dataBase.settingDao().deleteALL();
    }
    private void saveData(appSettings settings) {
        my_dataBase.settingDao().deleteALL();
        my_dataBase.storeDao().deleteall();
        my_dataBase.settingDao().insert(settings);

        generalMethod.showSweetDialog(this,1,this.getResources().getString(R.string.savedSuccsesfule),"");

    }
}