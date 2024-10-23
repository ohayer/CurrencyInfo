package pl.currencyinfo.curinf.nbpData;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
     */
    public List<Rate> getAllRates() {
        NbpResponse[] response = restTemplate.getForObject(NBP_API_URL, NbpResponse[].class);
        if (response != null && response.length > 0) {
            return response[0].getRates();
        }
        return List.of();
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