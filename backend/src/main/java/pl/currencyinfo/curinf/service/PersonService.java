package pl.currencyinfo.curinf.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.currencyinfo.curinf.entity.Person;
import pl.currencyinfo.curinf.exception.PersonNotFoundException;
import pl.currencyinfo.curinf.exception.CurrencyNotFoundException;
import pl.currencyinfo.curinf.model.CurrencyBodyRequest;
import pl.currencyinfo.curinf.nbpData.NbpCurrencyService;
import pl.currencyinfo.curinf.repository.PersonRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final NbpCurrencyService nbpCurrencyService;

    @Autowired
    public PersonService(PersonRepository personRepository, NbpCurrencyService nbpCurrencyService) {
        this.personRepository = personRepository;
        this.nbpCurrencyService = nbpCurrencyService;
    }

    /**
     * Oblicza majątek osoby w podanej walucie.
     *
     * @param request obiekt zawierający imię osoby i kod waluty
     * @return wartość majątku po przeliczeniu
     * @throws PersonNotFoundException    jeśli osoba nie zostanie znaleziona
     * @throws CurrencyNotFoundException  jeśli kod waluty jest niepoprawny
     */
    public BigDecimal calculatePersonWealthInCurrency(CurrencyBodyRequest request)
            throws PersonNotFoundException, CurrencyNotFoundException {

        // Znajduję osobę po imieniu
        Person person = personRepository.findByName(request.getName())
                .orElseThrow(() -> new PersonNotFoundException("Person not found with name: " + request.getName()));

        // Pobieram kurs wymiany dla podanego kodu waluty
        Double exchangeRate = nbpCurrencyService.getExchangeRateByCode(request.getCurrency());
        if (exchangeRate == null) {
            throw new CurrencyNotFoundException("Currency not found with code: " + request.getCurrency());
        }

        // Obliczona wartość majątku po przeliczeniu
        return person.getValue()
                .multiply(BigDecimal.valueOf(exchangeRate))
                .setScale(4, RoundingMode.HALF_UP);
    }
}
