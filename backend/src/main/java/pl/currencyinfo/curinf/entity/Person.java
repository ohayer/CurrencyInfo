package pl.currencyinfo.curinf.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
* Klasa przechowująca przykłady osób i ich majątku
* Majątek jest przechowywany w walucie PLN
* */
@Entity
@NoArgsConstructor
@Getter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Size(min = 0)
    private BigDecimal value;
}
