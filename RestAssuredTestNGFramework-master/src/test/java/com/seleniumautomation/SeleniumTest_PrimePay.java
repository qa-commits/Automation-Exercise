package com.seleniumautomation;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.time.Duration;
import java.util.List;

public class SeleniumTest_PrimePay {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private ExtentReports extent;
    private ExtentTest test;
    
    @BeforeClass
    @Parameters("browser")
    public void setup(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);
        driver.manage().window().maximize();
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }
    
    @Test(dataProvider = "urlProvider")
    public void testFlipkartNavigation(String url) {
        test = extent.createTest("Flipkart Navigation Test");
        driver.get(url);
        
        // Validate Page Title
        String expectedTitle = "Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!";
        Assert.assertEquals(driver.getTitle(), expectedTitle, "Page title verification failed");
        
        test.pass("Page title verified");
        
        // Now I'll Close login popup if it appears as an edge case
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
            closeButton.click();
            test.info("Closed login popup");
        } catch (Exception e) {
            test.info("No login popup appeared");
        }
        
        // Now, I'll hover over Electronics menu
        WebElement electronicsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Electronics']")));
        actions.moveToElement(electronicsMenu).perform();
        test.pass("Hovered over Electronics menu");
        
        // Now, I'll extract submenu items dynamically
        List<WebElement> subMenus = driver.findElements(By.xpath("//span[text()='Electronics']/ancestor::li//ul/li/a"));
        for (WebElement submenu : subMenus) {
            System.out.println("Submenu: " + submenu.getText());
            test.info("Submenu found: " + submenu.getText());
        }
    }
    
    @DataProvider(name = "urlProvider")
    public Object[][] getUrl() {
        return new Object[][] { { "https://www.flipkart.com/" } };
    }
    
    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
}
