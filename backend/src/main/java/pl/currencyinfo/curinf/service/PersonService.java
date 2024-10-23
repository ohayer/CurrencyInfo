package pl.currencyinfo.curinf.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.currencyinfo.curinf.entity.Person;
import pl.currencyinfo.curinf.entity.PersonSearchInfo;
import pl.currencyinfo.curinf.exception.PersonNotFoundException;
import pl.currencyinfo.curinf.exception.CurrencyNotFoundException;
import pl.currencyinfo.curinf.model.CurrencyBodyRequest;
import pl.currencyinfo.curinf.nbpData.NbpCurrencyService;
import pl.currencyinfo.curinf.repository.PersonRepository;
import pl.currencyinfo.curinf.repository.PersonSearchInfoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final NbpCurrencyService nbpCurrencyService;

    private final PersonSearchInfoRepository personSearchInfoRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, NbpCurrencyService nbpCurrencyService, PersonSearchInfoRepository personSearchInfoRepository) {
        this.personRepository = personRepository;
        this.nbpCurrencyService = nbpCurrencyService;
        this.personSearchInfoRepository = personSearchInfoRepository;
    }

    /**
     * Oblicza majątek osoby w podanej walucie.
     *
     * @param request obiekt zawierający imię osoby i kod waluty
     * @return wartość majątku po przeliczeniu
     * @throws PersonNotFoundException   jeśli osoba nie zostanie znaleziona
     * @throws CurrencyNotFoundException jeśli kod waluty jest niepoprawny
     */
    public BigDecimal calculatePersonWealthInCurrency(CurrencyBodyRequest request)
            throws PersonNotFoundException, CurrencyNotFoundException {

        int responseCode = 200; // Domyślny kod odpowiedzi to 200 (sukces)
        BigDecimal calculatedWealth = BigDecimal.ZERO;

        try {
            // Znajduję osobę po imieniu
            Person person = personRepository.findByName(request.getName())
                    .orElseThrow(() -> new PersonNotFoundException("Person not found with name: " + request.getName()));

            // Pobieram kurs wymiany dla podanego kodu waluty
            Double exchangeRate = nbpCurrencyService.getExchangeRateByCode(request.getCurrency());
            if (exchangeRate == null) {
                responseCode = 404; // Ustawiam kod błędu dla nieznalezienia kursu
                throw new CurrencyNotFoundException("Currency not found with code: " + request.getCurrency());
            }

            // Obliczam wartość majątku po przeliczeniu
            calculatedWealth = person.getValue()
                    .multiply(BigDecimal.valueOf(exchangeRate))
                    .setScale(4, RoundingMode.HALF_UP);

        } catch (PersonNotFoundException | CurrencyNotFoundException e) {
            responseCode = 404; // Ustawiam kod błędu dla braku osoby lub kursu
            throw e; // Rzucam wyjątek dalej, aby obsłużyły go handlery wyjątków
        } finally {
            // Zapisuję informacje o wyszukiwaniu do bazy
            savePersonSearchInfo(request, responseCode);
        }

        return calculatedWealth;
    }

    /**
     * Zapisuje informacje o wyszukiwaniu osoby do bazy danych.
     *
     * @param request      dane żądania z imieniem osoby i kodem waluty
     * @param responseCode kod odpowiedzi (sukces lub błąd)
     */
    private void savePersonSearchInfo(CurrencyBodyRequest request, int responseCode) {
        try {
            PersonSearchInfo searchInfo = new PersonSearchInfo(
                    request.getName(),
                    responseCode
            );
            personSearchInfoRepository.save(searchInfo);
        } catch (Exception e) {
            System.err.println("Error while saving PersonSearchInfo: " + e.getMessage());
        }
    }
}
