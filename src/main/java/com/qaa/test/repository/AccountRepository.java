package com.qaa.test.repository;

import com.qaa.test.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
  public Optional<Account> findOptionalByFirstNameAndSecondNameAndAccountNumber(String firstName, String secondName, String accountNumber);
}
