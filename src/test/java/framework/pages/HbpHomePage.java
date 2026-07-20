package framework.pages;

import framework.utils.WaitUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


/** Page Object for https://hbsp.harvard.edu/home/ */
public class HbpHomePage extends BasePage{

    // ── Header / Navigation ──────────────────────────────────────────────────
    private static final By TOP_NAV                    = By.cssSelector("[data-testid='top-navigation']");
    private static final By CATALOG_NAV_BUTTON         = By.cssSelector("``[data-testid='flyout-navigation__catalog-button']``");
    private static final By TEACHING_CENTER_NAV_BUTTON = By.cssSelector("[data-testid='flyout-navigation__teaching-center-button']");
    private static final By SEARCH_TOGGLE              = By.cssSelector("[aria-label='Toggle Search']");
    private static final By USER_ACCOUNT_NAV           = By.cssSelector("[aria-label='Sign in or Register']");
    private static final By SIGN_IN_LINK               = By.cssSelector("a[href='/signin']");
    private static final By REGISTER_LINK              = By.cssSelector("a[href='/registration']");
    private static final By MOBILE_MENU_TOGGLE         = By.cssSelector("[aria-label='Toggle menu']");
    private static final By SIGN_In                    = By.xpath("//span[@class='he-call-to-action__text' and text()='Sign In']");
    private static final By USERNAME                   = By.xpath("//input[@id='sign-in-form-username' and @name='username']");
    private static final By PASSWORD                   = By.xpath("//input[@id='sign-in-form-password' and @name='password']");
    private static final By SIGNINUSER                 = By.xpath("//a[@class='signin-form__register-link' and text()='New? Register for a free account']/preceding-sibling::button");
    // ── Search ───────────────────────────────────────────────────────────────
    private static final By SEARCH_INPUT  = By.id("search-coveo-input-top-navigation-search-box");
    private static final By SEARCH_FORM   = By.cssSelector("[data-testid='search-coveo-form']");
    private static final By SEARCH_SUBMIT = By.cssSelector("[aria-label='Enter Search']");

    // ── Hero section ─────────────────────────────────────────────────────────
    private static final By HERO_HEADING            = By.xpath("//*[contains(text(),'Build engaging business courses')]");
    private static final By HERO_MEDIA              = By.cssSelector("[data-testid='hero-new-media']");
    private static final By REGISTER_FOR_FREE_BTN   = By.xpath("//*[normalize-space()='Register for Free']");

    // ── Feature cards ────────────────────────────────────────────────────────
    private static final By BROWSE_CATALOG_CARD    = By.xpath("//*[contains(text(),'Browse Our Extensive Catalog')]");
    private static final By COURSE_EXPLORER_CARD   = By.xpath("//*[contains(text(),'Explore Curated Course Content')]");
    private static final By ENHANCE_TEACHING_CARD  = By.xpath("//*[contains(text(),'Enhance Your Teaching Skills')]");

    // ── Catalog section ──────────────────────────────────────────────────────
    private static final By CATALOG_SECTION  = By.cssSelector("[data-testid='catalog-section']");
    private static final By CATALOG_CAROUSEL = By.cssSelector("[data-testid='carousel-container']");

    // ── Teaching Center section ──────────────────────────────────────────────
    private static final By TEACHING_CENTER_SECTION  = By.cssSelector("[data-testid='teaching-center-section']");
    private static final By TEACHING_CENTER_ARTICLES = By.cssSelector("[data-testid='teaching-center-content-latest-article-item-anchor']");

    // ── Cookie banner ────────────────────────────────────────────────────────
    private static final By COOKIE_ACCEPT_BTN    = By.cssSelector("[aria-label='Accept cookie notice']");
    private static final By COOKIE_READ_FULL_BTN = By.cssSelector("[aria-label='Read full cookie notice']");

    // ── Footer ───────────────────────────────────────────────────────────────
    private static final By FOOTER               = By.cssSelector("footer");
    private static final By FOOTER_ABOUT_US      = By.cssSelector("footer a[href='/about-us/']");
    private static final By FOOTER_CONTACT_US    = By.cssSelector("footer a[href='/contact-us/']");
    private static final By FOOTER_PRIVACY       = By.cssSelector("footer a[href='/privacy-policy/']");
    private static final By FOOTER_TERMS         = By.cssSelector("footer a[href='https://hbsp.harvard.edu/terms-of-use']");
    private static final By FOOTER_HELP_CENTER   = By.cssSelector("footer a[href='https://help.hbsp.harvard.edu/']");
    private static final By FOOTER_INSPIRING_MINDS = By.cssSelector("footer a[href='/inspiring-minds/']");
    private static final By FOOTER_CAREERS       = By.cssSelector("footer a[href='/careers/']");
    private static final By FOOTER_FACEBOOK      = By.cssSelector("footer a[href*='facebook.com']");
    private static final By FOOTER_LINKEDIN      = By.cssSelector("footer a[href*='linkedin.com']");
    private static final By FOOTER_TWITTER       = By.cssSelector("footer a[href*='twitter.com']");
    private static final By FOOTER_YOUTUBE       = By.cssSelector("footer a[href*='youtube.com']");

    @FindBy(id = "search-coveo-input-top-navigation-search-box")
    private WebElement searchInput;


    public HbpHomePage() {
        super();
    }


    // ── Lifecycle ────────────────────────────────────────────────────────────

    public HbpHomePage open() {
        WaitUtil.untilVisible(TOP_NAV);
        dismissCookieBannerIfPresent();
        return this;
    }

