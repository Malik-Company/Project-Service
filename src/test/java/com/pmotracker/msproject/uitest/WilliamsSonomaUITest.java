package com.pmotracker.msproject.uitest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class WilliamsSonomaUITest {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void testWilliamsSonomaFlow() {
        page.navigate("https://www.williams-sonoma.com/");

        // 1. Click the "Brands" link
        page.locator("//a[normalize-space()='Brands']").click();

        // 2. Click the "A" section div
        page.locator("#A").click();

        // 3. Click the "AERIN" link
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("AERIN")).click();

        // 4. Click element
        page.locator("//img[@title='Aerin Lauder']").click();

        // 5. Hover element
        page.locator("//img[@title='Aerin Lauder']").hover();

        // 6. Click the "Track Order" span
        page.locator("//span[normalize-space()='Track Order']").click();

        // Assertion: Check if we are on the track order page
        assertThat(page.locator("h1:has-text('Track Your Order')")).isVisible();

        // 7. Click the "Postal Code (required)" field
        page.locator("#postalCode").click();

        // 8. Click the "submit" field
        page.locator("#orderStatusButton").click();

        // 9. Click the "Track Your OrderSign in to view order historyUse t…" div
        page.locator("body > div:nth-of-type(1) > div > div > div").click();
        
        // 10. Click the "Track Your OrderSign in to view order historyUse t…" div
        page.locator("body > div:nth-of-type(1) > div > div > div").click();
    }
}
