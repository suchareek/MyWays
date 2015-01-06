package com.example.myways;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.database.DatabaseHandler;

public class NewPointActivity extends Activity {
	
	private static final int CAMERA_REQUEST = 1888; 
	
    private ImageView imageView;
	private Button cancelButton, confirmButton, photoButton;
	private EditText pointName, pointDesc;
	private RatingBar markBar;
	private DatabaseHandler myDataBase;
	private Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_point);
		
		cancelButton = (Button) findViewById(R.id.cancelPoint);
		confirmButton = (Button) findViewById(R.id.addPoint);
		pointName= (EditText) findViewById(R.id.pointName);
		pointDesc= (EditText) findViewById(R.id.pointDescription);
		markBar = (RatingBar) findViewById(R.id.markBar);
		imageView = (ImageView) findViewById(R.id.image);
        photoButton = (Button) findViewById(R.id.takePhoto);
		
		
	}
	
	public void takePhoto(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        startActivityForResult(cameraIntent, CAMERA_REQUEST); 
    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            photo = (Bitmap) data.getExtras().get("data"); 
            imageView.setImageBitmap(photo);
        }  
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
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			
			if (myDataBase.createPoint(name, desc, lat, lng, mark, byteArray)==-1)
			{
				Toast.makeText( getApplicationContext(),"Nie uda³o sie zapisaæ, b³¹d bazy danych", Toast.LENGTH_SHORT ).show();
			}
			else
			{
				myDataBase.close();
				Toast.makeText( getApplicationContext(),"Pomyœlnie zapisano punkt", Toast.LENGTH_SHORT ).show();
				finish();
			}
		}
		
	}

}
