package pl.currencyinfo.curinf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.currencyinfo.curinf.entity.Person;
import pl.currencyinfo.curinf.entity.PersonSearchInfo;
import pl.currencyinfo.curinf.exception.CurrencyNotFoundException;
import pl.currencyinfo.curinf.exception.PersonNotFoundException;
import pl.currencyinfo.curinf.model.CurrencyBodyRequest;
import pl.currencyinfo.curinf.nbpData.NbpCurrencyService;
import pl.currencyinfo.curinf.repository.PersonRepository;
import pl.currencyinfo.curinf.repository.PersonSearchInfoRepository;
import pl.currencyinfo.curinf.service.PersonService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceTest.class);

    private PersonService personService;
    private PersonRepository personRepository;
    private NbpCurrencyService nbpCurrencyService;
    private PersonSearchInfoRepository personSearchInfoRepository;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        nbpCurrencyService = mock(NbpCurrencyService.class);
        personSearchInfoRepository = mock(PersonSearchInfoRepository.class);
        personService = new PersonService(personRepository, nbpCurrencyService, personSearchInfoRepository);
    }

    @Test
    void testCalculatePersonWealthInCurrency_Successful() {
        logger.info("Rozpoczęto test: testCalculatePersonWealthInCurrency_Successful");

        // Przygotowanie danych
        CurrencyBodyRequest request = new CurrencyBodyRequest("USD", "Jan Kowalski");
        Person person = new Person(1L, "Jan Kowalski", BigDecimal.valueOf(1000.00));

        when(personRepository.findByName("Jan Kowalski")).thenReturn(Optional.of(person));
        when(nbpCurrencyService.getExchangeRateByCode("USD")).thenReturn(3.8);

        // Wykonanie testowanej metody
        BigDecimal wealth = personService.calculatePersonWealthInCurrency(request);

        // Sprawdzenie wyników
        assertNotNull(wealth);
        assertEquals(BigDecimal.valueOf(3800.00).setScale(4, RoundingMode.HALF_UP), wealth);

        // Weryfikacja zapisu PersonSearchInfo z kodem odpowiedzi 200
        ArgumentCaptor<PersonSearchInfo> captor = ArgumentCaptor.forClass(PersonSearchInfo.class);
        verify(personSearchInfoRepository, times(1)).save(captor.capture());
        PersonSearchInfo savedInfo = captor.getValue();
        assertEquals("Jan Kowalski", savedInfo.getName());
        assertEquals(200, savedInfo.getResponseCode());

        logger.info("Zakończono test: testCalculatePersonWealthInCurrency_Successful");
    }

    @Test
    void testCalculatePersonWealthInCurrency_PersonNotFound() {
        logger.info("Rozpoczęto test: testCalculatePersonWealthInCurrency_PersonNotFound");

        // Przygotowanie danych
        CurrencyBodyRequest request = new CurrencyBodyRequest("USD", "Nieznana Osoba");

        when(personRepository.findByName("Nieznana Osoba")).thenReturn(Optional.empty());

        // Wykonanie testowanej metody i oczekiwanie wyjątku
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class, () ->
                personService.calculatePersonWealthInCurrency(request));

        assertEquals("Person not found with name: Nieznana Osoba", exception.getMessage());

        // Weryfikacja zapisu PersonSearchInfo z kodem odpowiedzi 404
        ArgumentCaptor<PersonSearchInfo> captor = ArgumentCaptor.forClass(PersonSearchInfo.class);
        verify(personSearchInfoRepository, times(1)).save(captor.capture());
        PersonSearchInfo savedInfo = captor.getValue();
        assertEquals("Nieznana Osoba", savedInfo.getName());
        assertEquals(404, savedInfo.getResponseCode());

        logger.info("Zakończono test: testCalculatePersonWealthInCurrency_PersonNotFound");
    }

    @Test
    void testCalculatePersonWealthInCurrency_CurrencyNotFound() {
        logger.info("Rozpoczęto test: testCalculatePersonWealthInCurrency_CurrencyNotFound");

        // Przygotowanie danych
        CurrencyBodyRequest request = new CurrencyBodyRequest("XXX", "Jan Kowalski");
        Person person = new Person(1L, "Jan Kowalski", BigDecimal.valueOf(1000.00));

        when(personRepository.findByName("Jan Kowalski")).thenReturn(Optional.of(person));
        when(nbpCurrencyService.getExchangeRateByCode("XXX")).thenReturn(null);

        // Wykonanie testowanej metody i oczekiwanie wyjątku
        CurrencyNotFoundException exception = assertThrows(CurrencyNotFoundException.class, () ->
                personService.calculatePersonWealthInCurrency(request));

        assertEquals("Currency not found with code: XXX", exception.getMessage());

        // Weryfikacja zapisu PersonSearchInfo z kodem odpowiedzi 404
        ArgumentCaptor<PersonSearchInfo> captor = ArgumentCaptor.forClass(PersonSearchInfo.class);
        verify(personSearchInfoRepository, times(1)).save(captor.capture());
        PersonSearchInfo savedInfo = captor.getValue();
        assertEquals("Jan Kowalski", savedInfo.getName());
        assertEquals(404, savedInfo.getResponseCode());

        logger.info("Zakończono test: testCalculatePersonWealthInCurrency_CurrencyNotFound");
    }

    @Test
    void testCalculatePersonWealthInCurrency_ExceptionInSavingSearchInfo() {
        logger.info("Rozpoczęto test: testCalculatePersonWealthInCurrency_ExceptionInSavingSearchInfo");

        // Przygotowanie danych
        CurrencyBodyRequest request = new CurrencyBodyRequest("USD", "Jan Kowalski");
        Person person = new Person(1L, "Jan Kowalski", BigDecimal.valueOf(1000.00));

        when(personRepository.findByName("Jan Kowalski")).thenReturn(Optional.of(person));
        when(nbpCurrencyService.getExchangeRateByCode("USD")).thenReturn(3.8);
        // Rzucenie wyjątku przy zapisie PersonSearchInfo, aby zapobiec zapisowi do bazy danych
        doThrow(new RuntimeException("Database error")).when(personSearchInfoRepository).save(any(PersonSearchInfo.class));

        // Wykonanie testowanej metody
        BigDecimal wealth = personService.calculatePersonWealthInCurrency(request);

        // Sprawdzenie wyników
        assertNotNull(wealth);
        assertEquals(BigDecimal.valueOf(3800.00).setScale(4, RoundingMode.HALF_UP), wealth);

        // Weryfikacja, że mimo wyjątku metoda działa poprawnie
        verify(personSearchInfoRepository, times(1)).save(any(PersonSearchInfo.class));

        logger.info("Zakończono test: testCalculatePersonWealthInCurrency_ExceptionInSavingSearchInfo");
    }
}