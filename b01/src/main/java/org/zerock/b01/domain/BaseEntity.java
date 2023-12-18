package org.zerock.b01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {


    @CreatedDate
    @Column(name = "regdate", updatable = false) /*컬럼명 고정*/
    private LocalDateTime regDate;
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}