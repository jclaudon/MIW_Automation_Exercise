import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Jeff on 2/24/2017.
 */
public class BaseSelenium
{
    protected static WebDriver chromeDriver = new ChromeDriver();
    protected static String googleURL = "http://www.google.com";
    protected static long timeout = 1;
    protected static String rbURL = "https://www.rbauction.com";
    protected static FluentWait wait = new WebDriverWait(chromeDriver, timeout);

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

//Public Functions------------------------------------------------------------------------------------------------------
    protected static void openBrowserAndNavigate(String url)
    {
        chromeDriver.get(url);
        chromeDriver.manage().window().maximize();
    }

    protected static void verifyGooglePage() throws Exception
    {
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
    protected static void selectElement(WebElement element, String text)
    {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    protected static boolean waitForPageLoad(WebDriver driver, long timeout)
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
