package com.example.whowantstobeamillionare;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {

    private int currentPosition = 14;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, 0, values);
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        notifyDataSetChanged();
    }

    /**
     * sets the background colour of a specific line.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.list_view_text_view);
        textView.setText(getItem(position));
        if (position == this.currentPosition) {
            convertView.setBackgroundColor(Color.argb(170,255, 170, 0));
        } else {
            convertView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
        return convertView;
    }
}