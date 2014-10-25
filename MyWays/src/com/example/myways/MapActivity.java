package com.example.myways;



import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {
	
	private GoogleMap map;
	private Button viewChangeButton, addPointButton;
	private int userIcon;
	private double lat,lng;
	private LocationManager mlocManager;
	private MyLocationListener mlocListener;
	private Marker userMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		viewChangeButton = (Button) findViewById(R.id.viewChange);
		addPointButton = (Button) findViewById(R.id.addPoint);
		
		map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		viewChangeButton.setText("Normalna");
		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.061452, 19.937925),12.0f), 1000, null);
		
		userIcon = R.drawable.yellow_point;
		
		mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		mlocListener = new MyLocationListener();

		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 0, mlocListener);
		
	}
	
	public void viewChange(View v)
	{
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
	
	public void addPoint(View v)
	{
		Intent j = new Intent(MapActivity.this, NewPoint.class);
		j.putExtra("lat", lat);
		j.putExtra("long", lng);
		startActivity(j);
	}
	
	private void updatePlaces()
	{
		Location lastLoc = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		LatLng lastLatLng = new LatLng(lat,lng);
		
		if(userMarker!=null){
			userMarker.remove();
		}
		
		userMarker = map.addMarker(new MarkerOptions()
	    	.position(lastLatLng)
	    	.title("Your last position")
	    	.icon(BitmapDescriptorFactory.fromResource(userIcon))
	    	.snippet(""));
		
		map.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 1000, null);
		
	}
	
	public class MyLocationListener implements LocationListener
	{

		@Override
		public void onLocationChanged(Location loc)
		{
			lat = loc.getLatitude();
			lng = loc.getLongitude();
			
			updatePlaces();
			
		}
		
		@Override
		public void onProviderDisabled(String provider)
		{
			Toast.makeText( getApplicationContext(),"Gps Disabled",	Toast.LENGTH_SHORT ).show();
		}
		
		@Override
		public void onProviderEnabled(String provider)
		{
			Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			
		}
		
	}

}
