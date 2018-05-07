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


    }


