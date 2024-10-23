package pl.currencyinfo.curinf.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CurrencyBodyRequest {
    private String currency;
    private String name;
}
