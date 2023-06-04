package ru.min.resaleplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    private String createdAt;
    @ManyToOne
    private User commentAuthor;
    @ManyToOne
    private Ads ads;

    public Comment(String text, String createdAt, User commentAuthor, Ads ads) {
        this.text = text;
        this.createdAt = createdAt;
        this.commentAuthor = commentAuthor;
        this.ads = ads;
    }
}
