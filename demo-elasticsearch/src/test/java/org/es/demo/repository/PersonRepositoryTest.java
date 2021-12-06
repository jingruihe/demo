package org.es.demo.repository;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.es.demo.EsApplicationTests;
import org.es.demo.entity.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

/**
 * <p>
 * 测试 Repository 操作ES
 * </p>
 */
@Slf4j
public class PersonRepositoryTest extends EsApplicationTests {

    @Autowired
    private PersonRepository repo;

    /**
     * 自定义高级查询
     */
    @Test
    public void customAdvanceSelect() {
        // 构造查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("remark", "东汉"));
        // 排序条件
        queryBuilder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));
        // 分页条件
        queryBuilder.withPageable(PageRequest.of(0, 2));
//        Page<Person> people = repo.search(queryBuilder.build());
//        log.info("【people】总条数 = {}", people.getTotalElements());
//        log.info("【people】总页数 = {}", people.getTotalPages());
//        people.forEach(person -> log.info("【person】= {}，年龄 = {}", person.getName(), person.getAge()));
    }

    /**
     * 测试聚合，测试平均年龄
     */
    @Test
    public void agg() {
        // 构造查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        // 平均年龄
        queryBuilder.addAggregation(AggregationBuilders.avg("avg").field("age"));

        log.info("【queryBuilder】= {}", JSONUtil.toJsonStr(queryBuilder.build()));

//        AggregatedPage<Person> people = (AggregatedPage<Person>) repo.search(queryBuilder.build());
//        double avgAge = ((InternalAvg) people.getAggregation("avg")).getValue();
//        log.info("【avgAge】= {}", avgAge);
    }

    /**
     * 测试高级聚合查询，每个国家的人有几个，每个国家的平均年龄是多少
     */
    @Test
    public void advanceAgg() {
        // 构造查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        // 1. 添加一个新的聚合，聚合类型为terms，聚合名称为country，聚合字段为age
        queryBuilder.addAggregation(AggregationBuilders.terms("country").field("country")
                // 2. 在国家聚合桶内进行嵌套聚合，求平均年龄
                .subAggregation(AggregationBuilders.avg("avg").field("age")));

        log.info("【queryBuilder】= {}", JSONUtil.toJsonStr(queryBuilder.build()));

        // 3. 查询
//        AggregatedPage<Person> people = (AggregatedPage<Person>) repo.search(queryBuilder.build());
//
//        // 4. 解析
//        // 4.1. 从结果中取出名为 country 的那个聚合，因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
//        StringTerms country = (StringTerms) people.getAggregation("country");
//        // 4.2. 获取桶
//        List<StringTerms.Bucket> buckets = country.getBuckets();
//        for (StringTerms.Bucket bucket : buckets) {
//            // 4.3. 获取桶中的key，即国家名称  4.4. 获取桶中的文档数量
//            log.info("{} 总共有 {} 人", bucket.getKeyAsString(), bucket.getDocCount());
//            // 4.5. 获取子聚合结果：
//            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("avg");
//            log.info("平均年龄：{}", avg);
//        }
    }

}
