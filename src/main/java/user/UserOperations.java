package user;

import constants.ApiEndpoint;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserOperations {

    @Step("Регистрация ползователя")
    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(ApiEndpoint.REGISTER_PATH);
    }

    @Step("Авторизация зарегистрированным пользователем")
    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(ApiEndpoint.LOGIN_PATH);
    }

    @Step("Получить доступ к токену пользователя")
    public static String getAccessToken(User user) {
        return loginUser(user).then().extract().path("accessToken");
    }

    @Step("Удаление пользователя")
    public static void deleteUser(String accessToken) {
        if (accessToken != null)
            given()
                    .header("Authorization", accessToken)
                    .when()
                    .delete(ApiEndpoint.DELETE_PATH);
    }
}
