$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("C:/Automation/BDDFramework/src/test/resources/com/Practice/Features/InProgress/MyStoreProductSearch.feature");
formatter.feature({
  "name": "As a user, I want to verify MyStore Website Login",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@MyStoreProductSearch"
    }
  ]
});
formatter.scenarioOutline({
  "name": "My Store - Login",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@MyStoreProductSearch"
    }
  ]
});
formatter.step({
  "name": "User opens \u003cBrowser\u003e",
  "keyword": "Given "
});
formatter.step({
  "name": "User navigates to URL1 with MyStoreHomePageTitle_Text",
  "keyword": "And "
});
formatter.step({
  "name": "User clicks MyStoreSignIn_xpath",
  "keyword": "When "
});
formatter.step({
  "name": "User should see MyStoreCreateAccountText_xpath from examples \u003cCreateAccountText\u003e",
  "keyword": "Then "
});
formatter.step({
  "name": "User enters from dataSheet UserName in MyStoreLoginEmail_xpath Textfield",
  "keyword": "When "
});
formatter.step({
  "name": "User enters from dataSheet Password in MyStoreLoginPwd_xpath Textfield",
  "keyword": "And "
});
formatter.step({
  "name": "User clicks MyStoreAccSubmitButton_xpath",
  "keyword": "And "
});
formatter.step({
  "name": "User should see MyStoreAccLoginPageText_xpath from examples \u003cMyAccountPageText\u003e",
  "keyword": "Then "
});
formatter.step({
  "name": "User enters from examples \u003cSearch\u003e in MyStoreSearchField_xpath",
  "keyword": "When "
});
formatter.step({
  "name": "User clicks MyStoreSeachButton_xpath",
  "keyword": "And "
});
formatter.step({
  "name": "User should check \u003cSearch\u003e in table MyStoreSearchResult_xpath",
  "keyword": "Then "
});
formatter.step({
  "name": "User scrollMouseTo MyStoreSearchResult_xpath",
  "keyword": "When "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "Browser",
        "HomePageTitle",
        "MyAccountPageText",
        "CreateAccountText",
        "Search"
      ]
    },
    {
      "cells": [
        "Chrome",
        "My Store",
        "Welcome to your account.",
        "Create an account",
        "Blouse"
      ]
    }
  ]
});
formatter.scenario({
  "name": "My Store - Login",
  "description": "",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "name": "@MyStoreProductSearch"
    },
    {
      "name": "@MyStoreProductSearch"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User opens Chrome",
  "keyword": "Given "
});
formatter.match({
  "location": "GenericSteps.openBrowser(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User navigates to URL1 with MyStoreHomePageTitle_Text",
  "keyword": "And "
});
formatter.match({
  "location": "GenericSteps.navigate(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User clicks MyStoreSignIn_xpath",
  "keyword": "When "
});
formatter.match({
  "location": "GenericSteps.clickButton(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User should see MyStoreCreateAccountText_xpath from examples Create an account",
  "keyword": "Then "
});
formatter.match({
  "location": "GenericSteps.verifyTextfromExamples(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User enters from dataSheet UserName in MyStoreLoginEmail_xpath Textfield",
  "keyword": "When "
});
formatter.match({
  "location": "GenericSteps.enterTextFromDataSheet(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User enters from dataSheet Password in MyStoreLoginPwd_xpath Textfield",
  "keyword": "And "
});
formatter.match({
  "location": "GenericSteps.enterTextFromDataSheet(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User clicks MyStoreAccSubmitButton_xpath",
  "keyword": "And "
});
formatter.match({
  "location": "GenericSteps.clickButton(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User should see MyStoreAccLoginPageText_xpath from examples Welcome to your account.",
  "keyword": "Then "
});
formatter.match({
  "location": "GenericSteps.verifyTextfromExamples(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User enters from examples Blouse in MyStoreSearchField_xpath",
  "keyword": "When "
});
formatter.match({
  "location": "GenericSteps.enterTextFromExamples(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User clicks MyStoreSeachButton_xpath",
  "keyword": "And "
});
formatter.match({
  "location": "GenericSteps.clickButton(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User should check Blouse in table MyStoreSearchResult_xpath",
  "keyword": "Then "
});
formatter.match({
  "location": "GenericSteps.verifyFromTable(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User scrollMouseTo MyStoreSearchResult_xpath",
  "keyword": "When "
});
formatter.match({
  "location": "GenericSteps.scrollMouseTo(String)"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});