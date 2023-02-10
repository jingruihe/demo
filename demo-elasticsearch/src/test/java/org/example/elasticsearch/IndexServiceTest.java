package org.example.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.util.ObjectBuilder;
import org.example.elasticsearch.service.IndexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

@SpringBootTest
class IndexServiceTest {

    private static final String INDEX_STUDENT = "student";
    @Autowired
    IndexService indexService;

    @Test
    void indexExist() throws Exception {
        indexService.indexExists(INDEX_STUDENT);
    }

    @Test
    void delIndex() throws Exception {
        indexService.delIndex(INDEX_STUDENT);
    }

    @Test
    void createIndex() throws Exception {
        // 索引名
        // 构建setting时，builder用到的lambda
        Function<IndexSettings.Builder, ObjectBuilder<IndexSettings>> settingFn = sBuilder -> sBuilder
                .index(iBuilder -> iBuilder
                        // 三个分片
                        .numberOfShards("3")
                        // 一个副本
                        .numberOfReplicas("1")
                );
        // 新的索引有三个字段，每个字段都有自己的property，这里依次创建
        // // 构建mapping时，builder用到的lambda
        Function<TypeMapping.Builder, ObjectBuilder<TypeMapping>> mappingFn = builder -> builder
                .properties("name", Property.of(pb -> pb.keyword(new KeywordProperty.Builder().ignoreAbove(256).build())))
                .properties("age", Property.of(pb -> pb.integer(new IntegerNumberProperty.Builder().build())))
                .properties("hobby", Property.of(pb -> pb.keyword(new KeywordProperty.Builder().build())))
                .properties("birthday", Property.of(pb -> pb.date(new DateProperty.Builder().format("yyyy-MM-dd").build())))
                .properties("subject", Property.of(pb -> pb.nested(new NestedProperty.Builder()
                        .properties("subjectName", Property.of(npb -> npb.keyword(new KeywordProperty.Builder().ignoreAbove(20).build())))
                        .properties("score", Property.of(npb -> npb.integer(new IntegerNumberProperty.Builder().build())))
                        .build())))
                ;
        // 创建索引，并且指定了setting和mapping
        indexService.create(INDEX_STUDENT, settingFn, mappingFn);
    }

}
