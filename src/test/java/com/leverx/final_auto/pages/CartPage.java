package com.leverx.final_auto.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {
    //https://www.saucedemo.com/
    @FindBy(xpath = "//div[@class='cart_item']")
    public List<WebElement> cartItems;

    @FindBy(id = "checkout")
    public WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
