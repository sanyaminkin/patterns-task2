package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id= login]").$("[class= input__control]").setValue(registeredUser.getLogin());
        $("[data-test-id= password]").$("[class= input__control]").setValue(registeredUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(".App_appContainer__3jRx1").shouldHave(exactText("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id= login]").$("[class= input__control]").setValue(notRegisteredUser.getLogin());
        $("[data-test-id= password]").$("[class= input__control]").setValue(notRegisteredUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id= error-notification]").shouldBe(visible).
                shouldHave(exactText("Ошибка\n" + "Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
         $("[data-test-id= login]").$("[class= input__control]").setValue(blockedUser.getLogin());
        $("[data-test-id= password]").$("[class= input__control]").setValue(blockedUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id= error-notification]").shouldBe(visible).
                shouldHave(exactText("Ошибка\n" + "Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id= login]").$("[class= input__control]").setValue(registeredUser.getLogin());
        $("[data-test-id= password]").$("[class= input__control]").setValue(wrongLogin);
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id= error-notification]").shouldBe(visible).
                shouldHave(exactText("Ошибка\n" + "Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id= login]").$("[class= input__control]").setValue(registeredUser.getLogin());
        $("[data-test-id= password]").$("[class= input__control]").setValue(wrongPassword);
        $$("button").find(exactText("Продолжить")).click();
        $("[data-test-id= error-notification]").shouldBe(visible).
                shouldHave(exactText("Ошибка\n" + "Ошибка! Неверно указан логин или пароль"));
    }
}
