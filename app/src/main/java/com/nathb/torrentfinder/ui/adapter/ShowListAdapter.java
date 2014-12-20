package com.nathb.torrentfinder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nathb.torrentfinder.model.Show;

import java.util.List;

public class ShowListAdapter extends ArrayAdapter<Show> {

    private LayoutInflater mInflater;

    public ShowListAdapter(Context context, int textViewResourceId, List<Show> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        final Show show = getItem(position);
        convertView.setTag(show);

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(show.toString());

        return convertView;
    }
}
