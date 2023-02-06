package org.example.controller;

import org.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RequestMapping("/")
@RestController
public class DemoController {


    @Autowired
    private TaskService taskService;

    @GetMapping("/demo")
    public String demo() throws Exception {
        taskService.test3();
//        taskService.test2();
        return "123";
    }
}
