package courier;

import com.github.javafaker.Faker;
import lombok.Getter;

@Getter
public class CourierTestData {
    private final String realLogin;
    private final String fakeLogin;
    private final String realPassword;
    private final String fakePassword;
    private final String firstName;

    private static final Faker faker = new Faker();

    public CourierTestData() {
        this.realLogin = generateUniqueUsername();
        this.fakeLogin = generateUniqueUsername() + "nonexistent";
        this.realPassword = faker.internet().password(8, 12);
        this.fakePassword = faker.internet().password(8, 12) + "fake";
        this.firstName = faker.name().firstName();
    }

    private String generateUniqueUsername() {
        return faker.name().username() + faker.number().randomNumber(5, true);
    }

    public static String generateRandomString(int length) {
        return faker.lorem().characters(length);
    }
}