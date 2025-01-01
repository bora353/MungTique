package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mymung")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyMung extends BaseTime {
    // TODO : 강아지 여러마리 키울 수도 있으니 1:N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myMungId;

    @Column(length = 50, nullable = false)
    private String mungName;

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

    @OneToOne(mappedBy = "myMung", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image;

}
