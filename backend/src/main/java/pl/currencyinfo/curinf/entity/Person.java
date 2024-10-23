package pl.currencyinfo.curinf.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
* Klasa przechowująca przykłady osób i ich majątku
* Majątek jest przechowywany w walucie PLN
* */
@Entity
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value")
    private BigDecimal value;
}
