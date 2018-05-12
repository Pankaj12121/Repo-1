package com.maventest.mavens.frameworkCore;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class BrowserInsatnce {


	WebDriver driver;

	public  WebDriver driverInsatnce(){


		return driver;
	}

	public WebDriver setup(String browserType) throws MalformedURLException 
	{
		if("Firefox".equalsIgnoreCase(browserType))
		{
			String GridURL="http://localhost:4444/wd/hub";
			DesiredCapabilities capability = new DesiredCapabilities();
			capability = DesiredCapabilities.firefox();
			driver = new RemoteWebDriver(new URL(GridURL), capability);
			driver.manage().window().maximize();

		}else if("chrome".equalsIgnoreCase(browserType))
		{
			String GridURL="http://localhost:4444/wd/hub";
			DesiredCapabilities capability = new DesiredCapabilities();
			capability = DesiredCapabilities.chrome();
			driver = new RemoteWebDriver(new URL(GridURL), capability);
			driver.manage().window().maximize();

		}


		return driver;

	}




}
