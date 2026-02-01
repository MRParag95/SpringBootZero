package org.thezerobytehunter.springbootzero.module.role.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.thezerobytehunter.springbootzero.base.entity.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table( name = "role" )
public class Role extends BaseEntity {
    @Basic( fetch = FetchType.LAZY )
    private String name;
}