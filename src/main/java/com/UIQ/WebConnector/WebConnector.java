package com.UIQ.WebConnector;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Document;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.UIQ.Report.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class WebConnector {
	
	
	WebDriver driver ;
	public Properties prop;
	public ExtentReports rep;
	public ExtentTest scenario;
	public JSONObject dataObject;
	public String Environment;
	public String NCCreateThresholdJobName;
	public String JobId;
	public String MeterAlreadyAvailable="False";
	public ArrayList<String> Meter210List = new ArrayList<String>();
	public ArrayList<String> MeterKvList = new ArrayList<String>();
	
/****Webconnector initializes the Properties and Jason Data file****/
	
	public WebConnector(){
		
		try {
		if (prop==null) {
			prop = new Properties();
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties");
			prop.load(fs);
					}
		
		Environment = prop.getProperty("Env");
		
/*Selecting the environment. Environment variable is set in Project.properties file*/
		
		System.out.println("Environment is "+ Environment);
		String dataFile = null;
		
		if (Environment.equalsIgnoreCase("Dev")){
			dataFile = System.getProperty("user.dir")+"\\src\\test\\resources\\data.json";
				
		}else if (Environment.equalsIgnoreCase("Test")){
			dataFile = System.getProperty("user.dir")+"\\src\\test\\resources\\testEnvData.json";
			
		}else if (Environment.equalsIgnoreCase("Prod")){
				dataFile = System.getProperty("user.dir")+"\\src\\test\\resources\\prodEnvData.json";
				
		}else if (Environment.equalsIgnoreCase("SandBox")){
			dataFile = System.getProperty("user.dir")+"\\src\\test\\resources\\sandboxEnvData.json";
	}else {
		
		System.out.println("File not found");
		reportFailure("File not found for " + Environment);
	}
		
		
/*Initializing the Jason data file*/	

		if (dataObject==null) {
			
			StringBuffer buffer =new StringBuffer();
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(dataFile));
			while ((line =reader.readLine())!=null ) {
				
				if(line.trim().length()!=0) {
					
					buffer.append(line);
					
				}
			}
			
			reader.close();
			dataObject = new JSONObject(buffer.toString());
		}
	
		
		/*String DeviceGroup = (String) dataObject.get("DeviceGroup");
		if(DeviceGroup==null||DeviceGroup.trim().length()==0) {
			
		//enterText(, prop.getProperty(username_xpath));	
			
		}
		
		else {
			openBrowser("chrome");
			navigate("URL", "LoginPageTitle_Text");
			enterTextFromDataSheet("username_Text", "username_xpath");
			enterTextFromDataSheet("password_Text", "password_xpath");
			click("loginSubmit_xpath");
			click("AMMLink_xpath");
			scrollMouseTo("DevicesTab_xpath");
			click("deviceSubGroups_xpath");
			click("AMMImportDeviceGroup_xpath");
			String Meter210Group = (String)dataObject.get("Meter210Group");
			//String Meter210Group1 = "210c_Fw5.0";
			enterText(Meter210Group, "AMMImportDeviceGroupName_xpath");	
			dropDown("DID_TYPE_METER", "AMMImportDeviceGroupDevType_xpath");
			String FilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\MeterGroup210C_AMM_MACID.txt";
			getElement("AMMImportDeviceListFile_xpath").sendKeys(FilePath);
			click("AMMImportDeviceGroupButton_xpath");
			
			if (MeterAlreadyAvailable=="False"){
			MeterAlreadyAvailable = "True";
			}
			
		}*/
		
		
		
		
		} catch (Exception e){
		reportFailure("Application or Code Error > " + e.getMessage());
		takeScreenShot("Error ->" + e.getMessage() );
		
	}
		}
	
	
public void writingInJsonFile(String dataFile ) throws IOException {

	Iterator<String> keys = dataObject.keys();
	while(keys.hasNext()) {
		String key = keys.next();
		
		if(key.startsWith("Meter210_")){
			Meter210List.add(key);
			}
		
       if(key.startsWith("MeterKv_")){
			MeterKvList.add(key);
			}
			
	}
	
	String Meter210Group1 = "Meter210";
	String MeterKvGroup1 = "MeterKv";
	
		
		
		dataObject.put("DeviceGroup", Meter210Group1);
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dataFile), false));
				
			writer.write(dataObject.toString());
			writer.close();
								
	} 
		


/*Method to identify the WebElement using xpath
 *locatorKey is Xpath of the element*/		

	public WebElement getElement(String locatorkey) {
		WebElement e = null;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		
		try {
		if(locatorkey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(prop.getProperty(locatorkey)));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorkey))));
		} else if (locatorkey.endsWith("_id")) {
				e = driver.findElement(By.id(prop.getProperty(locatorkey)));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorkey))));
		} else if (locatorkey.endsWith("_name")) {
				e = driver.findElement(By.name((prop.getProperty(locatorkey))));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorkey))));
		} else if (locatorkey.endsWith("_css")) {
					e = driver.findElement(By.cssSelector((prop.getProperty(locatorkey))));
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorkey))));
		}else if (locatorkey.endsWith("_css")) {
			e = driver.findElement(By.cssSelector((prop.getProperty(locatorkey))));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorkey))));
       }					
		}
		
		catch (Exception ex) {
			ex.printStackTrace();
			reportFailure(locatorkey + " is not found");
		}
		return e;
	}


/////////////////////////////////////////////////////@Given Functions////////////////////////////////////////////////////////////

