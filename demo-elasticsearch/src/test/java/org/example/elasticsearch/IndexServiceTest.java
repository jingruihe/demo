package org.example.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperationVariant;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.util.ObjectBuilder;
import org.example.elasticsearch.model.Product;
import org.example.elasticsearch.service.IndexService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SpringBootTest
class IndexServiceTest {
    @Autowired
    IndexService indexService;

    @Test
    void addIndex() throws Exception {
        String indexName = "test_index";
        Assertions.assertFalse(indexService.indexExists(indexName));
        indexService.addIndex(indexName);
        Assertions.assertTrue(indexService.indexExists(indexName));
        indexService.delIndex(indexName);
        Assertions.assertFalse(indexService.indexExists(indexName));
    }

    @Test
    void indexExists() throws Exception {
        indexService.indexExists("a");
    }

    @Test
    void createIndex() throws Exception {
        // 索引名
        String indexName = "product002";
        // 构建setting时，builder用到的lambda
        Function<IndexSettings.Builder, ObjectBuilder<IndexSettings>> settingFn = sBuilder -> sBuilder
                .index(iBuilder -> iBuilder
                        // 三个分片
                        .numberOfShards("3")
                        // 一个副本
                        .numberOfReplicas("1")
                );
        // 新的索引有三个字段，每个字段都有自己的property，这里依次创建
        Property keywordProperty = Property.of(pBuilder -> pBuilder.keyword(kBuilder -> kBuilder.ignoreAbove(256)));
        Property textProperty = Property.of(pBuilder -> pBuilder.text(tBuilder -> tBuilder));
        Property integerProperty = Property.of(pBuilder -> pBuilder.integer(iBuilder -> iBuilder));
        // // 构建mapping时，builder用到的lambda
        Function<TypeMapping.Builder, ObjectBuilder<TypeMapping>> mappingFn = mBuilder -> mBuilder
                .properties("name", keywordProperty)
                .properties("description", textProperty)
                .properties("price", integerProperty);
        // 创建索引，并且指定了setting和mapping
        indexService.create(indexName, settingFn, mappingFn);
    }

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    // 写入实体
    @Test
    void createProduct() throws Exception {
        Product product = new Product("N1", "food", "这是面包", 5.55);
        elasticsearchClient.index(i -> i.index("product_index")
                .id(product.getNo())
                .document(product));
    }

    // https://blog.csdn.net/qq_35270805/article/details/125231511
    // 批量写入
    @Test
    void createBatchProduct() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product("N1", "food", "这是面包", 5.55));
        products.add(new Product("N2", "book", "这是书", 0.55));

        BulkRequest.Builder builder = new BulkRequest.Builder();
        builder.index("product_index");
        for (Product product : products) {
            builder.operations(op -> op.index(index -> index.document(product)));
        }
        BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build());
        System.out.println(bulkResponse);
    }
}
