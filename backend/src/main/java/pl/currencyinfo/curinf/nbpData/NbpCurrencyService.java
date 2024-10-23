package pl.currencyinfo.curinf.nbpData;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import pl.currencyinfo.curinf.exception.NbpApiException;
import pl.currencyinfo.curinf.model.NbpResponse;
import pl.currencyinfo.curinf.model.Rate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NbpCurrencyService {

    private static final String NBP_API_URL = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";

    private final RestTemplate restTemplate;

    public NbpCurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Pobiera wszystkie dane z API NBP.
     *
     * @return lista wszystkich kursów walut
     * @throws NbpApiException jeśli API NBP zwraca błędy 404, 400 lub inne błędy serwera
     */
    public List<Rate> getAllRates() throws NbpApiException {
        try {
            NbpResponse[] response = restTemplate.getForObject(NBP_API_URL, NbpResponse[].class);
            if (response != null && response.length > 0) {
                return response[0].getRates();
            }
            return List.of();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NbpApiException("404 Not Found: No data found for the specified time range");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                if (e.getResponseBodyAsString().contains("limit")) {
                    throw new NbpApiException("400 Bad Request - Limit exceeded");
                }
                throw new NbpApiException("400 Bad Request: Invalid request to NBP API");
            } else {
                throw new NbpApiException("Fatal error on NBP API side: " + e.getMessage());
            }
        } catch (HttpServerErrorException e) {
            throw new NbpApiException("Fatal error on NBP API side: " + e.getMessage());
        } catch (Exception e) {
            throw new NbpApiException("Unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Pobiera listę kodów walut i ich kursów.
     *
     * @return lista obiektów Rate zawierających tylko pola code i mid
     */
    public List<Rate> getCurrencyCodesAndRates() {
        return getAllRates().stream()
                .map(rate -> {
                    Rate simplifiedRate = new Rate();
                    simplifiedRate.setCode(rate.getCode());
                    simplifiedRate.setMid(rate.getMid());
                    return simplifiedRate;
                })
                .collect(Collectors.toList());
    }

    /**
     * Zwraca kurs dla podanego kodu waluty.
     *
     * @param code kod waluty (np. "USD")
     * @return kurs waluty lub null, jeśli nie znaleziono
     */
    public Double getExchangeRateByCode(String code) {
        Optional<Rate> rateOptional = getAllRates().stream()
                .filter(rate -> rate.getCode().equalsIgnoreCase(code))
                .findFirst();

        return rateOptional.map(Rate::getMid).orElse(null);
    }
}