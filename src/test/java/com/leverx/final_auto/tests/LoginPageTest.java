package com.leverx.final_auto.tests;
import com.leverx.final_auto.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class LoginPageTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLoginSuccessIfValidCredentials() {

        loginPage.usernameInput.sendKeys("standard_user");
        loginPage.passwordInput.sendKeys("secret_sauce");
        loginPage.loginButton.click();
        assert (driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    public void testLoginFailIfInvalidLogin() {

        loginPage.usernameInput.sendKeys("invalid_user");
        loginPage.passwordInput.sendKeys("secret_sauce");
        loginPage.loginButton.click();

        loginPage.errorMessage.isDisplayed();
        String errorMessageText = loginPage.errorMessage.getText();
        assert (errorMessageText.equals("Epic sadface: Username and password do not match any user in this service"));
        assert (driver.getCurrentUrl().equals("https://www.saucedemo.com/"));
    }

    @Test
    public void testLoginFailIfInvalidPassword() {

        loginPage.usernameInput.sendKeys("standard_user");
        loginPage.passwordInput.sendKeys("invalid_password");
        loginPage.loginButton.click();

        loginPage.errorMessage.isDisplayed();
        String errorMessageText = loginPage.errorMessage.getText();
        assert (errorMessageText.equals("Epic sadface: Username and password do not match any user in this service"));
        assert (driver.getCurrentUrl().equals("https://www.saucedemo.com/"));
    }

    @Test
    public void testLoginFailIfEmptyCredentials() {

        loginPage.usernameInput.clear();
        loginPage.passwordInput.clear();
        loginPage.loginButton.click();

        loginPage.errorMessage.isDisplayed();
        String errorMessageText = loginPage.errorMessage.getText();
        assert (errorMessageText.equals("Epic sadface: Username is required"));
        assert (driver.getCurrentUrl().equals("https://www.saucedemo.com/"));
    }

}