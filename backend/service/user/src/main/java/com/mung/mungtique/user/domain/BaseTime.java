package com.mung.mungtique.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속관계매핑에서 자식 클래스에게 매핑 정보 상속 위해
@EntityListeners(AuditingEntityListener.class) // JPA의 Auditing 기능을 활성화
public class BaseTime {

    @CreatedDate // Entity가 생성되어 저장될 때 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;
}
