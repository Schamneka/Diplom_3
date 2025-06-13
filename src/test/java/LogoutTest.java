import constants.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pom.*;
import user.*;
import utils.Generator;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class LogoutTest {

    private WebDriver driver;
    User user;

    @Before
    public void setup() {
        RestAssured.baseURI = ApiEndpoint.BASE_URI;
        user = Generator.generateUser();
        UserOperations.createUser(user);

        driver = WebDriverCreator.createWebDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverCreator.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(Variables.MAIN_PAGE_URL);

    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void logoutGetSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);

        loginPage.clickProfileButton();
        loginPage.loginUser(user);
        profilePage.clickExitButton();

        MatcherAssert.assertThat(loginPage.getLoginTextFromHeader(), equalTo("Вход"));
    }

    @After
    public void teardown() {
        UserOperations.deleteUser(UserOperations.getAccessToken(user));
        driver.quit();
    }
}
