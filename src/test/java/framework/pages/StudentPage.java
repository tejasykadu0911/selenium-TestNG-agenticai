package framework.pages;

import framework.utils.WaitUtil;
import org.openqa.selenium.By;

public class StudentPage extends BasePage{

    // ── Header / Navigation ──────────────────────────────────────────────────

    private static final By WELCOME_MESSAGE = By.xpath("//p[@class='student-home__welcome-banner-text']");

    private static final By COURSEPACKS_MESSAGE = By.xpath("//h1[@class='cta-banner__title']//p[@class='rich-text-paragraph']");



    //Methods

    public String verifyWelcomeNote(){
        WaitUtil.untilVisible(WELCOME_MESSAGE);
        getWebElementByXpath(WELCOME_MESSAGE).isDisplayed();
        return getWebElementByXpath(WELCOME_MESSAGE).getText();

    }

    public String verifyViewYourCoursePacksMessage(){
        WaitUtil.untilVisible(COURSEPACKS_MESSAGE);
        getWebElementByXpath(COURSEPACKS_MESSAGE).isDisplayed();
        return getWebElementByXpath(COURSEPACKS_MESSAGE).getText();

    }
}
