package framework.tests;

import framework.base.BaseTest;
import framework.pages.HbpHomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HbpHomePageTest extends BaseTest  {
    HbpHomePage homePage;


    HbpHomePageTest(){
        super();
    }

    @BeforeMethod
    public void setup(){
       homePage = new HbpHomePage();
    }

    @Test(description = "verify home page title")
    public void verifyHomePageTitle(){

        step("verify login page");
        Assert.assertEquals(homePage.getTitle(),"Home | Harvard Business Impact Education");
    }

    @Test(description = "open sign in page")
    public void openSigninPage() throws InterruptedException {
        homePage.signIn("tejas988@gmail.com","Password@123");
    }
}
