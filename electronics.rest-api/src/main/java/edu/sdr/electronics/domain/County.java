package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class County {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;



    public County(String name) {
        this.name = name;
    }
}