/*Method to Open the Browser based on the name given in feature file
 *bName is browser from feature file*/

	public void openBrowser(String bName) {
	try {
		String projectPath = System.getProperty("user.dir");
				
		if (bName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", projectPath+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(bName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", projectPath+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(bName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", projectPath+"\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}else {
			reportFailure(bName + " is not a valid browser name");
		}
	driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	driver.manage().window().maximize();
	reportPass( "Opening "+bName+" was successfull");
	
	}
	
	catch (Exception e){
		reportFailure("Application or Code Error > " + e.getMessage());
		takeScreenShot("Error ->" + e.getMessage() );
		
	}
    }
	
		
/*Method to navigate to URL mentioned in Jason data file
 *urlKey is Key from JSon data file
 *titleKey is the pagetitle text to validate*/	

	public void navigate(String urlKey, String titleKey) {
	
	try {
		String URL = (String)dataObject.get(urlKey);
		driver.get(URL);
		verifyTitle(titleKey);
		reportPass( "Navigate to "+urlKey+" environment was successfull");
	}
	catch (Exception e){
		reportFailure("Application or Code Error > " + e.getMessage());
		takeScreenShot("Error ->" + e.getMessage() );
		
	}
		
	}

	
		
/////////////////////////////////////////////////////@When Functions////////////////////////////////////////////////////////////

////////From Generic Steps class file ///////////////
	
/*Method to enter text in any text field using element xpath and Text to enter. 
 *locatorKey is Xpath of the element
 *text is text to enter*/
	
	public void enterText(String text, String locatorKey) {
	
	try {
		getElement(locatorKey).sendKeys(prop.getProperty(text));
	}
	catch (Exception e){
		reportFailure("Application or Code Error > " + e.getMessage());
		takeScreenShot("Error ->" + e.getMessage() );
	}
	}

/*Method to enter text in any text field using element xpath and Text from Json data file.
 *locatorKey is Xpath of the element
 *textKey is Key from datafile to enter*/
	
	public void enterTextFromDataSheet(String textKey, String locatorKey) {

		try {	
		String text = (String)dataObject.get(textKey);
		getElement(locatorKey).sendKeys(text);
		}
		catch (Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		}
		}

/*Method to enter text in any text field using element xpath and Text from feature file.
 *locatorKey is Xpath of the element
 *text is from feature file to enter*/
	
	public void enterTextFromExamples(String text, String locatorKey) {
		try {
		//String text = (String)dataObject.get(textKey);
		getElement(locatorKey).sendKeys(text);
		}
		catch (Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		}
	}

/*Method to enter text in any text field using element xpath and Text to enter. 
 *locatorKey is Xpath of the element
 *text is text to enter*/
		
		public void uploadFile(String locatorKey) {
		
		try {
			
			String FilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\ImportDevice.txt";
			
			getElement(locatorKey).sendKeys(FilePath);
		}
		catch (Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		}
		}
/*Method to click element on the screen.
 *locatorKey is Xpath of the element*/
	
	public void click(String locatorKey) {
		try {
		WebElement element = getElement(locatorKey);
					
		//WebDriverWait wait =   new WebDriverWait(driver, 30);
		//wait.until(ExpectedConditions.visibilityOf(element));
		element.click();
		Thread.sleep(1000);
		
		}
		catch(Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		}		
	}
	
/*Method to click element on the screen.
 *locatorKey is Xpath of the element*/	

	public void clickPagination(String locatorKey) {
						
		try {
			WebElement element = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			
			if (element!=null) {
         // if (locatorKey.equals("AMMDeviceSearchResultPerPageImg_xpath")) {
        	element.click();
			
		}
		}catch (Exception ignored){ }
			
			
	}
	
	
	/*Method to switch the driver control to popup on the screen
	 *@param LocatorKey is Xpath of the popup window title text */ 	

		public void switchToPopup(String locatorKey) {
			try {
			String MainWindow=driver.getWindowHandle();		
			
	        // To handle all new opened window.				
	        Set<String> s1=driver.getWindowHandles();		
	        Iterator<String> i1=s1.iterator();		
	        while(i1.hasNext())			
	        {		
	            String ChildWindow=i1.next();		
	       // Switch to child window or popup     		
	            if(!MainWindow.equalsIgnoreCase(ChildWindow))			
	            {    		
	                driver.switchTo().window(ChildWindow);
	                verifyTitle(locatorKey);                			
	             }		
	        }	
			}
			catch(Exception e){
	   			reportFailure("Application or Code Error > " + e.getMessage());
	   			takeScreenShot("Error ->" + e.getMessage() );
	   		 }
			
	        }
		
	/*Method to switch the driver control to popup on the screen*/	

		public void switchToPopup() {
			try {
			String MainWindow=driver.getWindowHandle();		
			
	        // To handle all new opened window.				
	            Set<String> s1=driver.getWindowHandles();		
	        Iterator<String> i1=s1.iterator();		
	        		
	        while(i1.hasNext())			
	        {		
	            String ChildWindow=i1.next();
	            
	         // Switch to child window or popup 
	            if(!MainWindow.equalsIgnoreCase(ChildWindow))			
	            {    		
	                driver.switchTo().window(ChildWindow); 
	                                       			
	                    }		
	        }		
			}
			catch(Exception e){
	   			reportFailure("Application or Code Error > " + e.getMessage());
	   			takeScreenShot("Error ->" + e.getMessage() );
	   		 }
	        }
		
	/*Method to move the mouse control over the element under test
	 *@param LocatorKey is Xpath of the element*/
		
		public void scrollMouseTo(String locatorKey) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(getElement(locatorKey)).build().perform();
		}
		catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
			}

/*Method to select the drop down value from the screen
 *@param dropDownValue value to be selected and value is from feature file  
 *@param LocatorKey is Xpath of the dropdown*/

		public void dropDown(String dropDownValue, String locatorKey) {
		try {
			WebElement testDropDown = getElement(locatorKey);  
			Select dropdown = new Select(testDropDown);  
			dropdown.selectByValue(dropDownValue);
		}
		catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
		}

/*Method to select the drop down value from the screen
*@param dropDownValue value to be selected and value is from feature file  
*@param LocatorKey is Xpath of the dropdown*/

	public void pagesDropDown(String dropDownValue, String locatorKey) {
		try {
				//WebElement testDropDown = getElement(locatorKey);  
				WebElement testDropDown = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				if (testDropDown!=null) {
				Select dropdown = new Select(testDropDown);  
				dropdown.selectByValue(dropDownValue);
				}
				} catch (Exception ignored){ }
			}

		
	/*Method to select the drop down value from the screen
	 *@param dropDownValueKey value to be selected and valueKey is from JSon Data file  
	 *@param LocatorKey is Xpath of the dropdown*/	

	  public void dropDownfromDataSheet(String dropDownValueKey, String locatorKey) {
		  String dropDownValue = (String)dataObject.get(dropDownValueKey);
		  WebElement testDropDown = getElement(locatorKey);  
		  Select dropdown = new Select(testDropDown); 
		  dropdown.selectByValue(dropDownValue);  		
			
		}
	  
	 /*Method to select the endDate from DatePicker
	  *@param LocatorKey is Xpath of the DatePicker*/

		public void endDatePicker(String locatorKey) {
			try {
			//Get the today's date for comparison
			DateFormat dateFormat = new SimpleDateFormat("dd");
			Date date = new Date();
			String date1 = dateFormat.format(date);
			System.out.println(date1);
			
			WebElement dateWidget = getElement(locatorKey);
		// List<WebElement> rows = dateWidget.findElements(By.tagName("tr"));  
	        List<WebElement> columns = dateWidget.findElements(By.tagName("td"));  

	        //System.out.println("row count "+rows.size());
	        System.out.println("column count "+columns.size());
	             
	        	for (int j=0;j<columns.size();j++) {
	        		
	        		String cell = columns.get(j).getText();
	        		//System.out.print(cell+"--");
	           		if(cell.equals(date1)) {
	        			//System.out.println("cell data is " +cell);
	        			columns.get(j).click();
	        		    break;
	        		}
	        }
			}catch(Exception e){
	   			reportFailure("Application or Code Error > " + e.getMessage());
	   			takeScreenShot("Error ->" + e.getMessage() );
	   		 }
	    }

	/*Method to select the start date from DatePicker
	 *@param LocatorKey is Xpath of the DatePicker*/

		public void startDatePicker(String locatorKey) {
			try {
				//Getting the current date
				DateFormat dateFormat = new SimpleDateFormat("dd");
				Date date = new Date();
				String date1 = dateFormat.format(date);
				System.out.println(date1);
				int date2 = Integer.parseInt(date1);
				//Getting the date before three days
				date2 = date2-3;
				String expectedStartDate = String.valueOf(date2);
				System.err.println("Expected Date in String " +expectedStartDate);
				
				WebElement dateWidget = getElement(locatorKey);
				
		        List<WebElement> columns = dateWidget.findElements(By.tagName("td"));  

		        System.out.println("column count "+columns.size());
		        	        
	        	for (int j=0;j<columns.size();j++) {
	        		
	        		String actualStartDate = columns.get(j).getText();
	        		//System.out.print(actualStartDate+"--");
	        		//int actualStartDate = Integer.parseInt(cell);
	        		//System.out.println("actual Start Date " +actualStartDate );
	        		
	        		if (actualStartDate.equalsIgnoreCase(expectedStartDate)){
		            	 	    	
		                System.out.println("cell data is " +actualStartDate);
		       			columns.get(j).click();
	        			reportPass("Selection of Start Date was Successfull ");
	        		    break;
	        		}
	        }	          
		    }catch(Exception e){
		        reportFailure("Not able to select the date "+e.getMessage());
		        takeScreenShot("Error ->" + e.getMessage() );
		    }
		}

	/*Method to scroll the page down 
	 *@param value is X or Y Axis value to scroll down by*/
			
	    public void scrollPageDownBy(String value) {
		try {
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,"+value+")");
	    }catch(Exception e){
	        reportFailure("Not able to select the date "+e.getMessage());
	        takeScreenShot("Error ->" + e.getMessage() );
	    }
		}
	    
	    
	/*Method to scroll the page down 
	 *@param value is X or Y Axis value to scroll down by*/
	    		
	     public void scrollPageDownBy(int value) {
	    	try {
	        	JavascriptExecutor js = (JavascriptExecutor) driver;
	        	js.executeScript("window.scrollBy(0,"+value+")");
	        }catch(Exception e){
	            reportFailure("Not able to select the date "+e.getMessage());
	            takeScreenShot("Error ->" + e.getMessage() );
	        }
	    	}
	 
	 /*Method to scroll the page down 
	  *@param value is X or Y Axis value to scroll down by*/
	    
		public void scrollPageUp(String value) {
			try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,"+value+")");
			}catch(Exception e){
		        reportFailure("Not able to select the date "+e.getMessage());
		        takeScreenShot("Error ->" + e.getMessage() );
		    }
			}
		
	/*Method to select the deploy date from DatePicker
	 *@param LocatorKey is Xpath of the DatePicker*/
		
		public void deployDatePicker(String locatorKey) {
			
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd");
				Date date = new Date();
				String date1 = dateFormat.format(date);
				System.out.println(date1);
				int date2 = Integer.parseInt(date1);
				date2 = date2 +1;
				System.out.println("Expected deploy date in int " +date2);
				
				String expecteddeployDate = String.valueOf(date2);
				System.out.println("Expected deploy date in String " +expecteddeployDate);
				//Thread.sleep(5000);
				WebElement dateWidget = getElement(locatorKey);
			
		        List<WebElement> columns = dateWidget.findElements(By.tagName("td"));  

		        System.out.println("column count "+columns.size());
		        	        
	        	for (int j=0;j<columns.size();j++) {
	        		        		      		
	        		String actualDeployDate = columns.get(j).getText();
	        		
	        		//System.out.println("actual Start Date " +actualDeployDate );
	        		
	        		if (actualDeployDate.equalsIgnoreCase(expecteddeployDate)){
		            		            	    	
		                System.out.println("cell data is " +actualDeployDate);
		                columns.get(j).click();
	        			reportPass("Selection of Start Date was Successfull ");
	        		    break;
	        		}
	        }
		    	      
		    }catch(Exception e){
		        reportFailure("Not able to select the date "+e.getMessage());
		        takeScreenShot("Error ->" + e.getMessage() );
		    }
		  }

