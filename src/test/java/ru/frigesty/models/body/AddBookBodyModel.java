package ru.frigesty.models.body;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.frigesty.models.IsbnModel;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddBookBodyModel {
    String userId;
    List<IsbnModel> collectionOfIsbns;
}
