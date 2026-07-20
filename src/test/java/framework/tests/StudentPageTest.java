package framework.tests;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.HbpHomePage;
import framework.pages.StudentPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StudentPageTest extends BaseTest {

    StudentPage studentPage;

    StudentPageTest(){
        super();
    }

    @BeforeMethod
    public void setup(){
        // Full login per test: the site only grants student portal access via
        // the natural post-login redirect, not via cookie injection into a fresh browser.
        new HbpHomePage().signIn(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
        studentPage = new StudentPage();
    }

    @Test(description = "verify welcome message")
    public void verifyWelcomeMessage() {
        String welcomeMessage = studentPage.verifyWelcomeNote();
        step("Verifying welcome message: " + welcomeMessage);
        org.testng.Assert.assertEquals(welcomeMessage, "Welcome back Tejas!");
    }

    @Test(description = "verify welcome message")
    public void verifyViewYourCoursePacksMessage() {
        String welcomeMessage = studentPage.verifyViewYourCoursePacksMessage();
        step("Verifying welcome message: " + welcomeMessage);
        org.testng.Assert.assertEquals(welcomeMessage, "View Your Coursepacks");
    }
}
