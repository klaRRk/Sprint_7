package orderTest;

import BaseURI.BaseUriTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.OrderCreateGetList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static constants.Constants.SCOOTER_URL;

public class GetOrderListTest extends BaseUriTest {
    private OrderCreateGetList orderCreateGetList;

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_URL;
        orderCreateGetList = new OrderCreateGetList();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка возврата в тело ответа списка заказов")
    public void getOrderList() {
        ValidatableResponse responseCreate = orderCreateGetList.getOrderList();
        int actualStatusCodeCreate = responseCreate.extract().statusCode();
        List<HashMap> orderBody = responseCreate.extract().path("orders");
        Assert.assertEquals(200, actualStatusCodeCreate);
        // Проверка не пустого тела ответа
        Assert.assertNotNull(orderBody);
        Assert.assertFalse("Список заказов не должен быть пустым", orderBody.isEmpty());
    }
}