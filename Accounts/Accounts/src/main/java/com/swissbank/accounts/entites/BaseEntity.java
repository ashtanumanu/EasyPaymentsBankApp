package com.swissbank.accounts.entites;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Data
@MappedSuperclass
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private String createdBy;

   // @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

   // @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;


}
