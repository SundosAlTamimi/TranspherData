package com.hiaryabeer.transferapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hiaryabeer.transferapp.Models.AllItems;
import com.hiaryabeer.transferapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.hiaryabeer.transferapp.MainActivity.dialog1;
import static com.hiaryabeer.transferapp.MainActivity.itemcode;

public class Adapterr extends BaseAdapter {
    private Context context; //context
    private List<AllItems> items; //data source of the list adapter

    //public constructor
    public Adapterr(Context context, List<AllItems> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.searchrec, parent, false);
        }

        // get current item to be displayed
        AllItems currentItem = (AllItems) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.itemname);
        LinearLayout linearLayout= convertView.findViewById(R.id.linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemcode.setText(items.get(position).getItemOcode());
                dialog1.dismiss();
            }
        });
        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getItemName());

        // returns the view for the current row
        return convertView;
    }
}