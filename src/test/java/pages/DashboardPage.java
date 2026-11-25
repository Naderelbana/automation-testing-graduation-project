package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardPage {
    private final WebDriver driver;
    private final WaitHelper wait;

    private final By headerTitle = By.cssSelector(".oxd-topbar-header-breadcrumb h6");
    private final By sidebarItems = By.cssSelector(".oxd-main-menu-item span");
    private final By pimMenu = By.xpath("//span[text()='PIM']/ancestor::a");
    private final By adminMenu = By.xpath("//span[text()='Admin']/ancestor::a");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    public String getHeaderText() {
        return wait.waitForVisible(headerTitle).getText().trim();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public List<String> getSidebarMenuNames() {
        return wait.waitForAllVisible(sidebarItems).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    @Step("Open PIM module")
    public void openPim() {
        wait.waitForClickable(pimMenu).click();
    }

    @Step("Open Admin module")
    public void openAdmin() {
        wait.waitForClickable(adminMenu).click();
    }
}