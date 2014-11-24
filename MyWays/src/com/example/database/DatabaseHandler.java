package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myways.Point;
import com.example.myways.Route;


public class DatabaseHandler {
	
	private static final int DB_VERSION = 2;
	
	private static final String dbName = "DBways.db";
	
	private static final String TPoint = "Point";
	public static final String PointID = "PointID";
	public static final String PointName = "PointName";
	public static final String PointLat = "PointLat";
	public static final String PointLong = "PointLong";
	public static final String PointDescription = "PointDescription";
	public static final String PointMark = "PointMark";
	
	private static final String TWay = "Way";
	public static final String WayID = "WayID";
	public static final String WayName = "WayName";
	public static final String WayDescription = "WayDescription";
	
	public static final String TCoordinates = "Coordinates";
	public static final String CoorID = "CoordinateID";
	public static final String CWayID = "WayID";
	public static final String CLat = "Latitude";
	public static final String CLong = "Longitude";
	
	private static final String PointCreate = "CREATE TABLE "+
			TPoint+"( "+PointID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +PointName+" TEXT NOT NULL, "+
			PointLat+" REAL NOT NULL, "+PointLong+" REAL NOT NULL,"+ 
			PointDescription+ " TEXT NOT NULL, " +PointMark+" INTEGER); ";
	
	private static final String WayCreate= "CREATE TABLE "+TWay+"( "+WayID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+WayName+" TEXT NOT NULL, "+WayDescription+" TEXT NOT NULL); ";
	
	private static final String CoordinateCreate = "CREATE TABLE "+
			TCoordinates+"( "+CoorID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +CWayID+" INT NOT NULL, "+
			CLat+" REAL NOT NULL, "+CLong+" REAL NOT NULL," +
			" FOREIGN KEY("+CWayID+") REFERENCES "+TWay+" ("+WayID+"));";
	
	private SQLiteDatabase mydb;
	private final Context myContext;
	private DatabaseHelper myDatabaseHelper;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{

	    public DatabaseHelper(Context context, String name,
	            CursorFactory factory, int version) {
	        super(context, name, factory, version);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        
	    	db.execSQL(PointCreate);
	        db.execSQL(WayCreate);   
	        db.execSQL(CoordinateCreate);
	        
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
	    	
	        db.execSQL("DROP TABLE IF EXISTS " + TPoint);
	        db.execSQL("DROP TABLE IF EXISTS " + TWay);
	        onCreate(db);
	        
	        Log.w("ListView DatabaseAdapter","Aktualizacja bazy z wersji " + oldVer +
	                " do " + newVer + 
	                ". Wszystkie dane zostan¹ utracone.");
	    }

	}
	
	public DatabaseHandler(Context context)
	{
		this.myContext=context;
	}
	
	public DatabaseHandler open() throws SQLException
	{
		
		myDatabaseHelper=new DatabaseHelper(myContext, dbName, null, DB_VERSION);
		try
		{
			mydb=myDatabaseHelper.getWritableDatabase();
		}
		catch (SQLException e)
		{
			mydb=myDatabaseHelper.getReadableDatabase();
		}
		
		return this;
	}
	
	public void close() 
	{
		myDatabaseHelper.close();
	}
	
	public ArrayList<Route> getRoutes()
	{
		ArrayList<Route> zwroc=new ArrayList<Route>();
		
		String query="SELECT WayID, WayName, WayDescription FROM Way;";
		Cursor cursor = mydb.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
	        do {
	        	Route route = new Route(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
	            zwroc.add(route);
	        } while (cursor.moveToNext());
	    }
		
		return zwroc;
	}
	
	public ArrayList<Point> getCoordinates(int id)
	{
		ArrayList<Point> zwroc = new ArrayList<Point>();
		
		String query="SELECT Latitude, Longitude FROM Coordinates WHERE WayID = "+id+";";
		Cursor cursor = mydb.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
	        do {
	        	Point point = new Point(Double.parseDouble(cursor.getString(0)), Double.parseDouble(cursor.getString(1)));
	            zwroc.add(point);
	        } while (cursor.moveToNext());
	    }
		
		return zwroc;
	}
	
	public ArrayList<Point> getPoints()
	{
		ArrayList<Point> zwroc=new ArrayList<Point>();
		
		String query="SELECT PointID, PointName, PointLat, PointLong, PointDescription, PointMark FROM Point;";
		Cursor cursor = mydb.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
	        do {
	        	Point point = new Point(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)),cursor.getString(4),Integer.parseInt(cursor.getString(5)));
	            zwroc.add(point);
	        } while (cursor.moveToNext());
	    }
		
		return zwroc;
	}
	
	public long createPoint(String name, String desc, double lat, double lng, int mark)
	{
		ContentValues values = new ContentValues();
        values.put(PointName, name);
        values.put(PointLat, lat);
        values.put(PointLong, lng);
        values.put(PointDescription, desc);
        values.put(PointMark, mark);
        
        return mydb.insert(TPoint, null, values);
	}
	
}

