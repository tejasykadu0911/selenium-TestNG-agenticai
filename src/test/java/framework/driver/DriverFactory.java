package framework.driver;

import framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Thread-safe WebDriver factory using ThreadLocal.
 * Each thread (parallel test) gets its own isolated browser session.
 */
public final class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    // One WebDriver instance per thread — the key to parallel safety
    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String browserName) {
        boolean headless = ConfigReader.getBoolean("headless", false);
        BrowserType browser = BrowserType.fromString(browserName);
        log.info("[Thread {}] Starting {} (headless={})",
                Thread.currentThread().getId(), browser, headless);

        WebDriver driver = switch (browser) {
            case FIREFOX -> createFirefox(headless);
            case EDGE    -> createEdge(headless);
            case SAFARI  -> createSafari();
            default      -> createChrome(headless);
        };

        applyTimeouts(driver);
        driverHolder.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverHolder.get();
        if (driver == null) throw new IllegalStateException(
                "WebDriver not initialised for thread " + Thread.currentThread().getId());
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverHolder.get();
        if (driver != null) {
            log.info("[Thread {}] Quitting browser", Thread.currentThread().getId());
            driver.quit();
            driverHolder.remove();
        }
    }

    // ── private helpers ──────────────────────────────────────────────────────

    private static WebDriver createChrome(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        if (headless) opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox", "--disable-dev-shm-usage",
                          "--disable-gpu", "--window-size=1920,1080");
        return new ChromeDriver(opts);
    }

    private static WebDriver createFirefox(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions opts = new FirefoxOptions();
        if (headless) opts.addArguments("-headless");
        return new FirefoxDriver(opts);
    }

    private static WebDriver createEdge(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions opts = new EdgeOptions();
        if (headless) opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new EdgeDriver(opts);
    }

    private static WebDriver createSafari() {
        // Safari requires "Allow Remote Automation" in Develop menu; no headless support
        return new SafariDriver();
    }

    private static void applyTimeouts(WebDriver driver) {
        int implicit = ConfigReader.getInt("implicit.wait", 5);
        int pageLoad = ConfigReader.getInt("page.load.timeout", 30);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoad));
        driver.manage().window().maximize();
    }
}