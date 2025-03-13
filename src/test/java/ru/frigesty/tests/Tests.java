package ru.frigesty.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.frigesty.api.BooksApi;
import ru.frigesty.api.LoginApi;
import ru.frigesty.helpers.WithLogin;
import ru.frigesty.models.response.BookCollectionResponseModel;
import ru.frigesty.models.response.LoginResponseModel;
import ru.frigesty.pages.ProfilePage;
import static ru.frigesty.data.Credentials.PASSWORD;
import static ru.frigesty.data.Credentials.USERNAME;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@Tag("simple")
public class Tests extends TestBase{

    @Tag("simple")
    @Test
    public void authorisationOnBookStoreWithUITest() {
        open("/login");
        clearBrowserLocalStorage();
        clearBrowserCookies();
        refresh();
        $("#userName").setValue(USERNAME);
        $("#password").setValue(PASSWORD);
        $("#login").click();
        $("#userName-value").shouldHave(text("BookStore"));
    }

    @Tag("simple")
    @Test
    @WithLogin
    public void authorisationOnBookStoreWithAPITest() {
        open("/profile");
        $("#userName-value").shouldHave(text("BookStore"));
    }

    private final int BOOK_NO = 0;
    ProfilePage profilePage = new ProfilePage();
    BooksApi booksApi = new BooksApi();
    @Test
    @WithLogin
    void successDeleteBookFromProfileTest() {

        BookCollectionResponseModel collection = booksApi.requestBookCollection();

        LoginResponseModel authResponse = new LoginApi().login();

        booksApi.deleteAllBooks(authResponse);

        final String isbn = collection.getBooks()[BOOK_NO].getIsbn();
        final String title = collection.getBooks()[BOOK_NO].getTitle();
        booksApi.addBook(isbn, authResponse.getToken(), authResponse.getUserId());

        profilePage.googleConsent()
                   .openPage();
        profilePage.checkForBook(title);
        profilePage.deleteBook();
        profilePage.confirmDelete();
        profilePage.checkTableBody(title);
    }
}