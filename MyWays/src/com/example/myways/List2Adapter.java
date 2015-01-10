package com.example.myways;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myways.ListAdapter.ItemHolder;

public class List2Adapter extends ArrayAdapter<Route>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Route> data = null;
    
    public List2Adapter(Context context, int layoutResourceId, ArrayList<Route> data) 
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        
    }

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) 
	{
        View row = convertView;
        ItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ItemHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txt);
            holder.bar = (RatingBar)row.findViewById(R.id.ratingBar);
            
            row.setTag(holder);
            
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }
        
        Route item=data.get(position);

        holder.txtTitle.setText(item.getRouteName());
        holder.bar.setProgress(item.getRouteMark());
        
        return row;
    }
	
	static class ItemHolder
    {
        TextView txtTitle;
        RatingBar bar;
    }
	
}