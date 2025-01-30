package com.seleniumautomation;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumTest1 {


	    public static void main(String[] args) {
	        // Set up WebDriver and initialize ChromeDriver
			WebDriverManager.chromedriver().setup(); 
			WebDriver driver = (WebDriver) new ChromeDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			

	        try {
	            // 1. Navigate to BH home page
	            driver.get("https://www.brighthorizons.com/");

	            // 2. Click on search/loop icon (top, right corner)
	            WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.className("search-icon")));
	            searchIcon.click();

	            // 3. Verify if search field is visible on the page
	            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("site-search")));
	            if (searchField.isDisplayed()) {
	                System.out.println("Search field is visible.");
	            } else {
	                System.out.println("Search field is not visible.");
	                return;
	            }

	            // 4. Type "Employee Education in 2018: Strategies to Watch" into the search field and click on Search button
	            String searchQuery = "Employee Education in 2018: Strategies to Watch";
	            searchField.sendKeys(searchQuery);
	            WebElement searchButton = driver.findElement(By.className("search-button"));
	            searchButton.click();

	            // 5. Verify if the first search result is an exact match to what you typed into search
	            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("search-results")));
	            WebElement firstResult = driver.findElement(By.cssSelector(".search-results .result-item:first-child .result-title"));
	            String firstResultText = firstResult.getText();

	            if (firstResultText.equals(searchQuery)) {
	                System.out.println("The first search result is an exact match: " + firstResultText);
	            } else {
	                System.out.println("The first search result does not match. Expected: " + searchQuery + ", Found: " + firstResultText);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            // Close the browser
	            driver.quit();
	        }
	    }
	}
