package pl.currencyinfo.curinf.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CurrencyBodyRequest {
    private String currency;
    private String name;
}
