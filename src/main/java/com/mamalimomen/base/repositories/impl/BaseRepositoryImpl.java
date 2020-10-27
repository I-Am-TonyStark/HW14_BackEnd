package com.mamalimomen.base.repositories.impl;

import com.mamalimomen.base.domains.BaseEntity;
import com.mamalimomen.base.repositories.BaseRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl<PK extends Long, E extends BaseEntity> implements BaseRepository<PK, E> {
    private final EntityManager em;

    public BaseRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void closeEntityManger() {
        if (em.isOpen()) {
            em.close();
        }
    }

    @Override
    public boolean saveOne(E e) {
        try {
            em.getTransaction().begin();

            em.persist(e);

            em.getTransaction().commit();

            return true;
        } catch (EntityExistsException | RollbackException ex) {
            em.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean saveMany(List<E> l) {
        try {
            em.getTransaction().begin();

            for (E e : l)
                em.persist(e);

            em.getTransaction().commit();

            return true;
        } catch (EntityExistsException | RollbackException ex) {
            em.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean updateOne(E e) {
        try {
            em.getTransaction().begin();

            if (!em.contains(e))
                em.merge(e);

            em.getTransaction().commit();

            return true;
        } catch (IllegalArgumentException | RollbackException ex) {
            em.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean updateMany(List<E> l) {
        try {
            em.getTransaction().begin();

            for (E e : l) {
                if (!em.contains(e))
                    em.merge(e);
            }

            em.getTransaction().commit();

            return true;
        } catch (IllegalArgumentException | RollbackException ex) {
            em.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean deleteOne(E e) {
        try {
            em.getTransaction().begin();

            em.remove(e);

            em.getTransaction().commit();

            return true;
        } catch (IllegalArgumentException | RollbackException ex) {
            em.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean deleteMany(List<E> l) {
        try {
            em.getTransaction().begin();

            for (E e : l) {
                em.remove(e);
            }

            em.getTransaction().commit();
            return true;
        } catch (IllegalArgumentException | RollbackException ex) {
            em.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public Optional<E> findOneById(Class<E> c, PK id) {
        E e = em.find(c, id);
        return e != null ? Optional.of(e) : Optional.empty();
    }

    @Override
    public Optional<E> findOneByNamedQuery(String namedQuery, Class<E> c, Object... parameters) {
        E e;
        try {
            TypedQuery<E> nq = em.createNamedQuery(namedQuery, c);

            for (int i = 1; i <= parameters.length; i++) {
                nq = nq.setParameter(i, parameters[i - 1]);
            }

            e = nq.getSingleResult();
        } catch (NoResultException ex) {
            e = null;
        }
        return e != null ? Optional.of(e) : Optional.empty();
    }

    @Override
    public List<E> findManyByNamedQuery(String namedQuery, Class<E> c, Object... parameters) {
        TypedQuery<E> nq = em.createNamedQuery(namedQuery, c);

        for (int i = 1; i <= parameters.length; i++) {
            nq = nq.setParameter(i, parameters[i - 1]);
        }

        return nq.getResultList();
    }

    @Override
    public List<E> findAllByNamedQuery(String namedQuery, Class<E> c) {
        return em.createNamedQuery(namedQuery, c)
                .getResultList();
    }


    @Override
    public List<E> findAllByNativeQuery(String nativeQuery, Class<E> c) {
        return em.createNativeQuery(nativeQuery, c)
                .getResultList();
    }
}
