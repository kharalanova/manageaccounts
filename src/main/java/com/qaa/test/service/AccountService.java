package com.qaa.test.service;


import com.qaa.test.model.Account;
import com.qaa.test.model.ResponseMessage;
import com.qaa.test.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/account-project/rest/account/json")
public class AccountService {


    @Autowired
    private AccountRepository repository;

   @RequestMapping(method= RequestMethod.GET)
   public Iterable<Account> getAccounts() {
        return repository.findAll();
   }

    @RequestMapping(method= RequestMethod.POST)
    public ResponseMessage addAccount(@RequestBody Account acc) {

        Optional<Account> existingAccount = repository.findOptionalByFirstNameAndSecondNameAndAccountNumber(acc.getFirstName(), acc.getSecondName(), acc.getAccountNumber());
        if (!existingAccount.isPresent()) {
            repository.save(acc);
            return new ResponseMessage("The account is successfully added.");
        } else {
            return new ResponseMessage(String.format("The account was not added. There is already an account for firstName=%s, secondName=%s, accountNumber=%s",
                    existingAccount.get().getFirstName(),
                    existingAccount.get().getSecondName(),
                    existingAccount.get().getAccountNumber()));
        }

    }

    @RequestMapping(method=RequestMethod.DELETE, path="/account-project/rest/account/json/{id}")
    public ResponseMessage deleteAccount(@PathVariable("id")  long id) {
           Optional<Account> account = repository.findById(id);
           account.ifPresent(repository::delete);
           if (!account.isPresent()) {
               return new ResponseMessage(String.format("Error in account deletion: No account with id=%d exists", id));
           } else {
               return new ResponseMessage(String.format("Account with id=%d has been successfully deleted", id));
           }

    }
}
