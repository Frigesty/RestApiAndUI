package ru.frigesty.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.frigesty.api.BooksApi;
import ru.frigesty.api.LoginApi;
import ru.frigesty.helpers.WithLogin;
import ru.frigesty.models.response.BookCollectionResponseModel;
import ru.frigesty.models.response.LoginResponseModel;
import ru.frigesty.pages.LoginPage;
import ru.frigesty.pages.ProfilePage;

@Tag("simple")
public class Tests extends TestBase{
    ProfilePage profilePage = new ProfilePage();
    BooksApi booksApi = new BooksApi();
    LoginPage loginPage = new LoginPage();

    @Tag("simple")
    @Test
    public void authorisationOnBookStoreWithUITest() {
        loginPage.openPage();
        loginPage.refreshCookie();
        loginPage.setUserNameAndPassword();
        loginPage.pressOnLoginButton();
        profilePage.checkAuthorization();
    }

    @Tag("simple")
    @Test
    @WithLogin
    public void authorisationOnBookStoreWithAPITest() {
        profilePage.openPage();
        profilePage.checkAuthorization();
    }

    private final int BOOK_NO = 0;

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