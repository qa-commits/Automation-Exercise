package com.primepay.seleniumautomation.base;

	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import java.time.Duration;

	public class ReusableMethods {
	    private WebDriver driver;
	    private WebDriverWait wait;
	    private Actions actions;

	    public ReusableMethods(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        this.actions = new Actions(driver);
	    }

	    public void clickElement(WebElement element) {
	        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	    }

	    public void hoverOverElement(WebElement element) {
	        actions.moveToElement(element).perform();
	    }

	    public String getPageTitle() {
	        return driver.getTitle();
	    }

	    public boolean isElementDisplayed(WebElement element) {
	        try {
	            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }
	}

	
	
