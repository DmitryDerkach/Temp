package com.epam.entities.associatedclasses;

public class MealsIncluded {
	private boolean available;
	private MealType mealType;
	
	public MealsIncluded(boolean available, MealType mealType) {
		this.available = available;
		this.mealType = mealType;
	}
	
	public MealsIncluded() {
		
	}

	public boolean isAvailable() {
		return available;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	@Override
	public String toString() {
		return "		[MealsIncludedData: \n" +
			   "		available=" + available + ", \n" + 
			   "		mealType=" + mealType + "]";
	} 
	
	
}
