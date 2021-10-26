package com.hiaryabeer.transferapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hiaryabeer.transferapp.Adapters.Adapterr;
import com.hiaryabeer.transferapp.Adapters.ReplacementAdapter;
import com.hiaryabeer.transferapp.Interfaces.ReplacementDao;
import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.Models.ExportData;
import com.hiaryabeer.transferapp.Models.GeneralMethod;
import com.hiaryabeer.transferapp.Models.ImportData;
import com.hiaryabeer.transferapp.Models.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.hiaryabeer.transferapp.Models.ExportData.pdRepla;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.convertToEnglish;
import static com.hiaryabeer.transferapp.Models.GeneralMethod.showSweetDialog;
import static com.hiaryabeer.transferapp.Models.ImportData.AllImportItemlist;
import static com.hiaryabeer.transferapp.Models.ImportData.Storelist;
import static com.hiaryabeer.transferapp.Models.ImportData.listAllZone;
import static com.hiaryabeer.transferapp.Models.ImportData.listQtyZone;
import static com.hiaryabeer.transferapp.Models.ImportData.pdRepla2;

public class MainActivity extends AppCompatActivity {
    boolean saved = false;
    int position;
    public static  int actvityflage=1;
    public String UserNo;
    public static TextView respon,qtyrespons,exportAllState;
    GeneralMethod generalMethod;
    Spinner fromSpinner, toSpinner;
    ExportData exportData;
    ImportData importData;
    public static EditText  poststateRE, DZRE_ZONEcode;
    public static EditText zone, itemcode;
    public static TextView qty;
    public  String deviceId="";
    public static TextView DZRE_zonecodeshow, DZRE_qtyshow;
    public static  List<ReplacementModel> DB_replist=new ArrayList<>();;
    public static  List<ReplacementModel> DB_replistcopy=new ArrayList<>();
    public static  List<ReplacementModel> reducedqtyitemlist=new ArrayList<>();
    public static Dialog   Re_searchdialog;
    EditText recqty;
    TextView search;
    Button save;
    public int indexZone = -1;
    public int indexDBZone = 0,indexDBitem=0,indexOfReduceditem=0;
    public RoomAllData my_dataBase;
    String From, To, Zone, Itemcode, Qty;
    String FromNo, ToNo;
    ReplacementModel replacement;
    ReplacementModel replacementModel;
    static ReplacementAdapter adapter;
    public static boolean validItem=false,validateKind=false;
    RecyclerView replacmentRecycler;
    public static final int REQUEST_Camera_Barcode = 1;
    List<appSettings> appSettings;
    public static  Dialog dialog1;
    List<ReplacementModel>deleted_DBzone;
    private Dialog authenticationdialog;
    List<String> spinnerArray = new ArrayList<>();
    public static TextView itemrespons;
    public static  Spinner spinner,spinner2;
    public static ArrayList<ReplacementModel> replacementlist = new ArrayList<>();
    public List<ReplacementModel> UnPostedreplacementlist = new ArrayList<>();
    public List<ReplacementModel> Allreplacementlist1 = new ArrayList<>();
    public List<ReplacementModel> Allreplacementlist2 = new ArrayList<>();
    public static ArrayList<Store> Storelistt = new ArrayList<>();
    public static  Button DZRE_delete;
    public static TextView DIRE_close_btn,
            DIRE_zoneSearch2,
            DIRE_preQTY,
            DIRE_itemcodeshow,
            DIRE_zoneshow,
            DIRE_qtyshow ;
    List<String>DB_store;
    List<String>DB_zone;
    EditText UsNa;
    public static List<AllItems> AllItemDBlist = new ArrayList<>();
    public static EditText   DIRE_ZONEcode, DIRE_itemcode;
    public Button export;
    public  static ArrayList <AllItems>AllItemstest=new ArrayList<>();
    private int pos;
    String maxTrans="";
    private int max=0;
public static int saveflage;
    private int TransferNo;
    String maxVochNum;
    private String minVo;
    private String MaxVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AllItemDBlist.clear();

        init();
        maxVochNum= my_dataBase.replacementDao().getMaxReplacementNo();

