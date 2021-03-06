package stg.challenges;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

import static stg.utils.Helpers.*;

public class Challenge6 {
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

    // Challenge 6:
    @Test(priority = 2)
    public void searchForNissanModels() {
        WebElement searchBar = driver.findElement(By.xpath("//input[@id='input-search']"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default search-icon']"));
        String searchResultHeaderTerm = "//h1[@id='searchResultsHeader' and @data-uname='searchResultsHeader']";
        String searchTerm = "nissan";

        searchBar.click();
        searchBar.sendKeys(searchTerm);
        searchButton.click();

        waitForSpinnerToClose(driverWait);
        WebElement searchResultHeader = driver.findElement(By.xpath(searchResultHeaderTerm));
        Assert.assertTrue(searchResultHeader.getText().contains("Search Results for " + searchTerm));
    }

    @Test(priority = 3)
    public void selectModelSkyline() throws IOException {
        String modelInQuestion = "Skyline";
        WebElement modelToggle = driver.findElement(By.xpath("//a[@data-uname='ModelFilter']"));
        modelToggle.click();

        WebElement modelSearchBar = driver.findElement(By.xpath("//a[@data-uname='ModelFilter']/ancestor::li//form//input"));
        modelSearchBar.sendKeys(modelInQuestion);

        WebElement nissanSkylineCheckbox;
        try {
            nissanSkylineCheckbox = driver.findElement(By.xpath("//abbr[contains(text(),'Skyline')]"));
            nissanSkylineCheckbox.click();
            System.out.println("Model " + modelInQuestion + " found...");
            Assert.assertTrue(nissanSkylineCheckbox.getText().contains(modelInQuestion));
        } catch (NoSuchElementException e) {
            System.out.println("Model " + modelInQuestion + " not found" );
            takeScreenshot(driver,"./report/screenshots/image" + addTimeStamp() + ".png");
            e.getMessage();
        }
    }

    @AfterSuite
    public void stopSuite() {
        driver.quit();
    }
}
