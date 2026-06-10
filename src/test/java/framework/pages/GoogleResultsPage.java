package framework.pages;

import framework.utils.WaitUtil;
import org.openqa.selenium.By;

public class GoogleResultsPage extends BasePage {

    // #rso is the main search results container — more stable than #result-stats
    private static final By RESULTS_CONTAINER = By.id("rso");
    private static final By FIRST_RESULT_TITLE = By.cssSelector("#rso h3");

    public GoogleResultsPage() {
        super();
    }

    public boolean hasResults() {
        try {
            WaitUtil.untilVisible(RESULTS_CONTAINER);
            return !driver().findElements(FIRST_RESULT_TITLE).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstResultTitle() {
        return WaitUtil.untilVisible(FIRST_RESULT_TITLE).getText();
    }
}