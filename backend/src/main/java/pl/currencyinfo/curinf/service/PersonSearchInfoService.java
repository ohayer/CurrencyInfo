package pl.currencyinfo.curinf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.currencyinfo.curinf.entity.PersonSearchInfo;
import pl.currencyinfo.curinf.repository.PersonSearchInfoRepository;

import java.util.List;

@Service
public class PersonSearchInfoService {

    @Autowired
    PersonSearchInfoRepository repository;

    public List<PersonSearchInfo> getAll() {
        return repository.findAll();
    }
}
