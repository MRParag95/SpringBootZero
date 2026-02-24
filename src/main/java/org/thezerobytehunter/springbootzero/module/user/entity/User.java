package org.thezerobytehunter.springbootzero.module.user.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.thezerobytehunter.springbootzero.base.entity.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@Entity
@Table( name = "users" )
public class User extends BaseEntity {
    @Basic( fetch = FetchType.LAZY )
    private String firstName;

    @Basic( fetch = FetchType.LAZY )
    private String lastName;

    @Basic( fetch = FetchType.LAZY )
    private String username;

    @Basic( fetch = FetchType.LAZY )
    private String email;

    @Basic( fetch = FetchType.LAZY )
    private String password;
}