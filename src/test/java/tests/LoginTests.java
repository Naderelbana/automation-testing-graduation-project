package tests;

import base.BaseTest;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.JsonDataReader;

import java.util.List;

public class LoginTests extends BaseTest {

    @DataProvider(name = "validLogin")
    public Object[][] validLogin() {
        JsonObject obj = JsonDataReader.getObject("validLogin");
        return new Object[][] { { obj.get("username").getAsString(), obj.get("password").getAsString() } };
    }

    @DataProvider(name = "invalidLogin")
    public Object[][] invalidLogin() {
        JsonObject obj = JsonDataReader.getObject("invalidLogin");
        return new Object[][] { { obj.get("username").getAsString(), obj.get("password").getAsString() } };
    }

    @Test(dataProvider = "validLogin")
    @Description("TC #1: Login with valid credentials")
    public void testValidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        DashboardPage dashboardPage = new DashboardPage(getDriver());
        Assert.assertEquals(dashboardPage.getHeaderText(), "Dashboard");
        Assert.assertTrue(dashboardPage.getCurrentUrl().contains("/dashboard/index"));
    }

    @Test(dataProvider = "invalidLogin")
    @Description("TC #2: Login with invalid credentials")
    public void testInvalidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(username, password);

        Assert.assertEquals(loginPage.getInvalidCredentialsMessage(), "Invalid credentials");
    }

    @Test
    @Description("TC #3: Login with empty fields")
    public void testEmptyFieldsLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickLogin();

        List<String> messages = loginPage.getRequiredMessages();
        Assert.assertEquals(messages.size(), 2);
        Assert.assertEquals(messages.get(0), "Required");
        Assert.assertEquals(messages.get(1), "Required");
    }
}