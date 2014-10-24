package com.example.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myways.Point;


public class DatabaseHandler {
	
	private static final int DB_VERSION = 1;
	
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
	public static final String WayMark = "WayMark";
	public static final String StartLat = "StartLat";
	public static final String StartLong = "StartLong";
	public static final String StopLat = "StopLat";
	public static final String StopLong = "StopLong";
	
	private static final String PointCreate = "CREATE TABLE "+
			TPoint+"( "+PointID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +PointName+" TEXT NOT NULL, "+
			PointLat+" REAL NOT NULL, "+PointLong+" REAL NOT NULL,"+ 
			PointDescription+ " TEXT NOT NULL, " +PointMark+" INTEGER); ";
	
	private static final String WayCreate= "CREATE TABLE "+TWay+"( "+WayID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+WayName+" TEXT NOT NULL, "+WayDescription+" TEXT NOT NULL, "+WayMark+" INTEGER NOT NULL, "+
			StartLat+" REAL NOT NULL, "+ StartLong + "REAL NOT NULL, "+ 
			StopLat+" REAL NOT NULL, "+ StopLong + "REAL NOT NULL); ";
	
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
	
	public ArrayList<Point> getPoints()
	{
		ArrayList<Point> zwroc=new ArrayList<Point>();
		
		String query="SELECT PointID, PointName, PointLa, PointLong, PointDescription, PointMark FROM Point;";
		Cursor cursor = mydb.rawQuery(query, null);
		
		if (cursor.moveToFirst()) {
	        do {
	        	Point room = new Point(Integer.parseInt(cursor.getString(0)),cursor.getString(1),Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)),cursor.getString(4),Integer.parseInt(cursor.getString(5)));
	            zwroc.add(room);
	        } while (cursor.moveToNext());
	    }
		
		return zwroc;
	}
	
}

