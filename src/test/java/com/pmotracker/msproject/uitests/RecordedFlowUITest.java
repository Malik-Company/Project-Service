// MISSING DEPENDENCY: com.microsoft.playwright:playwright
package com.pmotracker.msproject.uitests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecordedFlowUITest {

    Playwright playwright;
    Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    void closeBrowser() {
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
    @DisplayName("Recorded Flow UI Test")
    void shouldFollowRecordedFlow() {
        // Start URL: https://www.williams-sonoma.com/
        page.navigate("https://www.williams-sonoma.com/");

        // 1. Click the "Cookware" link
        page.getByTestId("top-nav-item-cat-data-link")
            .getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cookware"))
            .click();
        page.waitForURL("https://www.williams-sonoma.com/shop/cookware/");

        // 2. Click the "Rowenta" link
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Rowenta")).click();
        page.waitForURL("https://www.williams-sonoma.com/shop/electrics/brand/rowenta/");

        // 3-5. Check the "Handheld (3)" checkbox
        // The original flow has multiple steps (click label, click input, fill true) for one logical action.
        // This is consolidated into a single 'check' action on the checkbox, which is more robust.
        Locator handheldCheckbox = page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("Handheld (3)"));
        handheldCheckbox.check();
        assertThat(handheldCheckbox).isChecked();
        
        page.waitForLoadState();

        // 6. Click the "Track Order" span
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Track Order")).click();
        assertThat(page).hasURL("https://www.williams-sonoma.com/customer-service/order-tracking.html");

        // 7. Click the "submit" button for tracking order
        page.getByTestId("trackOrderButton").click();
        
        // No assertion specified after this final click.
        // The page would likely show an error message for an empty order number.
    }
}
