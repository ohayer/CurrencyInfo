package pl.currencyinfo.curinf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import pl.currencyinfo.curinf.model.NbpResponse;
import pl.currencyinfo.curinf.model.Rate;
import pl.currencyinfo.curinf.nbpData.NbpCurrencyService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NbpCurrencyServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(NbpCurrencyServiceTest.class);

    private NbpCurrencyService nbpCurrencyService;
    private RestTemplate restTemplate;

    private static final String NBP_API_URL = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        nbpCurrencyService = new NbpCurrencyService(restTemplate);
    }

    @Test
    void testGetAllRates_ReturnsRatesList() {
        logger.info("Rozpoczęto test: testGetAllRates_ReturnsRatesList");

        // Przygotowanie danych
        Rate rate1 = new Rate("Dolar amerykański", "USD", 3.8);
        Rate rate2 = new Rate("Euro", "EUR", 4.5);
        NbpResponse nbpResponse = new NbpResponse("A", "123/A/NBP/2021", "2021-09-01", Arrays.asList(rate1, rate2));
        NbpResponse[] nbpResponses = new NbpResponse[]{nbpResponse};

        when(restTemplate.getForObject(eq(NBP_API_URL), eq(NbpResponse[].class))).thenReturn(nbpResponses);

        // Wykonanie testowanej metody
        List<Rate> rates = nbpCurrencyService.getAllRates();

        // Sprawdzenie wyników
        assertNotNull(rates);
        assertEquals(2, rates.size());
        assertEquals("USD", rates.get(0).getCode());
        assertEquals(3.8, rates.get(0).getMid());
        verify(restTemplate, times(1)).getForObject(eq(NBP_API_URL), eq(NbpResponse[].class));

        logger.info("Zakończono test: testGetAllRates_ReturnsRatesList");
    }

    @Test
    void testGetAllRates_ReturnsEmptyListWhenResponseIsNull() {
        logger.info("Rozpoczęto test: testGetAllRates_ReturnsEmptyListWhenResponseIsNull");

        // Przygotowanie danych
        when(restTemplate.getForObject(eq(NBP_API_URL), eq(NbpResponse[].class))).thenReturn(null);

        // Wykonanie testowanej metody
        List<Rate> rates = nbpCurrencyService.getAllRates();

        // Sprawdzenie wyników
        assertNotNull(rates);
        assertTrue(rates.isEmpty());
        verify(restTemplate, times(1)).getForObject(eq(NBP_API_URL), eq(NbpResponse[].class));

        logger.info("Zakończono test: testGetAllRates_ReturnsEmptyListWhenResponseIsNull");
    }

    @Test
    void testGetCurrencyCodesAndRates_ReturnsSimplifiedRates() {
        logger.info("Rozpoczęto test: testGetCurrencyCodesAndRates_ReturnsSimplifiedRates");

        // Przygotowanie danych
        Rate rate1 = new Rate("Dolar amerykański", "USD", 3.8);
        Rate rate2 = new Rate("Euro", "EUR", 4.5);
        NbpResponse nbpResponse = new NbpResponse("A", "123/A/NBP/2021", "2021-09-01", Arrays.asList(rate1, rate2));
        NbpResponse[] nbpResponses = new NbpResponse[]{nbpResponse};

        when(restTemplate.getForObject(eq(NBP_API_URL), eq(NbpResponse[].class))).thenReturn(nbpResponses);

        // Wykonanie testowanej metody
        List<Rate> simplifiedRates = nbpCurrencyService.getCurrencyCodesAndRates();

        // Sprawdzenie wyników
        assertNotNull(simplifiedRates);
        assertEquals(2, simplifiedRates.size());
        assertEquals("USD", simplifiedRates.get(0).getCode());
        assertNull(simplifiedRates.get(0).getCurrency()); // Nazwa waluty powinna być null
        assertEquals(3.8, simplifiedRates.get(0).getMid());

        logger.info("Zakończono test: testGetCurrencyCodesAndRates_ReturnsSimplifiedRates");
    }

    @Test
    void testGetExchangeRateByCode_ReturnsCorrectRate() {
        logger.info("Rozpoczęto test: testGetExchangeRateByCode_ReturnsCorrectRate");

        // Przygotowanie danych
        Rate rate1 = new Rate("Dolar amerykański", "USD", 3.8);
        Rate rate2 = new Rate("Euro", "EUR", 4.5);
        NbpResponse nbpResponse = new NbpResponse("A", "123/A/NBP/2021", "2021-09-01", Arrays.asList(rate1, rate2));
        NbpResponse[] nbpResponses = new NbpResponse[]{nbpResponse};

        when(restTemplate.getForObject(eq(NBP_API_URL), eq(NbpResponse[].class))).thenReturn(nbpResponses);

        // Wykonanie testowanej metody
        Double rate = nbpCurrencyService.getExchangeRateByCode("USD");

        // Sprawdzenie wyników
        assertNotNull(rate);
        assertEquals(3.8, rate);
        verify(restTemplate, times(1)).getForObject(eq(NBP_API_URL), eq(NbpResponse[].class));

        logger.info("Zakończono test: testGetExchangeRateByCode_ReturnsCorrectRate");
    }

    @Test
    void testGetExchangeRateByCode_ReturnsNullWhenCodeNotFound() {
        logger.info("Rozpoczęto test: testGetExchangeRateByCode_ReturnsNullWhenCodeNotFound");

        // Przygotowanie danych
        Rate rate1 = new Rate("Euro", "EUR", 4.5);
        NbpResponse nbpResponse = new NbpResponse("A", "123/A/NBP/2021", "2021-09-01", Collections.singletonList(rate1));
        NbpResponse[] nbpResponses = new NbpResponse[]{nbpResponse};

        when(restTemplate.getForObject(eq(NBP_API_URL), eq(NbpResponse[].class))).thenReturn(nbpResponses);

        // Wykonanie testowanej metody
        Double rate = nbpCurrencyService.getExchangeRateByCode("USD");

        // Sprawdzenie wyników
        assertNull(rate);
        verify(restTemplate, times(1)).getForObject(eq(NBP_API_URL), eq(NbpResponse[].class));

        logger.info("Zakończono test: testGetExchangeRateByCode_ReturnsNullWhenCodeNotFound");
    }
}