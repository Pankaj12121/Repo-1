package com.maventest.mavens;


import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.maventest.mavens.frameworkCore.BrowserInsatnce;
import com.maventest.mavens.frameworkCore.WebDriverWrapper;

public class TestCase1 {
	WebDriver driver ;
	
	String userNameText="Admin";
	String passwardText="admin";
	WebDriverWrapper wrapper;

	@Test(groups={"Regression"})
	@Parameters("browsername") 
	public void testCase1(String browsername) throws MalformedURLException {
	
		BrowserInsatnce instance= new BrowserInsatnce();
		if (browsername==null){
			browsername="chrome";	
			this.driver= instance.setup(browsername);
		}else{
			this.driver= instance.setup(browsername);
		}
		wrapper= new WebDriverWrapper(driver);
		wrapper.Report("<B><Font Color = \"black\">"+ "Driver Launched :" + browsername+" </Font></B>");
		String baseUrl = "http://opensource.demo.orangehrmlive.com/";
		String expectedTitle = "OrangeHRM1";
		String actualTitle = "";
		String ExpectedWelcomeUser="Welcome Admin";
		driver.get(baseUrl);
		wrapper.Report("Navigated to"+baseUrl);
		actualTitle = driver.getTitle();
		login();
		WebElement welcome= driver.findElement(By.linkText("Welcome Admin"));
		String ActualWelcomeUser= welcome.getText();
		try{
			//verify(actualTitle, expectedTitle);

			//verify(ActualWelcomeUser, ExpectedWelcomeUser);
			/*....................................Verify Vs Assert.........................................................	
			 */
			//hardAssert(ActualWelcomeUser, ExpectedWelcomeUser);

			//hardAssert(actualTitle, expectedTitle);

			wrapper.assertEqual(ActualWelcomeUser, ExpectedWelcomeUser);

			wrapper.assertEqual(actualTitle, expectedTitle);

		}catch(Exception e){

			wrapper.Report("<B><Font Color = \"black\">"+ "Test case execution stops as checkpoint failed- HardAssert used :" + browsername+" </Font></B>");
			System.out.println("Test case execution stops as checkpoint failed- HardAssert used");

		}
		exitApplication();
	}


	public void login(){
		WebElement userName= driver.findElement(By.id("txtUsername"));//By id 	
		WebElement password= driver.findElement(By.name("txtPassword"));//By name 
		WebElement submit= driver.findElement(By.className("button"));//By name 

		userName.sendKeys(userNameText);
		wrapper.Report("<B><Font Color = \"black\">"+ "UserName entered ::" + userNameText+" </Font></B>");
		System.out.println("UserName entered "+ "Admin");
		password.sendKeys(passwardText);
		wrapper.Report("<B><Font Color = \"black\">"+ "Password entered ::" + passwardText+" </Font></B>");
		System.out.println("Password entered "+ "admin");
		submit.click();
		wrapper.Report("<B><Font Color = \"black\">"+ "Submit buttin Clicked"+" </Font></B>");
		System.out.println("submit button clicked");
	}

	public void exitApplication(){
		driver.close();

		wrapper.Report("<B><Font Color = \"black\">"+ "TestCase Executed Successfully"+" </Font></B>");
		System.out.println("Testcase1 execution ended");

	}
	
	
}