/*Method to select the end date from DatePicker in AMM ExportFormat LodeStar
 *@param LocatorKey is Xpath of the DatePicker*/
		
		public void endDateLodeStar(String locatorKey) {
			
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd");
				Date date = new Date();
				String date1 = dateFormat.format(date);
				System.out.println(date1);
				int date2 = Integer.parseInt(date1);
				date2 = date2 +2;
				System.out.println("Expected end date in int " +date2);
				
				String expecteddeployDate = String.valueOf(date2);
				System.out.println("Expected end date in String " +expecteddeployDate);
		
				WebElement dateWidget = getElement(locatorKey);
		
		         List<WebElement> columns = dateWidget.findElements(By.tagName("td"));  

		        System.out.println("column count "+columns.size());
		        
		        for (int j=0;j<columns.size();j++) {
	        		        		
	        		String actualDeployDate = columns.get(j).getText();
	        		//System.out.println("actual Start Date " +actualDeployDate );
	        		
	        		if (actualDeployDate.equalsIgnoreCase(expecteddeployDate)){
		            	      	    	
		                System.out.println("cell data is " +actualDeployDate);
		                columns.get(j).click();
	        			reportPass("Selection of Start Date was Successfull ");
	        		    break;
	        		}
	        }
		    }catch(Exception e){
		        reportFailure("Not able to select the date "+e.getMessage());
		        takeScreenShot("Error ->" + e.getMessage() );
		    }
		    }

/*Method to navigate the control to previous page by clicking browser back button*/
	
	public void navigateBack() {
		try {
		driver.navigate().back();
		}
		catch(Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		}
	}
/*Method to add device group from Add device group table
 *@param dataKey is the Device Group key from Json data file
 *@param LocatorKey is Xpath of the Add Group Device table*/			

		public void selectFromAddGroupWebTable(String dataKey, String locatorKey) {
			try {
		      String data = (String)dataObject.get(dataKey);	
			  List<WebElement> cells =driver.findElements(By.xpath(prop.getProperty(locatorKey)));
	        // System.out.println("Expected device group "+data);
			 // System.out.println("Cells Size - " +cells.size());
			 
			  
			   for(int i=0;i<cells.size();i++){
			   String cellData=cells.get(i).getText();
			  	
			  //System.out.println("Device group name " +cellData);
			    if (cellData.equals(data)) {
				  System.out.println(cellData + " is equal to "+ data );
				  System.out.println(cells.get(i).getLocation()); 
				  int getY = cells.get(i).getLocation().getY();
				  
				  getY = getY - 200;
				 // System.out.println(getY);
				  System.out.println("Value of i "+i);
				  scrollPageDownBy(getY);
				  WebElement element = driver.findElement(By.xpath("//form[@name ='GroupAdd']/table/tbody/tr["+(i+2)+"]/td[1]/input"));
				  //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				  //Thread.sleep(500); 
				  element.click();
				  scrollPageDownBy(-getY);
				  break;
			  }
			}
			}catch(Exception e){
	   			reportFailure("Application or Code Error > " + e.getMessage());
	   			takeScreenShot("Error ->" + e.getMessage() );
	   		 }
		}
		
/*Method to create random number and affix it to nameKey from feature file
 *@param LocatorKey is Xpath of the Add Group Device table
 *@param nameKey is the name from the feature file*/	
			
		public void entersUniqueTextFromExamples(String nameKey, String locatorKey) {
			try {
			Random rand = new Random(); //instance of random class
		      int upperbound = 1000;
		     //generate random values from 0-1000
		      int int_random = rand.nextInt(upperbound); 
			  String text = nameKey+int_random;
		      NCCreateThresholdJobName = text;
		      System.out.println("Name with random number "+text);
		      getElement(locatorKey).sendKeys(text);
			}catch(Exception e){
	   			reportFailure("Application or Code Error > " + e.getMessage());
	   			takeScreenShot("Error ->" + e.getMessage() );
	   		 }
		}
/*Method to accept the Alent popup*/

		public void acceptsAlert() {
			try {
		        WebDriverWait wait = new WebDriverWait(driver, 20);
		       
		        if(wait.until(ExpectedConditions.alertIsPresent())==null) {
		        	
		            System.out.println("alert was not present");
		           		        
		        } else {	   	
		        
		       System.out.println("alert was present");
		       // wait.until(ExpectedConditions.alertIsPresent());
		        Alert alert = driver.switchTo().alert();
		       // System.out.println(driver.switchTo().alert().getText());
		        alert.accept();
		    } 
			}catch(Exception e){
	   			//reportFailure("Application or Code Error > " + e.getMessage());
	   			//takeScreenShot("Error ->" + e.getMessage() );
	   		 }
		}

/*Method to create random number and affix it to nameKey from feature file using value attribute of element
 *@param LocatorKey is Xpath of the text field*/

		public void updateTextField(String locatorKey) {
			try {
			String AuditJobName = getElement(locatorKey).getAttribute("value");
			Random rand = new Random(); //instance of random class
		      int upperbound = 1000;
		        //generate random values from 0-1000
		      int int_random = rand.nextInt(upperbound); 
			  AuditJobName = AuditJobName+int_random;
		      System.out.println("Name with random number "+AuditJobName);
		      getElement(locatorKey).clear();
		      getElement(locatorKey).sendKeys(AuditJobName);
		     } catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
			}

/*Method to switch the driver control to newly opened Tab
 *@param LocatorKey is Xpath of the text field in new Tab
 *@param textKey is the expected text value from new tab*/

		public void switchToNewTab(String locatorKey, String textKey) {
		  try{	
			ArrayList<String> windowHandles = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(windowHandles.get(1));
			verifyText(locatorKey, textKey);
		} catch(Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		 }			
		}
		
