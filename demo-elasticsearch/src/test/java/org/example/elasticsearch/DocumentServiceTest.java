package org.example.elasticsearch;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import org.example.elasticsearch.model.Student;
import org.example.elasticsearch.model.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: JingRui
 * @date: 2023/2/10
 **/
@SpringBootTest
public class DocumentServiceTest {

    private static final String INDEX_STUDENT = "student";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    // 写入实体
    @Test
    void createDocument() throws Exception {
        Student student = new Student("小明", 16, ListUtil.of("游戏", "听歌"), "1994-03-11",
                ListUtil.of(new Subject("语文", 70), new Subject("数学", 88)));
        elasticsearchClient.index(i -> i.index(INDEX_STUDENT)
//                .id(product.getNo())
                .document(student));
    }

    // https://blog.csdn.net/qq_35270805/article/details/125231511
    // 批量写入
    @Test
    void createBatchDocument() throws Exception {
        List<Student> students = new ArrayList<>();
        students.add(new Student("小红", 18, ListUtil.of("看书", "听歌"), "1992-03-11",
                ListUtil.of(new Subject("语文", 95), new Subject("数学", 59))));
        students.add(new Student("小张", 20, ListUtil.of("游戏", "听歌", "骑车"), "1994-03-11",
                ListUtil.of(new Subject("语文", 60), new Subject("数学", 100))));
        BulkRequest.Builder builder = new BulkRequest.Builder();
        builder.index(INDEX_STUDENT);
        for (Student student : students) {
            builder.operations(op -> op.index(index -> index.document(student)));
        }
        BulkResponse bulkResponse = elasticsearchClient.bulk(builder.build());
        System.out.println(JSONUtil.toJsonStr(bulkResponse));
    }

    @Test
    void updateDocument(){
//        elasticsearchClient.updateByQuery()
    }
}
