package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utils.WaitHelper;

public class FooterPage {
    private final WebDriver driver;
    private final WaitHelper wait;

    private final By footerContainer = By.cssSelector(".oxd-layout-footer");
    private final By orangeHrmLink = By.cssSelector(".oxd-layout-footer a");

    public FooterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitHelper(driver);
    }

    @Step("Scroll to footer and get text")
    public String scrollToFooterAndGetText() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        return wait.waitForVisible(footerContainer).getText().trim();
    }

    @Step("Click OrangeHRM, Inc link and switch to new tab")
    public String clickFooterLinkAndGetNewTabUrl() {
        String mainTab = driver.getWindowHandle();
        driver.findElement(orangeHrmLink).click();

        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(mainTab)) {
                driver.switchTo().window(tab);
                break;
            }
        }
        return driver.getCurrentUrl();
    }
}