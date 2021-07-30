package com.UIQ.Report;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;



public class ExtentManager {
    
    private static ExtentReports extent;
    public static String screenshotFolderPath;
	public static String reportFolderName = null;
	
	
    public static ExtentReports getInstance() {
    	
    	  String reportPath = System.getProperty("user.dir")+"\\reports\\";
    	//reportFolderName 
    	  
    	if (extent == null){
    		// generate report folder
    		String fileName="Report.html";
    		Date d = new Date();
    		String FolderName=d.toString().replace(":", "_");
    		
    		// directory of the report folder
    		new File(reportPath+FolderName+"//screenshots").mkdirs();
    		
    		reportPath = reportPath+FolderName+"\\";
    		reportFolderName = reportPath+fileName;
    		screenshotFolderPath = reportPath +"screenshots//";
    		
    		//System.out.println(screenshotFolderPath);
    		createInstance(reportPath+fileName);
    		
    		}
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
    	
    	extent = new ExtentReports();
    	
    	ExtentSparkReporter spark = new ExtentSparkReporter(fileName)
    			  .viewConfigurer()
    			    .viewOrder()
    			    .as (new ViewName[] {
    			      ViewName.DASHBOARD,
    			      ViewName.TEST,
    			      ViewName.AUTHOR,
    			      ViewName.DEVICE,
    			      ViewName.EXCEPTION,
    			      ViewName.LOG
    			    })
    			    .apply();
    			    
    			    
    			    
    			    /*
    			    
    			    .as(new ViewName[] { 
    				   ViewName.DASHBOARD, 
    				   ViewName.TEST, 
    				   ViewName.TAG, 
    				   ViewName.AUTHOR, 
    				   ViewName.DEVICE, 
    				   ViewName.EXCEPTION, 
    				   ViewName.LOG 
    				})
    			  .apply();*/
    	
    	
    	extent.setSystemInfo("os", "Windows - 10");
    	spark.config().setTheme(Theme.DARK);;
    	spark.config().setDocumentTitle("Automation Practice");
    	spark.config().setReportName("BDD Framework Report");
    	spark.config().setEncoding("utf-8");
    	spark.config().setTimeStampFormat("MM.dd.yyyy, HH:mm:ss");
    	spark.config().setCss("css-string");
    	spark.config().setJs("js-string");
    	spark.config().setProtocol(Protocol.HTTPS);
    	
    	
    	
    	
        extent.attachReporter(spark);
        
        return extent;
    }
}