package com.maventest.mavens;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.TestRunner;

public class TestCase3 {
	static WebDriver driver ;
	public static void main(String[] args) throws MalformedURLException {
		String GridURL="http://localhost:4444/wd/hub";
		//System.setProperty("webdriver.chrome.driver","C:\\Grid\\chromedriver.exe");
		//driver = new ChromeDriver();
		DesiredCapabilities capability = new DesiredCapabilities();
		capability = DesiredCapabilities.chrome();
		capability.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		
		ArrayList<String> excludeSwitches = new ArrayList<String>();
		excludeSwitches.add("ignore-certificate-errors"); 
		options.setExperimentalOption("excludeSwitches", excludeSwitches);		
		capability.setCapability(ChromeOptions.CAPABILITY, options);
		
		Report("Driver Launched");
		String baseUrl = "http://opensource.demo.orangehrmlive.com/";
		String expectedTitle = "OrangeHRM1";
		String actualTitle = "";
		String ExpectedWelcomeUser="Welcome Admin1";
		
		driver = new RemoteWebDriver(new URL(GridURL), capability);
		driver.get(baseUrl);
		Report("Navigated to"+baseUrl);
		actualTitle = driver.getTitle();
		login();
		WebElement welcome= driver.findElement(By.linkText("Welcome Admin"));
		String ActualWelcomeUser= welcome.getText();
		try{
			//verify(actualTitle, expectedTitle);

			//verify(ActualWelcomeUser, ExpectedWelcomeUser);
			/* ....................................Verify Vs Assert.........................................................	*/

			//hardAssert(ActualWelcomeUser, ExpectedWelcomeUser);

			//hardAssert(actualTitle, expectedTitle);

			softAssert(ActualWelcomeUser, ExpectedWelcomeUser);

			softAssert(actualTitle, expectedTitle);

		}catch(Exception e){

			Report("Test case execution stops as checkpoint failed- HardAssert used");

		}
		exitApplication();
	}

	public void verify(String actualTitle, String expectedTitle){

		if (actualTitle.contentEquals(expectedTitle)){
			Report("Test Passed!");
		} else {
			Report("Test Failed");
		}
		Report("Verify continues the execution even if checkpoint get failed");

	}

	public void hardAssert(String actualTitle, String expectedTitle){

		Assert.assertEquals(actualTitle, expectedTitle);

	}
	public static void softAssert(String actualTitle, String expectedTitle){
		try{

			Assert.assertEquals(actualTitle, expectedTitle);

		}catch(AssertionError e){

			e.printStackTrace();

		}
	}

	public static void login(){
		WebElement userName= driver.findElement(By.id("txtUsername"));//By id 	
		WebElement password= driver.findElement(By.name("txtPassword"));//By name 
		WebElement submit= driver.findElement(By.className("button"));//By name 	
		userName.sendKeys("Admin");
		Report("UserName entered "+ "Admin");
		password.sendKeys("admin");
		Report("Password entered "+ "admin");
		submit.click();
		Report("submit button clicked");
	}

	public static void exitApplication(){
		driver.close();

		Report("Test case executed successfully");

	}

	public static void Report(String s){

		Reporter.log(s);

	}

	public void GenReport(ITestContext ctx){

		System.out.println("In after suite");
		TestRunner runner = (TestRunner) ctx;
		runner.setOutputDirectory("C:\\Tosca");


	}


}
