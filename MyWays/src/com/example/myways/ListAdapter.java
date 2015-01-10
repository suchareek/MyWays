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
import android.widget.RatingBar.OnRatingBarChangeListener;


public class ListAdapter extends ArrayAdapter<Point>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Point> data = null;
    
    public ListAdapter(Context context, int layoutResourceId, ArrayList<Point> data) 
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
            holder.imgIcon = (ImageView)row.findViewById(R.id.image);
            holder.txtTitle = (TextView)row.findViewById(R.id.txt);
            holder.bar = (RatingBar)row.findViewById(R.id.ratingBar);
            
            row.setTag(holder);
            
        }
        else
        {
            holder = (ItemHolder)row.getTag();
        }
        
        Point item=data.get(position);

        holder.txtTitle.setText(item.getPointName());
        
        byte[] imgArray = item.getImg();
		
        Bitmap bmp=null;
		
		if(imgArray!=null)
		{
			bmp = BitmapFactory.decodeByteArray(imgArray, 0, imgArray.length);
			holder.imgIcon.setImageBitmap(bmp);
		}
        
        holder.bar.setProgress(item.getMark());
        
        return row;
    }
	
	static class ItemHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        RatingBar bar;
    }
	
}

