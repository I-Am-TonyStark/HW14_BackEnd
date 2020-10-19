package com.mamalimomen.services.Impl;

import com.mamalimomen.services.AccountService;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.concurrent.TimeUnit;

@Tag("service")
class AccountServiceImplTest {
    static EntityManagerFactory emf;
    AccountService as;
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
        as = new AccountServiceImpl(em);
    }

    @AfterEach
    void afterEach() {
        as.closeEntityManger();
        as = null;
    }

    @Test
    @Order(1)
    @DisplayName("Create new Account")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void createNewAccountTest() {
    }

    @Test
    @Order(2)
    @DisplayName("Retrieve exist active Account")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void retrieveExistActiveAccountTest() {
    }

    @Test
    @Order(3)
    @DisplayName("Update exist active Account password")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void updateExistActiveAccountPasswordTest() {
    }

    @Test
    @Order(4)
    @DisplayName("Update exist active Account information")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void updateExistActiveAccountInformationTest() {
    }

    @Test
    @Order(5)
    @DisplayName("Add exist active Account a Post")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void addExistActiveAccountAPostTest() {
    }

    @Test
    @Order(6)
    @DisplayName("Remove exist active Account a Post")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void removeExistActiveAccountAPostTest() {
    }

    @Test
    @Order(7)
    @DisplayName("Add exist active Account a following")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void addExistActiveAccountAFollowingTest() {
    }

    @Test
    @Order(8)
    @DisplayName("Remove exist active Account a following")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void removeExistActiveAccountAFollowingTest() {
    }

    @Test
    @Order(9)
    @DisplayName("Delete exist active Account")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void deleteExistActiveAccountTest() {
    }
}