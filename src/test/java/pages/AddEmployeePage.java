package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

public class AddEmployeePage {
    private final WebDriver driver;
    private final WaitHelper wait;

    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By firstNameRequiredError = By.xpath("//input[@name='firstName']/../following-sibling::span");
    private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");
    private final By formLoader = By.cssSelector(".oxd-form-loader");

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean areNameFieldsDisplayed() {
        return wait.waitForVisible(firstNameInput).isDisplayed()
                && driver.findElement(lastNameInput).isDisplayed();
    }

    @Step("Enter First Name: {0} and Last Name: {1}")
    public void enterEmployeeName(String firstName, String lastName) {
        if (firstName != null) {
            wait.waitForVisible(firstNameInput).sendKeys(firstName);
        } else {
            wait.waitForVisible(lastNameInput);
        }
        if (lastName != null) {
            driver.findElement(lastNameInput).sendKeys(lastName);
        }
    }


    @Step("Click Save employee button")
    public void clickSave() {
        wait.waitForInvisibility(formLoader);
        wait.waitForClickable(saveButton).click();
    }


    public String getFirstNameRequiredMessage() {
        return wait.waitForVisible(firstNameRequiredError).getText().trim();
    }

    public boolean isPersonalDetailsDisplayed() {
        return wait.waitForVisible(personalDetailsHeader).isDisplayed();
    }
}