package edu.sdr.electronics.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String text;
    private LocalDateTime date;
    private Integer noOfLikes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private StoreUser user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_like",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<StoreUser> likedBy = new HashSet<>();


}
