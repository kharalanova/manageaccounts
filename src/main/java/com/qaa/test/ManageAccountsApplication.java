package com.qaa.test;

import com.qaa.test.model.Account;
import com.qaa.test.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
    public class ManageAccountsApplication {
        private static final Logger log = LoggerFactory.getLogger(ManageAccountsApplication.class);

        public static void main(String[] args) {
            SpringApplication.run(ManageAccountsApplication.class, args);
        }


        @Bean
        public CommandLineRunner demo(AccountRepository repository) {
            return (args) -> {
                // save a couple of customers
                repository.save(new Account("Jack", "Bauer", "1111"));
                repository.save(new Account("Chloe", "O'Brian", "2222"));
                repository.save(new Account("Kim", "Bauer", "3333"));
                repository.save(new Account("David", "Palmer", "4444"));
                repository.save(new Account("Michelle", "Dessler", "5555"));

                // fetch all customers
                log.info("Accounts found with findAll():");
                log.info("-------------------------------");
                for (Account account : repository.findAll()) {
                    log.info(account.toString());
                }
                log.info("");

            };
        }
    }