/*Method to switch the driver control back to Main Tab by closing existing new tab*/	
		public void switchToMainTab() {
		try {
			ArrayList<String> windowHandles = new ArrayList<String> (driver.getWindowHandles());
			driver.close();
			driver.switchTo().window(windowHandles.get(0));
		} catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
		}
		
		
 /*Method to clear the text box 
  *@param LocatorKey is Xpath of the text box to be cleared */	
			
			public void clearTextBox(String locatorKey) {
			 try {	
				WebElement element = getElement(locatorKey);
				element.clear();
				getElement(locatorKey).sendKeys(Keys.BACK_SPACE);
		        String Attr =  element.getAttribute("name");
		       // String Attr =  element.getAttribute("id");
		        JavascriptExecutor js = (JavascriptExecutor) driver;
			   // js.executeScript("document.getElementById('"+Attr+"').setValue('')");
				js.executeScript("document.getElementsByName('"+Attr+"')[0].setValue('')");		
			 }catch(Exception e){
					reportFailure("Application or Code Error > " + e.getMessage());
					takeScreenShot("Error ->" + e.getMessage() );
				 }
			 }

/*Method to clear the text box 
 *@param LocatorKey is Xpath of the text box to be cleared */	
				
				public void clearsAuditTextbox(String locatorKey) {
				 try {	
					WebElement element = getElement(locatorKey);
					element.clear();
				 }catch(Exception e){
						reportFailure("Application or Code Error > " + e.getMessage());
						takeScreenShot("Error ->" + e.getMessage() );
					 }
				 }
			

/*Method to clear the text box 
 *@param LocatorKey is Xpath of the text box to be cleared */	
								
				public void searchJobId(String locatorKey1, String locatorKey2) {
				try {	
					WebElement element = getElement(locatorKey1);
					JobId = element.getText().trim();
					System.out.println(JobId);
					WebElement element1 = getElement(locatorKey2);
					element1.sendKeys(JobId);
					//System.out.println(JobId);
										
					 }catch(Exception e){
					reportFailure("Application or Code Error > " + e.getMessage());
					takeScreenShot("Error ->" + e.getMessage() );
					 }
				 }
				
				
			
/*Method to select the value from Optionlist  
 *@param LocatorKey is Xpath of the option list
 *@param value to be selected from the optionlist */
			
			public void selectFromOptionList(String value, String locatorKey) {
			
			try {	
			//Thread.sleep(5000);
		    List<WebElement> TableList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
			// System.out.println("Inside OptionList Method");
		    System.out.println(TableList.size());
		    	for(int i=0;i<TableList.size();i++){
					// String JobStatusListData = TableList.get(i).getText();
					String JobStatusListData =TableList.get(i).getAttribute("value");
					//System.out.println(TableList.get(i));
					//System.out.println("OptionList value is " + JobStatusListData);
			        //int exportlistInt = Integer.parseInt(exportListData);
				 	//System.out.println(value);
					  if(JobStatusListData.equalsIgnoreCase(value))	{
						  System.out.println("OptionList value is " + JobStatusListData);
						  TableList.get(i).click();
						  clickOutside();
						  Thread.sleep(2000);
				          break;			
						} 
				}	
			  }catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
			}

			
/*Method to select the value from Optionlist  
 *@param LocatorKey is Xpath of the option list
 *@param value to be selected from the optionlist */
			
		 public void selectTextFromOptionList(String value, String locatorKey) throws Throwable {
		    try {
			   List<WebElement> TableList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
			   // System.out.println("Inside OptionList Method");
			    System.out.println(TableList.size());
			   		for(int i=0;i<TableList.size();i++){
						// String JobStatusListData = TableList.get(i).getText();
						String JobStatusListData =TableList.get(i).getText().trim();
						//System.out.println(TableList.get(i));
				    	 System.out.println("OptionList value is " + JobStatusListData);
						  //int exportlistInt = Integer.parseInt(exportListData);
						  //System.out.println(value);
						  if(JobStatusListData.equalsIgnoreCase(value))	{
							  System.out.println("OptionList value is " + JobStatusListData);
							  TableList.get(i).click();
							 // clickOutside();
							  Thread.sleep(2000);
							  break;			
							} 				  		  
					}	
		    }catch(Exception e){
					reportFailure("Application or Code Error > " + e.getMessage());
					takeScreenShot("Error ->" + e.getMessage() );
				 }
			}
		 
/*Method to click outside the screen*/

		    public void clickOutside() {
		     try {
		        Actions action = new Actions(driver);
		        action.moveByOffset(0, 0).click().build().perform();
		     }catch(Exception e){
					reportFailure("Application or Code Error > " + e.getMessage());
					takeScreenShot("Error ->" + e.getMessage() );
				 }
		     }
		    
/*Method to select the Filter command table in network center 
 *@param dataKey is the key value from the Json datasheet 
 *@param LocatorKey is Xpath of the expected text from the screen */

			public void selectFromFilterCommandWebTable(String dataKey, String locatorKey) {
			 try {
				  List<WebElement> cells =driver.findElements(By.xpath(prop.getProperty(locatorKey)));
			      //System.out.println("Expected device group "+dataKey);
					for(int i=0;i<cells.size();i++){
					  String cellData=cells.get(i).getText();
					  if (cellData.equals(prop.get(dataKey))) {
						  System.out.println(cellData + " is equal to "+ prop.getProperty(dataKey) );
						  System.out.println("Value of i "+i);
						//  scrollPageDown();
						  WebElement element = driver.findElement(By.xpath("//tbody[@ng-model='commandModel']/tr/td[1]/input"));
					   	  element.click();
						  break;
					  }
				}
			    }
			     catch(Exception e){
					reportFailure("Application or Code Error > " + e.getMessage());
					takeScreenShot("Error ->" + e.getMessage() );
				 }	
				
			}

/*Method to switch the driver control iframe in the current screen 
 *@param LocatorKey is Xpath of the iframe */
			
		 public void switchesToIFrame(String locatorKey) {
			try {	
				WebElement iframeElement = getElement(locatorKey);
				//now using the switch command to switch to main frame.
				driver.switchTo().frame(0);
			}catch(Exception e){
					reportFailure("Application or Code Error > " + e.getMessage());
					takeScreenShot("Error ->" + e.getMessage() );
				 }
			}

	
/////////////////////////////////////////////////////@Then Methods////////////////////////////////////////////////////////////

////////From Generic Steps class file ///////////////

/*Method to verify the title of the page 
 *expectedTitle is title text to be expected*/
	
	public void verifyTitle(String expectedTitle) {
		try {
		String actualTitle = driver.getTitle();
		System.out.println("Actual Title is "+actualTitle);
		System.out.println("Expected Title is "+ prop.getProperty(expectedTitle));
		
		if(actualTitle.equals(prop.get(expectedTitle))) {
			reportPass( "Screen Title validation for "+expectedTitle+" was successfull");
			takeScreenShot(actualTitle + " Screen Title validation");
		} else {
			//System.out.println("Report Test is failed");
			reportFailure("Actual Title "+actualTitle + " is not matching ExpectedTitle "+expectedTitle);
			}
		}
		catch(Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		}
		}
	

