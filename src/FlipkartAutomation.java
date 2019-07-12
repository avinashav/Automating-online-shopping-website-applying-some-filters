import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class FlipkartAutomation {

    public static void clickElectronics(WebDriver driver, String Category ,String Brand) throws InterruptedException {
        WebElement electronics = driver.findElement(By.xpath("//span[text()='"+ Category +"']"));
        Actions actions =new Actions(driver);
        actions.moveToElement(electronics).build().perform();
        List<WebElement> allElectronics = driver.findElements(By.xpath("//a[text()='"+ Brand + "']"));
        waitFluentlyForElementToBePresent(driver , allElectronics , 30 , 5);
        driver.findElement(By.xpath("//a[text()='"+Brand + "']")).click();
        Thread.sleep(2000);
    }

    public static void clearDefaultFilters(WebDriver driver) throws InterruptedException {
        List<WebElement> viewAlls = driver.findElements(By.xpath("//a[@class=\"_2AkmmA _1eFTEo\"]"));
        viewAlls.get(0).click();
        driver.findElement(By.xpath("//div[text()='Exclude Out of Stock']")).click();
        Thread.sleep(2000);
    }

    public static void chooseMaxPrice(WebDriver driver, String price) throws InterruptedException {
        List<WebElement> priceDropdown = driver.findElements(By.className("fPjUPw"));
        Select dropMaxPrice = new Select(priceDropdown.get(1));
        dropMaxPrice.selectByVisibleText(price);
        Thread.sleep(3000);
    }

    public static void selectRAM(WebDriver driver, String ramSize) throws InterruptedException {
        String pathValue = "//div[text()='" + ramSize+ "']/preceding-sibling::input";
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(pathValue)));
        Thread.sleep(1000);
    }

    public static void selectOtherFeatures(WebDriver driver, String dropdownTitle, String feature) throws InterruptedException {
        driver.findElement(By.xpath("//div[text()='" + dropdownTitle + "']")).click();
        String pathValue =  "//div[text()='" + feature + "']/preceding-sibling::input";
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(pathValue)));
        Thread.sleep(1000);
    }

    public static void waitFluentlyForElementToBePresent(WebDriver driver, List<WebElement> webElement, int timeOutInSeconds, int pollingTime) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeOutInSeconds, TimeUnit.SECONDS)
                .pollingEvery(pollingTime, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                List<WebElement> elements = webElement;
                if (elements != null) {
                    return true;
                }
                return false;
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\Downloads\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        String baseUrl = "https://www.flipkart.com";
        driver.get(baseUrl);

        //close login popup
        driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']")).click();

        clickElectronics(driver,"Electronics","OPPO");

        clearDefaultFilters(driver);

        chooseMaxPrice(driver, "₹16000");

        selectRAM(driver, "2 GB");

        selectOtherFeatures(driver, "Battery Capacity" ,"2000 - 2999 mAh");

        selectOtherFeatures(driver, "Sim Type" ,"Dual Sim");


        List<WebElement> allfeatures = driver.findElements(By.xpath("//div[@class=\"_3O0U0u\"]//div[@class=\"col col-7-12\"]//div[@class=\"_3ULzGw\"]/ul/../.."));

        for (WebElement feature : allfeatures ) {
//            System.out.println(driver.findElement(By.xpath("//div[@class=\"_3O0U0u\"]//div[@class=\"col col-7-12\"]/div")).getText());
            System.out.println(feature.getText());
            System.out.println("==================================");
        }

//        driver.close();
//        driver.quit();
    }
}