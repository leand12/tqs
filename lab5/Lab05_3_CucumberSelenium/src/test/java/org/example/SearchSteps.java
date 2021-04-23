package org.example;

import java.util.NoSuchElementException;

import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class SearchSteps {

    private WebDriver webDriver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        webDriver = new FirefoxDriver();
        webDriver.get(url);
    }

    @And("I set destination to {string}")
    public void iSetDestinationTo(String destination) {
        WebElement dropdown = webDriver.findElement(By.name("toPort"));
        dropdown.findElement(By.xpath("//option[. = '" + destination + "']")).click();
    }

    @And("I click on button {string}")
    public void iClickOnButton(String buttonName) {
        webDriver.findElement(By.xpath("//input[@type='submit' and @value='" + buttonName + "']")).click();
    }

    @And("I type {string} on {string}")
    public void iClickOnLabel(String text, String label) {
        webDriver.findElement(By.xpath("//label[contains(text(), '" + label + "')]/following-sibling::div//input"))
                .sendKeys(text);
    }

    @Then("I should be shown results including {string}")
    public void iShouldBeShownResultsIncluding(String result) {
        try {
            webDriver.findElement(
                    By.xpath("//*[contains(text(), '" + result + "')]"));
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
        } finally {
            webDriver.quit();
        }
    }

    @Then("I should be redirected to {string}")
    public void iShouldBeRedirectedTo(String url) {
        assertEquals(webDriver.getCurrentUrl(), url);
    }
}
