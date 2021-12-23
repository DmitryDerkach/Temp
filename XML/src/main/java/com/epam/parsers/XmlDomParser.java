package com.epam.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.epam.entities.FamilyVoucher;
import com.epam.entities.TouristVoucher;
import com.epam.entities.associatedclasses.HotelCharacteristics;
import com.epam.entities.associatedclasses.MealType;
import com.epam.entities.associatedclasses.MealsIncluded;
import com.epam.entities.associatedclasses.RoomType;
import com.epam.entities.associatedclasses.Type;
import com.epam.exception.ParserErrorException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.epam.entities.BusinessVoucher;

public class XmlDomParser implements Parser{

	@Override
	public List<TouristVoucher> parse(String filePath) throws ParserErrorException {
		/*Converting XML to DOM Object Model with Error Handling*/
		Document domObject = buildDocument(filePath);
		
		/*Creating list of objects from XML input*/
		List<TouristVoucher> listOfVouchers = new ArrayList<>();
		
		/*Data processing*/
		Node rootNode = domObject.getFirstChild();
		NodeList rootChildNodes = rootNode.getChildNodes();
		for (int xmlDocItterator = 0; xmlDocItterator < rootChildNodes.getLength(); xmlDocItterator++) {
			Node childNode = rootChildNodes.item(xmlDocItterator);
			
			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			
			if (childNode.getNodeName().equals("familyVoucher")) {
				listOfVouchers.add(getInformationAboutFamilyVouchers(childNode));
			
			}
			
			if (childNode.getNodeName().equals("businessVoucher")) {
				listOfVouchers.add(getInformationAboutBusinessVouchers(childNode));
			}
		}
			
		return listOfVouchers;
	}	
	
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
				businessVoucher.setHotelCharacteristics(getHotelCharacteristics(childNode));
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

	private static HotelCharacteristics getHotelCharacteristics(Node hotelCharacteristicsNode) {
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
			
			if (childNodeName.equals("numberOfStars")) {
				hotelCharacteristics.setNumOfStars(Integer.parseInt(childNode.getTextContent()));
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
				mealsIncluded.setAvailable(true);
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

	private static FamilyVoucher getInformationAboutFamilyVouchers(Node familyVoucherNode) {
		
		/*FamilyVoucher object creation*/
		FamilyVoucher familyVoucher = new FamilyVoucher();
		
		/*Processing attributes*/
		NamedNodeMap attributes = familyVoucherNode.getAttributes();
		String idAttribute = attributes.item(0).getTextContent();
		familyVoucher.setId(Integer.parseInt(idAttribute));
		
		/*Processing familyVoucherNode children*/
		NodeList familyVoucherChildren =  familyVoucherNode.getChildNodes();
		for (int i = 0; i < familyVoucherChildren.getLength(); i++) {
			
			/*Variables for simplifying understanding*/
			Node childNode = familyVoucherChildren.item(i);
			String childNodeContent = familyVoucherChildren.item(i).getTextContent();
			String childNodeName = familyVoucherChildren.item(i).getNodeName();
			
			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			
			if (childNodeName.equals("type")) {
				if (childNode.getTextContent().equalsIgnoreCase("BUSINESS")) {
					familyVoucher.setType(Type.BUSINESS);
					continue;
				}
				if (childNode.getTextContent().equalsIgnoreCase("WEEKEND")) {
					familyVoucher.setType(Type.WEEKEND);
				}
			}
			
			if (childNodeName.equals("country")) {
				familyVoucher.setCountry(childNodeContent);
				continue;
			}
			
			if (childNodeName.equals("transport")) {
				familyVoucher.setTransport(childNodeContent);
				continue;
			}
			
			if (childNodeName.equals("numberOfDays")) {
				familyVoucher.setNumberOfDays(Integer.parseInt(childNodeContent));
				continue;
			}
			
			if (childNodeName.equals("hotelCharacteristics")) {
				familyVoucher.setHotelCharacteristics(getHotelCharacteristics(childNode));
				continue;
			}
			
			if (childNodeName.equals("cost")) {
				familyVoucher.setCost(Integer.parseInt(childNodeContent));
				continue;
			}	
			
			if (childNodeName.equals("numOfFamilyMembers")) {
				familyVoucher.setNumOfFamilyMembers((Integer.parseInt(childNodeContent)));
				continue;
			}
		}//for
		return familyVoucher;
		
	}//getInformationAboutFamilyVouchers
	
	private static Document buildDocument(String filePath) {
		File file = new File(filePath);
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
