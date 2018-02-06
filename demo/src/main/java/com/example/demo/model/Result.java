package com.example.demo.model;

import java.util.List;

public class Result {
	private Long time;
	private int pollingInterval;
	private List<Area> areas;
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public int getPollingInterval() {
		return pollingInterval;
	}
	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
	
}
