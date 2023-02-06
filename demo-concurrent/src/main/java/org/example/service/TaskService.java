package org.example.service;

import java.util.concurrent.ExecutionException;

public interface TaskService {

    void test1();
    void test2();
    void test3() throws ExecutionException, InterruptedException;
}
