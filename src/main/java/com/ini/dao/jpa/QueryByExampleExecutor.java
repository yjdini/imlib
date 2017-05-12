package com.ini.dao.jpa;

import org.springframework.data.domain.Example;

/**
 * Created by Somnus`L on 2017/5/13.
 */
public interface QueryByExampleExecutor<T> {

    <S extends T> S findOne(Example<S> example);
    <S extends T> Iterable<S> findAll(Example<S> example);
}