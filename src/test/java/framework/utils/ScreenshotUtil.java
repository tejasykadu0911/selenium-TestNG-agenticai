package framework.utils;

import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil() {}

    /**
     * Captures a screenshot and returns the absolute file path, or null on failure.
     * Thread-safe: each thread writes to its own timestamped file.
     */
    public static String capture(String testName) {
        try {
            String dir = ConfigReader.get("screenshot.dir", "target/screenshots");
            Path screenshotDir = Paths.get(dir);
            Files.createDirectories(screenshotDir);

            String fileName = testName + "_" + LocalDateTime.now().format(FMT) + ".png";
            Path dest = screenshotDir.resolve(fileName);

            byte[] bytes = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            Files.write(dest, bytes);

            log.info("Screenshot saved: {}", dest.toAbsolutePath());
            return dest.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Failed to save screenshot for {}: {}", testName, e.getMessage());
            return null;
        }
    }
}