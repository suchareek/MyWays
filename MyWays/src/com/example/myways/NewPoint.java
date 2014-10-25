package com.example.myways;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.database.DatabaseHandler;

public class NewPoint extends Activity {
	
	private Button cancelButton, confirmButton;
	private EditText pointName, pointDesc;
	private RatingBar markBar;
	private DatabaseHandler myDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_point);
		
		cancelButton = (Button) findViewById(R.id.cancelPoint);
		confirmButton = (Button) findViewById(R.id.addPoint);
		pointName= (EditText) findViewById(R.id.pointName);
		pointDesc= (EditText) findViewById(R.id.pointDescription);
		markBar = (RatingBar) findViewById(R.id.markBar);
		
	}
	
	public void cancelAdd(View v)
	{
		finish();
	}
	
	public void addNewPoint(View v)
	{
		Intent i = getIntent();
		double lat = i.getDoubleExtra("lat", 0.0);
		double lng = i.getDoubleExtra("long", 0.0);
		String name = pointName.getText().toString();
		String desc = pointDesc.getText().toString();
		int mark=markBar.getProgress();
		
		if (name.length()==0 || desc.length()==0)
		{
			Toast.makeText( getApplicationContext(),"Wpisz nazwê i opis miejsca", Toast.LENGTH_SHORT ).show();
		}
		else
		{
			myDataBase=new DatabaseHandler(this);
			myDataBase.open();
			
			if (myDataBase.createPoint(name, desc, lat, lng, mark)==-1)
			{
				Toast.makeText( getApplicationContext(),"Nie uda³o sie zapisaæ, b³¹d bazy danych", Toast.LENGTH_SHORT ).show();
			}
			else
			{
				Toast.makeText( getApplicationContext(),"Pomyœlnie zapisano punkt", Toast.LENGTH_SHORT ).show();
				finish();
			}
		}
		
	}

}
