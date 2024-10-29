package personal.bookshelf.model;

import jakarta.persistence.*;
import lombok.*;
import personal.bookshelf.core.enums.RoleType;

import java.security.Principal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends AbstractEntity implements IdentifiableEntity, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Override
    public String getName() {
        return username;        // username is our principal
    }
}
