package tests;

import base.BaseTest;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminUsersPage;
import pages.DashboardPage;
import pages.FooterPage;
import pages.LoginPage;
import utils.JsonDataReader;

import java.util.Arrays;
import java.util.List;

public class AdminAndNavigationTests extends BaseTest {

    private void loginAsAdmin() {
        JsonObject valid = JsonDataReader.getObject("validLogin");
        new LoginPage(getDriver()).login(
                valid.get("username").getAsString(),
                valid.get("password").getAsString()
        );
    }

    @Test
    @Description("TC #9: Verify Admin > Add User page")
    public void testAdminAddUserPage() {
        loginAsAdmin();
        new DashboardPage(getDriver()).openAdmin();

        AdminUsersPage adminUsersPage = new AdminUsersPage(getDriver());
        adminUsersPage.clickAdd();

        Assert.assertTrue(adminUsersPage.areAddUserFieldsDisplayed());
    }

    @Test
    @Description("TC #10: Verify the OrangeHRM footer/branding link")
    public void testFooterLink() {
        loginAsAdmin();

        FooterPage footerPage = new FooterPage(getDriver());
        String footerText = footerPage.scrollToFooterAndGetText();
        Assert.assertTrue(footerText.contains("OrangeHRM, Inc"));

        String newTabUrl = footerPage.clickFooterLinkAndGetNewTabUrl();
        Assert.assertTrue(newTabUrl.contains("orangehrm.com"));
    }

    @Test
    @Description("TC #11: Verify sidebar menu UI")
    public void testSidebarMenuUi() {
        loginAsAdmin();

        DashboardPage dashboardPage = new DashboardPage(getDriver());
        List<String> actualItems = dashboardPage.getSidebarMenuNames();
        List<String> expectedItems = Arrays.asList(
                "Admin", "PIM", "Leave", "Time", "Recruitment",
                "My Info", "Performance", "Dashboard", "Directory"
        );

        Assert.assertTrue(actualItems.containsAll(expectedItems));
    }
}