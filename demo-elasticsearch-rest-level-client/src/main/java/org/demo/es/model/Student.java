package org.demo.es.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 8510634155374943623L;

    private Long id;

    private Long uid;

    private Integer age;

    private String hobby;

    private String name;


}
