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

    // Challenge 3
    @Test(priority = 2)
    public void printListOfPopularModels() {
        String modelsSectionHeader = "//h2[@class='bold text-center blue-heading section-header']";

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(modelsSectionHeader)));
        WebElement popularVehicles = driver.findElement(
                By.xpath(modelsSectionHeader));
        Assert.assertEquals(popularVehicles.getText(), "Copart Auto Auction - Salvage & Used Cars for Sale");

        WebElement modelsTab = driver.findElement(By.xpath("//a[@data-toggle='tab' and @href='#tabModels']"));
        modelsTab.click();

        List<WebElement> modelsList = driver.findElements(By.xpath("//span[@class='items']//a"));
        Map<String, String> modelUrls = new HashMap<>();
        for (WebElement element : modelsList) {
            if(element.getText().contains("VIEW MORE")) continue;
            modelUrls.put(element.getText(), element.getAttribute("href"));
        }
        for (Map.Entry<String,String> singleEntry : modelUrls.entrySet()) {
            System.out.println(singleEntry.getKey().toUpperCase() + " - " + singleEntry.getValue());
        }
    }

    @AfterSuite
    public void stopSuite() {
        driver.quit();
    }
}
