package framework.utils;

import framework.config.ConfigReader;
import framework.driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtil {

    private WaitUtil() {}

    private static WebDriverWait build() {
        int timeout = ConfigReader.getInt("explicit.wait", 15);
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeout));
    }

    public static WebElement untilVisible(By locator) {
        return build().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement untilClickable(By locator) {
        return build().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean untilInvisible(By locator) {
        return build().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void untilUrlContains(String fragment) {
        build().until(ExpectedConditions.urlContains(fragment));
    }

    public static void untilTitleContains(String title) {
        build().until(ExpectedConditions.titleContains(title));
    }

    public static void untilTableHasRows(By rowLocator, int expectedRows) {
        build().until(TestUtils.tableHasRows(rowLocator, expectedRows));
    }
}