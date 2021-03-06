package com.epam.entities;

import com.epam.entities.associatedclasses.HotelCharacteristics;
import com.epam.entities.associatedclasses.Type;

public class TouristVoucher {
	private int id;
	private Type type;
	private String country;
	private int numberOfDays;
	private String transport;
	private HotelCharacteristics hotelCharacteristics;
	private int cost;
	
	public TouristVoucher() {
	
	}

	public int getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	public String getCountry() {
		return country;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public String getTransport() {
		return transport;
	}

	public HotelCharacteristics getHotelCharacteristics() {
		return hotelCharacteristics;
	}

	public int getCost() {
		return cost;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public void setHotelCharacteristics(HotelCharacteristics hotelCharacteristics) {
		this.hotelCharacteristics = hotelCharacteristics;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return  "id=" + id + ", \n" +
				"type=" + type + ", \n" +
				"country=" + country + ", \n" +
				"numberOfDays=" + numberOfDays + ", \n" +
				"transport=" + transport + ", \n" +
				"hotelCharacteristics: \n" + hotelCharacteristics.toString() + ", \n" +
				"cost=" + cost;
	}
	
	
	
}
