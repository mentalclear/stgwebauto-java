package stg.challenges;

import io.restassured.http.Cookies;
import io.restassured.response.ValidatableResponse;
import kong.unirest.Unirest;
import org.openqa.selenium.Cookie;
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
import java.util.Set;

import static io.restassured.RestAssured.given;

public class Challenge9RATest {
    public WebDriver driver;
    public WebDriverWait driverWait;

    @BeforeSuite
    public void startSuite() {
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, 10);
    }

    @Test (priority = 1)
    public void testCopartResponses() {
        driver.get("https://copart.com");
        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>();
        for (org.openqa.selenium.Cookie cookie : seleniumCookies) {
            restAssuredCookies.add(new io.restassured.http.Cookie.Builder(cookie.getName(), cookie.getValue()).build());
        }

        String endpointURL = "https://www.copart.com/public/lots/search";
        ValidatableResponse response = given()
                .cookies(new Cookies(restAssuredCookies))
                .header("accept", "application/json")
                .queryParam("query", "Toyota Camry")
                .when()
                .post(endpointURL)
                .then();

        response.log().body();
    }



    //@Test(priority = 1)
    public void logModelResponseBodyToFile() {
        String endpointURL = "https://www.copart.com/public/lots/search";

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
           Assert.assertEquals(testForServerResponse(model, endpointURL), 200);
           Assert.assertTrue(resultRequestToFile(model, endpointURL).isFile());
           verifyingTheResponse(model, endpointURL);
        }
    }

    private int testForServerResponse(String searchQuery, String endpoint) {
        return Unirest.post(endpoint)
                .header("accept", "application/json")
                .queryString("query", searchQuery)
                .asEmpty()
                .getStatus();
    }

    public File resultRequestToFile(String searchQuery, String endpoint) {
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

        File result = Unirest.post(endpoint)
                .header("accept", "application/json")
                .queryString("query", searchQuery)
                .asFile(searchQueryFileName)
                .getBody();
        System.out.println("Request data saved into a file: " + result);

        return result;
    }

    public void verifyingTheResponse(String searchQuery, String endpoint)  {
        String result = Unirest.post(endpoint)
                .header("accept", "application/json")
                .queryString("query", searchQuery)
                .asString()
                .getBody();
        // System.out.println(result);

        // parsing file "JSONExample.json"
//        Object obj = new JSONParser().parse(result);
//
//        // typecasting obj to JSONObject
//        JSONObject jo = (JSONObject) obj;
//
//        // getting lotNumberStr
//        String lotNumberStr = (String) jo.get("lotNumberStr");
//
//        System.out.println(lotNumberStr);

    }

    @AfterSuite
    public void stopSuite() {
       driver.quit();
       //Unirest.shutDown();
    }
}
