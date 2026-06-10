package framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import framework.utils.ScreenshotUtil;
import org.testng.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe TestNG listener that drives ExtentReports.
 * ExtentTest is stored in a ThreadLocal so parallel threads don't collide.
 */
public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    private static ExtentReports extent;
    // One ExtentTest node per thread
    private static final ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

    // ── Suite lifecycle ───────────────────────────────────────────────────────

    @Override
    public synchronized void onStart(ISuite suite) {
        String reportPath = "target/extent-report/index.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Test Execution Report");
        spark.config().setReportName(suite.getName());
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("OS",       System.getProperty("os.name"));
        extent.setSystemInfo("Java",     System.getProperty("java.version"));
        extent.setSystemInfo("Browser",  ConfigReader.get("browser", "chrome"));
        log.info("ExtentReports initialised → {}", reportPath);
    }

    @Override
    public synchronized void onFinish(ISuite suite) {
        if (extent != null) extent.flush();
        log.info("ExtentReports flushed");
    }

    // ── Test lifecycle ────────────────────────────────────────────────────────

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getTestClass().getRealClass().getSimpleName()
                + " :: " + result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(name);
        testNode.set(test);
        log.info("[Thread {}] START  {}", Thread.currentThread().getId(), name);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testNode.get().log(Status.PASS, "Test passed");
        log.info("[Thread {}] PASS   {}", Thread.currentThread().getId(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testNode.get();
        test.log(Status.FAIL, result.getThrowable());

        if (ConfigReader.getBoolean("screenshot.on.failure", true)) {
            String path = ScreenshotUtil.capture(result.getMethod().getMethodName());
            if (path != null) {
                try {
                    test.addScreenCaptureFromPath(path, "Failure screenshot");
                } catch (Exception e) {
                    log.warn("Could not attach screenshot to report: {}", e.getMessage());
                }
            }
        }
        log.error("[Thread {}] FAIL   {}: {}", Thread.currentThread().getId(),
                result.getMethod().getMethodName(), result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testNode.get().log(Status.SKIP,
                result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped");
        log.warn("[Thread {}] SKIP   {}", Thread.currentThread().getId(),
                result.getMethod().getMethodName());
    }

    /** Utility: log an info message to the current thread's test node. */
    public static void log(String message) {
        ExtentTest test = testNode.get();
        if (test != null) test.log(Status.INFO, message);
    }
}