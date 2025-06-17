import constants.ApiEndpoint;
import constants.Variables;
import constants.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pom.*;
import user.*;
import utils.Generator;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class GoToProfileTests {

    private WebDriver driver;

    @Before
    public void setup() {
        RestAssured.baseURI = ApiEndpoint.BASE_URI;
        driver = WebDriverCreator.createWebDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverCreator.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(Variables.MAIN_PAGE_URL);
    }

    @Test
    @DisplayName("Переход в профиль авторизованного пользователя")
    public void goToProfileAuthUserGetProfile() {
        User user = Generator.generateUser();
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        UserOperations.createUser(user);
        mainPage.clickProfileButton();
        loginPage.loginUser(user);

        Assert.assertTrue(profilePage.btnProfileTabIsEnabled());
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
    }

    @Test
    @DisplayName("Переход в профиль не авторизованного пользователя")
    public void goToProfileUnauthUserGetLogin() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        mainPage.clickProfileButton();

        MatcherAssert.assertThat(loginPage.getLoginTextFromHeader(), equalTo("Вход"));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
