package com.mamalimomen.services.Impl;

import com.mamalimomen.services.LikeService;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Tag("service")
class LikeServiceImplTest {
    static EntityManagerFactory emf;
    LikeService ls;
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
        ls = new LikeServiceImpl(em);
    }

    @AfterEach
    void afterEach() {
        ls.closeEntityManger();
        ls = null;
    }

    @Test
    @Order(1)
    @DisplayName("Create new Like")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void createNewLikeTest() {
    }
}