        if(maxVochNum!=null) {
            Log.e(" maxVochNum", maxVochNum);
            max=Integer.parseInt(maxVochNum)+1;
        }
        else
            max=1;

        new KeyboardUtil(this,    replacmentRecycler );
        AllItemDBlist.addAll(my_dataBase.itemDao().getAll());
//        if(AllItemDBlist .size()==0)
//            importData.getAllItems();
//  my_dataBase.storeDao().deleteall();
        Storelist.clear();
        Storelist=  my_dataBase.storeDao().getall();

// my_dataBase.replacementDao().deleteALL();

        spinnerArray.clear();

        if(Storelist.size()>0) {
            Log.e("sss","sss");
            for (int i = 0; i < Storelist.size(); i++) {
                spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());

            }
            fillSp();
        }

        else
        if( Storelist.size()==0)
        {
            getStors();
            Log.e("sss4","sss4");
        }



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sss="";
                if((AllItemDBlist.size())>0)
                    opensearchDailog();
                else
                {
                    AllItemDBlist.addAll(my_dataBase.itemDao().getAll());
                    if((AllItemDBlist.size())>0)
                    {
                        opensearchDailog();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"No Data",Toast.LENGTH_LONG).show();
                        itemcode.setText("");
                    }
                }

            }
        });





        zone.setEnabled(true);
        zone.requestFocus();
        itemcode.setEnabled(true);
        recqty.setEnabled(false);
        save.setEnabled(false);

        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);
        UserNo=my_dataBase.settingDao().getUserNo();
//my_dataBase.replacementDao().deleteALL();
      /*  if(Storelistt.size()==0) {
            itemcode.setEnabled(false);
            zone.setEnabled(false);

        }*/
        //testcode
