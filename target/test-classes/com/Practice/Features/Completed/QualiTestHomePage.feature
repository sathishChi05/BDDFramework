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
@QualiTestHomePage
Feature: As a user, I want to verify QualiTest Website Title
 
 @QualiTestHomePage
  Scenario Outline: QualiTest - Home Page 
   Given  User opens <Browser>
   And    User navigates to URL with HomePageTitle_Text
   Then   User display TextList from HomePageHeader_xpath  
    |Solutions|Industries|How We Engage|Products|About|Insights|
  
Examples: 

    |Browser|
    |Chrome |
