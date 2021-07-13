package stg.challenges;

import kong.unirest.Unirest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Challenge8 {
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
        driver.get("https://copart.com");
        Assert.assertEquals(driver.getTitle(),
                "Salvage Cars for Sale | Online Used Car Auctions - Copart Auto Auction");

        List<String> modelsList = new ArrayList<>(Arrays.asList(
                "Toyota Camry",
                "Nissan Skyline",
                "Subaru Impreza",
                "Mitsubishi Lancer EVO",
                "Mercedes-Benz s500",
                "Ford Mustang GT",
                "Chevrolet Corvette",
                "Ford Raptor",
                "Toyota Supra",
                "Lamborghini Urus"
        ));
        for (String model : modelsList) {
           Assert.assertEquals(testForServerResponse(model), 200);
           Assert.assertTrue(resultRequestToFile(model).isFile());
        }
    }

    private int testForServerResponse(String searchQuery) {
        return Unirest.post("https://www.copart.com/public/lots/search")
                .header("accept", "application/json")
                .queryString("query", searchQuery)
                .asEmpty()
                .getStatus();
    }

    public File resultRequestToFile(String searchQuery) {
        String searchQueryFileName = "./report/files/"
                + searchQuery.toLowerCase().replaceAll("\\s", "_") + ".json";

        // Removing previously created data files if exist, Unirest will not rewrite them.
        File dataFile = new File(searchQueryFileName);
        if(dataFile.isFile()) {
            System.out.println("Previous instance of " + searchQueryFileName + " has been found in the target folder");
            System.out.println("Deleting before continue returns: " + dataFile.delete());
        } else {
            System.out.println("Request data will be dumped into file: " + searchQueryFileName);
        }

        File result = Unirest.post("https://www.copart.com/public/lots/search")
                .header("accept", "application/json")
                .queryString("query", searchQuery)
                .asFile(searchQueryFileName)
                .getBody();
        System.out.println("Request data saved into a file: " + result);

        return result;
    }

    @AfterSuite
    public void stopSuite() {
       driver.quit();
       Unirest.shutDown();
    }
}
