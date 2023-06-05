package ru.min.resaleplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsDto {

    private int author;
    private String image;
    private int pk;
    private int price;
    private String title;
}
