package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;
}
// END
