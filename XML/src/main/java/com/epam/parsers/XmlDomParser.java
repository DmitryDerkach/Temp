package com.epam.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.epam.entities.FamilyVoucher;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.epam.entities.BusinessVoucher;
import com.epam.entities.associatveclasses.HotelCharacteristics;
import com.epam.entities.associatveclasses.MealType;
import com.epam.entities.associatveclasses.MealsIncluded;
import com.epam.entities.associatveclasses.RoomType;
import com.epam.entities.associatveclasses.Type;

public class XmlDomParser {
	public static void main(String[] args) {
		/*Converting XML to DOM Object Model with Error Handling*/
		Document domObject = buildDocument();
		
		/*Creating list of objects from XML input*/
		List<BusinessVoucher> listOfBusinessVouchers = new ArrayList();
		List<FamilyVoucher> listOfFamilyVouchers = new ArrayList();
		
		/*Data processing*/
		Node rootNode = domObject.getFirstChild();
		NodeList rootChildNodes = rootNode.getChildNodes();
		for (int xmlDocItterator = 0; xmlDocItterator < rootChildNodes.getLength(); xmlDocItterator++) {
			Node childNode = rootChildNodes.item(xmlDocItterator);
			
			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			
			if (childNode.getNodeName().equals("familyVoucher")) {
				listOfFamilyVouchers.add(getInformationAboutFamilyVouchers(childNode));
			
			}
			
			if (childNode.getNodeName().equals("businessVoucher")) {
				listOfBusinessVouchers.add(getInformationAboutBusinessVouchers(childNode));
			}
		}
		System.out.println(listOfBusinessVouchers);
		
	}//main

	private static BusinessVoucher getInformationAboutBusinessVouchers(Node businessVoucherNode) {
		/*BusinessVoucher object creation*/
		BusinessVoucher businessVoucher = new BusinessVoucher();
		
		/*Processing attributes*/
		NamedNodeMap attributes = businessVoucherNode.getAttributes();
		String idAttribute = attributes.item(0).getTextContent();
		businessVoucher.setId(Integer.parseInt(idAttribute));
		
		/*Processing businessVoucherNode children*/
		NodeList businessVoucherChildren =  businessVoucherNode.getChildNodes();
		for (int i = 0; i < businessVoucherChildren.getLength(); i++) {
			
			/*Variables for simplifying understanding*/
			Node childNode = businessVoucherChildren.item(i);
			String childNodeContent = businessVoucherChildren.item(i).getTextContent();
			String childNodeName = businessVoucherChildren.item(i).getNodeName();
			
			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			
			if (childNodeName.equals("type")) {
				if (childNode.getTextContent().equalsIgnoreCase("BUSINESS")) {
					businessVoucher.setType(Type.BUSINESS);
					continue;
				}
				if (childNode.getTextContent().equalsIgnoreCase("WEEKEND")) {
					businessVoucher.setType(Type.WEEKEND);
					continue;
				}
			}
			
			if (childNodeName.equals("country")) {
				businessVoucher.setCountry(childNodeContent);
				continue;
			}
			
			if (childNodeName.equals("transport")) {
				businessVoucher.setTransport(childNodeContent);
				continue;
			}
			
			if (childNodeName.equals("numberOfDays")) {
				businessVoucher.setNumberOfDays(Integer.parseInt(childNodeContent));
				continue;
			}
			
			if (childNodeName.equals("hotelCharacteristics")) {
				businessVoucher.setHotelCharacteristics(getHotelCharacteristics(childNode, businessVoucher));
				continue;
			}
			
			if (childNodeName.equals("cost")) {
				businessVoucher.setCost(Integer.parseInt(childNodeContent));
				continue;
			}	
			
			if (childNodeName.equals("numOfMeetings")) {
				businessVoucher.setNumOfMeetings(Integer.parseInt(childNodeContent));
				continue;
			}
			
		}
		return businessVoucher;
		
	}//getInformationAboutBusinessVouchers

