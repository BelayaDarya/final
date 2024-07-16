package com.leverx.final_auto.tests;

import com.leverx.final_auto.pages.LoginPage;
import com.leverx.final_auto.pages.InventoryPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class InventoryPageTest {
    private WebDriver driver;
    private InventoryPage inventoryPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.usernameInput.sendKeys("standard_user");
        loginPage.passwordInput.sendKeys("secret_sauce");
        loginPage.loginButton.click();
        assert (driver.getCurrentUrl().contains("inventory.html"));

        inventoryPage = new InventoryPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSortByNameByDefaultAsc() {

        String defaultSorting = inventoryPage.activeSortOption.getAttribute("innerText");
        assert (defaultSorting.equals("Name (A to Z)"));
        List<String> names = inventoryPage.inventoryItems.stream()
                .map(item -> item.findElement(By.xpath(".//div[@class='inventory_item_name ']"))
                        .getText())
                .toList();
        // Add for loop to check that names are sorted in ascending order
        for (int i = 0; i < names.size() - 1; i++) {
            assert (names.get(i).compareTo(names.get(i + 1)) < 0);
        }
    }

    @Test
    public void testSortByNameDesc() {

        inventoryPage.sortDropdown.click();
        inventoryPage.sortOptions.get(1).click();
        assert (inventoryPage.activeSortOption.getText().equals("Name (Z to A)"));

        List<String> names = inventoryPage.inventoryItems.stream()
                .map(item -> item.findElement(By.xpath(".//div[@class='inventory_item_name ']"))
                        .getText())
                .toList();
        // Add for loop to check that names are sorted in descending order
        for (int i = 0; i < names.size() - 1; i++) {
            assert (names.get(i).compareTo(names.get(i + 1)) > 0);
        }
    }

    @Test
    public void testSortByPriceAsc() {

        inventoryPage.sortDropdown.click();
        inventoryPage.sortOptions.get(2).click();
        assert (inventoryPage.activeSortOption.getText().equals("Price (low to high)"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Float> prices = inventoryPage.inventoryItems.stream()
                .map(item -> Float.parseFloat(item.findElement(By.xpath(".//div[@class='inventory_item_price']"))
                        .getText().substring(1))).toList();
        // Add for loop to check that prices are sorted from low to high
        for (int i = 0; i < prices.size() - 1; i++) {
            assert (prices.get(i) <= prices.get(i + 1));
        }
    }

    @Test
    public void testSortByPriceDesc() {

        inventoryPage.sortDropdown.click();
        inventoryPage.sortOptions.get(3).click();
        assert (inventoryPage.activeSortOption.getText().equals("Price (high to low)"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<Float> prices = inventoryPage.inventoryItems.stream()
                .map(item -> Float.parseFloat(item.findElement(By.xpath(".//div[@class='inventory_item_price']"))
                        .getText().substring(1))).toList();
        // Add for loop to check that prices are sorted from high to low
        for (int i = 0; i < prices.size() - 1; i++) {
            assert (prices.get(i) >= prices.get(i + 1));
        }
    }

}