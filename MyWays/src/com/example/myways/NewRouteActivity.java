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

public class NewRouteActivity extends Activity {

	private Button confirmButton, cancelButton;
	private EditText routeName, routeDesc;
	private RatingBar markBar;
	private DatabaseHandler myDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_route);
		
		cancelButton = (Button) findViewById(R.id.cancelRoute);
		confirmButton = (Button) findViewById(R.id.newRoute);
		routeName= (EditText) findViewById(R.id.routeName);
		routeDesc= (EditText) findViewById(R.id.routeDescription);
		markBar = (RatingBar) findViewById(R.id.markBarR);
		
	}
	
	public void cancelAdd(View v)
	{
		MapActivity.IS_REGISTER = false;
		finish();
	}
	
	public void addNewRoute(View v)
	{
		Intent i = getIntent();
		String name = routeName.getText().toString();
		String desc = routeDesc.getText().toString();
		int mark=markBar.getProgress();
		
		if (name.length()==0 || desc.length()==0)
		{
			Toast.makeText( getApplicationContext(),"Wpisz nazw� i opis miejsca", Toast.LENGTH_SHORT ).show();
		}
		else
		{
			myDataBase=new DatabaseHandler(this);
			myDataBase.open();
			
			if (myDataBase.createRoute(name, desc, mark)==-1)
			{
				Toast.makeText( getApplicationContext(),"Nie uda�o sie zapisa�, b��d bazy danych", Toast.LENGTH_SHORT ).show();
			}
			else
			{
				myDataBase.close();
				Toast.makeText( getApplicationContext(),"Rozpocz�to rejestracje trasy", Toast.LENGTH_SHORT ).show();
				MapActivity.IS_REGISTER = true;
				finish();
			}
		}
		
	}

}
