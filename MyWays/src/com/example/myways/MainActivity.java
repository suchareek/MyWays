package com.example.myways;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button launchMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		launchMap = (Button) findViewById(R.id.launchMap);
		
	}
	
	public void launchMap(View v)
	{
		Intent j = new Intent(MainActivity.this, MapActivity.class);
		startActivity(j);
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
