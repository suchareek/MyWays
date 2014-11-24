package com.example.myways;

public class Route {
	
	private int routeID;
	private String routeName;
	private String routeDescription;
	
	public Route(int routeID, String routeName, String routeDescription) {
		super();
		this.routeID = routeID;
		this.routeName = routeName;
		this.routeDescription = routeDescription;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}

}
