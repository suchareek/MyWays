package com.example.myways;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends FragmentActivity {
	
	private GoogleMap map;
	private Button viewChangeButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		viewChangeButton = (Button) findViewById(R.id.viewChange);
		
		map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		viewChangeButton.setText("Normalna");
		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.061452, 19.937925),12.0f), 3000, null);
		
		viewChangeButton.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	
		    	if (map.getMapType()==GoogleMap.MAP_TYPE_HYBRID)
		    	{
		    		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		    		viewChangeButton.setText("Hybryda");
		    	}
		    	else
		    	{
		    		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		    		viewChangeButton.setText("Normalna");
		    	}
		        
		    }
		});
		
		
	}

}
