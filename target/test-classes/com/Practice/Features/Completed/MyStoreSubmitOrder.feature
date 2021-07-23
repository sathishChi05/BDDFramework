#Author: Sathish.subramanian@Qualitestgroup.com
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
@MyStoreSubmitOrder
Feature: As a user, I want to verify MyStore Website Login
 
 @MyStoreSubmitOrder
  Scenario Outline: My Store - Login 
   Given  User opens <Browser>
   And    User navigates to URL1 with MyStoreHomePageTitle_Text
   When   User clicks MyStoreSignIn_xpath
   Then   User should see MyStoreCreateAccountText_xpath from examples <CreateAccountText>
   When   User enters from dataSheet UserName in MyStoreLoginEmail_xpath Textfield
   And    User enters from dataSheet Password in MyStoreLoginPwd_xpath Textfield
   And    User clicks MyStoreAccSubmitButton_xpath
   Then   User should see MyStoreAccLoginPageText_xpath from examples <MyAccountPageText>
   When   User enters from examples <Search> in MyStoreSearchField_xpath
   And    User clicks MyStoreSeachButton_xpath
   Then   User should check <Search> in table MyStoreSearchResult_xpath
   When   User scrollMouseTo MyStoreSearchResult_xpath
   And    User clicksbutton MyStoreAddToCart_xpath
   Then   User should see MyStoreAddToCartSuccess_xpath from examples <AddCartSuccess>
   When   User clickslink MyStoreProceedToCheckOut_xpath
   Then   User should see MyStoreShopCartSummary_xpath from examples <ShopCartSum> 
   
    
Examples: 

    |Browser|HomePageTitle|MyAccountPageText       |CreateAccountText|Search  |AddCartSuccess            |ShopCartSum          |
    |Chrome |My Store     |Welcome to your account.|Create an account|T-Shirts|Product successfully added|Shopping-cart summary|