/*Method to verify expected text 
 *locatorKey is Xpath of the element
 *expectedTextKey is the Key from Properties file to validate*/
	

	public void verifyText(String locatorKey, String expectedTextkey) {
			try {
			
			String Available = "False";
			
			//WebDriverWait wait = new WebDriverWait(driver, 20);
			//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorKey))));
			
			String actualText = getElement(locatorKey).getText().trim();
			System.out.println(actualText);
			String expectedText = prop.getProperty(expectedTextkey);
			
			
			
              if(actualText.equalsIgnoreCase(expectedText)||actualText.equalsIgnoreCase(JobId)) {
            	Available = "True";
				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
				} else if(actualText.contains(expectedText) || actualText.contains(JobId)) {
					Available = "True";
				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
				} else if (Available.equals("False")){
				reportFailure("Actual Text "+actualText+ " is not matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
			}
	        }
              catch(Exception e){
            	
            	 
      			reportFailure("Application or Code Error > " + e.getMessage());
      			takeScreenShot("Error ->" + e.getMessage() );
      		}
		}
/*Method to verify the expected text from examples 
 *LocatorKey is Xpath of the actual element text
 *expectedText is expected text to validate*/
	   
	   public void verifyTextfromExamples(String locatorKey, String expectedText) throws Throwable {
			try {
			String Available = "False";
			String actualText = null;
			
			for (int i=0;i<=20;i++) {
			Thread.sleep(2000);
			//driver.navigate().refresh();
			actualText = getElement(locatorKey).getText().trim();
			System.out.println(actualText);
			
			if(actualText.equalsIgnoreCase(expectedText)) {
				Available = "True";
				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
				break;
			} else if (actualText.contains(expectedText)) {
				Available = "True";
				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
				break;
			}
			}
			if (Available.equals("False")) {			
				reportFailure("Actual Text "+actualText+ " is not matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
			}
			}
			catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
		}
	   
/*Method to verify the expected text from examples 
 *LocatorKey is Xpath of the actual element text
 *expectedTextKey is expected text from Json data file to validate in Data table */ 
	   
	   public void verifyTextfromDataSheet(String locatorKey, String expectedTextKey) throws Throwable {
			
		   try {
			String Available = "False";
			String actualText = null;
			String expectedText = (String)dataObject.get(expectedTextKey);
			
			for (int i=0;i<=15;i++) {
			Thread.sleep(2000);
			actualText = getElement(locatorKey).getText().trim();
			System.out.println(actualText);
			if(actualText.equalsIgnoreCase(expectedText)) {
				Available = "True";
				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
				break;
			} else if (actualText.contains(expectedText)) {
				Available = "True";
				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
				break;
			}
			}
			if (Available.equals("False")) {			
				reportFailure("Actual Text "+actualText+ " is not matching Expected Text "+expectedText);
				takeScreenShot(locatorKey);
			}
		    } 
		     catch(Exception e){
				reportFailure("Application or Code Error > " + e.getMessage());
				takeScreenShot("Error ->" + e.getMessage() );
			 }
		}
	   
/*Method to verify the link button 
 *locatorKey is Xpath of the element*/	

   public void verifyLinkButton(String locatorKey) {
	 try {	
		String actualText = getElement(locatorKey).getText();
     }
     catch(Exception e){
		reportFailure("Application or Code Error > " + e.getMessage());
		takeScreenShot("Error ->" + e.getMessage() );
	 }				
	   }
  
/*Method to click and verify if the text is present 
 *expectedLocatorKey is Xpath of the expected text
 *actualLocatorKey is Xpath of the actual text to validate*/ 
   
   public void clickAndVerify(String expectedLocatorKey, String actualLocatorKey) {
	  try {
		 String Available = "False";
		 String expectedText = getElement(expectedLocatorKey).getText();
		
		getElement(expectedLocatorKey).click();
		
		String actualText = getElement(actualLocatorKey).getText();
		
		if(actualText.equalsIgnoreCase(expectedText)) {
			Available = "True";
			reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
			takeScreenShot(actualLocatorKey);
			
		} else if (actualText.contains(expectedText)) {
			Available = "True";
			reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
			takeScreenShot(actualLocatorKey);
			
		}
		
		if (Available.equals("False")) {			
			reportFailure("Actual Text "+actualText+ " is not matching Expected Text "+expectedText);
			takeScreenShot(actualLocatorKey);
		}
     }
       catch(Exception e){
		reportFailure("Application or Code Error > " + e.getMessage());
		takeScreenShot("Error ->" + e.getMessage() );
	 }
				
	}
  
/*Method to verify the success rate text in the screen
 *@param LocatorKey is Xpath of the SuccessText
 *@param expectedText status text expected in the screen*/
   	
   	public void verifySuccessRate(String locatorKey, String expectedText) {
   		try {
   		String Available = "False";
   		String actualText = null;
   		for (int i=0;i<=30;i++) {
   			Thread.sleep(10000);
   			driver.navigate().refresh();
   			actualText = getElement(locatorKey).getText().trim();
   			System.out.println(actualText);
   			String actualEndTime = driver.findElement(By.xpath("//table[@id = 'runDetailsResults']/tbody/tr[3]/td[3]")).getText().trim();
   			System.out.println("actualEndTime - " +actualEndTime);
   			
   			if (actualEndTime != null && !actualEndTime.isEmpty()) {
   			  if(actualText.contains(expectedText)) {
   				  Available = "True";
   				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
   				takeScreenShot(locatorKey);
   				break;
   			}
   			else if (actualText.equals(expectedText)){
   				Available = "True";
   				reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
   				takeScreenShot(locatorKey);
   				break;		
   			}
   			} 
   			  
   			  			
   			}
   		
   	          actualText = actualText.substring(0, actualText.length() - 1);
			  expectedText = expectedText.substring(0, expectedText.length()-1);  
			  System.out.println(actualText +", "+expectedText);
			  
			  double actualSucessRate = Double.parseDouble(actualText);
			  double expectedSuccessRate = Double.parseDouble(expectedText);
			   System.out.println(actualSucessRate +", "+expectedSuccessRate);
			  if(actualSucessRate >= expectedSuccessRate) {
				Available = "True";
				reportPass("Actual Text "+actualText+ " is exqual to or greater than "+expectedText);
				takeScreenShot(locatorKey);
				 
			  }
   			   		  		
    		if (Available.equals("False")) {
   				reportFailure("Actual Text " +actualText+ " is not matching Expected Text "+expectedText);
   				takeScreenShot(locatorKey);
   			}
    		
   		}catch(Exception e){
      			reportFailure("Application or Code Error > " + e.getMessage());
      			takeScreenShot("Error ->" + e.getMessage() );
      		 }
   				
   		}
/*Method to verify the success rate text in the screen
 *@param LocatorKey is Xpath of the SuccessText
 *@param expectedText status text expected in the screen*/
   		
   		public void verifyRunStatus( String locatorKey, String expectedText ) {
   			
   			System.out.println(expectedText +" , " + locatorKey );
   			
   			try {
   			String Available = "False";
   			String actualText = null;
   			for (int i=0;i<=25;i++) {
   				Thread.sleep(10000);
   				driver.navigate().refresh();
   				actualText = getElement(locatorKey).getText().trim();
   				System.out.println(actualText);
   				//String actualEndTime = driver.findElement(By.xpath("//table[@id = 'runDetailsResults']/tbody/tr[3]/td[3]")).getText().trim();
   								
   				  if(actualText.contains(expectedText)) {
   					  Available = "True";
   					reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
   					takeScreenShot(locatorKey);
   					break;
   				}
   				else if (actualText.equals(expectedText)){
   					Available = "True";
   					reportPass("Actual Text "+actualText+ " is matching Expected Text "+expectedText);
   					takeScreenShot(locatorKey);
   					break;		
   				}
   				}if (Available.equals("False")) {
   					reportFailure("Actual Text " +actualText+ " is not matching Expected Text "+expectedText);
   					takeScreenShot(locatorKey);
   				}
   			}catch(Exception e){
   	   			reportFailure("Application or Code Error > " + e.getMessage());
   	   			takeScreenShot("Error ->" + e.getMessage() );
   	   		 }
   					
   			}
   		/*Method to check for not null values from the element 
   	  *@param LocatorKey is Xpath of the text to check not null value */
   		
   	 public void verifyNotNullValues(String locatorKey) {
   		try {
   	     	String actualText = getElement(locatorKey).getText().trim();
   			System.out.println(actualText);
   						
   				if (actualText != null && !actualText.isEmpty()) {
   					reportPass("Actual Text "+actualText+ " is not null");
   					takeScreenShot(locatorKey);
   					
   				}else {
   					reportFailure("Actual Text " +actualText+ " is null ");
   					takeScreenShot(locatorKey);
   								
   				} 
   		}catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }							
   		}

 /*Method to verify the created job name from the table 
  *@param data is the expected value to be checked 
  *@param LocatorKey is Xpath of the expected text from the screen */

   		public void verifyCreatedNameInWebTable(String data, String locatorKey) {
   		
   		 try {	
   			String Available = "False";
   			String cellData = null;
   		   // data= NCCreateThresholdJobName.toLowerCase().trim();
   		    List<WebElement> cells =driver.findElements(By.xpath(prop.getProperty(locatorKey)));
   	       //System.out.println("Expected device group "+data);
   			for(int i=0;i<cells.size();i++){
   			  cellData=cells.get(i).getText();
   			  cellData = cellData.toLowerCase();
   			  //System.out.println("Device group name " +cellData);
   			  if (cellData.contains(data)) {
   				  Available = "True";
   				  System.out.println(cellData + " is equal to "+ data );
   				  //System.out.println("Value of i "+i);
   				  reportPass("Actual Text "+cellData+ " is matching Expected Text "+data);
   				  takeScreenShot(locatorKey);		  
   				  break;
   			  }
   		}
   			if (Available.equals("False")) {			
   			reportFailure("Actual Text "+cellData+ " is not matching Expected Text "+data);
   			takeScreenShot(locatorKey);
   		} 
   		 }catch(Exception e){
   				reportFailure("Application or Code Error > " + e.getMessage());
   				takeScreenShot("Error ->" + e.getMessage() );
   			 }
   		}
   		

 /*Method to verify the Timesync between system time and application time
  *@param LocatorKey is Xpath of the text field with application time*/	

   	public void verifyTimeSync(String locatorKey) throws Throwable  {
   	try {	
   		String localTime = getElement(locatorKey).getText().trim();
   		System.out.println(localTime);				
   		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy hh:mm aa");
   		//get current date time with Date()
   		 Date date = new Date();
   		 
   		 // Now format the date
   		 String systemTime= dateFormat.format(date);
   		 System.out.println(systemTime);
   		// convert to another format
   		
   		 //localTime = LocalDateTime.parse(localTime,DateTimeFormatter.ofPattern("MMM d, uuuu hh:mm:ss a" , Locale.US )) ;
   		 Date local = null;
   		 try {
   		 local = new SimpleDateFormat("MMM d, yyyy - hh:mm a", Locale.ENGLISH).parse(localTime);
   		 }catch (Exception ex) {}
   		 
   		 if (local==null) {
   			 try  {
   				 
   		 local = new SimpleDateFormat("dd/MMM/yy hh:mm aa", Locale.ENGLISH).parse(localTime);
   			 }catch (Exception ex) {}
   		 }
   		 
   		 Date system = new SimpleDateFormat("dd/MMM/yy hh:mm aa", Locale.ENGLISH).parse(systemTime);
   		 long localtimeinMS = local.getTime();
   		 long systemtimeinMS = system.getTime();
   		 System.out.println(systemtimeinMS -localtimeinMS);
   		 if ((systemtimeinMS -localtimeinMS) <180000){
   			 reportPass("Local time "+localTime+" matches with system time"+systemTime);
   			 takeScreenShot("TimeSync screen validation");
   		 }else {
   			 reportFailure("Local time "+localTime+" does not match with system time"+systemTime);
   		 }
   	}catch(Exception e){
   	   			reportFailure("Application or Code Error > " + e.getMessage());
   	   			takeScreenShot("Error ->" + e.getMessage() );
   	   		 }
   	}
   	
 /*Method to verify the Timesync between system time and application time
  *@param LocatorKey is Xpath of the text field with application time*/	

      	public void verifyDateSync(String locatorKey) throws Throwable  {
      	try {	
      		String localTime = getElement(locatorKey).getText().trim();
      		System.out.println(localTime);				
      		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy hh:mm aa");
      		//get current date time with Date()
      		 Date date = new Date();
      		 
      		 // Now format the date
      		 String systemTime= dateFormat.format(date);
      		 System.out.println(systemTime);
      		// convert to another format
      		
      		 //localTime = LocalDateTime.parse(localTime,DateTimeFormatter.ofPattern("MMM d, uuuu hh:mm:ss a" , Locale.US )) ;
      		 Date local = null;
      		 try {
      		 local = new SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.ENGLISH).parse(localTime);
      		 }catch (Exception ex) {}
      		 
      		 if (local==null) {
      			 try  {
      				 
      		 local = new SimpleDateFormat("dd/MMM/yy hh:mm aa", Locale.ENGLISH).parse(localTime);
      			 }catch (Exception ex) {}
      		 }
      		 
      		 Date system = new SimpleDateFormat("dd/MMM/yy hh:mm aa", Locale.ENGLISH).parse(systemTime);
      		
      		 long localtimeinMS = local.getTime();
      		 long systemtimeinMS = system.getTime();
      		 System.out.println(systemtimeinMS -localtimeinMS);
      		 if ((systemtimeinMS -localtimeinMS) <180000){
      			 reportPass("Local time "+localTime+" matches with system time"+systemTime);
      			 takeScreenShot("TimeSync screen validation");
      		 }else {
      			 reportFailure("Local time "+localTime+" does not match with system time"+systemTime);
      		 }
      	}catch(Exception e){
      	   			reportFailure("Application or Code Error > " + e.getMessage());
      	   			takeScreenShot("Error ->" + e.getMessage() );
      	   		 }
      	}
   	
   	

