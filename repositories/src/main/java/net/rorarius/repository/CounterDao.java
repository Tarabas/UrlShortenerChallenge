package net.rorarius.repository;

public interface CounterDao {

    int getNextCount(String key);
}
