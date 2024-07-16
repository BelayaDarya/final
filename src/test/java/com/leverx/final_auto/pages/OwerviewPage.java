package com.leverx.final_auto.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OwerviewPage {

    @FindBy(xpath = "//div[@class='cart_item']")
    public List<WebElement> cartItemsOverview;

    @FindBy(xpath = "//div[@class='summary_subtotal_label']")
    public WebElement itemsPrice;

    @FindBy(xpath = "//div[@class='summary_tax_label']")
    public WebElement taxPrice;

    @FindBy(xpath = "//div[@class='summary_total_label']")
    public WebElement totalPrice;

    @FindBy(id = "finish")
    public WebElement finishButton;

    public OwerviewPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
