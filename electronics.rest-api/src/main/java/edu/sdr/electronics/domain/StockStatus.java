package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StockStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String value;


    public StockStatus(Integer id) {
        this.id = id;
    }
    public StockStatus(String value) {
        this.value = value;
    }
}
