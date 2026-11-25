package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

import java.util.List;
import java.util.stream.Collectors;

public class LoginPage {
    private final WebDriver driver;
    private final WaitHelper wait;

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By invalidAlert = By.cssSelector(".oxd-alert-content-text");
    private final By requiredErrors = By.cssSelector(".oxd-input-field-error-message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    @Step("Login with username: {0}")
    public void login(String username, String password) {
        wait.waitForVisible(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    @Step("Click Login with empty fields")
    public void clickLogin() {
        wait.waitForClickable(loginButton).click();
    }

    public String getInvalidCredentialsMessage() {
        return wait.waitForVisible(invalidAlert).getText().trim();
    }

    public List<String> getRequiredMessages() {
        return wait.waitForAllVisible(requiredErrors).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }
}