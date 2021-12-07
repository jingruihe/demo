package org.es.demo.repository;

import org.es.demo.entity.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


@Component
public interface StudentRepository extends ElasticsearchRepository<Student, Long> {

}
