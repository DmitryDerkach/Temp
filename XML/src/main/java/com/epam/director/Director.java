package com.epam.director;

import com.epam.exception.ParserErrorException;
import com.epam.parsers.Parser;
import com.epam.validator.VouchersValidator;
import java.util.ArrayList;
import java.util.List;
import com.epam.entities.TouristVoucher;

public class Director {
	private Parser parser;
	private VouchersValidator vouchersValidator;

	public Director(Parser parser, VouchersValidator vouchersValidator) {
		this.parser = parser;
		this.vouchersValidator = vouchersValidator;
	}

	public List<TouristVoucher> parseXml(String xmlPath, String xsdPath) throws ParserErrorException {
		List<TouristVoucher> listOfVouchers = new ArrayList<>();
		if (vouchersValidator.isValid(xmlPath, xsdPath)) {
			listOfVouchers = parser.parse(xmlPath);
		}
		return listOfVouchers;
	}
}