/*Method to verify the Timesync between system time and application time
 *@param LocatorKey is Xpath of the text field with application time*/	

   		public void verifyRunDateTime(String locatorKey, int DaysRange)  {
   		
   			try {	
   				
   			List<WebElement> cells =driver.findElements(By.xpath(prop.getProperty(locatorKey)));
   		    //System.out.println("Expected device group "+dataKey);
   			
   			for(int i=0;i<cells.size();i++){
   			String localTime=cells.get(i).getText().trim();	
   			//String localTime = getElement(locatorKey).getText().trim();
   			System.out.println(localTime);				
   			DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yy hh:mm aa");
   			//get current date time with Date()
   			 Date date = new Date();
   			 
   			 // Now format the date
   			 String systemTime= dateFormat.format(date);
   			 System.out.println(systemTime);
   			// convert to another format
   			
   			 //localTime = LocalDateTime.parse(localTime,DateTimeFormatter.ofPattern("MMM d, uuuu hh:mm:ss a" , Locale.US )) ;
   			 Date local = null;
   			 try {
   			 local = new SimpleDateFormat("MMM d, yyyy - hh:mm a", Locale.ENGLISH).parse(localTime);
   			 }catch (Exception ex) {}
   			 
   			 if (local==null) {
   				 try  {
   			     local = new SimpleDateFormat("dd/MMM/yy hh:mm aa", Locale.ENGLISH).parse(localTime);
   				 }catch (Exception ex) {}
   			 }
   			 if (local==null) {
   				 try  {
   				     local = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).parse(localTime);
   					 }catch (Exception ex) {}
   				 }
   			 
   			 Date system = new SimpleDateFormat("dd/MMM/yy hh:mm aa", Locale.ENGLISH).parse(systemTime);
   			 
   			long localtimeinMS = local.getTime();
   			long systemtimeinMS = system.getTime();
   			 System.out.println(systemtimeinMS -localtimeinMS);
   			 
   			 int DateDiff = (int)((systemtimeinMS -localtimeinMS)/(24*3600*1000));
   			
   			 System.out.println("Date Difference" +DateDiff);
   			 
   			 if (DateDiff <=DaysRange){
   				 reportPass("Run time "+localTime+" is within "+DaysRange+ " days of  system time "+systemTime);
   				 takeScreenShot("TimeSync screen validation");
   			 }else {
   				 reportFailure("Run time "+localTime+" is not within "+DaysRange+" days of  system time "+systemTime);
   			 }
   		}
   			}catch(Exception e){
   		   			reportFailure("Application or Code Error > " + e.getMessage());
   		   			takeScreenShot("Error ->" + e.getMessage() );
   		   		 }
   		}
   	
      

 /*Method to verify the export list contains CSV, JSON and KML
  *@param LocatorKey is Xpath of the table which contains these export list */
   	
   	public void verifyExportList(String locatorKey) {
   		
   	try {			
   		List<WebElement> exportList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
   		ArrayList<String>  export = new ArrayList<String>();
   		for(int i=0;i<exportList.size();i++){
   			  String exportListData = exportList.get(i).getText();
   			 System.out.println(exportListData);
   			  export.add(exportListData);
   		}	  
   			if(export.contains("CSV")&&export.contains("JSON")&&export.contains("KML"))
   			{
   				reportPass("File Export List matches");
   				takeScreenShot("Export List");
   			} else 
   					reportFailure("File Export List does not match");
   	}catch(Exception e){
   	   			reportFailure("Application or Code Error > " + e.getMessage());
   	   			takeScreenShot("Error ->" + e.getMessage() );
   	   		 }
   		}

 /*Method to check the RSSI value form the screen
  *@param LocatorKey is Xpath of the rssi value text from the screen */
   	
   	public void checkRssiValue(String locatorKey) {
   	 try {	
           List<WebElement> exportList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
   		for(int i=0;i<exportList.size();i++){
   		String exportListData = exportList.get(i).getText();
   		  int exportlistInt = Integer.parseInt(exportListData);
   			  if((exportlistInt >= -96 && exportlistInt <= -48 )||exportlistInt ==0 )
   				{
   					reportPass("RSSI Value is between -48 and -96 "+exportlistInt );
   					takeScreenShot("RSSI Value");
   				} else 
   					reportFailure("RSSI Value is not between -48 and -96 " + exportlistInt);
   			 	}	  
   	 }catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }	
   		}

 /*Method to check the Network center DNS Zone status value form the screen
  *@param LocatorKey is Xpath of the DNS Zone status value text from the screen */	
   	
   	public void checkNCJobDNSZoneGetStatus(String locatorKey) {
   	 try {
   		List<WebElement> JobStatusList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
   		for(int i=0;i<JobStatusList.size();i++){
   			  String JobStatusListData = JobStatusList.get(i).getText();
   				  //int exportlistInt = Integer.parseInt(exportListData);
   			  if(JobStatusListData.equalsIgnoreCase("OK"))
   				{
   					reportPass("DNS_Zone_Get Status matches "+JobStatusListData );
   					takeScreenShot("RSSI Value");
   				} else 
   					reportFailure("DNS_Zone_Get Status does nt matches "+JobStatusListData);
   		}
   	 }catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }
   	}

   /*Method to verify value from table
    *@param status is expected status to be validated from the screen 
    *@param LocatorKey is Xpath of the expected text from the screen */
   	
   	public void verifyFromTable(String status, String locatorKey) {
   	 try {	
   		List<WebElement> TableList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
   		String JobStatusListData = null;
   		String Available = "False";
   		for(int i=0;i<TableList.size();i++){
   			   JobStatusListData = TableList.get(i).getText();
   			   //int exportlistInt = Integer.parseInt(exportListData);
   			  System.out.println(JobStatusListData);
   			  if(JobStatusListData.equalsIgnoreCase(status)){
   				    Available = "True";
   					reportPass(JobStatusListData +" matches "+status );
   					takeScreenShot("Verify from Table");
   				} else if (JobStatusListData.contains(status)){
   					Available = "True";
   					reportPass(JobStatusListData +" matches "+status );
   					takeScreenShot("Verify from Table");
   				} else if ((JobStatusListData.equalsIgnoreCase(status))||(JobStatusListData=="Inactive")||(JobStatusListData=="Initializing")){
   					Available = "True";
   					reportPass(JobStatusListData +" matches "+status );
   					takeScreenShot("Verify from Table");
   				} else {
   					
   					reportFailure(JobStatusListData + " does not match "+status );
   	   		        takeScreenShot("Verify from Table");
   					
   				}
   	 } 
   				/*else if (JobStatusListData!=null){
   					Available = "True";
   					reportPass(JobStatusListData +" matches "+status );
   					takeScreenShot("Verify from Table");
   		      }	
   			  
   		     if (Available.equals("False")) {
   		      reportFailure(JobStatusListData + " does not match "+status );
   		      takeScreenShot("Verify from Table");
   		     }*/
   		     
   	  }catch(Exception e){
   		reportFailure("Application or Code Error > " + e.getMessage());
   		takeScreenShot("Error ->" + e.getMessage() );
   	  }
   	 	     
         }
   	/*Method to verify value from table
     *@param status is expected status to be validated from the screen 
     *@param LocatorKey is Xpath of the expected text from the screen */
    	
    	public void compareDevicePrograms(String status, String locatorKey) {
    	 try {	
    		List<WebElement> TableList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
    		int count =0;
    		int getY = 0;
    		String DeviceProgramListData = null;
    		String Available = "False";
    		for(int i=0;i<TableList.size();i++){
    			   DeviceProgramListData = TableList.get(i).getText();
    			   //int exportlistInt = Integer.parseInt(exportListData);
    			  System.out.println(DeviceProgramListData);
    			  if(DeviceProgramListData.equalsIgnoreCase(status)){
    				    Available = "True";
    					reportPass(DeviceProgramListData +" matches "+status );
    					takeScreenShot("Verify from Table");
    					WebElement element = driver.findElement(By.xpath("//table[@class='standard']/tbody/tr["+(i+2)+"]/td[1]/input"));
    					
    					 getY = element.getLocation().getY();
    					 

    					System.out.println(getY);			  
    					getY = getY - 300;
    					System.out.println(getY);
    				    System.out.println("Value of i "+i);
    					scrollPageDownBy(getY);
    					element.click();
    					scrollPageDownBy(-getY);
    					
    					count = count+1;
    					if (count==2) {
    						//scrollPageDownBy(-getY);
    						break;
    					}
    					    					
    				} else if (DeviceProgramListData.contains(status)){
    					Available = "True";
    					reportPass(DeviceProgramListData +" matches "+status );
    					takeScreenShot("Verify from Table");
    					WebElement element = driver.findElement(By.xpath("//table[@class='standard']/tbody/tr["+(i+2)+"]/td[1]/input"));
    					getY = element.getLocation().getY();
   					 
    					System.out.println(getY);			  
    					getY = getY - 600;
    					System.out.println(getY);
    				    System.out.println("Value of i "+i);
    					scrollPageDownBy(getY);
    					
    					
    					element.click();
    					count = count+1;
    					if (count==2) {
    						
    						scrollPageDownBy(-getY);
    						break;
    					}
    				} 
    			  }  
    		     if (Available.equals("False")) {
    		      reportFailure(DeviceProgramListData + " does not match "+status );
    		      takeScreenShot("Verify from Table");
    		     }
    	  }catch(Exception e){
    		reportFailure("Application or Code Error > " + e.getMessage());
    		takeScreenShot("Error ->" + e.getMessage() );
    	  }
    	 	     
          }
   	
   /*Method to verify value from table
    *@param valueKey is expected value from Json dataSheet to be validated from the screen 
    *@param LocatorKey is Xpath of the expected text from the screen */
   		
   		public void verifyDataSheetValueFromTable(String dataKey, String locatorKey) {
   		 try {	
   			 
   			 String data = (String)dataObject.get(dataKey);
   			List<WebElement> TableList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
   			String JobStatusListData = null;
   			String Available = "False";
   			for(int i=0;i<TableList.size();i++){
   				   JobStatusListData = TableList.get(i).getText();
   				   //int exportlistInt = Integer.parseInt(exportListData);
   				  System.out.println(JobStatusListData);
   				  if(JobStatusListData.equalsIgnoreCase(data)){
   					    Available = "True";
   						reportPass(JobStatusListData +" matches "+data );
   						takeScreenShot("Verify from Table");
   					} else if (JobStatusListData.contains(data)){
   						Available = "True";
   						reportPass(JobStatusListData +" matches "+data );
   						takeScreenShot("Verify from Table");
   					} else if ((JobStatusListData.equalsIgnoreCase(data))||(JobStatusListData=="Inactive")||(JobStatusListData=="Initializing")){
   						Available = "True";
   						reportPass(JobStatusListData +" matches "+data );
   						takeScreenShot("Verify from Table");
   					}  	
   			}	  
   			     if (Available.equals("False")) {
   			      reportFailure(JobStatusListData + " does not match "+data );
   			      takeScreenShot("Verify from Table");
   			     }
   		  }catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		  }
   		 	     
   	      }

   /*Method to verify value from Json data sheet 
    *@param status is expected status to be validated from the screen 
    *@param LocatorKey is Xpath of the expected text from the screen */
   	
     public void verifyFromDataSheet(String statusKey, String locatorKey) {
   	try {	
   	   String status = (String)dataObject.get(statusKey);
   		List<WebElement> TableList =driver.findElements(By.xpath(prop.getProperty(locatorKey)));	
   		String JobStatusListData = null;
   		String Available = "False";
   		
   		for(int i=0;i<TableList.size();i++){
   			   JobStatusListData = TableList.get(i).getText();
   			   //int exportlistInt = Integer.parseInt(exportListData);
   			  System.out.println(JobStatusListData);
   				 	
   			  if(JobStatusListData.equalsIgnoreCase(status)){
   				    Available = "True";
   					reportPass(JobStatusListData +" matches "+status );
   					takeScreenShot("Verify from Table");
   				} else if (JobStatusListData.contains(status)){
   					Available = "True";
   					reportPass(JobStatusListData +" matches "+status );
   					takeScreenShot("Verify from Table");
   				}  	
   			  			  		  
   		}	  
   		     if (Available.equals("False")) {
   			  reportFailure(JobStatusListData + " does not match "+status );
   		      takeScreenShot("Verify from Table");
           	}
   	}catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }
   	}
   	
  
 
