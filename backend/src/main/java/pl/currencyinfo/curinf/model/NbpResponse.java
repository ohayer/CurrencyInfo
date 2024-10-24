package pl.currencyinfo.curinf.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NbpResponse {
    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;
}
