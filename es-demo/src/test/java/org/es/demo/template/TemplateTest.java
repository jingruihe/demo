package org.es.demo.template;

import org.es.demo.EsApplicationTests;
import org.es.demo.entity.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * <p>
 * 测试 ElasticTemplate 的创建/删除
 * </p>
 */
public class TemplateTest extends EsApplicationTests {
    @Autowired
    private ElasticsearchTemplate esTemplate;

    /**
     * 测试 ElasticTemplate 创建 index
     */
    @Test
    public void testCreateIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        esTemplate.createIndex(Person.class);

        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        esTemplate.putMapping(Person.class);
    }

    /**
     * 测试 ElasticTemplate 删除 index
     */
    @Test
    public void testDeleteIndex() {
        esTemplate.deleteIndex(Person.class);
    }
}
