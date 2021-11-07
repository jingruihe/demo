package com.demo.controller;

import com.demo.constant.ESConstant;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private UserService userService;


    @GetMapping("createIndex")
    public boolean createIndex() {
        String s = "{\n" +
                "  \"properties\": {\n" +
                "    \"city\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"sex\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        return userService.createIndex(ESConstant.USER_INDEX, s);
    }

    @GetMapping("getIndex")
    public String getIndex() {
        return userService.getIndex(ESConstant.USER_INDEX);
    }

    @DeleteMapping("deleteIndex")
    public boolean deleteIndex() {
        return userService.deleteIndex(ESConstant.USER_INDEX);
    }

    @PostMapping("getAllDocument")
    public String getAllDocument() {
        return userService.getAllDocument(ESConstant.USER_INDEX);
    }

    @PostMapping("addDocument")
    public boolean addDocument() {
        return userService.addDocument(ESConstant.USER_INDEX);
    }

    @PostMapping("getDocument")
    public String getDocument() {
        return userService.getDocument(ESConstant.USER_INDEX);
    }

    @PostMapping("updateDocument")
    public Boolean updateDocument() {
        return userService.updateDocument(ESConstant.USER_INDEX);
    }
}
