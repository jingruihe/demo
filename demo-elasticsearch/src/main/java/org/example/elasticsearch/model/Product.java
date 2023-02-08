package org.example.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private static final long serialVersionUID = 1L;
    private String no;
    private String name;
    private String desc;
    private Double price;
}
