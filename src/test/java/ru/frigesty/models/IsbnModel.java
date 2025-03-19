package ru.frigesty.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class IsbnModel {
    public IsbnModel(String isbn) {
        this.isbn = isbn;
    }

    private String isbn;
}