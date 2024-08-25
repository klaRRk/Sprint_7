package orderTest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.Order;
import order.OrderCreateGetList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constants.Constants.SCOOTER_URL;
import static org.hamcrest.CoreMatchers.*;


@RunWith(Parameterized.class)
public class CreateOrderTest {
    private static final String firstName = "Иван";
    private static final String lastName = "Иванов";
    private static final String address = "Томск, Ленина 1";
    private static final String metroStation = "ВДНХ";
    private static final String phone = "+79998886677";
    private static final int rentTime = 4;
    private static final String deliveryDate = "2024-08-25";
    private static final String comment = "Быстрее";
    private final String color;
    String track;

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
    }

    public CreateOrderTest(String color) {
        this.color = color;
    }

    // Параметризация с возможными цвета заказа
    @Parameterized.Parameters(name = "colour = ''{0}''")  // {0} - подставляется значение параметра
    public static Object[] getColour() {
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {""}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Заказ можно создать с указанием только одного или обоих цветов")
    public void createOrder() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, new String[]{color});
        Response response = OrderCreateGetList.createOrder(order);
        // Трек номер заказа
        track = response.then().extract().path("track").toString();
        // Проверка создания заказа
        response.then().assertThat().statusCode(201).and().assertThat().body("track", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без указания параметра color")
    @Description("Заказ можно создать, если не указать color")
    public void createOrderWithoutColor() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        Response response = OrderCreateGetList.createOrder(order);
        track = response.then().extract().path("track").toString();
        response.then().assertThat().statusCode(201).and().assertThat().body("track", notNullValue());
    }

    // После выполнения теста отменяем созданный заказ
    @After
    public void cancelOrder() {
        OrderCreateGetList.cancelOrder(track);
    }
}