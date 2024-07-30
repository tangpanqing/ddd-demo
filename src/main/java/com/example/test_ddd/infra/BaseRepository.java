package com.example.test_ddd.infra;

public interface BaseRepository<T> {
    T take(Long id);

    void put(T aggregate);
}
