import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Created by Jeff on 2/24/2017.
 */
public class BaseSelenium
{
    public static WebDriver chromeDriver = new ChromeDriver();
    public static String googleURL = "http://www.google.com";
    public static long timeout = 10;

    @Before
    public void setup()
    {
        System.setProperty("webdriver.chrome.driver", "D:\\DATA\\Downloads\\Selenium\\chromedriver_win32\\chromedriver.exe");
    }

    @After
    public void cleanUp()
    {
        chromeDriver.close();
    }

    @Test
    public void openChrome() throws Exception
    {
        chromeDriver.get(googleURL);
        chromeDriver.manage().window().maximize();
        if(!waitForPageLoad(chromeDriver, timeout))
        {
            throw new TimeoutException("Page failed to load in the given time of " + timeout + " seconds");
        }
        else
        {
            String expectedPageTitle = "Google";
            String actualPageTitle = chromeDriver.getTitle();
            if(actualPageTitle.equals(expectedPageTitle))
            {
                try
                {
                    chromeDriver.findElement(By.id("lga"));
                    chromeDriver.findElement(By.id("sbtc"));
                    chromeDriver.findElement(By.name("btnK"));
                }
                catch (NoSuchElementException e)
                {
                    String[] messageSplit = e.getMessage().split("\n");
                    String message = messageSplit[0];
                    throw new NoSuchElementException(message);
                }
            }
            else
            {
                throw new Exception("We are not on the correct page, we are on " + actualPageTitle + " , when we should be on " + expectedPageTitle);
            }
        }
        chromeDriver.close();
    }

    @Test()
    public void searchGoogle() throws InterruptedException
    {
        String searchString = "mobile integration workgroup";
        chromeDriver.get(googleURL);
        chromeDriver.manage().window().maximize();
        chromeDriver.findElement(By.className("gsfi")).sendKeys(searchString);
        chromeDriver.findElement(By.name("btnG")).click();
        chromeDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        //WebElement searchResults = (new WebDriverWait(chromeDriver, timeout)).until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
        List<WebElement> resultsList = chromeDriver.findElements(By.xpath("//*[@id='rso']//h3/a"));
        String expectedLink = "http://mobileintegration-group.com/";
        String firstLink = resultsList.get(0).getAttribute("href");

        if(!firstLink.equals(expectedLink))
        {
            throw new NoSuchElementException("The first element in the list is " + firstLink + " , when it should be " + expectedLink);
        }
    }

    public boolean waitForPageLoad(WebDriver driver, long timeout)
    {
        Boolean loaded;
        ExpectedCondition<Boolean> ec = new ExpectedCondition<Boolean>()
        {
            @Override
            public Boolean apply(WebDriver driver)
            {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        };

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try
        {
            wait.until(ec);
            loaded = true;
        }
        catch(Exception e)
        {
            loaded = false;
        }

        return loaded;
    }
}
