package org.example.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: JingRui
 * @date: 2023/2/10
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subject {

    private String subjectName;
    private Integer score;
}
