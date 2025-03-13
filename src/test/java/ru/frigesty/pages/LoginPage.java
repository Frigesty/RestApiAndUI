package ru.frigesty.pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.frigesty.data.Credentials.PASSWORD;
import static ru.frigesty.data.Credentials.USERNAME;

public class LoginPage {

    @Step("Вводим логин и пароль")
    public LoginPage setUserNameAndPassword() {
        $("#userName").setValue(USERNAME);
        $("#password").setValue(PASSWORD);
        return this;
    }

    @Step("Нажимаем на кнопку логина")
    public LoginPage pressOnLoginButton() {
        $("#login").click();
        return this;
    }

    @Step("Открытие страницы логина")
    public LoginPage openPage() {
        open("/login");

        return this;
    }

    @Step("Обновляем куки")
    public LoginPage refreshCookie() {
        open("/login");

        return this;
    }
}