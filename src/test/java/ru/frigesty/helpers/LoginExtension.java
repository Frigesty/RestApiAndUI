package ru.frigesty.helpers;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import ru.frigesty.api.LoginApiSteps;
import ru.frigesty.models.response.LoginResponseModel;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {

        open("/favicon.ico");
        LoginResponseModel authResponse = new LoginApiSteps().login();
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
    }
}