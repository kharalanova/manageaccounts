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
        return null;
   }

    @RequestMapping(method= RequestMethod.POST)
    public ResponseMessage addAccount(@RequestBody Account acc) {
        return null;
    }

    @RequestMapping(method=RequestMethod.DELETE, path="/account-project/rest/account/json/{id}")
    public ResponseMessage deleteAccount(@PathVariable("id")  long id) {
           return null;
    }
}
