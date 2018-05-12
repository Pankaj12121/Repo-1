package com.maventest.mavens;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CBT extends ExtentTestNGIReporterListener {
	String browserType;
	WebDriver driver;
	String baseUrl = "http://opensource.demo.orangehrmlive.com/";
	
	@Parameters("browserType") /* browserType parameter is test case parameter defined in testng.xml. */
	@Test
	public void setup(String browserType) throws MalformedURLException 
	{
		this.browserType = browserType;
		if("Firefox".equalsIgnoreCase(browserType))
		{
			String GridURL="http://localhost:4444/wd/hub";
			DesiredCapabilities capability = new DesiredCapabilities();
			capability = DesiredCapabilities.firefox();
			driver = new RemoteWebDriver(new URL(GridURL), capability);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			login();
			driver.close();
		}else if("Chrome".equalsIgnoreCase(browserType))
		{
			String GridURL="http://localhost:4444/wd/hub";
			DesiredCapabilities capability = new DesiredCapabilities();
			capability = DesiredCapabilities.chrome();
			driver = new RemoteWebDriver(new URL(GridURL), capability);
			driver.manage().window().maximize();
			driver.get(baseUrl);
			login();
			driver.close();
			
		}
	
	}
	public void login(){
		WebElement userName= driver.findElement(By.id("txtUsername"));//By id 	
		WebElement password= driver.findElement(By.name("txtPassword"));//By name 
		WebElement submit= driver.findElement(By.className("button"));//By name 	
		userName.sendKeys("Admin");
		Report("UserName entered "+ "Admin");
		System.out.println("UserName entered "+ "Admin");
		password.sendKeys("admin");
		Report("Password entered "+ "admin");
		System.out.println("Password entered "+ "admin");
		submit.click();
		Report("submit button clicked");
		System.out.println("submit button clicked");
		assertEquals(0, 1);
	}
	public void Report(String s){
		Reporter.log(s);
	
	}
	
	
	//@BeforeSuite
	public void beforeSuite(){
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		String time = dateFormat.format(now);
		File dir = new File("C:\\Tosca\\ExtentReports\\"+"ExtentReport_"+time+".html");
		String ReportPath= dir.toString();
		//extent= new ExtentReports(ReportPath,true);
    	//extent.loadConfig(new File(System.getProperty("user.dir")+"\\ReportConfig.xml"));
		//test= extent.startTest(browserType);
		

	}

	
}
