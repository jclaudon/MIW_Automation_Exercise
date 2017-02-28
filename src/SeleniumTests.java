import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jeff on 2/27/2017.
 */
public class SeleniumTests extends BaseSelenium
{
    @Test
    public void openGoogle() throws Exception
    {
        openBrowserAndNavigate(googleURL);
        verifyGooglePage();
    }

    @Test()
    public void searchGoogle() throws Exception
    {
        openBrowserAndNavigate(googleURL);
        verifyGooglePage();

        String searchString = "mobile integration workgroup";

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
        openBrowserAndNavigate(rbURL);
        wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElement(By.id("rba-header-logo-svg"))));
        chromeDriver.findElement(By.id("rba-keyword-toggle")).click();

        WebElement advSearchForm = chromeDriver.findElement(By.className("rba-adv-search-inner"));

        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable((chromeDriver.findElement(By.id("adv-industry")))));
        selectElement((chromeDriver.findElement(By.id("adv-industry"))), "Construction");

        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable((chromeDriver.findElement(By.id("adv-category")))));
        selectElement((chromeDriver.findElement(By.id("adv-category"))), "Excavators");

        chromeDriver.findElement(By.id("adv-make")).sendKeys("CATERPILLAR");
        chromeDriver.findElement(By.id("adv-year-1")).sendKeys("2014");
        chromeDriver.findElement(By.id("adv-year-2")).sendKeys("2017");

        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable((By.xpath("//*[@id='rba-adv-search']/div/div[3]/input"))));
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
}