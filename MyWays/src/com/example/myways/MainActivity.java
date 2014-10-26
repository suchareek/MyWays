package com.example.myways;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.database.DatabaseHandler;

public class MainActivity extends Activity {
	
	private Button launchMap, showPoints;
	private ListView list;
	private ArrayAdapter<Point> pointAdapter;
	private DatabaseHandler myDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		launchMap = (Button) findViewById(R.id.launchMap);
		showPoints = (Button) findViewById(R.id.showPoints);
		list = (ListView) findViewById(R.id.listView);
		
	}
	
	public void launchMap(View v)
	{
		Intent j = new Intent(MainActivity.this, MapActivity.class);
		startActivity(j);
	}
	
	public void showPoints(View v)
	{
		myDataBase=new DatabaseHandler(this);
		myDataBase.open();
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points=myDataBase.getPoints();
		
		myDataBase.close();
		
		pointAdapter = new ArrayAdapter<Point>(this, R.layout.text, points);
		list.setAdapter(pointAdapter);
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
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
