package framework.pages;

import framework.driver.DriverFactory;
import framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/**
 * Base for all Page Objects.
 * Uses DriverFactory.getDriver() so it is always on the calling thread's driver.
 */
public abstract class BasePage {

    protected BasePage() {
    }

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    protected void click(By locator) {
        WaitUtil.untilClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = WaitUtil.untilVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator) {
        return WaitUtil.untilVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try { return WaitUtil.untilVisible(locator).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].scrollIntoView({block:'center'})", element);
    }

    public String getTitle() {
        return driver().getTitle();
    }

    public String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    public WebElement getWeElementByXpath(By locator){
        return driver().findElement(locator);
    }
}