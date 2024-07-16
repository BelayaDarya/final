package com.leverx.final_auto.tests;

import com.leverx.final_auto.pages.InventoryPage;
import com.leverx.final_auto.pages.LoginPage;
import com.leverx.final_auto.pages.OwerviewPage;
import com.leverx.final_auto.pages.CartPage;
import com.leverx.final_auto.pages.CheckoutPage;
import com.leverx.final_auto.pages.CompletePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class IntegrationTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testUserCanBuyItem() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.usernameInput.sendKeys("standard_user");
        loginPage.passwordInput.sendKeys("secret_sauce");
        loginPage.loginButton.click();

        InventoryPage inventoryPage = new InventoryPage(driver);
        //select object from list by name
        inventoryPage.inventoryItems.stream()
                .filter(item -> item.findElement(By.xpath(".//div[@class='inventory_item_name ']"))
                        .getText().equals("Sauce Labs Backpack"))
                .findFirst()
                .get()
                .findElement(By.xpath(".//button")).click();
        assert (inventoryPage.shoppingCart.getText().equals("1"));
        inventoryPage.inventoryItems.stream()
                .filter(item -> item.findElement(By.xpath(".//div[@class='inventory_item_name ']"))
                        .getText().equals("Sauce Labs Fleece Jacket"))
                .findFirst()
                .get()
                .findElement(By.xpath(".//button")).click();
        assert (inventoryPage.shoppingCart.getText().equals("2"));

        inventoryPage.shoppingCart.click();
        assert (driver.getCurrentUrl().contains("cart.html"));

        CartPage cartPage = new CartPage(driver);
        List<String> selectedItemNames = cartPage.cartItems.stream()
                .map(item -> item.findElement(By.xpath(".//div[@class='inventory_item_name']"))
                        .getText())
                .toList();
        assert (selectedItemNames.contains("Sauce Labs Backpack") &&
                selectedItemNames.contains("Sauce Labs Fleece Jacket"));

        cartPage.checkoutButton.click();
        assert (driver.getCurrentUrl().contains("checkout-step-one.html"));

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.firstNameInput.sendKeys("John");
        checkoutPage.lastNameInput.sendKeys("Doe");
        checkoutPage.postalCodeInput.sendKeys("12345");
        checkoutPage.continueButton.click();

        assert (driver.getCurrentUrl().contains("checkout-step-two.html"));

        OwerviewPage owerviewPage = new OwerviewPage(driver);
        List<String> itemNames = owerviewPage.cartItemsOverview.stream()
                .map(item -> item.findElement(By.xpath(".//div[@class='inventory_item_name']"))
                        .getText())
                .toList();
        assert (itemNames.contains("Sauce Labs Backpack") &&
                itemNames.contains("Sauce Labs Fleece Jacket"));

        List<Float> itemPrices = owerviewPage.cartItemsOverview.stream()
                .map(item -> Float.parseFloat(item.findElement
                                (By.xpath(".//div[@class='inventory_item_price']"))
                        .getText().substring(1))).toList();
        assert (itemPrices.contains(29.99f) && itemPrices.contains(49.99f));
        assert (owerviewPage.itemsPrice.getText().equals("Item total: $79.98"));
        assert (owerviewPage.taxPrice.getText().equals("Tax: $6.40"));
        assert (owerviewPage.totalPrice.getText().equals("Total: $86.38"));

        owerviewPage.finishButton.click();

        assert (driver.getCurrentUrl().contains("checkout-complete.html"));

        CompletePage completePage = new CompletePage(driver);
        assert (completePage.header.getText().equals("Thank you for your order!"));












    }

}