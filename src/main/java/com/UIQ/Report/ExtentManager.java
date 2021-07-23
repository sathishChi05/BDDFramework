package com.UIQ.Report;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    
    private static ExtentReports extent;
    public static String screenshotFolderPath;
	public static String reportFolderName;
	public static String sourceFolderFileName;
	public static String destFolderFileName;
	public static String destFolder;
	
    public static ExtentReports getInstance() {
    	
    	  String reportPath = System.getProperty("user.dir")+"\\reports\\";
    	//reportFolderName 
    	  
    	if (extent == null){
    		// generate report folder
    		String fileName="Report.html";
    		Date d = new Date();
    		reportFolderName=d.toString().replace(":", "_");
    		
    		// directory of the report folder
    		new File(reportPath+reportFolderName+"//screenshots").mkdirs();
    		
    		String createdReportPath=reportPath+reportFolderName+"//";
    		
    		//destFolderFileName = reportPath+reportFolderName+"\\"+fileName;
    		
    		screenshotFolderPath=createdReportPath+"screenshots//";
    		
    		//source and destination file path to copy latest Report.html 
    		
    		sourceFolderFileName = createdReportPath+fileName;  	
    		
    		destFolder = reportPath+fileName;    
    		
    		System.out.println(sourceFolderFileName);
    		createInstance(sourceFolderFileName);
    	}
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Reports");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Reports - Automation Testing");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }
}