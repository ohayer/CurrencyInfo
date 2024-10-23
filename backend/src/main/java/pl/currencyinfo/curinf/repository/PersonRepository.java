package pl.currencyinfo.curinf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.currencyinfo.curinf.entity.Person;

public interface  PersonRepository extends JpaRepository<Person, Long> {
}
