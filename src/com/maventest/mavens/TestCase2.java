package com.maventest.mavens;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ISuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.maventest.mavens.frameworkCore.BrowserInsatnce;
import com.maventest.mavens.frameworkCore.FrameworkServices;
import com.maventest.mavens.frameworkCore.PageElement;
import com.maventest.mavens.frameworkCore.WaitType;
import com.maventest.mavens.frameworkCore.WebDriverWrapper;

public class TestCase2 {
	WebDriver driver ;

	FrameworkServices frameworkServices;
	String browserType;
	String userNameText="Admin";
	String passwardText="admin";
	String OrganizationName="OrangeHRM (Pvt) Ltd";
	String TaxID="123456";
	String NumberOfEmp="32";
	String RegNumber="A23456";
	String Email="info@orangehrm.com";
	String Address1="538 Teal Plaza";
	String City="Secaucus";
	String Country="United states";
	PageElement orgName;
	PageElement taxID;
	PageElement numberOfemp;
	PageElement regNo;
	PageElement email;
	PageElement address1;
	PageElement city;
	PageElement country;

	WebDriverWrapper wrapper;

	@Test(groups={"Regression"})
	@Parameters("browsername") 
	public void testCase2(String browsername) throws MalformedURLException {

		BrowserInsatnce instance= new BrowserInsatnce();
		if (browsername==null){
			browsername="chrome";	
			this.driver= instance.setup(browsername);
		}else{
			this.driver= instance.setup(browsername);
		}
		wrapper = new WebDriverWrapper(driver);
		frameworkServices= new FrameworkServices(driver);
		wrapper.Report("<B><Font Color = \"black\">"+ "Driver Launched :" + browserType+" </Font></B>");
		String baseUrl = "http://opensource.demo.orangehrmlive.com/";
		driver.get(baseUrl);
		wrapper.Report("Navigated to"+baseUrl);


		try{
			login();
			navigateToUserMgmt();
			usermgmt();
			verifyUserMgmt();
			exitApplication();
		}catch(Exception e){

			wrapper.Report("<B><Font Color = \"black\">"+ "Test case execution stops as checkpoint failed- HardAssert used :" + browserType+" </Font></B>");
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

	public void usermgmt(){
		
		orgName= new PageElement(By.xpath("//input[@id='organization_name']"),"organization name", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		taxID= new PageElement(By.xpath("//input[@id='organization_taxId']"),"organization tax ID", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		numberOfemp= new PageElement(By.xpath("//span[@id='numOfEmployees']"),"organization tax ID", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		regNo= new PageElement(By.xpath("//input[@id='organization_registraionNumber']"),"organization registration number", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		email= new PageElement(By.xpath("//input[@id='organization_email']"),"organization email", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		address1= new PageElement(By.xpath("//input[@id='organization_street1']"),"organization Address", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		city= new PageElement(By.xpath("//input[@id='organization_city']"),"organization city", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);
		country= new PageElement(By.xpath("//select[@id='organization_country']"),"organization country", false, WaitType.WAITFORELEMENTTOBEDISPLAYED,10);

	}

	public void navigateToUserMgmt(){
		By adminMenu=By.xpath("//a[@id='menu_admin_viewAdminModule']/b");
		By organization=By.linkText("Organization");//Admin menu
		By generalInformation=By.xpath("//a[contains(text(),'General Information')]");//Admin menu//Organization

		WebElement level1=driver.findElement(adminMenu);
		Actions builder = new Actions(driver);
		builder.moveToElement( level1 ).perform();
		WebElement level2=driver.findElement(organization);
		builder.moveToElement( level2 ).perform();;
		WebElement level3=driver.findElement(generalInformation);
		builder.click(level3).perform();
		//builder.build().perform();

	}
	public void verifyUserMgmt(){
		
		wrapper.assertEqual(OrganizationName, frameworkServices.getValue(orgName));
		wrapper.assertEqual(TaxID, frameworkServices.getText(taxID));
		wrapper.assertEqual(NumberOfEmp,frameworkServices.getValue(numberOfemp));
		wrapper.assertEqual(RegNumber, frameworkServices.getValue(regNo));
		wrapper.assertEqual(Email,frameworkServices.getValue(email));
		wrapper.assertEqual(Address1, frameworkServices.getValue(address1));
		wrapper.assertEqual(City, frameworkServices.getValue(city));
		wrapper.assertEqual(Country, frameworkServices.getValue(country));


	}
	public void exitApplication(){
		driver.close();
		wrapper.Report("<B><Font Color = \"black\">"+ "TestCase Executed Successfully"+" </Font></B>");
		System.out.println("Testcase1 execution ended");

	}

	public void generateReport(List<ISuite> suites, String outputDirectory){
		
	}

}