/*ReplacementModel replacementModel=new ReplacementModel();
        replacementModel.setRecQty("1");
        replacementModel.setFrom("1234");
        replacementModel.setTo("122222222222");
        replacementModel.setItemcode("tttttttttt");
        replacementModel.setQty("1");
        replacementModel.setZone("fffff");
        for (int i=0;i<50;i++)
      replacementlist.add(replacementModel);
        fillAdapter();*/


        findViewById(R.id.nextZone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zone.setText("");
                zone.setEnabled(true);
                zone.requestFocus();
                itemcode.setText("");
                if(replacementlist!=null)
                    if(replacementlist.size()>0)   replacementlist.clear();
                if(adapter!=null) adapter.notifyDataSetChanged();
            }
        });

        try {

            fromSpinner.setSelection(0);
            toSpinner.setSelection(1);
            toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code her

                    if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.samestore), "");
                        if (fromSpinner.getSelectedItemPosition() != 1) toSpinner.setSelection(1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // your code her

                    if (toSpinner.getSelectedItem().toString().equals(fromSpinner.getSelectedItem())) {
                        showSweetDialog(MainActivity.this, 3, getResources().getString(R.string.samestore), "");
                        if (toSpinner.getSelectedItemPosition() != 0) fromSpinner.setSelection(0);
                        //else
                        //   fromSP.setSelection(1);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        }catch (Exception e){}



        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("export","clicked");

               // UnPostedreplacementlist=my_dataBase.replacementDao().getallReplacement();



                exportAllData();
                maxVochNum= my_dataBase.replacementDao().getMaxReplacementNo();
                if(maxVochNum!=null)
                {

                    Log.e(" maxVochNum", maxVochNum);
                    max=Integer.parseInt(maxVochNum)+1;

                }
                zone.setEnabled(true);
                zone.requestFocus();

                zone.setText("");
                itemcode.setText("");

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromSpinner.setEnabled(true);
                toSpinner.setEnabled(true);







                exportAllData();
                maxVochNum= my_dataBase.replacementDao().getMaxReplacementNo();
                if(maxVochNum!=null)
                {  Log.e(" maxVochNum", maxVochNum);
                    max=Integer.parseInt(maxVochNum)+1;}
                zone.setEnabled(true);
                zone.requestFocus();

                zone.setText("");
                itemcode.setText("");
                //     qty.setText("");
            }
        });


        findViewById(R.id.Re_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getResources().getString(R.string.confirm_title))
                        .setContentText(getResources().getString(R.string.messageExit))
                        .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (replacementlist.size() > 0) {

                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(getResources().getString(R.string.confirm_title))
                                            .setContentText(getResources().getString(R.string.messageExit2))
                                            .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {


                                                    replacementlist.clear();
                                                    adapter.notifyDataSetChanged();
                                                    sweetAlertDialog.dismissWithAnimation();
                                                    finish();
                                                }
                                            })
                                            .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            }).show();
                                }
                                else
                                {
                                    sweetAlertDialog.dismiss();
                                    finish();
                                }
                            }
                        }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();


            }
        });


    }

    private void opensearchDailog() {
        AllItemstest.clear();
        dialog1 = new Dialog(MainActivity.this);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.searchdailog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = 500;
        lp.height = 700;
        lp.gravity = Gravity.CENTER;
        dialog1.show();
        Log.e("size",AllItemDBlist.size()+"");
        final ListView listView=    dialog1.findViewById(R.id.Rec);
        final EditText search=  dialog1.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(""))
                {   AllItemstest.clear();
                    for(int i=0;i< AllItemDBlist.size();i++)
                        if(AllItemDBlist.get(i).getItemName().toLowerCase().contains(editable.toString().toLowerCase()))
                            AllItemstest.add(AllItemDBlist.get(i));
                    Adapterr adapter1 = new Adapterr(MainActivity.this,  AllItemstest) ;
                    listView.setAdapter(adapter1);
                }
                else {
                    Adapterr adapter1 = new Adapterr(MainActivity.this,AllItemDBlist) ;
                    listView.setAdapter(adapter1);
                } }
        });



        Adapterr adapter1 = new Adapterr(this,AllItemDBlist) ;
        listView.setAdapter(adapter1);
    }


    private boolean isExistsInReducedlist(){
        boolean f=false;



        for(int x=0;x< reducedqtyitemlist.size();x++)
            if(reducedqtyitemlist.get(x).getZone().equals(DIRE_ZONEcode.getText().toString().trim())&&
                    reducedqtyitemlist.get(x).getTo().equals(spinner2.getSelectedItem().toString())
                    && reducedqtyitemlist.get(x).getItemcode().equals(DIRE_itemcodeshow.getText().toString().trim()))
            {  f=true;
                indexOfReduceditem =x;
            }
            else
            {    f=false;


                continue;}

        return f;
    }
    private boolean isExists(int flage,String zone,String store,String itemcode) {
        boolean f=false;
        if(flage==1)    {
            for(int i=0;i< DB_replist.size();i++)
                if(DB_replist.get(i).getZone().equals(zone)&&
                        DB_replist.get(i).getTo().equals(store)  ){
                    f=true;
                    indexDBZone =i;
                    break;
                }
                else
                {    f=false;
                    continue;}

        }
        if(flage==2){
            for(int i=0;i< DB_replist.size();i++)
                if(DB_replist.get(i).getZone().equals(zone)&&
                        DB_replist.get(i).getTo().equals(store)
                        && DB_replist.get(i).getItemcode().equals(itemcode)){
                    f=true;
                    indexDBitem =i;
                    break;
                }
                else
                {    f=false;
                    continue;}
        }
        return f;
    }

    public static void getqtyofDBzone() {
        int sum=0;
        for(int x=0;x< DB_replist.size();x++)
            if(DB_replist.get(x).getTo().equals(spinner.getSelectedItem().toString())&&
                    DB_replist.get(x).getZone().equals(DZRE_ZONEcode.getText().toString().trim()) )
                sum+=Integer.parseInt(DB_replist.get(x).getRecQty());
        DZRE_qtyshow.setText(sum+"");
    }










    public boolean ExistsInLocallist(ReplacementModel replacement) {
        boolean flag = false;
        if (replacementlist.size() != 0)
            for (int i = 0; i < replacementlist.size(); i++) {

                if (convertToEnglish(replacementlist.get(i).getItemcode()).equals(replacement.getItemcode())
                       )
                {

                    position=i;
                    flag = true;
                    break;

                } else
                    flag = false;
                continue;
            }

        return flag;

    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    private void showExitDialog() {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.confirm_title))
                .setContentText(getResources().getString(R.string.messageExit))
                .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (replacementlist.size() > 0) {

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getResources().getString(R.string.confirm_title))
                                    .setContentText(getResources().getString(R.string.messageExit2))
                                    .setConfirmButton(getResources().getString(R.string.yes), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {


                                            replacementlist.clear();
                                            adapter.notifyDataSetChanged();
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            sweetAlertDialog.dismiss();
                            finish();
                        }
                    }
                }).setCancelButton(getResources().getString(R.string.no), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        }).show();
    }

    private void getStors() {
        actvityflage=1;
        importData.getStore();
    }

    public void exportData() {
        try {
            saveflage=1;
            exportData.exportReplacementList(replacementlist);
        }catch (Exception e){

            // test
        }

    }
    public void exportAllData() {


         MaxVo=my_dataBase.replacementDao().getMaxVocherNo();
        saveflage=2;


        minVo=my_dataBase.replacementDao().getMinVocherNo();
        if(minVo!=null) {
            Allreplacementlist1 = my_dataBase.replacementDao().getReplacements(minVo);
            exportData.exportReplacementList(Allreplacementlist1);
        }
        else
        {
            Log.e("ggggg2","gggg2");
          if(saved==true)
              showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
           else {
               Log.e("ggggggg","gggggg");
              showSweetDialog(MainActivity.this, 0, "check connection", "");
          }
        }




    }
    private void init() {
        replacementlist.clear();
        export=findViewById(R.id.export);
        my_dataBase = RoomAllData.getInstanceDataBase(MainActivity.this);
        replacementlist.clear();
        poststateRE = findViewById(R.id.poststatRE);
        exportAllState=findViewById(R.id.exportAllState);
        itemrespons=findViewById(R.id.itemrespons);
        exportData = new ExportData(MainActivity.this);
        importData = new ImportData(MainActivity.this);
        listAllZone.clear();
//        importData.getAllZones();
        listQtyZone.clear();
        appSettings=new ArrayList();
        try {
            appSettings=my_dataBase.settingDao().getallsetting();
        }
        catch (Exception e){}
        fromSpinner = findViewById(R.id.fromspinner);
        toSpinner = findViewById(R.id.tospinner);
        zone = findViewById(R.id.zoneedt);
        itemcode = findViewById(R.id.itemcodeedt);
        qty = findViewById(R.id.qty);
        recqty = findViewById(R.id.qtyedt);
        replacmentRecycler = findViewById(R.id.replacmentRec);
        save = findViewById(R.id.save);
        respon = findViewById(R.id.respons);
        qtyrespons = findViewById(R.id.qtyrespon);
        //  recqty.setOnEditorActionListener(onEditAction);
        itemcode.setOnEditorActionListener(onEditAction);
        zone.setOnEditorActionListener(onEditAction);
        generalMethod = new GeneralMethod(MainActivity.this);


        zone.setOnKeyListener(onKeyListener);
        itemcode.setOnKeyListener(onKeyListener);
        search=findViewById(R.id.searchitemCode);
        maxTrans=my_dataBase.replacementDao().getMaxReplacementNo();
        Log.e("maxTrans",""+maxTrans);






        itemcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {

                    {
                        From = fromSpinner.getSelectedItem().toString();
                        To = toSpinner.getSelectedItem().toString();
                        FromNo=From.substring(0,From.indexOf(" "));
                        ToNo=To.substring(0,To.indexOf(" "));

                        ReplacementModel replacementModel=new ReplacementModel();


                        replacementModel.setTransNumber(max+"");
                        replacementModel.setFromName(From);
                        replacementModel.setIsPosted("0");
                        replacementModel.setToName(To);
                        replacementModel.setFrom( FromNo);
                        replacementModel.setDeviceId(deviceId);
                        replacementModel.setTo( ToNo);
                        replacementModel.setZone( "");
                        replacementModel.setReplacementDate(generalMethod.getCurentTimeDate(1));
                        replacementModel.setItemcode(itemcode.getText().toString());

                        if(ExistsInLocallist( replacementModel)) {
                            int sum=Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
                            replacementlist.get(position).setRecQty(sum+"");
                            my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(),replacementlist.get(position).getRecQty());
                       fillAdapter();
                            Log.e("case1","case1");
                            save.setEnabled(true);
                        }
                     else
                        {
//                            ReplacementModel replacementModel1 = my_dataBase.replacementDao().getReplacement(itemcode.getText().toString());
//                            if (replacementModel1 != null)
//                            {
//                                int sum=Integer.parseInt(replacementModel1.getRecQty())+1;
//                                replacementModel1.setRecQty(sum+"" );
//                                my_dataBase.replacementDao().updateQTY(itemcode.getText().toString(), replacementModel1.getRecQty());
//                            replacementlist.add(replacementModel1);
//                            fillAdapter();
//                                Log.e("case2","case2");
//                                save.setEnabled(true);
//                            }

//                            else
//                            {
                             if(ExsitsInItemlist(itemcode.getText().toString()))
                             {
                                 replacementModel.setItemname(AllItemDBlist.get(pos).getItemName());
                                 replacementModel.setRecQty("1");
                                replacementlist.add(replacementModel);
                                 SaveRow( replacementModel);
                                fillAdapter();
                                 Log.e("case3","case3");
                                 save.setEnabled(true);
                             }
                             else
                                 itemcode.setError("InValid");
                                itemcode.setText("");
                                Log.e("case4","case4");
//                            }
                        }

fromSpinner.setEnabled(false);
                     toSpinner.setEnabled(false);
                    }


                }
            }
        });


        itemrespons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (editable.toString().equals("ItemOCode")) {
                        Log.e("herea","aaaaa");
                        my_dataBase.itemDao().dELETEAll();
                        my_dataBase.itemDao().insertAll(AllImportItemlist);
                        Toast.makeText(MainActivity.this,"gat all data",Toast.LENGTH_SHORT).show();
                        ImportData.pdRepla.dismissWithAnimation();

                    }
                    else     if (editable.toString().equals("nodata")) {
                        Toast.makeText(MainActivity.this,"Network Error",Toast.LENGTH_SHORT).show();

                        ImportData.pdRepla.dismissWithAnimation();
                    }



                }
            }
        });



        //importData.getAllZones();
        respon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() != 0) {

                    if (editable.toString().equals("no data")) {

                        respon.setText("");
                        pdRepla2.dismiss();
                    } else {
                        if (editable.toString().equals("fill")) {
                            for (int i = 0; i < Storelist.size(); i++)
                            {   spinnerArray.add(Storelist.get(i).getSTORENO() + "  " + Storelist.get(i).getSTORENAME());
                                my_dataBase.storeDao().insert(Storelist.get(i));}


                            pdRepla2.dismiss();
                        }
                        fillSp();

                       // zone.requestFocus();
                        Log.e("afterTextChanged", "" + editable.toString());

                    }

                }

            }
        });
        exportAllState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    Log.e("editable.toString()+++",editable.toString()+"");
                    if (editable.toString().trim().equals("exported")) {
                        { //saveData(1);
                            //  for(int i=0;i<replacementlist.size();i++)
                            Log.e("max+++",max+"");
                            Log.e("TransferNo===",TransferNo+"");
                            my_dataBase.replacementDao().updatepostState(minVo);
                           exportAllData();

                       //   if(MaxVo.equals(TransferNo))  showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                            saved = true;
                            replacementlist.clear();
                            fillAdapter();
                            adapter.notifyDataSetChanged();
                            zone.setEnabled(true);
                            zone.requestFocus();

                            fromSpinner.setEnabled(true);
                            toSpinner.setEnabled(true);


                            save.setEnabled(false);
                        }
                    }
                    else if (editable.toString().trim().equals("not")) {
                        saved =false;
                        //saveData(0);
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();

                        save.setEnabled(false);
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);
                        Log.e("editable.to",editable.toString()+"");
                        showSweetDialog(MainActivity.this, 0, "check connection", "");
                    }
                    else{
                        Log.e("editable.t",editable.toString()+"");
                        saved=false;
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);
                        showSweetDialog(MainActivity.this, 0, "check connection", "");
                        save.setEnabled(false);
                    }
                }
            }
        });
        poststateRE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() != 0) {
                    if (editable.toString().trim().equals("exported")) {
                        { //saveData(1);
                          //  for(int i=0;i<replacementlist.size();i++)
                            Log.e("max+++",max+"");
                            my_dataBase.replacementDao().updatepostState(String.valueOf(max));
                            showSweetDialog(MainActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                            replacementlist.clear();
                            fillAdapter();
                            adapter.notifyDataSetChanged();
                            zone.setEnabled(true);
                            zone.requestFocus();

                            fromSpinner.setEnabled(true);
                            toSpinner.setEnabled(true);


                            save.setEnabled(false);
                        }
                    }
                    else if (editable.toString().trim().equals("not")) {

                        //saveData(0);
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();

                        save.setEnabled(false);
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);


                    }
                    else{
                        replacementlist.clear();
                        fillAdapter();
                        adapter.notifyDataSetChanged();
                        zone.setEnabled(true);
                        zone.requestFocus();
                        fromSpinner.setEnabled(true);
                        toSpinner.setEnabled(true);

                        save.setEnabled(false);


                    }
                }
            }
        });
