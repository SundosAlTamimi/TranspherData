package com.hiaryabeer.transferapp.Models;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.hiaryabeer.transferapp.MainActivity;
import com.hiaryabeer.transferapp.RoomAllData;
import com.hiaryabeer.transferapp.Store;
import com.hiaryabeer.transferapp.ZoneModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.Login.getListCom;
import static com.hiaryabeer.transferapp.MainActivity.itemcode;
import static com.hiaryabeer.transferapp.MainActivity.itemrespons;
import static com.hiaryabeer.transferapp.MainActivity.zone;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;


public class ImportData {
    public static ArrayList<ZoneModel> listAllZone = new ArrayList<>();
    public static int posize;
    public static String itemn;
    public static String item_name="";
    public static String poqty;
    private Context context;
    public String ipAddress = "", CONO = "", headerDll = "", link = "";
    public RoomAllData my_dataBase;
    public static String zonetype;
    public static List<Store> Storelist = new ArrayList<>();
    public static ArrayList<String> BoxNolist = new ArrayList<>();
    public static ArrayList<String> PoNolist = new ArrayList<>();
    public static List<AllItems> AllImportItemlist = new ArrayList<>();
    public static List<ZoneModel>  listQtyZone = new ArrayList<>();
    public static ArrayList<CompanyInfo> companyInList = new ArrayList<>();
    public static String  barcode="";
    public static   SweetAlertDialog pdRepla,pdRepla2;
    public JSONArray jsonArrayPo;
    public JSONObject stringNoObject;

