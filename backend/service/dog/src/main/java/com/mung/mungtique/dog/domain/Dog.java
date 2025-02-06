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

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private BreedType breedType;

    @Column(length = 10, nullable = false)
    private int weight;

    @Column(length = 10, nullable = false)
    private int age;

    @Column(length = 20, nullable = false)
    private String gender; // enum 타입?

    @Column(length = 20, nullable = false)
    private String fixed; // enum 타입?

    @Column(length = 20)
    private Long userId;

    @OneToOne(mappedBy = "dog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image;

}
