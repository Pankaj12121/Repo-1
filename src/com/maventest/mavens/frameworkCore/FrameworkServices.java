package com.maventest.mavens.frameworkCore;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class FrameworkServices {
	WebDriver driver;

	public FrameworkServices(WebDriver driver){
	this.driver=driver;
	}

	WebDriverWrapper wrapper= new WebDriverWrapper();
	

	public String getText(PageElement pageElement) {
		String text = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			text = webElement.getText().trim();

			wrapper.Report("Fetched text: " + text + " of " + pageElement.getName());
			//frameworkServices.logMessage("Fetched text: " + text + " of " + pageElement.getName(), logger);
		} catch (Exception exception) {

		} finally {
			pageElement = null;
		}
		return text;
	}
	public String getValue(PageElement pageElement) {
		String text = new String();
		try {
			WebElement webElement;
			if (!isWebElementAvailableInPageElement(pageElement))
				webElement = getWebElement(pageElement);
			else
				webElement = pageElement.getWebElement();

			text = webElement.getAttribute("value").trim();

			wrapper.Report("Fetched text: " + text + " of " + pageElement.getName());
			//frameworkServices.logMessage("Fetched text: " + text + " of " + pageElement.getName(), logger);
		} catch (Exception exception) {

		} finally {
			pageElement = null;
		}
		return text;
	}

	private boolean isWebElementAvailableInPageElement(PageElement pageElement) {
		return !(pageElement.getWebElement() == null);
	}

	protected WebElement getWebElement(PageElement pageElement) {
		if (pageElement.isSlowLoadableComponent()) {
			return waitForElementAndReturnElement(pageElement);
		} else
			return driver.findElement(pageElement.getBy());
	}

	private WebElement waitForElementAndReturnElement(PageElement pageElement) {
		switch (pageElement.getWaitType()) {
		case WAITFORELEMENTTOBECLICKABLE:
			try {
				return wrapper.waitForElementToBeClickable(pageElement.getBy(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				wrapper.Report (pageElement.getName() + " not loaded within specified wait time of ");
			}

		case WAITFORELEMENTTOBEEENABLED:
			try {
				return wrapper.waitForElementToBeEnabled(pageElement.getBy(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				wrapper.Report(pageElement.getName() + " was disabled beyond specified wait time ");
			}

		case WAITFORELEMENTTOBEDISPLAYED:
			try {
				return wrapper.waitForElementToBeDisplayed(pageElement.getBy(), pageElement.getTimeOut());
			} catch (TimeoutException e) {
				wrapper.Report(pageElement.getName() + " was not displayed in specified wait time ");
			}

		default:
			return driver.findElement(pageElement.getBy());
		}
	}
	
}