package com.seleniumautomation;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumTest2 {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup(); 
		WebDriver driver = (WebDriver) new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		try {
	            // 1. Navigate to BH home page
	            driver.get("https://www.brighthorizons.com/");

	            // 2. Click on Find a Center option
	            WebElement findCenterLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Find a Center")));
	            findCenterLink.click();

	            // 3. Verify that newly opened page contains "/child-care-locator" as part of its URL
	            wait.until(ExpectedConditions.urlContains("/child-care-locator"));
	            String currentUrl = driver.getCurrentUrl();
	            if (currentUrl.contains("/child-care-locator")) {
	                System.out.println("URL contains /child-care-locator");
	            } else {
	                System.out.println("URL verification failed. Current URL: " + currentUrl);
	            }

	            // 4. Type "New York" into search box and press Enter
	            WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("locator-search-box")));
	            searchBox.sendKeys("New York");
	            searchBox.submit();

	            // 5. Verify if the number of found centers matches the number displayed in the list
	            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("results-count")));
	            WebElement resultsCountElement = driver.findElement(By.className("results-count"));
	            String resultsCountText = resultsCountElement.getText().replaceAll("[^0-9]", "");
	            int resultsCount = Integer.parseInt(resultsCountText);

	            List<WebElement> centerList = driver.findElements(By.className("center-list-item"));
	            int displayedCentersCount = centerList.size();

	            if (resultsCount == displayedCentersCount) {
	                System.out.println("The number of found centers matches the displayed count: " + resultsCount);
	            } else {
	                System.out.println("Mismatch in counts. Found: " + resultsCount + ", Displayed: " + displayedCentersCount);
	            }

	            // 6. Click on the first center on the list
	            WebElement firstCenter = centerList.get(0);
	            String listCenterName = firstCenter.findElement(By.className("center-name")).getText();
	            String listCenterAddress = firstCenter.findElement(By.className("center-address")).getText();
	            firstCenter.click();

	            // 7. Verify if center name and address are the same on the list and popup
	            WebElement popup = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("center-popup")));
	            String popupCenterName = popup.findElement(By.className("popup-center-name")).getText();
	            String popupCenterAddress = popup.findElement(By.className("popup-center-address")).getText();

	            if (listCenterName.equals(popupCenterName) && listCenterAddress.equals(popupCenterAddress)) {
	                System.out.println("Center name and address match successfully.");
	            } else {
	                System.out.println("Mismatch in center name or address.");
	                System.out.println("List Name: " + listCenterName + ", Popup Name: " + popupCenterName);
	                System.out.println("List Address: " + listCenterAddress + ", Popup Address: " + popupCenterAddress);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            // Close the browser
	            driver.quit();
	        }
	    }
	}