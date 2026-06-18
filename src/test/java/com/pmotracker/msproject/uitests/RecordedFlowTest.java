package com.pmotracker.msproject.uitests;

// MISSING DEPENDENCY: com.microsoft.playwright:playwright
// MISSING DEPENDENCY: junit:junit
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RecordedFlowTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @After
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    public void recordedFlow() {
        // Navigate to the start URL.
        page.navigate("https://www.williams-sonoma.com/");

        // 1. Click the "Cookware Sets" link
        page.locator("//a[normalize-space()='Cookware Sets']").click();
        
        // 2. Hover element
        page.locator("div:nth-of-type(2) > div > div:nth-of-type(1) > a > div > section > div > ul > li:nth-of-type(5) > img").hover();

        // 3. Click element
        page.locator("div:nth-of-type(2) > div > div:nth-of-type(1) > a > div > section > div > ul > li:nth-of-type(6) > img").click();

        // 4. Click the "Track Order" span
        page.locator("//span[normalize-space()='Track Order']").click();

        // 5. Click the "Forgot your password?" link
        page.locator("//a[normalize-space()='Forgot your password?']").click();
    }
}
