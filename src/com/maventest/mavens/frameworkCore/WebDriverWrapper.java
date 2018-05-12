package com.maventest.mavens.frameworkCore;

import static org.testng.Assert.assertEquals;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;


public class WebDriverWrapper {
	WebDriver driver;
	static int counter=0;
	String errorMessage;
	boolean warning;
	AssertionType assertType= AssertionType.WARNING;
	File SnapShotFolderPath= new File ("C:\\Tosca\\ScreenShot\\");
	WebDriverWrapper wrapper;

	public WebDriverWrapper(WebDriver driver){
		this.driver=driver;
		wrapper = new WebDriverWrapper();
	}
	public WebDriverWrapper(){}
	public void assertEqual(String expected, String actual) {
		counter++;
		Report("-----------------------------------------------------------------------------------------------------------");
		try {

			if (actual == null) actual = "DEFAULTED TO NULL OBJECT"; ;
			if (expected == null) expected = "DEFAULTED TO NULL OBJECT";;
			assertEquals(actual, expected);
			Report("<B><Font Color = \"green\">"+counter+".  Actual= "+actual+";  Expected= "+expected+" ; PASSED </Font></B>");
		}
		catch(AssertionError error) {
			errorMessage= error.toString();
			switch (assertType) {
			case ERROR:

				Report(errorMessage);
				break;
			case WARNING:
				warning = true;
				//FrameworkServices.logMessage("<B>"+counter+".  Actual= "+actual+";  Expected= "+expected+" ; FAILED </B>");
				Report("<B><Font Color = \"red\">"+counter+".  Actual= "+actual+";  Expected= "+expected+" ; FAILED </Font></B>");
				captureAssertionScreenShot(SnapShotFolderPath,errorMessage);

				break;
			}
		}
	}
	public void captureScreenShotOnAssertionException(File snapshotFolder, String error){


		try {
			String fileName = wrapper.captureAssertionScreenShot(snapshotFolder, error);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String captureAssertionScreenShot(File destinationFilePathLocation, String error) {
		try{
			String fileName = new String();
			//BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			WebDriver augmentedDriver = driver;
			if (!(driver instanceof InternetExplorerDriver))
				augmentedDriver = new Augmenter().augment(driver);

			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			Reporter.log("<B>"+"TestCase Failed Due to:::"+error+"</B>");
			DateFormat dateFormat = new SimpleDateFormat("hh_mm_ssaadd_MMM_yyyy");
			Date date = new Date();
			fileName = driver.getTitle().replace(":", "").replace("-", "") + dateFormat.format(date) + ".png";
			File destinationFilePath = new File((destinationFilePathLocation + File.separator + fileName));
			FileUtils.copyFile(screenshot, destinationFilePath);
			//ImageIO.write(image, "png", destinationFilePath);

			Reporter.log("<br> <a target = \"_blank\" href=\"" + destinationFilePath +"\">"+
					"<img src=\""
					+ destinationFilePath 
					+ "\" alt=\"ScreenShot Not Available\" height=\"400\" width=\"400\"> </a>");
			return fileName;

		}catch(HeadlessException e){
			e.printStackTrace();

		}catch(IOException e) {
			e.printStackTrace();

		}
		return error;

	}


	public void Report(String s){

		Reporter.log(s);

	}
	public void hardAssert(String actual, String expected) throws HeadlessException, AWTException, IOException{
		try{

			Assert.assertEquals(actual, expected);
			Report("<B><Font Color = \"green\">"+ "Actual= "+actual+";  Expected= "+expected+" ; PASSED </Font></B>");
			System.out.println("Test Passed!");

		}catch(AssertionError e){

			e.printStackTrace();
			Report("<B><Font Color = \"red\">"+ "Actual= "+actual+";  Expected= "+expected+" ; FAILED </Font></B>");
			System.out.println("Test Failed");


		}
	}
	public void softAssert(String actual, String expected) throws HeadlessException, AWTException, IOException{
		try{

			Assert.assertEquals(actual, expected);
			Report("<B><Font Color = \"green\">"+ "Actual= "+actual+";  Expected= "+expected+" ; PASSED </Font></B>");
			System.out.println("Test Passed!");

		}catch(AssertionError e){

			e.printStackTrace();
			Report("<B><Font Color = \"red\">"+ "Actual= "+actual+";  Expected= "+expected+" ; FAILED </Font></B>");
			System.out.println("Test Failed");


		}
	}

	public void verify(String actual, String expected) throws HeadlessException, AWTException, IOException{

		if (actual.contentEquals(expected)){
			Report("<B><Font Color = \"green\">"+ "Actual= "+actual+";  Expected= "+expected+" ; PASSED </Font></B>");
			System.out.println("Test Passed!");
		} else {
			Report("<B><Font Color = \"red\">"+ "Actual= "+actual+";  Expected= "+expected+" ; FAILED </Font></B>");
			System.out.println("Test Failed");

		}
		Report("Verify continues the execution even if checkpoint get failed");
		System.out.println("Verify continues the execution even if checkpoint get failed");

	}

	public WebElement waitForElementToBeDisplayed(final By by, int timeOutPeriod) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					return null;
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}
	public WebElement waitForElementToBeClickable(final By by, int timeOutPeriod) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isEnabled() && element.isDisplayed())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					return null;
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}
	public WebElement waitForElementToBeEnabled(final By by, int timeOutPeriod) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					if (element.isEnabled())
						return element;
					else
						return null;
				} 
				catch (NoSuchElementException ex) {
					return null;
				} 
				catch (StaleElementReferenceException ex) {
					return null;
				} 
				catch (NullPointerException ex) {
					return null;
				}
			}
		});
	}

	@AfterSuite(groups={"Regression"})
	public void afterSuite(){
		System.out.println("test execution finished !!! ");
	}
	@BeforeSuite(groups={"Regression"})
	public void GenTestNGReport(ITestContext ctx){
		
		System.out.println("In before suite");
		TestRunner runner = (TestRunner) ctx;
		runner.setOutputDirectory("C:\\Tosca\\TestResultNG");

	}
	
}
