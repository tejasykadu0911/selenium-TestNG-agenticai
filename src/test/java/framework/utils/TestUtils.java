package framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class TestUtils {

    public static ExpectedCondition<Boolean> tableHasRows(By rowLocator, int expected) {
        return new ExpectedCondition<Boolean>() {
            @Override public Boolean apply(WebDriver driver) {
                int actual = driver.findElements(rowLocator).size();
                return actual == expected;
            }
            @Override public String toString() {
                return "table to have " + expected + " rows";
            }
        };
    }
// Usage:
//new WebDriverWait(driver, Duration.ofSeconds(10))
//            .until(tableHasRows(By.cssSelector("table#results tbody tr"), 25));

}
