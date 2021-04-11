import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class SampleTest {
    WebDriver browser;

    @BeforeEach
    public void setUp() {
        // System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        FirefoxOptions options = new FirefoxOptions().addPreference("browser.startup.homepage", "https://www.google.pt");
        // browser = new ChromeDriver();
        browser = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        browser.close();
    }


    @Test
    public void site_header_is_on_home_page() {
        browser.get("https://www.saucelabs.com");
        WebElement href = browser.findElement(By.xpath("//a[@href='https://accounts.saucelabs.com/']"));
        assertTrue((href.isDisplayed()));
    }

    @Test
    public void site_header_is_on_home_page_explicit_waits() {
        WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(5));

        browser.get("https://www.saucelabs.com");
        WebElement href = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='https://accounts.saucelabs.com/']")
        ));

        assertTrue(href.isDisplayed());
    }

}
