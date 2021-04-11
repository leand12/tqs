import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@ExtendWith(SeleniumJupiter.class)
public class FirefoxJupiterTest {
    private FirefoxDriver firefoxDriver;

    // dependency injection in constructor
    public FirefoxJupiterTest(FirefoxDriver firefoxDriver) {
        this.firefoxDriver = firefoxDriver;
    }

    @Test
    public void testWithOneFirefox() {
        firefoxDriver.get("https://bonigarcia.github.io/selenium-jupiter/");
        assertThat(firefoxDriver.getTitle(),
                containsString("JUnit 5 extension for Selenium"));
    }

    // dependency injection in method
    @Test
    public void testWithTwoFirefoxs(FirefoxDriver driver1,
                             FirefoxDriver driver2) {
        driver1.get("http://www.seleniumhq.org/");
        driver2.get("http://junit.org/junit5/");
        assertThat(driver1.getTitle(), startsWith("Selenium"));
        assertThat(driver2.getTitle(), equalTo("JUnit 5"));
    }

    // test to use a “headless browser”
    @Test
    public void testWithHeadless(HtmlUnitDriver driver) {
        driver.get("http://junit.org/junit5/");
        assertThat(driver.getTitle(), equalTo("JUnit 5"));
    }

    @Test
    public void blazedemotest(HtmlUnitDriver driver) {
        // Test name: blazedemotest
        // Step # | name | target | value
        // 1 | open | / |
        driver.get("https://blazedemo.com/");
        // 2 | setWindowSize | 999x692 |
        driver.manage().window().setSize(new Dimension(999, 692));
        // 3 | click | name=toPort |
        driver.findElement(By.name("toPort")).click();
        // 4 | select | name=toPort | label=London
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'London']")).click();
        }
        // 5 | click | css=.form-inline:nth-child(4) > option:nth-child(3) |
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        // 6 | click | css=.btn-primary |
        driver.findElement(By.cssSelector(".btn-primary")).click();
        // 7 | assertText | css=h3 | Flights from Paris to London:
        assertEquals(driver.findElement(By.cssSelector("h3")).getText(),"Flights from Paris to London:");
        // 8 | click | css=tr:nth-child(1) .btn |
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
        // 9 | click | id=inputName |
        driver.findElement(By.id("inputName")).click();
        // 10 | type | id=inputName | Leandro
        driver.findElement(By.id("inputName")).sendKeys("Leandro");
        // 11 | type | id=address | 123
        driver.findElement(By.id("address")).sendKeys("123");
        // 12 | click | id=city |
        driver.findElement(By.id("city")).click();
        // 13 | type | id=city | town
        driver.findElement(By.id("city")).sendKeys("town");
        // 14 | click | id=state |
        driver.findElement(By.id("state")).click();
        // 15 | type | id=state | state
        driver.findElement(By.id("state")).sendKeys("state");
        // 16 | click | id=zipCode |
        driver.findElement(By.id("zipCode")).click();
        // 17 | type | id=zipCode | 12345
        driver.findElement(By.id("zipCode")).sendKeys("12345");
        // 18 | click | id=cardType |
        driver.findElement(By.id("cardType")).click();
        // 19 | click | css=option:nth-child(1) |
        driver.findElement(By.cssSelector("option:nth-child(1)")).click();
        // 20 | click | css=.btn-primary |
        driver.findElement(By.cssSelector(".btn-primary")).click();
        // 21 | click | css=h1 |
        driver.findElement(By.cssSelector("h1")).click();
        // 22 | assertText | css=h1 | Thank you for your purchase today!
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Thank you for your purchase today!");
    }


}