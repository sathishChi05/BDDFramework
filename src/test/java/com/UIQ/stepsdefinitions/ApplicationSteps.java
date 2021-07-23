package com.UIQ.stepsdefinitions;
// passes object of webconnecter in both classes. Same object
// same object for 1 scenario
import com.UIQ.WebConnector.WebConnector;

public class ApplicationSteps{
	
	WebConnector con;
	
	public ApplicationSteps(WebConnector con) {
		this.con=con;
	}
	
	
	
}