	private static HotelCharacteristics getHotelCharacteristics(Node hotelCharacteristicsNode, BusinessVoucher businessVoucher) {
		/*HotelCharacteristics class creation*/
		HotelCharacteristics hotelCharacteristics = new HotelCharacteristics();
		
		/*Processing hotelCharacteristicsNode children*/
		NodeList hotelCharacteristicsChildren =  hotelCharacteristicsNode.getChildNodes();
		for (int i = 0; i < hotelCharacteristicsChildren.getLength(); i++) {
			
			/*Variables for simplifying understanding*/
			Node childNode = hotelCharacteristicsChildren.item(i);
			String childNodeContent = hotelCharacteristicsChildren.item(i).getTextContent();
			String childNodeName = hotelCharacteristicsChildren.item(i).getNodeName();
			
			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			
			if (childNodeName.equals("numOfStars")) {
				hotelCharacteristics.setNumOfStars(Integer.parseInt(hotelCharacteristicsChildren.item(i).getTextContent()));
				continue;
			}	
			
			if (childNodeName.equals("mealsIncluded")) {
				hotelCharacteristics.setMealsIncluded(getInformationAboutMeals(childNode));
				continue;
			}	
						
			if (childNodeName.equals("roomType")) {
				if (childNodeContent.equalsIgnoreCase("Single")) {
					hotelCharacteristics.setRoomType(RoomType.SINGLE);
					continue;
				}
				
				if (childNodeContent.equalsIgnoreCase("Double")) {
					hotelCharacteristics.setRoomType(RoomType.DOUBLE);
					continue;
				}
				
				if (childNodeContent.equalsIgnoreCase("Triple")) {
					hotelCharacteristics.setRoomType(RoomType.TRIPLE);
					continue;
				}
			}
		}//for
		return hotelCharacteristics;
		
	}//getHotelCharacteristics
	
	private static MealsIncluded getInformationAboutMeals(Node hotelCharacteristicsChild) {
		
			/*MealsIncluded class creation*/
			MealsIncluded mealsIncluded = new MealsIncluded();
			
			/*Processing attributes*/
			NamedNodeMap attributes = hotelCharacteristicsChild.getAttributes();
			String trueAttribute = attributes.item(0).getTextContent();
			
			if(trueAttribute.equals("true")) {
				boolean avaiable = Boolean.parseBoolean(trueAttribute);
				mealsIncluded.setAvailable(avaiable);
				NodeList mealTypeNodesChildren = hotelCharacteristicsChild.getChildNodes();
				for (int i = 0; i < mealTypeNodesChildren.getLength(); i++) {
					
					/*Variables for simplifying understanding*/
					Node childNode = mealTypeNodesChildren.item(i);
					String childNodeContent = childNode.getTextContent();
					String childNodeName = childNode.getNodeName();
					
					if (childNode.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					
					if (childNodeName.equals("mealType")) {
						if (childNodeContent.equals("AI")) {
							mealsIncluded.setMealType(MealType.AI);
							continue;
						}
						if (childNodeContent.equals("BB")) {
							mealsIncluded.setMealType(MealType.BB);
							continue;
						}
						if (childNodeContent.equals("HB")) {
							mealsIncluded.setMealType(MealType.HB);
							continue;
						}
					}
				}
			}
		return mealsIncluded;
		
	}//getInformationAboutMeals

	private static FamilyVoucher getInformationAboutFamilyVouchers(Node familyVoucher) {
		NodeList familyVoucherChildren =  familyVoucher.getChildNodes();
		for (int i = 0; i < familyVoucherChildren.getLength(); i++) {
			if (familyVoucherChildren.item(i).getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
		}
		return new FamilyVoucher();
	}

	private static Document buildDocument() {
		File file = new File("src/main/resources/vouchers.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			document = dbf.newDocumentBuilder().parse(file);
		} catch (Exception e) {
			System.out.println("Document Parsing Error");
			e.printStackTrace();
			System.exit(0);
		}
		return document; 
	}	
}
