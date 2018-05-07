package com.qaa.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaa.test.model.Account;
import com.qaa.test.model.ResponseMessage;
import com.qaa.test.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {

    private MockMvc mvc;

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountService accountService;

    private JacksonTester<List<Account>> jsonAccounts;
    private JacksonTester<Account> jsonAccount;
    private JacksonTester<ResponseMessage> jsonMessage;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(accountService)
                .build();
    }
    @Test
    public void getAccounts_AccountsExist_Success() throws Exception {
        List<Account> testAccounts = Arrays.asList(new Account("Jane", "Torvil", "1111"), new Account("Mia", "Ula", "2222"));
        // given
        when(repository.findAll()).thenReturn(testAccounts);

        // when
        MockHttpServletResponse response =
                mvc.perform(
                 get("/account-project/rest/account/json")
                         .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8 ))
                .andReturn().getResponse();
        //then
        assertThat(response.getContentAsString(), is(equalTo(
                jsonAccounts.write(testAccounts).getJson())));

    }
    @Test
    public void getAccounts_NoAccountsExist_Success() throws Exception {
        List<Account> testAccounts = new ArrayList<>();
        // given
        when(repository.findAll()).thenReturn(testAccounts);

        // when
        MockHttpServletResponse response =
                mvc.perform(
                        get("/account-project/rest/account/json")
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8 ))
                        .andReturn().getResponse();
        //then
        assertThat(response.getContentAsString(), is(equalTo(
                jsonAccounts.write(testAccounts).getJson())));

    }

    @Test
    public void addAccount_accountDoesntExist_Success() throws Exception {
        Account testAccount = new Account("Jane", "Torvil", "1111");
                // given
        when(repository.save(any(Account.class))).thenReturn(testAccount);


        // when
        MockHttpServletResponse response =
                mvc.perform(
                        post("/account-project/rest/account/json")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonAccount.write(testAccount).getJson()))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8 ))
                        .andReturn().getResponse();

        //then
        assertThat(response.getContentAsString(), is(equalTo(
                jsonMessage.write(new ResponseMessage("The account is successfully added.")).getJson())));

    }
    public void addAccounts_accountExists_Fail() throws Exception {
        Account testAccount = new Account("Jane", "Torvil", "1111");
        // given
        when(repository.findOptionalByFirstNameAndSecondNameAndAccountNumber(testAccount.getFirstName(), testAccount.getSecondName(), testAccount.getAccountNumber()))
                .thenReturn(Optional.of(testAccount));


        // when
        MockHttpServletResponse response =
                mvc.perform(
                        post("/account-project/rest/account/json")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(jsonAccount.write(testAccount).getJson()))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8 ))
                        .andReturn().getResponse();

        //then
        assertThat(response.getContentAsString(), is(equalTo(
                jsonMessage.write(new ResponseMessage(String.format("The account was not added. There is already an account for firstName=%s, secondName=%s, accountNumber=%s",
                testAccount.getFirstName(),
                testAccount.getSecondName(),
                testAccount.getAccountNumber()))).getJson())));

    }
    @Test
    public void deleteAccount_ExistingAccount_Success() throws Exception {

        Account testAccount = new Account("Jane", "Torvil", "1111");
        long accountId = 1l;
        // given
        when(repository.findById(accountId)).thenReturn(Optional.of(testAccount));

        // when
        MockHttpServletResponse response =
                mvc.perform(
                        delete("/account-project/rest/account/json/{id}", accountId))
                        .andExpect(status().isOk())
                      .andReturn().getResponse();

        //then
        assertThat(response.getContentAsString(), is(equalTo(
                jsonMessage.write(new ResponseMessage(String.format("Account with id=%d has been successfully deleted", accountId))).getJson())));

    }

    @Test
    public void deleteAccount_NonExistingAccount_Fail() throws Exception {

        long accountId = 1l;
        // given
        when(repository.findById(accountId)).thenReturn(Optional.empty());

        // when
        MockHttpServletResponse response =
                mvc.perform(
                        delete("/account-project/rest/account/json/{id}", accountId))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();

        //then
        assertThat(response.getContentAsString(), is(equalTo(
                jsonMessage.write(new ResponseMessage(String.format("Error in account deletion: No account with id=%d exists", accountId))).getJson())));

    }
}
