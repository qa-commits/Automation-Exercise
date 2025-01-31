package com.primepay.seleniumautomation.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.primepay.seleniumautomation.base.ReusableMethods;

import java.time.Duration;


public class PrimePayTest {
	protected WebDriver driver;
	protected ExtentReports extent;
	protected ExtentTest test;
	protected ReusableMethods reusableMethods;

	@BeforeClass
	@Parameters({ "browser", "url" })
	public void setup(String browser, String url) {
		driver = initializeDriver(browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
		reusableMethods = new ReusableMethods(driver);

		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		test = extent.createTest("Flipkart Automation Test");
	}

	public WebDriver initializeDriver(String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			return new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			return new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		extent.flush();
	}
}




