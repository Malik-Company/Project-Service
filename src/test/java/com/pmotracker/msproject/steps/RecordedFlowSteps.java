package com.pmotracker.msproject.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class RecordedFlowSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @When("I click the {string} link")
    public void iClickTheLink(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }

    @When("I click the {string} label")
    public void iClickTheLabel(String labelText) {
        driver.findElement(By.xpath(String.format("//label[text()='%s']", labelText))).click();
    }

    @When("I click the {string} field")
    public void iClickTheField(String fieldText) {
        // This step is ambiguous. It could be an input, a button, or any other clickable element.
        // We're using a generic XPath that looks for an element with the given text.
        // It also checks for common button/input attributes.
        String xpath = String.format(
            "//*[text()='%s' or @value='%s' or @name='%s' or @aria-label='%s'] | //input[@type='submit'] | //button",
            fieldText, fieldText, fieldText, fieldText);
        driver.findElement(By.xpath(xpath)).click();
    }

    @When("I fill the {string} field with {string}")
    public void iFillTheFieldWith(String fieldIdentifier, String value) {
        // This XPath attempts to find an input or textarea element that is
        // either associated with a label matching the identifier, or has a matching
        // placeholder or name attribute. This covers common ways to identify a form field.
        String xpath = String.format(
            "//input[@placeholder='%s' or @name='%s' or @id=(//label[text()='%s']/@for)] | " +
            "//textarea[@placeholder='%s' or @name='%s' or @id=(//label[text()='%s']/@for)]",
            fieldIdentifier, fieldIdentifier, fieldIdentifier,
            fieldIdentifier, fieldIdentifier, fieldIdentifier
        );
        WebElement field = driver.findElement(By.xpath(xpath));
        field.clear();
        field.sendKeys(value);
    }

    @When("I click the {string} span")
    public void iClickTheSpan(String spanText) {
        driver.findElement(By.xpath(String.format("//span[text()='%s']", spanText))).click();
    }
}
