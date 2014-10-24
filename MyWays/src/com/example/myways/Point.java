package com.example.myways;

public class Point {
	
	private int pointID;
	private String pointName;
	private double pointLat;
	private double pointLong;
	private String pointDescription;
	private int pointMark;
	
	public Point(int id, String name, double lat, double longg, String desc, int mark)
	{
		this.pointID=id;
		this.pointName=name;
		this.pointLat=lat;
		this.pointLong=longg;
		this.pointDescription=desc;
		this.pointMark=mark;
	}
	
	

}
