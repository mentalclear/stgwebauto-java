package stg.challenges;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge3 {
    public WebDriver driver;
    public WebDriverWait driverWait;

    @BeforeSuite
    public void startSuite() {
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, 10);
    }

    @Test(priority = 1)
    public void openCopartAndVerifyLocation() {
        driver.navigate().to("https://copart.com");
        Assert.assertEquals(driver.getTitle(),
                "Salvage Cars for Sale | Online Used Car Auctions - Copart Auto Auction");
    }

    /* Challenge 3: Go to copart and print a list of all the “Popular Items” of vehicle Make/Models on the home page
       and the URL/href for each type. This list can dynamically change depending on what is authored by the content
       creator but using a loop will make sure that everything will be displayed regardless of the list size. */
    @Test(priority = 2)
    public void printListOfPopularMakes() {
        String makesSectionHeader = "//h2[@class='bold text-center blue-heading section-header']";

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(makesSectionHeader)));
        WebElement popularVehicles = driver.findElement(
                By.xpath(makesSectionHeader));
        Assert.assertEquals(popularVehicles.getText(), "Copart Auto Auction - Salvage & Used Cars for Sale");

        List<WebElement> makesList = driver.findElements(By.xpath("//span[@class='make-items']//a"));
        Map<String, String> makeUrls = new HashMap<>();
        for (WebElement element : makesList) {
            if(element.getText().contains("VIEW MORE")) continue;
            makeUrls.put(element.getText(), element.getAttribute("href"));
        }
        for (Map.Entry<String,String> singleEntry : makeUrls.entrySet()) {
            System.out.println(singleEntry.getKey().toUpperCase() + " - " + singleEntry.getValue());
        }
    }

    @AfterSuite
    public void stopSuite() {
        driver.quit();
    }
}
