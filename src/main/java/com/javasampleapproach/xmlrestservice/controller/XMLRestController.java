package com.javasampleapproach.xmlrestservice.controller;
import com.javasampleapproach.xmlrestservice.model.Account;
import com.javasampleapproach.xmlrestservice.service.XMLProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class XMLRestController {

	@Autowired
	private XMLProcessing xmlProcessing;

	// This api accepts xml input and appends user's account details to output file
	@PostMapping("/consumer")
	public String postAccountDetails(@RequestBody String xml) throws Exception {
		Map<String, List<Account>> userAccounts = xmlProcessing.parse(xml);
		xmlProcessing.validation(userAccounts);
		xmlProcessing.writeToFile(userAccounts);
		return "Done";
	}
}