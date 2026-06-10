package framework.base;

import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import framework.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * All test classes extend this.
 * @BeforeMethod / @AfterMethod run per thread, so each parallel test
 * gets its own browser session via DriverFactory's ThreadLocal.
 */
public abstract class BaseTest {

    /**
     * browser param can come from:
     *   1. testng.xml  <parameter name="browser" value="chrome"/>
     *   2. Maven CLI   -Dbrowser=firefox
     *   3. config.properties  browser=chrome  (default fallback)
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional String browser) {
        String resolved = (browser != null && !browser.isBlank())
                ? browser
                : ConfigReader.get("browser", "chrome");
        DriverFactory.initDriver(resolved);
        driver().get(ConfigReader.get("base.url", "https://www.google.com"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    /** Convenience accessor — always returns the current thread's driver. */
    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }

    /** Log a step message to the ExtentReport node for this test. */
    protected void step(String message) {
        TestListener.log(message);
    }
}