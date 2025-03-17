package com.mung.mungtique.dog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dog")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dogId;

    @Column(length = 50, nullable = false)
    private String dogName;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BreedType breedType;

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private FixedStatus fixed;

    private Long userId;

    @Builder
    private Dog(Long dogId, String dogName, BreedType breedType, int weight, int age, Gender gender, FixedStatus fixed, Long userId, Image image) {
        this.dogId = dogId;
        this.dogName = dogName;
        this.breedType = breedType;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.fixed = fixed;
        this.userId = userId;
        this.image = image;
    }

    @OneToOne(mappedBy = "dog", cascade = CascadeType.ALL)
    private Image image;
}
