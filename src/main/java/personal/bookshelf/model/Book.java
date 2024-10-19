package personal.bookshelf.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "books")
public class Book extends AbstractEntity implements IdentifiableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String title;

    // TODO: Create separate table for authors
    private String author;

    @Column(name = "release_year")
    private String releaseYear;

    // TODO: Add more info e.g: ISBN, Publisher
}
