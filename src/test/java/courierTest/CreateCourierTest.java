package couriertest;

import static org.hamcrest.CoreMatchers.equalTo;

import BaseURI.BaseUriTest;
import courier.Courier;
import courier.CourierCreateAuthDelete;
import courier.CourierTestData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

public class CreateCourierTest extends BaseUriTest {
    private final CourierTestData courierTestData = new CourierTestData();
    String id = null; // id курьера

    //Тест на создание курьера с верно заполненными полями.
    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера с верно заполеннными полями")
    public void createCourier () {
        // Объект курьера с валидными данными
        Courier courier = new Courier(courierTestData.getRealLogin(), courierTestData.getRealPassword(), courierTestData.getFirstName());
        // Создание курьера
        Response response = CourierCreateAuthDelete.createCourier(courier);
        // ID курьера после его создания
        id = CourierCreateAuthDelete.loginCourier(courier)
                .then().extract()
                .path("id").toString();
        // Проверка статус кода и тела ответа
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера только с именем и паролем")
    public void createCourierWithoutLogin () {
        // Объект курьера без логина
        Courier courier = new Courier("", courierTestData.getRealPassword(), courierTestData.getFirstName());
        // Создание курьера
        Response response = CourierCreateAuthDelete.createCourier(courier);
        // Проверка статус кода и тела ответа
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера только с именем и логином")
    public void createCourierWithoutPassword() {
        // Объект курьера без пароля
        Courier courier = new Courier(courierTestData.getRealLogin(), "", courierTestData.getFirstName());
        // Создание курьера
        Response response = CourierCreateAuthDelete.createCourier(courier);
        // Проверка статус кода и тела ответа
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание одинаковых курьеров")
    @Description("Создание курьера с верными данными и создание этого же курьера")
    public void createDoubleCouriers() {
        Courier courier = new Courier(courierTestData.getRealLogin(), courierTestData.getRealPassword(), courierTestData.getFirstName());
        CourierCreateAuthDelete.createCourier(courier);
        // Повторное создание того же курьера
        Response response = CourierCreateAuthDelete.createCourier(courier);
        id = CourierCreateAuthDelete.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    // Удаление курьера, если его id не null.
    @After
    public void deleteCourier() {
        if (id != null) {
            CourierCreateAuthDelete.deleteCourier(id);
        }
    }
}