package ru.min.resaleplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private int price;
    private String title;
    private String image;
    @ManyToOne
    private User adsAuthor;
    @OneToMany
    private List<Comment> comments;

    public Ads(String description, int price, String title, String image) {
        this.description = description;
        this.price = price;
        this.title = title;
        this.image = image;
    }

    public Ads(String description, int price, String title) {
        this.description = description;
        this.price = price;
        this.title = title;
    }
}
