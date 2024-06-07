package com.mung.mungtique.care.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mymung")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyMung {
    // TODO : 강아지 여러마리 키울 수도 있으니 1:N

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myMungId;

    @Column(length = 50)
    private String mungName;

    @Column(length = 50)
    private String breed;

    @Column(length = 10)
    private int weight;

    @Column(length = 10)
    private int age;

    @Column(length = 20)
    private String gender; // enum 타입?

    @Column(length = 20)
    private String fixed; // enum 타입?

    @Column(length = 20)
    private Long userId;

    @OneToOne(mappedBy = "myMung", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image;

}
