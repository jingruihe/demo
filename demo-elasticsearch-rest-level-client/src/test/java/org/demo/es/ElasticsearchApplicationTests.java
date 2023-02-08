package org.demo.es;

import org.demo.es.contants.ElasticsearchConstant;
import org.demo.es.model.Person;
import org.demo.es.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {

    @Autowired
    private PersonService personService;

    /**
     * 测试删除索引
     */
    @Test
    public void deleteIndexTest() {
        personService.deleteIndex(ElasticsearchConstant.INDEX_PERSON);
    }

    /**
     * 测试创建索引
     */
    @Test
    public void createIndexTest() {
        personService.createIndex(ElasticsearchConstant.INDEX_PERSON);
    }

    /**
     * 测试新增
     */
    @Test
    public void insertTest() throws InterruptedException {

        List<Person> list = new ArrayList<>();
        Person person = Person.builder().age(1).birthday(new Date()).country("CN").id(1L).name("哈哈").remark("test1").build();
        list.add(person);
        personService.insert(ElasticsearchConstant.INDEX_PERSON, list);
//
//        Random r = new Random();
//        ExecutorService pool = Executors.newFixedThreadPool(20);
//        long l = System.currentTimeMillis();
//
//        for (int i = 0; i < 1000; i++) {
//            int finalI = i;
//            pool.submit(new Runnable() {
//                @Override
//                public void run() {
//                    // 每次插入1条
//                    List<Person> list = new ArrayList<>();
//                    Person person = Person.builder().age(r.nextInt(100)).birthday(new Date()).country("CN").id((long) finalI).name("哈哈").remark("test1").build();
//                    list.add(person);
//                    if (list.size() >= 1000){
//                        personService.insert(ElasticsearchConstant.INDEX_PERSON, list);
//                    }
//                }
//            });
////            if (i % 10000 == 0){
////                System.out.println((i/10000) + "W数据耗时"+(System.currentTimeMillis() - l));
////            }
//        }
//        System.out.println("耗时："+(System.currentTimeMillis() - l));
//        Thread.sleep(10000);
    }

    /**
     * 测试更新
     */
    @Test
    public void updateTest() {
        Person person = Person.builder().age(33).birthday(new Date()).country("ID_update").id(3L).name("呵呵update").remark("test3_update").build();
        List<Person> list = new ArrayList<>();
        list.add(person);
        personService.update(ElasticsearchConstant.INDEX_PERSON, list);
    }

    /**
     * 测试删除
     */
    @Test
    public void deleteTest() {
        personService.delete(ElasticsearchConstant.INDEX_PERSON, Person.builder().id(1L).build());
    }

    /**
     * 测试查询
     */
    @Test
    public void searchListTest() {
        List<Person> personList = personService.searchList(ElasticsearchConstant.INDEX_PERSON);
        System.out.println(personList);
    }

}
