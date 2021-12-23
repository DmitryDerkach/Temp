package com.epam.entities;

public class FamilyVoucher extends TouristVoucher {
	
	private int numOfFamilyMembers;
	
	public FamilyVoucher() {
		
	}

	public int getNumOfFamilyMembers() {
		return numOfFamilyMembers;
	}

	public void setNumOfFamilyMembers(int numOfFamilyMembers) {
		this.numOfFamilyMembers = numOfFamilyMembers;
	}
	
	@Override
	public String toString() {
		return "FamilyVoucher object: \n" + 
				super.toString() + " \n" + 
			   "numOfFamilyMembers=" + numOfFamilyMembers;
	}
	
}
