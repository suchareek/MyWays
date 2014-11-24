package com.example.myways;



import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {
	
	private static boolean IS_REGISTER;
	
	private GoogleMap map;
	private Button viewChangeButton, addPointButton, showPointButton, registerButton;
	private int userIcon,pointIcon;
	private double lat,lng;
	private LocationManager mlocManager;
	private MyLocationListener mlocListener;
	private Marker userMarker;
	private DatabaseHandler myDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		viewChangeButton = (Button) findViewById(R.id.viewChange);
		addPointButton = (Button) findViewById(R.id.addPoint);
		showPointButton = (Button) findViewById(R.id.showPoints);
		registerButton = (Button) findViewById(R.id.registerRoute);
		
		registerButton.setText("Rejestruj Trasê");
		IS_REGISTER = false;
		
		map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		viewChangeButton.setText("Normalna");
		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.061452, 19.937925),12.0f), 1000, null);
		
		userIcon = R.drawable.yellow_point;
		pointIcon = R.drawable.green_point;
		
		mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		mlocListener = new MyLocationListener();

		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 0, mlocListener);
		
	}
	
	public void register(View v)
	{
		
		if (registerButton.getText()=="Rejestruj Trasê")
		{
			registerButton.setText("STOP");
			IS_REGISTER=true;
		}
		else
		{
			registerButton.setText("Rejestruj Trasê");
			IS_REGISTER=false;
		}
		
		/*/ Instantiates a new Polyline object and adds points to define a rectangle
		PolylineOptions rectOptions = new PolylineOptions()
		        .add(new LatLng(50.04, 19.91))
		        .add(new LatLng(50.04, 19.93))  // North of the previous point, but at the same longitude
		        .add(new LatLng(50.04, 19.95))  // Same latitude, and 30km to the west
		        .add(new LatLng(50.04, 19.97))  // Same longitude, and 16km to the south
		        .add(new LatLng(50.04, 19.99))
		        .color(Color.RED)
		        .width(4); // Closes the polyline.


		// Get back the mutable Polyline
		Polyline polyline = map.addPolyline(rectOptions);*/
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
		Intent j = new Intent(MapActivity.this, NewPointActivity.class);
		j.putExtra("lat", lat);
		j.putExtra("long", lng);
		startActivity(j);
	}
	
	public void showPoints(View v)
	{
		myDataBase=new DatabaseHandler(this);
		myDataBase.open();
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points=myDataBase.getPoints();
		
		LatLng lastLatLng;
		
		for(int i=0; i<points.size(); i++)
		{
			lastLatLng = new LatLng(points.get(i).getPointLat(),points.get(i).getPointLong());
			
			map.addMarker(new MarkerOptions()
	    	.position(lastLatLng)
	    	.title(points.get(i).getPointName())
	    	.icon(BitmapDescriptorFactory.fromResource(pointIcon))
	    	.snippet(""));
		}
		
		
		
		myDataBase.close();
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
	
	@Override
	protected void onResume(){
		super.onResume();
		Log.d("onResume", "MapActivity onResume");
		
		myDataBase=new DatabaseHandler(this);
		myDataBase.open();
		
		ArrayList<Point> points = new ArrayList<Point>();
		
		points=myDataBase.getPoints();
		
		LatLng lastLatLng = new LatLng(points.get(points.size()-1).getPointLat(),points.get(points.size()-1).getPointLong());
		
		map.addMarker(new MarkerOptions()
    	.position(lastLatLng)
    	.title(points.get(points.size()-1).getPointName())
    	.icon(BitmapDescriptorFactory.fromResource(pointIcon))
    	.snippet(""));
		
		myDataBase.close();
		
	}
	
	@Override
	protected void onStop() {
	    super.onStop();
	    Log.d("onStop", "MapActivity onStop");
	}

	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    Log.d("onStop", "MapActivity onDestroy");
	}

}
