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
		this.setPointName(name);
		this.setPointLat(lat);
		this.setPointLong(longg);
		this.pointDescription=desc;
		this.pointMark=mark;
	}
	
	@Override
	public String toString() {
		
		return this.pointID +". "+this.pointName+",  "+this.pointMark;
	}

	public double getPointLat() {
		return pointLat;
	}

	public void setPointLat(double pointLat) {
		this.pointLat = pointLat;
	}

	public double getPointLong() {
		return pointLong;
	}

	public void setPointLong(double pointLong) {
		this.pointLong = pointLong;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	

}
