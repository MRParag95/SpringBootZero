package org.thezerobytehunter.springbootzero.base.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Basic( fetch = FetchType.LAZY )
    @CreationTimestamp
    private Instant createdAt;

    @Basic( fetch = FetchType.LAZY )
    private String createdBy;

    @Basic( fetch = FetchType.LAZY )
    @UpdateTimestamp
    private Instant updatedAt;

    @Basic( fetch = FetchType.LAZY )
    private String updatedBy;

    private Boolean isDeleted;
}