package ru.frigesty.models.body;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginBodyModel {
    String userName, password;
}