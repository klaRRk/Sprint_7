package courier;

import java.util.Random;

public class CourierTestData {
    private final String realLogin;      // Существующий логин курьера
    private final String fakeLogin;      // Несуществующий логин курьера
    private final String realPassword;   // Существующий пароль курьера
    private final String fakePassword;   // Несуществующий пароль курьера
    private final String firstName;          // Имя курьера

    //Конструктор класса c тестовыми данные
    public CourierTestData() {
        this.realLogin = randomLoginOrPass(9);         // Генерация случайного логина
        this.fakeLogin = randomLoginOrPass(9) + "abc"; // Генерация случайного несуществующего логина c добавлением "abc"
        this.realPassword = "existPass";                      // Установка существующего пароля
        this.fakePassword = randomLoginOrPass(8);      // Генерация случайного несуществующего пароля
        this.firstName = "firstName";                             // Установка имени курьера
    }

    // Метод для генерации случайного логина или пароля заданной длины
    public static String randomLoginOrPass (int length) {
        int leftLimit = 97;   // Код символа 'a' в таблице ASCII
        int rightLimit = 122; // Код символа 'z' в таблице ASCII
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)   // Последовательность случайных целых чисел в заданном диапазоне с шагом 1 (от 'a' до 'z' включительно).
                .limit(length)    // Ограничивает количество сгенерированных чисел количеством символов в строке.
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)  // Здесь используется метод collect, который собирает сгенерированные числа в объект StringBuilder. StringBuilder::new создает новый объект StringBuilder, StringBuilder::appendCodePoint добавляет к нему каждый сгенерированный код символа.
                .toString();  // Вызывается метод toString(), чтобы преобразовать объект StringBuilder в строку.
    }

    // Существующий логин курьера
    public String getRealLogin() {
        return realLogin;
    }
    // Несуществующий логин курьера
    public String getFakeLogin() {
        return fakeLogin;
    }
    // Существующий пароль курьера
    public String getRealPassword() {
        return realPassword;
    }
    // Несуществующий пароль курьера
    public String getFakePassword() {
        return fakePassword;
    }
    // Имя курьера
    public String getFirstName() {
        return firstName;
    }
}