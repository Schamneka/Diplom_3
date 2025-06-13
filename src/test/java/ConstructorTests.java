import constants.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pom.*;

import java.util.concurrent.TimeUnit;

public class ConstructorTests {

    private WebDriver driver;

    @Before
    public void setup() {
        driver = WebDriverCreator.createWebDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverCreator.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(Variables.MAIN_PAGE_URL);
    }

    @Test
    @DisplayName("Переход в раздел «Булки»")
    public void SwitchToBunsTabGetSuccess() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsButton();
        mainPage.clickBunsButton();
        Assert.assertTrue(mainPage.btnBunsIsEnabled());
    }

    @Test
    @DisplayName("Переход в раздел «Соусы»")
    public void SwitchToSaucesTabGetSuccess() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesButton();
        Assert.assertTrue(mainPage.btnSaucesIsEnabled());
    }

    @Test
    @DisplayName("Переход в раздел «Начинки»")
    public void SwitchToFillingsTabGetSuccess() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsButton();
        Assert.assertTrue(mainPage.btnFillingsIsEnabled());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
