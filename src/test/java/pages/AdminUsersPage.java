package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

public class AdminUsersPage {
    private final WebDriver driver;
    private final WaitHelper wait;

    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleLabel = By.xpath("//label[text()='User Role']");
    private final By employeeNameLabel = By.xpath("//label[text()='Employee Name']");
    private final By usernameLabel = By.xpath("//label[text()='Username']");
    private final By passwordLabel = By.xpath("//label[text()='Password']");

    public AdminUsersPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    @Step("Click Add User")
    public void clickAdd() {
        wait.waitForClickable(addButton).click();
    }

    public boolean areAddUserFieldsDisplayed() {
        return wait.waitForVisible(userRoleLabel).isDisplayed()
                && driver.findElement(employeeNameLabel).isDisplayed()
                && driver.findElement(usernameLabel).isDisplayed()
                && driver.findElement(passwordLabel).isDisplayed();
    }
}