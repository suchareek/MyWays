package com.example.myways;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.database.DatabaseHandler;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends Activity {
	
	private Button launchMap, showPoints, showRoutes;
	private ListView pointList, routeList;
	private ArrayAdapter<Point> pointAdapter;
	private ArrayAdapter<Route> routeAdapter;
	private DatabaseHandler myDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		launchMap = (Button) findViewById(R.id.launchMap);
		showPoints = (Button) findViewById(R.id.showPoints);
		showRoutes = (Button) findViewById(R.id.showRoutes);
		pointList = (ListView) findViewById(R.id.listView);
		routeList = (ListView) findViewById(R.id.listView1);
		
		myDataBase=new DatabaseHandler(this);
		
		pointAdapter=null;
		routeAdapter=null;
		
		pointList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				Point p =(Point) pointList.getItemAtPosition(position);
				
				Intent j = new Intent(MainActivity.this, ChangePointActivity.class);
        		
        		j.putExtra("id", p.getPointID());
        		j.putExtra("name", p.getPointName());
        		j.putExtra("desc", p.getPointDescription());
        		j.putExtra("mark", p.getMark());
        		j.putExtra("img", p.getImg());
        		
        		startActivity(j);
						
			}
		});
		
		routeList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				Route r =(Route) routeList.getItemAtPosition(position);
				
				Intent j = new Intent(MainActivity.this, ChangeRouteActivity.class);
        		
        		j.putExtra("id", r.getRouteID());
        		j.putExtra("name", r.getRouteName());
        		j.putExtra("desc", r.getRouteDescription());
        		j.putExtra("mark", r.getRouteMark());
        		
        		startActivity(j);
						
			}
		});
		
	}
	// zmiany
	public void launchMap(View v)
	{
		Intent j = new Intent(MainActivity.this, MapActivity.class);
		startActivity(j);
	}
	
	public void showPoints(View v)
	{
		
		myDataBase.open();
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points=myDataBase.getPoints();
		
		myDataBase.close();
		
		if(routeAdapter!=null)
		{
			routeAdapter.clear();
			routeList.setAdapter(routeAdapter);
			routeAdapter=null;
		}
		
		pointAdapter = new ArrayAdapter<Point>(this, R.layout.text, points);
		pointList.setAdapter(pointAdapter);
		
	}
	
	public void showRoutes(View v)
	{
		
		myDataBase.open();
		
		ArrayList<Route> routes = new ArrayList<Route>();
		
		routes=myDataBase.getRoutes();
		
		myDataBase.close();
		
		if(pointAdapter!=null)
		{
			pointAdapter.clear();
			pointList.setAdapter(pointAdapter);
			pointAdapter=null;
		}
		
		routeAdapter = new ArrayAdapter<Route>(this, R.layout.text, routes);
		routeList.setAdapter(routeAdapter);
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		myDataBase.open();
		
		if(routeAdapter!=null)
		{
			ArrayList<Route> routes = new ArrayList<Route>();
			
			routes=myDataBase.getRoutes();
			
			routeAdapter = new ArrayAdapter<Route>(this, R.layout.text, routes);
			routeList.setAdapter(routeAdapter);
		}
		
		if(pointAdapter!=null)
		{
			ArrayList<Point> points = new ArrayList<Point>();
			
			points=myDataBase.getPoints();
			
			pointAdapter = new ArrayAdapter<Point>(this, R.layout.text, points);
			pointList.setAdapter(pointAdapter);
		}
		
		myDataBase.close();
	}
	
	@Override
	protected void onStop() {
	    super.onStop();
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	}
	
}
