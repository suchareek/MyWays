package com.example.myways;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewPoint extends Activity {
	
	private Button cancelButton, confirmButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_point);
		
		cancelButton = (Button) findViewById(R.id.cancelPoint);
		confirmButton = (Button) findViewById(R.id.addPoint);
		
		
	}
	
	public void cancelAdd(View v)
	{
		finish();
	}
	
	public void addNewPoint(View v)
	{
		
	}

}
