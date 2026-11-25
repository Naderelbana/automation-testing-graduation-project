package tests;

import base.BaseTest;
import com.google.gson.JsonObject;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import utils.JsonDataReader;

public class PimTests extends BaseTest {

    private void loginAsAdmin() {
        JsonObject valid = JsonDataReader.getObject("validLogin");
        new LoginPage(getDriver()).login(
                valid.get("username").getAsString(),
                valid.get("password").getAsString()
        );
    }

    @DataProvider(name = "existingEmployee")
    public Object[][] existingEmployee() {
        return new Object[][] { { JsonDataReader.getString("searchExistingEmployee") } };
    }

    @DataProvider(name = "nonExistingEmployee")
    public Object[][] nonExistingEmployee() {
        return new Object[][] { { JsonDataReader.getString("searchNonExistingEmployee") } };
    }

    @DataProvider(name = "newEmployee")
    public Object[][] newEmployee() {
        JsonObject emp = JsonDataReader.getObject("newEmployee");
        return new Object[][] { { emp.get("firstName").getAsString(), emp.get("lastName").getAsString() } };
    }

    @Test(dataProvider = "newEmployee")
    @Description("TC #8: End-to-end — Add a new employee successfully")
    public void testAddEmployeeEndToEnd(String firstName, String lastName) {
        loginAsAdmin();
        new DashboardPage(getDriver()).openPim();

        PimPage pimPage = new PimPage(getDriver());
        pimPage.openAddEmployee();

        AddEmployeePage addEmployeePage = new AddEmployeePage(getDriver());
        addEmployeePage.enterEmployeeName(firstName, lastName);
        addEmployeePage.clickSave();
        Assert.assertTrue(addEmployeePage.isPersonalDetailsDisplayed());

        pimPage.openEmployeeList();
        pimPage.searchEmployee(firstName);
        Assert.assertTrue(pimPage.getResultsTableText().contains(firstName));
    }

    @Test(dataProvider = "existingEmployee")
    @Description("TC #4: Search for an employee (PIM module)")
    public void testSearchExistingEmployee(String employeeName) {
        loginAsAdmin();
        new DashboardPage(getDriver()).openPim();

        PimPage pimPage = new PimPage(getDriver());
        pimPage.openEmployeeList();
        pimPage.searchEmployee(employeeName);

        Assert.assertTrue(pimPage.getResultsTableText().contains(employeeName));
    }

    @Test(dataProvider = "nonExistingEmployee")
    @Description("TC #5: Search for a non-existing employee")
    public void testSearchNonExistingEmployee(String employeeName) {
        loginAsAdmin();
        new DashboardPage(getDriver()).openPim();

        PimPage pimPage = new PimPage(getDriver());
        pimPage.openEmployeeList();
        pimPage.searchEmployee(employeeName);

        Assert.assertTrue(pimPage.isNoRecordsFoundDisplayed());
    }

    @Test
    @Description("TC #6: Open Add Employee page")
    public void testOpenAddEmployeePage() {
        loginAsAdmin();
        new DashboardPage(getDriver()).openPim();

        PimPage pimPage = new PimPage(getDriver());
        pimPage.openAddEmployee();

        AddEmployeePage addEmployeePage = new AddEmployeePage(getDriver());
        Assert.assertTrue(addEmployeePage.areNameFieldsDisplayed());
        Assert.assertTrue(addEmployeePage.getCurrentUrl().contains("/pim/addEmployee"));
    }

    @Test
    @Description("TC #7: Add a new employee with an empty required field")
    public void testAddEmployeeEmptyFirstName() {
        loginAsAdmin();
        new DashboardPage(getDriver()).openPim();

        new PimPage(getDriver()).openAddEmployee();

        AddEmployeePage addEmployeePage = new AddEmployeePage(getDriver());
        addEmployeePage.enterEmployeeName(null, "Tester");
        addEmployeePage.clickSave();

        Assert.assertEquals(addEmployeePage.getFirstNameRequiredMessage(), "Required");
    }

}