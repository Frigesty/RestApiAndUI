package ru.frigesty.api;

import io.qameta.allure.Step;
import ru.frigesty.models.body.LoginBodyModel;
import ru.frigesty.models.response.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.frigesty.data.Credentials.PASSWORD;
import static ru.frigesty.data.Credentials.USERNAME;
import static ru.frigesty.data.Paths.LOGIN_PATH;
import static ru.frigesty.specs.ApiSpecs.requestSpecBase;
import static ru.frigesty.specs.ApiSpecs.responseSpecBase;

public class LoginApi {

    @Step("Авторизация через API")
    public LoginResponseModel login(){
        LoginBodyModel credentialsModel = new LoginBodyModel();
        credentialsModel.setUserName(USERNAME);
        credentialsModel.setPassword(PASSWORD);

        return
                given(requestSpecBase)
                        .body(credentialsModel)
                .when()
                        .post(LOGIN_PATH)
                .then()
                        .body(matchesJsonSchemaInClasspath("schemes/loginScheme.json"))
                        .spec(responseSpecBase)
                        .extract().as(LoginResponseModel.class);
    }
}