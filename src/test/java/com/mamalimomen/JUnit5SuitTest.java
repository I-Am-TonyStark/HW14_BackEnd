package com.mamalimomen;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RunWith(JUnitPlatform.class)
@SelectPackages({"com.mamalimomen"})
@IncludeTags({"repository"})
public class JUnit5SuitTest {
    public static EntityManagerFactory  emf = Persistence.createEntityManagerFactory("persistence-unit-one");
}
