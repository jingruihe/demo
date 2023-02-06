package org.example.service.impl;

import org.example.service.TaskService;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private Executor orderExecutor;

    @Async(AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    public void test1() {
        System.out.println(Thread.currentThread().getName());
    }

    @Async("orderExecutor")
    public void test2() {
        System.out.println(Thread.currentThread().getName());
    }

    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        System.out.println();
                        return Thread.currentThread().getName();
                    }
                }, orderExecutor)
                .thenApplyAsync(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        System.out.println("s = " + s);
                        return Thread.currentThread().getName();
                    }
                });
        System.out.println(completableFuture.get());
    }
}
