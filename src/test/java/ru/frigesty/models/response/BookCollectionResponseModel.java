package ru.frigesty.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BookCollectionResponseModel {
    private BookResponseModel [] books;
}
