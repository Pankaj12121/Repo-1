package com.maventest.mavens;

import java.io.File;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


/*public class ExtentManager {
	
	public static ExtentReports extent;
	public static ExtentTest test;
	
	@BeforeTest
	public void startTest(){
		extent= new ExtentReports("C:\\Tosca\\ExtentReports\\ExtentReport.html",true);
		extent.addSystemInfo("Host Name", "localhost");
		extent.addSystemInfo("Environment", "QA-Selenium");
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\ReportConfig.xml"));
		
	}
	@Test
	public void demoReportPass(){
		test= extent.startTest("demoTest");
		Assert.assertTrue(true, "test Passed");
		test.log(LogStatus.PASS, "Values Matching");
		
	}
	@Test
	public void demoReportFail(){
		test= extent.startTest("demoTest");
		Assert.assertTrue(false, "test Failed");
		test.log(LogStatus.FAIL, "Values MissMatching");
		
	}
	@AfterMethod
	public void getResult(ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE){
			test.log(LogStatus.FAIL, result.getThrowable());
		}
		extent.endTest(test);
	}
	
	@AfterTest
	public void endReport(){
		extent.flush();
	}

}
*/