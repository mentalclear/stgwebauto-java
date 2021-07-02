package challenge2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class Challenge2 {
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
    public void openCopart() {
        driver.get("https://copart.com");
    }

    @Test(priority = 2)
    public void verifyLocationCopart() {
        Assert.assertEquals(driver.getTitle(), "Salvage Cars for Sale | Online Used Car Auctions - Copart Auto Auction");
    }

    @Test(priority = 3)
    public void searchForPorscheInExotics() {
        WebElement searchBar = driver.findElement(By.xpath("//input[@id='input-search']"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default search-icon']"));
        WebElement searchResultHeader;
        String searchResultHeaderTerm = "//h1[@id='searchResultsHeader' and @data-uname='searchResultsHeader']";

        searchBar.click();
        searchBar.clear();
        searchBar.sendKeys("exotics");
        searchButton.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchResultHeaderTerm)));
        searchResultHeader = driver.findElement(By.xpath(searchResultHeaderTerm));
        Assert.assertTrue(searchResultHeader.getText().contains("Search Results for exotics"));

        List<WebElement> exoticsSearchResults = driver.findElements(By.xpath("//span[@data-uname='lotsearchLotmake']"));
        List<String> listOfFundExotics = new ArrayList<>();
        for (WebElement singleResult : exoticsSearchResults) {
            listOfFundExotics.add(singleResult.getText());
        }
        Assert.assertTrue(listOfFundExotics.contains("PORSCHE"));
    }

    @AfterSuite
    public void stopSuite() {
        driver.quit();
    }
}