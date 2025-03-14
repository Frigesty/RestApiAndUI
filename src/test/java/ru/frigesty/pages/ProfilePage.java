package ru.frigesty.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.frigesty.data.Paths.PROFILE_PATH;

public class ProfilePage {
    final private SelenideElement deleteButton = $("#delete-record-undefined"),
            okButton = $("#closeSmallModal-ok"),
            consentBanner = $(".fc-consent-root");

    @Step("Открытие профиля")
    public ProfilePage openPage() {
        open(PROFILE_PATH);

        return this;
    }

    @Step("Проверка баннера")
    public ProfilePage googleConsent() {
        if (consentBanner.isDisplayed()) {
            consentBanner.$(byText("Consent")).click();
        }
        else{
            System.out.println("No consent banner");
        }
        return this;
    }

    @Step("Проверка, что в коллекции есть книга {title}")
    public ProfilePage checkForBook(String title) {
        $("[id='see-book-"+title+"']").shouldBe(visible);
        return this;
    }

    @Step("Удаление книги через UI")
    public ProfilePage deleteBook() {
        deleteButton.click();
        return this;
    }

    @Step("Подтверждение удаления книги")
    public ProfilePage confirmDelete() {
        okButton.click();
        Selenide.switchTo().alert().accept();
        Selenide.switchTo().parentFrame();
        return this;
    }

    @Step("Проверка, что в коллекции нет книги {title}")
    public ProfilePage checkTableBody(String title) {
        $("[id='see-book-"+title+"']").shouldNot(visible);
        return this;
    }

    @Step("Проверка, что мы авторизовались")
    public ProfilePage checkAuthorization() {
        $("#userName-value").shouldHave(text("BookStore"));
        return this;
    }
}