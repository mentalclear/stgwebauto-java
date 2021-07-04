package challenge5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.*;

public class Challenge5 {
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

    // Challenge 2: write a script that will go to copart.com, search for exotics and verify porsche
    // is in the list of cars.  Use the hard assertion for this challenge.
    @Test(priority = 3)
    public void searchForPorscheInExotics() {
        WebElement searchBar = driver.findElement(By.xpath("//input[@id='input-search']"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default search-icon']"));
        String searchResultHeaderTerm = "//h1[@id='searchResultsHeader' and @data-uname='searchResultsHeader']";

        searchBar.click();
        searchBar.sendKeys("porsche");
        searchButton.click();

        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchResultHeaderTerm)));
        WebElement searchResultHeader = driver.findElement(By.xpath(searchResultHeaderTerm));
        Assert.assertTrue(searchResultHeader.getText().contains("Search Results for "));

        WebElement itemsPerPage = driver.findElement(By.xpath("//select[@name='serverSideDataTable_length']"));
        Select select = new Select(itemsPerPage);
        select.selectByVisibleText("100");

        driverWait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='dataTables_info'][1]"),"Showing 1 to 100 "));
        List<WebElement> porscheResultModels = driver.findElements(By.xpath("//span[@data-uname='lotsearchLotmodel']"));
        Set<String> porscheModels = new HashSet<>();
        for(WebElement model : porscheResultModels) {
            if(model.getText().equals("")) continue;
            porscheModels.add(model.getText());
        }


        System.out.println("Total Porsche models on the page: " + porscheModels.size());
    }

    @AfterSuite
    public void stopSuite() {
        //driver.quit();
    }
}
