package pl.currencyinfo.curinf.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/*
 * Klasa przechowująca informacje o osobie, której dane zostały wyszukane wraz z datą i kodem odpowiedzi
 * */
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PersonSearchInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, updatable = false)
    private Instant date;

    @Size(min = 200, max = 600)
    private int responseCode;

    @PrePersist
    protected void onCreate() {
        this.date = Instant.now();
    }

    /**
     * Konstruktor przyjmujący parametry name i responseCode.
     *
     * @param name         nazwa osoby
     * @param responseCode kod odpowiedzi
     */
    public PersonSearchInfo(String name, int responseCode) {
        this.name = name;
        this.responseCode = responseCode;
    }
}
