package org.thezerobytehunter.springbootzero.module.role.repository;

import org.springframework.stereotype.Repository;
import org.thezerobytehunter.springbootzero.base.repository.AbstractRepository;
import org.thezerobytehunter.springbootzero.module.role.entity.Role;

@Repository
public interface RoleRepository extends AbstractRepository< Role > {
}