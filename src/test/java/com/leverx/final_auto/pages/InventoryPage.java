package com.leverx.final_auto.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InventoryPage {

    @FindBy(xpath = "//span[@class='active_option']")
    public WebElement activeSortOption;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    public WebElement sortDropdown;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    public WebElement shoppingCart;

    @FindBy(xpath = "//select[@class='product_sort_container']/option")
    public List<WebElement> sortOptions;

    @FindBy(xpath = "//div[@class='inventory_item']")
    public List<WebElement> inventoryItems;

    public InventoryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


}
