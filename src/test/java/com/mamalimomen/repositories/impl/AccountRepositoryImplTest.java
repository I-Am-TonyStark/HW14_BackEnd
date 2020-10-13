package com.mamalimomen.repositories.impl;

import com.mamalimomen.JUnit5SuitTest;
import com.mamalimomen.domains.Account;
import com.mamalimomen.domains.Post;
import com.mamalimomen.domains.User;
import com.mamalimomen.repositories.AccountRepository;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.*;

@Tag("repository")
class AccountRepositoryImplTest {
    public static User user;
    public static User user2;
    public static Account account;
    public static Account account2;
    static long time;
    static EntityManager em;
    static AccountRepository accountRepository;

    @BeforeAll
    static void beforeAll() {
        try {
            em = JUnit5SuitTest.emf.createEntityManager();
            accountRepository = new AccountRepositoryImpl(em);

            user = new User();
            account = new Account();
            user.setPassword("test");
            user.setUsername("test");
            user.setFirstName("test");
            user.setLastName("test");
            user.setAboutMe("test");
            account.setUser(user);
            assertTrue(accountRepository.saveOne(account));

            user2 = new User();
            user2.setPassword("test");
            user2.setUsername("test1");
            user2.setFirstName("test");
            user2.setLastName("test");
            user2.setAboutMe("test");
            account2 = new Account();
            account2.setUser(user2);
            assertTrue(accountRepository.saveOne(account2));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @BeforeEach
    void beforeEach() {
        time = System.nanoTime();
    }

    @AfterEach
    void afterEach() {
        System.out.println((System.nanoTime() - time) / 1000000000.0);
    }


    @Test
    @Order(2)
    @DisplayName("Update One Account Test")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void updateOne() {
        Optional<Account> oAccount = accountRepository.findOneActiveAccountByUsername("test");
        Account account = oAccount.get();
        account.getUser().setUsername("test2");
        assertTrue(accountRepository.updateOne(account));

        oAccount = accountRepository.findOneActiveAccountByUsername("test1");
        account = oAccount.get();
        account.getUser().setUsername("test2");
        assertFalse(accountRepository.updateOne(account));
    }

    @Test
    @Order(3)
    @DisplayName("Find Account by username Test")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void findOneActiveAccountByUsername() {

        Optional<Account> oTest = accountRepository.findOneActiveAccountByUsername("test");
        assertTrue(oTest.isEmpty());

        Optional<Account> oTest1 = accountRepository.findOneActiveAccountByUsername("test1");
        assertFalse(oTest1.isEmpty());
    }


    @AfterAll
    @DisplayName("Close EntityManager Test")
    static void closeEntityManger() {
        Optional<Account> oTest2 = accountRepository.findOneActiveAccountByUsername("test2");
        Account account = oTest2.get();
        assertTrue(accountRepository.deleteOne(account));

        Optional<Account> oTest1 = accountRepository.findOneActiveAccountByUsername("test1");
        account = oTest1.get();
        assertTrue(accountRepository.deleteOne(account));

        assertFalse(accountRepository.deleteOne(AccountRepositoryImplTest.account));

        accountRepository.closeEntityManger();
    }
}