    public ImportData(Context context) {
        this.context = context;
        my_dataBase = RoomAllData.getInstanceDataBase(context);
        try {
            getIpAddress();
        } catch (Exception e) {
            Toast.makeText(context, "Fill Ip and Company No", Toast.LENGTH_SHORT).show();
        }

        headerDll="/Falcons/VAN.Dll/";
    }
    public void getAllItems(){
        pdRepla = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla.setTitleText(" Start Import DATA");
        pdRepla.setCancelable(false);
        pdRepla.show();
        if(!ipAddress.equals(""))
            new  JSONTask_getAllItems().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    public  void getAllZones(){
        if(!ipAddress.equals(""))
        {
            new JSONTask_getAllZoneCode().execute();
        }
        else {
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }

    }
    private void getIpAddress() {
        headerDll="";
        ipAddress=my_dataBase.settingDao().getIpAddress().trim();
        CONO=my_dataBase.settingDao().getCono().trim();
        Log.e("getIpAddress",""+ipAddress);


    }
    public void getCompanyInfo() {
        if(!ipAddress.equals(""))
        {
            try {
                new JSONTask_getCompanyInfo().execute();
            }
            catch (Exception e)
            {}

        }
        else {

            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
        }
    }
    public void getQty() {
        listQtyZone.clear();
        new  JSONTask_getQTYOFZone().execute();

    }
    public void getStore() {
        pdRepla2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdRepla2.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdRepla2.setTitleText(" Start get store");
        pdRepla2.setCancelable(false);
        pdRepla2.show();
        if(!ipAddress.equals(""))
            new JSONTask_getAllStoreData().execute();
        else
            Toast.makeText(context, "Fill Ip", Toast.LENGTH_SHORT).show();
    }
    private class JSONTask_getQTYOFZone extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/GetZoneDatInfo?CONO=" + CONO.trim()+"&ZONENO="+zone.getText().toString().trim()+"&ITEMCODE="+itemcode.getText().toString().trim();


                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                // JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);
            String d="";
            JSONObject jsonObject1 = null;

            if (array != null) {
                if (array.contains("QTY")) {

                    if (array.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);

                            for (int i = 0; i < requestArray.length(); i++) {

                                ZoneModel zoneModel = new ZoneModel();
                                jsonObject1 = requestArray.getJSONObject(i);
                                zoneModel.setZoneCode(jsonObject1.getString("ZONENO"));
                                zoneModel.setItemCode(jsonObject1.getString("ITEMCODE"));
                                zoneModel.setQty(jsonObject1.getString("QTY"));
                                d=jsonObject1.getString("QTY");
                                listQtyZone.add(zoneModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    MainActivity.qty.setText(d);
                    MainActivity.qtyrespons.setText("QTY");



                }
                else {

                    MainActivity.qtyrespons.setText("nodata");



                }

            }
            else {

                MainActivity.qtyrespons.setText("nodata");

            }
        }

    }
    public class JSONTask_getCompanyInfo extends AsyncTask<String, String, String> {

        private String itemNo = "", JsonResponse;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetCoYear

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetCoYear";
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", "Company" + finalJson);



                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null ) {
                // {
                //    "CoNo": "200",
                //    "CoYear": "2021",
                //    "CoNameA": "Al Rayyan Plastic Factory 2017"
                //  },

                if (result.contains("CoNo")) {
                    try {
                        CompanyInfo requestDetail = new CompanyInfo();
                        JSONArray requestArray = null;
                        requestArray = new JSONArray(result);
                        companyInList = new ArrayList<>();


                        for (int i = 0; i < requestArray.length(); i++) {
                            JSONObject infoDetail = requestArray.getJSONObject(i);
                            requestDetail = new CompanyInfo();
                            requestDetail.setCoNo(infoDetail.get("CoNo").toString());
                            requestDetail.setCoYear(infoDetail.get("CoYear").toString());
                            requestDetail.setCoNameA(infoDetail.get("CoNameA").toString());

                            companyInList.add(requestDetail);
                        }
                        if (companyInList.size() != 0) {
                            getListCom.setText("fill");
                        }


//                            itemKintText.setText(requestDetail.getZONETYPE());


                    } catch (JSONException e) {
//                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }


            }

            else {

                 //   itemKintText1.setText("NOTEXIST");
            }



        }
    }
    private class JSONTask_getAllStoreData extends AsyncTask<String, String, String> {


        Store store;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    http:
//http://10.0.0.22:8082/Getsore?CONO=304

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/Getsore?CONO="+CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("getAllSto", e.getMessage());
                pdRepla2.dismiss();
            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();


                //JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        pdRepla2.dismiss();
                        Toast.makeText(context, "Ip Connection Failed ", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
                pdRepla2.dismiss();
                        Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        try {
                            showSweetDialog(context, 0, "check connection", "");
                        } catch (WindowManager.BadTokenException e) {
                            //use a log message
                        }
                    }
                });
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String array) {
            super.onPostExecute(array);

            JSONObject jsonObject1 = null;
            if (array != null) {
                if (array.contains("STORENO")) {

                    if (array.length() != 0) {
                        try {
                            JSONArray requestArray = null;
                            requestArray = new JSONArray(array);
                            Storelist.clear();

                            for (int i = 0; i < requestArray.length(); i++) {
                                store = new Store();
                                jsonObject1 = requestArray.getJSONObject(i);
                                store.setSTORENO(jsonObject1.getString("STORENO"));
                                store.setSTORENAME(jsonObject1.getString("STORENAME"));

                                Storelist.add(store);
                            }
                               MainActivity.respon.setText("fill");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }



                }
            } else {

                MainActivity.respon.setText("nodata");


            }
        }



    }
    private class JSONTask_getAllZoneCode extends AsyncTask<String, String, JSONArray> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";

        }

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {
                    //http://localhost:8082/IrGetAllZone?CONO=290

                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllZone?CONO=" + CONO.trim();
                    Log.e("link", "" + link);
                }
            } catch (Exception e) {

            }

            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


                JSONArray parentObject = new JSONArray(finalJson);

                return parentObject;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(JSONArray array) {
            super.onPostExecute(array);

            JSONObject result = null;


            if (array != null ) {
                if (array.length() != 0) {


                    for (int i = 0; i < array.length(); i++) {
                        try {
                            result = array.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ZoneModel itemZone = new ZoneModel();
                        try {
                            itemZone.setZoneCode(result.getString("ZONENO"));
                            itemZone.setZONENAME(result.getString("ZONENAME"));
                            itemZone.setZONETYPE(result.getString("ZONETYPE"));

                            listAllZone.add(itemZone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }
            }
        }
    }

    private class  JSONTask_getAllItems extends AsyncTask<String, String, String> {

        private String custId = "", JsonResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String do_ = "my";
            Log.e("onPreExecute", "onPreExecute");
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                if (!ipAddress.equals("")) {


                    link = "http://" + ipAddress.trim() + headerDll.trim() + "/IrGetAllItems?CONO=" + CONO.trim();

                    Log.e("link", "" + link);
                }
            } catch (Exception e) {
                Log.e("Exception",""+e.getMessage());
                pdRepla.dismissWithAnimation();
            }


            try {

                //*************************************

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));

//

                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                Log.e("finalJson***Import", sb.toString());

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                // JsonResponse = sb.toString();

                String finalJson = sb.toString();
                Log.e("finalJson***Import", finalJson);


//                JSONArray parentObject = new JSONArray(finalJson);

                return finalJson;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "" + e.getMessage());
//                progressDialog.dismiss();
                return null;
            }


            //***************************

        }

        @Override
        protected void onPostExecute(String respon) {
            super.onPostExecute(respon);
            String d="";
            JSONObject jsonObject1 = null;

            if (respon != null) {
                if (respon.contains("ItemOCode")) {

                    if (respon.length() != 0) {
                        try {

                            JSONArray requestArray = null;
                            requestArray = new JSONArray(respon);

                            for (int i = 0; i < requestArray.length(); i++) {

                                AllItems allItems= new  AllItems ();
                                jsonObject1 = requestArray.getJSONObject(i);
                                allItems.setItemOcode(jsonObject1.getString("ItemOCode"));
                                allItems.setItemName(jsonObject1.getString("ItemNameA"));
                                AllImportItemlist.add(allItems);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    itemrespons.setText("ItemOCode");

                    Log.e("itemrespons",itemrespons.getText().toString()+d);

                    Log.e("itemrespons",itemrespons.getText().toString()+d);
                }
                else {

                    itemrespons.setText("nodata");


                }

            }
            else {
                itemrespons.setText("nodata");
            }
        }


    }


}
