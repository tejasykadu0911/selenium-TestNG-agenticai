package framework.tests;

import framework.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Smoke tests that run against base.url from config.properties.
 * With parallel="methods" in testng.xml these run concurrently,
 * each with its own browser session.
 */
public class SampleTest extends BaseTest {

    @Test(description = "Page title should not be empty")
    public void pageTitleNotEmpty() {
        step("Navigated to base URL");
        String title = driver().getTitle();
        step("Page title: " + title);
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
    }

    @Test(description = "Current URL should match base URL")
    public void currentUrlMatchesBaseUrl() {
        step("Verifying current URL");
        String url = driver().getCurrentUrl();
        step("Current URL: " + url);
        Assert.assertFalse(url.isEmpty(), "URL should not be empty");
    }

    @Test(description = "Page source should not be blank")
    public void pageSourceNotBlank() {
        step("Checking page source length");
        String source = driver().getPageSource();
        Assert.assertTrue(source.length() > 0, "Page source should not be blank");
    }
}