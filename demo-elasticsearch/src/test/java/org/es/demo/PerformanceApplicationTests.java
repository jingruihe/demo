package org.es.demo;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.es.demo.constant.ESConstant;
import org.es.demo.entity.Person;
import org.es.demo.entity.Student;
import org.es.demo.repository.PersonRepository;
import org.es.demo.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceApplicationTests {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private StudentRepository repo;

    @Test
    public void testCreateIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(ESConstant.INDEX_PERSON));
        indexOperations.create();
    }

    /**
     * -Xms3g -Xmx3g 单机配置下：
     * 插入100w耗时：63789
     */
    @Test
    public void saveList() {
        long l = System.currentTimeMillis();
        List<Student> students = new ArrayList<>();
        int start = 0;
        for (int i = start; i < 1000000; i++) {
            Student student = new Student((long) i, "学生" + i, i, DateUtil.parse("1988-01-02 03:04:05"), i % 2 == 0 ? "男" : "女", System.currentTimeMillis() + "");
            students.add(student);
        }
        List<List<Student>> partition = Lists.partition(students, 100000);
        partition.forEach(s -> {
            repo.saveAll(s);
        });
        log.info("插入100w耗时：{}", (System.currentTimeMillis()-l));
    }
}
