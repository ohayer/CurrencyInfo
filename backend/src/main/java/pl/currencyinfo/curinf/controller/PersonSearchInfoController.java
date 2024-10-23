package pl.currencyinfo.curinf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.currencyinfo.curinf.service.PersonSearchInfoService;
import pl.currencyinfo.curinf.entity.PersonSearchInfo;

import java.util.List;

@RestController
@RequestMapping("/currencies/")
public class PersonSearchInfoController {

    @Autowired
    PersonSearchInfoService service;

    @GetMapping("requests")
    public List<PersonSearchInfo> getAll() {
        return service.getAll();
    }
}
