package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StoreOrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String value;

    public StoreOrderStatus(Integer id) {
        this.id = id;
    }

    public StoreOrderStatus(String value) {
        this.value = value;
    }
}
