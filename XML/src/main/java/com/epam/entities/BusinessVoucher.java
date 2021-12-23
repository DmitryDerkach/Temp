package com.epam.entities;

public class BusinessVoucher extends TouristVoucher{
	
	private int numOfMeetings;
	
	public BusinessVoucher() {
		
	}

	public int getNumOfMeetings() {
		return numOfMeetings;
	}

	public void setNumOfMeetings(int numOfMeetings) {
		this.numOfMeetings = numOfMeetings;
	}

	@Override
	public String toString() {
		return "BusinessVoucher object: \n" + 
				super.toString() + " \n" + 
			   "numOfMeetings=" + numOfMeetings;
	}

	
	
}