//        qtyrespons.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                if (editable.toString().length() != 0) {
//                    Log.e("afterTextChanged",qtyrespons.getText().toString());
//                    if (qtyrespons.getText().toString().equals("QTY"))
//                    {
//                        if(Integer.parseInt(listQtyZone.get(0).getQty())>0)
//                        {
//                            try {
//
//
//                                filldata();
//                                MainActivity.qty.setText("");
//
//
//                            } catch (Exception e) {
//                                Log.e("Exception", e.getMessage());
//                            }
//                            save.setEnabled(true);
//                            recqty.setText("1");
//
//
//                            zone.setEnabled(false);
//                            itemcode.setText("");
//                            itemcode.requestFocus();
//
//                            recqty.setEnabled(false);
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this,getResources().getString(R.string.notvaildqty),Toast.LENGTH_SHORT).show();
//                            itemcode.setText("");
//                            itemcode.requestFocus();
//                        }
//
//
//
//                    }
//                    else
//                    {
//                        showSweetDialog(MainActivity.this, 3, "", getResources().getString(R.string.existsBARCODE));
//                        recqty.setEnabled(false);
//
//                        itemcode.setText("");
//                        itemcode.requestFocus();
//
//                    }
//                }
//            }
//        });

        if(appSettings.size()!=0)
        {
            deviceId=  appSettings.get(0).getDeviceId();
            Log.e("appSettings","+"+deviceId);

        }


    }

    private boolean ExsitsInItemlist(String itemcode) {


        boolean flage = false;
        for (int x1 = 0; x1 < AllItemDBlist.size(); x1++) {
            if (AllItemDBlist.get(x1).getItemOcode().equals(itemcode)) {
                pos=x1;

                flage = true;
                break;
            } else {
                flage = false;
            }




        }
        return flage;
    }
    private void fillSp() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
        toSpinner.setSelection(1);
        Log.e("sss1","sss1");
        if(AllItemDBlist .size()==0)
            importData.getAllItems();
    }



    public void ScanCode(View view) {
        switch (view.getId()) {
            case R.id.scanZoneCode:
                readBarcode(4);

                break;
            case R.id.scanItemCode:
                readBarcode(5);


                break;
        }
    }




    TextView.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.e("keyEvent.getAction()",keyEvent.getAction()+"");


            if (i == KeyEvent.KEYCODE_BACK) {
                onBackPressed();

            }

            if (i != KeyEvent.KEYCODE_ENTER) {

                { if (keyEvent.getAction() == KeyEvent.ACTION_UP)
                    switch (view.getId()) {
                      /*  case R.id.zoneedt:
                            Log.e("ZONEHERE",zone.getText().toString()+"");
                            if(!zone.getText().toString().equals(""))
                                if(searchZone(zone.getText().toString().trim()))
                                {
                                    Log.e("zone",zone.getText().toString());
                                }
                                else {
                                    zone.setError("Invalid");
                                    Log.e("elsezone",zone.getText().toString());
                                    zone.setText("");}
                            else
                                zone.requestFocus();
                            break;*/
                        case R.id.itemcodeedt:

                            if(!itemcode.getText().toString().equals(""))
                            {

                                //varify();


                            }

                            else
                                itemcode.requestFocus();
                            break;
                    }  return true;
                }}
            return false;

        }};

