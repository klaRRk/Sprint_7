package order;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {
    protected RequestSpecification requestSpec; // Хранение спецификации запроса

    public Client(){
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