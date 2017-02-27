import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jeff on 2/24/2017.
 */
public class BaseSelenium
{
    public static WebDriver chromeDriver = new ChromeDriver();
    public static String googleURL = "http://www.google.com";
    public static long timeout = 10;
    public static String rbURL = "https://www.rbauction.com";
    public static FluentWait wait = new WebDriverWait(chromeDriver, timeout);

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
    }

    @Test()
    public void searchGoogle()
    {


        String searchString = "mobile integration workgroup";
        chromeDriver.get(googleURL);
        chromeDriver.manage().window().maximize();
        chromeDriver.findElement(By.className("gsfi")).sendKeys(searchString);
        chromeDriver.findElement(By.name("btnG")).click();
        chromeDriver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable((chromeDriver.findElement(By.id("rso")))));
        List<WebElement> resultsList = chromeDriver.findElements(By.xpath("//*[@id='rso']//h3/a"));
        String expectedLink = "http://mobileintegration-group.com/";
        String firstLink = resultsList.get(0).getAttribute("href");

        if(!firstLink.equals(expectedLink))
        {
            throw new NoSuchElementException("The first element in the list is " + firstLink + " , when it should be " + expectedLink);
        }
    }

    @Test
    public void searchRichieBros()
    {
        chromeDriver.get(rbURL);
        chromeDriver.manage().window().maximize();
        chromeDriver.findElement(By.id("rba-keyword-toggle")).click();

        WebElement advSearchForm = chromeDriver.findElement(By.className("rba-adv-search-inner"));

        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable((chromeDriver.findElement(By.id("adv-industry")))));
        selectElement((chromeDriver.findElement(By.id("adv-industry"))), "Construction");

        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable((chromeDriver.findElement(By.id("adv-category")))));
        selectElement((chromeDriver.findElement(By.id("adv-category"))), "Excavators");

        chromeDriver.findElement(By.id("adv-make")).sendKeys("CATERPILLAR");
        chromeDriver.findElement(By.id("adv-year-1")).sendKeys("2014");
        chromeDriver.findElement(By.id("adv-year-2")).sendKeys("2017");

        WebElement searchButton = advSearchForm.findElement(By.xpath("//*[@id='rba-adv-search']/div/div[3]/input"));
        searchButton.click();

        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf((chromeDriver.findElement(By.id("rba-search-results-list")))));

        try
        {
            Alert alert = chromeDriver.switchTo().alert();
            alert.dismiss();
        }
        catch(NoAlertPresentException e)
        {
            System.out.println("No Alert/PopUp detected");
        }

        WebElement searchResults = chromeDriver.findElement(By.id("rba-search-results-list"));
        List<WebElement> resultsList = searchResults.findElements(By.xpath("//*[contains(@id, 'search-results-row')]"));
        WebElement detailsElement = resultsList.get(0).findElement(By.xpath("//*[contains(@class, 'item-extra-info')]"));
        System.out.println(detailsElement.getText());
    }

    public void selectElement(WebElement element, String text)
    {
        Select select = new Select(element);
        select.selectByVisibleText(text);
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
