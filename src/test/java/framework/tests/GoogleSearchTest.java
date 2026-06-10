package framework.tests;

import framework.base.BaseTest;
import framework.pages.GoogleHomePage;
import framework.pages.GoogleResultsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Demonstrates Page Object Model + parallel execution.
 * Each @Test method spins up its own browser session (thread-safe).
 */
public class GoogleSearchTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleSearchTest.class);

    @Test(description = "Search for Selenium returns results")
    public void searchSeleniumReturnsResults() {

        LOG.info("test case being run ");

        step("Opening Google home page");
        GoogleResultsPage results = new GoogleHomePage()
                .open()
                .search("Selenium WebDriver");

        step("Verifying results are shown");
        Assert.assertTrue(results.hasResults(), "Search results should be displayed");
    }

    @Test(description = "Search for TestNG returns results")
    public void searchTestNGReturnsResults() {
        step("Opening Google home page");
        GoogleResultsPage results = new GoogleHomePage()
                .open()
                .search("TestNG parallel execution");

        step("Verifying results are shown");
        Assert.assertTrue(results.hasResults(), "Search results should be displayed");
        String firstTitle = results.getFirstResultTitle();
        step("First result: " + firstTitle);
        Assert.assertFalse(firstTitle.isEmpty(), "First result title should not be empty");
    }

    @Test(description = "Search for Java returns results")
    public void searchJavaReturnsResults() {
        step("Opening Google home page");
        GoogleResultsPage results = new GoogleHomePage()
                .open()
                .search("Java 17 features");

        step("Verifying results are shown");
        Assert.assertTrue(results.hasResults(), "Search results should be displayed");
    }
}