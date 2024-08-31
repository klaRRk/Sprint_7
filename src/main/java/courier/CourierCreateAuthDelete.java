package courier;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierCreateAuthDelete {
    @Step("Создание курьера")
    public static Response createCourier(Courier courier) {
        // POST на ручку COURIER
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER);
    }

    @Step("Авторизация курьера")
    public static Response loginCourier(Courier courier) {
        // POST на ручку LOGIN
        return given()
                .header("Content-Type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN);
    }

    @Step("Удаление курьера")
    public static void deleteCourier(String courierId) {
        // DELETE на ручку COURIER
        given()
                .delete(COURIER + "{courierId}", courierId);
    }
}
