import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;


public class SauceDemoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws MalformedURLException {
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        String buildNumber = System.getenv("SAUCE_TC_BUILDNUMBER"); 
        String sauceUrl = String.format("https://%s:%s@ondemand.saucelabs.com:443/wd/hub",sauceUserName,sauceAccessKey);

        System.out.println("------------------");
        System.out.println(buildNumber);
        System.out.println(System.getenv("SAUCE_BAMBOO_BUILDNUMBER"));
        System.out.println("------------------");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("version", "85");
        capabilities.setCapability("build", "Sample App - Java-Junit");
        capabilities.setCapability("name", "Simple test");
        capabilities.setCapability("build",buildNumber);

        driver = new RemoteWebDriver(new URL(sauceUrl), capabilities);
        wait = new WebDriverWait(driver, 5);
    }

    @After
    public void tearDown() {
        driver.quit();

    }

    @Test
    public void testSauceDemoPageOpened() {
        driver.get("http://www.saucedemo.com");

        By userNameFieldLocator = By.cssSelector("[type='text']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator));

        ((JavascriptExecutor)driver).executeScript("sauce:job-result=passed");

        printSessionId();
    }

    private void printSessionId() {
        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s",
                (((RemoteWebDriver) driver).getSessionId()).toString(), "test job name");
        System.out.println(message);
    }
}
