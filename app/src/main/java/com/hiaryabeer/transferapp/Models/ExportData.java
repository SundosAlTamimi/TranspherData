package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hiaryabeer.transferapp.ReplacementModel;
import com.hiaryabeer.transferapp.RoomAllData;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.MainActivity.exportAllState;
import static com.hiaryabeer.transferapp.MainActivity.poststateRE;
import static com.hiaryabeer.transferapp.MainActivity.saveflage;

public class ExportData {
    private Context context;
    public  String ipAddress="",CONO="",headerDll="",link="",PONO="";
    public RoomAllData my_dataBase;
    public  static  SweetAlertDialog pdVoucher,pdshipmant,pdRepla,pdstock,pdzone;
    JSONObject vouchersObject;
    JSONObject ShipmentObject;
    JSONObject ReplacmentObject,StockObject,ZoneRepObject;
    private JSONArray jsonArrayShipment;
    private JSONArray jsonArrayReplacement,jsonArrayStock,jsonArrayZoneRep;
    private JSONArray jsonArrayVouchers;

    public  ArrayList<ReplacementModel> listAllReplacment =new ArrayList<>();
    int typeExportZone=0;
    int typeExportShipment=0;
    int typeExportReplacement=0;
    public ExportData(Context context) {
        this.context = context;
        my_dataBase= RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        }catch (Exception e){
            Toast.makeText(context, "Fill Ip and Company No", Toast.LENGTH_SHORT).show();
        }
 //   headerDll="/Falcons/VAN.Dll/";
 headerDll="";

    }
    private void getIpAddress() {
//
        ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        CONO=my_dataBase.settingDao().getCono().trim();
        Log.e("getIpAddress",""+ipAddress);


    }
    public void exportReplacementList(List<ReplacementModel>replacementlist) {
        getReplacmentObject(replacementlist);
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla.setTitleText(" Start export Replenishments");
        pdRepla.setCancelable(false);
        pdRepla.show();

        new JSONTask_AddReplacment(replacementlist).execute();
    }
    private void  getReplacmentObject(List<ReplacementModel>replacementlist) {
        jsonArrayReplacement = new JSONArray();
        for (int i = 0; i < replacementlist.size(); i++)
        {

            jsonArrayReplacement.put(replacementlist.get(i).getJSONObjectDelphi());

        }
        try {
            ReplacmentObject=new JSONObject();
            ReplacmentObject.put("JSN",jsonArrayReplacement);
            Log.e("vouchersObject",""+ReplacmentObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class  JSONTask_AddReplacment extends AsyncTask<String, String, String> {
        private String JsonResponse = null;

        List<ReplacementModel> replacementList = new ArrayList<>();

        public JSONTask_AddReplacment(List<ReplacementModel> replacementList) {
            this.replacementList = replacementList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {


            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {

                    http:
//localhost:8082/IrTransFer?CONO=290&JSONSTR={"JSN":[{"ITEMCODE":"4032900116167","FROMSTR":"1","TOSTR":"2","QTY":"10","ZONE":"50"},{"ITEMCODE":"7614900001130","FROMSTR":"1","TOSTR":"2","QTY":"30","ZONE":"51"}]}
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrTransFer";


                    Log.e("URL_TO_HIT", "" + link);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();
                pdRepla.dismissWithAnimation();

            }

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", ReplacmentObject.toString().trim()));
                Log.e("JSONSTR", ReplacmentObject.toString());
                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                HttpResponse response = client.execute(request);

                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("JsonResponse", "Expor Replacement" + JsonResponse);


            } catch (Exception e) {
            }
            return JsonResponse;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("JSONTaskAddReplacment", "" + result);
            pdRepla.dismissWithAnimation();

            if (result != null && !result.equals("")) {
                if (result.contains("Saved Successfully")) {
                    //  poststateRE.setText("exported");


                      exportAllState.setText("exported");



                }

                else

                {


                   exportAllState.setText("not");

                }





            } else {

               exportAllState.setText("not");


            }


        }

    }

}
