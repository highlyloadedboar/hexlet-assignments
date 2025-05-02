package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import exercise.model.Person;

import java.util.List;
import java.util.Optional;

// BEGIN
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
// END
