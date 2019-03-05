package com.example.bartek.badminton.Profile;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bartek.badminton.R;


public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private String[] contentArray;
    private TypedArray imageArray;

    public SpinnerAdapter(Context context, int resource, String[] objects, TypedArray imageArray) {
        super(context, R.layout.spinner_country_item, R.id.edit_profile_country_spinner, objects);
        this.ctx = context;
        this.contentArray = objects;
        this.imageArray = imageArray;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_country_item, null);
        }

        TextView textView = convertView.findViewById(R.id.spinner_country_item_name);
        textView.setText(contentArray[position]);

        ImageView imageView = convertView.findViewById(R.id.spinner_country_item_flag);
        imageView.setImageResource(imageArray.getResourceId(position, -1));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}

