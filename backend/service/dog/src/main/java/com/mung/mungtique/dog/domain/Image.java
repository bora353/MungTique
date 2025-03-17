package com.mung.mungtique.dog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dogId", nullable = false)
    private Dog dog;

    @Builder
    private Image(Long imageId, String url, Dog dog) {
        this.imageId = imageId;
        this.url = url;
        this.dog = dog;
    }

    public void updateUrl(String url){
        this.url = url;
    }
}
