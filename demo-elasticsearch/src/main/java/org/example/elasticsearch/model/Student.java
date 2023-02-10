package org.example.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {

    private String name;
    private Integer age;
    private List<String> hobby;
    private String birthday;

    private List<Subject> subject;
}
