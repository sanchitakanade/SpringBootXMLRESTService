package com.javasampleapproach.xmlrestservice.controller;
import com.javasampleapproach.xmlrestservice.model.Account;
import com.javasampleapproach.xmlrestservice.model.Description;
import com.javasampleapproach.xmlrestservice.service.XMLProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@RestController
public class XMLRestController {

	@Autowired
	private XMLProcessing xmlProcessing;

	// This api accepts xml input and returns a string that we need to append to output file
	@PostMapping("/consumer")
	public String postAccountDetails(@RequestBody String xml) {
		Map<String, List<Account>> userAccounts = xmlProcessing.parse(xml);

		StringBuilder accountDetails = new StringBuilder();

		//retrieving all user accounts from map
		Collection<List<Account>> lists = userAccounts.values();

		for(List<Account> list: lists) {
			for(Account account: list) {
				Description description = account.createDescription();
				accountDetails.append(description.toString() +", amount: "+ account.getAmount() +"\n");
			}
		}
		return accountDetails.toString();
	}
}