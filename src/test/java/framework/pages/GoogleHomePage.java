package framework.pages;

import framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Page Object for https://www.google.com */
public class GoogleHomePage extends BasePage {

    // Cookie / consent dialog buttons (region-specific)
    private static final By CONSENT_ACCEPT   = By.xpath("//button[.//span[contains(text(),'Accept all') or contains(text(),'I agree')]]");
    private static final By SEARCH_INPUT     = By.name("q");

    @FindBy(name = "q")
    private WebElement searchBox;

    public GoogleHomePage() {
        super();
    }

    public GoogleHomePage open() {
        driver().get("https://www.google.com");
        dismissConsentIfPresent();
        return this;
    }

    public GoogleResultsPage search(String query) {
        WaitUtil.untilClickable(SEARCH_INPUT);
        searchBox.clear();
        searchBox.sendKeys(query + Keys.RETURN);
        return new GoogleResultsPage();
    }

    private void dismissConsentIfPresent() {
        try {
            WebElement btn = driver().findElement(CONSENT_ACCEPT);
            if (btn.isDisplayed()) btn.click();
        } catch (NoSuchElementException ignored) {
            // No consent dialog — continue normally
        }
    }
}