    public void dismissCookieBannerIfPresent() {
        // Try the primary aria-label locator first, then fall back to button text
        try {
            WebElement btn = driver().findElement(COOKIE_ACCEPT_BTN);
            if (btn.isDisplayed()) { btn.click(); return; }
        } catch (NoSuchElementException ignored) {}
        try {
            WebElement btn = driver().findElement(
                    By.xpath("//button[normalize-space()='Accept']"));
            if (btn.isDisplayed()) btn.click();
        } catch (NoSuchElementException ignored) {}
    }

    // ── Header actions ───────────────────────────────────────────────────────

    public HbpHomePage clickCatalogNav() {
        click(CATALOG_NAV_BUTTON);
        return this;
    }

    public HbpHomePage clickTeachingCenterNav() {
        click(TEACHING_CENTER_NAV_BUTTON);
        return this;
    }

    public HbpHomePage toggleSearchBar() {
        click(SEARCH_TOGGLE);
        return this;
    }

    public HbpHomePage clickUserAccountNav() {
        click(USER_ACCOUNT_NAV);
        return this;
    }

    public void clickSignIn() {
        click(SIGN_IN_LINK);
    }

    public void clickOnSignIn(){
        click(SIGN_In);
    }

    public void clickRegister() {
        click(REGISTER_LINK);
    }

    public void clickMobileMenuToggle() {
        click(MOBILE_MENU_TOGGLE);
    }


    private void acceptCookies(){
        dismissCookieBannerIfPresent();
    }

    public void signIn(String userName, String password){
        acceptCookies();
        click(SIGN_In);
        // The sign-in page may show its own cookie banner; dismiss before interacting
        dismissCookieBannerIfPresent();
        type(USERNAME, userName);
        dismissCookieBannerIfPresent();
        type(PASSWORD, password);
        click(SIGNINUSER);
    }


    // ── Search ───────────────────────────────────────────────────────────────

    public HbpHomePage search(String query) {
        toggleSearchBar();
        WaitUtil.untilVisible(SEARCH_INPUT);
        searchInput.clear();
        searchInput.sendKeys(query + Keys.RETURN);
        return this;
    }

    public boolean isSearchFormDisplayed() {
        return isDisplayed(SEARCH_FORM);
    }

    // ── Hero ─────────────────────────────────────────────────────────────────

    public String getHeroHeadingText() {
        return getText(HERO_HEADING);
    }

    public boolean isHeroMediaDisplayed() {
        return isDisplayed(HERO_MEDIA);
    }

    public void clickRegisterForFree() {
        click(REGISTER_FOR_FREE_BTN);
    }

    // ── Feature cards ────────────────────────────────────────────────────────

    public boolean isBrowseCatalogCardDisplayed() {
        return isDisplayed(BROWSE_CATALOG_CARD);
    }

    public boolean isCourseExplorerCardDisplayed() {
        return isDisplayed(COURSE_EXPLORER_CARD);
    }

    public boolean isEnhanceTeachingCardDisplayed() {
        return isDisplayed(ENHANCE_TEACHING_CARD);
    }

    // ── Catalog section ──────────────────────────────────────────────────────

    public boolean isCatalogSectionDisplayed() {
        return isDisplayed(CATALOG_SECTION);
    }

    public boolean isCatalogCarouselDisplayed() {
        return isDisplayed(CATALOG_CAROUSEL);
    }

    // ── Teaching Center ──────────────────────────────────────────────────────

    public boolean isTeachingCenterSectionDisplayed() {
        return isDisplayed(TEACHING_CENTER_SECTION);
    }

    public int getTeachingCenterArticleCount() {
        WaitUtil.untilVisible(TEACHING_CENTER_SECTION);
        return driver().findElements(TEACHING_CENTER_ARTICLES).size();
    }

    // ── Footer ───────────────────────────────────────────────────────────────

    public boolean isFooterDisplayed() {
        return isDisplayed(FOOTER);
    }

    public void clickFooterAboutUs() {
        scrollIntoView(driver().findElement(FOOTER_ABOUT_US));
        click(FOOTER_ABOUT_US);
    }

    public void clickFooterContactUs() {
        scrollIntoView(driver().findElement(FOOTER_CONTACT_US));
        click(FOOTER_CONTACT_US);
    }

    public void clickFooterPrivacyPolicy() {
        scrollIntoView(driver().findElement(FOOTER_PRIVACY));
        click(FOOTER_PRIVACY);
    }

    public void clickFooterHelpCenter() {
        scrollIntoView(driver().findElement(FOOTER_HELP_CENTER));
        click(FOOTER_HELP_CENTER);
    }

    public void clickFooterInspiringMinds() {
        scrollIntoView(driver().findElement(FOOTER_INSPIRING_MINDS));
        click(FOOTER_INSPIRING_MINDS);
    }

    public void clickFooterCareers() {
        scrollIntoView(driver().findElement(FOOTER_CAREERS));
        click(FOOTER_CAREERS);
    }

    public void clickFooterFacebook() {
        scrollIntoView(driver().findElement(FOOTER_FACEBOOK));
        click(FOOTER_FACEBOOK);
    }

    public void clickFooterLinkedIn() {
        scrollIntoView(driver().findElement(FOOTER_LINKEDIN));
        click(FOOTER_LINKEDIN);
    }

    public void clickFooterTwitter() {
        scrollIntoView(driver().findElement(FOOTER_TWITTER));
        click(FOOTER_TWITTER);
    }

    public void clickFooterYouTube() {
        scrollIntoView(driver().findElement(FOOTER_YOUTUBE));
        click(FOOTER_YOUTUBE);
    }
}
