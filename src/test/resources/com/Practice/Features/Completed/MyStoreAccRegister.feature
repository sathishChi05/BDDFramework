#Author: Sathishkumar.subramanian@exeloncorp.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@MyStoreAccRegister
Feature: As a user, I want to verify QualiTest Website Title
 
 @MyStoreAccRegister
  Scenario Outline: QualiTest - Home Page 
   Given  User opens <Browser>
   And    User navigates to URL1 with MyStoreHomePageTitle_Text
   When   User clicks MyStoreSignIn_xpath
   Then   User should see MyStoreCreateAccountText_xpath from examples <CreateAccountText>
   When   User enters from examples <Email> in MyStoreCreateAccEmail_xpath
   And    User clicks MyStoreCreateAccButton_xpath
    
  
Examples: 

    |Browser|HomePageTitle|CreateAccountText|Email          |
    |Chrome |My Store     |Create an account|Test45@test.com|