/////////////////////////////////////////////////////Extent Report Functions////////////////////////////////////////////////////////////

/*Method to Inititalize the Extent Report
 * sceanrioName is the name of the BDD Test case to be displayed in the report */    
    public void initReports(String scenarioName) {
   		try {
   	    rep = ExtentManager.getInstance();
   		scenario = rep.createTest(scenarioName);
   		scenario.log(Status.INFO, "Starting scenario "+scenarioName);
   		//scenario.log(Status.INFO, "Starting scenario "+ExtentManager.reportFolderName);
   		
   		}
   		catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage());
   		 }
    }
    
 /*Method to quiting the Extent Report
     * sceanrioName is the name of the BDD Test case to be displayed in the report */    
        public void copyReport() {
       		try {
       	   String  destFolder = ExtentManager.destFolder;
       	   String sourceFolderFileName = ExtentManager.sourceFolderFileName ;
       	   
        	//System.out.println(destFolder);
        	//System.out.println(sourceFolderFileName);
       	   // Files.copy(Paths.get(ExtentManager.reportFolderName), Paths.get(System.getProperty("user.dir")+"//reports//Report.html"),StandardCopyOption.REPLACE_EXISTING );
        	Files.copy(Paths.get(sourceFolderFileName), Paths.get(destFolder),StandardCopyOption.REPLACE_EXISTING );
       	   //Files.copy(source, target, options)
       	 //System.out.println("after message");
       		}
       		catch(Exception e){
       			//System.out.println(e.getLocalizedMessage());
       			e.printStackTrace();
       			System.out.println(e.getMessage());
       			reportFailure(e.getLocalizedMessage());
       			takeScreenShot("Error ->" + e.getMessage() );
       		 }
       		
       		} 
  
   
