package com.mung.mungtique.dog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dog")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dog extends BaseTime {
    // TODO : 강아지 여러마리 키울 수도 있으니 1:N

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

    @OneToOne(mappedBy = "dog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image;
}
