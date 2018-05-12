package com.maventest.mavens;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestNGIReporterListener implements IReporter {
	  
	    public String s;
	    private ExtentReports extent;

	    @Override
	  
		public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
	    	extent= new ExtentReports(dir(),true);
	    	extent.loadConfig(new File(System.getProperty("user.dir")+"\\ReportConfig.xml"));
	    	
	        for (ISuite suite : suites) {
	            Map<String, ISuiteResult> result = suite.getResults();
	            
	            
	            
	            for (ISuiteResult r : result.values()) {
	                ITestContext context = r.getTestContext();
	                
	                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
	                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
	                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
	                
	            }
	        }
	        
	        for (String s : Reporter.getOutput()) {
	            extent.setTestRunnerOutput(s);
	        }
	        
	        extent.flush();
	    }
	    
	  /*  private void init() {
	        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
	        htmlReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");
	        htmlReporter.config().setReportName("ExtentReports - Created by TestNG Listener");
	        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        extent.setReportUsesManualConfiguration(true);
	    }*/
	    
	    private void buildTestNodes(IResultMap tests, LogStatus status) {
	        ExtentTest test;
	        
	        if (tests.size() > 0) {
	            for (ITestResult result : tests.getAllResults()) {
	                test = extent.startTest(result.getMethod().getMethodName());
	               
	                test.setStartedTime(getTime(result.getStartMillis()));
	                test.setEndedTime(getTime(result.getEndMillis()));
	                
	                for (String group : result.getMethod().getGroups())
	                    test.assignCategory(group);
	                
	                if (result.getThrowable() != null) {
	                    test.log(status, result.getThrowable());
	                    String screenShotPath= System.getProperty("user.dir") + File.separator + "//screenshots//"+"1.png";
	                    test.log(LogStatus.INFO,"screenshot below :"+ test.addScreenCapture("1.png"));
	                   
	                }
	                else {
	                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
	                }
	              
	            }
	        }
	    }
	    
	    private Date getTime(long millis) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(millis);
	        return calendar.getTime();      
	    }

	    public String dir(){
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
			String time = dateFormat.format(now);
			File dir = new File("C:\\Tosca\\ExtentReports\\"+"ExtentReport_"+time+".html");
			s= dir.toString();
			System.out.println(s);
			//dir.mkdir();
			return s;
		}
	}


