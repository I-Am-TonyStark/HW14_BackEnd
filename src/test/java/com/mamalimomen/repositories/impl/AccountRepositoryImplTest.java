package com.mamalimomen.repositories.impl;

import com.mamalimomen.base.controllers.utilities.InValidDataException;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.User;
import com.mamalimomen.repositories.AccountRepository;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;

@Tag("repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountRepositoryImplTest {
    static EntityManagerFactory emf;
    AccountRepository ar;
    EntityManager em;

    @BeforeAll
    static void beforeAll() {
        emf = Persistence.createEntityManagerFactory("persistence-unit-one");
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }

    @BeforeEach
    void beforeEach() {
        em = emf.createEntityManager();
        ar = new AccountRepositoryImpl(em);
    }

    @AfterEach
    void afterEach() {
        ar.closeEntityManger();
        ar = null;
    }


    @Test
    @Order(2)
    @DisplayName("Find one active Account by username")
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void findOneActiveAccountByUsernameTest() {
        Optional<Account> oAccount1 = ar.findOneActiveAccountByUsername("test");
        assertFalse(oAccount1.isPresent());

        try{
            User user = new User();
            user.setAboutMe("test test test test test");
            user.setFirstName("test");
            user.setLastName("test");
            user.setUsername("test");
            user.setPassword("Test2120");

            Account account1 = new Account();
            account1.setUser(user);
            ar.saveOne(account1);

            Optional<Account> oAccount2 = ar.findOneActiveAccountByUsername("test");
            Account account2 = oAccount2.get();
            assertEquals(account1,account2);

            account2.setDeleted(true);
            ar.updateOne(account2);
            Optional<Account> oAccount3 = ar.findOneActiveAccountByUsername("test");
            assertFalse(oAccount3.isPresent());

            ar.deleteOne(account2);
            Optional<Account> oAccount4 = ar.findOneActiveAccountByUsername("test");
            assertFalse(oAccount4.isPresent());
        }catch (InValidDataException| NoSuchElementException e){
            fail();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Find one Account by username")
    @Timeout(value = 10000, unit = TimeUnit.MILLISECONDS)
    void findOneAccountByUsernameTest() {
        Optional<Account> oAccount1 = ar.findOneAccountByUsername("test");
        assertFalse(oAccount1.isPresent());

        try{
            User user = new User();
            user.setAboutMe("test test test test test");
            user.setFirstName("test");
            user.setLastName("test");
            user.setUsername("test");
            user.setPassword("Test2120");

            Account account1 = new Account();
            account1.setUser(user);
            ar.saveOne(account1);

            Optional<Account> oAccount2 = ar.findOneAccountByUsername("test");
            Account account2 = oAccount2.get();
            assertEquals(account1,account2);

            account2.setDeleted(true);
            ar.updateOne(account2);
            Optional<Account> oAccount3 = ar.findOneAccountByUsername("test");
            Account account3 = oAccount3.get();
            assertEquals(account2,account3);

            ar.deleteOne(account3);
            Optional<Account> oAccount4 = ar.findOneAccountByUsername("test");
            assertFalse(oAccount4.isPresent());
        }catch (InValidDataException| NoSuchElementException e){
            fail();
        }
    }
}