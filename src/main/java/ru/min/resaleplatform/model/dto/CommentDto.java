package ru.min.resaleplatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private int author;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    private int pk;
    private String text;
}
