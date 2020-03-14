package com.example.whowantstobeamillionare;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {


    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, -1, values);

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if ( position % 2 == 0) {
            view.setBackgroundColor(Color.RED);
        } else {
            view.setBackgroundColor(Color.YELLOW);
        }
        return view;
    }


}