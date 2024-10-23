package pl.currencyinfo.curinf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.time.Instant;

/*
* Klasa przechowująca informacje o osobie, której dane zostały wyszukane wraz z datą i kodem odpowiedzi
* */
@Entity
@NoArgsConstructor
public class PersonSearchInfo extends Person {
    private String currency;
    @Column(name = "date", nullable = false, updatable = false)
    private Instant date;

    @Size(min = 200, max = 600)
    private int responseCode;

    @PrePersist
    protected void onCreate() {
        this.date = Instant.now();
    }
}
