package com.seleniumautomation;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumTest {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup(); 
		WebDriver driver = (WebDriver) new ChromeDriver();

		try {
			// Step 2: Navigate to ebay.com
			driver.get("https://www.ebay.com");
			driver.manage().window().maximize();

			// Step 3: then I'll Search for 'book'
			WebElement searchBox = driver.findElement(By.id("gh-ac"));
			searchBox.sendKeys("book");
			WebElement searchButton = driver.findElement(By.id("gh-btn"));
			searchButton.click();

			// Step 4: then click on the first book in the list
			// Wait for the search results to load and find the first item link
		
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement firstBookLink = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".s-item__link")));
			firstBookLink.click();

			// Step 5: then I'll click on 'Add to cart'
			// Wait for the 'Add to cart' button to appear and click it
			WebElement addToCartButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.id("atcRedesignId_btn")));
			addToCartButton.click();

			// Step 6: Then I'll verify the cart is updated
			// Wait for the cart icon to update with the number of items
			WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("gh-cart-n")));
			String cartItemCount = cartIcon.getText();

			// Then i'll use Assert class to validate that that the cart item count is updated to 1
			if (cartItemCount.equals("1")) {
				System.out.println("Test Passed: Item successfully added to cart.");
			} else {
				System.out.println("Test Failed: Cart did not get updated correctly.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the browser after the test
			driver.quit();
		}
	}
}