package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

public class PimPage {
    private final WebDriver driver;
    private final WaitHelper wait;

    private final By employeeListTab = By.xpath("//a[text()='Employee List']");
    private final By addEmployeeTab = By.xpath("//a[text()='Add Employee']");
    private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By tableBody = By.cssSelector(".oxd-table-body");
    private final By noRecordsText = By.xpath(
            "//*[contains(@class,'oxd-toast-content-text') and contains(text(),'No Records Found')] | " +
                    "//*[contains(@class,'oxd-table-body') and contains(.,'No Records Found')] | " +
                    "//span[normalize-space()='No Records Found']"
    );
    public PimPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    @Step("Open Employee List tab")
    public void openEmployeeList() {
        wait.waitForClickable(employeeListTab).click();
    }

    @Step("Open Add Employee tab")
    public void openAddEmployee() {
        wait.waitForClickable(addEmployeeTab).click();
    }

    @Step("Search for employee: {0}")
    public void searchEmployee(String name) {
        wait.waitForVisible(employeeNameInput).clear();
        driver.findElement(employeeNameInput).sendKeys(name);
        driver.findElement(searchButton).click();
    }

    public String getResultsTableText() {
        return wait.waitForVisible(tableBody).getText();
    }

    public boolean isNoRecordsFoundDisplayed() {
        return wait.waitForVisible(noRecordsText).isDisplayed();
    }
}