//    private void varify() {
//        {
//
//
//            fromSpinner.setEnabled(false);
//            toSpinner.setEnabled(false);
//            Log.e( "itemcodeedt ",itemcode.getText().toString()+"");
//
//            From = fromSpinner.getSelectedItem().toString();
//            To = toSpinner.getSelectedItem().toString();
//            FromNo=From.substring(0,From.indexOf(" "));
//            ToNo=To.substring(0,To.indexOf(" "));
//            Zone = convertToEnglish(zone.getText().toString().trim());
//            Itemcode = convertToEnglish(itemcode.getText().toString().trim());
//
//
//            replacement = new ReplacementModel();
//            replacement.setFrom( FromNo);
//            replacement.setTo(ToNo);
//            replacement.setZone(Zone);
//            replacement.setItemcode(Itemcode);
//            replacement.setFromName(From);
//            replacement.setToName(To);
//            replacement.setDeviceId(deviceId);
//
//            zone.setEnabled(false);
//            ReplacementModel replacementModel=my_dataBase.replacementDao().getReplacement(Itemcode,Zone, FromNo,ToNo);
//            if(replacementModel!=null) {
//                if (!CaseDuplicates(replacementModel))
//                    replacementlist.add(replacementModel);
//                save.setEnabled(true);
//            }
//
//
//            if (CaseDuplicates(replacement)) {
//                Log.e("replacementFrom",replacement.getTo());
//                Log.e("AddInCaseDuplicates","AddInCaseDuplicates");
//                //update qty in Duplicate case
//                int sum=Integer.parseInt(replacementlist.get(position).getRecQty()) + Integer.parseInt("1");
//                Log.e("aaasum ",sum+"");
//
//                if(sum<=Integer.parseInt(replacementlist.get(position).getQty()))
//                {
//                    replacementlist.get(position).setRecQty((sum+""));
//                    my_dataBase.replacementDao().updateQTY(replacementlist.get(position).getItemcode(),replacementlist.get(position).getRecQty());
//
//                    zone.setEnabled(false);
//                    itemcode.setText("");
//                    itemcode.requestFocus();
//                    fillAdapter();
//                    Log.e("heree","here2");
//                }
//                else
//                {   showSweetDialog(MainActivity.this, 3, "", getResources().getString(R.string.notvaildqty));
//
//                    fillAdapter();
//                    zone.setEnabled(false);
//                    itemcode.setText("");
//                    itemcode.requestFocus();
//                }
//
//
//            }
//
//
//            else
//            {
//                Log.e("not in db","not in db");
//
//                importData.getQty();
//
//            }
//        }
//    }


    EditText.OnEditorActionListener onEditAction = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH
                    || i == EditorInfo.IME_NULL) {
                switch (textView.getId()) {



                    case R.id.zoneedt:
                        Log.e("ZONEHERE",zone.getText().toString()+"");
                        if(!zone.getText().toString().equals(""))
                            if(searchZone(zone.getText().toString().trim()))
                            {
                                Log.e("zone",zone.getText().toString());
                            }
                            else {
                                zone.setError("Invalid");
                                Log.e("elsezone",zone.getText().toString());
                                zone.setText("");}
                        else
                            zone.requestFocus();
                        break;
                    case R.id.itemcodeedt:

                        if(!itemcode.getText().toString().equals(""))
                        {

                            //varify();
                        }

                        else
                            itemcode.requestFocus();
                        break;

                }



            }

            return true;
        }
    };


    /* private boolean searchZone(String codeZone) {
         Log.e("search", " searchZone1");
         Log.e("listAllZone", "" + listAllZone.size());
         zone.setError(null);
         for (int i = 0; i < listAllZone.size(); i++) {
             if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                 Log.e(" searchZone", " searchZone");
                return true;

             }
         }
    return false; }*/
    private boolean searchZone(String codeZone) {
        Log.e("search",codeZone);
        Log.e("search"," searchZone1");
        Log.e("listAllZone",""+listAllZone.size());
        zone.setError(null);
        for (int i = 0; i < listAllZone.size(); i++) {

            if (listAllZone.get(i).getZoneCode().equals(codeZone)) {
                itemcode.setEnabled(true);
                itemcode.requestFocus();
                zone.setEnabled(false);
                return true;

            }
        }

        return  false;

    }
    private String getusernum() {
        return "";
    }
    private void filldata() {

        Qty = recqty.getText().toString().trim();
        if (Zone.toString().trim().equals("")) zone.setError("required");

        else if (Itemcode.toString().trim().equals("")) itemcode.setError("required");


        else if (Qty.toString().trim().equals(""))
            recqty.setError("required");
        else {


            replacement.setRecQty(Qty);
            replacement.setUserNO(UserNo);
            Log.e("replacement","1=="+qty.getText().toString());
            replacement.setQty( listQtyZone.get(0).getQty());
            Log.e("replacement","2=="+replacement.getQty());
            replacement.setIsPosted("0");
            replacement.setUserNO(getusernum());
            replacement.setDeviceId(deviceId);
            replacement.setReplacementDate(generalMethod.getCurentTimeDate(1) + "");
            Log.e("else","AddInCaseDuplicates");
            Log.e("replacementlist.size", replacementlist.size()+"");
            replacementlist.add(replacement);
            Log.e("replacementlist.size", replacementlist.size()+""+replacementlist.get(0).getFrom());
            fillAdapter();
            SaveRow(replacement);
        }




    }

    private void SaveRow(ReplacementModel replacement) {
        Log.e("SaveRow","replacement"+replacement.getDeviceId());
        my_dataBase.replacementDao().insert(replacement);
    }

    private void updateQTYOfZone() {
        String d="";
        for (int i = 0; i < listQtyZone.size(); i++) {
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                {
                    listQtyZone.get(i).setQty(Integer.parseInt(listQtyZone.get(i).getQty()) -Integer.parseInt( Qty)+"");
                    d=listQtyZone.get(i).getQty();
                }
            }}
        Log.e(" updateQTYOfZone()",d) ;
    }

    private void fillAdapter() {
        Log.e(" fillAdapter"," fillAdapter");
        replacmentRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new ReplacementAdapter(replacementlist,MainActivity.this);
        replacmentRecycler.setAdapter(adapter);
    }


    public  void readBarcode(int type) {
        //new IntentIntegrator(AddZone.this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();

        openSmallCapture(type);
    }

    private void openSmallCapture(int type) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                Log.e("requestresult", "PERMISSION_GRANTED");
                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
                // searchByBarcodeNo(s + "");
            }
        } else {
            Intent i = new Intent(MainActivity.this, ScanActivity.class);
            if (type == 4) {
                i.putExtra("key", "4");
            } else {
                i.putExtra("key", "5");
            }

            startActivity(i);
            //  searchByBarcodeNo(s + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Toast.makeText(MainActivity.this, "cancelled", Toast.LENGTH_SHORT).show();


            } else {
                String valueBarcode = Result.getContents();

            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void saveData(int isposted) {
        if (isposted == 1)
            for (int i = 0; i < replacementlist.size(); i++)
                replacementlist.get(i).setIsPosted("1");
        long result[] = my_dataBase.replacementDao().insertAll(replacementlist);

        if (result.length != 0) {
            showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
        }


    }
    public static void updateAdpapter() {
        adapter.notifyDataSetChanged();
    }

    public  boolean checkQtyValidate(String recqty) {
        Log.e("checkQtyValidate","heckQtyValidate");
        for (int i = 0; i < listQtyZone.size(); i++) {
            Log.e("checkQtyValidate","for");
            if (itemcode.getText().toString().trim().equals(listQtyZone.get(i).getItemCode().trim())
                    && zone.getText().toString().trim().equals(listQtyZone.get(i).getZoneCode().trim())) {
                if (Integer.parseInt(recqty) <= Integer.parseInt(listQtyZone.get(i).getQty()))
                {

                    return true;

                }
                else {

                    return false;


                }
            }
        }

        return false; }
}