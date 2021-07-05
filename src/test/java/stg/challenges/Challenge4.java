package stg.challenges;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Challenge4 {
    public static WebDriver driver;
    public static WebDriverWait driverWait;

    @BeforeSuite
    public void startSuite() {
        beforeStartPrep();
    }

    public static void beforeStartPrep(){
        System.setProperty("webdriver.chrome.driver", "./bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, 10);
    }

    @Test
    void fibonacciSequencing() {
        int fibNum = 356;

        for(int i = 0; i < fibNum + 1; i++) {
            System.out.println("F("+ i +")= " + getFib(i).toString() + ", " + convertToText(getFib(i)));
            if (i > 19) {
                System.out.println("This test only supports fibonacci numbers up to 20.");
                break;
            }
        }
    }

    public Integer getFib(int n) {
        if (n <= 1)
            return n;
        else
            return getFib(n - 1) + getFib(n - 2);
    }

    // Small helper function to parse ints.
    public int parseGivenInt(int passedNum, int substringStart, int substringEnd) {
        return Integer.parseInt(String.valueOf(passedNum).substring(substringStart, substringEnd));
    }

    public String convertToText(int num) {
        String[] ones = {"one","two","three","four","five","six","seven","eight","nine"};
        String[] teens = {"ten","eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};
        String[] tens = {"ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"};

        if(num == 0) {
            return "zero";
        }
        else if(num <= 9) {
            return ones[num - 1];
        }
        else if(num <= 19) {
            return teens[num - 10];
        }
        else if(num <= 99) {
            int first = parseGivenInt(num, 0, 1);
            int second = parseGivenInt(num, 1, 2);

            if(second==0) {
                return tens[first - 1];
            } else {
                return tens[first - 1] + "-" + ones[second - 1];
            }
        }
        else if(num <= 999) {
            int first = parseGivenInt(num, 0, 1);
            int second = parseGivenInt(num, 1, 2);
            int third = parseGivenInt(num, 2, 3);

            if(second==0 && third==0) {
                return ones[first - 1] + " hundred";
            } else if(second==0 && third>0) {
                return ones[first - 1] + " hundred and " + ones[third - 1];
            } else if(second>0 && third==0) {
                return ones[first - 1] + " hundred and " + tens[second - 1];
            } else {
                return ones[first - 1] + " hundred and " + tens[second - 1] + "-" + ones[third - 1];
            }
        }
        else if(num <= 9999) {
            int first = parseGivenInt(num, 0, 1);
            int second = parseGivenInt(num, 1, 2);
            int third = parseGivenInt(num, 2, 3);
            int fourth = parseGivenInt(num, 3, 4);

            if(second==0 && third==0 && fourth==0) {
                return ones[first - 1] + " thousand";
            }else if(second>0 && third==0 && fourth==0) {
                return ones[first - 1] + " thousand " + ones[second - 1] + " hundred";
            } else if(second==0 && third>0 && fourth==0) {
                return ones[first - 1] + " thousand and " + tens[third - 1];
            } else if (second==0 && third==0 && fourth>0) {
                return ones[first - 1] + " thousand and" + ones[fourth - 1];
            } else if (second>0 && third>0 && fourth==0) {
                return ones[first - 1] + " thousand " + ones[second - 1] + " hundred and " + tens[third - 1];
            } else if(second>0 && third==0 && fourth>0) {
                return ones[first - 1] + " thousand " + ones[second - 1] + " hundred and " + ones[fourth - 1];
            } else if(second==0 && third>0 && fourth>0) {
                return ones[first - 1] + " thousand " + tens[third -1] + "-" + ones[fourth - 1];
            }
            else {
                return ones[first - 1] + " thousand " + ones[second - 1] + " hundred and " + tens[third - 1] + "-" + ones[fourth - 1];
            }
        }
        return null;
    }

    @AfterSuite
    public void stopSuite() {
        driver.quit();
    }
}
