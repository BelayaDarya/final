package com.leverx.final_auto.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CompletePage {

    @FindBy(xpath = "//h2[@class='complete-header']")
    public WebElement header;

    @FindBy(xpath = "//div[@class='summary_subtotal_label']")
    public List<WebElement> itemsPrice;

    @FindBy(xpath = "//div[@class='summary_tax_label']")
    public List<WebElement> taxPrice;

    @FindBy(xpath = "//div[@class='summary_total_label']")
    public List<WebElement> totalPrice;

    @FindBy(id = "finish")
    public WebElement finishButton;

    public CompletePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
