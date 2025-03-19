package ru.frigesty.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.frigesty.config.WebDriverConfig;
import ru.frigesty.helpers.Attach;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class TestBase {

    private static final WebDriverConfig webDriverConfig =
            ConfigFactory.create(WebDriverConfig.class, System.getProperties());

    @BeforeAll
    static void setUpBrowserConfiguration() {

        configureBrowser();
        if (webDriverConfig.isRemote()) {
            configureRemote();
        }
    }


    private static void configureBrowser() {
        Configuration.browser = webDriverConfig.browser();
        Configuration.browserVersion = webDriverConfig.browserVersion();
        Configuration.browserSize = webDriverConfig.browserSize();
        Configuration.pageLoadStrategy = webDriverConfig.loadStrategy();
        Configuration.baseUrl = webDriverConfig.baseUrl();
        RestAssured.baseURI = webDriverConfig.baseUrl();
    }

    private static void configureRemote() {
        Configuration.remote = webDriverConfig.remoteUrl();
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.addVideo();
        Attach.pageSource();
        if (!Configuration.browser.equalsIgnoreCase("firefox")) {
            Attach.browserConsoleLogs();
        }
    }

    @AfterAll
    public static void tearDownWebDriver() {
        closeWebDriver();
    }
}