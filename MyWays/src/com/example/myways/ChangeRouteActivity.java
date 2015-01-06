package com.example.myways;

import com.example.database.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ChangeRouteActivity extends Activity {
	
	private Button cancelButton, confirmButton;
	private EditText pointName, pointDesc;
	private RatingBar markBar;
	private DatabaseHandler myDataBase;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_activity_route);
		
		
		cancelButton = (Button) findViewById(R.id.cancelcRoute);
		confirmButton = (Button) findViewById(R.id.newcRoute);
		pointName= (EditText) findViewById(R.id.routecName);
		pointDesc= (EditText) findViewById(R.id.routecDescription);
		markBar = (RatingBar) findViewById(R.id.markcBarR);
		
		Intent i = getIntent();
		id = i.getIntExtra("id", 0);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");
		int mark = i.getIntExtra("mark", 0);
		
		pointName.setText(name);
		pointDesc.setText(desc);
		markBar.setProgress(mark);
		
	}
	
	public void cancelrChange(View v)
	{
		finish();
	}
	
	public void addChangeRoute(View v)
	{
		myDataBase=new DatabaseHandler(this);
		myDataBase.open();
		
		if (myDataBase.updateRoute(id, pointName.getText().toString(), pointDesc.getText().toString(), markBar.getProgress())==-1)
		{
			Toast.makeText( getApplicationContext(),"Nie uda³o sie zapisaæ, b³¹d bazy danych", Toast.LENGTH_SHORT ).show();
		}
		else
		{
			myDataBase.close();
			Toast.makeText( getApplicationContext(),"Pomyœlnie zmieniono punkt", Toast.LENGTH_SHORT ).show();
			finish();
		}
	}

}
