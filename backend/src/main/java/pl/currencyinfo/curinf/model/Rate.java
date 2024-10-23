package pl.currencyinfo.curinf.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rate {
    private String currency;
    private String code;
    private double mid;

}
