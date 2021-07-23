package com.UIQ.stepsdefinitions;

import java.util.List;

import com.UIQ.WebConnector.WebConnector;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GenericSteps {

	WebConnector con;
	
	
	public GenericSteps(WebConnector con) {
	
		this.con = con;
	}
	
	@Before
	public void Before(Scenario s) {
		
		con.initReports(s.getName());
		
	}
	
	
	@After
	public void After(Scenario s) {
		
		con.quit(s.getName());
		con.copyReport();
	}
	
	
	@Given("^User opens (.*)$")
	public void openBrowser(String Browser) {
				con.openBrowser(Browser);
	}
	
	
	@And("^User navigates to (.*) with (.*)$")
	public void navigate(String url, String titleKey){
		
		con.navigate(url, titleKey);
		
	}
	
	@When ("^User enters (.*) in (.*) field$")
	public void enterText(String text, String locatorkey) {
		
	   con.enterText(text,locatorkey);
		
	}
	
	@When ("^User enters from dataSheet (.*) in (.*) Textfield$")
	public void enterTextFromDataSheet(String text, String locatorkey) {
		
	   con.enterTextFromDataSheet(text,locatorkey);
		
	}
	
	
	@When("^User enters from examples (.*) in (.*)$")
	public void enterTextFromExamples(String textKey, String locatorKey) {
	    
		con.enterTextFromExamples(textKey,locatorKey );
			
	}
	
	@When("User enters from unique examples (.*) in (.*)")
	public void entersUniqueTextFromExamples(String nameKey, String locatorKey) {
	    
		con.entersUniqueTextFromExamples(nameKey,locatorKey);
	}
		
	@When("^User clicks (.*)$")
	public void clickButton(String locatorkey) throws Throwable {
		
		//System.out.println("Click "+locatorkey);
		
		con.click(locatorkey);
	}
	
	@When("^User scrollMouseTo (.*)$")
    public void  scrollMouseTo(String locatorKey) {
	
		con.scrollMouseTo(locatorKey);
	
	}
	
	@When("User hits (.*) Key in (.*)")
	public void userHitsKeyBoardKey(String keyName, String locatorKey) {
	   
		con.userHitsKeyBoardKey(keyName, locatorKey);	
		
	}
	
	@When("^User selects (.*) from dropDown (.*)$")
	public void dropDown(String dropDownValue, String locatorKey) {
		System.out.println(dropDownValue + " and " + locatorKey);
		
		con.dropDown(dropDownValue,locatorKey);
				
	}
	
	@When("^User selectPages (.*) from dropDown (.*)$")
	public void pagesDropDown(String dropDownValue, String locatorKey) {
		//System.out.println(dropDownValue + " and " + locatorKey);
		
		con.pagesDropDown(dropDownValue,locatorKey);
				
	}
	
	@When("^User select dataSheet value (.*) from dropDown (.*)$")
	public void dropDownfromDataSheet(String dropDownValueKey, String locatorKey) {
		System.out.println(dropDownValueKey + " and " + locatorKey);
		
		con.dropDownfromDataSheet(dropDownValueKey,locatorKey);
				
	}
	
	@When("^User selects (.*) from OptionList (.*)$")
    public void selectFromOptionList(String value, String locatorKey) throws Throwable {
	    
		con.selectFromOptionList(value, locatorKey);
		
	}
	
	@When("User selects (.*) from OptionListtext (.*)")
	public void selectTextFromOptionList(String value, String locatorKey) throws Throwable {
		con.selectTextFromOptionList(value, locatorKey);
		
	}
			
	@When("^User scrolls PageDown by (.*)$")
	public void scrollPagedownBy(String value) {
		
		con.scrollPageDownBy(value);
	}
	
	@When("^User scrolls PageUp by (.*)$")
	public void scrollPageUp(String value) {
		
		con.scrollPageUp(value);
	}
	
		
	@When("^User accepts Alert$")
	public void acceptsAlert() throws Throwable {
		
		con.acceptsAlert();
		
	}
	
	@When("^User updates text in (.*) field$")
	public void updateTextField(String locatorKey) {
	    
		con.updateTextField(locatorKey);
	}
	
	@When("^User clears textbox (.*)$")
	public void clearTextBox(String locatorKey) {
	    
		con.clearTextBox(locatorKey);
		
	}
		
	
	@When("^User Switches to IFrame (.*)$")
	public void switchesToIFrame(String locatorKey) {
	    
		con.switchesToIFrame(locatorKey);
		
	}
	
	@When("User waits for PageLoad (.*)")
	public void waitForPageLoad(String locatorKey) {
	    con.waitForPageLoad(locatorKey);
	}
	
	@When("^User switchTo MainTab$")
	public void switchToMainTab() {
	    con.switchToMainTab();
	}
	
	@When("^User getCurrentURL and Open in NewTab$")
	public void getCurrentURLOpeninNewTab() {
	   con.getCurrentURLOpeninNewTab();
	}
	
	@When("^User uploads file in (.*)$")
	public void uploadFile(String locatorKey) {
	    
		con.uploadFile(locatorKey);
		
	}
	
		
	
	@Then("^User logged into UIQ (.*)$")
	public void verifyTitle(String expectedTitle) {
		con.verifyTitle(expectedTitle);
	}
	
	@Then("^User should see (.*) screen (.*)$")
	public void verifyText(String locatorkey, String expectedTextKey) {
	    
		con.verifyText(locatorkey, expectedTextKey);
	}
	
	@Then("^User should see (.*) from examples (.*)$")
	public void verifyTextfromExamples(String locatorKey, String textKey) throws Throwable {
	    
		con.verifyTextfromExamples(locatorKey, textKey);
		
	}
	
	@Then("User display TextList from (.*)")
	public void displayTextList(String locatorKey, List<String> data) {
	    
		con.displayTextList(locatorKey, data);
		
	}
	
//	@Then("User should check examples (.*) in table (.*)")
//	public void verifyCreatedNameInWebTable(String data, String locatorKey) {
	    
//		con.verifyCreatedNameInWebTable( data,  locatorKey);
		
//	}
	
	@Then("^User should see (.*) from dataSheet (.*)$")
	public void verifyTextfromDataSheet(String locatorKey, String textKey) throws Throwable {
	    
		con.verifyTextfromDataSheet(locatorKey, textKey);
		
	}
	
	@Then("^User should check (.*) in table (.*)$")
	public void verifyFromTable(String statusKey, String locatorKey) {
	   
		con.verifyFromTable(statusKey,locatorKey);
	}
	
	@Then("User should refresh and see (.*) from examples (.*)")
	public void verifyRunStatus(String locatorKey ,String status ) {
		System.out.println(status +" , " + locatorKey );
		con.verifyRunStatus(locatorKey, status);
				
	}

	
	@Then("^User should validate dataSheet value (.*) in table (.*)$")
	public void verifyDataSheetValueFromTable(String dataKey, String locatorKey) {
	   
		con.verifyDataSheetValueFromTable(dataKey,locatorKey);
	}
	
	@Then("^User should check (.*) in dataSheet (.*)$")
	public void verifyFromDataSheet(String status, String locatorKey) {
	   
		con.verifyFromDataSheet(status,locatorKey);
	}
	
	

	@Then("User clickPagination (.*)")
	public void clickPagination(String locatorKey) {
	    
		con.clickPagination(locatorKey);
		
	}
	
	

	@Then("^User should see popupWindow$")
	public void switchToPopup() {
		
		con.switchToPopup();
				
	}
	
	
	
	
	@Then("^User should switchTo newTab (.*) with Text (.*)$")
	public void switchToNewTab(String locatorKey, String textKey) {
	    
		con.switchToNewTab(locatorKey,textKey );
		
	}
	
	@Then("^User should see NotNull values in (.*)$")
	public void verifyNotNullValues(String locatorKey) {
		con.verifyNotNullValues(locatorKey);
	}
	
	@Then("^User validate the (.*)$")
	public void verifyDateSync(String locatorKey ) throws Throwable {
	    con.verifyDateSync(locatorKey);
		
	}
	/*
	@When("^User clicksbutton (.*)$")
	public void clickButtons(String locatorKey) {
	    // Write code here that turns the phrase above into concrete actions
	    con.click(locatorKey);
	}

	@When("^User clickslink (.*)$")
	public void clickLinks(String locatorKey) {
	    con.click(locatorKey);
	}
*/

	
	@When("^User clickButton (.*)$")
	public void clickButton1(String locatorKey) {
	    
		con.click(locatorKey);
		
	}
	
	
	
	
}