/**************Logging Methods*********************/
/*Method to log a Pass,Fail and Info status in Extent Report
 *@param msg is message to be displayed in the report */  	
    
    public void infoLog(String msg) {
    	// info in extent reports	
    	scenario.log(Status.INFO, msg);
    }
	
    public void reportPass(String msg) {
    	// Pass in extent reports
    	scenario.log(Status.PASS, msg);
    }
    
    public void reportFailure(String errMsg) {
		// fail in extent reports
		scenario.log(Status.FAIL, errMsg);
		assertThat(false);
	}

/*Method to take screenshot and add it to the Extent Report
 *@param message is message to be displayed in the report */ 
	
	public void takeScreenShot(String message){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//put screenshot file in reports
			scenario.log(Status.INFO,"Screenshot-> "+ message+ "->" + scenario.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
			//scenario.log(Status.INFO, "Screenshot attached ->");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
 
/////////////////////////////////////////////////////Quit Methods////////////////////////////////////////////////////////////
   
  /**************
 * @param scenarioName *********************/
    
	public void quit(String scenarioName) {
		
		scenario.log(Status.INFO, "End of scenario " + scenarioName );
		
		if(rep!=null) {
			
			rep.flush();
								
		}
		
		if(driver!=null) {
			
			driver.quit();
		}
		
	}



/*Method to wait for the particular status in the screen
 *@param LocatorKey is Xpath of the DatePicker
 *@param expectedText status text expected in the screen*/
	
	public void waitForStatus(String locatorKey, String expectedText) throws Throwable {
		try {
		Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.attributeContains(getElement(locatorKey), "class", expectedText));
		//String actualValue = getElement(locatorKey).getAttribute("class");
		//System.out.println("actualValue of status "+actualValue);
		reportPass("Operation Successfully Completed");
		}catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }
		}


	

 

/*Method to hit keyboard likes ENTER, RETURN and TAB 
 *@param keyName is the keyboard key name coming from feature file 
 *@param LocatorKey is Xpath of the field from the screen */

public void userHitsKeyBoardKey(String keyName,String locatorKey) {
		try {
				if (keyName.equalsIgnoreCase("ENTER")) {
					WebElement element = getElement(locatorKey);
					element.sendKeys(Keys.ENTER);	
				}else if (keyName.equalsIgnoreCase("RETURN")) {
					WebElement element = getElement(locatorKey);
					element.sendKeys(Keys.RETURN);
				}else if (keyName.equalsIgnoreCase("TAB")) {
					WebElement element = getElement(locatorKey);
					element.sendKeys(Keys.TAB);
				}
		}catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }

	}

/*Method to click the Event Info from BackGround Web Table in AMM 
 *@param keyValue is the value to be searched and clicked 
 *@param LocatorKey is Xpath of the link to be clicked */

	public void clickEventInfoFromBackgroundWebTable(String keyValue, String locatorKey) {
		try {
		List<WebElement> cells =driver.findElements(By.xpath(prop.getProperty(locatorKey)));
	        //System.out.println("Expected device group "+dataKey);
			for(int i=0;i<cells.size();i++){
			  String cellData=cells.get(i).getText();
			  if (cellData.equals(keyValue)) {
				  System.out.println(cellData + " is equal to "+ keyValue );
				  System.out.println("Value of i "+i);
				  WebElement element = driver.findElement(By.xpath("//table/tbody/tr["+i+"]/td[9]/a"));
				  element.click();
				  break;
			  }
			}
		}catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }

	}

/*Method to wait for the control for 20 secs until the page loads 
 *@param LocatorKey is Xpath of the element to be loaded */

	public void waitForPageLoad(String locatorKey) {
		try {	
         WebDriverWait wait = new WebDriverWait(driver,20);
         wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(locatorKey))));
		}catch(Exception e){
   			reportFailure("Application or Code Error > " + e.getMessage());
   			takeScreenShot("Error ->" + e.getMessage() );
   		 }
	}

/*Method to get the current url of the current page */

	public void getCurrentURLOpeninNewTab() {
	 try {	
		String UrlStr = driver.getCurrentUrl();
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(UrlStr);
	 }catch(Exception e){
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
		 }
	}

	
/*Method to display the list of text from the particular container from the page  */
	public void displayTextList(String locatorKey, List<String> data) {
		try {
		List<WebElement> textList = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		ArrayList<String> li = new ArrayList<String>();
				
		
		for (int i=0;i<textList.size();i++) {
			
			String text = textList.get(i).getText();
		    li.add(text);	
			System.out.println(text);
		}
		
		for (int j = 0;j<data.size();j++) {
			
			String dataText = data.get(j);
			if(li.contains(dataText))
			{
			reportPass(li.get(j) + " - is displayed in the Home Page");
			takeScreenShot(li.get(j));
			}
		}
		
		
		}catch(Exception e) {
			reportFailure("Application or Code Error > " + e.getMessage());
			takeScreenShot("Error ->" + e.getMessage() );
			
		}
	}


	public void saySomething(String anything) {
		// TODO Auto-generated method stub
		
	}

				
	}
		
			
	
	
	
