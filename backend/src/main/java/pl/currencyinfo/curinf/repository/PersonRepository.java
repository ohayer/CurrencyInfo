package pl.currencyinfo.curinf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.currencyinfo.curinf.entity.Person;

import java.util.Optional;

public interface  PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);
}
