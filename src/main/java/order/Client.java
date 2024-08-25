package order;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static constants.Constants.SCOOTER_URL;

public class Client {
    protected RequestSpecification requestSpec; // Хранение спецификации запроса

    public Client() {
        RestAssured.baseURI = SCOOTER_URL;

        // Настройка спецификации запроса
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI) // Устанавливает базовый URL для запросов
                .setContentType(ContentType.JSON) // Устанавливает тип контента запроса как JSON
                .build();  // Строит и возвращает RequestSpecification
    }

    // Получение настроенной спецификации запроса
    protected RequestSpecification getSpec() {
        return requestSpec;
    }
}