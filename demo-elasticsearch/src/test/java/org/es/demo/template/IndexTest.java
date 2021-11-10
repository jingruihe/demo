package org.es.demo.template;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.es.demo.EsApplicationTests;
import org.es.demo.entity.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

/**
 * <p>
 * 测试 ElasticTemplate 的创建/删除
 * </p>
 */
@Slf4j
public class IndexTest extends EsApplicationTests {
//    @Autowired
//    private ElasticsearchTemplate esTemplate;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 测试 ElasticTemplate 创建 index
     */
    @Test
    public void testCreateIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
//        esTemplate.createIndex(Person.class);

        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Person.class);
        indexOperations.create();

        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
//        esTemplate.putMapping(Person.class);
        Document mapping = indexOperations.createMapping();
        indexOperations.putMapping(mapping);
    }

    /**
     * 测试 ElasticTemplate 删除 index
     * TODO 删除无效
     */
    @Test
    public void testDeleteIndex() {
        log.info(elasticsearchRestTemplate.indexOps(Person.class).delete() + "");
    }

    @Test
    public void indexExists() {
        log.info(elasticsearchRestTemplate.indexOps(Person.class).exists() + "");
    }
}
