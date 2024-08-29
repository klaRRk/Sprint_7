package ordertest;

import BaseURI.BaseUriTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.Order;
import order.OrderCreateGetList;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseUriTest {
    private static final String FIRST_NAME = "Иван";
    private static final String LAST_NAME = "Иванов";
    private static final String ADDRESS = "Томск, Ленина 1";
    private static final String METRO_STATION = "ВДНХ";
    private static final String PHONE = "+79998886677";
    private static final int RENT_TIME = 4;
    private static final String DELIVERY_DATE = "2024-08-25";
    private static final String COMMENT = "Быстрее";
    private final String color;
    String track;

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
        Order order = new Order(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, new String[]{color});
        Response response = OrderCreateGetList.createOrder(order);
        // Трек номер заказа
        track = response.then().extract().path("track").toString();
        // Проверка создания заказа
        response.then().assertThat().statusCode(201).and().assertThat().body("track", notNullValue());
    }

    // ИЗМЕНЕНИЕ: Удален метод createOrderWithoutColor(), так как он дублировал функциональность
    // параметризованного теста createOrder() с пустым значением цвета

    // После выполнения теста отменяем созданный заказ
    @After
    public void cancelOrder() {
        OrderCreateGetList.cancelOrder(track);
    }
}