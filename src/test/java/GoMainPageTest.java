import constants.Variables;
import constants.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pom.MainPage;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class GoMainPageTest {

    private WebDriver driver;
    private final String button;

    @Before
    public void setup() {
        driver = WebDriverCreator.createWebDriver();
        driver.manage().timeouts().implicitlyWait(WebDriverCreator.WAIT_SEC_TIMEOUT, TimeUnit.SECONDS);
        driver.navigate().to(Variables.MAIN_PAGE_URL);
    }

    public GoMainPageTest(String button) {
        this.button = button;
    }


    @Parameterized.Parameters(name = "Возврат на главную страницу с помощью кнопки")
    public static Object[] backToMainButtons() {
        return new Object[][]{
                {Variables.LOGO_BACK_TO_MAIN_PAGE},
                {Variables.CONSTRUCTOR_BACK_TO_MAIN},
        };
    }

    @Test
    @DisplayName("Переход на главную страницу")
    public void goFromProfileToMain() {
        MainPage mainPage = new MainPage(driver);

        mainPage.clickProfileButton();
        mainPage.backToMainPage(button);

        MatcherAssert.assertThat(mainPage.getCreateBurgerTextFromHeader(), equalTo("Соберите бургер"));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
