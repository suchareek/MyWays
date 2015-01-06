package com.example.myways;



import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.database.DatabaseHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity {
	
	private static boolean IS_REGISTER;
	
	private GoogleMap map;
	private Button viewChangeButton, addPointButton, showPointButton, registerButton, showRoutesButton;
	private int userIcon,pointIcon, registerRouteId, licznikCzasu;
	private double lat,lng;
	private LocationManager mlocManager;
	private MyLocationListener mlocListener;
	private Marker userMarker;
	private DatabaseHandler myDataBase;
	private ListView routeList;
	private ArrayAdapter<Route> routeAdapter;
	private Polyline polyline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		viewChangeButton = (Button) findViewById(R.id.viewChange);
		addPointButton = (Button) findViewById(R.id.addPoint);
		showPointButton = (Button) findViewById(R.id.showPoints);
		registerButton = (Button) findViewById(R.id.registerRoute);
		routeList = (ListView) findViewById(R.id.routeList);
		showRoutesButton = (Button) findViewById(R.id.showRoutes);
		
		myDataBase=new DatabaseHandler(this);
		
		licznikCzasu=0;
		
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
		
		routeList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				Route route =(Route) routeList.getItemAtPosition(position);
				
				ArrayList<Point> pts = myDataBase.getCoordinates(route.getRouteID());
				
				PolylineOptions pOptions = new PolylineOptions()
						.color(Color.RED)
				        .width(4);
				
				for(int i=0; i<pts.size(); i++)
				{
					pOptions.add(new LatLng(pts.get(i).getPointLat(), pts.get(i).getPointLong()));
				}
				
				if(polyline != null)
				{
					polyline.remove();
				}
				polyline = map.addPolyline(pOptions);
						
			}
		});
		
	}
	
	public void showRoutes(View v)
	{
		myDataBase.open();
		
		ArrayList<Route> routes = new ArrayList<Route>();
		
		routes=myDataBase.getRoutes();
		
		//myDataBase.close();
		
		routeAdapter = new ArrayAdapter<Route>(this, R.layout.text, routes);
		System.out.println(routes.size());
		routeList.setAdapter(routeAdapter);
	}
	
	public void register(View v)
	{
		
		if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			
			if (registerButton.getText()=="Rejestruj Trasê")
			{
				myDataBase.open();
				
				ArrayList<Route> r = myDataBase.getRoutes();
				
				if(r.size()>0)
				{
					registerButton.setText("STOP");
					
					registerRouteId = r.get(r.size()-1).getRouteID();
					
					licznikCzasu=0;
					
					IS_REGISTER=true;
				}
				
				Intent j = new Intent(MapActivity.this, NewRouteActivity.class);
				startActivity(j);
				
			}
			else
			{
				registerButton.setText("Rejestruj Trasê");
				
				IS_REGISTER=false;
				
				licznikCzasu=0;
				
			}
	    }
		else
		{
			
			Toast.makeText( getApplicationContext(),"Wlacz GPS",	Toast.LENGTH_SHORT ).show();
			
			registerButton.setText("Rejestruj Trasê");
			
			IS_REGISTER=false;
			
			licznikCzasu=0;
		}
		
		
		
		
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
		
		//myDataBase.close();
	}
	
	private void updatePlaces()
	{
		//Location lastLoc = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
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
			
			if(licznikCzasu>8 && IS_REGISTER)
			{
				licznikCzasu=0;
				
				if(myDataBase.addCoordinates(registerRouteId+1, lat, lng)==-1)
				{
					Toast.makeText( getApplicationContext(),"Blad zapisu w bazie danych",	Toast.LENGTH_SHORT ).show();
				}
				
			}
			else
			{
				licznikCzasu++;
			}
			
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
		
		if(points.size()>0)
		{
			LatLng lastLatLng = new LatLng(points.get(points.size()-1).getPointLat(),points.get(points.size()-1).getPointLong());
			
			map.addMarker(new MarkerOptions()
	    	.position(lastLatLng)
	    	.title(points.get(points.size()-1).getPointName())
	    	.icon(BitmapDescriptorFactory.fromResource(pointIcon))
	    	.snippet(""));
		
		}
		
		//myDataBase.close();
		
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
