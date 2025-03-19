package ru.frigesty.api;

import io.qameta.allure.Step;
import ru.frigesty.models.IsbnModel;
import ru.frigesty.models.body.AddBookBodyModel;
import ru.frigesty.models.response.*;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.frigesty.data.Paths.COLLECTION_PATH;
import static ru.frigesty.specs.ApiSpecs.*;

public class BooksApiSteps {

    @Step("Удаление всех книг из профиля через API")
    public void deleteAllBooks(LoginResponseModel loginResponse) {
        given(requestSpecBase)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParam("UserId", loginResponse.getUserId())
        .when()
                .delete(COLLECTION_PATH)
        .then()
                .spec(deleteResponseSpec);
    }

    @Step("Получение коллекции книг через API")
    public BookCollectionResponseModel requestBookCollection() {
        return given(requestSpecBase)
               .when()
                        .get("/BookStore/v1/Books")
               .then()
                        .body(matchesJsonSchemaInClasspath("schemes/getBookCollectionsScheme.json"))
                        .spec(responseSpecBase)
                        .extract().as(BookCollectionResponseModel.class);
    }

    @Step("Добавление новой книги через API")
    public AddBookResponseModel addBook(String isb, String token, String userId) {

        List<IsbnModel> books = new ArrayList<>();
        books.add(new IsbnModel(isb));

        AddBookBodyModel bookData = new AddBookBodyModel();
        bookData.setUserId(userId);
        bookData.setCollectionOfIsbns(books);
        return given(requestSpecBase)
                        .header("Authorization", "Bearer " + token)
                        .body(bookData)
               .when()
                        .post(COLLECTION_PATH)
               .then()
                        .body(matchesJsonSchemaInClasspath("schemes/addBookScheme.json"))
                        .spec(createResponseSpec)
                        .extract().as(AddBookResponseModel.class);
    }
}