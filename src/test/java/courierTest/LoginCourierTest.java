package courierTest;

import static constants.Constants.SCOOTER_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import courier.Courier;
import courier.CourierCreateAuthDelete;
import courier.CourierTestData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LoginCourierTest {
    private final CourierTestData courierTestData = new CourierTestData();
    String id = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    @Test
    @DisplayName("Успешный логин курьера")
    @Description("Курьер может авторизоваться. Запрос возвращает id курьера.")
    public void loginCourier() {
        Courier courier = new Courier(courierTestData.getRealLogin(), courierTestData.getRealPassword());
        CourierCreateAuthDelete.createCourier(courier);
        id = CourierCreateAuthDelete.loginCourier(courier)
                .then()
                .extract()
                .path("id")
                .toString();
        Response response = CourierCreateAuthDelete.loginCourier(courier);
        response.then().assertThat().statusCode(200).and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Для авторизации курьера необходимо передать все обязательные поля.")
    public void authorizationCourierWithoutLogin() {
        Courier courier = new Courier("", courierTestData.getRealPassword());
        Response response = CourierCreateAuthDelete.loginCourier(courier);
        response.then().assertThat().statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Для авторизации курьера необходимо передать все обязательные поля.")
    public void authorizationCourierWithoutPassword() {
        Courier courier = new Courier(courierTestData.getRealLogin(), "");
        Response response = CourierCreateAuthDelete.loginCourier(courier);
        response.then().assertThat().statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера c неправильным логином")
    @Description("Для авторизации курьера необходимо передать существующие данные.")
    public void authorizationCourierWithNonExistentLogin() {
        Courier courier = new Courier(courierTestData.getFakeLogin(), courierTestData.getRealPassword());
        Response response = CourierCreateAuthDelete.loginCourier(courier);
        response.then().assertThat().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера c неправильным паролем")
    @Description("Для авторизации курьера необходимо передать существующие данные.")
    public void authorizationCourierWithNonExistentPassword() {
        Courier courier = new Courier(courierTestData.getRealLogin(), courierTestData.getFakePassword());
        Response response = CourierCreateAuthDelete.loginCourier(courier);
        response.then().assertThat().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        if (id != null) {
            CourierCreateAuthDelete.deleteCourier(id);
        }
    }
}