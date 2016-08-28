package com.amsen.par.govideoyoself.persistence;

import java.util.List;

/**
 * Just a repo interface
 * @author Pär Amsen 2016
 */
public interface Storage<T> {
    void put(T t);
    T get(int id);
    List<T> getAll();
    void invalidate();
}
