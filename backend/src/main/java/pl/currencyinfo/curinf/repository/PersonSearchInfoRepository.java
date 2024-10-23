package pl.currencyinfo.curinf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.currencyinfo.curinf.entity.PersonSearchInfo;

public interface PersonSearchInfoRepository extends JpaRepository<PersonSearchInfo, Long> {
}
