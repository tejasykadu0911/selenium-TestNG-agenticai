# Selenium TestNG Framework

A thread-safe Selenium 4 + TestNG automation framework with parallel browser execution, Page Object Model, and ExtentReports HTML reporting.

---

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17 | Language |
| Selenium | 4.21.0 | Browser automation |
| TestNG | 7.10.2 | Test runner + parallel execution |
| WebDriverManager | 5.9.2 | Auto-downloads browser drivers |
| ExtentReports | 5.1.2 | HTML test reports |
| Logback | 1.5.12 | Logging |
| Maven | 3.x | Build + dependency management |

---

## Project Structure

```
src/test/
├── resources/
│   ├── config.properties       # Browser, timeouts, base URL settings
│   ├── testng.xml              # Suite config — parallel mode & thread count
│   └── logback-test.xml        # Console + rolling file logging
└── java/framework/
    ├── config/
    │   └── ConfigReader.java   # Loads config.properties; system props override
    ├── driver/
    │   ├── BrowserType.java    # Enum: CHROME | FIREFOX | EDGE | SAFARI
    │   └── DriverFactory.java  # ThreadLocal<WebDriver> — one browser per thread
    ├── base/
    │   └── BaseTest.java       # @BeforeMethod / @AfterMethod lifecycle
    ├── pages/
    │   ├── BasePage.java       # POM base: click, type, getText, waits
    │   ├── GoogleHomePage.java # Example page object
    │   └── GoogleResultsPage.java
    ├── tests/
    │   ├── SampleTest.java     # Smoke tests against base URL
    │   └── GoogleSearchTest.java
    ├── listeners/
    │   └── TestListener.java   # ExtentReports + screenshot on failure
    └── utils/
        ├── WaitUtil.java       # Explicit wait helpers
        └── ScreenshotUtil.java # Timestamped PNG capture
```

---

## How Parallel Execution Works

`DriverFactory` stores each `WebDriver` in a `ThreadLocal` — every thread gets its own isolated browser session. TestNG spawns threads according to `testng.xml`:

```xml
<suite parallel="methods" thread-count="3">
```

| `parallel` value | Behavior |
|---|---|
| `methods` | Each `@Test` method runs in its own thread (maximum parallelism) |
| `classes` | Each test class runs in its own thread |
| `tests` | Each `<test>` block runs in its own thread |

Change `thread-count` to control how many browsers open simultaneously.

---

## Configuration

Edit `src/test/resources/config.properties`:

```properties
browser=chrome           # chrome | firefox | edge | safari
headless=false           # true for CI environments
base.url=https://www.google.com

implicit.wait=5          # seconds
explicit.wait=15         # seconds
page.load.timeout=30     # seconds

screenshot.on.failure=true
```

System properties override the file at runtime (see CLI examples below).

---

## Running Tests

```bash
# Run full suite (uses testng.xml defaults)
mvn test

# Override browser
mvn test -Dbrowser=firefox

# Run headless (ideal for CI)
mvn test -Dheadless=true

# Override base URL
mvn test -DbaseUrl=https://staging.example.com

# Combine options
mvn test -Dbrowser=edge -Dheadless=true -DbaseUrl=https://staging.example.com
```

---

## Test Outputs

| Artifact | Path |
|---|---|
| HTML report | `target/extent-report/index.html` |
| Failure screenshots | `target/screenshots/` |
| Log file | `target/logs/test.log` |

Open `target/extent-report/index.html` in a browser after the run to view the full report with pass/fail status, step logs, and embedded screenshots.

---

## Adding a New Test

1. **Create a Page Object** in `framework/pages/` extending `BasePage`:

```java
public class LoginPage extends BasePage {
    private static final By EMAIL    = By.id("email");
    private static final By PASSWORD = By.id("password");
    private static final By SUBMIT   = By.cssSelector("button[type='submit']");

    public DashboardPage login(String email, String password) {
        type(EMAIL, email);
        type(PASSWORD, password);
        click(SUBMIT);
        return new DashboardPage();
    }
}
```

2. **Create a test class** in `framework/tests/` extending `BaseTest`:

```java
public class LoginTest extends BaseTest {

    @Test
    public void validLoginRedirectsToDashboard() {
        step("Navigating to login page");
        LoginPage login = new LoginPage();
        DashboardPage dashboard = login.login("user@example.com", "secret");
        Assert.assertTrue(dashboard.isLoaded(), "Dashboard should be visible after login");
    }
}
```

3. **Register the class** in `testng.xml`:

```xml
<class name="framework.tests.LoginTest"/>
```

---

## Adding a New Browser

All browser wiring is in `DriverFactory.java`. To add a new browser, add a case to the `switch` and a value to `BrowserType`:

```java
case SAFARI -> createSafari();   // already present — no headless support
```

To run the same tests across multiple browsers in parallel, add a new `<test>` block to `testng.xml`:

```xml
<test name="Firefox Tests">
    <parameter name="browser"  value="firefox"/>
    <parameter name="headless" value="true"/>
    <classes>
        <class name="framework.tests.GoogleSearchTest"/>
    </classes>
</test>
```

---

## CI Integration (GitHub Actions example)

```yaml
- name: Run Selenium Tests
  run: mvn test -Dheadless=true -Dbrowser=chrome

- name: Upload report
  uses: actions/upload-artifact@v4
  with:
    name: extent-report
    path: target/extent-report/
```
