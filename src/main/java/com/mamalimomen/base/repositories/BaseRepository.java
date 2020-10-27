package com.mamalimomen.base.repositories;

import com.mamalimomen.base.domains.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<PK extends Long, E extends BaseEntity> {
    void closeEntityManger();

    boolean saveOne(E e);

    boolean saveMany(List<E> l);

    boolean updateOne(E e);

    boolean updateMany(List<E> l);

    boolean deleteOne(E e);

    boolean deleteMany(List<E> l);

    Optional<E> findOneById(Class<E> c, PK id);

    Optional<E> findOneByNamedQuery(String namedQuery, Class<E> c, Object... parameters);

    List<E> findManyByNamedQuery(String namedQuery, Class<E> c, Object... parameters);

    List<E> findAllByNamedQuery(String namedQuery, Class<E> c);

    List<E> findAllByNativeQuery(String nativeQuery, Class<E> c);
}