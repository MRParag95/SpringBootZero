package org.thezerobytehunter.springbootzero.module.user.repository;

import org.springframework.stereotype.Repository;
import org.thezerobytehunter.springbootzero.base.repository.AbstractRepository;
import org.thezerobytehunter.springbootzero.module.user.entity.User;

@Repository
public interface UserRepository extends AbstractRepository< User > {
}