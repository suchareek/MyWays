package com.example.myways;

import com.example.database.DatabaseHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

public class ChangePointActivity extends Activity {
	
	private Button cancelButton, confirmButton;
	private EditText pointName, pointDesc;
	private RatingBar markBar;
	private DatabaseHandler myDataBase;
	int id;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_activity_point);
		
		
		cancelButton = (Button) findViewById(R.id.cancelcPoint);
		confirmButton = (Button) findViewById(R.id.newcPoint);
		pointName= (EditText) findViewById(R.id.pointcName);
		pointDesc= (EditText) findViewById(R.id.pointcDescription);
		markBar = (RatingBar) findViewById(R.id.markcBar);
		img = (ImageView) findViewById(R.id.img);
		
		Intent i = getIntent();
		id = i.getIntExtra("id", 0);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");
		int mark = i.getIntExtra("mark", 0);
		byte[] imgArray = i.getByteArrayExtra("img");
		
		pointName.setText(name);
		pointDesc.setText(desc);
		markBar.setProgress(mark);
		
		Bitmap bmp=null;
		
		if(imgArray!=null)
		{
			bmp = BitmapFactory.decodeByteArray(imgArray, 0, imgArray.length);
			img.setImageBitmap(bmp);
		}
		
		
		
	}
	
	public void cancelChange(View v)
	{
		finish();
	}
	
	public void changeNewPoint(View v)
	{
		myDataBase=new DatabaseHandler(this);
		myDataBase.open();
		
		if (myDataBase.updatePoint(id, pointName.getText().toString(), pointDesc.getText().toString(), markBar.getProgress())==-1)
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
