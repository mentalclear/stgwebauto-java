package stg.challenges;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import stg.utils.Helpers;

import java.util.*;
import java.util.stream.Collectors;

public class Challenge5 {
    public WebDriver driver;
    public WebDriverWait driverWait;
    public Helpers helpers;

    @BeforeSuite
    public void startSuite() {
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, 10);
        helpers = new Helpers();
    }

    @Test(priority = 1)
    public void openCopartAndVerifyLocation() {
        driver.navigate().to("https://copart.com");
        Assert.assertEquals(driver.getTitle(),
                "Salvage Cars for Sale | Online Used Car Auctions - Copart Auto Auction");
    }

    // Challenge 5:
    @Test(priority = 2)
    public void searchForPorscheInExotics() {
        WebElement searchBar = driver.findElement(By.xpath("//input[@id='input-search']"));
        WebElement searchButton = driver.findElement(By.xpath("//button[@class='btn btn-default search-icon']"));
        String searchResultHeaderTerm = "//h1[@id='searchResultsHeader' and @data-uname='searchResultsHeader']";

        searchBar.click();
        searchBar.sendKeys("porsche");
        searchButton.click();

        helpers.waitForSpinnerToClose(driverWait);
        WebElement searchResultHeader = driver.findElement(By.xpath(searchResultHeaderTerm));
        Assert.assertTrue(searchResultHeader.getText().contains("Search Results for "));

        WebElement itemsPerPage = driver.findElement(By.xpath("//select[@name='serverSideDataTable_length']"));
        Select select = new Select(itemsPerPage);
        select.selectByVisibleText("100");

        helpers.waitForSpinnerToClose(driverWait);
        List<WebElement> porscheResultModels = driver.findElements(By.xpath("//span[@data-uname='lotsearchLotmodel']"));
        List<String>listOfModels = porscheResultModels.stream().map(WebElement::getText).sorted().collect(Collectors.toList());
        Set<String> porscheModels = new HashSet<>();
        for(WebElement model : porscheResultModels) {
            if(model.getText().equals("")) continue;
            porscheModels.add(model.getText());
        }
        System.out.println("PART 1: MODELS");
        System.out.println("Total Porsche models on the page: " + porscheModels.size());
        System.out.println("Quantity for each model: ");
        for(String model : porscheModels){
            System.out.println(model + " - " + Collections.frequency(listOfModels, model));
        }
    }



    @AfterSuite
    public void stopSuite() {
        driver.quit();
    }
}
