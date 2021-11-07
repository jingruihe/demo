package com.demo.service;

import com.alibaba.fastjson.JSON;
import com.demo.entity.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private RestHighLevelClient client;

    public boolean createIndex(String index, String jsonString){
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
                .build());
        request.mapping(jsonString, XContentType.JSON);
        try {
            //获得连接
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SneakyThrows
    public boolean deleteIndex(String index) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @SneakyThrows
    public String getIndex(String index) {
        GetIndexResponse response = client.indices().get(new GetIndexRequest(index), RequestOptions.DEFAULT);
        return Arrays.stream(response.getIndices()).collect(Collectors.joining(","));
    }

    @SneakyThrows
    public boolean addDocument(String index) {
        User user = new User();
        user.setId("1");
        user.setAge(18);
        user.setSex("man");
        user.setName("张三");
        user.setCity("西安");
        IndexRequest indexRequest = new IndexRequest(index)
                .id(user.getId())
                .source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.status().equals(RestStatus.OK);
    }

    @SneakyThrows
    public String getDocument(String index) {
        GetResponse response = client.get(new GetRequest(index, "1"), RequestOptions.DEFAULT);
        return JSON.toJSONString(response);
    }


    @SneakyThrows
    public Boolean updateDocument(String index) {
        User user = new User();
        user.setAge(18+1);
        user.setSex("man"+1);
        user.setName("张三"+1);
        user.setCity("西安"+1);
        UpdateRequest request = new UpdateRequest(index, "1");
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return response.status().equals(RestStatus.OK);
    }

    @SneakyThrows
    public String getAllDocument(String index) {
        // 搜索条件构造器 设置搜索条件为：matchAll
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        // 构建搜索请求 传入参数为：index名
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(builder);

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        // 获取TotalHits和MaxScore
        System.out.println("检索出的文档总数:" + search.getHits().getTotalHits());
        System.out.println("检索出的文档最大得分:" + search.getHits().getMaxScore());
        // 检索出的所有文档
        for (SearchHit hit : search.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
        return null;
    }
}
